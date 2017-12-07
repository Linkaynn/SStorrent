package com.jeseromero.model;

import com.jeseromero.util.RandomString;

public class Token {

    private static final int DEFAULT_TOKEN_EXPIRE_IN_MILIS = 30 * 60 * 1000;

    private final String token;
    private final long expires;

    public Token() {
        token = new RandomString().nextString();
        expires = System.currentTimeMillis() + DEFAULT_TOKEN_EXPIRE_IN_MILIS;
    }

    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }
}
