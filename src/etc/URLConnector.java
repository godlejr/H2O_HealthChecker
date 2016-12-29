package etc;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ㅇㅇ on 2016-12-20.
 */

public class URLConnector extends Thread {
    private String result, url, id, db = null;

    public URLConnector(String url) {
        this.url = url;
    }

    public URLConnector(String url, String db){
        this.db = db;
        this.url = url;
    }

    @Override
    public void run() {
        final String output = request(url);
        result = output;
    }

    public String getResult() {
        return result;
    }

    public String request(String urlStr) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                if(db == null)
                {
                    Log.e("dd", "dd");
                    conn.setRequestProperty("dbName", db);
                    conn.setRequestProperty("id", "ddd");

                }
                conn.connect();
                int resCode = conn.getResponseCode();

                if (resCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while (true) {
                        line = reader.readLine();
                        if (line == null) break;

                        output.append(line + "\n");
                    }

                    reader.close();
                    conn.disconnect();
                }
            }
        } catch (Exception e) {
            Log.e("연결 에러", e.toString());
        }

        Log.e("url", output.toString());

        return output.toString();
    }

}
