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

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;


public class Homepage extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private ListView list;
    private ArrayList<Event> events;
    private EventDatabase eventdb;
    private LoginDataBaseAdapter users;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = preferences.getString("email", "");
        eventdb=new EventDatabase(this);
        eventdb=eventdb.open();
        users=new LoginDataBaseAdapter(this);
        users=users.open();
        events=new ArrayList();
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
                    Intent intent = new Intent(Homepage.this, UserProfile.class);
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
        if(users.isowner(email))
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email","");
            editor.apply();
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            Intent intent = new Intent(Homepage.this, editProfile.class);
            startActivity(intent);
        }
        events=eventdb.getRows();
        //title θα μπει η arraylist με τα μαγαζια , imageId οι φωτο από τα μαγαζιά
        String[] title=new String[events.size()];
        Bitmap[] imageId = new Bitmap[events.size()];

        for(int i=0;i<events.size();i++)
        {
            title[i]=events.get(i).getDescription();
            imageId[i]=events.get(i).getImage();
        }

        CustomList adapter = new CustomList(Homepage.this, title, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(Homepage.this, showEvent.class);
                intent.putExtra("MyClass", position);
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
        eventdb.close();
        users.close();
    }

}
