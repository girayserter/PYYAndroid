package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.girayserter.pyyandroid.adapters.ProjeMesajAdapter;
import com.girayserter.pyyandroid.databinding.ActivityProjeMesajlarBinding;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.ProjeMesaj;

import java.text.SimpleDateFormat;
import java.util.List;

public class ProjeMesajlarActivity extends AppCompatActivity {
    ActivityProjeMesajlarBinding binding;
    ProjeMesajAdapter adapter;
    Database database;
    SessionManagement session;
    Kullanici kullanici;
    Bundle bundle;
    Handler refreshHandler = new Handler();//Timer for screenshot timed to 10 sec

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_proje_mesajlar);

        database=new Database(this);
        bundle=getIntent().getExtras();
        session=new SessionManagement(getApplicationContext());
        kullanici=session.getUser();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setStackFromEnd(true);
        binding.rcvMesajlar.setLayoutManager(layoutManager);
        adapter=new ProjeMesajAdapter(this,kullanici);
        binding.rcvMesajlar.setAdapter(adapter);

        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                refreshHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mesajlarYenile();
                        refreshHandler.postDelayed(this,1000);
                    }
                }, 0);
                Looper.loop();
            }
        }).start();


        binding.btnGonder.setOnClickListener(v -> {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            database.yeniProjeMesaj(bundle.getInt("projeid"),binding.txtMesaj.getText().toString(),kullanici.id,timeStamp,kullanici.ad+" "+kullanici.soyad);
            binding.txtMesaj.setText("");
        });


    }

    private void mesajlarYenile(){
        List <ProjeMesaj> mesajList=database.projeMesajlarAl(bundle.getInt("projeid"));
        if(binding.rcvMesajlar.getAdapter().getItemCount()!=mesajList.size()) {
            adapter.addMesajList(mesajList);
            adapter.notifyDataSetChanged();
            binding.rcvMesajlar.getLayoutManager().scrollToPosition(mesajList.size()-1);
        }

    }
}