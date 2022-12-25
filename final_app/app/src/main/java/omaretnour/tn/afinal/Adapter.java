package omaretnour.tn.afinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<Contact> {
   private Context context ;
   private  int resource ;

    public Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Contact> objects) {
        super(context, resource, objects);
        this.context = context ;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);

        TextView nom = (TextView) convertView.findViewById(R.id.nom);
        TextView num = (TextView) convertView.findViewById(R.id.num);
        nom.setText("Name : "+getItem(position).getNom());
        num.setText("Phone : "+getItem(position).getNum());

        return convertView ;
    }
}
