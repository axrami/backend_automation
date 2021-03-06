package model;

import org.joda.time.DateTime;
import org.joda.time.Instant;

/**
 * Created by andrew on 6/9/15.
 */
public class LPMobileHttpResponse {
    private String requestType;
    private Integer responseCode;
    private String visit_id;
    private String httpResponse;
    private String url;
    private String continue_url;
    private String postBody;
    private Long latency;

    public LPMobileHttpResponse() {}

    public LPMobileHttpResponse(String url, int responseCode, String postBody, String httpResponse, String requestType) {
        this.url = url;
        this.responseCode = responseCode;
        this.postBody = postBody;
        this.httpResponse = httpResponse;
        this.requestType = requestType;
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

    public Integer getResponseCode() {
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

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Long getLatency() {
        return latency;
    }

    public void setLatency(Long latency) {
        this.latency = latency;
    }

    @Override
    public String toString() {
        return "requestType: " + requestType +
                "responseCode: " + responseCode +
                "visit_id: " + visit_id +
                "httpResponse: " + httpResponse +
                "url: " + url +
                "continue_url: " + continue_url +
                "postBody: " + postBody +
                "latency: " + latency;
    }
}
