package com.jeseromero.model.lightweight;

import com.jeseromero.model.Token;
import com.jeseromero.model.User;


public class JSONLightUser extends JSONable{
    private final String name;
    private final Token token;

    public JSONLightUser(User user, Token token) {
        this.name = user.getName();
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public Token getToken() {
        return token;
    }
}
