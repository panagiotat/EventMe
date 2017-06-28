package eventme.eventme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private String email2;

    private SharedPreferences preferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        email2 = preferences.getString("email", "");

        name = (TextView) findViewById(R.id.onoma);
        email = (TextView) findViewById(R.id.email_user);

        name.setText("sdgsdgs");
        email.setText(email2);

    }
    public void newEvent(View v)
    {
        Intent intent = new Intent(Profile.this, NewEvent.class);
        startActivity(intent);
    }

    public void editProfile(View view)
    {
        String current_name;
        String current_email;

        current_name = name.getText().toString();
        current_email = email.getText().toString();

        Intent intent = new Intent(Profile.this, profileEdit.class);

        intent.putExtra("stringname",current_name);
        intent.putExtra("stringemail",current_email);

        startActivity(intent);
    }


}
