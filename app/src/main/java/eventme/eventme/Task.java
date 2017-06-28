package eventme.eventme;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by kyriakos on 28-Jun-17.
 */

public class Task {
    private DatabaseReference database;
    ArrayList<Event> events=new ArrayList<>();
    public Task(DatabaseReference database) {
        this.database = database;
    }
    public void setEvent(Event s)
    {
        database.child("Event").push().setValue(s);
    }
    public void setImage(Event s)
    {
        database.child("Event").push().setValue(s);
    }
    public void setEmail(String s)
    {
        database.child("Event").push().setValue(s);
    }
    public void setTime(String s)
    {
        database.child("Event").push().setValue(s);
    }
    public void setDate(String s)
    {
        database.child("Event").push().setValue(s);
    }
    public void setLocation(String s)
    {
        database.child("Event").push().setValue(s);
    }
    public void setDescription(String s)
    {
        database.child("Event").push().setValue(s);
    }
    public void setPhone(String s)
    {
        database.child("Event").push().setValue(s);
    }

}
