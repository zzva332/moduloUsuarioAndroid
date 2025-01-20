package com.example.gestionusuarios.usuarios;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionusuarios.databinding.FragmentItemBinding;

import java.util.List;

public class UsuariosRecyclerViewAdapter extends RecyclerView.Adapter<UsuariosRecyclerViewAdapter.ViewHolder> {

    private final List<Usuario> mValues;
    private ViewHolder holder;
    private View.OnClickListener onClickListener;
    public UsuariosRecyclerViewAdapter(List<Usuario> items) {
        mValues = items;
    }

    public void setOnClickListener(View.OnClickListener l){
        onClickListener = l;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        holder = new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        if(onClickListener != null)
            holder.itemView.setOnClickListener(onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText((mValues.get(position).id + ""));
        holder.mContentView.setText(mValues.get(position).name + " " + mValues.get(position).lastName);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Usuario mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}