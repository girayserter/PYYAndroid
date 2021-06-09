package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.girayserter.pyyandroid.databinding.ActivityGorevListesiOlusturBinding;
import com.girayserter.pyyandroid.models.Personel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class GorevListesiOlusturActivity extends AppCompatActivity {

    ActivityGorevListesiOlusturBinding binding;
    Bundle bundle;
    Database database;
    int projeid=0;
    List<Personel> personelList;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gorev_listesi_olustur);

        bundle=getIntent().getExtras();
        database=new Database(this);

        projeid=bundle.getInt("projeid");

        binding.txtSontarih.setOnClickListener(v -> {
            btnDatePicker(binding.txtSontarih);
        });

        binding.btnOlustur.setOnClickListener(v -> {
            int projeyoneticisiid=personelList.get(binding.spPersonel.getSelectedItemPosition()).id;
            if(binding.txtSontarih.equals("")){
                Toast.makeText(this,"Lütfen bir bitiş tarihi belirleyin",Toast.LENGTH_LONG).show();
            }
            else {
                database.yeniGorevListesi(
                        binding.txtGorevlisteadi.getText().toString(),
                        projeyoneticisiid,
                        projeid,
                        binding.txtSontarih.getText().toString());
                setResult(RESULT_OK);
                finish();
            }
        });

        ArrayAdapter adapterInterval2=new ArrayAdapter(this, R.layout.spinner_item,personellerAl());
        adapterInterval2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spPersonel.setAdapter(adapterInterval2);

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