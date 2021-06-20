package com.girayserter.pyyandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.girayserter.pyyandroid.adapters.AsamaAdapter;
import com.girayserter.pyyandroid.databinding.ActivityProjeAsamalariBinding;
import com.girayserter.pyyandroid.models.Asama;

public class ProjeAsamalariActivity extends AppCompatActivity implements AsamaAdapter.AsamalarOnClickInterface {

    ActivityProjeAsamalariBinding binding;
    AsamaAdapter adapter;
    Database database;
    Bundle bundle;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        asamalarYenile();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_proje_asamalari);

        database=new Database(this);
        bundle=getIntent().getExtras();

        binding.rcvAsamalar.setLayoutManager(new LinearLayoutManager(this));
        adapter=new AsamaAdapter(this,this);
        binding.rcvAsamalar.setAdapter(adapter);

        binding.btnYeniAsamaEkle.setOnClickListener(v -> {
            Intent intent=new Intent(this,YeniAsamaActivity.class);
            intent.putExtra("projeid",bundle.getInt("projeid"));
            mStartForResult.launch(intent);
        });

        asamalarYenile();

        binding.btnKaydet.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

    }

    private void asamalarYenile() {
        adapter.addAsamaList(database.asamalarAl(bundle.getInt("projeid")));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Asama asama) {
        database.asamaSil(asama.id);
        asamalarYenile();
    }
}