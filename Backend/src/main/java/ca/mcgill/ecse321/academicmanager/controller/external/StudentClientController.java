package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class StudentClientController {

    public static final String GET_URL = "https://employer-backend-8888.herokuapp.com/mainapp/1/getstudents";

    private StudentService studentService;
    /**
     * source: https://www.journaldev.com/7148/java-httpurlconnection-example-java-http-request-get-post
     * @author Bach Tran
     * @return a String in JSON format, or an empty String if request has an error.
     * */
    public String sendGETStudents() throws IOException {
        // prepare to connect
        URL getURL = new URL(GET_URL);
        HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        String responseString = "";
        System.out.println(responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            responseString = response.toString();
        }
        System.out.println(responseString + responseCode);
        return responseString;
    }

    @GetMapping(value = { "/students/sync", "/students/sync/" })
    public String getAllStudents() {
        String responseMessage = "Sync complete!";
        try {
            sendGETStudents();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            responseMessage = e.getMessage();
        }
        return responseMessage;
    }
}
