package eventme.eventme;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

public class editProfile extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private ShopDatabase shops;
    private int[] emptyFields;
    private ImageButton changePic;
    private EditText name;
    private EditText desc;
    private EditText phone;
    private TextView email;
    private EditText location;
    private EditText fb;
    private EditText insta;
    private SharedPreferences preferences;
    private String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        emptyFields = new int[8];
        for (int i = 0; i < 7; i++)
            emptyFields[i]=0; //if 0 : empty field and hint appears

        //create and open db
        shops=new ShopDatabase(this);

        //user's email
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mail = preferences.getString("email","");

        changePic=(ImageButton) findViewById(R.id.photo_magaziou);
        name=(EditText) findViewById(R.id.onoma_magaziou);
        desc=(EditText) findViewById(R.id.perigrafi_magaziou);
        phone=(EditText) findViewById(R.id.til_magaziou);
        email=(TextView) findViewById(R.id.email_magaziou);
        location=(EditText) findViewById(R.id.dieuthynsi_magaziou);
        fb=(EditText) findViewById(R.id.fb_magaziou);
        insta=(EditText) findViewById(R.id.insta_magaziou);

        if(emptyFields[0]!=0)
            changePic.setImageBitmap(shops.getPic(mail));
       // if(emptyFields[1]!=0)
        if(!shops.getName(mail).equals("NOT EXIST"))
            name.setText(shops.getName(mail));
       // if(emptyFields[2]!=0)
        if(!shops.getDescription(mail).equals("NOT EXIST"))
            desc.setText(shops.getDescription(mail));
       // if(emptyFields[3]!=0)
        if(!shops.getPhone(mail).equals("NOT EXIST"))
            phone.setText(shops.getPhone(mail));
        //if(emptyFields[4]!=0)
            email.setText(mail);
      //  if(emptyFields[5]!=0)
        if(!shops.getLocation(mail).equals("NOT EXIST"))
            location.setText(shops.getLocation(mail));
       // if(emptyFields[6]!=0)
        if(!shops.getFb(mail).equals("NOT EXIST"))
            fb.setText(shops.getFb(mail));
      //  if(emptyFields[7]!=0)
        if(!shops.getInsta(mail).equals("NOT EXIST"))
            insta.setText(shops.getInsta(mail));
    }

    public void changePhoto(View v){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            changePic.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }

    public void saveChanges(View v){
        String onoma, tilefwno, emailtext, perigrafitext, locationtext , fbtext , instatext;
        Bitmap imageForSave;
        boolean onomab=true,tilefwnob,emailb,perigrafib,locationb;
        tilefwnob=emailb=perigrafib=locationb=onomab;

        /*if(emptyFields[0]==0) {imageForSave=((BitmapDrawable)changePic.getDrawable()).getBitmap();}
        else {imageForSave=changePic}
*/

        onoma = name.getText().toString();
        if (onoma.matches("")) {
            onomab=false;
        }

        tilefwno = phone.getText().toString();
        if (tilefwno.matches("")) {
            tilefwnob=false;

        }


        emailtext =mail;
        if (emailtext.matches("")) {
            emailb=false;

        }

        perigrafitext = desc.getText().toString();
        if (perigrafitext.matches("")) {
            perigrafib=false;

        }

        locationtext = location.getText().toString();
        if (locationtext.matches("")) {
            locationb=false;

        }


        fbtext = fb.getText().toString();
        instatext = insta.getText().toString();


        if(onomab==false || tilefwnob==false || perigrafib==false || emailb==false || locationb==false)
        {
            Toast.makeText(this, "Συμπληρώστε όλα τα υποχρεωτικά πεδία", Toast.LENGTH_SHORT).show();
        }
        else
        {

        }


        if(shops.getName(mail).equals("NOT EXIST"))
        {
            shops.insertEntry(onoma,perigrafitext, tilefwno, emailtext, locationtext, fbtext, instatext,((BitmapDrawable)changePic.getDrawable()).getBitmap());
            Toast.makeText(this, "Αποθήκευση Ολοκληρώθηκε", Toast.LENGTH_SHORT).show();
        }
        else
        {
            shops.updateEntry(onoma,perigrafitext, tilefwno, emailtext,locationtext, fbtext, instatext,((BitmapDrawable)changePic.getDrawable()).getBitmap());
            Toast.makeText(this, "Αποθήκευση Ολοκληρώθηκε", Toast.LENGTH_SHORT).show();
        }

    }
    public void newEvent(View v)
    {
        Intent intent = new Intent(editProfile.this, NewEvent.class);

        startActivity(intent);
    }

    public void logout(View v)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email","");
        editor.apply();
        Intent intent = new Intent(editProfile.this, Homepage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
