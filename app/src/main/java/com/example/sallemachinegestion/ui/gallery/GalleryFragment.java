package com.example.sallemachinegestion.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.sallemachinegestion.MachineActivity;
import com.example.sallemachinegestion.R;
import com.example.sallemachinegestion.bean.Machine;
import com.example.sallemachinegestion.bean.Salle;
import com.example.sallemachinegestion.databinding.FragmentGalleryBinding;
import com.example.sallemachinegestion.service.MachineService;
import com.example.sallemachinegestion.service.SalleService;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private EditText marque ;
    private EditText reference ;
    private Button create ;
    private Button listeMachines ;

    private Spinner spinner ;
    private SalleService salleService;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_machine, container, false);
        salleService = new SalleService(view.getContext());
        marque = view.findViewById(R.id.marque);
        reference = view.findViewById(R.id.reference);
        create = view.findViewById(R.id.btnCreate);
        listeMachines = (Button) view.findViewById(R.id.btnListeMachines);
        spinner = (Spinner) view.findViewById(R.id.Spinner);

        ArrayAdapter<String> adapter;
        List<String> liste = new ArrayList<>();
        for (Salle salle : salleService.findAll()) {
            liste.add(salle.getCode());
        }
        adapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item, liste);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (marque.getText().length() != 0 && reference.getText().length() != 0) {
                    MachineService machineService = new MachineService(view.getContext());
                    salleService = new SalleService(view.getContext());
                    Salle salle = salleService.findByCode(spinner.getSelectedItem().toString());
                    machineService.add(new Machine(marque.getText().toString(), reference.getText().toString(), salle));
                    marque.setText("");
                    reference.setText("");
                    Toast.makeText(view.getContext(), "Machine est bien ajouter", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(view.getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            }

        });

        listeMachines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MachineActivity.class);
                startActivity(intent);

            }

        });

        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}