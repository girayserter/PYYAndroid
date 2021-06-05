package com.girayserter.pyyandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.girayserter.pyyandroid.adapters.KullanicilarAdapter;
import com.girayserter.pyyandroid.adapters.ProjelerAdapter;
import com.girayserter.pyyandroid.databinding.ActivityKullanicilarBinding;
import com.girayserter.pyyandroid.models.Kullanici;

import java.time.Instant;

public class KullanicilarActivity extends AppCompatActivity implements KullanicilarAdapter.RcvOnClickInterface {

    ActivityKullanicilarBinding binding;
    KullanicilarAdapter adapter;
    Database database;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        kullanicilarYenile();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kullanicilar);

        database=new Database(this);

        binding.rcvKullanicilar.setHasFixedSize(true);
        binding.rcvKullanicilar.setLayoutManager(new LinearLayoutManager(this));
        adapter=new KullanicilarAdapter(this,this);
        binding.rcvKullanicilar.setAdapter(adapter);

        kullanicilarYenile();


        binding.btnKullaniciekle.setOnClickListener(v -> {
            mStartForResult.launch(new Intent(this,KullaniciDetayActivity.class));
        });


    }

    private void kullanicilarYenile(){
        adapter.addKullaniciList(database.kullaniciTabloAl());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Kullanici kullanici) {
        Intent intent=new Intent(this,KullaniciDetayActivity.class);
        intent.putExtra("id",kullanici.id);
        intent.putExtra("kullanici_adi", kullanici.kullanici_adi);
        intent.putExtra("ad", kullanici.ad);
        intent.putExtra("soyad", kullanici.soyad);
        intent.putExtra("pozisyon", kullanici.pozisyon);
        intent.putExtra("yetki", kullanici.yetki);
        mStartForResult.launch(intent);
    }
}