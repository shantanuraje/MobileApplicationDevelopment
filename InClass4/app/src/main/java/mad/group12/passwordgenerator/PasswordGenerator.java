package mad.group12.passwordgenerator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordGenerator extends AppCompatActivity {

    public static final int MAX_POOL_COUNT = 2;
    public static final int MIN_PASSWORD_COUNT = 1;
    public static final int MAX_PASSWORD_COUNT = 10;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 23;

    Handler handler;

    LinkedList<String> passwordList;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);
        LinearLayout ll_numOfPassCount = (LinearLayout) findViewById(R.id.ll_numOfPassCount);
        final TextView tv_numOfPasses = findViewById(R.id.tv_numOfPasses);


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);


        SeekBar sb_numOfPassCount = findViewById(R.id.sb_numOfPassCount);
        sb_numOfPassCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tv_numOfPasses.setText(String.valueOf(progress + MIN_PASSWORD_COUNT));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        final LinearLayout ll_lenPass = (LinearLayout) findViewById(R.id.ll_lenPass);
        final SeekBar sb_lenPass = findViewById(R.id.sb_lenPass);
        final TextView tv_lenOfPasses = findViewById(R.id.tv_lenOfPasses);

        sb_lenPass.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tv_lenOfPasses.setText(String.valueOf(progress + MIN_PASSWORD_LENGTH));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button threadButton = findViewById(R.id.btn_gen_threads);
        threadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePasswordByThreads();
            }
        });

        Button asyncButton = findViewById(R.id.btn_gen_async);
        asyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePasswordByAsync();
            }
        });

        passwordList = new LinkedList<>();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                SeekBar sbPasswordCount = findViewById(R.id.sb_numOfPassCount);
                Integer passwordCount = sbPasswordCount.getProgress() + MIN_PASSWORD_COUNT;
                SeekBar sbPasswordLength = findViewById(R.id.sb_lenPass);
                Integer passwordLength = sbPasswordLength.getProgress() + MIN_PASSWORD_LENGTH;

                passwordList.add(msg.getData().get("password").toString());
                progressDialog.setProgress(passwordList.size());
                if(passwordList.size() == passwordCount.intValue()) {
                    progressDialog.dismiss();
                    showPasswordDialog(passwordList.toArray(new String[passwordList.size()]));
                    passwordList = new LinkedList<>();
                }
                return false;
            }
        });
    }



    public void generatePasswordByThreads() {
        SeekBar sbPasswordCount = findViewById(R.id.sb_numOfPassCount);
        SeekBar sbLengthOfPassword = findViewById(R.id.sb_lenPass);

        Integer passwordCount = sbPasswordCount.getProgress() + MIN_PASSWORD_COUNT;
        Integer passwordLength = sbLengthOfPassword.getProgress() + MIN_PASSWORD_LENGTH;
        progressDialog.setProgress(0);
        progressDialog.setMax(passwordCount);
        progressDialog.show();

        ExecutorService executor = Executors.newFixedThreadPool(MAX_POOL_COUNT);
        for(int i=0; i<passwordCount; i++) {
            executor.execute(new ThreadGenerator(passwordLength));
        }
    }

    public void generatePasswordByAsync() {
        SeekBar sbPasswordCount = findViewById(R.id.sb_numOfPassCount);
        SeekBar sbLengthOfPassword = findViewById(R.id.sb_lenPass);

        Integer passwordCount = sbPasswordCount.getProgress() + MIN_PASSWORD_COUNT;
        Integer passwordLength = sbLengthOfPassword.getProgress() + MIN_PASSWORD_LENGTH;

        progressDialog.setProgress(0);
        progressDialog.setMax(passwordCount);
        progressDialog.show();
        new BackgroundGenerator().execute(passwordCount, passwordLength);
    }

    public void showPasswordDialog(String[] result) {
        final String[] options = result;

        AlertDialog.Builder builder = new AlertDialog.Builder(PasswordGenerator.this)
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView textView = findViewById(R.id.tv_password);
                        textView.setText(options[i]);
                    }
                }).setCancelable(false);
        builder.create().show();
    }

    private class BackgroundGenerator extends AsyncTask<Integer, Integer, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(Integer... length) {
            Integer passwordCount = length[0];
            Integer passwordLength = length[1];

            String[] result = new String[passwordCount];

            for(int i=0; i<passwordCount; i++) {
                result[i] = Generator.getPassword(passwordLength);
                publishProgress(i);
            }
            progressDialog.dismiss();
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
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
