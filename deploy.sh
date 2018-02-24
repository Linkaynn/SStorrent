#!/bin/sh

# Variable init

HOST=${HOST}
USER=${USER}
PASSWORD=${PASSWORD}
DEPLOY_REMOTE_PATH=${DEPLOY_REMOTE_PATH}

INITIAL_PATH=`pwd`

TEMP_FOLDER_PATH=${INITIAL_PATH}/temp/

# Functions

function print {
    echo -e "$1\n"
}

function removeTemp {
    rm -rf ${TEMP_FOLDER_PATH}
    rm -rf ${INITIAL_PATH}/sstorrent-back/target/
    rm -rf ${INITIAL_PATH}/sstorrent-front/dist/
}

function createTemp {
    removeTemp

    mkdir ${INITIAL_PATH}/temp/
    mkdir ${INITIAL_PATH}/temp/sstorrent/
    mkdir ${INITIAL_PATH}/temp/dist/
}

function checkLastCommand {
    if [ $? != 0 ]; then
        print "$1. Exiting..."
        removeTemp
        exit 1
    fi
}

function buildBack {
    cd ${INITIAL_PATH}/sstorrent-back/

    print "Building SSTorrent back end..."

    mvn package >& ${INITIAL_PATH}/deploy-back.log

    checkLastCommand "Error packaging SSTorrent back..."

    mv ./target/sstorrent/* ${TEMP_FOLDER_PATH}sstorrent/

    checkLastCommand "Error moving the build of back end..."
}

function buildFront {
    cd ${INITIAL_PATH}/sstorrent-front/

    print "Installing dependencies..."

    npm install >& ${INITIAL_PATH}/deploy-front.log

    checkLastCommand "Error installing dependencies..."

    print "Building SSTorrent front end..."

    ng build --prod >& ${INITIAL_PATH}/deploy-front.log

    checkLastCommand "Error building front end..."

    mv ./dist/* ${TEMP_FOLDER_PATH}dist

    checkLastCommand "Error moving the build of front end..."
}

function execute {
    ssh ${USER}@${HOST} $1 >& ${INITIAL_PATH}/deploy-host.log
}

function deploy {
    print "Removing deploy files in $HOST..."

    execute "rm -rf $DEPLOY_REMOTE_PATH/*"

    print "Coping files in $HOST..."

    scp -r ${TEMP_FOLDER_PATH}* ${USER}@${HOST}:${DEPLOY_REMOTE_PATH}

    print "Moving files of back end in $HOST..."

    execute "\$CATALINA_HOME/bin/shutdown.sh && rm -rf \$CATALINA_HOME/webapps/sstorrent/ && mkdir \$CATALINA_HOME/webapps/sstorrent/ && mv $DEPLOY_REMOTE_PATH/sstorrent/* \$CATALINA_HOME/webapps/sstorrent/ && \$CATALINA_HOME/bin/startup.sh"

    print "Moving files of front end in $HOST..."

    execute "rm -rf /var/www/html/* && mv $DEPLOY_REMOTE_PATH/dist/* /var/www/html/"
}

# Main

print "This script will deploy to $HOST..."

print "Create temporal out folder in $INITIAL_PATH/temp"

createTemp

print "Entering in back building..."

buildBack

print "Back built..."

print "Entering in front building..."

buildFront

print "Front built..."

print "Deploy initialized..."

deploy

print "SSTorrent deployed..."

removeTemp