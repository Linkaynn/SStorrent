package com.jeseromero;

import com.jeseromero.resources.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class SSTorrent extends Application {
	public Set<Class<?>> getClasses() {

		Set<Class<?>> s = new HashSet<>();
		s.add(LoginResource.class);
		s.add(RegisterResource.class);
		s.add(UserResources.class);
		s.add(SearchResource.class);
		s.add(LogoutResource.class);
		return s;
	}
}
