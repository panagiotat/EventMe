package eventme.eventme;
/**
 * Created by kyriakos on 11-Feb-17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "EMAIL text,PASSWORD text,NAME text,SURNAME text,ISOWNER text); ";
    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
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

    public void insertEntry(String email,String password,String name,String surname,String isowner)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("EMAIL", email);
        newValues.put("PASSWORD",password);
        newValues.put("NAME", name);
        newValues.put("SURNAME",surname);
        newValues.put("ISOWNER",isowner);
        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
    }
    public String getSinlgeEntry(String email)
    {
        Cursor cursor=db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public String getFirstName(String email)
    {
        Cursor cursor=db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String name= cursor.getString(cursor.getColumnIndex("NAME"));
        cursor.close();
        return name;
    }
    public String getLastName(String email)
    {
        Cursor cursor=db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String surname= cursor.getString(cursor.getColumnIndex("SURNAME"));
        cursor.close();
        return surname;
    }

    public void  updateEntry(String email, String password, String name, String surname)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("EMAIL", email);
        updatedValues.put("PASSWORD",password);
        updatedValues.put("NAME", name);
        updatedValues.put("SURNAME",surname);
        String where="EMAIL = ?";
        db.update("LOGIN",updatedValues, where, new String[]{email});
    }

    public boolean isowner(String email){
        Cursor cursor=db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return false;
        }
        cursor.moveToFirst();

        String k= cursor.getString(cursor.getColumnIndex("ISOWNER"));
        if(k.equals("true"))
        {
            return true;
        }else
        {return false;}



    }
}
