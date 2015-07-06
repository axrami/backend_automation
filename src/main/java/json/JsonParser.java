package json;


import com.google.gson.Gson;
import model.LPMobileVisit;
import org.json.simple.JSONObject;

/**
 * Created by andrew on 6/9/15.
 */
public class JsonParser {
    private static final String F_VISIT_ID = "visit_id";
    private static final String F_VISITOR_ID = "visitor_id";
    private static final String F_NEXT_INTERVAL = "next_interval";
    private static final String F_CONTINUE_URL = "continue_url";
    private static final String F_SKILLS = "skills";
    private static final String F_SKILLS_ACCOUNTS = "accounts";
    private static final String F_DEFAULT = "default";

    private static final String F_BUTTON_TEXT = "button_text";
    private static final String F_BUTTON_TEXT_COLOR = "button_text_color";
    private static final String F_BUTTON_TINT = "button_tint";
    private static final String F_BUTTON_VISIBILITY = "button_visibility";
    private static final String F_ENABLED = "enabled";
    private static final String F_WELCOME_TEXT = "welcome_text";

    private static final String F_SSE_URL_NAME = "sse_url";
    private static final String F_TYPE_NAME = "type";
    private static final String F_POST_URL_NAME = "post_url";
    private static final String F_MEDIA_URL_NAME = "media_url";
    private static final String F_ENGAGEMENT_ID_NAME = "engagement_id";
    private static final String F_LOCALIZED_NAME = "localized_strings";
    private static final String F_BRANDING_HASH_NAME = "branding_md5";
    private static final String F_BRANDING_NAME = "branding";
    private static final String F_LOCALIZED_LANGUAGE_NAME = "langauge";
    private static final String F_LOCALIZED_STRINGS_NAME = "strings";
    private static final String F_MASK_NAME = "mask_cc";
    private static final String F_BUTTON = "button";
    private static final String F_POPUP_CHAT = "popup_chat";
    private static final String F_SSO_KEY = "ssoKey";


    public static void parseVisitResponse(String jsonString, LPMobileVisit visit) {
        Gson gson = new Gson();
        gson.toJson(jsonString);

        System.out.println(jsonString);
    }
}


