package com.jeseromero.core.controller;


import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.configurations.LimeTorrentsConfiguration;
import com.jeseromero.core.model.configurations.MagnetLinkConfiguration;
import com.jeseromero.core.model.configurations.PirateBayConfiguration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author jrarbelo - Jes? Romero Arbelo
 * @version Revision 1.1
 * <br> Revision 1.1 -> Versi?n inicial.
 * @since Release 2017
 */
public class ConfigurationController {

    public Configuration getConfigurationByName(String name) {
	    for (Configuration configuration : allConfigurations()) {
		    if (configuration.getName().equals(name))
		    	return configuration;
	    }

        return null;
    }

    public Collection<Configuration> allConfigurations() {
		ArrayList<Configuration> configurations = new ArrayList<>();

		configurations.add(new MagnetLinkConfiguration());
		configurations.add(new PirateBayConfiguration());
		configurations.add(new LimeTorrentsConfiguration());

		return configurations;
	}
}
