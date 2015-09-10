import json.model.AppSettings;
import model.LPMobileHttpResponse;
import model.TestReporter;
import networking.VisitHandler;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.Session;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by andrew on 9/4/15.
 */
public class VisitTest extends LPTest {
    private Logger logger = org.slf4j.LoggerFactory.getLogger("SessionTest");
    private static TestReporter reporter = new TestReporter();
    private CopyOnWriteArrayList resultArray = new CopyOnWriteArrayList();

    @Test(dataProvider = "platformPicker", threadPoolSize = 1, invocationCount = 1, timeOut = 10000)
    public void beginVisit(AppSettings appSettings) {
        Session session = new Session(appSettings, null);
        setSessionConfig(session);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
        LPMobileHttpResponse result = visit.response;
        resultArray.add(result);
        logger.debug("START: " + result.getStart().getMillis() + " STOP: " + result.getStop().getMillis());
    }

    @AfterTest
    public void logResults() {
        reporter.createResults(resultArray);
    }
}
