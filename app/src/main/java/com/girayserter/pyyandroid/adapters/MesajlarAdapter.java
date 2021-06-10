package com.girayserter.pyyandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.girayserter.pyyandroid.R;
import com.girayserter.pyyandroid.databinding.ListItemMesajkutusuBinding;
import com.girayserter.pyyandroid.models.Mesaj;

import java.util.ArrayList;
import java.util.List;

public class MesajlarAdapter extends RecyclerView.Adapter<MesajlarAdapter.MyViewHolder>{

    private List<Mesaj> mesajlar;
    private MesajlarAdapter.MesajlarOnClickInterface mesajlarOnClickInterface;
    private Context context;


    public MesajlarAdapter(Context context, MesajlarAdapter.MesajlarOnClickInterface mesajlarOnClickInterface) {
        mesajlar = new ArrayList<>();
        this.mesajlarOnClickInterface =mesajlarOnClickInterface;
        this.context=context;
    }

    public void addMesajList(List<Mesaj> mesajList) {
        this.mesajlar = mesajList;
    }

    @Override
    public int getItemCount() {
        return mesajlar != null ? mesajlar.size() : 0;
    }

    @NonNull
    @Override
    public MesajlarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemMesajkutusuBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_mesajkutusu, parent, false);
        return new MesajlarAdapter.MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListItemMesajkutusuBinding binding;


        MyViewHolder(ListItemMesajkutusuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.mesajkutusuCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mesajlarOnClickInterface.onItemClick(binding.getMesaj());
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(final MesajlarAdapter.MyViewHolder holder, final int position) {
        holder.binding.setMesaj(mesajlar.get(position));
    }

    public interface MesajlarOnClickInterface {
        void onItemClick(Mesaj mesaj);
    }
}
