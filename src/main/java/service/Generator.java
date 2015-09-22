package service;

import model.LPMobileHttpResponse;
import model.SetEnvironment;
import model.TestReporter;
import networking.VisitHandler;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.chat.ChatHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private Set<Callable<String>> callables = new HashSet<>();
    private GenConfig genConfig;


    public void beginVisits(int amount, long time) {
        beginVisits(amount, time, 30);
    }

    public void beginVisits(int amount, long time, int continueInterval) {
        long start = new Instant().getMillis();
        long end = start + time * 1000;
        this.continueInterval = continueInterval;
        this.requestedAmount = amount;
        createVisitSessions();
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
//        genConfig = new GenConfig(end, messageInterval); Used for second approach
        createChatSessions();
        while(end > System.currentTimeMillis()) {}
        chatStopRequested.set(true);
//        reportResults();
    }

    // So lame..
    private void createChatSessions() {
        while(counter.get() < requestedAmount) {
            counter.getAndIncrement();
            Session session = new Session();
            beginChat(session);
        }
    }

    // So lame..
    private void createVisitSessions() {
        while (counter.get() < requestedAmount) {
            counter.getAndIncrement();
            Session session = new Session();
            beginVisit(session);
        }
    }

    // keeps chats running for defined amount of time, sends lines according to messageInterval
    private void beginChat(Session session) {
        ScheduledExecutorService chatExecutor = Executors.newSingleThreadScheduledExecutor();
        chatExecutor.scheduleAtFixedRate(() -> {
            VisitHandler visit = session.beginVisit();
            resultArray.add(visit.response);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, messageInterval, TimeUnit.SECONDS);
    }

    // keeps visits running for defined amount of time, sends continues according to continueInterval
    private void beginVisit(Session session) {
        ScheduledExecutorService visitExecutor = Executors.newSingleThreadScheduledExecutor();
        visitExecutor.scheduleAtFixedRate(() -> {
            VisitHandler visit = session.beginVisit();
            resultArray.add(visit.response);
            while (!visitStopRequested.get()) {
                resultArray.add(visit.continueVisit());
            }
        }, 0, continueInterval, TimeUnit.SECONDS);

    }

    private void reportResults() {
        TestReporter reporter = new TestReporter();
        reporter.createResults(resultArray);
    }




    /*
     * All below needs further development should not be used
     */

    private void sessonExecutor() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(sessionRunnable);
    }

    private void createChatExecutor() {
        try {
            ScheduledExecutorService sessionexe = Executors.newScheduledThreadPool(3);
            Future<Set<Callable<String>>> future = sessionexe.submit(sessionCallable);
            executor = Executors.newScheduledThreadPool(10);
            executor.invokeAll(future.get());

        } catch (InterruptedException | ExecutionException e ) {
            e.printStackTrace();
        }
    }

    // runnable that creates sessions and adds to exsisting hashset
    Runnable sessionRunnable = () -> {
        while (counter.get() < requestedAmount) {
                counter.getAndIncrement();
                Session session = new Session();
                session.setConfig("staging", 1, false);
                callables.add(new ChatRun(session , genConfig));
        }
    };

    // callable that creats session and returns a hashset
    Callable<Set<Callable<String>>> sessionCallable = () -> {
        while(counter.get() < requestedAmount) {
            counter.getAndIncrement();
            Session session = new Session();
            session.setConfig("staging", 1, false);
            callables.add(new ChatRun(session, genConfig));
        }
        return callables;
    };


}
