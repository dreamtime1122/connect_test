package com.example.connect_test;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class connect{

    public static String result=null;
    public String input;

        public String connect_run(String s) {
            input=s;
            Thread thread=new Thread(mutiThread);
            thread.start(); // 開始執行
            return result;
        }


// 建立一個執行緒執行的事件取得網路資料
// Android 有規定，連線網際網路的動作都不能再主線程做執行
// 畢竟如果使用者連上網路結果等太久整個系統流程就卡死了
private Runnable mutiThread=new Runnable(){
    public void run()
            {
            try{
                String data="input="+URLEncoder.encode(input+"","UTF-8");
                URL url=new URL("http://106.107.241.115/yingli/test.php");
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                OutputStream out=connection.getOutputStream();
                out.write(data.getBytes());
                out.flush();

                int responseCode=
                connection.getResponseCode();
                // 建立取得回應的物件
                if(responseCode==
                HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream=
                    connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader=new BufferedReader(new InputStreamReader(inputStream,"utf-8"),8);
                    // 讀取輸入串流的資料
                    String box=""; // 宣告存放用字串
                    String line=null; // 宣告讀取用的字串
                    while((line=bufReader.readLine())!=null){
                        box+=line+"\n";
                        // 每當讀取出一列，就加到存放字串後面
                    }
                    inputStream.close(); // 關閉輸入串流
                    result=box; // 把存放用字串放到全域變數
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理
                out.close();

            }
            catch(Exception e){
                result=e.toString(); // 如果出事，回傳錯誤訊息
            }


            }
        };
        }
