package service.chat;

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

import java.io.IOException;

/**
 * Created by andrew on 7/29/15.
 */
public class ChatHelper {
//    public String CHAT_BASE_URL = "https://%s/api/v2/chat/";
//    public Logger logger = LoggerFactory.getLogger("ChatHelper");
//
//    public void SendEventPostReqeust() {
//        try {
//            HttpResponse response = sendPostReqeust();
//        } catch (IOException e) {
//            logger.error(e.toString());
//        }
//
//    }
//
//
//    public HttpResponse sendPostReqeust(String chatBaseUrl, String uriSuffix, String postBody, String secretToken, Visitor visitor, LPMobileVisit visit) {
//        HttpClient httpClient = new DefaultHttpClient();
//        String url = chatBaseUrl + uriSuffix;
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.addHeader(new BasicHeader("Content-type","application/json"));
//        httpPost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities" , "account-skills"));
//        httpPost.addHeader(new BasicHeader("Cookie" , secretToken));
//        httpPost.addHeader(new BasicHeader("Cookie", "visitor_id=" + visitor.getVisitorId()));
//        httpPost.setEntity(new StringEntity(postBody , "UTF8"));
//
//        HttpResponse response = httpClient.execute(httpPost);
//
//        return response;
//
//    }

}
