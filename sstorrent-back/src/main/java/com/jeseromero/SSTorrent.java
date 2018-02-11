package com.jeseromero;

import com.jeseromero.resources.LoginResource;
import com.jeseromero.resources.LogoutResource;
import com.jeseromero.resources.SearchResource;
import com.jeseromero.resources.UserResources;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class SSTorrent extends Application {
	public Set<Class<?>> getClasses() {

		Set<Class<?>> s = new HashSet<>();
		s.add(LoginResource.class);
		s.add(UserResources.class);
		s.add(SearchResource.class);
		s.add(LogoutResource.class);
		return s;
	}
}
