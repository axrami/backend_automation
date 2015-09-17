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
        Generator gen = new Generator();
        gen.beginChats(1 , 10);
    }
}
