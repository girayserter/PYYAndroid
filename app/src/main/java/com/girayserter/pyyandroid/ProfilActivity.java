package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.girayserter.pyyandroid.databinding.ActivityProfilBinding;
import com.girayserter.pyyandroid.models.Kullanici;

public class ProfilActivity extends AppCompatActivity {

    ActivityProfilBinding binding;
    SessionManagement session;
    Kullanici kullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profil);

        session = new SessionManagement(getApplicationContext());
        kullanici=session.getUser();

        binding.txtId.setText(String.valueOf(kullanici.id));
        binding.txtKullaniciadi.setText(kullanici.kullanici_adi);
        binding.txtAd.setText(kullanici.ad);
        binding.txtSoyad.setText(kullanici.soyad);
        binding.txtPozisyon.setText(kullanici.pozisyon);

    }
}