package com.girayserter.pyyandroid.models;

public class Asama {
    public int id;
    public String asamaAdi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsamaAdi() {
        return asamaAdi;
    }

    public void setAsamaAdi(String asamaAdi) {
        this.asamaAdi = asamaAdi;
    }

    public int getProjeId() {
        return projeId;
    }

    public void setProjeId(int projeId) {
        this.projeId = projeId;
    }

    public String getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(String baslangicTarihi) {
        this.baslangicTarihi = baslangicTarihi;
    }

    public String getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(String bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public int projeId;
    public String baslangicTarihi;
    public String bitisTarihi;
}
