import org.testng.annotations.Test;
import service.Session;

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
