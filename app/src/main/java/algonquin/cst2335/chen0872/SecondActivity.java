package algonquin.cst2335.chen0872;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    TextView textView;
    EditText phoneNumber;
    Button callButton;
    ImageView profilePic;
    Button changePic;
    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView);
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        textView.setText("Welcome back " + emailAddress);

        callButton = findViewById(R.id.buttonCall);
        phoneNumber = findViewById(R.id.editTextPhone);

        callButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:" + phoneNumber.getText()));
                startActivity(call);
            }
        });

        changePic = findViewById(R.id.buttonPic);
        profilePic = findViewById(R.id.imageViewCamera);

        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        Bitmap thumbnail = data.getParcelableExtra("data");
                        profilePic.setImageBitmap(thumbnail);

                        FileOutputStream fOut = null;
                            try{fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                                thumbnail.compress(Bitmap.CompressFormat.PNG,100,fOut);
                                fOut.flush();
                                fOut.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                    }

                }
        );

        File file = new File(getFilesDir(),"Picture.png");
        if(file.exists()){
            Bitmap image = BitmapFactory.decodeFile("/data/data/algonquin.cst2335.chen0872/files/Picture.png");
            profilePic.setImageBitmap(image);
        }


        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                cameraResult.launch(cameraIntent);
            }
        });


    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.w( TAG, "In onPause() - The application no longer responds to user input" );
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNumber", phoneNumber.getText().toString());
        editor.apply();

    }
}