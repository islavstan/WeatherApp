package com.islavdroid.weatherapp.data;


import com.islavdroid.weatherapp.model.Place;
import com.islavdroid.weatherapp.model.Weather;
import com.islavdroid.weatherapp.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {
    public static Weather getWeather(String data){
        Weather weather =new Weather();
        //create json object from data
        try {
            JSONObject jsonObject =new JSONObject(data);
            Place place =new Place();
            //получаем координаты с сайта
            JSONObject coordObj = Utils.getObject("coord",jsonObject);
            //задаем долготу и ширину
            place.setLat(Utils.getFloat("lat",coordObj));
            place.setLon(Utils.getFloat("lon",coordObj));

            //get the sys obj
            JSONObject  sysObj = Utils.getObject("sys",jsonObject);
            place.setCountry(Utils.getString("country",sysObj));
            //здесь обращаемся к jsonObject так как dt принадлежит к main object
            place.setLastUpdate(Utils.getInt("dt",jsonObject));
            place.setSunrice(Utils.getInt("sunrise",sysObj));
            place.setSunset(Utils.getInt("sunset",sysObj));
            place.setCity(Utils.getString("name",jsonObject));
            weather.place =place;

            //get the weather info
            //weather на сайте представлен как array
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            //так как там всего один объект в списке указываем 0
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("id",jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description",jsonWeather));
            weather.currentCondition.setCondition(Utils.getString("main",jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon",jsonWeather));

            JSONObject windObj = Utils.getObject("wind",jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed",windObj));
            weather.wind.setDeg(Utils.getFloat("deg",windObj));

            JSONObject cloudObj = Utils.getObject("clouds",jsonObject);
            weather.clouds.setPrecipitations(Utils.getInt("all",cloudObj));
            return weather;



        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
