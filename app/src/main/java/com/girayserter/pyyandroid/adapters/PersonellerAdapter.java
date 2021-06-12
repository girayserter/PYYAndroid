package com.girayserter.pyyandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.girayserter.pyyandroid.PersonelGrubuOlusturActivity;
import com.girayserter.pyyandroid.R;
import com.girayserter.pyyandroid.databinding.ListItemPersonelGrubuOlusturBinding;
import com.girayserter.pyyandroid.databinding.ListItemProjectBinding;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Personel;
import com.girayserter.pyyandroid.models.Proje;

import java.util.ArrayList;
import java.util.List;

public class PersonellerAdapter extends RecyclerView.Adapter<PersonellerAdapter.MyViewHolder> {

    private List<Personel> personeller;
    private ArrayList<Integer> seciliPersoneller=new ArrayList<>();
    Context context;
    Boolean multiSelect=false;
    private PersonellerAdapter.RcvOnClickInterface rcvOnClickInterface;
    private int selectedPersonelId = 0;
    private ArrayList<Integer> selectCheck = new ArrayList<>();


    public PersonellerAdapter(Context context) {
        personeller = new ArrayList<>();
        this.context=context;

        for (int i = 0; i < personeller.size(); i++) {
            selectCheck.add(0);
        }
    }

    public PersonellerAdapter(Context context, Boolean multiSelect) {
        personeller = new ArrayList<>();
        this.context=context;
        this.multiSelect=multiSelect;
        this.rcvOnClickInterface=rcvOnClickInterface;
    }

    public interface RcvOnClickInterface {
        void onItemClick(Kullanici kullanici);
    }

    public void addPersonelList(List<Personel> personelList) {
        this.personeller = personelList;
        for (int i = 0; i < personeller.size(); i++) {
            selectCheck.add(0);
        }
    }

    @Override
    public int getItemCount() {
        return personeller != null ? personeller.size() : 0;
    }

    @NonNull
    @Override
    public PersonellerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemPersonelGrubuOlusturBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_personel_grubu_olustur, parent, false);
        return new PersonellerAdapter.MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListItemPersonelGrubuOlusturBinding binding;

        MyViewHolder(ListItemPersonelGrubuOlusturBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.chkPersonel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(multiSelect==true) {
                        if (isChecked) {
                            seciliPersoneller.add(binding.getPersonel().id);
                        } else {
                            seciliPersoneller.remove(binding.getPersonel().id);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(final PersonellerAdapter.MyViewHolder holder, final int position) {
        holder.binding.setPersonel(personeller.get(position));

        if (selectCheck.get(position) == 1) {
            selectedPersonelId=personeller.get(position).id;
            holder.binding.chkPersonel.setChecked(true);
        } else {
            holder.binding.chkPersonel.setChecked(false);
        }


        holder.binding.chkPersonel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(multiSelect==false) {
                    for (int k = 0; k < selectCheck.size(); k++) {
                        if (k == position) {
                            selectCheck.set(k, 1);

                        } else {
                            selectCheck.set(k, 0);

                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });

    }

    public ArrayList<Integer> getSeciliPersoneller() {
        return seciliPersoneller;
    }

    public int getSeciliPersonel(){
        return selectedPersonelId;
    }
}
