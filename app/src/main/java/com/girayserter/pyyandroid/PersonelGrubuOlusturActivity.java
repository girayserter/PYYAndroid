package com.girayserter.pyyandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.girayserter.pyyandroid.adapters.PersonellerAdapter;
import com.girayserter.pyyandroid.databinding.ActivityPersonelGrubuOlusturBinding;
import com.girayserter.pyyandroid.models.Personel;

import java.util.ArrayList;
import java.util.List;

public class PersonelGrubuOlusturActivity extends AppCompatActivity {

    ActivityPersonelGrubuOlusturBinding binding;
    PersonellerAdapter adapter;
    Database database;
    Button btn_olustur;
    EditText txt_grupAdi;
    EditText txt_grupTanimi;
    private List<Personel> currentSelectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personel_grubu_olustur);

        database=new Database(this);

        binding.rcvPersoneller.setHasFixedSize(true);
        binding.rcvPersoneller.setLayoutManager(new LinearLayoutManager(this));
        adapter=new PersonellerAdapter(this);
        binding.rcvPersoneller.setAdapter(adapter);

        personellerYenile();

        btn_olustur=findViewById(R.id.btn_olustur);
        txt_grupAdi =findViewById(R.id.txt_grup_adi);
        txt_grupTanimi =findViewById(R.id.txt_grup_tanimi);

        btn_olustur.setOnClickListener(v -> {
            try{
                if(txt_grupAdi.getText().equals("")||txt_grupTanimi.getText().equals("")){
                    Toast.makeText(this,"Grup adı ve tanımı boş olamaz",Toast.LENGTH_LONG).show();
                }else {
                    ArrayList<Integer> secilenler=adapter.getSeciliPersoneller();
                    int grupid = database.calisanGrubuOlustur(txt_grupAdi.getText().toString(), txt_grupTanimi.getText().toString());
                    database.grubaPersonelEkle(secilenler, grupid);
                    finish();

                }
            }
            catch (Exception ex){
                Toast.makeText(this,"hata",Toast.LENGTH_LONG).show();
            }
        });


        currentSelectedItems = new ArrayList<>();



    }

    private void personellerYenile(){
        adapter.addPersonelList(database.tabloyuAlPersoneller());
        adapter.notifyDataSetChanged();
    }
}