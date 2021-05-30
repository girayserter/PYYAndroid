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
import android.widget.Button;

import com.girayserter.pyyandroid.adapters.ProjelerAdapter;
import com.girayserter.pyyandroid.databinding.ActivityAdminAnasayfaBinding;

public class AdminAnasayfaActivity extends AppCompatActivity {

    ActivityAdminAnasayfaBinding binding;
    ProjelerAdapter adapter;
    Database database;
    Button btn_projeolustur;
    Button btn_personelgrubuolustur;
    Button btn_kullanicilar;
    Button btn_mesajlar;


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

        binding.rcvProjeler.setHasFixedSize(true);
        binding.rcvProjeler.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ProjelerAdapter();
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

    }

    private void projelerYenile(){
        adapter.addProjeList(database.tabloyuAlProjeler());
        adapter.notifyDataSetChanged();
    }
}