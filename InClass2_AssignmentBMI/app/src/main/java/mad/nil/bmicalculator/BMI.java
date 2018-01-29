package mad.nil.bmicalculator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
/**
 * Assignment 2
 * File name: BMI.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
public class BMI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        Button calculateButton = findViewById(R.id.buttonCalculate);

        // set onclick listener for button
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
            }
        });
    }

    // method to calculate bmi and display the result
    public void calculate() {
        LinearLayout res = findViewById(R.id.llResult);
        // round off format
        DecimalFormat format = new DecimalFormat("##.00");
        DecimalFormat format1 = new DecimalFormat("##.0");
        Boolean error = false;

        // Get all the values and validate them
        TextView textViewAge = findViewById(R.id.textViewAge);
        try {
            Integer age = Integer.parseInt(textViewAge.getText().toString());
            if(age < 18) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            textViewAge.setError("Please enter a valid age > 18");
            error = true;
        }

        TextView textViewWeight = findViewById(R.id.textViewWeight);
        Double weight = null;
        try {
            weight = Double.parseDouble(textViewWeight.getText().toString());
            if(weight <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            textViewWeight.setError("Please enter a valid weight");
            error = true;
        }

        TextView textViewDeet = findViewById(R.id.textViewHeightFeet);
        Integer heightFeet = null;
        try {
            heightFeet = Integer.parseInt(textViewDeet.getText().toString());
            if(heightFeet <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            textViewDeet.setError("Please enter a valid height");
            error = true;
        }

        TextView textViewInch = findViewById(R.id.textViewHeightInches);
        Integer heightInches = null;
        try {
            heightInches = Integer.parseInt(textViewInch.getText().toString());
            if(heightInches < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            textViewInch.setError("Please enter a valid height");
            error = true;
        }

        // Stop execution in case of failure
        if(error) {
            res.setVisibility(View.INVISIBLE);
            return;
        }

        Integer totalHeight = heightFeet * 12 + heightInches;
        Double bmi = 703 * (weight / Math.pow(totalHeight,2));

        TextView tv = (TextView) findViewById(R.id.textViewRes);
        tv.setText(format.format(bmi).toString());
        String category = "";

        //Logic for color and category
        String message = "";
        int color = 0;
        Double expectedBmi = null;
        if (bmi < 18.5){
            category = "Underweight";
            color = Color.RED;
            expectedBmi = 18.5;
        }else if(bmi >= 18.5 && bmi < 25){
            category = "Normal";
            color = Color.GREEN;
            expectedBmi = bmi;
        }else if(bmi >= 25 && bmi < 30){
            category = "Overweight";
            color = Color.parseColor("#ffa500");
            expectedBmi = 25.0;
        }else{
            category = "Obese";
            color = Color.YELLOW;
            expectedBmi = 25.0;
        }

        // Calculate expected BMI and weight
        TextView textViewMessage = findViewById(R.id.textViewMessage);
        String textMessage = null;
        if(bmi < 18.5 || bmi >= 25) {
            Double expectedWeight = (expectedBmi * totalHeight * totalHeight) / 703;
            Double differenceWeight = expectedWeight - weight;
            if(differenceWeight < 0) {
                differenceWeight = -differenceWeight;
                textMessage = "You will need to loose " + format1.format(differenceWeight) +"lbs to reach a BMI of " + expectedBmi;
            } else {
                textMessage = "You will need to gain " + format1.format(differenceWeight) +"lbs to reach a BMI of " + expectedBmi;
            }
        } else {
            textMessage = getResources().getString(R.string.normal_bmi);
        }

        // set display
        textViewMessage.setText(textMessage);
        tv = (TextView) findViewById(R.id.textViewCategory);
        tv.setText(" (" + category +")");
        tv.setTextColor(color);

        // change visibility
        res.setVisibility(View.VISIBLE);
    }
}
