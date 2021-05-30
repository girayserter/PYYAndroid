package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ProjeOlusturActivity extends AppCompatActivity {

    EditText txt_projeadi;
    EditText txt_baslangictarihi;
    EditText txt_bitistarihi;
    EditText txt_projetanimi;
    Spinner sp_projeyoneticisi;
    Spinner sp_gelistirmemodeli;
    Button btn_olustur;
    Database database;
    List<Personel> personelList;
    private int mYear, mMonth, mDay, mHour, mMinute,mSecond;// Saat ve Tarihe dayalı verilerin tutulduğu değişkenler



    String[] intervals={"Çağlayan (Şelale) Modeli",
            "V Modeli",
            "Evrimsel Model",
            "Artırımsal Model",
            "Gelişigüzel Model",
            "Barok Modeli",
            "Helezonik-Spiral Model",
            "Araştırma Tabanlı Model"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje_olustur);

        txt_projeadi=findViewById(R.id.txt_projeadi);
        txt_baslangictarihi=findViewById(R.id.txt_baslangictarihi);
        txt_bitistarihi=findViewById(R.id.txt_bitistarihi);
        txt_projetanimi=findViewById(R.id.txt_projetanimi);
        sp_projeyoneticisi=findViewById(R.id.sp_projeyoneticisi);
        sp_gelistirmemodeli=findViewById(R.id.sp_gelistirmemodeli);
        btn_olustur=findViewById(R.id.btn_olustur);
        database=new Database(this);

        txt_baslangictarihi.setOnClickListener(v -> {
            btnDatePicker(txt_baslangictarihi);
        });

        txt_bitistarihi.setOnClickListener(v -> {
            btnDatePicker(txt_bitistarihi);
        });



        btn_olustur.setOnClickListener(v -> {
            String projeadi=txt_projeadi.getText().toString();
            String projetanimi=txt_projetanimi.getText().toString();
            String gelistirmeModeli=sp_gelistirmemodeli.getSelectedItem().toString();
            int projeyoneticisiid=personelList.get(sp_projeyoneticisi.getSelectedItemPosition()).id;
            String projebaslangic=txt_baslangictarihi.getText().toString();
            String projebitis=txt_bitistarihi.getText().toString();

            if(projeadi.equals("")){
                Toast.makeText(this,"Proje adı boş bırakılamaz",Toast.LENGTH_LONG).show();
            }else if(projebaslangic.equals(null)){
                Toast.makeText(this,"Lütfen bir başlangıç tarihi belirleyin",Toast.LENGTH_LONG).show();
            }else if(projebitis.equals(null)){
                Toast.makeText(this,"Lütfen bir bitiş tarihi belirleyin",Toast.LENGTH_LONG).show();
            }
            else {
                if(database.yeniProje(projeadi,projetanimi,projeyoneticisiid,gelistirmeModeli,projebaslangic,projebitis));
                setResult(RESULT_OK);
                finish();
            }
        });


        ArrayAdapter adapterInterval=new ArrayAdapter(this, R.layout.spinner_item,intervals);
        adapterInterval.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sp_gelistirmemodeli.setAdapter(adapterInterval);

        ArrayAdapter adapterInterval2=new ArrayAdapter(this, R.layout.spinner_item,personellerAl());
        adapterInterval2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sp_projeyoneticisi.setAdapter(adapterInterval2);
    }

    private String[] personellerAl(){
        personelList=database.tabloyuAlPersoneller();
        String[] isimSoyisimler=new String[personelList.size()];
        for(int i=0;i<personelList.size();i++){
            isimSoyisimler[i]=personelList.get(i).ad+" "+personelList.get(i).soyad;
        }
        return isimSoyisimler;
    }

    //Tarihi seç butonu fonksiyonu
    public void btnDatePicker(EditText txtDate)
    {
        // Şimdiki Zamanı Al
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);//Yıl bilgisi
        mMonth = c.get(Calendar.MONTH);//Ay bilgisi
        mDay = c.get(Calendar.DAY_OF_MONTH);//Gün bilgisi

        //Tarih seçmek için takvim açılması
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        //Bilgiler alındıktan sonra format ayarlama
                        txtDate.setText(year+
                                "/" + String.format("%02d", (monthOfYear + 1)) +
                                "/" + String.format("%02d", dayOfMonth));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}