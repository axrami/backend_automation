package networking;


import json.JsonMarshaller;
import json.model.AppSettings;
import json.model.VisitIntroResponse;
import model.LPMobileHttpResponse;
import model.Visitor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import properties.LPMobileConfig;

import javax.xml.bind.JAXBException;
import java.io.IOException;


/**
 * Created by andrew on 6/16/15.
 */
public class VisitHandler {
    private VisitIntroResponse visitIntroResponse;
    private AppSettings appSettings;
    private Visitor visitor;
    private LPMobileConfig config = LPMobileConfig.getInstance();
    private JsonMarshaller jsonMarshaller = new JsonMarshaller();
    public Logger logger = LoggerFactory.getLogger("VisitHandler");
    public LPMobileHttpResponse response;

    public VisitIntroResponse launch(AppSettings appSettings, Visitor visitor) {
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
        HttpPost httppost = new HttpPost(config.getVisitDomain());
        httppost.addHeader(new BasicHeader("Content-type", "application/json"));
        httppost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities", "account-skills"));
        String postBody = jsonMarshaller.marshalObj(appSettings, Class.forName("json.model.AppSettings"));
        httppost.setEntity(new StringEntity(postBody));
        HttpResponse httpResponse = httpclient.execute(httppost);
        response = new LPMobileHttpResponse();
        response.setUrl(config.getVisitDomain());
        response.setPostBody(postBody);
        response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
        if (config.isDebug()){
            logger.debug("<sendVisitRequest> url " + config.getVisitDomain());
            logger.debug("<sendVisitRequest> postBody " + postBody);
            logger.debug("<sendVisitRequest> httpResponse " + httpResponse);
        }
        if (response.isSuccess()) {
            Object marshalledObj = jsonMarshaller.unmarshalJson(httpResponse, Class.forName("json.model.VisitIntroResponse"));
            if (marshalledObj instanceof VisitIntroResponse) {
                VisitIntroResponse visitIntroResponse = (VisitIntroResponse)marshalledObj;
                response.setContinue_url(visitIntroResponse.getContinue_url());
                response.setVisit_id(visitIntroResponse.getVisit_id());
                return visitIntroResponse;
            } else {
                logger.error("<sendVisitRequest> Server response isSuccess: failed to unmarshal");
            }
        } else {
            logger.error("<sendVisitRequest> Failed code: " + response.getResponseCode());
        }
        return null;
    }

    public LPMobileHttpResponse continueVisit() {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(visitIntroResponse.getContinue_url());
            httppost.addHeader(new BasicHeader("Content-type", "application/json"));
            httppost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities", "account-skills"));
            String postBody = jsonMarshaller.marshalObj(appSettings, Class.forName("json.model.AppSettings"));
            httppost.setEntity(new StringEntity(postBody));
            HttpResponse httpResponse = httpclient.execute(httppost);
            LPMobileHttpResponse response = new LPMobileHttpResponse();
            response.setContinue_url(visitIntroResponse.getContinue_url());
            response.setPostBody(postBody);
            response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
            response.setVisit_id(visitIntroResponse.getVisit_id());
            if (config.isDebug()) {
                logger.debug("<contineuVisit> ***************Continue RESPONSE*************");
                logger.debug("<contineuVisit> *** ContinueURL " + response.getContinue_url());
                logger.debug("<contineuVisit> *** ResponseCode " + response.getResponseCode());
                logger.debug("<contineuVisit> *** VisitID " + response.getVisit_id());

            }
            return response;
        } catch (IOException | JAXBException | ClassNotFoundException e ) {
            e.printStackTrace();
        }
        return null;
    }
}
