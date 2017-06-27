package eventme.eventme;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;

/**
 * Created by Παντελής on 12/2/2017.
 */

public class ShopDatabase {
    static final String DATABASE_NAME = "shop.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"SHOP"+
            "( " +"ID"+" integer primary key autoincrement,"+ "EMAIL  text,NAME text,DESCRIPTION text,PHONE text,LOCATION text, FB text, INSTA text, PIC BLOB, STARS INTEGER,VOTERS INTEGER); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelperShop dbHelper;
    public  ShopDatabase(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelperShop(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  ShopDatabase open() throws SQLException
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

    //FUNCTIONS
    //GETTERS
    public String getName(String email)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SHOP", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String name= cursor.getString(cursor.getColumnIndex("NAME"));
        cursor.close();
        db.close();
        return name;
    }

    public float getBathmologia(String email)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SHOP", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return 0;
        }
        cursor.moveToFirst();
        float stars= cursor.getInt(cursor.getColumnIndex("STARS"));
        float voters= cursor.getInt(cursor.getColumnIndex("VOTERS"));
        cursor.close();
        db.close();
        if(voters<1)
        {
            voters=1;
        }
        return stars/voters;
    }

    public String getDescription(String email)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SHOP", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String desc= cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
        cursor.close();
        db.close();
        return desc;
    }
    public String getPhone(String email)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SHOP", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String phone= cursor.getString(cursor.getColumnIndex("PHONE"));
        cursor.close();
        db.close();
        return phone;
    }

    public String getEmail(String email)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SHOP", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String e= cursor.getString(cursor.getColumnIndex("EMAIL"));
        cursor.close();
        db.close();
        return e;
    }
    public String getLocation(String email)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SHOP", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String l= cursor.getString(cursor.getColumnIndex("LOCATION"));
        cursor.close();
        db.close();
        return l;
    }
    public String getFb(String email)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SHOP", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String fb= cursor.getString(cursor.getColumnIndex("FB"));
        cursor.close();
        db.close();
        return fb;
    }
    public String getInsta(String email)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SHOP", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String insta= cursor.getString(cursor.getColumnIndex("INSTA"));
        cursor.close();
        db.close();
        return insta;
    }
    public Bitmap getPic(String email)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SHOP", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();
        byte[] pic= cursor.getBlob(cursor.getColumnIndex("PIC"));
        cursor.close();
        db.close();
        DbBitmapUtility a =new DbBitmapUtility();
        return a.getImage(pic);
    }

    public void insertEntry(String name,String description,String phone,String email,String location,String fb,String insta,Bitmap pic)
    {
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("DESCRIPTION", description);
        newValues.put("NAME", name);
        newValues.put("PHONE",phone);
        newValues.put("EMAIL", email);
        newValues.put("LOCATION",location);
        newValues.put("FB",fb);
        newValues.put("INSTA",insta);
        newValues.put("STARS",0);
        newValues.put("VOTERS",0);
        DbBitmapUtility a =new DbBitmapUtility();
        newValues.put("PIC",a.getBytes(pic));
        // Insert the row into your table
        db.insert("SHOP", null, newValues);
        db.close();
    }
    public void  updateEntry(String name,String description,String phone,String email,String location,String fb,String insta,Bitmap pic)
    {
        db=dbHelper.getWritableDatabase();
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("DESCRIPTION", description);
        updatedValues.put("NAME", name);
        updatedValues.put("PHONE",phone);
        updatedValues.put("EMAIL", email);
        updatedValues.put("LOCATION",location);
        updatedValues.put("FB",fb);
        updatedValues.put("INSTA",insta);
        DbBitmapUtility a =new DbBitmapUtility();
        updatedValues.put("PIC",a.getBytes(pic));
        String where="EMAIL = ?";
        db.update("SHOP",updatedValues, where, new String[]{email});
        db.close();
    }
    public void setBathmologia(String email,float star)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SHOP", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // Email Not Exist
        {
            cursor.close();
        }
        cursor.moveToFirst();
        star=star+ cursor.getInt(cursor.getColumnIndex("STARS"));
        int voters=1+cursor.getInt(cursor.getColumnIndex("VOTERS"));
        cursor.close();
        db.close();
        db=dbHelper.getWritableDatabase();
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("STARS",(int)star );
        updatedValues.put("VOTERS",voters);
        String where="EMAIL = ?";
        db.update("SHOP",updatedValues, where, new String[]{email});
        db.close();
    }
}
