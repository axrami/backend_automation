package model;

/**
 * Created by andrew on 6/9/15.
 */
public class LPMobileHttpResponse {
    private String url;
    private int responseCode;
    private String postBody;
    private String httpResponse;
    private String continue_url;
    private String visit_id;

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

    public String getContinue_url() {
        return continue_url;
    }

    public void setContinue_url(String continue_url) {
        this.continue_url = continue_url;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }
}
