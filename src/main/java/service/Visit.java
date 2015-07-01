package service;


import model.LPMobileEnvironment;
import model.LPMobileHttpResponse;
import model.LPMobileVisit;
import model.SetEnvironment;
import networking.VisitRequestHandler;
import properties.LPMobileProperties;


/**
 * Created by andrew on 6/16/15.
 */
public class Visit {
    private static LPMobileVisit visit = new LPMobileVisit();
    private static boolean agentAvailability = false;
    private static Visit currentVisitService = null;
    private static String visitorId = "12345678";
    private static String visitBaseURL;
    private static String visitDomain;
    private static String APP_LAUNCHER_URL = "https://%s/api/v1/app/launch";

    private static LPMobileEnvironment env = SetEnvironment.createEnv();

    public static void launch() {
        try {
            String visitDomain = LPMobileProperties.getVisitStagDomain();
            visitBaseURL = String.format(APP_LAUNCHER_URL, visitDomain);
            System.out.println(visitBaseURL);

            visit.setVisitId("");
            visit.setContinueURL("");
            visit.forgetSkills();



            LPMobileHttpResponse response = VisitRequestHandler.sendVisitRequest(env, visit, visitBaseURL, visitorId);
            System.out.println(response);
//            if (response.isSuccess()) {
//
//            }
        } catch (Throwable t) {
            System.out.println(t);
        }

    }
}
