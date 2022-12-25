package omaretnour.tn.afinal;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowContacts extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    ListView ls ;
    Button add;
    TextView adtx;
    String user ;
    boolean needToRefreshadd,needToRefreshmodify ;
    ProgressDialog dialog;
    Adapter adapter ;
    JSONParser parser=new JSONParser();
    ArrayList<Contact> list_contacts= new ArrayList<Contact>();
    int contact_id,user_id_show,delete_success,user_id,itemToDeletePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contacts);

        ls = (ListView) findViewById(R.id.ls);

        add =(Button) findViewById(R.id.add);

        adtx = (TextView) findViewById(R.id.txtadd);
        needToRefreshadd = (Boolean) getIntent().getBooleanExtra("refresh",false);
        needToRefreshmodify = (Boolean) getIntent().getBooleanExtra("refresh",false);

        user = (String) getIntent().getStringExtra("user");
        new ShowUserContacts().execute();
        if(needToRefreshadd || needToRefreshmodify) {
            new ShowUserContacts().execute();
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Add.class);
                i.putExtra("user",user);
                startActivity(i);
            }
        });
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new ShowUserContacts().execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    class Delete extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HashMap<String,String> mappy = new HashMap<>();
            mappy.put("user",user);
            user_id = getIdHttpRequest(mappy);
            Contact contact = list_contacts.get(itemToDeletePosition);
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(user_id));
            map.put("nom", contact.getNom());
            map.put("num", contact.getNum());
            contact_id = getContactIdHttpRequest(map);
            HashMap<String, String> mapped = new HashMap<>();
            mapped.put("contact_id", String.valueOf(contact_id));
            delete_success = deleteHttpRequest(mapped);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            deleteStatusToaster(delete_success);
        }
    }
    class ShowUserContacts extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(ShowContacts.this);
            dialog.setMessage("Please Wait");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> mp = new HashMap<>();
            mp.put("user",user);
            user_id_show = getIdHttpRequest(mp);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user", String.valueOf(user_id_show));
            try {
                list_contacts = getContactsHttpRequest(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();
            Log.e("contactes",list_contacts.toString());
            adapter = new Adapter(getApplicationContext(),R.layout.item,list_contacts);
            ls.setAdapter(adapter);
            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Button delete,modify,call;
                    TextView adtx,deltx,modtx,calltx;
                    itemToDeletePosition = i;
                    call = (Button) findViewById(R.id.call) ;
                    calltx = (TextView) findViewById(R.id.calltxt);
                    deltx = (TextView) findViewById(R.id.txtdelete);
                    modtx = (TextView) findViewById(R.id.txtmodify);
                    delete = (Button) findViewById(R.id.delete);
                    modify = (Button) findViewById(R.id.modify);
                    modify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent o = new Intent(getApplicationContext(),ModifyActivity.class);
                            o.putExtra("nom",list_contacts.get(i).getNom());
                            o.putExtra("num",list_contacts.get(i).getNum());
                            o.putExtra("user",list_contacts.get(i).getUser());
                            startActivity(o);
                        }
                    });
                    delete.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                           new Delete().execute();

                        }
                    });
                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           makePhoneCall();
                        }
                    });
                }
            });

        }
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

    public int getContactIdHttpRequest(HashMap<String,String> map) {
        int success=0,contact_id = 0 ;

        JSONObject object = parser.makeHttpRequest(
                "https://omarandnour.000webhostapp.com/and/contact_id.php", "GET", map);
        try {
            success = object.getInt("success");
            contact_id = object.getInt("contact_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(success == 1 )
            return contact_id ;
        else
            return 0 ;
    }
    public int deleteHttpRequest(HashMap<String,String> map) {
        int success = 0 ;
        JSONObject ob = parser.makeHttpRequest(
                "https://omarandnour.000webhostapp.com/and/delete.php", "GET", map);
        try {
            success= ob.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success ;
    }
    public void deleteStatusToaster(int success) {
        if(success==0)
        {
            Toast.makeText(ShowContacts.this,"Failed to delete contact",Toast.LENGTH_LONG).show();
        }
            else if(success==1)
        {
            Toast.makeText(ShowContacts.this,"Contact deleted",Toast.LENGTH_LONG).show();
            finish();
            startActivity(getIntent());
        }
    }
    public ArrayList<Contact> getContactsHttpRequest(HashMap<String,String> map) throws JSONException {
        int success = 0;
        String message="";
        ArrayList<Contact> list_contacts = new ArrayList<>();
        JSONObject object = parser.makeHttpRequest(
                "https://omarandnour.000webhostapp.com/and/select_one.php", "GET", map);
        try {
            success = object.getInt("success");
            message = object.getString("message");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (success == 1 || message.equals("no data found")) {

            JSONArray contacts = object.getJSONArray("contacts");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);
                list_contacts.add(new Contact(user, contact.getString("nom"), contact.getString("num")));
            }
            return list_contacts;
        }
        else
            return null ;

    }
    private void makePhoneCall() {
        String number = list_contacts.get(itemToDeletePosition).getNum();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(ShowContacts.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ShowContacts.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(ShowContacts.this, "Type a Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Confirm Logging Out ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(ShowContacts.this, LoginScreenApp.class));
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      dialogInterface.dismiss();
                    }
                });
        builder.create();
        builder.show();

    }

}