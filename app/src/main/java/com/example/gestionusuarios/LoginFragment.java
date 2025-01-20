package com.example.gestionusuarios;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestionusuarios.database.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    public LoginFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(this.getContext());
        Button buttonRegister = view.findViewById(R.id.button3);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.goToFragment(new RegisterFragment(), true);
            }
        });


        Button loginBtn = view.findViewById(R.id.button2);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                String username = getValue(view, R.id.username);
                String password = getValue(view, R.id.password);

                if (databaseHelper.login(username, password) != null){
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "username o password invalidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public String getValue(View view, int elementId){
        EditText element = (EditText) view.findViewById(elementId);
        String txt = element.getText().toString();

        if(txt.isEmpty()){
            element.setError("Este campo es requerido");
        }

        return element.getText().toString();
    }
}