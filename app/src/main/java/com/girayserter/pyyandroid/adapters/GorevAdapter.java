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
import com.girayserter.pyyandroid.databinding.ListItemGorevBinding;
import com.girayserter.pyyandroid.databinding.ListItemGorevListesiBinding;
import com.girayserter.pyyandroid.models.Gorev;
import com.girayserter.pyyandroid.models.GorevidTamamlandi;

import java.util.ArrayList;
import java.util.List;

public class GorevAdapter extends RecyclerView.Adapter<GorevAdapter.MyViewHolder> {

    private List<Gorev> gorevList=new ArrayList<>();
    private Context context;
    private ArrayList<GorevidTamamlandi> al=new ArrayList<>();


    public GorevAdapter(Context context) {
        gorevList = new ArrayList<>();
        this.context = context;
    }

    public void addGorevListeList(List<Gorev> gorevList) {
        this.gorevList = gorevList;
    }

    @Override
    public int getItemCount() {
        return gorevList != null ? gorevList.size() : 0;
    }

    @NonNull
    @Override
    public GorevAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemGorevBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_gorev, parent, false);
        return new GorevAdapter.MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListItemGorevBinding binding;


        MyViewHolder(ListItemGorevBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.chkTamamlandi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for(int i=0;i<gorevList.size();i++){
                        if(al.get(i).gorevid==binding.getGorev().id){
                            al.get(i).tamamlandi=isChecked;
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
    public void onBindViewHolder(final GorevAdapter.MyViewHolder holder, final int position) {
        holder.binding.setGorev(gorevList.get(position));
    }

    public ArrayList<GorevidTamamlandi> gorevTamamlandiListAl(){
        return al;
    }

    public void arrayDoldur(){
        al=new ArrayList<>();
        for(int i=0;i<gorevList.size();i++){
            GorevidTamamlandi gt=new GorevidTamamlandi(gorevList.get(i).id,gorevList.get(i).tamamlandi);
            al.add(gt);
        }
    }


}