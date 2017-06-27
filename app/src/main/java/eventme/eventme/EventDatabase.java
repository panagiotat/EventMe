package eventme.eventme;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

/**
 * Created by andreas on 12-Feb-17.
 */

public class EventDatabase {
    static final String DATABASE_NAME = "events.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"EVENT"+
            "( " +"ID"+" integer primary key autoincrement,"+ "EMAIL  text,DATE text,TIME text,LOCATION text,DESCRIPTION text,PHOTO BLOB); ";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelperEvent dbHelper;
    public  EventDatabase(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelperEvent(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  EventDatabase open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String email, String date, String time, String location, String description,byte[]  photo)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("EMAIL", email);
        newValues.put("DATE",date);
        newValues.put("TIME", time);
        newValues.put("LOCATION",location);
        newValues.put("DESCRIPTION",description);
        newValues.put("PHOTO",photo);
        // Insert the row into your table
        db.insert("EVENT", null, newValues);
    }
    public ArrayList<Event> getRows() {
        ArrayList<Event> list = new ArrayList<>();
        Cursor cursor = db.query("EVENT", null,null, null, null, null, null);
        // looping through all rows and adding to list
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                Event e = new Event(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), new DbBitmapUtility().getImage(cursor.getBlob(6)));
                list.add(e);
            }
        }
        return list;
    }
}
