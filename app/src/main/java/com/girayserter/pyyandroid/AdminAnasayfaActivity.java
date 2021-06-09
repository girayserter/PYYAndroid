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
import android.view.View;
import android.widget.Button;

import com.girayserter.pyyandroid.adapters.ProjelerAdapter;
import com.girayserter.pyyandroid.databinding.ActivityAdminAnasayfaBinding;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Proje;

public class AdminAnasayfaActivity extends AppCompatActivity implements ProjelerAdapter.ProjelerOnClickInterface {

    ActivityAdminAnasayfaBinding binding;
    ProjelerAdapter adapter;
    Database database;
    Button btn_projeolustur;
    Button btn_personelgrubuolustur;
    Button btn_kullanicilar;
    Button btn_mesajlar;
    Proje proje=new Proje();
    SessionManagement session;
    Kullanici kullanici;


    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        projelerYenile();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_anasayfa);

        database=new Database(this);
        session=new SessionManagement(getApplicationContext());
        kullanici=session.getUser();

        binding.rcvProjeler.setHasFixedSize(true);
        binding.rcvProjeler.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ProjelerAdapter(this,this);
        binding.rcvProjeler.setAdapter(adapter);

        projelerYenile();

        btn_projeolustur=findViewById(R.id.btn_projeOlustur);
        btn_personelgrubuolustur=findViewById(R.id.btn_personelgrubuolustur);
        btn_kullanicilar=findViewById(R.id.btn_kullanicilar);
        btn_mesajlar=findViewById(R.id.btn_mesajlar);

        btn_projeolustur.setOnClickListener(v -> {
            mStartForResult.launch(new Intent(this,ProjeOlusturActivity.class));
        });

        btn_personelgrubuolustur.setOnClickListener(v -> {
            Intent intent =new Intent(this,PersonelGrubuOlusturActivity.class);
            startActivity(intent);
        });

        btn_kullanicilar.setOnClickListener(v -> {
            Intent intent =new Intent(this,KullanicilarActivity.class);
            startActivity(intent);
        });

        if(kullanici.yetki.equals("Admin")){
            binding.btnProfil.setVisibility(View.GONE);
        }
        else {
            binding.btnPersonelgrubuolustur.setVisibility(View.GONE);
            binding.btnKullanicilar.setVisibility(View.GONE);
            binding.btnProjeOlustur.setVisibility(View.GONE);

        }

    }

    private void projelerYenile(){
        if(kullanici.yetki.equals("Admin")){
            adapter.addProjeList(database.tabloyuAlProjeler());
        }
        else {
            adapter.addProjeList(database.kullaniciPanelProje(kullanici.id));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Proje proje) {
        Intent intent=new Intent(this,ProjeBilgiActivity.class);
        intent.putExtra("Id",proje.id);
        startActivity(intent);
    }
}