package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.girayserter.pyyandroid.databinding.ActivityYeniMesajBinding;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Mesaj;
import com.girayserter.pyyandroid.models.Personel;

import java.util.List;

public class YeniMesajActivity extends AppCompatActivity {

    ActivityYeniMesajBinding binding;
    Database database;
    Bundle bundle;
    List<Personel> personelList;
    SessionManagement session;
    Kullanici kullanici;
    Mesaj mesaj;

    int mod=0;/**1->yeni mesaj
                *2->mesaj cevaplama
                *3->mesaj görüntüleme
                */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_yeni_mesaj);

        database=new Database(this);
        bundle=getIntent().getExtras();
        session = new SessionManagement(getApplicationContext());
        kullanici=session.getUser();

        ArrayAdapter adapterInterval=new ArrayAdapter(this, R.layout.spinner_item,personellerAl());
        adapterInterval.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spAlici.setAdapter(adapterInterval);

        mod=bundle.getInt("mod");

        if(mod==1){
            binding.txtGonderen.setVisibility(View.GONE);
            binding.btnCevapla.setVisibility(View.GONE);
            binding.btnSil.setVisibility(View.GONE);
            binding.lblGonderen.setVisibility(View.GONE);

            binding.btnGonder.setOnClickListener(v -> {
                if(binding.txtMesajkonusu.getText().toString().equals("")){
                    Toast.makeText(this,"Lütfen bir konu giriniz",Toast.LENGTH_LONG).show();
                }else{
                    database.mesajGonder(
                            kullanici.id,
                            personelList.get(binding.spAlici.getSelectedItemPosition()).id,
                            binding.txtMesaj.getText().toString(),
                            binding.txtMesajkonusu.getText().toString());

                }
                setResult(RESULT_OK);
                finish();
            });
        }
        else if(mod==2){
            mesaj=new Mesaj();
            mesaj.id=bundle.getInt("mesajid");
            mesaj.gonderen=bundle.getInt("gonderen");
            mesaj.alici=bundle.getInt("alici");
            mesaj.konu=bundle.getString("konu");

            binding.spAlici.setSelection(projeYoneticiIndexBul(mesaj.gonderen));
            binding.txtGonderen.setText(database.iddenAdSoyadAl(mesaj.alici));
            binding.txtMesajkonusu.setText(mesaj.konu);

            binding.txtGonderen.setFocusable(false);
            binding.txtGonderen.setClickable(false);
            binding.txtGonderen.setFocusableInTouchMode(false);
            binding.txtGonderen.setEnabled(false);
            binding.txtGonderen.setVisibility(View.GONE);
            binding.lblGonderen.setVisibility(View.GONE);


            binding.txtMesajkonusu.setFocusable(false);
            binding.txtMesajkonusu.setClickable(false);
            binding.txtMesajkonusu.setFocusableInTouchMode(false);
            binding.txtMesajkonusu.setEnabled(false);


            binding.spAlici.setFocusable(false);
            binding.spAlici.setClickable(false);
            binding.spAlici.setFocusableInTouchMode(false);
            binding.spAlici.setEnabled(false);


            binding.btnSil.setVisibility(View.GONE);
            binding.btnCevapla.setVisibility(View.GONE);


            binding.btnGonder.setOnClickListener(v -> {
                database.mesajGonder(
                        kullanici.id,
                        personelList.get(binding.spAlici.getSelectedItemPosition()).id,
                        binding.txtMesaj.getText().toString(),
                        binding.txtMesajkonusu.getText().toString());
                setResult(RESULT_OK);
                finish();
            });

        }
        else if(mod==3){
            mesaj=database.mesajAl(bundle.getInt("mesajid"));
            binding.spAlici.setSelection(projeYoneticiIndexBul(mesaj.alici));
            binding.txtGonderen.setText(database.iddenAdSoyadAl(mesaj.gonderen));
            binding.txtMesaj.setText(mesaj.mesaj);
            binding.txtMesajkonusu.setText(mesaj.konu);

            binding.txtGonderen.setFocusable(false);
            binding.txtGonderen.setClickable(false);
            binding.txtGonderen.setFocusableInTouchMode(false);
            binding.txtGonderen.setEnabled(false);


            binding.txtMesajkonusu.setFocusable(false);
            binding.txtMesajkonusu.setClickable(false);
            binding.txtMesajkonusu.setFocusableInTouchMode(false);
            binding.txtMesajkonusu.setEnabled(false);


            binding.txtMesaj.setFocusable(false);
            binding.txtMesaj.setClickable(false);
            binding.txtMesaj.setFocusableInTouchMode(false);
            binding.txtMesaj.setEnabled(false);

            binding.spAlici.setFocusable(false);
            binding.spAlici.setClickable(false);
            binding.spAlici.setFocusableInTouchMode(false);
            binding.spAlici.setEnabled(false);
            binding.spAlici.setVisibility(View.GONE);
            binding.lblAlici.setVisibility(View.GONE);

            binding.btnGonder.setVisibility(View.GONE);
            if(bundle.getInt("kutu")==2){
                binding.btnCevapla.setVisibility(View.GONE);
            }

            binding.btnCevapla.setOnClickListener(v -> {
                Intent intent=new Intent(this,YeniMesajActivity.class);
                intent.putExtra("mod",2);
                intent.putExtra("gonderen",mesaj.gonderen);
                intent.putExtra("alici",mesaj.alici);
                intent.putExtra("konu",mesaj.konu);
                intent.putExtra("mesajid",mesaj.id);
                startActivity(intent);
            });

            binding.btnSil.setOnClickListener(v -> {
                database.mesajSil(mesaj.id);
                setResult(RESULT_OK);
                finish();
            });
        }




    }

    private String[] personellerAl(){
        personelList=database.tabloyuAlPersoneller();
        String[] isimSoyisimler=new String[personelList.size()];
        for(int i=0;i<personelList.size();i++){
            isimSoyisimler[i]=personelList.get(i).ad+" "+personelList.get(i).soyad;
        }
        return isimSoyisimler;
    }

    private int projeYoneticiIndexBul(int id){
        String isim=database.iddenAdSoyadAl(id);
        for(int i=0;i<binding.spAlici.getCount();i++){
            if(isim.equals(binding.spAlici.getItemAtPosition(i).toString())){
                return i;
            }
        }
        return 0;
    }
}