package ntweb.info.dietproj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etUserName, etPassword;
    SharedPreferences sharedPref;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (!TextUtils.isEmpty(sharedPref.getString("userName", ""))) {
            registerBtn.setVisibility(View.GONE);
        }
    }

    public void login(View view) {
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String registeredUserName = sharedPref.getString("userName", "");
        String registerePassword = sharedPref.getString("password", "");
        if (userName.length() < 1 || password.length() < 1) {
            Toast.makeText(getBaseContext(), "All Fields are requied!", Toast.LENGTH_SHORT).show();
        } else if (!userName.equals(registeredUserName) || !password.equals(registerePassword)) {
            Toast.makeText(getBaseContext(), "This User not exists", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void register(View view) {
        startActivity(new Intent(this, UserInfotmation.class));
    }
}
