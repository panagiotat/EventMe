package eventme.eventme;

import com.google.firebase.database.DatabaseReference;


/**
 * Created by kyriakos on 28-Jun-17.
 */

public class Task {
    private DatabaseReference database;
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
