package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KullaniciGirisActivity extends AppCompatActivity {

    EditText txt_kullaniciadi;
    EditText txt_sifre;
    Button btn_girisyap;
    Database database;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_giris);

        database=new Database(this);
        session = new SessionManagement(getApplicationContext());

        txt_kullaniciadi=findViewById(R.id.txt_kullaniciadi);
        txt_sifre=findViewById(R.id.txt_sifre);
        btn_girisyap=findViewById(R.id.btn_girisyap);

        btn_girisyap.setOnClickListener(v -> {
            String ad,sifre;
            ad=txt_kullaniciadi.getText().toString();
            sifre=txt_sifre.getText().toString();

            KullaniciBilgileri kullanici =database.kullaniciAl(ad,sifre);
            if(kullanici.kullaniciAdi.equals("")){
                Toast.makeText(this,"Kullanıcı adı veya şifre yanlış",Toast.LENGTH_LONG).show();
            }
            else{
                session.createLoginSession(database.kullaniciBilgiAl(kullanici.kullaniciAdi));
                if(kullanici.sifre.equals("")){
                    Intent intent=new Intent(this,FirstLoginSetPasswordActivity.class);
                    intent.putExtra("kullaniciadi",kullanici.kullaniciAdi);
                    startActivity(intent);
                }
                else {
                    if(kullanici.yetki.equals("Admin")){
                        Intent intent=new Intent(this,AdminAnasayfaActivity.class);
                        intent.putExtra("kullaniciadi",kullanici.kullaniciAdi);
                        startActivity(intent);
                    }
                    else if(kullanici.yetki.equals("Personel")){
                        Intent intent=new Intent(this,AdminAnasayfaActivity.class);
                        intent.putExtra("kullaniciadi",kullanici.kullaniciAdi);
                        startActivity(intent);
                    }
                }
            }

        });
    }
}