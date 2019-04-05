package ca.mcgill.ecse321.academicmanager.controller.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ConcurrentModificationException;
import java.util.Stack;

/**
 * The Listener define a basic step-by-step procedure on how to request, receive, and interpret
 * JSON data in the external databases.
 * It records time of each transaction, also reports information on errors from a HTTP Request,
 * or a failure to interpret JSON data.
 * Each concrete Listener can only handle 1 HTTP Request and work on a specific number of entities only,
 * this is currently a problem and can be developed in future releases.
 * @author Bach Tran
 * @since Sprint 4
 */
abstract class Listener {
    protected Stack<ZonedDateTime> updateHistory = new Stack<>();

    /**
     * The main flow of a Listener.
     * This follows a 4-step procedures: send > interpret > persist > update (notify user)
     * @return a String containing a message indicating if this HTTP GET has succeed or failed.
     */
    protected String mainProcedure(String url) {
        String serverResponse = "";
        // Step 1: send HTTP GET
        try {
            serverResponse = sendGetRequest(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + " at " + ZonedDateTime.now());
            return "Request failed at " + ZonedDateTime.now()
                + '\n' + e.getMessage() ;
        }
        // Step 2: interpret the String data
        try {
            this.interpretRequest(serverResponse);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "Request failed at " + ZonedDateTime.now();
        }
        // Step 3: data persistence
        try {
            this.persist();
        } catch (ConcurrentModificationException e) {
            // RESOLVE CONCURRENCY PROBLEM!!! :D
            // This means the previous transaction is going on! This current transaction will be denied.
            System.out.println("Another transaction is going on, request delayed at " + ZonedDateTime.now());
            return "Request denied! Another transaction is going on.";
        }
        // Step 4: record the time of update, and return the message
        //return "Request completed at " + this.updateHistory.peek() + " updateHistory=" + this.updateHistory;
        return this.calculateTimeElapsed();
    }
    /**
     * The method binds to the HTTP Request.
     * It contains the instruction on how to react to the Request.
     * When user call the RESTful endpoints, this is the first method to be run.
     * Note: add tag @GetMapping in this method for each concrete class only!
     */
    protected abstract String trigger();
    /**
     * Low-level method to open a connection to the Student team's RESTful calls.
     * source: https://www.journaldev.com/7148/java-httpurlconnection-example-java-http-request-get-post
     * @author Bach Tran
     * @return a String in JSON format, or an empty String if request has an error.
     * */
    protected String sendGetRequest(String url) throws IOException, RuntimeException {
        // prepare to connect
        URL getURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        // if everything is ok, read the data from server as a String.
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new RuntimeException("Error " + responseCode + ", message from subsystem: " + connection.getResponseMessage());
        }
    }

    /**
     * Interprets raw JSON String to something meaningful.
     * Converts the JSON String to Java object.
     * Each subclass will have different strategies to interpret (read) the JSON string
     * from external subsystems.
     * @param jsonString the String return as the result of the RESTful call.
     */
    protected abstract void interpretRequest(String jsonString) throws RuntimeException;

    /**
     * A method contains the procedure on how to persist external database
     * to the internal database.
     */
    protected void persist() throws ConcurrentModificationException {
        handleDependencies();
        removeObsolete();
        postData();
        System.out.println("Persisted data to the database at " + ZonedDateTime.now());
    }
    /**
     * To create some entities, it is required to create their dependencies first,
     * otherwise, the internal Service class might fail to create objects.
     * Each concrete class has different dependencies and therefore different way to handle them.
     * Triggers this method first before posting the data.
     */
    protected abstract void handleDependencies();

    /**
     * Some data on the external systems might have been deleted, this method helps
     * to reflect those deletes on the internal data.
     * Triggers this method first before posting the data.
     */
    protected abstract void removeObsolete();

    /**
     * Injects records from the external systems to the internal database.
     * If data does not exist, create an object.
     * If data exists, update their attributes corresponding to the external sources.
     */
    protected abstract void postData();

    /**
     * Simply print out the update history
     * @return a String contain the update history.
     */
    @Override
    public String toString() {
        return "Update History: " + updateHistory.toString();
    }

    private String calculateTimeElapsed() {

        ZonedDateTime currentTime = ZonedDateTime.now();
        if (updateHistory.isEmpty()) {
            updateHistory.push(currentTime);
            return "Updated for the first time. ";
        }
        Duration duration = Duration.between(updateHistory.peek(), updateHistory.push(currentTime));
        if (duration.getSeconds() <= 1) {
            return "Updated less than a second ago. ";
        }
        String response = "Updated ";
        if (duration.toDays() > 0) {
            response += duration.toDays() + singularOrPlural("day", duration.toDays());
            duration = duration.minusDays(duration.toDays());
        }
        if (duration.toHours() > 0) {
            response += duration.toHours() + singularOrPlural("hour", duration.toHours());
            duration = duration.minusHours(duration.toHours());
        }
        if (duration.toMinutes() > 0) {
            response += duration.toMinutes() + singularOrPlural("minute", duration.toMinutes());
            duration = duration.minusMinutes(duration.toMinutes());
        }
        if (duration.getSeconds() > 0) {
            response += duration.getSeconds() + singularOrPlural("second", duration.getSeconds());
        }
        response += " ago. ";
        return  response;
    }

    /**
     * We respect the English grammar! :)
     * @param word the word
     * @param value the value to be decided to make plural or not
     * @return the result
     */
    private static String singularOrPlural(String word, long value) {
        return " " + ((value == 1) ? word : word + "s") + " ";
    }
}
