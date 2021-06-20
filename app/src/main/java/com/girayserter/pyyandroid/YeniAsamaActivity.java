package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.girayserter.pyyandroid.adapters.AsamaAdapter;
import com.girayserter.pyyandroid.databinding.ActivityProjeAsamalariBinding;
import com.girayserter.pyyandroid.databinding.ActivityYeniAsamaBinding;

import java.util.Calendar;

public class YeniAsamaActivity extends AppCompatActivity {

    ActivityYeniAsamaBinding binding;
    Database database;
    Bundle bundle;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_yeni_asama);

        database=new Database(this);
        bundle=getIntent().getExtras();

        binding.txtBaslangictarihi.setOnClickListener(v -> {
            btnDatePicker(binding.txtBaslangictarihi);
        });

        binding.txtBitistarihi.setOnClickListener(v -> {
            btnDatePicker(binding.txtBitistarihi);
        });

        binding.btnOlustur.setOnClickListener(v -> {
            database.yeniAsama(
                    bundle.getInt("projeid"),
                    binding.txtAsamadi.getText().toString(),
                    binding.txtBaslangictarihi.getText().toString(),
                    binding.txtBitistarihi.getText().toString());
            setResult(RESULT_OK);
            finish();
        });
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