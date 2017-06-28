package eventme.eventme;

import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;


public class Homepage extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private ListView list;
    private ArrayList<Event> events=new ArrayList<>();

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = preferences.getString("email", "");


        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {

                mDrawerLayout.closeDrawers();
                if (item.getItemId() == R.id.nav_login)
                {
                    Intent intent = new Intent(Homepage.this, LoginActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout)
                {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email","");
                    editor.apply();
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);

                }
                else if(item.getItemId()==R.id.nav_profile)
                {
                    Intent intent = new Intent(Homepage.this, Profile.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        if(!email.equals(""))
        {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
        }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email","");
            editor.apply();
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            Intent intent = new Intent(Homepage.this, profileEdit.class);
            startActivity(intent);



        //title θα μπει η arraylist με τα μαγαζια , imageId οι φωτο από τα μαγαζιά
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();

        ref.addChildEventListener(new ChildEventListener()  {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                events.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Event e=ds.getValue(Event.class);
                    events.add(e);

                }
                CustomList adapter = new CustomList(Homepage.this,events);
                list.setAdapter(adapter);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                events.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Event e=ds.getValue(Event.class);
                    events.add(e);

                }
                CustomList adapter = new CustomList(Homepage.this,events);
                list.setAdapter(adapter);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        list=(ListView)findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(Homepage.this, showEvent.class);
                intent.putExtra("Date", events.get(position).getDate());
                intent.putExtra("Time", events.get(position).getTime());
                intent.putExtra("Location", events.get(position).getLocation());
                intent.putExtra("Description", events.get(position).getDescription());
                intent.putExtra("Email", events.get(position).getemail());
                startActivity(intent);

            }

        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if(resultCode == 1){
                events.add((Event)data.getSerializableExtra("event"));
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database


    }

}
