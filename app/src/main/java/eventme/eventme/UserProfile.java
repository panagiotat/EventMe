package eventme.eventme;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {
    private ImageButton buttonUploadImage;
    private TextView FirstName;
    private TextView LastName;
    private EditText oldPassword;
    private EditText newPassword;
    private Button ChangePassword;
    private String email;
    private LoginDataBaseAdapter loginDataBaseAdapter;
    private Button save;
    private SharedPreferences preferences;
    private static int RESULT_LOAD_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        email = preferences.getString("email", "");
        oldPassword=(EditText)findViewById(R.id.oldPassword);
        oldPassword.setVisibility(View.INVISIBLE);
        newPassword=(EditText)findViewById(R.id.newPassword);
        newPassword.setVisibility(View.INVISIBLE);
        save = (Button)findViewById(R.id.savePassword);
        save.setVisibility(View.INVISIBLE);

        buttonUploadImage = (ImageButton) findViewById(R.id.userIcon);
        buttonUploadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }

        });

        FirstName= (TextView) findViewById(R.id.firstName);
        LastName=(TextView) findViewById(R.id.LastName);
        oldPassword=(EditText) findViewById(R.id.oldPassword);
        newPassword=(EditText) findViewById(R.id.newPassword);
        ChangePassword=(Button) findViewById(R.id.changePassword);
        FirstName.setText(loginDataBaseAdapter.getFirstName(email).toString());
        LastName.setText(loginDataBaseAdapter.getLastName(email).toString());


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
            buttonUploadImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }
    public void SaveChange(View w ){

        TextView oldPassword=(TextView)findViewById(R.id.oldPassword);
        oldPassword.setVisibility(View.VISIBLE);
        TextView newPassword=(TextView)findViewById(R.id.newPassword);
        newPassword.setVisibility(View.VISIBLE);
        Button save = (Button)findViewById(R.id.savePassword);
        save.setVisibility(View.VISIBLE);

    }
    public void Changecomplete(View w){
        String s1;
        EditText palioskodikos = (EditText) findViewById(R.id.oldPassword);
        s1 = palioskodikos.getText().toString();

        String s2;
        EditText kainourgioskodikos = (EditText) findViewById(R.id.newPassword);
        s2 = kainourgioskodikos.getText().toString();
        TextView oldPassword=(TextView)findViewById(R.id.oldPassword);
        oldPassword.setVisibility(View.INVISIBLE);
        TextView newPassword=(TextView)findViewById(R.id.newPassword);
        newPassword.setVisibility(View.INVISIBLE);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String storedPassword=loginDataBaseAdapter.getSinlgeEntry(email);


            if(s1.equals(storedPassword))
            {

                loginDataBaseAdapter.updateEntry(email,s2,FirstName.getText().toString(),LastName.getText().toString());

            }
            else
            {
                Toast.makeText(this, "Ο παλιός κωδικός δεν είναι σωστός", Toast.LENGTH_SHORT).show();
            }
        Toast.makeText(this, "Αποθήκευση Ολοκληρώθηκε", Toast.LENGTH_SHORT).show();
        save.setVisibility(View.INVISIBLE);
    }

}
