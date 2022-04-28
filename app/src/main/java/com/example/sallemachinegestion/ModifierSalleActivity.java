package com.example.sallemachinegestion;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.sallemachinegestion.bean.Salle;
import com.example.sallemachinegestion.service.SalleService;

import java.io.Serializable;
import java.util.HashMap;

public class ModifierSalleActivity extends AppCompatActivity {

    private EditText code;
    private EditText libelle;
    private Button modifier;
    private HashMap data;
    private Intent i;
    private Serializable s;
    private SalleService salleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_salle);

        code = (EditText) findViewById(R.id.code);
        libelle = (EditText) findViewById(R.id.libelle);
        modifier = (Button) findViewById(R.id.edit);

        i = getIntent();
        s = getIntent().getSerializableExtra("data");
        if (s != null) {
            data = (HashMap) s;
            code.setText("" + data.get("code"));
            libelle.setText("" + data.get("libelle"));
        } else {
            Toast.makeText(this, " Impossible", Toast.LENGTH_LONG).show();
        }

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salleService = new SalleService(getApplicationContext());
                salleService.update(new Salle(Integer.parseInt(data.get("id").toString()), code.getText().toString(), libelle.getText().toString()));
                startActivity(new Intent(getApplicationContext(), sallelist.class));
            }
        });
    }
}