package eventme.eventme;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class shop_profile extends AppCompatActivity {

    private ShopDatabase shops;
    private RatingBar ratingBar;
    private String emailmagaziou;
    private TextView MO;
    private SharedPreferences preferences;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_profile);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        email = preferences.getString("email", "");
        shops = new ShopDatabase(this);

        Intent intent = getIntent();
        emailmagaziou = intent.getExtras().getString("profile");
        addListenerOnRatingBar();

        TextView om = (TextView)findViewById(R.id.onoma_magaziou);
        TextView dm = (TextView)findViewById(R.id.perigrafi_magaziou);
        TextView pm = (TextView)findViewById(R.id.til_magaziou);
        TextView lm = (TextView)findViewById(R.id.dieuthynsi_magaziou);
        TextView em = (TextView)findViewById(R.id.email_magaziou);
        TextView fbmm = (TextView)findViewById(R.id.fb_magaziou);
        TextView im = (TextView)findViewById(R.id.insta_magaziou);

        TextView MO = (TextView) findViewById(R.id.bathmologia);
        MO.setText("Bαθμολογία: "+new DecimalFormat("##.#").format(shops.getBathmologia(emailmagaziou))+"/4");
        om.setText(shops.getName(emailmagaziou));
        dm.setText(shops.getDescription(emailmagaziou));
        pm.setText(shops.getPhone(emailmagaziou));
        lm.setText(shops.getLocation(emailmagaziou));
        em.setText(shops.getEmail(emailmagaziou));
        fbmm.setText(shops.getFb(emailmagaziou));
        im.setText(shops.getInsta(emailmagaziou));

    }
    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        if(email.equals(""))
        {
            ratingBar.setVisibility(View.INVISIBLE);
        }
        else
        {
            ratingBar.setVisibility(View.VISIBLE);
            //if rating value is changed,
            //display the current rating value in the result (textview) automatically
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                public void onRatingChanged(RatingBar ratingBar, float rating,
                                            boolean fromUser) {
                    shops.setBathmologia(emailmagaziou,ratingBar.getRating());

                }
            });
        }


    }
}
