import model.TestReporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import service.Generator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by andrew on 9/9/15.
 */
public class LoadTester {

//    @Test
//    public void testGen() {
////         begins 50 visits over 60 seconds sends continue ever 20 seconds
//        Generator visitGen = new Generator();
//        visitGen.beginVisitRun(100, 10, 0);
////         begins 10 chats over 60 seconds a line sent every 10 second
//        Generator chatGen = new Generator();
//        chatGen.beginChatRun(100, 10, 2);
//    }
//
//    @Parameters({"Something"})
//    @Test
//    public void testTest(String something) {
//        System.out.println(something);
//    }

    @Test
    public void makeVisits() {
        TestReporter test = new TestReporter();
        Generator generator = new Generator();
//        test.createResults(generator.startVisitGenerator(100, 60));
        generator.generateVisits(5, 10);
    }
}
