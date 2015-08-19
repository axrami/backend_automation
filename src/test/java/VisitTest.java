import json.IntroMarshaller;
import json.model.LPMobileEnvironment;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.Session;

import java.util.UUID;

/**
 * Created by andrew on 8/18/15.
 */
public class VisitTest {

    @Test
    public void createVisit() {
        Session session = new Session();
        session.beginVisit();

    }
}
