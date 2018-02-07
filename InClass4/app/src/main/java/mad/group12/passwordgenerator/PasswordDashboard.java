/**
 * Inclass Assignment 4
 * File name: PasswordDashboard.java - launcher screen activity
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
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
        setContentView(R.layout.activity_password_dashboard); //set layout to launcher screen layout file
    }

    @Override
    protected void onStart() {
        super.onStart();
        //set delay of 3 sec before launching PasswordGenerator activity
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), PasswordGenerator.class);
                startActivity(intent);
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
    }
}
