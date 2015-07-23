package service.chat;

import json.JsonGenerator;
import json.JsonParser;
import model.LPMobileChat;
import model.LPMobileEnvironment;
import model.LPMobileVisit;
import model.Visitor;
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
    public static String CHAT_BASE_URL = "https://%s/api/v2/chat/";

    public void createChatConnection(LPMobileEnvironment env, LPMobileVisit visit, Visitor visitor, LPMobileChat chat) {
        introChatResponse = sendIntroRequest(env, visit, visitor);
    }

    public IntroChatResponse sendIntroRequest(LPMobileEnvironment env, LPMobileVisit visit, Visitor visitor) {
        IntroChatResponse introChatResponse = null;

        try {
            String postbody = JsonGenerator.generateVisitRequest(env, visit.getVisitId(), visitor.getVisitorId(), null);
            visit.setChatBaseURL(String.format(CHAT_BASE_URL, LPMobileProperties.getChatStagDomain()));
            HttpResponse httpResponse = sendPostRequest(visit, postbody, null, visitor);

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200 ) {
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
                System.out.println("Chat Failed");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return introChatResponse;
    }

    public HttpResponse sendPostRequest(LPMobileVisit visit, String postBody, String secretToken, Visitor visitor) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        String url = visit.getChatBaseURL() + "intro/";
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(new BasicHeader("Content-type" , "application/json"));
        httpPost.addHeader(new BasicHeader("X-Liveperson-Capabilities", "account-skills"));
        httpPost.setEntity(new StringEntity(postBody, "UTF8"));

        HttpResponse response = httpClient.execute(httpPost);
        return response;

    }

    public StringBuffer getBodyContent(BasicManagedEntity e) throws IOException {
        InputStream is = e.getContent();
        int c;
        StringBuffer b = new StringBuffer();
        while ((c=is.read()) !=-1) {
            b.append((char)c);
        }
        return b;
    }



}
