package com.islavdroid.weatherapp.data;


import com.islavdroid.weatherapp.model.Place;
import com.islavdroid.weatherapp.model.Weather;
import com.islavdroid.weatherapp.util.Utils;

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

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
