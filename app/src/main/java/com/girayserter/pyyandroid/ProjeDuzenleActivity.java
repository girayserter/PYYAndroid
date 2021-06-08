package com.girayserter.pyyandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.girayserter.pyyandroid.adapters.PersonellerAdapter;
import com.girayserter.pyyandroid.adapters.ProjeCalisanAdapter;
import com.girayserter.pyyandroid.adapters.ProjelerAdapter;
import com.girayserter.pyyandroid.databinding.ActivityProjeDuzenleBinding;
import com.girayserter.pyyandroid.models.Personel;
import com.girayserter.pyyandroid.models.ProjePersonel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ProjeDuzenleActivity extends AppCompatActivity implements ProjeCalisanAdapter.RcvOnClickInterface{

    ActivityProjeDuzenleBinding binding;
    Database database;
    List<Personel> personelList;
    ProjeCalisanAdapter adapter;
    private int mYear, mMonth, mDay;
    Bundle bundle;
    HashMap<String,Integer> hashMap=new HashMap<>();
    String[] intervals={"Çağlayan (Şelale) Modeli",
            "V Modeli",
            "Evrimsel Model",
            "Artırımsal Model",
            "Gelişigüzel Model",
            "Barok Modeli",
            "Helezonik-Spiral Model",
            "Araştırma Tabanlı Model"};



    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        projeCalisanlarYenile();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_proje_duzenle);

        database=new Database(this);
        bundle=getIntent().getExtras();

        hashMap.put("Çaglayan (Selale) Modeli",0);
        hashMap.put("V Modeli",1);
        hashMap.put("Evrimsel Model",2);
        hashMap.put("Artirimsal Model",3);
        hashMap.put("Gelisiguzel Model",4);
        hashMap.put("Barok Modeli",5);
        hashMap.put("Helezonik-Spiral Model",6);
        hashMap.put("Arastirma Tabanli Model",7);

        binding.rcvCalisanlar.setHasFixedSize(true);
        binding.rcvCalisanlar.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ProjeCalisanAdapter(this,this);
        binding.rcvCalisanlar.setAdapter(adapter);

        projeCalisanlarYenile();
        binding.txtProjeadi.setText(bundle.getString("projeadi"));
        binding.txtBitistarihi.setText(bundle.getString("bitistarihi"));
        binding.txtBaslangictarihi.setText(bundle.getString("baslangictarihi"));
        binding.txtProjetanimi.setText(bundle.getString("projetanimi"));
        binding.spGelistirmemodeli.setSelection(hashMap.get(bundle.getString("gelistirmemodeli")));
        binding.spProjeyoneticisi.setSelection(projeYoneticiIndexBul(bundle.getString("yoneticiisim")));


        ArrayAdapter adapterInterval=new ArrayAdapter(this, R.layout.spinner_item,intervals);
        adapterInterval.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spGelistirmemodeli.setAdapter(adapterInterval);

        ArrayAdapter adapterInterval2=new ArrayAdapter(this, R.layout.spinner_item,personellerAl());
        adapterInterval2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spProjeyoneticisi.setAdapter(adapterInterval2);

        binding.btnCalisanekle.setOnClickListener(v -> {
            Intent intent=new Intent(this,PersonelSecActivity.class);
            intent.putExtra("projeid",bundle.getInt("projeid"));
            intent.putExtra("mod","calisan");
            mStartForResult.launch(intent);
        });

        binding.btnCalisangrubuekle.setOnClickListener(v -> {
            Intent intent=new Intent(this,PersonelSecActivity.class);
            intent.putExtra("projeid",bundle.getInt("projeid"));
            intent.putExtra("mod","calisangrubu");
            mStartForResult.launch(intent);
        });

        binding.btnOlustur.setOnClickListener(v -> {
            String projeadi=binding.txtProjeadi.getText().toString();
            String projetanimi=binding.txtProjetanimi.getText().toString();
            String gelistirmeModeli=binding.spGelistirmemodeli.getSelectedItem().toString();
            int projeyoneticisiid=personelList.get(binding.spProjeyoneticisi.getSelectedItemPosition()).id;
            String projebaslangic=binding.txtBaslangictarihi.getText().toString();
            String projebitis=binding.txtBitistarihi.getText().toString();

            if(projeadi.equals("")){
                Toast.makeText(this,"Proje adı boş bırakılamaz",Toast.LENGTH_LONG).show();
            }else if(projebaslangic.equals(null)){
                Toast.makeText(this,"Lütfen bir başlangıç tarihi belirleyin",Toast.LENGTH_LONG).show();
            }else if(projebitis.equals(null)){
                Toast.makeText(this,"Lütfen bir bitiş tarihi belirleyin",Toast.LENGTH_LONG).show();
            }
            else {
                database.projeDuzenle(projeadi,projetanimi,projeyoneticisiid,bundle.getInt("projeid"),gelistirmeModeli,projebaslangic,projebitis);
                setResult(RESULT_OK);
                finish();
            }
        });
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

    private int projeYoneticiIndexBul(String isim){
        for(int i=0;i<binding.spProjeyoneticisi.getCount();i++){
            if(isim.equals(binding.spProjeyoneticisi.getItemAtPosition(i).toString())){
                return i;
            }
        }
        return 0;
    }

    private void projeCalisanlarYenile(){
        adapter.addPersonelList(database.projeCalisanListesi(bundle.getInt("projeid")));
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(ProjePersonel projePersonel) {

    }
}