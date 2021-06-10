package com.girayserter.pyyandroid.models;

import com.girayserter.pyyandroid.Database;

public class Mesaj {

    public int getGonderen() {
        return gonderen;
    }

    public void setGonderen(int gonderen) {
        this.gonderen = gonderen;
    }

    public int getAlici() {
        return alici;
    }

    public void setAlici(int alici) {
        this.alici = alici;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
    }

    public int gonderen;
    public int alici;
    public String mesaj;
    public String konu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String adSoyad;
}
