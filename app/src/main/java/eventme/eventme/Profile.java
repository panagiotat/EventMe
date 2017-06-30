package eventme.eventme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference().child("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    User a= ds.getValue(User.class);
                    Log.i("TAGGG",a.getUsername());
                    if(a.getEmail().equals(email2.replace(".",",")))
                    {
                        name.setText(a.getUsername());

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
    public void logOut(View view)
    {

        Intent intent = new Intent(this,Homepage.class);
        intent.putExtra("email","");
        startActivity(intent);

    }


}
