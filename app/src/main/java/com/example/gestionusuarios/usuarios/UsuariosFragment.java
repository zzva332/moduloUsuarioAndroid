package com.example.gestionusuarios.usuarios;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gestionusuarios.HomeActivity;
import com.example.gestionusuarios.R;
import com.example.gestionusuarios.database.DatabaseHelper;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class UsuariosFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private DatabaseHelper databaseHelper;
    public UsuariosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UsuariosFragment newInstance(int columnCount) {
        UsuariosFragment fragment = new UsuariosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        Context context = view.getContext();
        databaseHelper = new DatabaseHelper(context);
        // Set the adapter
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            List<Usuario> users = databaseHelper.getUsers();

            UsuariosRecyclerViewAdapter adapter = new UsuariosRecyclerViewAdapter(users);

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerView.getChildAdapterPosition(view);

                    UsuarioFragment fragment = UsuarioFragment.newInstance(users.get(position).id);
                    ((HomeActivity)getActivity()).goToFragment(fragment, true);
                    Toast.makeText(getContext(), users.get(position).email, Toast.LENGTH_SHORT).show();
                }
            });

            recyclerView.setAdapter(adapter);

        }
        return view;
    }
}