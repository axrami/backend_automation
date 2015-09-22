import org.testng.annotations.Test;
import service.Generator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by andrew on 9/9/15.
 */
public class LoadTester {

    @Test
    public void testGen() {
        // begins 50 visits over 60 seconds sends continue ever 20 seconds
//        Generator visitGen = new Generator();
//        visitGen.beginVisits(50, 60, 20);
        // begins 10 chats over 60 seconds a line sent every 10 second
        Generator chatGen = new Generator();
        chatGen.beginChats(5, 30, 5);
    }
}
