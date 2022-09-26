package com.api;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class GlobalConfigs {

    private static final EnvironmentVariables VARIABLES = SystemEnvironmentVariables.createEnvironmentVariables();

    public static String getApiBaseUrl() {
        return EnvironmentSpecificConfiguration.from(VARIABLES).getProperty("api.base.url");
    }

}
