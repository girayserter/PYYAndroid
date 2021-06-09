package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.girayserter.pyyandroid.databinding.ActivityGorevEkleBinding;

public class GorevEkleActivity extends AppCompatActivity {

    ActivityGorevEkleBinding binding;
    Database database;
    Bundle bundle;

    String[] intervals={"1",
            "2",
            "3",
            "4",
            "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gorev_ekle);

        database=new Database(this);
        bundle=getIntent().getExtras();

        binding.btnKaydet.setOnClickListener(v -> {
            database.yeniGorev(bundle.getInt("listeid"),binding.editTextTextPersonName2.getText().toString(),binding.spZorlukderecesi.getSelectedItem().toString());
            setResult(RESULT_OK);
            finish();
        });

        ArrayAdapter adapterInterval=new ArrayAdapter(this, R.layout.spinner_item,intervals);
        adapterInterval.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spZorlukderecesi.setAdapter(adapterInterval);
    }
}