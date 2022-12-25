package omaretnour.tn.afinal;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignUp extends AppCompatActivity  {
    EditText user,pwd,conf;
    TextView title ;
    Button sign ,back;
    ProgressDialog dialog;
    boolean addPossible;
    JSONParser parser=new JSONParser();
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        back = (Button) findViewById(R.id.back);
        user = (EditText) findViewById(R.id.usernameedit);
        pwd = (EditText) findViewById(R.id.passwordedit);
        conf = (EditText) findViewById(R.id.confirmedit);
        title = (TextView) findViewById(R.id.signTitle);
        sign = (Button) findViewById(R.id.signButton);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check(user.getText().toString(), pwd.getText().toString(), conf.getText().toString())) {
                    new Signup().execute();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent i = new Intent(getApplicationContext(),LoginScreenApp.class);
                 startActivity(i);
                  finish();
            }
        });
    }
        class Signup extends AsyncTask<String, String,String>
        {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog=new ProgressDialog(SignUp.this);
                dialog.setMessage("Please Wait");
                dialog.show();

            }

            @Override
            protected String doInBackground(String... strings) {
                HashMap<String,String> mp=new HashMap<String,String>();
                mp.put("nom",user.getText().toString());

                addPossible = canAddUserHttpRequest(mp);
                if(addPossible) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("nom", user.getText().toString());
                    map.put("password", pwd.getText().toString());
                    success = signUpHttpRequest(map);
                }
                return null;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialog.cancel();
                signupToaster(success,addPossible);
            }
        }
    public boolean check(String name , String pass,String connf) {
        if (name.trim().equals("")) {
            Toast.makeText(this, "Type a username", Toast.LENGTH_SHORT).show();
        }
        else if (pass.trim().equals("")) {
            Toast.makeText(this,"Please choose a password",Toast.LENGTH_SHORT).show();
        }
        else if (!pass.equals(connf)) {
            Toast.makeText(this,"Please confirm your password",Toast.LENGTH_SHORT).show();
        }
        else {
            return true ;
        }
        return false ;
    }

    public void ShowHidePass(View view) {

        if (view.getId() == R.id.show_pass_btnn) {
            if (pwd.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.ic_visibility);
                //Show Password
                pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.ic_visibility_off);
                //Hide Password
                pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
    public boolean canAddUserHttpRequest(HashMap<String,String> map) {
        int success=0,found=0;
        JSONObject object=parser.makeHttpRequest(
                "https://omarandnour.000webhostapp.com/and/select_all_user.php","GET",map);
        try {
            success = object.getInt("success");
            found = object.getInt("found");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(found == 0)
            return true ;
        else
            return false ;
    }
    public int signUpHttpRequest(HashMap<String,String> map) {
        int success =0;
        JSONObject object = parser.makeHttpRequest(
                "https://omarandnour.000webhostapp.com/and/signin.php", "GET", map);

        try {
            success = object.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success;
    }
    public void signupToaster(int success,boolean addPossible) {
        if(!addPossible) {
            Toast.makeText(getApplicationContext(),"Username Already Exists",Toast.LENGTH_SHORT).show();
        }
        if(success==1)
        {
            Toast.makeText(SignUp.this,"Sign Up Success",Toast.LENGTH_LONG).show();
            Intent i = new Intent(SignUp.this,LoginScreenApp.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(SignUp.this,"Sign Up Failure",Toast.LENGTH_LONG).show();
        }
    }
}