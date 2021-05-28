package com.girayserter.pyyandroid;

public class KullaniciBilgileri {
    public int kullaniciId;
    public String kullaniciAdi="";
    public String yetki="";
    public String sifre="";
    public KullaniciBilgileri(int kullaniciId,String kullaniciAdi,String yetki){
        this.kullaniciAdi=kullaniciAdi;
        this.kullaniciId=kullaniciId;
        this.yetki=yetki;
        this.sifre=sifre;
    }
    public KullaniciBilgileri(){
    }
}
