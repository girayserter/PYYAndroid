package com.girayserter.pyyandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.girayserter.pyyandroid.R;
import com.girayserter.pyyandroid.databinding.ListItemAsamaBinding;
import com.girayserter.pyyandroid.databinding.ListItemProjectBinding;
import com.girayserter.pyyandroid.models.Asama;
import com.girayserter.pyyandroid.models.Proje;

import java.util.ArrayList;
import java.util.List;

public class AsamaAdapter extends RecyclerView.Adapter<AsamaAdapter.MyViewHolder>{

    private List<Asama> asamalar;
    private AsamaAdapter.AsamalarOnClickInterface asamalarOnClickInterface;
    private Context context;


    public AsamaAdapter(Context context, AsamaAdapter.AsamalarOnClickInterface asamalarOnClickInterface) {
        asamalar = new ArrayList<>();
        this.asamalarOnClickInterface =asamalarOnClickInterface;
        this.context=context;
    }

    public void addAsamaList(List<Asama> teamList) {
        this.asamalar = teamList;
    }

    @Override
    public int getItemCount() {
        return asamalar != null ? asamalar.size() : 0;
    }

    @NonNull
    @Override
    public AsamaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemAsamaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_asama, parent, false);
        return new AsamaAdapter.MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListItemAsamaBinding binding;


        MyViewHolder(ListItemAsamaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.btnSil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    asamalarOnClickInterface.onItemClick(binding.getAsama());
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(final AsamaAdapter.MyViewHolder holder, final int position) {
        holder.binding.setAsama(asamalar.get(position));
    }

    public interface AsamalarOnClickInterface {
        void onItemClick(Asama asama);
    }
}
