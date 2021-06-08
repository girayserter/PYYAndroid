package com.girayserter.pyyandroid.models;

import com.girayserter.pyyandroid.Database;

public class GorevListe {
    public int id;

    Database database=new Database();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjeid() {
        return projeid;
    }

    public void setProjeid(int projeid) {
        this.projeid = projeid;
    }

    public int getPersonelid() {
        return personelid;
    }

    public void setPersonelid(int personelid) {
        this.personelid = personelid;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getListe_adi() {
        return liste_adi;
    }

    public void setListe_adi(String liste_adi) {
        this.liste_adi = liste_adi;
    }

    public int getToplam_gorev_katsayisi() {
        return toplam_gorev_katsayisi;
    }

    public void setToplam_gorev_katsayisi(int toplam_gorev_katsayisi) {
        this.toplam_gorev_katsayisi = toplam_gorev_katsayisi;
    }

    public int getTamamlanan_gorev_katsayisi() {
        return tamamlanan_gorev_katsayisi;
    }

    public void setTamamlanan_gorev_katsayisi(int tamamlanan_gorev_katsayisi) {
        this.tamamlanan_gorev_katsayisi = tamamlanan_gorev_katsayisi;
    }

    public float getTamamlanma_yuzdesi() {
        return tamamlanma_yuzdesi;
    }

    public void setTamamlanma_yuzdesi(float tamamlanma_yuzdesi) {
        this.tamamlanma_yuzdesi = tamamlanma_yuzdesi;
    }

    private String iddenAdSoyad(int id){
        return database.iddenAdSoyadAl(id);
    }

    public int projeid;
    public int personelid;
    public String deadline;
    public String liste_adi;
    public int toplam_gorev_katsayisi;
    public int tamamlanan_gorev_katsayisi;
    public float tamamlanma_yuzdesi;

    public String getAdSoyad() {
        return database.iddenAdSoyadAl(this.personelid);
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = database.iddenAdSoyadAl(this.personelid);
    }

    String adSoyad;
}
