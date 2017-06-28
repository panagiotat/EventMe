package eventme.eventme;


        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.Serializable;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";


     private EditText _nameText;
    private EditText _emailText;
    private EditText _surnameText;
    private EditText _passwordText;
    private EditText _reEnterPasswordText;
    private  Button _signupButton;
    private TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // get Instance  of Database Adapter


         _nameText=(EditText) findViewById(R.id.name);
        _emailText=(EditText) findViewById(R.id.email);
        _surnameText=(EditText) findViewById(R.id.surname);
         _passwordText=(EditText) findViewById(R.id.input_password);
         _reEnterPasswordText=(EditText) findViewById(R.id.input_reEnterPassword);
        _signupButton=(Button) findViewById(R.id.btn_signup);
        _loginLink=(TextView) findViewById(R.id.link_login);
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });


    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }else {
            onSignupSuccess();



        }

        _signupButton.setEnabled(false);




    }


    public void onSignupSuccess() {
        String name = _nameText.getText().toString();

        String email = _emailText.getText().toString();
        String surname = _surnameText.getText().toString();
        String password = _passwordText.getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email",email);
        editor.apply();

            Intent intent = new Intent(SignupActivity.this, Homepage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;


        String name = _nameText.getText().toString();

        String email = _emailText.getText().toString();
        String surname = _surnameText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }



        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (surname.isEmpty()) {
            _surnameText.setError("Enter Valid Username");
            valid = false;
        }
        else {
            _surnameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
