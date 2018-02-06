package mad.group12.passwordgenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;

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
}
