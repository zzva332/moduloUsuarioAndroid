package com.example.gestionusuarios.usuarios;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionusuarios.HomeActivity;
import com.example.gestionusuarios.R;
import com.example.gestionusuarios.database.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private DatabaseHelper databaseHelper;

    public UsuarioFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UsuarioFragment newInstance(int param1) {
        UsuarioFragment fragment = new UsuarioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getContext());

        Usuario user = databaseHelper.getUserById(mParam1);

        showValue(view, R.id.labelNombre, user.name);
        showValue(view, R.id.labelLastname, user.lastName);
        showValue(view, R.id.labelEmail, user.email);
        showValue(view, R.id.labelFechaCreado, user.createDate);
        showValue(view, R.id.labelEstado, (user.estado == 1) ? "Activo" : "Inactivo");

        Button eliminarBtn = view.findViewById(R.id.eliminarBtn);
        Button updateBtn = view.findViewById(R.id.updateBtn);

        eliminarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Estás seguro de que quieres eliminar este elemento?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (databaseHelper.deleteUserById(user.id)){
                                Toast.makeText(getContext(), "Se elimino el registro " + user.id + " Satisfactoriamente", Toast.LENGTH_SHORT).show();
                                getParentFragmentManager().popBackStack();
                            } else {
                                Toast.makeText(getContext(), "Hubo un error al remover este registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = UsuarioFormFragment.newInstance(user.id);
                ((HomeActivity)getActivity()).goToFragment(fragment, true);
            }
        });

    }

    public void showValue (View view, int resourceId, String value){
        TextView text = view.findViewById(resourceId);
        text.setText(value);
    }
}