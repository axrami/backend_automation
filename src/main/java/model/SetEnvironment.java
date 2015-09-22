package model;

import json.model.AppSettings;
import json.model.LPMobileEnvironment;
import service.Session;

/**
 * Created by andrew on 6/11/15.
 * Sets the environment to request from server
 */
public class SetEnvironment {
    public static LPMobileEnvironment createBaseEnv() {
        LPMobileEnvironment env = new LPMobileEnvironment();
        env.setAppID("com.liveperson.mobile.ecosmart-P36511428");
        env.setLanguage("en");
        env.setAccessibilityActive(false);
        env.setPlatform("Android");
        env.setBrandingHashStr("e3fa6750a0c2e557342831924e6d39d4");
        env.setSkillId("mobile");
        env.setLocalizedHashStr("38ceb8450c28f8adbe48ee71ae01e744");
        env.setDeviceID("286c5550ba06df78");
        env.setCurrentSkill("mobile");
        env.setCurrentAccount("P36511428");
        env.setTzOffset("-25200");
        env.setLocale("en_US");

        return env;
    }

    public static AppSettings createAppSettings() {
        AppSettings appSettings = new AppSettings();
        appSettings.setApp_id("P36511428").setLanguage("en");
        appSettings.setPlatform("Android");
        appSettings.setPlateform_release("0.1.0");
        appSettings.setDevice_id("286c5550ba06df78");
        appSettings.setApp_id("com.liveperson.mobile.ecosmart-P36511428");
        appSettings.setBranding_md5("e3fa6750a0c2e557342831924e6d39d4");
        appSettings.setRequest_visitor_id(true);
        return appSettings;
    }

    public static Session createSession() {
        Session session = new Session(createAppSettings() , null);
        session.setConfig("staging", 1, true);
        return session;
    }

}
