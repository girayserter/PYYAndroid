package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Personel;

import java.util.List;

public class KullaniciDetayActivity extends AppCompatActivity {
    EditText txt_kullaniciadi;
    EditText txt_ad;
    EditText txt_soyad;
    EditText txt_pozisyon;
    Spinner sp_yetki;
    Button btn_kaydet;
    Button btn_sil;
    Database database;

    Kullanici kullanici=new Kullanici();

    String[] intervals={"Personel",
            "Admin"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_detay);

        Bundle bundle = getIntent().getExtras();

        txt_kullaniciadi=findViewById(R.id.txt_kullaniciadi);
        txt_ad=findViewById(R.id.txt_ad);
        txt_soyad=findViewById(R.id.txt_soyad);
        txt_pozisyon=findViewById(R.id.txt_pozisyon);
        sp_yetki=findViewById(R.id.sp_yetki);
        btn_kaydet=findViewById(R.id.btn_kaydet);
        btn_sil=findViewById(R.id.btn_sil);
        database=new Database(this);

        ArrayAdapter adapterInterval=new ArrayAdapter(this, R.layout.spinner_item,intervals);
        adapterInterval.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sp_yetki.setAdapter(adapterInterval);

        btn_kaydet.setOnClickListener(v -> {
            if(bundle != null){
                if(txt_ad.getText().equals("")||txt_pozisyon.getText().equals("")||txt_kullaniciadi.getText().equals("")||txt_soyad.getText().equals("")||sp_yetki.getSelectedItem().toString().equals("")){
                    Toast.makeText(this,"Lütfen boş alan bırakmayınız",Toast.LENGTH_LONG).show();
                }else {
                    database.kullaniciGuncelle(bundle.getInt("id"), txt_kullaniciadi.getText().toString(), sp_yetki.getSelectedItem().toString());
                    database.personelGuncelle(bundle.getInt("id"), txt_ad.getText().toString(), txt_soyad.getText().toString(), txt_pozisyon.getText().toString());
                    setResult(RESULT_OK);
                    finish();
                }
            }
            else{
                if(txt_ad.getText().equals("")||txt_pozisyon.getText().equals("")||txt_kullaniciadi.getText().equals("")||txt_soyad.getText().equals("")||sp_yetki.getSelectedItem().toString().equals("")){
                    Toast.makeText(this,"Lütfen boş alan bırakmayınız",Toast.LENGTH_LONG).show();
                }else {
                    database.kullaniciKaydet(txt_kullaniciadi.getText().toString(), sp_yetki.getSelectedItem().toString());
                    database.personelKaydet(txt_kullaniciadi.getText().toString(), txt_ad.getText().toString(), txt_soyad.getText().toString(), txt_pozisyon.getText().toString());
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        btn_sil.setOnClickListener(v -> {
            database.kullaniciSil(bundle.getInt("id"));
            setResult(RESULT_OK);
            finish();
        });


        if(bundle != null){
            kullanici.kullanici_adi=bundle.getString("kullanici_adi");
            kullanici.ad=bundle.getString("ad");
            kullanici.soyad=bundle.getString("soyad");
            kullanici.pozisyon=bundle.getString("pozisyon");
            kullanici.yetki=bundle.getString("yetki");

            txt_kullaniciadi.setText(kullanici.kullanici_adi);
            txt_ad.setText(kullanici.ad);
            txt_soyad.setText(kullanici.soyad);
            txt_pozisyon.setText(kullanici.pozisyon);
            if(kullanici.yetki.equals("Personel")){
                sp_yetki.setSelection(0);
            }
            else {
                sp_yetki.setSelection(1);
            }

            btn_sil.setVisibility(View.VISIBLE);
        }

    }
}