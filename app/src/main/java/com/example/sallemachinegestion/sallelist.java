package com.example.sallemachinegestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.sallemachinegestion.adapter.SalleAdapter;
import com.example.sallemachinegestion.bean.Salle;
import com.example.sallemachinegestion.service.SalleService;

import java.util.HashMap;

public class sallelist extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;
    private SalleService ad;
    private View v;
    SalleAdapter as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sallelist);
        list = (ListView) findViewById(R.id.listView);
        ad = new SalleService(this);
        SalleAdapter as = new SalleAdapter(this, ad.findAll());
        list.setAdapter(as);
        list.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, ((TextView) view.findViewById(R.id.ids)).getText().toString() + " " + ((TextView) view.findViewById(R.id.code)).getText() + " " + ((TextView) view.findViewById(R.id.libelle)).getText(), Toast.LENGTH_LONG).show();
        v = view;
        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
        final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);

        alertDialogBuilder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialogBuilder1.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                        ad.delete(ad.findById(id));
                        finish();
                        startActivity(getIntent());
                    }
                });
                alertDialogBuilder1.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder1.create();
                alertDialog.show();
            }
        });

        alertDialogBuilder.setNeutralButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), ModifierSalleActivity.class);
                int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                Salle salle = ad.findById(id);
                HashMap<String,String> data = new HashMap<>();
                data.put("id",salle.getId()+"");
                data.put("code",salle.getCode());
                data.put("libelle",salle.getLibelle());
                intent.putExtra("data",data);
                startActivity(intent);

            }
        });



        AlertDialog alertDialog1 = alertDialogBuilder.create();
        alertDialog1.show();
    }
}