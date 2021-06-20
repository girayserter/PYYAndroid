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
import android.view.View;

import com.girayserter.pyyandroid.adapters.GorevListesiAdapter;
import com.girayserter.pyyandroid.adapters.ProjelerAdapter;
import com.girayserter.pyyandroid.databinding.ActivityProjeBilgiBinding;
import com.girayserter.pyyandroid.models.Asama;
import com.girayserter.pyyandroid.models.GorevListe;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Proje;

public class ProjeBilgiActivity extends AppCompatActivity implements GorevListesiAdapter.GorevListeOnClickInterface {

    ActivityProjeBilgiBinding binding;
    Database database;
    SessionManagement session;
    Kullanici kullanici;
    GorevListesiAdapter adapter;
    Proje proje;
    int id=0;
    Bundle bundle;
    String rol="";
    Boolean duzenlemeYetki=false;
    Asama asama;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        projeYenile();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_proje_bilgi);

        database=new Database(this);
        session = new SessionManagement(getApplicationContext());
        kullanici=session.getUser();
        bundle=getIntent().getExtras();

        if(bundle.containsKey("rol")){
        if(bundle.getString("rol").equals("Yönetici")){
            rol="Yönetici";
        }}

        if(rol.equals("Yönetici")||kullanici.yetki.equals("Admin")){
            binding.btnYenigorevlistesi.setVisibility(View.VISIBLE);
            binding.btnProjeduzenle.setVisibility(View.VISIBLE);
            duzenlemeYetki=true;
        }

        adapter=new GorevListesiAdapter(this,this,duzenlemeYetki);
        binding.rcvGorevlisteleri.setHasFixedSize(true);
        binding.rcvGorevlisteleri.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvGorevlisteleri.setAdapter(adapter);

        asama=database.asamaAl(bundle.getInt("Id"));

        projeYenile();

        binding.btnProjeduzenle.setOnClickListener(v -> {
            Intent intent=new Intent(this,ProjeDuzenleActivity.class);
            intent.putExtra("projeid",proje.id);
            intent.putExtra("projeadi",proje.proje_adi);
            intent.putExtra("bitistarihi",proje.proje_bitis_tarihi);
            intent.putExtra("baslangictarihi",proje.proje_baslama_tarihi);
            intent.putExtra("projetanimi",proje.proje_tanimi);
            intent.putExtra("gelistirmemodeli",proje.gelistirme_modeli);
            intent.putExtra("yoneticiisim",database.iddenAdSoyadAl(proje.proje_yoneticisi_id));

            mStartForResult.launch(intent);
        });

        binding.btnYenigorevlistesi.setOnClickListener(v -> {
            Intent intent=new Intent(this,GorevListesiOlusturActivity.class);
            intent.putExtra("projeid",proje.id);
            mStartForResult.launch(intent);
        });

    }

    private void projeYenile() {
        proje=database.projeBilgisiAl(bundle.getInt("Id"));
        binding.txtProjeadi.setText(proje.proje_adi);
        binding.txtProjedetay.setText(proje.proje_tanimi);
        binding.txtBaslamatarihi.setText(proje.proje_baslama_tarihi);
        binding.txtBitistarihi.setText(proje.proje_bitis_tarihi);
        binding.txtProjeyonetici.setText(database.iddenAdSoyadAl(proje.proje_yoneticisi_id));
        binding.txtIlerlemedurumu.setText(proje.progress.toString());
        binding.txtAsamaadi.setText(asama.asamaAdi+" ("+asama.baslangicTarihi+" - "+asama.bitisTarihi+")");

        if(rol.equals("Yönetici")||kullanici.yetki.equals("Admin")){
            adapter.addGorevListeList(database.yoneticiGorevListesi(proje.id));
        }
        else{
            adapter.addGorevListeList(database.personelGorevListesi(proje.id,kullanici.id));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(GorevListe gorevListe) {
        Intent intent= new Intent(this,GorevlerActivity.class);
        intent.putExtra("listeid",gorevListe.id);
        intent.putExtra("listeadi",gorevListe.liste_adi);
        intent.putExtra("personelid",gorevListe.personelid);
        intent.putExtra("deadline",gorevListe.deadline);
        intent.putExtra("rol",rol);

        mStartForResult.launch(intent);
    }
}