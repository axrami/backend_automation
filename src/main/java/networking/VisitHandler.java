package networking;


import json.IntroMarshaller;
import json.JsonGenerator;
import json.model.Intro;
import model.LPMobileEnvironment;
import model.LPMobileHttpResponse;
import model.LPMobileVisit;
import model.Visitor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.LPMobileProperties;


/**
 * Created by andrew on 6/16/15.
 */
public class VisitHandler {
    LPMobileVisit visit = new LPMobileVisit();
    boolean agentAvailability = false;
    String visitBaseURL;
    String visitDomain;
    String APP_LAUNCHER_URL = "https://%s/api/v1/app/launch";
    public Logger logger = LoggerFactory.getLogger("VisitHandler");

    public LPMobileVisit getVisit() {
        return visit;
    }

    public void launch(LPMobileEnvironment env, Visitor visitor) {
        try {
            String visitDomain = LPMobileProperties.getVisitStagDomain();
            visitBaseURL = String.format(APP_LAUNCHER_URL, visitDomain);

            LPMobileHttpResponse response = sendVisitRequest(env, visit, visitBaseURL, visitor.getVisitorId());
            visit.addResponse(response);
//            if (response.isSuccess()) {
//            } else {
//                //TODO add retry
//            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public LPMobileHttpResponse sendVisitRequest(LPMobileEnvironment env, LPMobileVisit visit, String visitBaseURL, String visitorId) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(visitBaseURL);
        logger.debug("<sendVisitRequest> url " + visitBaseURL);
        httppost.addHeader(new BasicHeader("Content-type", "application/json"));
        httppost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities", "account-skills"));
        if (visitorId != null) {
            httppost.setHeader("Cookie", "visitor_id=" + visitorId);
        }

        String postBody = JsonGenerator.generateVisitRequest(env, (visit != null && visit.getVisitId() != null) ? visit.getVisitId() : null, visitorId, null);
        logger.debug("<sendVisitRequest> postBody " + postBody);

        httppost.setEntity(new StringEntity(postBody));
        HttpResponse httpResponse = httpclient.execute(httppost);
        logger.debug("<sendVisitRequest> httpResponse " + httpResponse);
        LPMobileHttpResponse response = new LPMobileHttpResponse();
        response.setUrl(visitBaseURL);
        response.setHttpResponse(httpResponse.toString());
        response.setPostBody(postBody);
        response.setResponseCode(httpResponse.getStatusLine().getStatusCode());

        if (response.isSuccess()) {
            IntroMarshaller introMarshaller = new IntroMarshaller();
            Object marshalledObj = introMarshaller.unmarshalJson(httpResponse, Class.forName("json.model.Intro"));
            if (marshalledObj instanceof Intro) {
                Intro intro = (Intro)marshalledObj;
                this.visit.setIntro(intro);
            } else {
                logger.error("<sendVisitRequest> Server response isSuccess: failed to unmarshall ");
            }
//            JsonParser.parseResponseBody(httpResponse, httppost.getURI().toString(), visit);
        } else {
            logger.error("<sendVisitRequest> Failed code: " + response.getResponseCode());
        }

        return response;
    }

}
