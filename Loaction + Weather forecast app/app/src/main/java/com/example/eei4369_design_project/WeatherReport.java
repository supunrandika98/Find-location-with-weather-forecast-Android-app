package com.example.eei4369_design_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WeatherReport extends AppCompatActivity {


    //    weather api
    final String APP_ID = "c60d70e8fbef34886fe2e14b59ca5d5f";
    //    weather api
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";


    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView NameofCity, weatherState, Temperature,TemperatureMin,TemperatureMax,FeelsLike,Humidity,Pressure,WindSpeed,WindDeg,Date,Description,SunRise,SunSet;
    ImageView mweatherIcon;
    RelativeLayout mCityFinder;
    LocationManager mLocationManager;
    LocationListener mLocationListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);
        //getWindow().setLayout(200, 550);

        weatherState = findViewById(R.id.weatherCondition);
        Temperature = findViewById(R.id.temperature);
        mweatherIcon = findViewById(R.id.weatherIcon);
        TemperatureMin = findViewById(R.id.temperaturemin);
        TemperatureMax = findViewById(R.id.temperaturemax);
        FeelsLike = findViewById(R.id.feelLike);
        Humidity = findViewById(R.id.humidity);
        Pressure = findViewById(R.id.presure);
        WindSpeed = findViewById(R.id.windspeed);
        WindDeg = findViewById(R.id.winddeg);
        //NameofCity = findViewById(R.id.city);
        Date =findViewById(R.id.date);
        Description =findViewById(R.id.description);
        SunRise = findViewById(R.id.sunrise);
        SunSet = findViewById(R.id.sunset);


    }

    //    first method that runs when the app start
    @Override
    protected void onResume() {
        super.onResume();
        getWeatherForCurrentLocation();
    }


    //    fetching the weather of current location

    private void getWeatherForCurrentLocation() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListner = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", Latitude);
                params.put("lon", Longitude);
                params.put("appid", APP_ID);
                letsdoSomeNetworking(params);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //not able to get location
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListner);
    }

    //   checking is user allow location or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(WeatherReport.this,"Locationget Successffully" , Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            }
            else
            {
                //user denied the permission
            }
        }
    }

    //running when fetching the current location
    private void letsdoSomeNetworking(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler()
        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {

                Toast.makeText(WeatherReport.this, "Data Get Success",Toast.LENGTH_SHORT).show();
                com.example.eei4369_design_project.WeatherData weatherD= null;
                weatherD = WeatherData.fromJson(response);
                updateUI(weatherD);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }


    private void updateUI(com.example.eei4369_design_project.WeatherData weather)
    {
        Temperature.setText(weather.getmTemperature());
        weatherState.setText(weather.getmWeatherType());
        int resourseID = getResources().getIdentifier(weather.getMicon(),"drawable",getPackageName());
        mweatherIcon.setImageResource(resourseID);
        //NameofCity.setText(weather.getMcity());
        TemperatureMin.setText(weather.getmTemperatureMin());
        TemperatureMax.setText(weather.getmTemperatureMax());
        FeelsLike.setText(weather.getmFeelsLike());
        Humidity.setText(weather.getmHumidity());
        Pressure.setText(weather.getmPressure());
        WindSpeed.setText(weather.getmWindSpeed());
        WindDeg.setText(weather.getmWindDeg());
        Date.setText(weather.getmDate());
        Description.setText(weather.getmDescription());
        SunSet.setText(weather.getmSunSet());
        SunRise.setText(weather.getmSunRise());



    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationManager!=null)
        {
            mLocationManager.removeUpdates(mLocationListner);
        }
    }
}