package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.girayserter.pyyandroid.adapters.CalisanGrubuAdapter;
import com.girayserter.pyyandroid.adapters.PersonellerAdapter;
import com.girayserter.pyyandroid.databinding.ActivityPersonelSecBinding;
import com.girayserter.pyyandroid.models.Personel;

import java.util.ArrayList;
import java.util.List;

public class PersonelSecActivity extends AppCompatActivity {

    ActivityPersonelSecBinding binding;
    PersonellerAdapter personelAdapter;
    CalisanGrubuAdapter calisanGrubuAdapter;
    Database database;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personel_sec);

        database=new Database(this);
        bundle=getIntent().getExtras();

        if(bundle.getString("mod").equals("calisan")) {
            binding.rcvCalisanlar.setHasFixedSize(true);
            binding.rcvCalisanlar.setLayoutManager(new LinearLayoutManager(this));
            personelAdapter = new PersonellerAdapter(this, false);
            binding.rcvCalisanlar.setAdapter(personelAdapter);

            personellerYenile();

            binding.btnEkle.setOnClickListener(v -> {
                ArrayList<Integer> secilen = new ArrayList<>();
                secilen.add(personelAdapter.getSeciliPersonel());
                try {
                    if (secilen == null) {
                        Toast.makeText(this, "Lütfen bir çalışan seçiniz", Toast.LENGTH_LONG).show();
                    } else {
                        database.projeyePersonelAta(bundle.getInt("projeid"), secilen.get(0), binding.txtRol.getText().toString());
                        setResult(RESULT_OK);
                        finish();
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "hata", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(bundle.getString("mod").equals("calisangrubu")){
            binding.rcvCalisanlar.setHasFixedSize(true);
            binding.rcvCalisanlar.setLayoutManager(new LinearLayoutManager(this));
            calisanGrubuAdapter= new CalisanGrubuAdapter(this,false);
            binding.rcvCalisanlar.setAdapter(calisanGrubuAdapter);

            calisanGrubuYenile();

            binding.txtRol.setVisibility(View.GONE);

            binding.btnEkle.setOnClickListener(v -> {
                ArrayList<Integer> secilen = new ArrayList<>();
                secilen.add(calisanGrubuAdapter.getSeciliGrup());
                try {
                    if (secilen == null) {
                        Toast.makeText(this, "Lütfen bir grup seçiniz", Toast.LENGTH_LONG).show();
                    } else {
                        database.projeyeGrupEkle(secilen.get(0),bundle.getInt("projeid"));
                        setResult(RESULT_OK);
                        finish();
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "hata", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void personellerYenile(){
        personelAdapter.addPersonelList(database.tabloyuAlPersoneller());
        personelAdapter.notifyDataSetChanged();
    }

    private void calisanGrubuYenile(){
        calisanGrubuAdapter.addPersonelList(database.calisanGruplari());
        calisanGrubuAdapter.notifyDataSetChanged();
    }
}