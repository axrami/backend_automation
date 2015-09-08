package model;

import j2html.tags.ContainerTag;
import json.JsonMarshaller;
import model.LPMobileHttpResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import static j2html.TagCreator.*;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by andrew on 9/4/15.
 */
public class TestReporter {
    private JSONObject resultJson = new JSONObject();
    private JsonMarshaller jsonMarshaller = new JsonMarshaller();
    private Logger logger = org.slf4j.LoggerFactory.getLogger("model.TestReporter");

    public void logResult(LPMobileHttpResponse result) {
        try {
            String resultString = jsonMarshaller.marshalObj(result, Class.forName("model.LPMobileHttpResponse"));
            resultJson.put("visit", resultString);
        } catch (ClassNotFoundException | JAXBException e ) {
            e.printStackTrace();
        }
    }

    public void parseArray(List list) {
        JSONArray obj = new JSONArray();
        try {
            for (int i = 0; i < list.size(); i++) {
                Object item = list.get(i);
                String resultString = jsonMarshaller.marshalObj(item, Class.forName("model.LPMobileHttpResponse"));
                obj.add(resultString);
            }
        } catch(ClassNotFoundException | JAXBException e ) {
            e.printStackTrace();
        }
        postResults(obj.toJSONString());
    }

    public void postResults(String postBody) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://localhost:4567/test-result");
            httpPost.addHeader(new BasicHeader("Content-type", "application/json"));
            httpPost.setEntity(new StringEntity(postBody));
            HttpResponse response = httpClient.execute(httpPost);
        } catch (IOException e ) {
            e.printStackTrace();
        }

    }

//    public String generateHTML(List list) {
//        String html = body().with(
//        ).render();
//        return html;
//    }
//
//    public ContainerTag htmlStuff(List list) {
//        for (int i = 0; i > list.size(); i++) {
//            Object obj = list.get(i);
//            LPMobileHttpResponse result = (LPMobileHttpResponse)obj;
//            div().with(
//                    p(result.getVisit_id())
//            );
//        }
//    }
//
//    public void createResults(List list) {
//        try {
//            PrintWriter writer = new PrintWriter("test.html", "UTF8");
//            writer.println(generateHTML());
//            writer.close();
//        } catch (FileNotFoundException | UnsupportedEncodingException e ) {
//            e.printStackTrace();
//        }
//    }
}
