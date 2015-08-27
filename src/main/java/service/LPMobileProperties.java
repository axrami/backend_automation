package service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by andrew on 6/16/15.
 */
public class LPMobileProperties {
    private boolean isDebug;
    private String environment;
    private int apiVersion;
    public Logger logger = LoggerFactory.getLogger("LPMobileProperties");

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


    public LPMobileProperties() {
        this.environment = "staging";
        this.isDebug = true;
        this.apiVersion = 2;
        setBaseUrls();
    }

    public LPMobileProperties(String environment, int apiVersion, boolean isDebug) {
        this.environment = environment;
        this.apiVersion = apiVersion;
        this.isDebug = isDebug;
        setBaseUrls();
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

    public void setDomains() {
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
                chatDomain = String.format(baseChatURL, chatStagDomain);
                visitDomain = String.format(baseVisitURL, visitStagDomain);
        }
    }


}
