package com.example.bmicalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 BMI Calculator Program ReadMe File
 ======================================
 Hagay Cohen ID 201305679, hagaico100@gmail.com
 Elihai Ben Avraham ID 206056400, elihai1995@gmail.com
 Second year
 Computer Science Department- Ashqelon College
 ================================================
 Assignment:
 Writing a program that implements a BMI calculator with both Java and Android Studio. We get the information from the user (gender, age, height and weight) and we use that information to calculate the BMI (https://en.wikipedia.org/wiki/Body_mass_index) and to print the BMI, the status weight and the recommended weight of the user.

 Main Goals:
 Use our knowledge in Java programming and implement a BMI calculator with Android Studio java.
 Write a ReadMe file that explains how the program works.

 Package:
 BMI Calculator - Hagay&Elihai

 Classes:
 MainActivity.java
 activity_main.xml

 Classes Explanation:
 We used this formula to calculate the BMI:
 BMI = weight (kg) / height * height (m), and according to the result we decided the weight status.
 For the recommended\actual weight, we used this Leffler formula (https://en.wikipedia.org/wiki/Human_body_weight#Estimation_in_children):
 For kids:
 weight = 0/5 * months + 4	0 <= age <= 1
 weight = 2 * years + 10		1 <= age <= 10
 For other ages:
 male weight (kg) = 48 + 1.1 * (h – 152)	h = height in cm
 female weight (kg) = 45.4 + 0.9 * (h – 152)	h = height in cm

 Application details:
 We designed our app using the Palette. We then set up our own layout. To run the calculations and get the results of the data entered by the user, you must press the "CALCULATE BMI" button at the bottom of the screen. In addition, we added two additional buttons "CLEAR" and "EXIT" to allow the user to clear all current data on the screen and start a new BMI calculation or exit the application respectively.
 In our program we used various tools to diversify the operations and make it easier for the user. For example, we chose Spinner, and we chose RadioButton to make the app special.
 In our application we chose to use one screen where the calculations are made, and when you fill in all the data and press the "CALCULATE BMI" button, the results appear at the bottom of the screen in an orderly manner (example in "bmi_results.jpg" in the "drawable" folder).
 The last thing is to use try-and-catch to catch the user in a situation that tries to calculate BMI but does not enter all the required data, preventing the application from making incorrect calculations or, heaven forbid, flying out. If the user clicks "CALCULATE BMI" and does not type all the required data, the following toast screen appears: "You have to fill all the fields" (examples appear in the "drawable" folder).

 Running the program:
 To run the program, from within the *.java files location, type the following orders:
 Enter to Android Studio and import this file "BMI Calculator - Hagay&Elihai". After that you can push the play button and select your emulator and wait. Now you can enter your numbers and push " CALCULATE BMI " and enjoy.
 To exit the program: within the GUI, just press the 'X' with your mouse, or press the "EXIT" Button.
 All the process of running the program have been done only on "Genymotion" emulator, and in the "drawable" folder are attached images that show the screen of the BMI CALCULATORE, an example of results and examples of "You have to fill all the fields" cases in the "Genymotion" emulator.

 */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button buttonClear, buttonCalculateBMI;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner ageSpinner;
    private String spinnerSelect;
    private String selectAge;
    private String selectWeight;
    private TextView textViewCm, BmiResults, WeightStatus, RecommendedWeight;
    private EditText editTextAge;
    private EditText editTextWeight;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);
        ageSpinner = (Spinner) findViewById(R.id.age);
        ArrayList <String> spinnerList = new ArrayList<>();
        spinnerList.add("years old");
        spinnerList.add("months old");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line , spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelect = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        editTextAge = (EditText)findViewById(R.id.enter_age);
        editTextAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAge = editTextAge.getText().toString();
            }
        });
        editTextWeight = (EditText)findViewById(R.id.enter_weight);
        editTextWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectWeight = editTextWeight.getText().toString();
            }
        });
        //seekbar
        textViewCm = (TextView)findViewById(R.id.textViewCm);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(260);
        textViewCm.setText(seekBar.getProgress() + " cm");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value = progress;
                textViewCm.setText(seekBar.getProgress() + " cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textViewCm.setText(seekBar.getProgress() + " cm");
            }
        });
        BmiResults = findViewById((R.id.textViewBmiResults));
        WeightStatus = findViewById((R.id.textViewWeightStatus));
        RecommendedWeight = findViewById(R.id.textViewRecommendedWeight);
        buttonClear = (Button)findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                radioGroup.clearCheck();
                editTextAge.setText("");
                editTextWeight.setText("");
                seekBar.setProgress(0);
                BmiResults.setText("Your BMI is: ");
                WeightStatus.setText("Your weight status is: ");
                RecommendedWeight.setText("Your recommended weight is: ");
            }
        });
        buttonCalculateBMI = (Button)findViewById(R.id.buttonCalculateBMI);
        buttonCalculateBMI.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String gender, status="", recWeightT= "", yourBmi= "";
                double age, height, actualWeight, recWeight = -1 , bmi, temp;
                try {
                    gender = radioButton.getText().toString();
                    if (spinnerSelect.equals("months old")) {
                        age = Double.parseDouble(editTextAge.getText().toString()) / 12;
                    }else {
                        age = Double.parseDouble(editTextAge.getText().toString());
                    }
                    height = Double.parseDouble(seekBar.getProgress() + "");
                    actualWeight = Double.parseDouble(editTextWeight.getText().toString());
                    if (age >= 0) {
                        if (age <= 1)
                            recWeight = 6 * age + 4; // (0.5 * 12) * age + 4
                        else if (age <= 10)
                            recWeight = 2 * age + 10;
                        else if ((radioButton.getText().toString()).equals("Male"))
                            recWeight = 48 + 1.1 * (height - 152);
                        else if ((radioButton.getText().toString()).equals("Female"))
                            recWeight = 45.4 + 0.9 * (height - 152);
                    }
                    if ((radioButton.getText().toString()).equals("Male"))
                        recWeight = 48 + 1.1 * (height - 152);
                    else if ((radioButton.getText().toString()).equals("Female"))
                        recWeight = 45.4 + 0.9 * (height - 152);
                    if (recWeight != -1) {
                        temp = Math.round(recWeight * 100);
                        recWeight = temp / 100;
                        recWeightT =("Your recommended weight is: " + recWeight);
                    }
                    bmi = actualWeight / ((height / 100) * (height / 100));
                    temp = Math.round(bmi * 100);
                    bmi = temp / 100;
                    yourBmi = ("Your BMI is: " + bmi);
                    if (bmi < 15)
                        status = "Your weight status is: Anorexic";
                    else if (bmi >= 15 && bmi < 18.5)
                        status = ("Your weight status is: Underweight");
                    else if (bmi >= 18.5 && bmi <= 24.9)
                        status = ("Your weight status is: Normal");
                    else if (bmi >= 25 && bmi <= 29.9)
                        status = ("Your weight status is: Overweight");
                    else if (bmi >= 30 && bmi <= 35)
                        status = ("Your weight status is: Obese");
                    else if (bmi > 35)
                        status = ("Your weight status is: Extreme Obese");
                    BmiResults.setText(yourBmi.toString());
                    WeightStatus.setText(status);
                    RecommendedWeight.setText(recWeightT.toString());
                }
                catch(Exception missed)
                {
                    Toast.makeText(MainActivity.this, "You have to fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void clickRadioButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

    public void clickExit(View v){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}