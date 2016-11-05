package com.islavdroid.weatherapp.data;

import com.islavdroid.weatherapp.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class WeatherHttpClient {
    //здесь мы будем получать json data
    public String getWeatherData(String place){
        //здесь будем создавать http connection
        HttpURLConnection connection =null;
        InputStream inputStream =null;
        try {
            connection =(HttpURLConnection)(new URL(Utils.BASE_URL+place+Utils.ID)).openConnection();
            connection.setRequestMethod("GET"); //так как мы получаем данные
            connection.setDoInput(true);
            connection.setDoInput(true);
            connection.connect();

            //читаем ответ
            StringBuffer stringBuffer =new StringBuffer();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line =null;
            while ((line=bufferedReader.readLine())!=null){
                stringBuffer.append(line + "\r\n");

            }
            inputStream.close();
            connection.disconnect();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
