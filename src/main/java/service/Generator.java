package service;

import model.LPMobileHttpResponse;
import model.TestReporter;
import networking.VisitHandler;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by andrew on 9/11/15.
 */
public class Generator {
    private List resultArray = new CopyOnWriteArrayList();
    public Logger logger = LoggerFactory.getLogger("Generator");
    private static boolean stopRequested;
    private ScheduledExecutorService executor;

    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean isStopReqeusted() {
        return stopRequested;
    }

    public void beginVisits(int amount, long time ) {
        long start = new Instant().getMillis();
        long end = start + time * 1000; // assuming time is seconds convert to mills
        long cycle = amount / time;
        createExecutor(1);
        while(System.currentTimeMillis() < end) {}
        logger.debug("***********Shutting down************");
        requestStop();
        reportResults();
    }

    public void createExecutor(int rate) {
        executor = Executors.newScheduledThreadPool(10);
        executor.scheduleAtFixedRate(visitRunable, 0, rate, TimeUnit.SECONDS);
    }

    public void reportResults() {
        TestReporter reporter = new TestReporter();
        reporter.createResults(resultArray);
    }

    Runnable visitRunable = () -> {
        while(!isStopReqeusted()) {
            Session session = new Session();
            session.setConfig("staging", 1, true);
            VisitHandler visit = session.beginVisit();
            LPMobileHttpResponse response = visit.response;
            resultArray.add(response);
        }
        executor.shutdown();
    };

}
