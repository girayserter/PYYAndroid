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
import com.girayserter.pyyandroid.models.Personel;
import com.girayserter.pyyandroid.models.Proje;

import java.util.ArrayList;
import java.util.List;

public class PersonellerAdapter extends RecyclerView.Adapter<PersonellerAdapter.MyViewHolder> {

    private List<Personel> personeller;
    private ArrayList<Integer> seciliPersoneller=new ArrayList<>();
    Context context;

    interface OnItemCheckListener {
        void onItemCheck(Personel personel);
        void onItemUncheck(Personel personel);
    }

    public PersonellerAdapter(Context context) {
        personeller = new ArrayList<>();
        this.context=context;
    }

    public void addPersonelList(List<Personel> personelList) {
        this.personeller = personelList;
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
                    if(isChecked){
                        seciliPersoneller.add(Integer.parseInt(binding.txtId.getText().toString()));
                    }
                    else {
                        seciliPersoneller.remove(Integer.parseInt(binding.txtId.getText().toString()));
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
        /*if (holder instanceof MyViewHolder) {
            final Personel currentItem = personeller.get(position);

            ((MyViewHolder) holder).binding.se(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MyViewHolder) holder).binding.chkPersonel.setChecked(
                            !((MyViewHolder) holder).binding.chkPersonel.isChecked());
                    if (((MyViewHolder) holder).binding.chkPersonel.isChecked()) {
                        onItemClick.onItemCheck(currentItem);
                    } else {
                        onItemClick.onItemUncheck(currentItem);
                    }
                }
            });
        }*/
    }

    public ArrayList<Integer> getSeciliPersoneller() {
        return seciliPersoneller;
    }
}
