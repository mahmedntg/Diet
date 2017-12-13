package ntweb.info.dietproj;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DietInfoActivity extends AppCompatActivity {
    ListView listView;
    private String[] dietArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_info);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int body = sharedPref.getInt("body", 0);
        if (body == 1) {
            dietArray = getResources().getStringArray(R.array.diet1);
        } else if (body == 2) {
            dietArray = getResources().getStringArray(R.array.diet2);
        }
        if (body == 3) {
            dietArray = getResources().getStringArray(R.array.diet3);
        }
        if (body == 4) {
            dietArray = getResources().getStringArray(R.array.diet4);
        }

        listView = (ListView) findViewById(R.id.dietInfoList);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.diet_layout, R.id.dietText, dietArray);
        listView.setAdapter(adapter);
    }
}
