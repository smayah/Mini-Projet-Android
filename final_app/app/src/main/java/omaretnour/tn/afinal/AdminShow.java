package omaretnour.tn.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminShow extends AppCompatActivity {
    ListView ls;
    JSONParser parser = new JSONParser();
    ProgressDialog dialog;
    ArrayList<Contact> list_contacts= new ArrayList<>();
    int success ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show);
        ls = (ListView) findViewById(R.id.ls_admin);
        new AdminShowContacts().execute();
    }


    class AdminShowContacts extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(AdminShow.this);
            dialog.setMessage("Please Wait");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {

            HashMap<String, String> map = new HashMap<>();
            success = showMakeHttpRequest(map);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();
                Adapter adapter = new Adapter(getApplicationContext(), R.layout.item, list_contacts);
                ls.setAdapter(adapter);
                if (success == 0)
                    Toast.makeText(AdminShow.this, "Empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public int showMakeHttpRequest(HashMap<String,String> map) {
        int success =0 ;
        JSONObject object_all_contacts = parser.makeHttpRequest("https://omarandnour.000webhostapp.com/and/all.php", "GET", map);
        try {
            success = object_all_contacts.getInt("success");
            if (success == 1) {
                JSONArray contacts = object_all_contacts.getJSONArray("contacts");
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject contact = contacts.getJSONObject(i);
                    list_contacts.add(new Contact("admin",contact.getString("nom"),contact.getString("num")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success ;
    }
}