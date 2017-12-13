package ntweb.info.dietproj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ShowCurrentBodyType extends AppCompatActivity {
    LinearLayout ll1,ll2,ll3,ll4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_current_body_type);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int body = sharedPref.getInt("body", 0);

        if (body == 1)
            ll1.setVisibility(View.VISIBLE);
        else if (body == 2)
            ll2.setVisibility(View.VISIBLE);
        else if (body == 3)
            ll3.setVisibility(View.VISIBLE);
        else if (body == 4)
            ll4.setVisibility(View.VISIBLE);
    }

    public void done(View v)
    {
        // Go to BodyShapeDetector Activity
        Intent userInfoActivity = new Intent(getBaseContext(), MainActivity.class);
        startActivity(userInfoActivity);
        finish();
    }
}
