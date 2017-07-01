package eventme.eventme;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



public class showEvent extends AppCompatActivity {

    private Button date,time,location,EventName;
    private TextView description;
    private ImageView image;
    private String temp;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        Intent mIntent = getIntent();

        description=(TextView) findViewById(R.id.Description);

        date = (Button) findViewById(R.id.date);

       // location = (Button) findViewById(R.id.location);

        EventName = (Button) findViewById(R.id.ename);
        EventName.setText(mIntent.getStringExtra("Name"));
        date.setText(mIntent.getStringExtra("Date")+"  "+ mIntent.getStringExtra("Time"));
       // location.setText(mIntent.getStringExtra("Location"));
        description.setMovementMethod(new ScrollingMovementMethod());   //description scrolls down (shows 4lines)

        description.setText(mIntent.getStringExtra("Description"));


        text = (TextView) findViewById(R.id.name);
        temp =mIntent.getStringExtra("Email");
        takeUserName(); //sets username in textview under image

        image = (ImageView) findViewById(R.id.imageView);


        String tempForImageRetreival=mIntent.getStringExtra("Date");

        retrieveImage(temp,tempForImageRetreival);
        image.setAdjustViewBounds(true);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);



    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database

    }
    private void retrieveImage(String email,String date)
    {
        StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();
        StorageReference islandRef = mStorageRef.child(email+date.replace("/","")+".jpg");

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(islandRef)
                .into(image);

    }

    public void goToShop(View v)
    {
        Intent intent = new Intent(showEvent.this, Profile.class);
        intent.putExtra("email",temp);
        intent.putExtra("yourprofile",false);
        startActivity(intent);
    }

    private void takeUserName(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference().child("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    User a= ds.getValue(User.class);
                    if(a.getEmail().equals(temp.replace(".",",")))
                    {
                        text.setText(a.getUsername());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

}
