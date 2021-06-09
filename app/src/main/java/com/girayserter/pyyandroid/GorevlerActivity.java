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
import android.widget.Adapter;

import com.girayserter.pyyandroid.adapters.GorevAdapter;
import com.girayserter.pyyandroid.adapters.ProjelerAdapter;
import com.girayserter.pyyandroid.databinding.ActivityGorevlerBinding;
import com.girayserter.pyyandroid.models.GorevidTamamlandi;
import com.girayserter.pyyandroid.models.Kullanici;

import java.util.ArrayList;

public class GorevlerActivity extends AppCompatActivity {

    ActivityGorevlerBinding binding;
    GorevAdapter adapter;
    Database database;
    Bundle bundle;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gorevler);

        adapter=new GorevAdapter(this);
        bundle=getIntent().getExtras();
        database=new Database(this);
        session = new SessionManagement(getApplicationContext());
        kullanici=session.getUser();

        binding.rcvGorevler.setHasFixedSize(true);
        binding.rcvGorevler.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvGorevler.setAdapter(adapter);

        projelerYenile();

        binding.txtGorevlisteadi.setText(bundle.getString("listeadi"));
        binding.txtAtananpersonel.setText(database.iddenAdSoyadAl(bundle.getInt("personelid")));
        binding.txtSontarih.setText(bundle.getString("deadline"));

        if(kullanici.yetki.equals("Yönetici")||kullanici.yetki.equals("Admin")){
            binding.btnGorevekle.setVisibility(View.VISIBLE);
        }

        binding.btnKaydet.setOnClickListener(v -> {

            database.gorevListesiGuncelle(adapter.gorevTamamlandiListAl(),bundle.getInt("listeid"));
            setResult(RESULT_OK);
            finish();
        });

        binding.btnGorevekle.setOnClickListener(v -> {
            Intent intent=new Intent(this,GorevEkleActivity.class);
            intent.putExtra("listeid",bundle.getInt("listeid"));
            mStartForResult.launch(intent);
        });

    }

    private void projelerYenile(){
        adapter.addGorevListeList(database.gorevListesiDetay(bundle.getInt("listeid")));
        adapter.arrayDoldur();
        adapter.notifyDataSetChanged();
    }
}