import model.LPMobileEnvironment;
import org.junit.Test;
import service.Session;

/**
 * Created by andrew on 7/23/15.
 */
public class SessionTest {
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

    @Test
    public void beginVisit() {
        Session session = new Session();
        session.beginVisit(createBaseEnv());
    }
}
