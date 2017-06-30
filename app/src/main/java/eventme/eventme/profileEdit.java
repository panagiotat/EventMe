package eventme.eventme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class profileEdit extends AppCompatActivity {

    private EditText et_name ;
    private EditText et_pass ;
    private EditText et_email;
    private ImageView buttonUploadImage ;
    private  static final int SELECT_PICTURE = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        et_name = (EditText) findViewById(R.id.etonoma);
        et_pass = (EditText) findViewById(R.id.etcode);
        et_email = (EditText) findViewById(R.id.etemail);

        buttonUploadImage = (ImageView) findViewById(R.id.userphoto);
        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                selectImage();
            }

        });

        Intent intent = getIntent();

        String cn = intent.getStringExtra("stringname");

        String ce = intent.getStringExtra("stringemail");

        et_name.setText(cn);

        et_email.setText(ce);
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                //Get ImageURi and load with help of picasso

                Picasso.with(profileEdit.this).load(data.getData()).centerInside().fit()
                        .into((ImageView) findViewById(R.id.userphoto));
            }
            //Bitmap bitmap = getPath(data.getData());
            //buttonUploadImage.setImageBitmap(bitmap);

        }
    }

    public void saveChanges(View view)
    {
        String newname = et_name.getText().toString();
        DatabaseReference change1= FirebaseDatabase.getInstance().getReference().child("Users").child("username");
        change1.setValue(newname);


        String newemail = et_email.getText().toString();

        DatabaseReference change2= FirebaseDatabase.getInstance().getReference().child("Users").child("email");
        change2.setValue(newemail);

        String newpass= et_pass.getText().toString();

        DatabaseReference change3= FirebaseDatabase.getInstance().getReference().child("Users").child("password");
        change3.setValue(newpass);
        Toast.makeText(profileEdit.this, "Done!",
                Toast.LENGTH_SHORT).show();
        this.finish();

    }



}
