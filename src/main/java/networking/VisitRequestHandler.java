package networking;

import json.JsonGenerator;
import json.JsonParser;
import model.LPMobileEnvironment;
import model.LPMobileHttpResponse;
import model.LPMobileVisit;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by andrew on 6/11/15.
 */
public class VisitRequestHandler {

    // break this up have jsonGen in another method
//    public static LPMobileHttpResponse sendVisitRequest(LPMobileEnvironment env, LPMobileVisit visit, String visitBaseURL, String visitorId) throws Exception {
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(visitBaseURL);
//
//        httppost.addHeader(new BasicHeader("Content-type", "application/json"));
//        httppost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities", "account-skills"));
//        if(visitorId != null) {
//            httppost.setHeader("Cookie", "visitor_id=" + visitorId);
//        }
//
//        String postBody = JsonGenerator.generateVisitRequest(env, (visit != null && visit.getVisitId() != null) ? visit.getVisitId() : null, visitorId, null);
//        System.out.println("<PostBody>" + postBody);
//
//        httppost.setEntity(new StringEntity(postBody));
//        HttpResponse httpResponse = httpclient.execute(httppost);
//
//        LPMobileHttpResponse response = new LPMobileHttpResponse();
//        response.setUrl(visitBaseURL);
//        response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
//
//        if (response.isSuccess()) {
//            parseResponseBody(httpResponse, httppost.getURI().toString(), visit);
//        } else {
//            System.out.println("failed launch code: " + response.getResponseCode());
//        }
//
//        return response;
//    }


//    public static void parseResponseBody(HttpResponse httpResponse, String uri, LPMobileVisit visit) throws Exception {
//        if (httpResponse.getEntity() instanceof BasicManagedEntity) {
//            BasicManagedEntity entity = (BasicManagedEntity)httpResponse.getEntity();
//            BufferedReader br = null;
//            StringBuilder inputStr = new StringBuilder(1000);
//            try {
//                br = new BufferedReader(new InputStreamReader(entity.getContent(),"utf8"), 8192);
//                String currentLine;
//                while ((currentLine = br.readLine()) != null) {
//                    inputStr.append(currentLine);
//                }
//            } catch (IOException e1) {
//                System.out.println(e1);
//            } finally {
//                if (br != null) {
//                    try {
//                        br.close();
//                    } catch (IOException e1) {
//                        System.out.println(e1);
//                    }
//                }
//            }
//
//            if (inputStr.length() > 0) {
//                JsonParser.parseVisitResponse(inputStr.toString(), visit);
//            }
//
//        }
//    }


}
