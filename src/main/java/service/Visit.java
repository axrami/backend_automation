package service;


import model.LPMobileEnvironment;
import model.LPMobileHttpResponse;
import model.LPMobileVisit;
import model.SetEnvironment;
import networking.VisitRequestHandler;
import properties.LPMobileProperties;
import service.chat.ChatConnectionHandler;


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
    private static int numOfContinues = 0;

    private LPMobileEnvironment env = SetEnvironment.createEnv();

    public void launch() {
        try {
            String visitDomain = LPMobileProperties.getVisitStagDomain();
            visitBaseURL = String.format(APP_LAUNCHER_URL, visitDomain);
            System.out.println(visitBaseURL);

//            visit.setVisitId(""); May not need
//            visit.setContinueURL("");

            LPMobileHttpResponse response = VisitRequestHandler.sendVisitRequest(env, visit, visitBaseURL, visitorId);
            if (response.isSuccess()) {
                System.out.println("<RESPONSE CODE> " + response.getResponseCode());
//                continueRequest(env, visit, visitorId);

            }
        } catch (Throwable t) {
            System.out.println(t);
        }

    }

    private void continueRequest(LPMobileEnvironment env, LPMobileVisit visit, String visitorId) {
        int nextInterval = visit.getNextInterval();
        beginChat(env, visit, visitorId);
        for (int i = 0; i < 1; i++) {
            try {
                Thread.sleep(nextInterval * 100);
                LPMobileHttpResponse response = VisitRequestHandler.sendVisitRequest(env, visit, visitBaseURL, visitorId);
                System.out.println("<CONTINUE_VISIT>" + visit.getVisitId());
                System.out.println("<CONTINUE_RESPONSE>" + response.getResponseCode());

            }catch(Throwable t) {
                System.out.println(t);
            }
        }

    }

    private void beginChat(LPMobileEnvironment env, LPMobileVisit visit, String visitorId) {
        ChatConnectionHandler chat = new ChatConnectionHandler();
        chat.creatChatConnection(env , visit.getVisitId(), visitorId);
    }
}
