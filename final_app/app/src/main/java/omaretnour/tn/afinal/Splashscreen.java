package omaretnour.tn.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import maes.tech.intentanim.CustomIntent;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            final String user=(String) getIntent().getStringExtra("user");
            @Override
            public void run() {
                Intent i=new Intent(Splashscreen.this, ShowContacts.class);
                i.putExtra("user",user);
                startActivity(i);
                CustomIntent.customType(Splashscreen.this, "fadein-to-fadeout");
                finish();
            }
        },3000 );
    }
}
