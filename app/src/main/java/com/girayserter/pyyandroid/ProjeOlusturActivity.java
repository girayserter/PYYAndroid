package com.girayserter.pyyandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.girayserter.pyyandroid.models.Personel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    private int mYear, mMonth, mDay;
    Bundle bundle;




    String[] intervals={"Çağlayan (Şelale) Modeli",
            "V Modeli",
            "Scrum"};

    HashMap <String,Integer> hashMap=new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje_olustur);
        bundle=getIntent().getExtras();

        hashMap.put("Çaglayan (Selale) Modeli",0);
        hashMap.put("V Modeli",1);
        hashMap.put("Scrum",2);

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

        if(bundle!=null){
            txt_projeadi.setText(bundle.getString("projeadi"));
            txt_bitistarihi.setText(bundle.getString("bitistarihi"));
            txt_baslangictarihi.setText(bundle.getString("baslangictarihi"));
            txt_projetanimi.setText(bundle.getString("projetanimi"));
            sp_gelistirmemodeli.setSelection(hashMap.get(bundle.getString("gelistirmemodeli")));
            sp_projeyoneticisi.setSelection(projeYoneticiIndexBul(bundle.getString("yoneticiisim")));
        }



        btn_olustur.setOnClickListener(v -> {
            if(bundle!= null){
                
            }else{
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
                    int projeid=database.yeniProje(projeadi,projetanimi,projeyoneticisiid,gelistirmeModeli,projebaslangic,projebitis);
                    if(sp_gelistirmemodeli.getSelectedItemId()==0){
                        int gun=0;
                        try {
                            gun=gunSayisiBul(projebaslangic,projebitis);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        database.yeniAsama(projeid,"Analiz",projebaslangic,tarihEkle(projebaslangic,gun*15/100));
                        database.yeniAsama(projeid,"Tasarım",tarihEkle(projebaslangic,(gun*15/100)+1),tarihEkle(projebaslangic,gun*30/100));
                        database.yeniAsama(projeid,"Kodlama",tarihEkle(projebaslangic,(gun*30/100)+1),tarihEkle(projebaslangic,gun*65/100));
                        database.yeniAsama(projeid,"Test",tarihEkle(projebaslangic,(gun*65/100)+1),tarihEkle(projebaslangic,gun*85/100));
                        database.yeniAsama(projeid,"Entegrasyon",tarihEkle(projebaslangic,(gun*85/100)+1),projebitis);
                    }
                    else if(sp_gelistirmemodeli.getSelectedItemId()==1){
                        int gun=0;
                        try {
                            gun=gunSayisiBul(projebaslangic,projebitis);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        database.yeniAsama(projeid,"Gereksinim Analizi",projebaslangic,tarihEkle(projebaslangic,gun*10/100));
                        database.yeniAsama(projeid,"Sistem Tasarım",tarihEkle(projebaslangic,(gun*10/100)+1),tarihEkle(projebaslangic,gun*20/100));
                        database.yeniAsama(projeid,"Mimari Tasarım",tarihEkle(projebaslangic,(gun*20/100)+1),tarihEkle(projebaslangic,gun*30/100));
                        database.yeniAsama(projeid,"Modül Tasarım",tarihEkle(projebaslangic,(gun*30/100)+1),tarihEkle(projebaslangic,gun*40/100));
                        database.yeniAsama(projeid,"Kodlama",tarihEkle(projebaslangic,(gun*40/100)+1),tarihEkle(projebaslangic,gun*60/100));
                        database.yeniAsama(projeid,"Birim Testleri",tarihEkle(projebaslangic,(gun*60/100)+1),tarihEkle(projebaslangic,gun*70/100));
                        database.yeniAsama(projeid,"Bütünleme Testleri",tarihEkle(projebaslangic,(gun*70/100)+1),tarihEkle(projebaslangic,gun*80/100));
                        database.yeniAsama(projeid,"Sistem Testleri",tarihEkle(projebaslangic,(gun*80/100)+1),tarihEkle(projebaslangic,gun*90/100));
                        database.yeniAsama(projeid,"Kabul Testleri",tarihEkle(projebaslangic,(gun*90/100)+1),projebitis);
                    }
                    else if(sp_gelistirmemodeli.getSelectedItemId()==2){
                        int gun=0;
                        try {
                            gun=gunSayisiBul(projebaslangic,projebitis);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        database.yeniAsama(projeid,"Product Backlog",projebaslangic,tarihEkle(projebaslangic,7));
                        database.yeniAsama(projeid,"Sprint Backlog",tarihEkle(projebaslangic,8),tarihEkle(projebaslangic,14));
                        int k=1;
                        for(int i=15;i<gun;i+=21){
                            if(i+21>gun){
                                database.yeniAsama(projeid,"Sprint "+k,tarihEkle(projebaslangic,i),projebitis);
                            }
                            else{
                                database.yeniAsama(projeid,"Sprint "+k,tarihEkle(projebaslangic,i),tarihEkle(projebaslangic,i+20));
                            }
                            k++;
                        }
                    }
                    setResult(RESULT_OK);
                    finish();
                    Intent intent=new Intent(this,ProjeAsamalariActivity.class);
                    intent.putExtra("projeid",projeid);
                    startActivity(intent);
                }
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

    private int projeYoneticiIndexBul(String isim){
        for(int i=0;i<sp_projeyoneticisi.getCount();i++){
            if(isim.equals(sp_projeyoneticisi.getItemAtPosition(i).toString())){
                return i;
            }
        }
        return 0;
    }

    private String tarihEkle(String tarih,int miktar){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(tarih));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, miktar);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        String output = sdf1.format(c.getTime());
        return output;
    }

    private int gunSayisiBul(String baslangic,String bitis) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date basla=sdf.parse(baslangic);
        Date bit=sdf.parse(bitis);

        if(baslangic==null||bitis==null)
            return 0;

        return (int)( (bit.getTime() - basla.getTime()) / (1000 * 60 * 60 * 24));

    }
}