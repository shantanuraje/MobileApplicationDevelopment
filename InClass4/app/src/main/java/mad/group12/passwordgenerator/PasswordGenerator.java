package mad.group12.passwordgenerator;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class PasswordGenerator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);
        final LinearLayout ll_numOfPassCount = (LinearLayout) findViewById(R.id.ll_numOfPassCount);
        final SeekBar sb_numOfPassCount = (SeekBar) findViewById(R.id.sb_lenPass);
        final TextView tv_numOfPasses = findViewById(R.id.tv_numOfPasses);

        sb_numOfPassCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tv_numOfPasses.setText(String.valueOf(progress));
                Log.d("PassGen1", String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        final LinearLayout ll_lenPass = (LinearLayout) findViewById(R.id.ll_lenPass);
        final SeekBar sb_lenPass = (SeekBar)findViewById(R.id.sb_lenPass);
        final TextView tv_lenOfPasses = findViewById(R.id.tv_lenOfPasses);

        sb_lenPass.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tv_lenOfPasses.setText(String.valueOf(progress));
                Log.d("PassGen2", String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private class BackgroundGenerator extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
}
