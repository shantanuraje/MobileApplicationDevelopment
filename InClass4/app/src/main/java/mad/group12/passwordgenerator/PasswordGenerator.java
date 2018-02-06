package mad.group12.passwordgenerator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class PasswordGenerator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);
        LinearLayout ll_numOfPassCount = (LinearLayout) findViewById(R.id.ll_numOfPassCount);
        SeekBar sb_numOfPassCount = (SeekBar) findViewById(R.id.sb_lenPass);

        LinearLayout ll_lenPass = (LinearLayout) findViewById(R.id.ll_lenPass);
        SeekBar sb_lenPass = (SeekBar)findViewById(R.id.sb_lenPass);
    }

    private class BackgroundGenerator extends AsyncTask<Integer, Integer, String[]> {
        Integer passwordCount;
        Integer passwordLength;

        @Override
        protected void onPreExecute() {
            SeekBar sbPasswordCount = findViewById(R.id.sb_numOfPassCount);
            SeekBar sbLengthOfPassword = findViewById(R.id.sb_lenPass);

            passwordCount = sbPasswordCount.getProgress();
            passwordLength = sbLengthOfPassword.getProgress();
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(Integer... length) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);

            final String[] options = result;

            AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext()).setTitle("Select your gender")
                    .setSingleChoiceItems(options, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            TextView textView = findViewById(R.id.textView2);
                            textView.setText(options[i]);
                        }
                    });
            final AlertDialog genderDialog = builder.create();
        }
    }
}
