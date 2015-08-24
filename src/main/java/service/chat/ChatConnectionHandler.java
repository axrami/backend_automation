package service.chat;

import json.JsonMarshaller;
import json.JsonGenerator;
import json.JsonParser;
import json.model.VisitIntroResponse;
import model.LPMobileChat;
import json.model.LPMobileEnvironment;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.LPMobileProperties;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by andrew on 7/10/15.
 */
public class ChatConnectionHandler {
    public IntroChatResponse introChatResponse;
    public static String CHAT_BASE_URL = "https://%s/api/v2/chat/";
    public HttpsURLConnection sseClient;
    public String COOKIE_HEADER_NAME = "Cookie";
    public Logger logger = LoggerFactory.getLogger("ChatConnectionHandler");
    JsonMarshaller marshaller = new JsonMarshaller();

    public IntroChatResponse createChatConnection(LPMobileEnvironment env, VisitIntroResponse visitIntroResponse, Visitor visitor) {
        boolean success = false;
        introChatResponse = sendChatIntroRequest(env, visitIntroResponse, visitor);
        success = openSseChatConnection();
        if (success = true) {
            chatConnected();
        }
        return introChatResponse;

    }

    public IntroChatResponse sendChatIntroRequest(LPMobileEnvironment env, VisitIntroResponse visitIntroResponse, Visitor visitor) {
        IntroChatResponse introChatResponse = null;

        try {
            String postBody = JsonGenerator.generateVisitRequest(env, visitIntroResponse.getVisit_id(), visitor.getVisitorId(), null);
            HttpResponse httpResponse = sendPostRequest(visitIntroResponse, postBody, null, "intro/");
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200 ) {
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
                logger.error("<VisitIntroResponse Not Successful>");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return introChatResponse;
    }

    public HttpResponse sendPostRequest(VisitIntroResponse visitIntroResponse, String postBody, IntroChatResponse chatIntro, String uriSuffix) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        String url = String.format(CHAT_BASE_URL, LPMobileProperties.getDomain()) + uriSuffix;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(new BasicHeader("Content-type", "application/json"));
        httpPost.addHeader(new BasicHeader("X-Liveperson-Capabilities", "account-skills"));
        httpPost.setEntity(new StringEntity(postBody, "UTF8"));
        if (chatIntro != null) {
            httpPost.addHeader(new BasicHeader("Cookie", chatIntro.getCookieHeader()));
        }
        HttpResponse response = httpClient.execute(httpPost);
        if (LPMobileProperties.isDebug) {
            logger.debug("<sendPostRequest> " + url + " postBody " + postBody);
            logger.debug("<sendPostRequest> response " + response.getStatusLine());
        }
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

    public boolean openSseChatConnection() {
        try {
            if (introChatResponse == null) {
                return false;
            }
            String sseUrl = introChatResponse.getSseURL() + introChatResponse.getEngagementId();
            if (LPMobileProperties.isDebug) {
                logger.debug("<openSseChatConnection> sseURL " + sseUrl);
            }
            try {
                URL url = new URL(sseUrl);
                sseClient = (HttpsURLConnection) url.openConnection();
                sseClient.setRequestProperty("Last-Event-Id" , introChatResponse.getLastEventId() );

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void chatConnected() {
        sendSSEPostRequest();

    }

    public boolean sendSSEPostRequest() {
        boolean success = false;
        DataOutputStream outputStream = null;

        try {
            if (sseClient != null) {
                sseClient.setRequestMethod("POST");
                String cookieHeader = introChatResponse.getCookieHeader();
                if(cookieHeader != null && !cookieHeader.isEmpty()) {
                    sseClient.setRequestProperty(COOKIE_HEADER_NAME, cookieHeader);
                }

                sseClient.setUseCaches(false);
                sseClient.setDoInput(true);
                sseClient.setDoOutput(true);

                outputStream = new DataOutputStream(sseClient.getOutputStream());
                outputStream.writeBytes("");
                outputStream.flush();
                success = true;
            }
        } catch (ProtocolException e ) {
            e.printStackTrace();
            success = false;
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }


}
