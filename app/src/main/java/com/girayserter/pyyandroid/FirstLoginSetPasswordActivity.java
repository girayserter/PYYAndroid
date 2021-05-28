package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstLoginSetPasswordActivity extends AppCompatActivity {

    EditText txt_sifretekrar;
    EditText txt_sifre;
    Button btn_sifrekaydet;
    Database database;
    String kullaniciadi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login_set_password);

        database=new Database(this);

        txt_sifretekrar=findViewById(R.id.txt_sifretekrar);
        txt_sifre=findViewById(R.id.txt_sifre);
        btn_sifrekaydet=findViewById(R.id.btn_sifrekaydet);

        btn_sifrekaydet.setOnClickListener(v -> {
            String myPass=txt_sifre.getText().toString();
            String myPass2=txt_sifretekrar.getText().toString();
            if(myPass.equals(myPass2)){
                database.SifreDegistir(kullaniciadi,myPass);
                Toast.makeText(this,"Şifreniz başarıyla değiştirilmiştir.\nYeni şifrenizle giriş yapabilirsiniz.",Toast.LENGTH_LONG).show();

                finish();
            }
            else{
                Toast.makeText(this,"Şifreler eşleşmiyor tekrar deneyin",Toast.LENGTH_LONG).show();
            }
        });
    }
}