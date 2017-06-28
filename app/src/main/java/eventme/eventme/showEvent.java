package eventme.eventme;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class showEvent extends AppCompatActivity {
    private  Animation animAlpha;
    private Button date,time,location;
    private TextView description;
    private String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        Intent mIntent = getIntent();

        description=(TextView) findViewById(R.id.Description);

        date = (Button) findViewById(R.id.date);

        time = (Button) findViewById(R.id.hour);

        location = (Button) findViewById(R.id.location);

        time.setText(mIntent.getStringExtra("Time"));
        date.setText(mIntent.getStringExtra("Date"));
        location.setText(mIntent.getStringExtra("Location"));
        description.setText("Description");

        TextView text = (TextView) findViewById(R.id.name);
        temp =mIntent.getStringExtra("Email");
        text.setText(temp);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database

    }

    public void goToShop(View v)
    {
       // Intent intent = new Intent(showEvent.this, shop_profile.class);
        //intent.putExtra("profile",temp);
        //startActivity(intent);
    }
}
