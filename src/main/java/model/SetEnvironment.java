package model;

/**
 * Created by andrew on 6/11/15.
 */
public class SetEnvironment {
    public static LPMobileEnvironment createEnv() {
        LPMobileEnvironment env = new LPMobileEnvironment();
        env.setAppID("88310325");
        env.setLanguage("en");
        env.setAccessibilityActive(false);
        env.setPlatform("android");
        env.setBrandingHashStr("e3fa6750a0c2e557342831924e6d39d4");



        return env;
    }

}
