package eventme.eventme;
import android.graphics.Bitmap;



/**
 * Created by andreas agapitos on 11-Jan-17.
 */

public class Event {
    private String email,date,time,location,description;
    public Event()
    {}
    public Event(String e,String d, String t, String l, String de) //Constructor
    {

        date=d;
        time=t;
        location=l;
        description=de;
        email=e;
    }

    public String getemail()
    {
        return email;
    }
    public String getDate()
    {
        return date;
    }
    public String getLocation()
    {
        return location;
    }
    public String getDescription()
    {
        return description;
    }
    public String getTime()
    {
        return time;
    }
}

