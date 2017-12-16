package ntweb.info.dietproj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvName,tvWeight,tvGoal,tvBody,tvBMI ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // get reference to UI component
        tvName = (TextView) findViewById(R.id.tvName);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvGoal =  (TextView) findViewById(R.id.tvGoal);
        tvBody =  (TextView) findViewById(R.id.tvBody);
        tvBMI = (TextView) findViewById(R.id.tvBMI);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int gender = sharedPref.getInt("gender", 0);
        int body = sharedPref.getInt("body", 0);
        String name = sharedPref.getString("name","NO NAME");
        String weight = sharedPref.getString("weight","0");
        String goal = sharedPref.getString("goal","0");
        String height = sharedPref.getString("height","0");
        String bodyType = "";

        // Check saved data,, to determine if this is the first running time to app
        // thats mean first install .... go to setting page
        if (gender == 0)
        {
            Intent userInfoActivity = new Intent(getBaseContext(), UserInfotmation.class);
            startActivity(userInfoActivity);
            finish();
        }

        else if (body == 0)
        {
            Intent userInfoActivity = new Intent(getBaseContext(), BodyShapeDetect.class);
            startActivity(userInfoActivity);
            finish();
        }
        else if (body == 1)
        {
            bodyType = getString(R.string.banana);
        }
        else if (body == 2)
        {
            bodyType = getString(R.string.apple);
        }
        else if (body == 3)
        {
            bodyType = getString(R.string.pear);
        }
        else if (body == 4)
        {
            bodyType = getString(R.string.hourGlass);
        }


        tvName.setText(name);
        tvWeight.setText("الوزن: "  + weight);
        tvGoal.setText("الهدف: " + goal );
        tvBody.setText("شكل الجسم: " + bodyType);
        float bmi = CalculateBMI(Float.parseFloat(weight), Float.parseFloat(height));
        tvBMI.setText("BMI: " + String.format("%.02f", bmi));


    }


    private float CalculateBMI(float weight, float height)
    {
        return (weight / ((height/100)*(height/100)));
    }

    public void changeInfo(View v)
    {
        Intent userInfoActivity = new Intent(getBaseContext(), UserInfotmation.class);
        startActivity(userInfoActivity);
        finish();
    }

    public void changeBody(View v)
    {
        Intent userInfoActivity = new Intent(getBaseContext(), BodyShapeDetect.class);
        startActivity(userInfoActivity);
        finish();
    }
    public void dietInfo(View v)
    {
        Intent dietInfoActivity = new Intent(getBaseContext(), DietInfoActivity.class);
        startActivity(dietInfoActivity);
        finish();
    }

}
