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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 9/4/15.
 */
public class VisitTest extends LPTest {
    private Logger logger = org.slf4j.LoggerFactory.getLogger("SessionTest");
    private static TestReporter reporter = new TestReporter();
    private List resultArray = new CopyOnWriteArrayList();

    @Test(dataProvider = "platformPicker", threadPoolSize = 100, invocationCount = 100, timeOut = 10000)
    public void beginVisit(AppSettings appSettings) {
        Session session = new Session(appSettings, null);
        setSessionConfig(session);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
        LPMobileHttpResponse result = visit.response;
        System.out.println(result.getLatency());
        resultArray.add(result);
    }

//    @Test
//    public void testExe() {
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
//        Runnable task = () -> {
//            Session session = new Session();
//            setSessionConfig(session);
//            session.beginVisit();
//        };
//        executor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
//    }

    @AfterTest
    public void logResults() {
        reporter.createResults(resultArray);
    }

}
