package omaretnour.tn.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Add extends AppCompatActivity {
    TextView title;
    EditText name,phone;
    Button back,add ;
    int success ;
    int succes_id ,id;
    boolean testNumber;
    String user ;
    ProgressDialog dialog;
    JSONParser parser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title = findViewById(R.id.addTitle);
        phone = findViewById(R.id.phoneEdit);
        name = findViewById(R.id.nameEdit);
        add = findViewById(R.id.addButton);
        back = (Button) findViewById(R.id.back);
        user = (String) getIntent().getStringExtra("user");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testNumber = checkPhoneNumber(phone.getText().toString());
                if(testNumber)
                    new AddContact().execute();
                else
                    Toast.makeText(getApplicationContext(),"Please enter a valid number",Toast.LENGTH_SHORT).show();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ShowContacts.class);
                intent.putExtra("user",user);
                intent.putExtra("nom",name.getText().toString());
                intent.putExtra("num",phone.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
    class AddContact extends AsyncTask<String, String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(Add.this);
            dialog.setMessage("Please Wait");
            dialog.show();

        }
        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map_user = new HashMap<String,String>();
            map_user.put("user",user);
            id = getIdHttpRequest(map_user);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user",String.valueOf(id) );
            map.put("nom", name.getText().toString());
            map.put("num", phone.getText().toString());
            success = addHttpRequest(map);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();
            addStatusToaster(success);
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ShowContacts.class);
        i.putExtra("refresh", true);
        i.putExtra("user",user);
        i.putExtra("nom",name.getText().toString());
        i.putExtra("num",phone.getText().toString());
        startActivity(i);
        finish();

    }
    public int getIdHttpRequest(HashMap<String,String> map) {
        int success=0,id =0;
        JSONObject object=parser.makeHttpRequest(
                "https://omarandnour.000webhostapp.com/and/user_id_get.php","GET",map);
        try {
            success = object.getInt("success");
            id = object.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(success == 1)
            return id ;
        else
            return 0 ;
    }
    public int addHttpRequest(HashMap<String,String> map) {
        int success = 0 ;
        JSONObject object_add = parser.makeHttpRequest(
                "https://omarandnour.000webhostapp.com/and/add.php", "GET", map);
        try {
            success = object_add.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success;
    }
    public void addStatusToaster(int success) {
        if(success==1)
        {
            Toast.makeText(Add.this,
                    "Contact added",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(Add.this,
                    "Adding contact failed",Toast.LENGTH_LONG).show();
        }
    }
    public boolean checkPhoneNumber(String num) {
        return !num.trim().equals("") && num.trim().length() == 8;
    }
}