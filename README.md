# Build
mvn clean package && docker build -t com.jeseromero/sstorrent .

# RUN

docker rm -f sstorrent || true && docker run -d -p 8080:8080 -p 4848:4848 --name sstorrent com.jeseromero/sstorrent 