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

import com.girayserter.pyyandroid.adapters.MesajlarAdapter;
import com.girayserter.pyyandroid.databinding.ActivityMesajlarBinding;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Mesaj;

public class MesajlarActivity extends AppCompatActivity implements MesajlarAdapter.MesajlarOnClickInterface{

    ActivityMesajlarBinding binding;
    SessionManagement session;
    Kullanici kullanici;
    Database database;
    MesajlarAdapter adapter;
    int kutu=1;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if(kutu==2){
                            mesajlarYenile();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mesajlar);

        session = new SessionManagement(getApplicationContext());
        kullanici=session.getUser();
        database=new Database(this);
        adapter=new MesajlarAdapter(this,this);

        binding.rcvMesajlar.setHasFixedSize(true);
        binding.rcvMesajlar.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvMesajlar.setAdapter(adapter);

        mesajlarYenile();

        binding.btnGelenkutusu.setOnClickListener(v ->{
            kutu=1;
            mesajlarYenile();
            binding.txtKutuadi.setText("Gelen Kutusu");
        });

        binding.btnGonderilmismesajlar.setOnClickListener(v -> {
            kutu=2;
            mesajlarYenile();
            binding.txtKutuadi.setText("Giden Kutusu");
        });

        binding.btnYenimesaj.setOnClickListener(v -> {
            Intent intent=new Intent(this,YeniMesajActivity.class);
            intent.putExtra("mod",1);
            mStartForResult.launch(intent);
        });
    }

    private void mesajlarYenile() {
        if(kutu==1){
            adapter.addMesajList(database.gelenKutusuAl(kullanici.id));
        }
        else{
            adapter.addMesajList(database.gidenKutusuAl(kullanici.id));
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(Mesaj mesaj) {
        boolean cevapla=false;
        if(kutu==1){
            cevapla=true;
        }
        else{
            cevapla=false;
        }
        Intent intent=new Intent(this,YeniMesajActivity.class);
        intent.putExtra("mod",3);
        intent.putExtra("kutu",kutu);
        intent.putExtra("cevapla",cevapla);
        intent.putExtra("mesajid",mesaj.id);
        mStartForResult.launch(intent);
    }
}