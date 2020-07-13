package com.example.connect_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final Location TODO = null ;
    TextView textView; // 把視圖的元件宣告成全域變數
    String input;
    String result; // 儲存資料用的字串
    private TextView show_loc;
    private LocationManager mLocationManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("請輸入欲搜尋地區或路段");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                input = s;
                connect test_connect = new connect();
                result = test_connect.connect_run(input);
                // 當這個執行緒完全跑完後執行
                runOnUiThread(new Runnable() {
                    public void run() {
                        textView.setText(result); // 更改顯示文字
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 找到視圖的元件並連接
        textView = findViewById(R.id.textView);
        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        show_loc = (TextView) findViewById(R.id.text);
        btnStart.setOnClickListener(btnClickListener); //開始定位
        btnStop.setOnClickListener(btnClickListener); //結束定位按鈕

    }

    public Button.OnClickListener btnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn = (Button) view;
            if (btn.getId() == R.id.btnStart) {
                if (!gpsIsOpen())
                    return;
                Location mLocation = getLocation();
                if (mLocation != null)
                    show_loc.setText("維度:" + mLocation.getLatitude() + "\n經度:" + mLocation.getLongitude());
                else
                    show_loc.setText("獲取不到資料");
            } else if (btn.getId() == R.id.btnStop) {
                mLocationManager.removeUpdates(locationListener);
            }
        }
    };

    //判斷當前是否開啟GPS
    private boolean gpsIsOpen() {
        boolean bRet = true;
        LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "未開啟GPS", Toast.LENGTH_SHORT).show();
            bRet = false;
        } else {
            Toast.makeText(this, "GPS已開啟", Toast.LENGTH_SHORT).show();
        }
        return bRet;
    }

    private Location getLocation() {
//獲取位置管理服務
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//查詢服務資訊
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
        criteria.setAltitudeRequired(false); //海拔資訊：不需要
        criteria.setBearingRequired(false); //方位資訊: 不需要
        criteria.setCostAllowed(true);  //是否允許付費
        criteria.setPowerRequirement(Criteria.POWER_LOW); //耗電量: 低功耗
        String provider = mLocationManager.getBestProvider(criteria, true); //獲取GPS資訊

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return TODO;
        }
        Location location = mLocationManager.getLastKnownLocation(provider);
        mLocationManager.requestLocationUpdates(provider, 2000, 5, locationListener);
        return location;
    }
    private final LocationListener locationListener = new LocationListener()
    {
        public void onLocationChanged(Location location)
        {
// TODO Auto-generated method stub
            if(location != null)
                textView.setText("維度:"   +location.getLatitude()   +"\n經度:"
                        +location.getLongitude());
            else
                textView.setText("獲取不到資料" );
        }
        public void onProviderDisabled(String provider)
        {
// TODO Auto-generated method stub
        }
        public void onProviderEnabled(String provider)
        {
// TODO Auto-generated method stub
        }
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
// TODO Auto-generated method stub
        }
    };

}