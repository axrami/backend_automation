package json;


import model.LPMobileVisit;
import model.Skill;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.util.concurrent.ConcurrentHashMap;

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

//        Object obj = JSONValue.parse(jsonString);
//        JSONObject json = (JSONObject)obj;
        JSONObject json = (JSONObject)JSONValue.parse(jsonString);

        if (json.containsKey(F_VISIT_ID)) {
            visit.setVisitId(json.get(F_VISIT_ID).toString());
            System.out.println("<Visit_Id> " + visit.getVisitId());
        } else {
            System.out.println("<WARN>visit_id no reported" );
        }

        if (json.containsKey(F_CONTINUE_URL)) {
            visit.setContinueURL(json.get(F_CONTINUE_URL).toString());
            System.out.println("<Continue URL> " + visit.getContinueURL());
        } else {
            System.out.println("<WARN>continue_url no reported");
        }


        // skills
        if (json.containsKey(F_SKILLS)) {
            JSONObject accountSkillsJson = (JSONObject)json.get(F_SKILLS);
            ConcurrentHashMap<String, ConcurrentHashMap<String, Skill>> accountSkillsMap = visit.getSkills();
            JSONObject accountsJson = (JSONObject)accountSkillsJson.get(F_SKILLS_ACCOUNTS);

            System.out.println("<F_SKILLS_ACCOUNTS>" + accountsJson);

        } else {
            System.out.println("<WARN>skills not reported");
        }


    }
}


