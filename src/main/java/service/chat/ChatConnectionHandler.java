package service.chat;

import json.JsonMarshaller;
import json.JsonParser;
import json.model.AppSettings;
import json.model.VisitIntroResponse;
import model.LPMobileHttpResponse;
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
import properties.LPMobileConfig;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by andrew on 7/10/15.
 */
public class ChatConnectionHandler {
    public IntroChatResponse introChatResponse;
    private HttpsURLConnection sseClient;
    private String COOKIE_HEADER_NAME = "Cookie";
    private Logger logger = LoggerFactory.getLogger("ChatConnectionHandler");
    private LPMobileConfig config = LPMobileConfig.getInstance();
    private JsonMarshaller jsonMarshaller = new JsonMarshaller();

    public IntroChatResponse createChatConnection(AppSettings appSettings, VisitIntroResponse visitIntroResponse) {
        boolean success = false;
        introChatResponse = sendChatIntroRequest(appSettings, visitIntroResponse);
        success = openSseChatConnection();
        if (success = true) {
            chatConnected();
        }
        return introChatResponse;

    }

    public IntroChatResponse sendChatIntroRequest(AppSettings appSettings, VisitIntroResponse visitIntroResponse) {
        IntroChatResponse introChatResponse = null;
        try {
            String postBody = jsonMarshaller.marshalObj(appSettings, Class.forName("json.model.AppSettings"));
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

        } catch (IOException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }

        return introChatResponse;
    }

    public LPMobileHttpResponse postRequest(VisitIntroResponse visitIntroResponse, String postBody, IntroChatResponse chatIntro, String uriSuffix) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        String url = config.getChatDomain() + uriSuffix;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(new BasicHeader("Content-type", "application/json"));
        httpPost.addHeader(new BasicHeader("X-Liveperson-Capabilities", "account-skills"));
        httpPost.setEntity(new StringEntity(postBody, "UTF8"));
        if (chatIntro != null) {
            httpPost.addHeader(new BasicHeader("Cookie", chatIntro.getCookieHeader()));
        }
        HttpResponse response = httpClient.execute(httpPost);
        LPMobileHttpResponse lpmResponse = new LPMobileHttpResponse(url, response.getStatusLine().getStatusCode(), postBody, getResponseBody(response));
        if (config.isDebug()) {
            logger.debug("<sendPostRequest> " + url + " postBody " + postBody);
            logger.debug("<sendPostRequest> response " + response.getStatusLine());
        }
        return lpmResponse;

    }

    public HttpResponse sendPostRequest(VisitIntroResponse visitIntroResponse, String postBody, IntroChatResponse chatIntro, String uriSuffix) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        String url = config.getChatDomain() + uriSuffix;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(new BasicHeader("Content-type", "application/json"));
        httpPost.addHeader(new BasicHeader("X-Liveperson-Capabilities", "account-skills"));
        httpPost.setEntity(new StringEntity(postBody, "UTF8"));
        if (chatIntro != null) {
            httpPost.addHeader(new BasicHeader("Cookie", chatIntro.getCookieHeader()));
        }
        HttpResponse response = httpClient.execute(httpPost);
        logger.debug("<sendPostRequest> what is e? ");
//        LPMobileHttpResponse lpmResponse = new LPMobileHttpResponse(url, response.getStatusLine().getStatusCode(), postBody, getResponseBody(response));
        if (config.isDebug()) {
            logger.debug("<sendPostRequest> " + url + " postBody " + postBody);
            logger.debug("<sendPostRequest> response " + response.getStatusLine());
        }
        return response;

    }

    public String getResponseBody(HttpResponse response) {
        try {
            if (response.getEntity() instanceof BasicManagedEntity) {
                BasicManagedEntity entity = (BasicManagedEntity) response.getEntity();
                StringBuilder inputstr = new StringBuilder(1000);
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "utf8"), 8192);
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    inputstr.append(currentLine);
                }
                return inputstr.toString();
            }
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return null;
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
            if (config.isDebug()) {
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
