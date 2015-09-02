package json.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by andrew on 8/12/15.
 */
@XmlRootElement
public class Feedback {
    String email;
    String message;

    public String getEmail() {
        return email;
    }

    public Feedback setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Feedback setMessage(String message) {
        this.message = message;
        return this;
    }
}
