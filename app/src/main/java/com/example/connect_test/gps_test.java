/*

package com.example.connect_test;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class gps_test {
    private Location mLocation;
    private LocationManager mLocationManager;
    public static Toast toast;
*/
/* Called when the activity is first created. *//*

*/
/*
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);
        textView = (TextView)findViewById(R.id.text);
        btnStart.setOnClickListener(btnClickListener); //開始定位
        btnStop.setOnClickListener(btnClickListener); //結束定位按鈕
    }
    //gpsIsOpen是自己寫的檢視當前GPS是否開啟
    //getLocation 是自己寫的一個獲取定位資訊的方法
    //mLocationManager.removeUpdates()是停止當前的GPS位置監聽
    public Button.OnClickListener btnClickListener = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            Button btn = (Button)v;
            if(btn.getId() == R.id.btnStart)
            {
                if(!gpsIsOpen())
                    return;
                mLocation = getLocation();
                if(mLocation != null)
                    textView.setText("維度:"   mLocation.getLatitude()   "\n經度:"   mLocation.getLongitude());
                else
                    textView.setText("獲取不到資料");
            }
            else if(btn.getId() == R.id.btnStop)
            {
                mLocationManager.removeUpdates(locationListener);
            }
        }
    };*//*



    //該方法獲取當前的經緯度， 第一次獲取總是null
    //後面從LocationListener獲取已改變的位置
    //mLocationManager.requestLocationUpdates()是開啟一個LocationListener等待位置變化
    private Location getLocation()
    {
//獲取位置管理服務
        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
//查詢服務資訊
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
        criteria.setAltitudeRequired(false); //海拔資訊：不需要
        criteria.setBearingRequired(false); //方位資訊: 不需要
        criteria.setCostAllowed(true);  //是否允許付費
        criteria.setPowerRequirement(Criteria.POWER_LOW); //耗電量: 低功耗
        String provider = mLocationManager.getBestProvider(criteria, true); //獲取GPS資訊
        Location location = mLocationManager.getLastKnownLocation(provider);
        mLocationManager.requestLocationUpdates(provider, 2000, 5, locationListener);
        return location;
    }

    private Object getSystemService(String locationService) {
    }

    //改方法是等待GPS位置改變後得到新的經緯度
    private final LocationListener locationListener = new LocationListener()
    {
        public void onLocationChanged(Location location)
        {
// TODO Auto-generated method stub
            if(location != null)
                textView.setText("維度:"   location.getLatitude()   "\n經度:"
                        location.getLongitude());
            else
                textView.setText("獲取不到資料"   Integer.toString(nCount));
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
*/
