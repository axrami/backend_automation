package service;

import model.LPMobileHttpResponse;
import networking.VisitHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by andrew on 9/11/15.
 */
public class Generator {
//    private List resultArray = new CopyOnWriteArrayList();
//    private static boolean stopRequested;
//
//    private static synchronized void reqeustStop() {
//        stopRequested = true;
//    }
//
//    private static synchronized boolean isStopReqeusted() {
//        return stopRequested;
//    }
//
//
//
//    public List beginVisits(int amount) {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        ExecutorService es = Executors.newScheduledThreadPool(50);
//        es.execute(visitRunable);
//
//        return resultArray;
//    }
//
//    public List beginVisits(int visitsPerSecond) {
//
//    }
//
//    public void shutDownExe(ExecutorService executor) {
//        executor.shutdown();
//    }
//
//
//    public void createExecutor() {
//
//    }
//
//    Runnable visitRunable = () -> {
//        Session session = new Session();
//        session.setConfig("staging", 1, true);
//        VisitHandler visit = session.beginVisit();
//        LPMobileHttpResponse response = visit.response;
//        resultArray.add(response);
//    };

}
