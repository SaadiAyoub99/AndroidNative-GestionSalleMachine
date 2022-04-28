package com.example.sallemachinegestion.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.sallemachinegestion.R;
import com.example.sallemachinegestion.adapter.SalleAdapter;
import com.example.sallemachinegestion.bean.Salle;
import com.example.sallemachinegestion.databinding.FragmentHomeBinding;
import com.example.sallemachinegestion.sallelist;
import com.example.sallemachinegestion.service.SalleService;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private EditText code;
    private EditText libelle;
    private SalleService ad;
    private Button add;
    private Button liste_salle;
    SalleService db = null;
    SalleAdapter as;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.salle_activity, container, false);
        code = view.findViewById(R.id.code);
        libelle = (EditText) view.findViewById(R.id.libelle);
        add = (Button) view.findViewById(R.id.add);
        liste_salle = (Button) view.findViewById(R.id.list);
        ad = new SalleService(getActivity().getApplicationContext());


        liste_salle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), sallelist.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.getText().length() != 0 && libelle.getText().length() != 0) {
                    db =  new SalleService(view.getContext());
                    db.add(new Salle(code.getText().toString(), libelle.getText().toString()));
                    code.setText("");
                    libelle.setText("");
                    Toast.makeText(view.getContext(), "Salle est bien ajouter", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(view.getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}