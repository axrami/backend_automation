import json.JsonMarshaller;
import json.model.AppSettings;
import model.SetEnvironment;
import model.TestReporter;
import networking.VisitHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import properties.LPMobileConfig;
import service.ChatRun;
import service.Generator;
import service.Session;
import service.chat.ChatHandler;
import java.io.IOException;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by andrew on 6/16/15.
 */
public class Main {

    public static void main(String args[]) {
        TestReporter test = new TestReporter();
        Generator generator = new Generator();
        generator.createChatExecutor();
    }
}




