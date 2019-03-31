package ecse321.mcgill.ca.academicmanager_android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.JsonHttpResponseHandler;
import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class MainActivity extends AppCompatActivity {

    private String error = null;
    String token;

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    public void listStudent(View v) {
        error = "";
        final TextView tv = (TextView) findViewById(R.id.error);
        final TextView displayresult = (TextView) findViewById(R.id.textViewStudent);
        final String mytext="";
        Intent intent = getIntent();
        token = intent.getStringExtra("token");


        TextView textView = (TextView) findViewById(R.id.text);

        displayresult.setText("response");

        HttpUtils.get("students/list", null, token, new JsonHttpResponseHandler() {
            private JSONArray response;
            @Override
            public void onStart() {
                displayresult.setText("onStart");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    String myoutput = response.toString();
                    //displayresult.setText(myoutput);
                    displayresult.setText("");

                    for (int i = 0; i < response.length(); i++) {

                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            String firstname = jsonobject.getString("firstName");
                            String lastname = jsonobject.getString("lastName");
                            String studentid = jsonobject.getString("studentID");
                            String studentproblematic = jsonobject.getString("studentProblematicStatus");
                            displayresult.append(studentid + "   ");
                            displayresult.append(firstname + "   " + lastname );
                            displayresult.append(" - is Problematic :   " + studentproblematic + "\n");



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                    }


                    //displayresult.setText("On  Success");
                    //refreshErrorMessage();

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }


        });
// ...


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        refreshErrorMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
