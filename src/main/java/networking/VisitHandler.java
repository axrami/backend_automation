package networking;


import json.JsonMarshaller;
import json.model.AppSettings;
import json.model.VisitIntroResponse;
import json.model.LPMobileEnvironment;
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

import javax.xml.bind.JAXBException;
import java.io.IOException;


/**
 * Created by andrew on 6/16/15.
 */
public class VisitHandler {
    private String APP_LAUNCHER_URL = "https://%s/api/v1/app/launch";
    private LPMobileEnvironment env;
    private VisitIntroResponse visitIntroResponse;
    private AppSettings appSettings;
    private Visitor visitor;
    private JsonMarshaller jsonMarshaller = new JsonMarshaller();
    private String visitBaseURL = String.format(APP_LAUNCHER_URL, LPMobileProperties.getDomain());
    public Logger logger = LoggerFactory.getLogger("VisitHandler");


    public VisitIntroResponse launch(LPMobileEnvironment env , AppSettings appSettings, Visitor visitor) {
        this.env = env;
        this.appSettings = appSettings;
        this.visitor = visitor;
        try {
            visitIntroResponse = sendVisitRequest();
            return visitIntroResponse;
        } catch (Exception t) {
            t.printStackTrace();
        }
        logger.error("<launch> failed to set visitIntroResponse");
        return null;
    }

    public VisitIntroResponse sendVisitRequest() throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(visitBaseURL);
        httppost.addHeader(new BasicHeader("Content-type", "application/json"));
        httppost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities", "account-skills"));
        String postBody = jsonMarshaller.marshalObj(appSettings, Class.forName("json.model.AppSettings"));
        httppost.setEntity(new StringEntity(postBody));
        HttpResponse httpResponse = httpclient.execute(httppost);
        LPMobileHttpResponse response = new LPMobileHttpResponse();
        response.setUrl(visitBaseURL);
        response.setPostBody(postBody);
        response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
        if (LPMobileProperties.isDebug){
            logger.debug("<sendVisitRequest> url " + visitBaseURL);
            logger.debug("<sendVisitRequest> postBody " + postBody);
            logger.debug("<sendVisitRequest> httpResponse " + httpResponse);
        }
        if (response.isSuccess()) {
            Object marshalledObj = jsonMarshaller.unmarshalJson(httpResponse, Class.forName("json.model.VisitIntroResponse"));
            if (marshalledObj instanceof VisitIntroResponse) {
                return visitIntroResponse = (VisitIntroResponse)marshalledObj;
            } else {
                logger.error("<sendVisitRequest> Server response isSuccess: failed to unmarshal");
            }
        } else {
            logger.error("<sendVisitRequest> Failed code: " + response.getResponseCode());
        }
        return null;
    }

    public void continueVisit() {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(visitIntroResponse.getContinue_url());
            httppost.addHeader(new BasicHeader("Content-type", "application/json"));
            httppost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities", "account-skills"));
            String postBody = jsonMarshaller.marshalObj(appSettings, Class.forName("json.model.AppSettings"));
            httppost.setEntity(new StringEntity(postBody));
            HttpResponse httpResponse = httpclient.execute(httppost);
            LPMobileHttpResponse response = new LPMobileHttpResponse();
            response.setUrl(visitIntroResponse.getContinue_url());
            response.setPostBody(postBody);
            response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
        } catch (IOException | JAXBException | ClassNotFoundException e ) {
            e.printStackTrace();
        }

    }

    public LPMobileHttpResponse retryLaunch(LPMobileEnvironment env, LPMobileVisit visit) {
        LPMobileHttpResponse retryResponse = new LPMobileHttpResponse();
        return retryResponse;
    }

}
