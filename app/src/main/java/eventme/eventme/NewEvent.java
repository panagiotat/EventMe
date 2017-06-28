package eventme.eventme;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.EventLogTags;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class NewEvent extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private ImageButton buttonUploadImage;
    private Button DateButton,TimeButton,SaveTimeButton,LocationButton,SaveButton;
    private CalendarView calender;
    private DatabaseReference database;
    private TimePicker editTime;
    private EditText editText;
    private SharedPreferences preferences;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);





        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        email = preferences.getString("email", "");

        buttonUploadImage = (ImageButton) findViewById(R.id.UploadButton);
        DateButton=(Button) findViewById(R.id.DateButton);
        TimeButton=(Button) findViewById(R.id.TimeButton);
        SaveButton=(Button) findViewById(R.id.SaveBtn);
        LocationButton=(Button) findViewById(R.id.LocationButton);
        calender=(CalendarView)  findViewById(R.id.calendarView);
        calender.setVisibility(View.INVISIBLE);
        SaveTimeButton=(Button) findViewById(R.id.SaveButtonTime);
        SaveTimeButton.setVisibility(View.INVISIBLE);
        editTime=(TimePicker)findViewById(R.id.timePicker);
        editTime.setVisibility(View.INVISIBLE);
        buttonUploadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) { //Upload Image from Gallery

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                buttonUploadImage.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });
        editText=(EditText) findViewById(R.id.editText);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            buttonUploadImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }
    public void ClickDate(View v) // Click the button Date
    {
        DateButton.setEnabled(false);DateButton.setVisibility(View.INVISIBLE);
        TimeButton.setEnabled(false);TimeButton.setVisibility(View.INVISIBLE);
        SaveButton.setEnabled(false);SaveButton.setVisibility(View.INVISIBLE);
        LocationButton.setEnabled(false);LocationButton.setVisibility(View.INVISIBLE);
        editTime.setVisibility(View.INVISIBLE);
        calender.setVisibility(View.VISIBLE);
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                DateButton.setText(dayOfMonth +" / " + (month+1) + " / " + year);
                calender.setVisibility(View.INVISIBLE);
                DateButton.setEnabled(true);DateButton.setVisibility(View.VISIBLE);
                TimeButton.setEnabled(true);TimeButton.setVisibility(View.VISIBLE);
                LocationButton.setEnabled(true);LocationButton.setVisibility(View.VISIBLE);
                SaveButton.setEnabled(true);SaveButton.setVisibility(View.VISIBLE);

            }
        });
    }
    public void ClickLocation(View v) // Click the button Location
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewEvent.this);
        builder.setTitle("Τοποθεσία");
        final EditText input = new EditText(NewEvent.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocationButton.setText( input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    public void ClickTime(View v) // Click the button Time
    {
        DateButton.setEnabled(false);DateButton.setVisibility(View.INVISIBLE);
        TimeButton.setEnabled(false);TimeButton.setVisibility(View.INVISIBLE);
        SaveButton.setEnabled(false);SaveButton.setVisibility(View.INVISIBLE);
        LocationButton.setEnabled(false);LocationButton.setVisibility(View.INVISIBLE);
        SaveTimeButton.setEnabled(true);SaveTimeButton.setVisibility(View.VISIBLE);
        calender.setVisibility(View.INVISIBLE);
        editTime.setVisibility(View.VISIBLE);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void ClickSave(View v) // Click the button save
    {
            editTime.setVisibility(View.INVISIBLE);
            TimeButton.setText(editTime.getHour()+":"+editTime.getMinute());
            DateButton.setEnabled(true);DateButton.setVisibility(View.VISIBLE);
            TimeButton.setEnabled(true);TimeButton.setVisibility(View.VISIBLE);
            SaveButton.setEnabled(true);SaveButton.setVisibility(View.VISIBLE);
            LocationButton.setEnabled(true);LocationButton.setVisibility(View.VISIBLE);
            SaveTimeButton.setEnabled(false);SaveTimeButton.setVisibility(View.INVISIBLE);

    }
    public void Done(View v) // Click the button for share the event
    {
        database= FirebaseDatabase.getInstance().getReference();
        Task t=new Task(database);
        t.setEvent(new Event(email,DateButton.getText().toString(),TimeButton.getText().toString(),LocationButton.getText().toString(),editText.getText().toString()));//((BitmapDrawable)buttonUploadImage.getDrawable()).getBitmap()));
       // t.setTime(TimeButton.getText().toString());
        //t.setDate(DateButton.getText().toString());
        //t.setLocation(LocationButton.getText().toString());
        //t.setDescription(editText.getText().toString());
        //t.setPhone("");
        //t.setImage(((BitmapDrawable)buttonUploadImage.getDrawable()).getBitmap());
        Intent intent = new Intent(NewEvent.this,profileEdit.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database

    }
}
