package eventme.eventme;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class showEvent extends AppCompatActivity {
    private  Animation animAlpha;
    private Button date,time,location;
    private TextView description;
    private ArrayList<Event> events;
    private EventDatabase eventdb;
    private ShopDatabase shops;
    private String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        eventdb=new EventDatabase(this);
        eventdb=eventdb.open();
        events=eventdb.getRows();

        shops = new ShopDatabase(this);
        shops=shops.open();

        Intent mIntent = getIntent();
        int i = mIntent.getIntExtra("MyClass", 0);

        description=(TextView) findViewById(R.id.Description);
        animAlpha= AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
        date = (Button) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
            }
        });
        time = (Button) findViewById(R.id.hour);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
            }
        });
        location = (Button) findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
            }
        });
        time.setText(events.get(i).getTime());
        date.setText(events.get(i).getDate());
        location.setText(events.get(i).getLocation());
        description.setText(events.get(i).getDescription());

        TextView text = (TextView) findViewById(R.id.name);
        temp = events.get(i).getemail(); //prepei apo to email na vroume to onoma tou magaziou
        text.setText(shops.getName(temp));

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        eventdb.close();
        shops.close();
    }

    public void goToShop(View v)
    {
        Intent intent = new Intent(showEvent.this, shop_profile.class);
        intent.putExtra("profile",temp);
        startActivity(intent);
    }
}
