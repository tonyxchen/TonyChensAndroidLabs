package algonquin.cst2335.chen0872.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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




        variableBinding.myButton.setOnClickListener( vw -> model.editString.postValue(variableBinding.myedittext.getText().toString()));
        model.editString.observe(this, s -> {variableBinding.myText.setText("Your edit text has " + s);});
        model.isSelected.observe(this, selected -> {
            variableBinding.myCheckBox.setChecked(selected);
            variableBinding.myRadioButton.setChecked(selected);
            variableBinding.mySwitch.setChecked(selected);
            Toast.makeText(getApplicationContext(), "The value is now: " + selected, Toast.LENGTH_SHORT).show();

        });
        variableBinding.myCheckBox.setOnCheckedChangeListener( (btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });
        variableBinding.myRadioButton.setOnCheckedChangeListener( (btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });
        variableBinding.mySwitch.setOnCheckedChangeListener( (btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });
        variableBinding.myImageButton.setOnClickListener( vw ->
                Toast.makeText(getApplicationContext(), "The width = " + variableBinding.myImageButton.getWidth() + " and height = " + variableBinding.myImageButton.getHeight(), Toast.LENGTH_SHORT).show()
        );


    }
}