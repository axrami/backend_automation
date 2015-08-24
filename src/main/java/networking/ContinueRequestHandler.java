package networking;

import json.JsonGenerator;
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

/**
 * Created by andrew on 7/22/15.
 */
public class ContinueRequestHandler {

    public void launchContinue(LPMobileEnvironment env, LPMobileVisit visit, Visitor visitor) {
        try {
            LPMobileHttpResponse response = sendContinueRequest(visit, env, visitor);
            visit.addResponse(response);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public LPMobileHttpResponse sendContinueRequest(LPMobileVisit visit, LPMobileEnvironment env, Visitor visitor) throws Exception {
        String continueURL = visit.getContinueURL();
        String postBody = JsonGenerator.generateVisitRequest(env, visit.getVisitId(), visitor.getVisitorId(), null);

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(continueURL);
        httppost.addHeader(new BasicHeader("Content-type", "application/json"));
        httppost.setEntity(new StringEntity(postBody));
        HttpResponse httpResponse = httpClient.execute(httppost);

        LPMobileHttpResponse response = new LPMobileHttpResponse();
        response.setUrl(continueURL);
        response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
        response.setHttpResponse(httpResponse.toString());
        response.setPostBody(postBody);

        return response;

    }

}
