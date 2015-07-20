package service.chat;

import json.JsonGenerator;
import json.JsonParser;
import model.LPMobileEnvironment;
import networking.chat.IntroChatResponse;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import properties.LPMobileProperties;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by andrew on 7/10/15.
 */
public class ChatConnectionHandler {
    public IntroChatResponse introChatResponse;
    public HttpsURLConnection sseClient;

    public void creatChatConnection(LPMobileEnvironment env, String visitId, String visitorId) {
        boolean success = false;
        introChatResponse = sendIntroRequest(env, visitId, visitorId);

    }

    public IntroChatResponse sendIntroRequest(LPMobileEnvironment env, String visitId, String visitorId) {
        IntroChatResponse introChatResponse = null;
        try {
            String postBody = JsonGenerator.generateVisitReqeust(env, visitId, visitorId, null);
            String chatBaseURL = LPMobileProperties.getChatStagDomain();
            HttpResponse httpResponse = sendPostRequest(chatBaseURL, postBody, null, visitorId);

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                System.out.println("200!");
                if (httpResponse.getEntity() instanceof BasicManagedEntity) {
                    BasicManagedEntity e = (BasicManagedEntity)httpResponse.getEntity();
                    StringBuffer b = getBodyContent(e);
                    if (b.length() > 0) {
                        introChatResponse = JsonParser.parseIntroResponse(b.toString());
                        Header[] headers = httpResponse.getHeaders("set-cookie");
                        if (headers != null && headers.length > 0) {
                            introChatResponse.setCookieHeader(headers[0].getValue());
                        }
                    }
                }
            } else {
                System.out.println("damn.. response " + introChatResponse.toString());
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        return introChatResponse;
    }

    public static HttpResponse sendPostRequest(String chatBaseURL, String postBody, String secretToken, String visitorId) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        String url = chatBaseURL;
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader(new BasicHeader("Content-type", "application/json"));
        httppost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities", "account-skills"));
        httppost.setEntity(new StringEntity(postBody, "UTF8"));

        HttpResponse response = httpClient.execute(httppost);
        return response;
    }

    public static StringBuffer getBodyContent(BasicManagedEntity e) throws IOException {
        InputStream is = e.getContent();
        int c;
        StringBuffer b = new StringBuffer();
        while ((c=is.read())!=-1) {
            b.append((char)c);
        }
        return b;
    }
}
