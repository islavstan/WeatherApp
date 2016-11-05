package com.islavdroid.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.islavdroid.weatherapp.data.JSONWeatherParser;
import com.islavdroid.weatherapp.data.WeatherHttpClient;
import com.islavdroid.weatherapp.model.Weather;

//https://www.youtube.com/watch?v=UuGIAomlU1I   48/00
public class MainActivity extends AppCompatActivity {

    private TextView city,tempurature,wind,cloud,pressure,humid,rise,sunset,last_update;
    private Button change_city;
    private ImageView image;

    Weather weather =new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city =(TextView)findViewById(R.id.city);
        tempurature =(TextView)findViewById(R.id.tempurature);
        wind =(TextView)findViewById(R.id.wind);
        cloud =(TextView)findViewById(R.id.cloud);
        pressure =(TextView)findViewById(R.id.pressure);
        humid =(TextView)findViewById(R.id.humid);
        rise =(TextView)findViewById(R.id.rise);
        sunset =(TextView)findViewById(R.id.sunset);
        last_update =(TextView)findViewById(R.id.last_update);
        change_city =(Button)findViewById(R.id.change_city);
        image =(ImageView)findViewById(R.id.image);
        renderWeatherData("Dnepr");
    }

    public void renderWeatherData(String city){
        WeatherTask weatherTask =new WeatherTask();
        weatherTask.execute(city + "&units=metric");


    }

    private class WeatherTask extends AsyncTask<String,Void,Weather>{

        @Override
        protected Weather doInBackground(String... params) {
            //передаём параметр в сам метод
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));
            weather = JSONWeatherParser.getWeather(data);
            Log.v("Data: ",weather.currentCondition.getDescription());

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            city.setText(weather.place.getCity()+","+weather.place.getCountry());
            tempurature.setText(""+weather.currentCondition.getTempurature()+"°");
            humid.setText("Влажность: "+weather.currentCondition.getHumidity()+"%");


        }
    }
}
