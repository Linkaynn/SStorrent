package com.jeseromero.model;

import com.jeseromero.util.RandomString;

import java.util.Objects;

public class Token {

    private static final int DEFAULT_TOKEN_EXPIRE_IN_MILIS = 15 * 1000;

    private final String token;
    private final long expires;

	public Token(String token) {
		this.token = token;
		expires = DEFAULT_TOKEN_EXPIRE_IN_MILIS;
	}

	public Token() {
		this(new RandomString().nextString());
	}

	public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;

		if (o.getClass() == String.class) {
			return o.equals(token);
		}

		if (getClass() != o.getClass()) return false;

		Token token = (Token) o;

		return token.token.equals(((Token) o).token);
	}

	@Override
	public int hashCode() {

		return Objects.hash(token);
	}
}
