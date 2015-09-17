package service;

import model.LPMobileHttpResponse;
import model.TestReporter;
import networking.VisitHandler;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.chat.ChatHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by andrew on 9/11/15.
 */
public class Generator {
    private AtomicInteger counter = new AtomicInteger(0);
    private List resultArray = new CopyOnWriteArrayList();
    public Logger logger = LoggerFactory.getLogger("Generator");
    private int requestedAmount;
    private static boolean stopRequested;
    private ScheduledExecutorService executor;

    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean isStopReqeusted() {
        return stopRequested;
    }

    public void beginVisits(int amount, long time) {
        long start = new Instant().getMillis();
        long end = start + time * 1000;
        this.requestedAmount = amount;
        createVisitExecutor(20);


    }

    public void beginChats(int amount, long time) {
        long start = new Instant().getMillis();
        long end = start + time * 1000; // assuming time is seconds convert to mills
        this.requestedAmount = amount;
        createChatExecutor(1);
        while (System.currentTimeMillis() < end) {}
        if (resultArray.size() < requestedAmount) {
            logger.error("Results less than requested amount");
            logger.error("Requested: " + requestedAmount + " Executed: " + resultArray.size());
            logger.error("May not be enough time");
        }
        requestStop();
        reportResults();
    }

    public void createVisitExecutor(int continueInvercal) {
        executor = Executors.newScheduledThreadPool(10);
        executor.scheduleAtFixedRate(visitRunnable, 0, continueInvercal, TimeUnit.SECONDS);
    }


    public void createChatExecutor(int messageInverval) {
        executor = Executors.newScheduledThreadPool(10);
        executor.scheduleAtFixedRate(chatRunnable, 0, messageInverval, TimeUnit.SECONDS);
    }

    public void reportResults() {
        TestReporter reporter = new TestReporter();
        reporter.createResults(resultArray);
    }

    Runnable visitRunnable = () -> {

    };

    Runnable chatRunnable = () -> {
        while (!isStopReqeusted() && counter.get() < requestedAmount) {
                counter.getAndIncrement();
                Session session = new Session();
                session.setConfig("staging", 1, false);
                VisitHandler visit = session.beginVisit();
                resultArray.add(visit.response);
                beginChat(session);
                logger.debug("**********WHILE LOOP IS RUNNING********");
        }
    };

    private void beginChat(Session session) {
        Executor chatExe = Executors.newSingleThreadExecutor();
        chatExe.execute(new Runnable() {
            @Override
            public void run() {
                ChatHandler chat = session.beginChat();
                while (!isStopReqeusted()) {
                    try {
                        logger.debug("Thread name: " + Thread.currentThread().getName());
                        resultArray.add(chat.sendLinePostRequest("Hello"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    resultArray.add(chat.sendLinePostRequest("end"));
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        });
    }


}
