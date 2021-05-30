package com.girayserter.pyyandroid.models;

public class Proje {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProje_adi() {
        return proje_adi;
    }

    public void setProje_adi(String proje_adi) {
        this.proje_adi = proje_adi;
    }

    public String getProje_tanimi() {
        return proje_tanimi;
    }

    public void setProje_tanimi(String proje_tanimi) {
        this.proje_tanimi = proje_tanimi;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public int getProje_yoneticisi_id() {
        return proje_yoneticisi_id;
    }

    public void setProje_yoneticisi_id(int proje_yoneticisi_id) {
        this.proje_yoneticisi_id = proje_yoneticisi_id;
    }

    public String getGelistirme_modeli() {
        return gelistirme_modeli;
    }

    public void setGelistirme_modeli(String gelistirme_modeli) {
        this.gelistirme_modeli = gelistirme_modeli;
    }

    public String getProje_baslama_tarihi() {
        return proje_baslama_tarihi;
    }

    public void setProje_baslama_tarihi(String proje_baslama_tarihi) {
        this.proje_baslama_tarihi = proje_baslama_tarihi;
    }

    public String getProje_bitis_tarihi() {
        return proje_bitis_tarihi;
    }

    public void setProje_bitis_tarihi(String proje_bitis_tarihi) {
        this.proje_bitis_tarihi = proje_bitis_tarihi;
    }

    public int id;
    public String proje_adi;
    public String proje_tanimi;
    public Double progress;
    public int proje_yoneticisi_id;
    public String gelistirme_modeli;
    public String proje_baslama_tarihi;
    public String proje_bitis_tarihi;
}
