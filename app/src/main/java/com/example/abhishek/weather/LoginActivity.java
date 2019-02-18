package com.example.abhishek.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import GetterSetter.LoginPojo;

public class LoginActivity extends AppCompatActivity
{

    private Activity activity;
    @SuppressWarnings("unused")
    private ImageView img_login;
    private EditText edt_login_email,edt_login_password;
    private TextView txt_login;
    public static final String  MY_PREFS_NAME = "MyPrefsFile";
    public static final String USER_NAME_STEING = "user";
    private LinearLayout ll_signup;
    private SharedPreferences sharedpreferences;
    private DatabaseHandler dbHandler;
    private SQLiteDatabase db;
    public int loginId = 0;
    public String email = "email";
    public String password = "password";
    public String username ="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = LoginActivity.this;


        dbHandler = new DatabaseHandler(activity);

        createlogin();
        /*sharedpreferences = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        sharedpreferences.getString(email, null);
        sharedpreferences.getString(password, null);*/

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefsFile", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

    }

    public void createlogin()
    {
        edt_login_email = (EditText)findViewById(R.id.edt_login_email);
        edt_login_password = (EditText)findViewById(R.id.edt_login_password);
        txt_login = (TextView)findViewById(R.id.txt_login);
        ll_signup = (LinearLayout)findViewById(R.id.ll_signup);

        getDataFromDB();

        ll_signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(activity,SignupActivity.class);
                startActivity(intent);
            }
        });


        //sharedpreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        txt_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //if (isValid())
                //{
                    String email = edt_login_email.getText().toString();
                    String password = edt_login_password.getText().toString();
                    Log.v("Login DB", "email = " + email + "password = " + password);

                    login(email,password);

                    if(loginId  > 0)
                    {
                        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME,Context.MODE_PRIVATE).edit();
                        editor.putInt("id", loginId);
                        editor.putString("username", username);
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putBoolean("isLoggedIn",true);
                        editor.commit();

                        Log.v("Login DB", "email = " + email + "password = " + password + "loginid = " + loginId + "username = "+ username);

                        Toast.makeText(activity, "Login successfully", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(activity,MainActivity.class);
                        startActivity(intent);
                        edt_login_email.setText("");
                        edt_login_password.setText("");
                    }
                    else
                    {
                        Toast.makeText(activity, "User name or password does not match.", Toast.LENGTH_LONG).show();
                        edt_login_email.setText("");
                        edt_login_password.setText("");
                    }

                //}
            }
        });


    }


//    private boolean isValid()
//    {
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16})";
//
//        String emailStr = edt_login_email.getText().toString().trim();
//        String passwordStr = edt_login_password.getText().toString().trim();
//
//        if (emailStr.length() == 0 || !emailStr.matches(emailPattern))
//        {
//            edt_login_email.setError("Please enter valid  email");
//            return false;
//        }
//        else if (passwordStr.length() == 0 || !passwordStr.matches(passwordPattern))
//        {
//            edt_login_password.setError("Please Enter minimum 6 character and one capital, one small and one numerical");
//            return false;
//        }
//        else
//        {
//
//           /* SharedPreferences.Editor editor = sharedpreferences.edit();
//
//            editor.putString(email, emailStr);
//            editor.putString(password, passwordStr);
//
//            editor.commit();*/
//
//            return true;
//        }
 //   }


    // GET DATA
    public void getDataFromDB()
    {
        try
        {
            db = dbHandler.getReadableDatabase();
            String query = "SELECT * FROM " + DatabaseHandler.LOGIN_TABLE;
            Log.d("DBCHECK", "getDataFromDB: "+query);
            Cursor cursor = db.rawQuery(query, null);
            System.out.println("total row = " + cursor.getCount());
            if(cursor != null){
                Log.d("DBCHECK", "getDataFromDB: CURSER NULL " + cursor.moveToFirst());
            }
            if (cursor.moveToFirst())
            {
                do
                {
                    try
                    {
                        //	LoginID, Email, Password
                        int id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.LOGIN_ID));
                        Log.d("DBCHECK", "getDataFromDB: ID :"+id);
                        String Name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.USERNAME));
                        Log.d("DBCHECK", "getDataFromDB: name: "+Name);

                        String Password = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PASSWORD));
                        Log.d("DBCHECK", "getDataFromDB: Pass : "+Password);
                        String Email = cursor.getString(2);
                        Log.d("DBCHECK", "getDataFromDB: email : "+Email);


                        LoginPojo loginPojo = new LoginPojo();
                        loginPojo.setLoginId(id);
                        loginPojo.setName(Name);
                        loginPojo.setEmail(Email);
                        loginPojo.setPassword(Password);


                        Log.v("GET DATA LOGIN", " id = " + id + Name+" & Name " +Email+" & Email " +Password+" & Password ");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void login(String email, String password) throws SQLException
    {
        try
        {
            db = dbHandler.getReadableDatabase();
            Cursor mCursor = db.rawQuery("SELECT * FROM " + DatabaseHandler.LOGIN_TABLE + " WHERE email=? AND password=?", new String[]{email,password});
            //Cursor mCursor = db.rawQuery("SELECT * FROM " + DatabaseHandler.LOGIN_TABLE + " WHERE " + DatabaseHandler.EMAIL + "=? AND " +
              //      DatabaseHandler.PASSWORD + "=?",new String[]{email, password});

            //Cursor mCursor = db.rawQuery(DatabaseHandler.LOGIN_ID, new String[] {DatabaseHandler.EMAIL, DatabaseHandler.PASSWORD});
            mCursor.moveToFirst();

            System.out.println("cursor count : " + mCursor.getCount());

            if (mCursor != null)
            {
                if(mCursor.getCount() > 0)
                {
                    loginId = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.LOGIN_ID)));
                    System.out.println("row id : " + loginId);
                    username  = (mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.USERNAME)));
                    System.out.println("username :" + username);
                }
            }
            mCursor.close();
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
