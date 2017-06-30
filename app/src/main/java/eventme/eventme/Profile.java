package eventme.eventme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private boolean yourprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (TextView) findViewById(R.id.onoma);
        Intent intent=getIntent();
        email2 = intent.getStringExtra("email");
        yourprofile=intent.getBooleanExtra("yourprofile",false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference().child("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    User a= ds.getValue(User.class);
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
        email = (TextView) findViewById(R.id.email_user);
        email.setText(email2);

        if(!yourprofile)
        {
            Button logout=(Button) findViewById(R.id.logout);
            Button new_event=(Button) findViewById(R.id.add_event);
            logout.setEnabled(false);
            logout.setVisibility(View.INVISIBLE);
            findViewById(R.id.grammoula).setVisibility(View.INVISIBLE);
            new_event.setEnabled(false);
            new_event.setVisibility(View.INVISIBLE);
        }
        else
        {
            Button logout=(Button) findViewById(R.id.logout);
            Button new_event=(Button) findViewById(R.id.add_event);
            logout.setEnabled(true);
            new_event.setEnabled(true);
            logout.setVisibility(View.VISIBLE);
            findViewById(R.id.grammoula).setVisibility(View.VISIBLE);
            new_event.setVisibility(View.VISIBLE);
        }

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
