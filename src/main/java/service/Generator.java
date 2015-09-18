package service;

import model.TestReporter;
import networking.VisitHandler;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.chat.ChatHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by andrew on 9/11/15.
 */
public class Generator {
    private AtomicInteger counter = new AtomicInteger(0);
    private AtomicBoolean chatStopRequested = new AtomicBoolean();
    private AtomicBoolean visitStopRequested = new AtomicBoolean();
    private List resultArray = new CopyOnWriteArrayList();
    public Logger logger = LoggerFactory.getLogger("Generator");
    private int requestedAmount;
    private ScheduledExecutorService executor;
    private int messageInterval;
    private int continueInterval;

    public void beginVisits(int amount, long time) {
        beginVisits(amount, time, 30);
    }

    public void beginVisits(int amount, long time, int continueInterval) {
        long start = new Instant().getMillis();
        long end = start + time * 1000;
        this.continueInterval = continueInterval;
        this.requestedAmount = amount;
        createVisitExecutor();
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        visitStopRequested.set(true);
        reportResults();
    }

    public void beginChats(int amount, long time) {
        beginChats(amount, time, 1);
    }

    public void beginChats(int amount, long time, int messageInterval) {
        long start = new Instant().getMillis();
        long end = start + time * 1000; // assuming time is seconds convert to mills
        this.requestedAmount = amount;
        this.messageInterval = messageInterval > -1 ? messageInterval : 1;
        createChatExecutor();
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chatStopRequested.set(true);
        reportResults();
    }

    private void createVisitExecutor() {
        executor = Executors.newScheduledThreadPool(10);
        executor.scheduleAtFixedRate(visitRunnable, 0, 1, TimeUnit.SECONDS);
    }


    private void createChatExecutor() {
        executor = Executors.newScheduledThreadPool(10);
        executor.scheduleAtFixedRate(chatRunnable, 0, 1, TimeUnit.SECONDS);
    }

    private void reportResults() {
        TestReporter reporter = new TestReporter();
        reporter.createResults(resultArray);
    }

    // runnable that creates the requested amount of visits
    Runnable visitRunnable = () -> {
        while (!visitStopRequested.get() && counter.get() < requestedAmount) {
            counter.getAndIncrement();
            Session session = new Session();
            session.setConfig("staging", 1,false);
            VisitHandler visit = session.beginVisit();
            resultArray.add(visit.response);
            beginVisit(session);
        }

    };

    // runnable that creates the requested amount of chats starts
    Runnable chatRunnable = () -> {
        while (!chatStopRequested.get() && counter.get() < requestedAmount) {
                counter.getAndIncrement();
                Session session = new Session();
                session.setConfig("staging", 1, false);
                VisitHandler visit = session.beginVisit();
                resultArray.add(visit.response);
                beginChat(session);
        }
    };

    // keeps chats running for defined amount of time, sends lines according to messageInterval
    private void beginChat(Session session) {
        ScheduledExecutorService chatExecutor = Executors.newSingleThreadScheduledExecutor();
        chatExecutor.scheduleAtFixedRate(() -> {
            ChatHandler chat = session.beginChat();
            while (!chatStopRequested.get()) {
                try {
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
        }, 0, messageInterval, TimeUnit.SECONDS);
    }

    // keeps visits running for defined amount of time, sends continues according to continueInterval
    private void beginVisit(Session session) {
        ScheduledExecutorService visitExecutor = Executors.newSingleThreadScheduledExecutor();
        visitExecutor.scheduleAtFixedRate(() -> {
            VisitHandler visit = session.getVisitHandler();
            while(!visitStopRequested.get()) {
                resultArray.add(visit.continueVisit());
            }
        }, 0, continueInterval, TimeUnit.SECONDS);

    }


}
