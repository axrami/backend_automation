package service.chat;

import json.JsonGenerator;
import json.JsonParser;
import json.model.Intro;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import properties.LPMobileProperties;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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

    public IntroChatResponse createChatConnection(LPMobileEnvironment env, LPMobileVisit visit, Visitor visitor, LPMobileChat chat) {
        boolean success = false;
        introChatResponse = sendIntroRequest(env, visit, visitor);
        success = openSseChatConnection();
        if (success = true) {
            chatConnected();
        }
        return introChatResponse;

    }

    public IntroChatResponse sendIntroRequest(LPMobileEnvironment env, LPMobileVisit visit, Visitor visitor) {
        IntroChatResponse introChatResponse = null;

        try {
            String postBody = JsonGenerator.generateVisitRequest(env, visit.getVisitId(), visitor.getVisitorId(), null);
            visit.setChatBaseURL(String.format(CHAT_BASE_URL, LPMobileProperties.getChatStagDomain()));
            HttpResponse httpResponse = sendPostRequest(visit, postBody, null, "intro/");

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200 ) {
                logger.debug("<IntroSuccessful>");
                if (httpResponse.getEntity() instanceof BasicManagedEntity) {
                    BasicManagedEntity e = (BasicManagedEntity)httpResponse.getEntity();
                    StringBuffer b = getBodyContent(e);
                    if (b.length() > 0) {
                        introChatResponse = JsonParser.parseIntroResponse(b.toString());
                        Header[] headers = httpResponse.getHeaders("set-cookie");
                        if (headers != null && headers.length > 0) {
                            introChatResponse.setCookieHeader(headers[0].getValue());
                            logger.debug("<sendIntroRequest> Headers " + headers[0].getValue());
                        }
                    }
                }
            } else {
                logger.error("<Intro Not Successful>");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return introChatResponse;
    }

    public HttpResponse sendPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro, String uriSuffix) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        String url = visit.getChatBaseURL() + uriSuffix;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(new BasicHeader("Content-type" , "application/json"));
        httpPost.addHeader(new BasicHeader("X-Liveperson-Capabilities", "account-skills"));
        httpPost.setEntity(new StringEntity(postBody, "UTF8"));
        if (intro != null) {
            httpPost.addHeader(new BasicHeader("Cookie", intro.getCookieHeader()));
        }

        HttpResponse response = httpClient.execute(httpPost);
        return response;

    }

    // Example: {"text":"hello"}
    public HttpResponse sendLinePostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return sendPostRequest(visit, postBody, intro, "line/" + intro.getEngagementId());
    }

    // Body not parsed
    public HttpResponse sendOutroPostReqeust(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return sendPostRequest(visit, postBody, intro, "outro/" + intro.getEngagementId());
    }

    // Example: {“prechat”:survey,"postchat":survey,"offline":survey}
    public HttpResponse sendSurveyPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException{
        return sendPostRequest(visit, postBody, intro, "survey/" + intro.getEngagementId());
    }

    // Example: {"email_address":"visitor@email.com","message":"feedback text"}
    public HttpResponse sendFeedbackPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return sendPostRequest(visit, postBody, intro, "feedback/" + intro.getEngagementId());
    }

    // Example: {"capabilities":["show_leavemessage","capability2",”capability3”]}
    public HttpResponse sendCapabilitiesPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return sendPostRequest(visit, postBody, intro, "capabilities/" + intro.getEngagementId());
    }

    // Example (to send chat history to email): {"email_addresses":["email1","email2"]}
    // Example (to send chat history to SSE channel): {}
    public HttpResponse sendChatHistoryPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return sendPostRequest(visit, postBody, intro, "chat_history/" + intro.getEngagementId());
    }

    // Example: {“variable”:value}
    public HttpResponse sendCustomVarsPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return sendPostRequest(visit, postBody, intro, "custom_vars/" + intro.getEngagementId());
    }

    // Example: {"action":"typing_start"}
    public HttpResponse sendAdvisoryPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return sendPostRequest(visit, postBody, intro, "advisory/" + intro.getEngagementId());
    }

    // Body: binary
    public HttpResponse sendScreenShotPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return sendPostRequest(visit, postBody, intro, "screenshot/" + intro.getEngagementId());
    }

    //Example: {"permission":"revoked", “asset”:”screenshare”} , {"permission":"granted", “asset”:”screenshare”}
    public HttpResponse sendPermissionPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return sendPostRequest(visit, postBody, intro, "permission/" + intro.getEngagementId());
    }



    public HttpResponse sendPost(LPMobileVisit visit, Visitor visitor, String postBody, IntroChatResponse intro, String suffix) throws IOException{
        HttpClient httpCLient = new DefaultHttpClient();
        String url = visit.getChatBaseURL() + suffix + intro.getEngagementId();
        logger.debug("<sendPost> postURL " + url);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(new BasicHeader("Content-type" , "application/json"));
        httpPost.addHeader(new BasicHeader("X-Liveperson-Capabilities", "account-skills"));
        if (!introChatResponse.getCookieHeader().isEmpty()) {
            httpPost.addHeader(new BasicHeader("Cookie" , introChatResponse.getCookieHeader()));
        }
        httpPost.setEntity(new StringEntity(postBody, "UTF8"));
        HttpResponse response = httpCLient.execute(httpPost);
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
            logger.debug("<openSseChatConnection> sseURL " + sseUrl);
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
        logger.debug("<chatConnected>");
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
