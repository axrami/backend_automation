package service;

/**
 * Created by andrew on 8/27/15.
 */
public class LPMobileConfig {
    private String environment;
    private int apiVersion;

    public static boolean isDebug = true;

    public LPMobileConfig LPMobileConfig() {
        this.environment = "staging";
        this.apiVersion = 2;
        return this;
    }

    public LPMobileConfig LPMobileConfig(String environment, int apiVersion) {
        this.apiVersion = apiVersion;
        this.environment = environment;
        return this;
    }
}
