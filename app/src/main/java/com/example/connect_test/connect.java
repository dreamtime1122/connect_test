package com.example.connect_test;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class connect {
    public static String result = null;
    public static List<String> s_result = new ArrayList<>();
    public static int floor = 1;

    public static void getNavi() { //連接資料庫取得運算後路徑結果
        Thread thread = new Thread(mutiThread);
        thread.start();

    }

    private static Runnable mutiThread = new Runnable() {
        public void run() {
            connect_php();

        }
    };

    private static void connect_php() {
        try {
            URL url = new URL("http://120.107.172.58/ICCC/astartry5.php");    // php的位置
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  // 對資料庫打開連結
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);  //Post方式不能缓存,需手动设置为false
            //  接收回傳訊息
            if (urlConnection.getResponseCode() == 200) {
                InputStream is = urlConnection.getInputStream();//從database 開啟 stream
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                is.close();
                result = builder.toString();
            }
            Log.d("phpastar", result);
            //  Close stream & disconnect
            urlConnection.disconnect();
        } catch (Exception e) {
            Log.e("log_tag", e.toString());
        }
    }

}





