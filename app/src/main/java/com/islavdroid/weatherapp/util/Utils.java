package com.islavdroid.weatherapp.util;


import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

    //http://api.openweathermap.org/data/2.5/weather?q=London&appid=7f2a3f4038008c167d48a29dea4c2d7b
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String ICON_URL = "http://api.openweathermap.org/img/w/";
    public static final String ID = "&appid=7f2a3f4038008c167d48a29dea4c2d7b";

   public static JSONObject getObject(String tagName,JSONObject jsonObject) throws JSONException{
       //получаем JSONObject value по введённому имени
       JSONObject object = jsonObject.getJSONObject(tagName);
       return object;
   }

    public static String getString(String tagName,JSONObject jsonObject)throws JSONException{
        //получаем String value по введённому имени
        return  jsonObject.getString(tagName);
    }

    public static float getFloat(String tagName,JSONObject jsonObject)throws JSONException{
        return (float)jsonObject.getDouble(tagName);
    }
    public static double getDouble(String tagName,JSONObject jsonObject)throws JSONException{
        return (float)jsonObject.getDouble(tagName);
    }
    public static int getInt(String tagName,JSONObject jsonObject)throws JSONException{
        return jsonObject.getInt(tagName);
    }


}
