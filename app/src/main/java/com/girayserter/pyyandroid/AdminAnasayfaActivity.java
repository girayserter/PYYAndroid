package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.girayserter.pyyandroid.databinding.ActivityAdminAnasayfaBinding;

import java.util.List;

public class AdminAnasayfaActivity extends AppCompatActivity {

    ActivityAdminAnasayfaBinding binding;
    ProjelerAdapter adapter;
    List<Proje> projeler;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_anasayfa);

        database=new Database(this);

        binding.rcvProjeler.setHasFixedSize(true);
        binding.rcvProjeler.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ProjelerAdapter();
        binding.rcvProjeler.setAdapter(adapter);

        adapter.addProjeList(database.tabloyuAl());
        adapter.notifyDataSetChanged();

    }
}