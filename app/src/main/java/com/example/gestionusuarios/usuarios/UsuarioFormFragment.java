package com.example.gestionusuarios.usuarios;

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

import com.example.gestionusuarios.R;
import com.example.gestionusuarios.database.DatabaseHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsuarioFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioFormFragment extends Fragment {

    private static final String ARG_PARAM1 = "userId";
    private int mParam1;
    private DatabaseHelper databaseHelper;

    public UsuarioFormFragment() {
        // Required empty public constructor
    }

    public static UsuarioFormFragment newInstance(int userId) {
        UsuarioFormFragment fragment = new UsuarioFormFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
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
        return inflater.inflate(R.layout.fragment_usuario_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getContext());

        EditText name = (EditText) view.findViewById(R.id.name);
        EditText lastName = (EditText) view.findViewById(R.id.lastname);
        EditText email = (EditText) view.findViewById(R.id.labelEmail);
        EditText password = (EditText) view.findViewById(R.id.password);
        EditText confirmPassword = (EditText) view.findViewById(R.id.password2);

        if(mParam1 != 0) {
            Usuario user = databaseHelper.getUserById(mParam1);
            name.setText(user.name);
            lastName.setText(user.lastName);
            email.setText(user.email);
        } else {
            if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                Toast.makeText(getContext(), "Las 2 password deben ser iguales", Toast.LENGTH_SHORT).show();
            }
        }

        Button registerBtn = (Button) view.findViewById(R.id.button2);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = false;
                try {
                    Usuario model = new Usuario();
                    model.id = mParam1;
                    model.name = getValueValid(name);
                    model.lastName = getValueValid(lastName);
                    model.estado = 1;
                    model.email = getValueValidFormat(email,
                            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                            "Debe ser un email valido");
                    model.password = (mParam1 == 0) ? getValueValid(password) : password.getText().toString();

                    if(model.name.isEmpty() || model.lastName.isEmpty() || model.email.isEmpty()){
                        throw new Exception("Campos invalidos");
                    }

                    if (mParam1 != 0){
                        result = databaseHelper.updateUser(model);
                    } else {
                        result = databaseHelper.insertUser(model);
                    }


                } catch (Exception e) {}

                if (!result){
                    Toast.makeText(getContext(), "Hubo un error al completar esta accion", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getContext(), "Se completo esta accion", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack();
            }
        });
    }
    public String getValueValid(EditText element) throws Exception{
        String value = element.getText().toString();
        if(value.isEmpty()){
            element.setError("Campo requerido");
            return "";
        }
        return value;
    }
    public String getValueValidFormat(EditText element, String patternString, String messageError){
        String value = element.getText().toString();
        if (value.isEmpty()){
            element.setError("Campo requerido");
            return "";
        }

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(value);
        if(!matcher.matches()){
            element.setError(messageError);
            return "";
        }

        return value;
    }
}