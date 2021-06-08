package com.girayserter.pyyandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.girayserter.pyyandroid.R;
import com.girayserter.pyyandroid.databinding.ListItemPersonelGrubuOlusturBinding;
import com.girayserter.pyyandroid.databinding.ListItemProjeCalisanBinding;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Personel;
import com.girayserter.pyyandroid.models.ProjePersonel;

import java.util.ArrayList;
import java.util.List;

public class ProjeCalisanAdapter extends RecyclerView.Adapter<ProjeCalisanAdapter.MyViewHolder> {

    private List<ProjePersonel> personeller;
    Context context;
    private ProjeCalisanAdapter.RcvOnClickInterface rcvOnClickInterface;


    public ProjeCalisanAdapter(Context context) {
        personeller = new ArrayList<>();
        this.context=context;
    }

    public ProjeCalisanAdapter(Context context, ProjeCalisanAdapter.RcvOnClickInterface rcvOnClickInterface) {
        personeller = new ArrayList<>();
        this.context=context;
        this.rcvOnClickInterface=rcvOnClickInterface;
    }

    public interface RcvOnClickInterface {
        void onItemClick(ProjePersonel projePersonel);
    }

    public void addPersonelList(List<ProjePersonel> personelList) {
        this.personeller = personelList;
    }

    @Override
    public int getItemCount() {
        return personeller != null ? personeller.size() : 0;
    }

    @NonNull
    @Override
    public ProjeCalisanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemProjeCalisanBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_proje_calisan, parent, false);
        return new ProjeCalisanAdapter.MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListItemProjeCalisanBinding binding;

        MyViewHolder(ListItemProjeCalisanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.projecalisanCardview.setOnClickListener(v -> {
                rcvOnClickInterface.onItemClick(binding.getPersonel());
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(final ProjeCalisanAdapter.MyViewHolder holder, final int position) {
        holder.binding.setPersonel(personeller.get(position));

    }

}