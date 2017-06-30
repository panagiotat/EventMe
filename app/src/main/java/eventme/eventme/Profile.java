package eventme.eventme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private String email2;
    private ImageView buttonUploadImage ;
    private boolean yourprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (TextView) findViewById(R.id.onoma);
        Intent intent=getIntent();
        email2 = intent.getStringExtra("email");
        yourprofile=intent.getBooleanExtra("yourprofile",true);
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

        buttonUploadImage = (ImageView) findViewById(R.id.photo_magaziou);
        buttonUploadImage.setAdjustViewBounds(true);
        buttonUploadImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // EDW MPAINEI TO FIX ENABLED VISIBLE BUTTON LOGOUT KAI NEW EVENT
        if(yourprofile)
        {
            findViewById(R.id.grammoula).setVisibility(View.VISIBLE);
            findViewById(R.id.logout).setVisibility(View.VISIBLE);
            findViewById(R.id.add_event).setVisibility(View.VISIBLE);
            findViewById(R.id.editProfilebtn).setVisibility(View.VISIBLE);
        }
        else
        {
            findViewById(R.id.grammoula).setVisibility(View.INVISIBLE);
            findViewById(R.id.logout).setVisibility(View.INVISIBLE);
            findViewById(R.id.add_event).setVisibility(View.INVISIBLE);
            findViewById(R.id.editProfilebtn).setVisibility(View.INVISIBLE);
        }


        email = (TextView) findViewById(R.id.email_user);

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
