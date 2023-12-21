package algonquin.cst2335.chen0872;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;



public class MainActivity extends AppCompatActivity {


    protected String cityName;
    RequestQueue queue = null;

    Bitmap image;

    String description;
    String iconName;

    double current;
    double min;
    double max;
    int humidity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.getForecast);
        TextView citytxt = findViewById(R.id.city);
        TextView temptxt = findViewById(R.id.temp);
        TextView mintxt = findViewById(R.id.min);
        TextView maxtxt = findViewById(R.id.max);
        TextView desctxt = findViewById(R.id.description);
        TextView humiditytxt = findViewById(R.id.humidity);
        ImageView imageView = findViewById(R.id.icon);




        queue = Volley.newRequestQueue(this);

        btn.setOnClickListener(clk -> {

            cityName = citytxt.getText().toString();
            String stringURL = null;
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName, "UTF-8")
                        + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {
                        try {
                            JSONObject coord = response.getJSONObject("coord");
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject position0 = weatherArray.getJSONObject(0);
                            JSONObject mainObject = response.getJSONObject("main");

                            description = position0.getString("description");
                            iconName = position0.getString("icon");


                            current = mainObject.getDouble("temp");
                            min = mainObject.getDouble("temp_min");
                            max = mainObject.getDouble("temp_max");
                            humidity = mainObject.getInt("humidity");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    },

                    (error) -> {
                    });
            queue.add(request);
            runOnUiThread( (  )  -> {
                temptxt.setText("The current temperature is " + current);
                mintxt.setText("The minimum temperature is " + min);
                desctxt.setText("Description: " + description);
                maxtxt.setText("The maximum temperature is " + max);
                humiditytxt.setText("The humidity is " + humidity);

                temptxt.setVisibility(View.VISIBLE);
                humiditytxt.setVisibility(View.VISIBLE);
                maxtxt.setVisibility(View.VISIBLE);
                mintxt.setVisibility(View.VISIBLE);
                desctxt.setVisibility(View.VISIBLE);
            });

            String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
            ImageRequest imgReq = new ImageRequest(imageUrl, bitmap -> {
                try {
                    image = bitmap;
                    //image.compress(Bitmap.CompressFormat.PNG, 100, MainActivity.this.openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {
            });
            queue.add(imgReq);

/*
                FileOutputStream fOut = null;
                try {
                    fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);

                    image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }

 */

            runOnUiThread( (  )  -> {
                imageView.setImageBitmap(image); imageView.setVisibility(View.VISIBLE); });







        });

    }



}