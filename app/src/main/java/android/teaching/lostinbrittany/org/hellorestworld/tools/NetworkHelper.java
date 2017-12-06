package android.teaching.lostinbrittany.org.hellorestworld.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NetworkHelper {

    public static final String BASE_URL = "http://lostinbrittany-chat-server.cleverapps.io";
    public static final String HELLO_SERVICE = "/hello";

    public static boolean isInternetAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return null != activeNetwork && activeNetwork.isConnectedOrConnecting();
        } catch (Exception ex) {
            Log.d("NetworkHelper", "Error in checking internet");
            return false;
        }
    }

    // Reads an InputStream and converts it to a String.
    private static String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        int ch;
        StringBuffer sb = new StringBuffer();
        /*
        while ((ch = stream.read()) != -1) {
            sb.append((char) ch);
        }
        Log.d("Read Response", sb.toString());
        */
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");

        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        Log.d("Read Response", "Begin");
        if (sb.length() > 0) {
            return sb.toString();
        }
        return null;
    }


    public static String callHello(String name) {
        try {
            URL url = new URL(BASE_URL+HELLO_SERVICE+"/"+name);
            Log.d("NetworkHelper", "Connecting to: " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            Log.d("NetworkHelper", "The response code is: " + responseCode);

            if (responseCode >= 400) {
                return readIt(conn.getErrorStream());
            }
            return readIt(conn.getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
