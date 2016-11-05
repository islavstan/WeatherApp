package com.islavdroid.weatherapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.islavdroid.weatherapp.data.CityPreference;
import com.islavdroid.weatherapp.data.JSONWeatherParser;
import com.islavdroid.weatherapp.data.WeatherHttpClient;
import com.islavdroid.weatherapp.model.Weather;
import com.islavdroid.weatherapp.util.Utils;
import com.squareup.picasso.Picasso;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

//https://www.youtube.com/watch?v=UuGIAomlU1I   1/22
public class MainActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
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
        change_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        image =(ImageView)findViewById(R.id.image);
        CityPreference cityPreference =new CityPreference(MainActivity.this);
        renderWeatherData(cityPreference.getCity());
    }

    public void renderWeatherData(String city){
        WeatherTask weatherTask =new WeatherTask();
        weatherTask.execute(city + "&units=metric");


    }



    private class WeatherTask extends AsyncTask<String,Void,Weather>{
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(MainActivity.this);
           pDialog.setMessage("Загрузка...");
           pDialog.setCancelable(false);
           pDialog.show();
       }

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
            if (pDialog.isShowing())
                pDialog.dismiss();
            DateFormat dateFormat =DateFormat.getTimeInstance();
            String sunRiseDate = dateFormat.format(new Date(weather.place.getSunrice()));
            String sunSetDate = dateFormat.format(new Date(weather.place.getSunset()));
            String updateDate = dateFormat.format(new Date(weather.place.getLastUpdate()));
            DecimalFormat decimalFormat =new DecimalFormat("#.#");
            String tempFormat = decimalFormat.format(weather.currentCondition.getTempurature());
            city.setText(weather.place.getCity()+","+weather.place.getCountry());
            tempurature.setText(""+tempFormat+"°");
            humid.setText("Влажность: "+weather.currentCondition.getHumidity()+"%");
            pressure.setText("Давление: "+weather.currentCondition.getPressure()+"hPa");
            wind.setText("Ветер: "+weather.wind.getSpeed()+"м/с");
            rise.setText("Восход солнца: "+ sunRiseDate);
            sunset.setText("Закат: "+sunSetDate);
            cloud.setText(weather.currentCondition.getCondition()+"("+weather.currentCondition.getDescription()+")");
            last_update.setText("Последнее обновление: "+updateDate);
          Picasso.with(getApplicationContext()).load(Utils.ICON_URL+weather.currentCondition.getIcon()).into(image);



        }
    }

    private void showDialog(){
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(MainActivity.this);
        final EditText changeCity =new EditText(MainActivity.this);
        alertDialog.setTitle("Укажите страну или город");
        changeCity.setInputType(InputType.TYPE_CLASS_TEXT);
        changeCity.setHint("Kiev");
        alertDialog.setView(changeCity);
        alertDialog.setPositiveButton("продолжить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CityPreference cityPreference =new CityPreference(MainActivity.this);
                if(changeCity.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Поле не может быть пустым",Toast.LENGTH_LONG).show();
                }else{
                cityPreference.setCity(changeCity.getText().toString());
                String newCity =cityPreference.getCity();
                renderWeatherData(newCity);}
            }
        });
        alertDialog.show();

    }
}
