package com.girayserter.pyyandroid.models;

public class Gorev {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGorevlist_id() {
        return gorevlist_id;
    }

    public void setGorevlist_id(int gorevlist_id) {
        this.gorevlist_id = gorevlist_id;
    }

    public String getGorev_tanimi() {
        return gorev_tanimi;
    }

    public void setGorev_tanimi(String gorev_tanimi) {
        this.gorev_tanimi = gorev_tanimi;
    }

    public Boolean getTamamlandi() {
        return tamamlandi;
    }

    public void setTamamlandi(Boolean tamamlandi) {
        this.tamamlandi = tamamlandi;
    }

    public int getZorluk_derecesi() {
        return zorluk_derecesi;
    }

    public void setZorluk_derecesi(int zorluk_derecesi) {
        this.zorluk_derecesi = zorluk_derecesi;
    }

    int id;
    int gorevlist_id;
    String gorev_tanimi;
    Boolean tamamlandi;
    int zorluk_derecesi;
}
