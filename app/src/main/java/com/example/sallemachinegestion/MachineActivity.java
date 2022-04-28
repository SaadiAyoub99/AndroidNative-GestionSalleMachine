package com.example.sallemachinegestion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.sallemachinegestion.adapter.MachineAdapter;
import com.example.sallemachinegestion.bean.Machine;
import com.example.sallemachinegestion.service.MachineService;

import java.util.HashMap;

public class MachineActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;
    private MachineService machineService;
    private View v ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_machines);
        list = (ListView) findViewById(R.id.listView);
        machineService = new MachineService(this);
        MachineAdapter as = new MachineAdapter(this, machineService.findAll());
        list.setAdapter(as);
        list.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
                        machineService.delete(machineService.findById(id));
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

                Intent intent = new Intent(getApplicationContext(), ModifierMachineActivity.class);
                int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                Machine machine = machineService.findById(id);
                HashMap<String,String> data = new HashMap<>();
                data.put("id",machine.getId()+"");
                data.put("idSalle",machine.getSalle().getCode()+"");
                data.put("marque",machine.getMarque());
                data.put("reference",machine.getRefernce());
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog1 = alertDialogBuilder.create();
        alertDialog1.show();

    }
}