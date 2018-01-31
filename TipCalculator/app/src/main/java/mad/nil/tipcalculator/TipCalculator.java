package mad.nil.tipcalculator;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toolbar;
/**
 * Homework 1
 * File name: TipCalculator.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
public class TipCalculator extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_tip_calculator);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();

        EditText billValueText = findViewById(R.id.bill_input_value);
        billValueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });

        RadioGroup radioGroup = findViewById(R.id.tip_selected_perc);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calculate();
            }
        });

        SeekBar seekBar = findViewById(R.id.tip_custom_seek_value);
        seekBar.setEnabled(false);
        seekBar.setMax(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                TextView seekBarValue = findViewById(R.id.seek_value);
                seekBarValue.setText(String.valueOf(progress) + "%");
                calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        Button exit = findViewById(R.id.exit_button);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void calculate() {
        Resources resources = getResources();

        RadioGroup radioGroup = findViewById(R.id.tip_selected_perc);
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String radioButtonSelected = radioButton.getText().toString();

        Double tipPercent = 0.0;

        SeekBar seekBar = findViewById(R.id.tip_custom_seek_value);
        TextView customLabel = findViewById(R.id.custom_label);
        TextView seekLabel = findViewById(R.id.seek_value);
        if(resources.getString(R.string.tip_custom).equals(radioButtonSelected)) {
            seekBar.setEnabled(true);
            customLabel.setEnabled(true);
            seekLabel.setEnabled(true);
            tipPercent = new Double(seekBar.getProgress());
        } else {
            seekBar.setEnabled(false);
            customLabel.setEnabled(false);
            seekLabel.setEnabled(false);
            tipPercent = Double.parseDouble(radioButtonSelected.replaceAll("%", ""));
        }


        EditText billValueText = findViewById(R.id.bill_input_value);
        TextView tipTextView = findViewById(R.id.tip_value);
        TextView totalTextView = findViewById(R.id.total_value);
        Double billValue = 0.0;
        try {
            billValue = Double.parseDouble(billValueText.getText().toString());
        } catch (NumberFormatException e) {
            billValueText.setError("Enter a valid bill total");
            tipTextView.setText("0.00");
            totalTextView.setText("0.00");
            return;
        }

        Double tipValue = billValue * tipPercent / 100.0;
        String tipValueStr = String.format("%.2f", tipValue);

        Double totalValue = billValue + new Double(tipValueStr);

        tipTextView.setText(tipValueStr);
        totalTextView.setText(String.format("%.2f", totalValue));
    }
}
