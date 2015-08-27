package model;

/**
 * Created by andrew on 6/9/15.
 */
public class LPMobileHttpResponse {
    private String url;
    private int responseCode;
    private String postBody;
    private String httpResponse;

    public LPMobileHttpResponse() {}

    public LPMobileHttpResponse(String url, int responseCode, String postBody, String httpResponse) {
        this.url = url;
        this.responseCode = responseCode;
        this.postBody = postBody;
        this.httpResponse = httpResponse;
    }

    public String getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(String httpResponse) {
        this.httpResponse = httpResponse;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return responseCode >= 200 && responseCode < 300;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}
