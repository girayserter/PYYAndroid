package com.girayserter.pyyandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.girayserter.pyyandroid.R;
import com.girayserter.pyyandroid.databinding.ListItemKullanicilarBinding;
import com.girayserter.pyyandroid.databinding.ListItemPersonelGrubuOlusturBinding;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Personel;

import java.util.ArrayList;
import java.util.List;

public class KullanicilarAdapter extends RecyclerView.Adapter<KullanicilarAdapter.MyViewHolder> {

    private List<Kullanici> kullanicilar;
    private Context context;
    private Kullanici seciliKullanici=new Kullanici();
    private RcvOnClickInterface rcvOnClickInterface;


    public KullanicilarAdapter(Context context,RcvOnClickInterface rcvOnClickInterface) {
        kullanicilar = new ArrayList<>();
        this.context=context;
        this.rcvOnClickInterface=rcvOnClickInterface;
    }

    public void addKullaniciList(List<Kullanici> kullaniciList) {
        this.kullanicilar = kullaniciList;
    }


    @Override
    public int getItemCount() {
        return kullanicilar != null ? kullanicilar.size() : 0;
    }

    @NonNull
    @Override
    public KullanicilarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemKullanicilarBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_kullanicilar, parent, false);
        return new KullanicilarAdapter.MyViewHolder(binding);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListItemKullanicilarBinding binding;

        MyViewHolder(ListItemKullanicilarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.kullaniciCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rcvOnClickInterface.onItemClick(binding.getKullanici());

                }
            });

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context,getLayoutPosition(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBindViewHolder(final KullanicilarAdapter.MyViewHolder holder, final int position) {
        holder.binding.setKullanici(kullanicilar.get(position));

    }


    public interface RcvOnClickInterface {
        void onItemClick(Kullanici kullanici);
    }


    public Kullanici getSeciliKullanici() {
        return seciliKullanici;
    }
}
