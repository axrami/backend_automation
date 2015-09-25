package model;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import j2html.tags.ContainerTag;
import json.JsonMarshaller;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import static j2html.TagCreator.*;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by andrew on 9/4/15.
 */
public class TestReporter {
    private JsonMarshaller jsonMarshaller = new JsonMarshaller();
    private Logger logger = org.slf4j.LoggerFactory.getLogger("model.TestReporter");
//    BasicAWSCredentials awsCredentials = new BasicAWSCredentials("AKIAJCAD6HSB5OYLZOTA" , "DfrdTSmMekbIquJO1hfzOvOBGj6CVZlxV49mn5Fs");

//    private void postToS3(String fileName) {
//        AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
//        File file = new File(fileName);
//        amazonS3.putObject(new PutObjectRequest("lpmobile-test-results", fileName, file));
//        file.delete();
//    }



    // Can be used to send post to another service
    public String parseArray(List list) {
        JSONArray obj = new JSONArray();
        try {
            for (int i = 0; i < list.size(); i++) {
                Object item = list.get(i);
                String resultString = jsonMarshaller.marshalObj(item, Class.forName("model.LPMobileHttpResponse"));
                obj.add(resultString);
            }
        } catch (ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
//        postResults(obj.toJSONString());
        return obj.toJSONString();
    }

    public void postResults(String postBody) {
        try {
            logger.debug("Attempting to post out results");
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://lpmobile-test-results.s3.amazonaws.com");
            httpPost.addHeader(new BasicHeader("Content-type", "application/json"));
            httpPost.setEntity(new StringEntity(postBody));
            HttpResponse response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // takes array of LPMobileHTTPResponse and gens html
    public String generateHTML(List list) {
        String html = html().with(
                head().with(link().withRel("stylesheet").withHref("css/test.css"))).with(
                body().with(
                        h1("Test Results"),
                        h3("Total Request: " + list.size()),
                        resultBuilder(list)
                )).render();
        return html;
    }

    public ContainerTag resultBuilder(List list) {
        ContainerTag tag = new ContainerTag("div");
        for (Object obj : list) {
            System.out.println("obj trying to report " + obj);
            System.out.println("list from result builder " + list);
            LPMobileHttpResponse result = (LPMobileHttpResponse) obj;
            tag.with(
                    ul().withClass("result").with(
                            li().withClass("request-type").with(
                                    p("Request-type: " + result.getRequestType())
                            ),
                            li().withClass("response-code").with(
                                    p("Response-code: " + result.getResponseCode().toString())
                            ),
                            li().withClass("visit-id").with(
                                    p("VisitId: " + result.getVisit_id())
                            ),
                            li().withClass("latency").with(
                                    p("Latency in Mill: " + result.getLatency())
                            ),
                            li().withClass("request-url").with(
                                    p("request-url: " + result.getUrl())
                            )
                    )
            );
        }
        return tag;
    }

    // Creates a file with results
    public void createResults(List list) {
        try {
            String fileName = new DateTime().toString();
            PrintWriter htmlwriter = new PrintWriter(fileName + ".html", "UTF8");
            htmlwriter.println(generateHTML(list));
            htmlwriter.close();
            PrintWriter jsonwriter = new PrintWriter(fileName + ".json", "UTF8");
            jsonwriter.println(parseArray(list));
            jsonwriter.close();
//            File result = new File(generateHTML(list));
//            postToS3(fileName + ".html");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
