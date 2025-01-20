package com.example.gestionusuarios;

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
import com.example.gestionusuarios.usuarios.Usuario;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getContext());

        Button register = view.findViewById(R.id.button2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                registrar(view);
            }
        });
    }

    public void registrar(View view){
        boolean result = false;
        try {
            String firstName = getValue(view, R.id.fullname);
            String lastName = getValue(view, R.id.lastname);
            String email = getValueValidFormat(view, R.id.email,
                    "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                    "Debe ser un email valido");
            String password = getValue(view, R.id.password);
            String password2 = getValue(view, R.id.password2);

            if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                    || password.isEmpty()){
                throw new Exception("Campos requeridos");
            }

            if(!password.equals(password2)){
                Toast.makeText(getContext(), "Las 2 password no son iguales", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario model = new Usuario();
            model.name = firstName;
            model.lastName = lastName;
            model.email = email;
            model.password = password;

            model.createDate = getCurrentDate();
            model.estado = 1;

            result = databaseHelper.insertUser(model);
        } catch (Exception e) {}

        if(!result){
            Toast.makeText(getContext(), "Hubo un error. valide la informacion diligenciada", Toast.LENGTH_SHORT).show();
            return;
        }
        getParentFragmentManager().popBackStack();
    }

    private String getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    public String getValue(View view, int elementId) throws Exception{
        EditText element = (EditText) view.findViewById(elementId);
        String txt = element.getText().toString();

        if(txt.isEmpty()){
            element.setError("Este campo es requerido");
        }

        return element.getText().toString();
    }

    public String getValueValidFormat(View view, int resourceId, String patternString, String messageError){
        EditText element = view.findViewById(resourceId);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
}