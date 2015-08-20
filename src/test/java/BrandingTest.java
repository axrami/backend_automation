import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import model.LPMobileEnvironment;
import model.LPMobileVisit;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import service.Session;
import org.slf4j.Logger;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 8/7/15.
 */
public class BrandingTest {

    String originalBranding = "{\"engagement\":{\"system_message\":{\"border\":{\"color\":\"gray\",\"width\":\"2\"},\"size\":{\"width\":\"1\"},\"background\":{\"color\":\"#CC000000\",\"alpha\":\"-1\"},\"bubble_alignment\":\"left\",\"font\":{\"color\":\"white\",\"size\":\"16\"}},\"webview\":{\"header_bar\":{\"background\":{\"color\":\"red\"},\"font\":{\"color\":\"black\",\"size\":\"14.0\"}}},\"send_bar\":{\"background\":{\"color\":\"black\"},\"text_field\":{\"placeholder\":{\"font\":{\"color\":\"#ff808080\"}},\"font\":{\"color\":\"white\",\"size\":\"18\"}},\"send_button\":{\"background\":{\"color\":\"gray\"},\"font\":{\"color\":\"white\",\"size\":\"18\"}},\"plus_button\":{\"color\":\"white\"}},\"surveys\":{\"text_field\":{\"border\":{\"color\":\"gray\",\"width\":\"2\"},\"background\":{\"color\":\"white\"},\"font\":{\"color\":\"black\",\"size\":\"16.0\"}},\"stars\":{\"border\":{\"color\":\"gray\",\"width\":\"2\"},\"unselected\":{\"background\":{\"color\":\"gray\"}},\"selected\":{\"background\":{\"color\":\"green\"}},\"font\":{\"color\":\"red\",\"size\":\"14.0\"}},\"list\":{\"border\":{\"color\":\"gray\",\"width\":\"2\"},\"background\":{\"color\":\"white\"},\"font\":{\"color\":\"black\",\"size\":\"16.0\"}},\"page_control\":{\"color\":\"#FF33B5E5\"},\"survey_card\":{\"border\":{\"color\":\"gray\",\"width\":\"2\"},\"cancel_button\":{\"background\":{\"color\":\"gray\"},\"font\":{\"color\":\"white\",\"size\":\"16.0\"}},\"next_button\":{\"background\":{\"color\":\"gray\"},\"font\":{\"color\":\"white\",\"size\":\"16.0\"}},\"background\":{\"color\":\"#CC000000\"},\"subtitle\":{\"font\":{\"color\":\"white\",\"size\":\"14.0\"}},\"title\":{\"font\":{\"color\":\"white\",\"size\":\"18.0\"}}},\"validation_alert\":{\"background\":{\"color\":\"red\"},\"font\":{\"color\":\"white\",\"size\":\"14.0\"}}},\"branding_bar\":{\"background\":{\"color\":\"#661A1A1A\",\"alpha\":\"-1\",\"gradient\":{\"edge\":\"#661A1A1A\",\"middle\":\"#1A1A1A1A\"}},\"notifications\":{\"font\":{\"color\":\"#ffffff\",\"size\":\"14\"}}},\"loading_screen\":{\"image\":{\"spin\":\"1\",\"url\":\"\"},\"background\":{\"color\":\"#661A1A1A\"},\"subtitle\":{\"font\":{\"color\":\"#ffffff\",\"size\":\"16\"}},\"title\":{\"font\":{\"color\":\"#ffffff\",\"size\":\"18\"}}},\"photo_sharing\":{\"validation_alert\":{\"background\":{\"color\":\"red\"},\"font\":{\"color\":\"white\",\"size\":\"14.0\"}}},\"logo\":{\"image\":{\"url\":\"\"}},\"chat_background\":{\"background\":{\"color\":\"#661A1A1A\",\"alpha\":\"-1\",\"gradient\":{\"edge\":\"#661A1A1A\",\"middle\":\"#1A1A1A1A\"}}},\"visit\":{\"badge\":{\"background\":{\"color\":\"#ff0000\"},\"font\":{\"color\":\"#ffffff\",\"size\":\"12.0\"}},\"control_button\":{\"font\":{\"size\":\"20\"}}},\"end_button\":{\"background\":{\"color\":\"red\"},\"font\":{\"color\":\"#ffffff\",\"size\":\"14\"}},\"chat_bubbles\":{\"corner_radius\":\"16\",\"agent\":{\"border\":{\"color\":\"gray\",\"width\":\"2\"},\"size\":{\"width\":\"1.0\"},\"background\":{\"color\":\"#CC000000\",\"alpha\":\"-1\"},\"links\":{\"border\":{\"color\":\"#019fde\",\"width\":\"2\"},\"background\":{\"color\":\"#ffffff\"},\"font\":{\"color\":\"#555555\",\"size\":\"15.0\"}},\"font\":{\"color\":\"white\",\"size\":\"16\"}},\"visitor\":{\"border\":{\"color\":\"gray\",\"width\":\"2\"},\"size\":{\"width\":\"1.0\"},\"background\":{\"color\":\"#CC000000\",\"alpha\":\"-1\"},\"links\":{\"border\":{\"color\":\"#019fde\",\"width\":\"2\"},\"background\":{\"color\":\"#ffffff\"},\"font\":{\"color\":\"#555555\",\"size\":\"15.0\"}},\"font\":{\"color\":\"white\",\"size\":\"16\"}}},\"font\":{\"family\":\"\"},\"hide_bar\":{\"background\":{\"color\":\"#77000000\"},\"label\":{\"font\":{\"color\":\"gray\",\"size\":\"14\"}}}}}";
    Logger logger = LoggerFactory.getLogger("TestLogger");

    public static LPMobileEnvironment createBaseEnv() {
        LPMobileEnvironment env = new LPMobileEnvironment();
        env.setAppID("com.liveperson.mobile.ecosmart-P36511428");
        env.setLanguage("en");
        env.setAccessibilityActive(false);
        env.setPlatform("Android");
        env.setBrandingHashStr("e3fa6750a0c2e557342831924e6d39d4");
        env.setSkillId("mobile");
        env.setLocalizedHashStr("38ceb8450c28f8adbe48ee71ae01e744");
        env.setDeviceID(UUID.randomUUID().toString());
        env.setCurrentSkill("mobile");
        env.setCurrentAccount("P36511428");
        env.setTzOffset("-25200");
        env.setLocale("en_US");

        return env;
    }

    public boolean isValidJson(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException j) {
            return false;
        }
    }

    public boolean isBrandingEqual(String originalBranding , String responseBranding) {
        if (originalBranding.equals(responseBranding)) {
            logger.error("<***************Branding MATCHES********************>");
            return true;
        } else {
            logger.debug("<*****************BRANDING DOSE NOT MATCH*********************>");
            return false;
        }
    }

    public void beginCounter() {
        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            Session session = new Session();
            session.beginVisit(createBaseEnv());
            LPMobileVisit visit = session.getVisit();
            if (!isValidJson(visit.getBranding()) ) {
                logger.error("<BrandingJsonNotValid>");
            }
//            isBrandingEqual(originalBranding, visit.getBranding());

        };

        int intDelay = 1;
        int period = 5000;
        executor2.scheduleAtFixedRate(task, intDelay, period, TimeUnit.MILLISECONDS);
    }


    @Test
    public void beginVisit() {
        beginCounter();
//        logger.debug(originalBranding);
//        logger.debug(visit.getBranding());

    }

}
