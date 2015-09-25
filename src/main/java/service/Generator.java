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
import java.util.*;
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
    private TestReporter reporter = new TestReporter();

    public void beginVisitRun(int amount, long time, int continueInterval) {
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

    public void beginChatRun(int amount, long time) {
        beginChatRun(amount, time, 1);
    }

    public void beginChatRun(int amount, long time, int messageInterval) {
        long start = new Instant().getMillis();
        long end = start + time * 1000; // assuming time is seconds convert to mills
        this.requestedAmount = amount;
        this.messageInterval = messageInterval > -1 ? messageInterval : 1;
//        genConfig = new GenConfig(end, messageInterval); Used for second approach
        createChatSessions();
        while(end > System.currentTimeMillis()) {}
        chatStopRequested.set(true);
        reportResults();
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

    private AtomicBoolean visitGeneratorStopRequest = new AtomicBoolean();

    public List startVisitGenerator(int interval, int time) {
        List results = visitGenerator(interval);
        long now = System.currentTimeMillis();
        long end = now + time * 1000;
        while(end > System.currentTimeMillis()) {}
        visitGeneratorStopRequest.getAndSet(true);
        return results;
    }


    // creates a visitor every interval over amount of time in mills
    public List visitGenerator(int interval) {
        ExecutorService visitExecutor = Executors.newFixedThreadPool(2);
        visitExecutor.submit(() -> {
            while (!visitGeneratorStopRequest.get()) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Session session = new Session();
                session.setConfig("staging", 1, true);
                VisitHandler visit = session.beginVisit();
                resultArray.add(visit.response);
            }
        });
        return resultArray;
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

    public void createChatExecutor() {
        this.requestedAmount = 10;
        try {
            ExecutorService sessionexe = Executors.newScheduledThreadPool(3);
            Future<Set<Callable<String>>> future = sessionexe.submit(sessionCallable);
//            sessionexe.execute(sessionRunnable);
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

    /*
    Trying something else experimental
     */


    public void generateVisits(int visits, long time) {
        long timeInMills = time * 1000; // assume time is seconds
        long intervalTimeout = timeInMills / visits;
        try {
            ExecutorService executor = Executors.newFixedThreadPool(5);
            List<Future<LPMobileHttpResponse>> responseList = new ArrayList<>();
            // adds callable to executor
            // Thread timeout to gen visits over period of time
            for (int i=0; i < visits; i++) {
                    responseList.add(executor.submit(new ChatRun()));
                    Thread.sleep(intervalTimeout);
            }

            // runs while responseList has content
            while(!responseList.isEmpty()) {
                Future<LPMobileHttpResponse> response = null;
                // checks if future is complete takes out of response list
                for(int idx=0; idx < responseList.size(); idx++) {
                    response = responseList.get(idx);
                    if (response.isDone()) {
                        responseList.remove(idx);
                        break;
                    }
                    response = null;
                }
                if (response == null) {
                } else {
                    // adds result to thread safe array
                    resultArray.add(response.get());
                }
            }
            executor.shutdown();
            reporter.createResults(resultArray);
        } catch ( InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


}
