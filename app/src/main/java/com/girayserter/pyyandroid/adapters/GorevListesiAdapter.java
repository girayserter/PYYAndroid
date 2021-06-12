package com.girayserter.pyyandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.girayserter.pyyandroid.R;
import com.girayserter.pyyandroid.databinding.ListItemGorevListesiBinding;
import com.girayserter.pyyandroid.databinding.ListItemProjectBinding;
import com.girayserter.pyyandroid.models.GorevListe;
import com.girayserter.pyyandroid.models.Proje;

import java.util.ArrayList;
import java.util.List;

public class GorevListesiAdapter extends RecyclerView.Adapter<GorevListesiAdapter.MyViewHolder> {

    private List<GorevListe> gorevListeList;
    private GorevListeOnClickInterface gorevListeOnClickInterface;
    private Context context;
    private Boolean duzenlemeYetki;


    public GorevListesiAdapter(Context context, GorevListeOnClickInterface gorevListeOnClickInterface,Boolean duzenlemeYetki) {
        gorevListeList = new ArrayList<>();
        this.gorevListeOnClickInterface = gorevListeOnClickInterface;
        this.context = context;
        this.duzenlemeYetki=duzenlemeYetki;
    }

    public void addGorevListeList(List<GorevListe> gorevListeList) {
        this.gorevListeList = gorevListeList;
    }

    @Override
    public int getItemCount() {
        return gorevListeList != null ? gorevListeList.size() : 0;
    }

    @NonNull
    @Override
    public GorevListesiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemGorevListesiBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_gorev_listesi, parent, false);
        return new GorevListesiAdapter.MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListItemGorevListesiBinding binding;


        MyViewHolder(ListItemGorevListesiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.gorevlistesiCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gorevListeOnClickInterface.onItemClick(binding.getGorevListe());
                }
            });

            if(!duzenlemeYetki) {
                binding.txtSoyisim.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(final GorevListesiAdapter.MyViewHolder holder, final int position) {
        holder.binding.setGorevListe(gorevListeList.get(position));
    }

    public interface GorevListeOnClickInterface {
        void onItemClick(GorevListe gorevListe);
    }
}