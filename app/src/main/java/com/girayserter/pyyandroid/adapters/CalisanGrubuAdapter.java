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
import com.girayserter.pyyandroid.databinding.ListItemCalisanGrubuBinding;
import com.girayserter.pyyandroid.databinding.ListItemPersonelGrubuOlusturBinding;
import com.girayserter.pyyandroid.models.CalisanGrubu;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Personel;

import java.util.ArrayList;
import java.util.List;

public class CalisanGrubuAdapter extends RecyclerView.Adapter<CalisanGrubuAdapter.MyViewHolder>{

    private List<CalisanGrubu> calisanGrubuList;
    private ArrayList<Integer> seciliPersoneller=new ArrayList<>();
    Context context;
    Boolean multiSelect=false;
    private PersonellerAdapter.RcvOnClickInterface rcvOnClickInterface;
    private int selectedPersonelId = 0;
    private ArrayList<Integer> selectCheck = new ArrayList<>();


    public CalisanGrubuAdapter(Context context) {
        calisanGrubuList = new ArrayList<>();
        this.context=context;

        for (int i = 0; i < calisanGrubuList.size(); i++) {
            selectCheck.add(0);
        }
    }

    public CalisanGrubuAdapter(Context context, Boolean multiSelect) {
        calisanGrubuList = new ArrayList<>();
        this.context=context;
        this.multiSelect=multiSelect;
        this.rcvOnClickInterface=rcvOnClickInterface;
    }

    public interface RcvOnClickInterface {
        void onItemClick(Kullanici kullanici);
    }

    public void addPersonelList(List<CalisanGrubu> calisanGrubuList) {
        this.calisanGrubuList = calisanGrubuList;
        for (int i = 0; i < calisanGrubuList.size(); i++) {
            selectCheck.add(0);
        }
    }

    @Override
    public int getItemCount() {
        return calisanGrubuList != null ? calisanGrubuList.size() : 0;
    }

    @NonNull
    @Override
    public CalisanGrubuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemCalisanGrubuBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_calisan_grubu, parent, false);
        return new CalisanGrubuAdapter.MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListItemCalisanGrubuBinding binding;

        MyViewHolder(ListItemCalisanGrubuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.chkPersonel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(multiSelect==true) {
                        if (isChecked) {
                            seciliPersoneller.add(binding.getGrup().id);
                        } else {
                            seciliPersoneller.remove(binding.getGrup().id);
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
    public void onBindViewHolder(final CalisanGrubuAdapter.MyViewHolder holder, final int position) {
        holder.binding.setGrup(calisanGrubuList.get(position));

        if (selectCheck.get(position) == 1) {
            selectedPersonelId=calisanGrubuList.get(position).id;
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

    public int getSeciliGrup(){
        return selectedPersonelId;
    }
}
