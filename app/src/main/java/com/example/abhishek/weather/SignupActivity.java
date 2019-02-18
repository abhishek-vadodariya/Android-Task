package com.example.abhishek.weather;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import GetterSetter.LoginPojo;

public class SignupActivity extends AppCompatActivity
{

    private Activity activity;
    private EditText edt_signup_name, edt_signup_email, edt_signup_password;
    private TextView txt_signup_create;
    private LinearLayout ll_login;
    public static DatabaseHandler dbHandler;
    public static SQLiteDatabase db;
    public static ArrayList<LoginPojo>loginPojos = new ArrayList<LoginPojo>();
    private String TAG ="Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        activity = SignupActivity.this;
        dbHandler = new DatabaseHandler(activity);

        SetUpView();

        txt_signup_create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isValid())
                {
                    insertDataToDB(edt_signup_name.getText().toString().trim(), edt_signup_email.getText().toString().trim().toLowerCase(), edt_signup_password.getText().toString().trim());
                    edt_signup_name.getText().clear();
                    edt_signup_email.getText().clear();
                    edt_signup_password.getText().clear();
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        ll_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void SetUpView()
    {

        edt_signup_name = (EditText) findViewById(R.id.edt_signup_name);
        edt_signup_email = (EditText) findViewById(R.id.edt_signup_email);
        edt_signup_password = (EditText) findViewById(R.id.edt_signup_password);
        txt_signup_create = (TextView) findViewById(R.id.txt_signup_create);
        ll_login = (LinearLayout) findViewById(R.id.ll_login);
    }

    private boolean isValid()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

        if (edt_signup_name.getText().toString().trim().length() == 0)
        {
            edt_signup_name.setError("Please enter first name");
            return false;
        }
        else if (edt_signup_email.getText().toString().length() == 0
                || !edt_signup_email.getText().toString().trim().matches(emailPattern))
        {
            edt_signup_email.setError("Please enter valid email");
            return false;
        }
        else if (edt_signup_password.getText().toString().length() == 0
                || !edt_signup_password.getText().toString().trim().matches(passwordPattern)) {
            edt_signup_password.setError("Please Enter minimum 6 character and one capital, one small and one numerical");
            return false;
        }
        if (edt_signup_name.equals("") || edt_signup_email.equals("") || edt_signup_password.equals("")) {
            Toast.makeText(activity.getApplicationContext(), "Field accant", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return true;
        }
    }


    // INSERT DATA
    private void insertDataToDB(final String name, final String email, final String password)
    {
        try
        {
            db = dbHandler.getWritableDatabase();

            ContentValues cv = new ContentValues();
            //cv.put(DatabaseHandler.LOGIN_ID, LoginFragment.loginId);
            cv.put(DatabaseHandler.USERNAME, name);
            cv.put(DatabaseHandler.EMAIL, email);
            cv.put(DatabaseHandler.PASSWORD, password);

            final long id = db.insert(DatabaseHandler.LOGIN_TABLE, null, cv);

            if(id > 0)
            {
                // success
                LoginPojo loginPojo = new LoginPojo();
                //loginPojo.setLoginId(LoginFragment.loginId);
                loginPojo.setName(name);
                loginPojo.setEmail(email);
                loginPojo.setPassword(password);


                Toast.makeText(activity, "Insert successful", Toast.LENGTH_SHORT).show();

                Log.i(TAG, " & email = " + email + " & password = " + password + "& name = " + name);
            }
            else
            {
                Toast.makeText(activity, "Failed to insert!", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
