package mad.group12.passwordgenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class PasswordDashboard extends Activity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_password_dashboard);
    }

    @Override
    protected void onStart() {
        super.onStart();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), PasswordGenerator.class);
                startActivity(intent);
            }
        }, 3000);
    }
}
