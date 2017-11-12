package nyc.c4q.login_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_KEY = "sharedPrefsTesting";
    //WHen Letter is all caps, meaning is constant, it is never changing onces it is used because of final.
    private EditText username;
    private EditText password;
    private CheckBox checkBox;
    private Button submitButton;
    private Button registerButton;
    private SharedPreferences login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username_edittext);
        password = (EditText) findViewById(R.id.password_edittext);
        checkBox = (CheckBox) findViewById(R.id.remember_me_checkbox);
        submitButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.sign_up);

        login = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        if (login.getBoolean("isChecked", false)) {
            username.setText(login.getString("username", null));
            password.setText(login.getString("password", null));
            checkBox.setChecked(login.getBoolean("isChecked", false));
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = login.edit();
                if (checkBox.isChecked()) {
                    editor.putString("username", username.getText().toString());
                    editor.putString("password", password.getText().toString());
                    editor.putBoolean("isChecked", checkBox.isChecked());
                    //commit is when you want to store your sharedpref immediately
                    editor.commit();
                    //apply is you are letting android to take their time to store the data.
                } else {
                    editor.putBoolean("isChecked", checkBox.isChecked());
                    editor.commit();
                }
                String checkUser = "user" + username.getText().toString();
                String checkPassword = "password" + username.getText().toString();

                if (username.getText().toString().equalsIgnoreCase(login.getString(checkUser, null))
                        && password.getText().toString().equals(login.getString(checkPassword, null))) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("currentUser", username.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid usuername or password!", Toast.LENGTH_LONG).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.putExtra("testKey", SHARED_PREFS_KEY);
                startActivity(intent);
            }
        });

    }
}
