package ntweb.info.dietproj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UserInfotmation extends AppCompatActivity {

    // user interface component
    EditText etName, etWeight, etAge, etGoal, etHeight, etUserName, etPassword;
    RadioButton rbMale, rbFemale;
    TextView tvUserName, tvPassword;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infotmation);

        //getSupportActionBar().setTitle(getString(R.string.user_information));

        // get saved information (if available)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = sharedPref.getString("name", "");
        String weight = sharedPref.getString("weight", "");
        String height = sharedPref.getString("height", "");
        String age = sharedPref.getString("age", "");
        String goal = sharedPref.getString("goal", "");
        int gender = sharedPref.getInt("gender", 0);
        String userName = sharedPref.getString("userName", "");

        //get reference to UI component
        etName = (EditText) findViewById(R.id.etName);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etAge = (EditText) findViewById(R.id.etAge);
        etGoal = (EditText) findViewById(R.id.etGoal);
        etHeight = (EditText) findViewById(R.id.etHeight);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvPassword = (TextView) findViewById(R.id.tvPassword);

        if (!TextUtils.isEmpty(userName)) {
            etUserName.setVisibility(View.GONE);
            etPassword.setVisibility(View.GONE);
            tvUserName.setVisibility(View.GONE);
            tvPassword.setVisibility(View.GONE);
        }
        // set saved data to the foarm
        etName.setText(name);
        etWeight.setText(weight + "");
        etAge.setText(age + "");
        etGoal.setText(goal + "");
        etHeight.setText(height + "");
        if (gender == 1)
            rbMale.setChecked(true);
        if (gender == 2)
            rbFemale.setChecked(true);


    }

    // when user click on save button!
    public void save(View v) {
        String userName = sharedPref.getString("userName", "");
        String password = sharedPref.getString("password", "");
        if (etName.getText().toString().length() < 1 || etWeight.getText().toString().length() <= 0 ||
                etAge.getText().toString().length() <= 0 || etGoal.getText().toString().length() <= 0 ||
                etHeight.getText().toString().length() <= 0) {
            if (TextUtils.isEmpty(userName) &&
                    (etUserName.getText().toString().length() < 1 || etPassword.getText().toString().length() < 1))
                Toast.makeText(getBaseContext(), "All Fields are requied!", Toast.LENGTH_SHORT).show();
        } else {

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("name", etName.getText().toString());
            editor.putString("weight", etWeight.getText().toString());
            editor.putString("height", etHeight.getText().toString());
            editor.putString("age", etAge.getText().toString());
            editor.putString("goal", etGoal.getText().toString());
            editor.putString("userName", TextUtils.isEmpty(userName)?etUserName.getText().toString():userName);
            editor.putString("password", TextUtils.isEmpty(password)? etPassword.getText().toString() :password);

            int sGender = 1;
            if (rbFemale.isChecked())
                sGender = 2;
            editor.putInt("gender", sGender);
            editor.commit();

            // Go to BodyShapeDetector Activity
            int body = sharedPref.getInt("body", 0);

            // Go to BodyShapeDetector Activity if there are no body typed saved
            if (body == 0) {
                Intent userInfoActivity = new Intent(getBaseContext(), BodyShapeDetect.class);
                startActivity(userInfoActivity);
                finish();
            } else {
                Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        }
    }
}
