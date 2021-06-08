package com.girayserter.pyyandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.girayserter.pyyandroid.R;
import com.girayserter.pyyandroid.databinding.ListItemProjectBinding;
import com.girayserter.pyyandroid.models.Proje;

import java.util.ArrayList;
import java.util.List;

public class ProjelerAdapter extends RecyclerView.Adapter<ProjelerAdapter.MyViewHolder>{

    private List<Proje> projeler;
    private ProjelerOnClickInterface projelerOnClickInterface;
    private Context context;


    public ProjelerAdapter(Context context,ProjelerOnClickInterface projelerOnClickInterfac) {
        projeler = new ArrayList<>();
        this.projelerOnClickInterface=projelerOnClickInterfac;
        this.context=context;
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
    public ProjelerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemProjectBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_project, parent, false);
        return new ProjelerAdapter.MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListItemProjectBinding binding;


        MyViewHolder(ListItemProjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.projectCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    projelerOnClickInterface.onItemClick(binding.getProje());
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.setProje(projeler.get(position));
    }

    public interface ProjelerOnClickInterface {
        void onItemClick(Proje proje);
    }
}
