package com.example.eei4369_design_project;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherData {

    private String mTemperature;
    private String micon;
    private String mcity;
    private String mWeatherType;
    private String mTemperatureMin;
    private String mTemperatureMax;
    private String mHumidity;
    private String mPressure;
    private String mFeelsLike;
    private int mCondition;
    private String mWindSpeed;
    private String mWindDeg;
    private String mSunRise;
    private String mSunSet;
    private String mDate;
    private String mDescription;

    public static WeatherData fromJson(JSONObject jsonObject)
    {

        try
        {
            WeatherData weatherD=new WeatherData();
            weatherD.mcity=jsonObject.getString("name");
            weatherD.mCondition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.mWeatherType=jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.micon=updateWeatherIcon(weatherD.mCondition);

            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue=(int)Math.rint(tempResult);
            weatherD.mTemperature=Integer.toString(roundedValue);

            double tempMin=jsonObject.getJSONObject("main").getDouble("temp_min")-273.15;
            int roundedValue1=(int)Math.rint(tempMin);
            weatherD.mTemperatureMin=Integer.toString(roundedValue1);

            double tempMax=jsonObject.getJSONObject("main").getDouble("temp_max")-273.15;
            int roundedValue2=(int)Math.rint(tempMax);
            weatherD.mTemperatureMax=Integer.toString(roundedValue2);

            double feelslike=jsonObject.getJSONObject("main").getDouble("feels_like")-273.15;
            int roundedValue3=(int)Math.rint(feelslike);
            weatherD.mFeelsLike=Integer.toString(roundedValue3);

            int humidity=jsonObject.getJSONObject("main").getInt("humidity");
            weatherD.mHumidity = Integer.toString(humidity);

            int pressure=jsonObject.getJSONObject("main").getInt("pressure");
            weatherD.mPressure = Integer.toString(pressure);

            double windspeed=jsonObject.getJSONObject("wind").getDouble("speed");
            int roundedValue4=(int)Math.rint(windspeed);
            weatherD.mWindSpeed=Integer.toString(roundedValue4);

            int deg=jsonObject.getJSONObject("wind").getInt("deg");
            weatherD.mWindDeg = Integer.toString(deg);

            Long date = jsonObject.getLong("dt");
            weatherD.mDate= new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH).format(new Date(date *1000));

            weatherD.mDescription=jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

            Long sunrise = jsonObject.getJSONObject("sys").getLong("sunrise");
            weatherD.mSunRise= new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH).format(new Date(sunrise *1000));

            Long sunset = jsonObject.getJSONObject("sys").getLong("sunset");
            weatherD.mSunSet= new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH).format(new Date(sunset *1000));

            return weatherD;



        }


        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    private static String updateWeatherIcon(int condition)
    {
        if(condition>=0 && condition<=300)
        {
            return "thunderstrom1";
        }
        else if(condition>=300 && condition<=500)
        {
            return "lightrain";
        }
        else if(condition>=500 && condition<=600)
        {
            return "shower";
        }
        else  if(condition>=600 && condition<=700)
        {
            return "snow2";
        }
        else if(condition>=701 && condition<=771)
        {
            return "fog";
        }

        else if(condition>=772 && condition<=800)
        {
            return "overcast";
        }
        else if(condition==800)
        {
            return "sunny";
        }
        else if(condition>=801 && condition<=804)
        {
            return "cloudy";
        }
        else  if(condition>=900 && condition<=902)
        {
            return "thunderstrom1";
        }
        if(condition==903)
        {
            return "snow1";
        }
        if(condition==904)
        {
            return "sunny";
        }
        if(condition>=905 && condition<=1000)
        {
            return "thunderstrom2";
        }

        return "dunno";


    }

    public String getmTemperature() {
        return mTemperature+"°C";
    }

    public String getMicon() {
        return micon;
    }

    public String getMcity() {
        return mcity;
    }

    public String getmWeatherType() {
        return mWeatherType;
    }

    public String getmTemperatureMin() {
        return "Temperature Min : "+mTemperatureMin+"°C";
    }
    public String getmTemperatureMax() {
        return "Temperature Max : "+mTemperatureMax+"°C";
    }
    public String getmFeelsLike() {
        return "Feels Like : "+mFeelsLike +"°C";
    }

    public String getmHumidity() {
        return " Humidity : "+mHumidity;
    }
    public String getmPressure() {
        return "Pressure : "+ mPressure;
    }
    public String getmWindSpeed() {
        return "Wind Speed : "+ mWindSpeed;
    }

    public String getmWindDeg() {
        return "Wind Deg : "+ mWindDeg;
    }


    public String getmDate() {
        return "Date & Time : "+ mDate;
    }

    public String getmDescription() {
        return "Description : " + mDescription;
    }

    public String getmSunRise() {
        return "SunRise : "+ mSunRise;
    }

    public String getmSunSet() {
        return "SunSet : "+ mSunSet;
    }
}

