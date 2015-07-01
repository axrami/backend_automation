package properties;

/**
 * Created by andrew on 6/16/15.
 */
public class LPMobileProperties {
    private static final String visitProdDomain = "dispatch.look.io";
    private static final String visitStagDomain = "dispatch.staging.look.io";
    private static final String visitDevDomain = "dispatch.dev.liveperson.io";
    private static final String chatProdDomain = "dispatch.look.io";
    private static final String chatStagDomain = "dispatch.staging.look.io";
    private static final String chatDevDomain = "dispatch.dev.liveperson.io";

    public static String getVisitProdDomain() {
        return visitProdDomain;
    }

    public static String getVisitStagDomain() {
        return visitStagDomain;
    }

    public static String getVisitDevDomain() {
        return visitDevDomain;
    }

    public static String getChatProdDomain() {
        return chatProdDomain;
    }

    public static String getChatStagDomain() {
        return chatStagDomain;
    }

    public static String getChatDevDomain() {
        return chatDevDomain;
    }

}
