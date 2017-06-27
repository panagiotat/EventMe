package eventme.eventme;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


/**
 * Created by andreas agapitos on 11-Jan-17.
 */

public class Event {
    private Bitmap image;
    private String e,date,time,location,description;
    public  Event(String email,String d, String t, String l, String de,Bitmap im) //Constructor
    {
        image=im;
        date=d;
        time=t;
        location=l;
        description=de;
        e=email;
    }

    public String getemail()
    {
        return e;
    }
    public Bitmap getImage()
    {
        return image;
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

