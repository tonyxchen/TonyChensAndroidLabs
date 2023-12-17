package algonquin.cst2335.chen0872.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.chen0872.data.MainViewModel;
import algonquin.cst2335.chen0872.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding variableBinding;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());




        variableBinding.mybutton.setOnClickListener( vw -> model.editString.postValue(variableBinding.myedittext.getText().toString()));
        model.editString.observe(this, s -> {variableBinding.mytext.setText("Your edit text has " + s);});


    }
}