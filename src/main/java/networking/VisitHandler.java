package networking;


import json.JsonMarshaller;
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

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by andrew on 6/16/15.
 */
public class VisitHandler {
    LPMobileVisit visit = new LPMobileVisit();
    boolean agentAvailability = false;
    String visitBaseURL;
    String visitDomain;
    String APP_LAUNCHER_URL = "https://%s/api/v1/app/launch";
    LPMobileEnvironment env;
    Intro intro;
    public Logger logger = LoggerFactory.getLogger("VisitHandler");

    public LPMobileVisit getVisit() {
        return visit;
    }

    public void launch(LPMobileEnvironment env, Visitor visitor) {
        this.env = env;
        try {
            String visitDomain = LPMobileProperties.getVisitStagDomain();
            visitBaseURL = String.format(APP_LAUNCHER_URL, visitDomain);

            LPMobileHttpResponse response = sendVisitRequest(env, visit, visitBaseURL, visitor.getVisitorId());
            visit.addResponse(response);
            if (response.isSuccess()) {
//                continueVisit();
                if(LPMobileProperties.isDebug) {
                    logger.debug("<launch> isSuccessful");
                }
            } else {
                logger.error("<launch> failed" );
            }
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    public LPMobileHttpResponse sendVisitRequest(LPMobileEnvironment env, LPMobileVisit visit, String visitBaseURL, String visitorId) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(visitBaseURL);
        httppost.addHeader(new BasicHeader("Content-type", "application/json"));
        httppost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities", "account-skills"));
        if (visitorId != null) {
            httppost.setHeader("Cookie", "visitor_id=" + visitorId);
        }

        String postBody = JsonGenerator.generateVisitRequest(env, (visit != null && visit.getVisitId() != null) ? visit.getVisitId() : null, visitorId, null);
        httppost.setEntity(new StringEntity(postBody));
        HttpResponse httpResponse = httpclient.execute(httppost);
        LPMobileHttpResponse response = new LPMobileHttpResponse();
        response.setUrl(visitBaseURL);
        response.setHttpResponse(httpResponse.toString());
        response.setPostBody(postBody);
        response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
        if (LPMobileProperties.isDebug){
            logger.debug("<sendVisitRequest> url " + visitBaseURL);
            logger.debug("<sendVisitRequest> postBody " + postBody);
            logger.debug("<sendVisitRequest> httpResponse " + httpResponse);
        }

        if (response.isSuccess()) {
            JsonMarshaller jsonMarshaller = new JsonMarshaller();
            Object marshalledObj = jsonMarshaller.unmarshalJson(httpResponse, Class.forName("json.model.Intro"));
            if (marshalledObj instanceof Intro) {
                this.intro = (Intro)marshalledObj;
            } else {
                logger.error("<sendVisitRequest> Server response isSuccess: failed to unmarshall ");
            }
//            JsonParser.parseResponseBody(httpResponse, httppost.getURI().toString(), visit);
        } else {
            logger.error("<sendVisitRequest> Failed code: " + response.getResponseCode());
        }

        return response;
    }

    public void continueVisit() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(intro.getContinue_url());
        httpPost.addHeader(new BasicHeader("Content-type" , "application/json"));
        httpPost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities", "account-skills"));
        if (visit.getVisitorId() != null) {
            httpPost.addHeader(new BasicHeader("Cookie" , "visitor_id=" + visit.getVisitorId()));
        }
        Runnable task = () -> {
            LPMobileHttpResponse response = sendContinueRequest(visit, httpClient, httpPost);
            if(LPMobileProperties.isDebug) {
                logger.debug("<continueVisit> response" +response.toString());
            }
        };

        executorService.scheduleAtFixedRate(task, intro.getNext_interval(), intro.getNext_interval(), TimeUnit.SECONDS);

    }

    public LPMobileHttpResponse sendContinueRequest(LPMobileVisit visit, HttpClient httpClient, HttpPost httpPost) {
        try {
            String postBody = JsonGenerator.generateVisitRequest(env, intro.getVisit_id(), visit.getVisitorId(), null);
            httpPost.setEntity(new StringEntity(postBody));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LPMobileHttpResponse response = new LPMobileHttpResponse();
            response.setUrl(intro.getContinue_url());
            response.setHttpResponse(httpResponse.toString());
            response.setPostBody(postBody);
            response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
