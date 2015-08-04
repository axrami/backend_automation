package json;


import model.LPMobileVisit;
import model.Skill;
import networking.chat.IntroChatResponse;
import org.apache.http.HttpResponse;
import org.apache.http.conn.BasicManagedEntity;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

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
    public static Logger logger = LoggerFactory.getLogger("JsonParser");

// divide this from a json gen and env setter
    public static void parseVisitResponse(String jsonString, LPMobileVisit visit) {
        int number = 1;
        JSONObject json = (JSONObject) JSONValue.parse("1");
        new HashMap<String, Object>();
        visit.setResponse(json.toString());
        logger.debug("<jsonString> " + jsonString);

        if (json.containsKey(F_VISIT_ID)) {
            visit.setVisitId(json.get(F_VISIT_ID).toString());
        } else {
            logger.debug("<parseVisitResponse> visit_id not reported");
        }

        if (json.containsKey(F_VISITOR_ID)) {
            visit.setVisitorId(F_VISITOR_ID);
        }


        if (json.containsKey(F_CONTINUE_URL)) {
            visit.setContinueURL(json.get(F_CONTINUE_URL).toString());
        } else {
            logger.debug("<parseVisitResponse> continue_)url not reported");
        }

        // skills
        if (json.containsKey(F_SKILLS)) {
            JSONObject accountSkillsJson = (JSONObject) json.get(F_SKILLS);
            HashMap<String, HashMap<String, Skill>> accountSkillsMap = visit.getSkills();
            JSONObject accountsJson = (JSONObject) accountSkillsJson.get(F_SKILLS_ACCOUNTS);
            Iterator accountsKeys = accountsJson.keySet().iterator();

            while (accountsKeys.hasNext()) {
                String account = (String) accountsKeys.next();
                if (account != null) {
                    HashMap<String, Skill> skillsMap = accountSkillsMap.get(account);
                    if (skillsMap == null) {
                        skillsMap = new HashMap<String, Skill>();
                    }

                    JSONObject skillsJson = (JSONObject) accountsJson.get(account);
                    Iterator skillsKeys = skillsJson.keySet().iterator();

                    while (skillsKeys.hasNext()) {
                        String skillName = (String) skillsKeys.next();

                        if (skillName != null) {
                            JSONObject skillJson = (JSONObject) skillsJson.get(skillName);
                            boolean isEnabled = false;
                            boolean isDefault = false;

                            if (skillJson.containsKey(F_ENABLED)) {
                                isEnabled = (Boolean) skillJson.get(F_ENABLED);
                            }

                            if (skillJson.containsKey(F_DEFAULT)) {
                                isDefault = (Boolean) skillJson.get(F_DEFAULT);
                            }

                            Skill newSkill = new Skill(isDefault, isEnabled);
                            Skill oldSkill = skillsMap.putIfAbsent(skillName, newSkill);

                            if (oldSkill != null && !newSkill.equals(oldSkill)) {
                                skillsMap.replace(skillName, newSkill);
                            }
                        }
                    }
                    accountSkillsMap.put(account, skillsMap);

                }
            }
            visit.setSkills(accountSkillsMap);

        } else {
            logger.debug("<parseVisitResponse> skills not reported");
        }

        if (json.containsKey(F_BRANDING_HASH_NAME)) {
            visit.setBranding_mp5(json.get(F_BRANDING_HASH_NAME).toString());
        } else {
        }

        if (json.containsKey(F_NEXT_INTERVAL)) {
            visit.setNextInterval(json.get(F_NEXT_INTERVAL).hashCode());
        } else {
        }
    }

    public static IntroChatResponse parseIntroResponse(String jsonString) {
        JSONObject json = (JSONObject) JSONValue.parse(jsonString);
        IntroChatResponse introChatResponse = new IntroChatResponse();

        if (json.containsKey(F_SSE_URL_NAME)) {
            introChatResponse.setSseURL(json.get(F_SSE_URL_NAME).toString());
        }
        if (json.containsKey(F_TYPE_NAME)) {
            introChatResponse.setType(json.get(F_TYPE_NAME).toString());
        }
        if (json.containsKey(F_POST_URL_NAME)) {
            introChatResponse.setPostURL(json.get(F_POST_URL_NAME).toString());
        }
        if (json.containsKey(F_MEDIA_URL_NAME)) {
            introChatResponse.setMediaURL(json.get(F_MEDIA_URL_NAME).toString());
        }
        if (json.containsKey(F_SSE_URL_NAME)) {
            introChatResponse.setSseURL(json.get(F_SSE_URL_NAME).toString());
        }
        if (json.containsKey(F_ENGAGEMENT_ID_NAME)) {
            introChatResponse.setEngagementId(json.get(F_ENGAGEMENT_ID_NAME).toString());
        }


        return introChatResponse;
    }

    public static void parseResponseBody(HttpResponse httpResponse, String uri, LPMobileVisit visit) {
        if (httpResponse.getEntity() instanceof BasicManagedEntity) {
            BasicManagedEntity entity = (BasicManagedEntity)httpResponse.getEntity();
            BufferedReader br = null;
            StringBuilder inputStr = new StringBuilder(1000);
            try {
                br = new BufferedReader(new InputStreamReader(entity.getContent(),"utf8"), 8192);
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    inputStr.append(currentLine);
                }
            } catch (IOException e1) {
                logger.debug(e1.toString());
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e1) {
                        logger.debug(e1.toString());
                    }
                }
            }

            if (inputStr.length() > 0) {
                JsonParser.parseVisitResponse(inputStr.toString(), visit);
            }

        }
    }


}


