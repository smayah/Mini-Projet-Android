package omaretnour.tn.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class ModifyActivity extends AppCompatActivity {
    TextView title;
    EditText name,phone;
    Button back,modify ;
    int success_modify,id,contact_id;
    String user ;
    String nom ;
    String num ;
    JSONParser parser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        title = findViewById(R.id.modifyTitle);
        phone = findViewById(R.id.phoneEdit);
        name = findViewById(R.id.nameEdit);
        modify = findViewById(R.id.ModifyButton);
        back = (Button) findViewById(R.id.back);
        user = (String) getIntent().getStringExtra("user");
        nom = (String) getIntent().getStringExtra("nom");
        num = (String) getIntent().getStringExtra("num");
        name.setText(nom);
        phone.setText(num);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              new Modifier().execute();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ShowContacts.class);
                i.putExtra("user",user);
                startActivity(i);
                finish();
            }
        });
    }
    class Modifier extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HashMap<String,String> mp = new HashMap<>();
            mp.put("user",user);
            id = getIdHttpRequest(mp);
            HashMap<String, String> mpi = new HashMap<>();
            mpi.put("user_id", String.valueOf(id));
            mpi.put("nom", nom);
            mpi.put("num", num);
            contact_id = getContactIdHttpRequest(mpi);
            HashMap<String, String> map = new HashMap<>();
            map.put("num", phone.getText().toString());
            map.put("nom", name.getText().toString());
            map.put("contact", String.valueOf(contact_id));
            success_modify = updateHttpRequest(map);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateStatusToaster(success_modify);
        }
    }
    public int getIdHttpRequest(HashMap<String,String> map) {
        int success=0,id =0;
        JSONObject object=parser.makeHttpRequest("https://omarandnour.000webhostapp.com/and/user_id_get.php","GET",map);
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
    public int getContactIdHttpRequest(HashMap<String,String> map) {
    int success=0,contact_id = 0 ;
        JSONObject object = parser.makeHttpRequest("https://omarandnour.000webhostapp.com/and/contact_id.php", "GET", map);
        try {
            success = object.getInt("success");
            contact_id = object.getInt("contact_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(success == 1)
            return contact_id ;
        else
            return 0 ;
    }
    public int updateHttpRequest(HashMap<String,String> map) {
        int success=0;
        JSONObject object = parser.makeHttpRequest("https://omarandnour.000webhostapp.com/and/update.php", "GET", map);
        try {
            success = object.getInt("success");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success ;
    }
    public void updateStatusToaster(int success) {
        if(success==0)
        {
            Toast.makeText(ModifyActivity.this,"Failed to update contact",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(ModifyActivity.this,"Contact Updated",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ShowContacts.class);
        i.putExtra("refresh_modify", true);
        i.putExtra("user",user);
        startActivity(i);
        finish();

    }

}