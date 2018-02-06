package mad.group12.passwordgenerator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordGenerator extends AppCompatActivity {

    Handler handler;

    LinkedList<String> passwordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);
        LinearLayout ll_numOfPassCount = (LinearLayout) findViewById(R.id.ll_numOfPassCount);
        final SeekBar sb_numOfPassCount = (SeekBar) findViewById(R.id.sb_lenPass);
        final TextView tv_numOfPasses = new TextView(this);

        sb_numOfPassCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tv_numOfPasses.setText(String.valueOf(progress));
                Log.d("PassGen", String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
        ll_numOfPassCount.addView(tv_numOfPasses);

        LinearLayout ll_lenPass = (LinearLayout) findViewById(R.id.ll_lenPass);
        SeekBar sb_lenPass = (SeekBar)findViewById(R.id.sb_lenPass);
        final TextView tv_lenOfPasses = new TextView(this);

        sb_lenPass.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tv_lenOfPasses.setText(String.valueOf(progress));
                Log.d("PassGen", String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ll_lenPass.addView(tv_lenOfPasses);

        passwordList = new LinkedList<>();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                SeekBar sbPasswordCount = findViewById(R.id.sb_numOfPassCount);
                Integer passwordCount = sbPasswordCount.getProgress();

                passwordList.add(msg.getData().get("password").toString());
                if(passwordList.size() == passwordCount.intValue()) {
                    showPasswordDialog((String[]) passwordList.toArray());
                }
                return false;
            }
        });
    }

    public void generatePasswordByThreads() {
        SeekBar sbPasswordCount = findViewById(R.id.sb_numOfPassCount);
        SeekBar sbLengthOfPassword = findViewById(R.id.sb_lenPass);

        Integer passwordCount = sbPasswordCount.getProgress();
        Integer passwordLength = sbLengthOfPassword.getProgress();

        ExecutorService executor = Executors.newFixedThreadPool(passwordCount / 2 + 1);
        for(int i=0; i<passwordCount; i++) {
            executor.execute(new ThreadGenerator(passwordLength));
        }
    }

    public void showPasswordDialog(String[] result) {
        final String[] options = result;

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext()).setTitle("Select your gender")
                .setSingleChoiceItems(options, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView textView = findViewById(R.id.tv_password);
                        textView.setText(options[i]);
                    }
                });
        final AlertDialog genderDialog = builder.create();
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

            showPasswordDialog(result);
        }
    }

    class ThreadGenerator implements Runnable {
        private Integer length;

        public ThreadGenerator(Integer length) {
            this.length = length;
        }

        @Override
        public void run() {
            String password = Generator.getPassword(length);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("password", password);
            message.setData(bundle);

            handler.sendMessage(message);
        }
    }
}
