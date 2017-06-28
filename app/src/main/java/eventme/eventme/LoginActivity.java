package eventme.eventme;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Owner;
import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText _email;
    private EditText _passwordText;
    private Button _loginButton;
    private TextView _signupLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // create a instance of SQLite Database


        _passwordText=(EditText) findViewById(R.id.input_password);
        _email= (EditText) findViewById(R.id.email);
        _loginButton=(Button) findViewById(R.id.btn_login);
        _signupLink=(TextView) findViewById(R.id.link_signup);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });


        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }


        _loginButton.setEnabled(false);



        String email = _email.getText().toString();
        String password = _passwordText.getText().toString();

        if(logUser (email,password)) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", _email.getText().toString());
            editor.apply();



                Intent intent = new Intent(LoginActivity.this,Profile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

        }
        else
        {
            onLoginFailed();
        }

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _email.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _email.setError("Enter Valid Username");
            valid = false;
        }
        else {
            _email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


public boolean logUser(String email,String password){
    // fetch the Password form database for respective user name
    String storedPassword="1234";
    // check if the Stored password matches with  Password entered by user
    if(password.equals(storedPassword))
    {
        return true;
    }
    else
    {
        return false;
    }
}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database

    }

}

