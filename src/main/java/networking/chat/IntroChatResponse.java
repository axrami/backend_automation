package networking.chat;

/**
 * Created by andrew on 7/10/15.
 */
public class IntroChatResponse {
    String sseURL;
    String type;
    String postURL;
    String mediaURL;
    String engagementId;
    String cookieHeader;

    public IntroChatResponse() {}

    public IntroChatResponse(String sseURL, String type, String postURL, String mediaURL, String engagementId, String cookieHeader) {
        this.sseURL = sseURL;
        this.type = type;
        this.postURL = postURL;
        this.mediaURL = mediaURL;
        this.engagementId = engagementId;
        this.cookieHeader = cookieHeader;
    }

    public String getCookieHeader() {
        return cookieHeader;
    }

    public void setCookieHeader(String cookieHeader) {
        this.cookieHeader = cookieHeader;
    }

    public String getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(String engagementId) {
        this.engagementId = engagementId;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public String getPostURL() {
        return postURL;
    }

    public void setPostURL(String postURL) {
        this.postURL = postURL;
    }

    public String getSseURL() {
        return sseURL;
    }

    public void setSseURL(String sseURL) {
        this.sseURL = sseURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IntroChatResponse{" +
                "sseURL='" + sseURL + '\'' +
                ", type='" + type + '\'' +
                ", postURL='" + postURL + '\'' +
                ", mediaURL='" + mediaURL + '\'' +
                ", engagementId='" + engagementId + '\'' +
                ", cookieHeader=" + cookieHeader +
                '}';
    }
}
