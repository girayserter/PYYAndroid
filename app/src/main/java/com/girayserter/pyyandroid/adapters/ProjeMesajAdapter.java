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
import com.girayserter.pyyandroid.databinding.ListItemProjeMesajAlinanBinding;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Mesaj;
import com.girayserter.pyyandroid.models.Personel;
import com.girayserter.pyyandroid.models.ProjeMesaj;

import java.util.ArrayList;
import java.util.List;

public class ProjeMesajAdapter extends RecyclerView.Adapter<ProjeMesajAdapter.MyViewHolder>{

    private List<ProjeMesaj> mesajlar;
    private Context context;
    private Kullanici kullanici;


    public ProjeMesajAdapter(Context context,Kullanici kullanici) {
        mesajlar = new ArrayList<>();
        this.kullanici=kullanici;
        this.context=context;
    }

    public void addMesajList(List<ProjeMesaj> mesajList) {
        this.mesajlar = mesajList;
    }

    @Override
    public int getItemCount() {
        return mesajlar != null ? mesajlar.size() : 0;
    }

    @NonNull
    @Override
    public ProjeMesajAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemProjeMesajAlinanBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_proje_mesaj_alinan, parent, false);
        return new ProjeMesajAdapter.MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListItemProjeMesajAlinanBinding binding;


        MyViewHolder(ListItemProjeMesajAlinanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(final ProjeMesajAdapter.MyViewHolder holder, final int position) {
        holder.binding.setMesaj(mesajlar.get(position));
        holder.binding.setKullanici(kullanici);
    }

}
