package android.teaching.lostinbrittany.org.hellorestworld;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.teaching.lostinbrittany.org.hellorestworld.tools.NetworkHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName = (EditText) findViewById(R.id.editTextName);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Button b = (Button)findViewById(R.id.buttonSend);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = txtName.getText().toString();

                HelloAsyncTask asyncTask = new HelloAsyncTask();
                asyncTask.execute(name);

            }
        });
    }

    private class HelloAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            Log.d("HelloAsyncTask", "doInBackground begin");
            // SystemClock.sleep(5000);

            boolean networkAvailable = NetworkHelper.isInternetAvailable(getApplicationContext());
            Log.d("Available network?", Boolean.toString(networkAvailable));
            if (!networkAvailable) { return null; }

            String result = NetworkHelper.callHello(strings[0]);
            Log.d("HelloAsyncTask", "doInBackground end");
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            Intent helloIntent =
                    new Intent("org.lostinbrittany.android.teaching.helloworldwithintents.HELLO");
            helloIntent.putExtra("message", result);

            startActivity(helloIntent);
        }
    }
}


