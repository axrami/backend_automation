package properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by andrew on 8/28/15.
 */
public class LPMobileConfig {
    private boolean isDebug;
    private String environment;
    private int apiVersion;
    private Logger logger = LoggerFactory.getLogger("LPMobileProperties");

    // Final base strings
    private final String visitProdDomain = "dispatch.look.io";
    private final String visitStagDomain = "dispatch.staging.look.io";
    private final String visitDevDomain = "dispatch.dev.liveperson.io";
    private final String chatProdDomain = "dispatch.look.io";
    private final String chatStagDomain = "dispatch.staging.look.io";
    private final String chatDevDomain = "dispatch.dev.liveperson.io";

    // Visit API launcher strings
    private String APP_LAUNCHER_URL_1 = "https://%s/api/v1/app/launch";
    private String APP_LAUNCHER_URL_2 = "https://%s/api/v2/app/launch";

    // Chat API base Strings
    private static final String BASECHATURL_1 = "https://%s/api/v1/chat/";
    private static final String BASECHATURL_2 = "https://%s/api/v2/chat/";

    // used to build domain stings
    private String chatDomain;
    private String visitDomain;
    private String baseChatURL;
    private String baseVisitURL;

    private static LPMobileConfig instance = null;

    // constructors
    protected LPMobileConfig() {
        this.environment = "staging";
        this.apiVersion = 1;
        this.isDebug = true;
        setBaseUrls();
    }

    public static LPMobileConfig getInstance() {
        if(instance == null) {
            instance = new LPMobileConfig();
        }
        return instance;
    }


    public void setBaseUrls() {
        if (apiVersion == 1) {
            baseChatURL = BASECHATURL_1;
            baseVisitURL = APP_LAUNCHER_URL_1;
            setDomains();
        } else if (apiVersion == 2) {
            baseChatURL = BASECHATURL_2;
            baseVisitURL = APP_LAUNCHER_URL_2;
            setDomains();
        } else {
            logger.error("<setChatDomain> Creating domain url failed API version may be invalid");
        }
    }

    private void setDomains() {
        switch (environment) {
            case "staging":
                chatDomain = String.format(baseChatURL, chatStagDomain);
                visitDomain = String.format(baseVisitURL, visitStagDomain);
                break;
            case "production":
                chatDomain = String.format(baseChatURL, chatProdDomain);
                visitDomain = String.format(baseVisitURL, visitProdDomain);
                break;
            case "dev":
                chatDomain = String.format(baseChatURL, chatDevDomain);
                visitDomain = String.format(baseVisitURL, visitDevDomain);
                break;
            default:
                chatDomain = String.format(baseChatURL, chatProdDomain);
                visitDomain = String.format(baseVisitURL, visitProdDomain);
        }
    }

    public String getVisitDomain() {
        return visitDomain;
    }

    public String getChatDomain() {
        return chatDomain;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }


}
