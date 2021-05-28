package com.girayserter.pyyandroid;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.girayserter.pyyandroid.databinding.ListItemProjectBinding;

import java.util.ArrayList;
import java.util.List;

public class ProjelerAdapter extends RecyclerView.Adapter<ProjelerAdapter.MyViewHolder>{

    private List<Proje> projeler;


    public ProjelerAdapter() {
        projeler = new ArrayList<>();
    }

    public void addProjeList(List<Proje> teamList) {
        this.projeler = teamList;
    }

    @Override
    public int getItemCount() {
        return projeler != null ? projeler.size() : 0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemProjectBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_project, parent, false);
        return new MyViewHolder(binding);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ListItemProjectBinding binding;

        MyViewHolder(ListItemProjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.setProje(projeler.get(position));
    }
}
