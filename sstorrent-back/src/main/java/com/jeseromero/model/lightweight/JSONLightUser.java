package com.jeseromero.model.lightweight;

import com.jeseromero.model.Token;
import com.jeseromero.model.User;


public class JSONLightUser extends JSONable{
    private final String username;
    private final String name;
    private final String email;

    private final Token token;

    public JSONLightUser(User user, Token token) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.token = token;
    }

	public String getUsername() {
		return username;
	}

	public String getName() {
        return name;
    }
}
