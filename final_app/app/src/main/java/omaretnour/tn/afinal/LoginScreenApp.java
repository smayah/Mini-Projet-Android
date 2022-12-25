package omaretnour.tn.afinal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class LoginScreenApp extends AppCompatActivity {
    TextView title,question_txt ;
    EditText pass,user;
    Button login_button,signup_button ;
    ProgressDialog dialog;
    JSONParser parser=new JSONParser();
    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen_app);
        title = findViewById(R.id.logTitle);
        pass = findViewById(R.id.passwordEdit);
        user = findViewById(R.id.usernameEdit);
        login_button = findViewById(R.id.loginButton);
        signup_button = (Button) findViewById(R.id.signbtn);
        question_txt = (TextView) findViewById(R.id.Quest);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getText().toString().equals("admin")) {
                    Intent intent_admin = new Intent(LoginScreenApp.this,AdminShow.class);
                    startActivity(intent_admin);
                    finish();
                }
                else if (check(user.getText().toString(), pass.getText().toString())) {
                    new Login().execute();
                }
            }
        });
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }
        class Login extends AsyncTask<String, String,String>
        {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog=new ProgressDialog(LoginScreenApp.this);
                dialog.setMessage("Please Wait");
                dialog.show();

            }

            @Override
            protected String doInBackground(String... strings) {
                HashMap<String,String> map=new HashMap<String,String>();
                map.put("nom",user.getText().toString());
                map.put("password", pass.getText().toString());
                success = loginHttpRequest(map);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialog.cancel();
                loginStatusToaster(success);
            }
        }

    public boolean check(String name , String pass) {
        if (name.trim().equals("")) {
            Toast.makeText(this, "Please type your username", Toast.LENGTH_SHORT).show();
        }
        else if (pass.trim().equals("")) {
            Toast.makeText(this,"Please type your password",Toast.LENGTH_SHORT).show();
        }

        else {

            return true ;
        }

        return false ;
    }

    public void ShowHidePass(View view) {
        if(view.getId()==R.id.show_pass_btn){
            if(pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_visibility);
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_visibility_off);
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    public int loginHttpRequest(HashMap<String,String> map) {
        JSONObject object_login=parser.makeHttpRequest(
                "https://omarandnour.000webhostapp.com/and/logingin.php","GET",map);
        try {
            success=object_login.getInt("success");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success ;

    }
    public void loginStatusToaster(int success) {
        if(success==1)
        {
            Intent i ;
            Toast.makeText(LoginScreenApp.this,"Login Successful",Toast.LENGTH_LONG).show();
            i = new Intent(LoginScreenApp.this,Splashscreen.class);
            i.putExtra("user",user.getText().toString());
            startActivity(i);
            finish();
        }
        else
        {
            Toast.makeText(LoginScreenApp.this,"Login Failed",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Leaving The App :( ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create();
        builder.show();

    }


}
