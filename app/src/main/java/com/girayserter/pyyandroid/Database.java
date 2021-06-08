package com.girayserter.pyyandroid;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import com.girayserter.pyyandroid.databinding.ListItemGorevListesiBinding;
import com.girayserter.pyyandroid.models.CalisanGrubu;
import com.girayserter.pyyandroid.models.GorevListe;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Personel;
import com.girayserter.pyyandroid.models.Proje;
import com.girayserter.pyyandroid.models.ProjePersonel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    Context context;

    public Database(Context context){
        this.context=context;
    }
    public Database(){

    }

    public Connection connectionclass() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection=null;
        String ConnectionURL=null;
        try{
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL="jdbc:jtds:sqlserver://greyfox.database.windows.net:1433;DatabaseName=ProjeYonetimi;user=greyfox@greyfox;password=Projeyonetim.;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection= DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se){
        }
        catch (ClassNotFoundException e){}
        catch (Exception e){
            Toast.makeText(context,"Bağlantı Hatası",Toast.LENGTH_LONG).show();
        }
        return connection;
    }

    public KullaniciBilgileri kullaniciAl(String kullaniciadi, String sifre)
    {
        KullaniciBilgileri kullanici=new KullaniciBilgileri();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query;
                if(sifre.equals("")){
                    query="select*from kullanicilar where kullanici_adi='"+kullaniciadi+"' and sifre IS NULL";
                }
                else{
                    query="select*from kullanicilar where kullanici_adi='"+kullaniciadi+"' and sifre='"+sifre+"'";}
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    kullanici.kullaniciId=rs.getInt("id");
                    kullanici.kullaniciAdi=rs.getString("kullanici_adi");
                    kullanici.yetki=rs.getString("yetki");
                    kullanici.sifre=rs.getString("sifre");
                    if (rs.wasNull()) {
                        kullanici.sifre = "";
                    }
                }
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return kullanici;
    }

    public void SifreDegistir(String kullaniciadi,String sifre)
    {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="update kullanicilar set sifre='"+sifre+"' where kullanici_adi='"+kullaniciadi+"'";
                Statement stmt=con.createStatement();
                stmt.executeQuery(query);
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
    }

    public List<Proje> tabloyuAlProjeler()
    {
        List<Proje> projeList = new ArrayList<>();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="select*from projeler";
                Statement stmt=con.createStatement();
                 ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Proje proje=new Proje();
                    proje.id=rs.getInt("id");
                    proje.proje_adi=rs.getString("proje_adi");
                    proje.proje_tanimi=rs.getString("proje_tanimi");
                    proje.progress=rs.getDouble("progress");
                    proje.proje_yoneticisi_id=rs.getInt("proje_yoneticisi_id");
                    proje.gelistirme_modeli=rs.getString("gelistirme_modeli");
                    proje.proje_baslama_tarihi=rs.getString("proje_baslama_tarihi");
                    proje.proje_bitis_tarihi=rs.getString("proje_bitis_tarihi");
                    projeList.add(proje);
                }

                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return projeList;
    }

    public List<Personel> tabloyuAlPersoneller()
    {
        List<Personel> personelList = new ArrayList<>();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="select*from personel";
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Personel personel=new Personel();
                    personel.id=rs.getInt("id");
                    personel.ad=rs.getString("ad");
                    personel.soyad=rs.getString("soyad");
                    personel.pozisyon=rs.getString("pozisyon");
                    personelList.add(personel);
                }
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return personelList;
    }

    public boolean yeniProje(String projeadi, String projetanimi, int yoneticiId, String gelistirmeModeli, String baslangicTarihi, String bitisTarihi)
    {
        int projeid=0;
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="insert into projeler (proje_adi,proje_tanimi,proje_yoneticisi_id,gelistirme_modeli,proje_baslama_tarihi,proje_bitis_tarihi) values ('"+projeadi+"','"+projetanimi+"',"+yoneticiId+",'"+gelistirmeModeli+"','"+baslangicTarihi+"','"+bitisTarihi+"')";
                PreparedStatement stmt=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs=stmt.getGeneratedKeys();
                if(rs.next()){
                    projeid=(int)rs.getLong(1);
                }
                con.close();
                if(projeid!=0){
                    if(projeyePersonelAta(projeid,yoneticiId,"Yönetici"))
                        return true;
                }
                return true;
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        catch(Exception ex){
            Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean projeyePersonelAta(int projeid,int personelid, String rol)
    {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="insert into projeAtamalari(proje_id,personel_id,rol) values ("+projeid+","+personelid+",'"+rol+"')";
                Statement stmt=con.createStatement();
                stmt.executeQuery(query);
                Toast.makeText(context,"Ekleme Başarılı",Toast.LENGTH_LONG).show();
                con.close();
                return true;
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        catch(Exception ex){
            return false;
        }
    }

    public int calisanGrubuOlustur(String grupadi, String gruptanimi) {
        int grupid = 0;
        try {
            Connection con = connectionclass();
            if (con != null) {
                String query = "insert into calisanGruplari (grup_adi,grup_aciklamasi) values ('" + grupadi + "','" + gruptanimi + "')";
                PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    grupid = (int) rs.getLong(1);
                }
                con.close();

                return grupid;

            } else {
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
                return grupid;
            }
        } catch (Exception ex) {
            Toast.makeText(context,"hata",Toast.LENGTH_LONG).show();
            return grupid;
        }
    }

    public void grubaPersonelEkle(ArrayList<Integer> secilenler, int grupid) {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                for(int i=0;i<secilenler.size();i++){
                    String query="insert into grupAtamalari (grup_id,personel_id) values ("+grupid+","+secilenler.get(i)+")";
                    Statement stmt=con.createStatement();
                    stmt.execute(query);
                }
                con.close();
                Toast.makeText(context,"Kaydedildi",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            Toast.makeText(context,"hata",Toast.LENGTH_LONG).show();
        }
    }

    public List<Kullanici> kullaniciTabloAl() {
        List<Kullanici> kullaniciList = new ArrayList<>();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query = "select kullanicilar.id,kullanicilar.kullanici_adi,personel.ad,personel.soyad,personel.pozisyon,kullanicilar.yetki from kullanicilar right join personel on kullanicilar.id=personel.id order by id" ;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Kullanici kullanici=new Kullanici();
                    kullanici.id=rs.getInt("id");
                    kullanici.ad=rs.getString("ad");
                    kullanici.soyad=rs.getString("soyad");
                    kullanici.pozisyon=rs.getString("pozisyon");
                    kullanici.yetki=rs.getString("yetki");;
                    kullanici.kullanici_adi=rs.getString("kullanici_adi");;
                    kullaniciList.add(kullanici);
                }
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return kullaniciList;
    }

    public Kullanici kullaniciBilgiAl(String kullanici_adi) {
        Kullanici kullanici = new Kullanici();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query = "select kullanicilar.id,kullanicilar.kullanici_adi,personel.ad,personel.soyad,personel.pozisyon,kullanicilar.yetki from kullanicilar right join personel on kullanicilar.id=personel.id where kullanicilar.kullanici_adi='"+kullanici_adi+"' order by id" ;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    kullanici.id=rs.getInt("id");
                    kullanici.ad=rs.getString("ad");
                    kullanici.soyad=rs.getString("soyad");
                    kullanici.pozisyon=rs.getString("pozisyon");
                    kullanici.yetki=rs.getString("yetki");;
                    kullanici.kullanici_adi=rs.getString("kullanici_adi");;
                }
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return kullanici;
    }
    public void kullaniciKaydet(String kullaniciadi, String yetki)
    {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="insert into kullanicilar (kullanici_adi,yetki) values ('"+kullaniciadi+"','"+yetki+"')";
                Statement stmt=con.createStatement();
                stmt.executeQuery(query);
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception ex){

        }
    }
    public void personelKaydet(String kullaniciadi,String ad, String soyad,String pozisyon)
    {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="insert into personel (id,ad,soyad,pozisyon) values ('"+kullaniciAdindanKullaniciIdAl(kullaniciadi)+"','"+ad+"','"+soyad+"','"+pozisyon+"')";
                Statement stmt=con.createStatement();
                stmt.executeQuery(query);
                con.close();
                Toast.makeText(context,"Kaydedildi",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
    }
    private int kullaniciAdindanKullaniciIdAl(String kullaniciAdi){
        int id=0;
        try{
            Connection con=connectionclass();

            if(con!=null) {
                //String query="select * from ihbar";
                String query="select id from kullanicilar where kullanici_adi='"+kullaniciAdi+"' ";
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    id=rs.getInt("id");

                }
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return id;
    }

    public String iddenAdSoyadAl(int personelid){
        String adsoyad="";
        try{
            Connection con=connectionclass();
            if(con!=null) {
                //String query="select * from ihbar";
                String query="select ad,soyad from personel where id="+personelid+" ";
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    adsoyad=rs.getString("ad");
                    adsoyad=adsoyad+" "+rs.getString("soyad");

                }
                con.close();
            }
            else{

            }
        }
        catch(Exception ex){

        }
        return adsoyad;
    }

    public void kullaniciGuncelle(int id,String kullaniciadi, String yetki) {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="update kullanicilar set kullanici_adi='"+kullaniciadi+"',yetki='"+yetki+"' where id="+id+"";
                Statement stmt=con.createStatement();
                stmt.execute(query);
                con.close();

            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            Toast.makeText(context,"hata",Toast.LENGTH_LONG).show();

        }
    }

    public void personelGuncelle(int id, String ad, String soyad, String pozisyon) {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="update personel set ad='"+ad+"',soyad='"+soyad+"',pozisyon='"+pozisyon+"' where id="+id+"";
                Statement stmt=con.createStatement();
                stmt.execute(query);
                con.close();
                Toast.makeText(context,"Kaydedildi",Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            Toast.makeText(context,"hata",Toast.LENGTH_LONG).show();
        }
    }

    public void kullaniciSil(int personelid) {
        try{
            Connection con=connectionclass();
            if(con!=null) {

                String query="delete from kullanicilar where id="+personelid+"";
                Statement stmt=con.createStatement();
                stmt.execute(query);
                con.close();
                Toast.makeText(context,"Personel Silindi",Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            Toast.makeText(context,"hata",Toast.LENGTH_LONG).show();
        }
    }

    public List<GorevListe> yoneticiGorevListesi(int projeid)
    {
        List<GorevListe> gorevListeList = new ArrayList<>();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="select*from gorev_listesi where projeid="+projeid;
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    GorevListe gorevListe=new GorevListe();
                    gorevListe.id=rs.getInt("id");
                    gorevListe.projeid=rs.getInt("projeid");
                    gorevListe.personelid=rs.getInt("personelid");
                    gorevListe.deadline=rs.getString("deadline");
                    gorevListe.liste_adi=rs.getString("liste_adi");;
                    gorevListe.toplam_gorev_katsayisi=rs.getInt("toplam_gorev_katsayisi");;
                    gorevListe.tamamlanan_gorev_katsayisi=rs.getInt("tamamlanan_gorev_katsayisi");;
                    gorevListe.tamamlanma_yuzdesi=rs.getFloat("tamamlanma_yuzdesi");;
                    gorevListeList.add(gorevListe);
                }

                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return gorevListeList;
    }
    public List<GorevListe> personelGorevListesi(int projeid, int personelid)
    {
        List<GorevListe> gorevListeList = new ArrayList<>();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query = "select*from gorev_listesi where projeid=" + projeid + "and personelid=" + personelid;
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    GorevListe gorevListe=new GorevListe();
                    gorevListe.id=rs.getInt("id");
                    gorevListe.projeid=rs.getInt("ad");
                    gorevListe.personelid=rs.getInt("soyad");
                    gorevListe.deadline=rs.getString("pozisyon");
                    gorevListe.liste_adi=rs.getString("yetki");;
                    gorevListe.toplam_gorev_katsayisi=rs.getInt("kullanici_adi");;
                    gorevListe.tamamlanan_gorev_katsayisi=rs.getInt("kullanici_adi");;
                    gorevListe.tamamlanma_yuzdesi=rs.getFloat("kullanici_adi");;
                    gorevListeList.add(gorevListe);
                }

                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return gorevListeList;
    }

    public Proje projeBilgisiAl(int id)
    {
        Proje projeBilgisi=new Proje();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query;
                query="select*from projeler where id="+id+"";
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    projeBilgisi.id=rs.getInt("id");
                    projeBilgisi.proje_adi=rs.getString("proje_adi");
                    projeBilgisi.proje_tanimi=rs.getString("proje_tanimi");
                    if (rs.wasNull()) {
                        projeBilgisi.proje_tanimi = "";
                    }
                    projeBilgisi.progress=rs.getDouble("progress");
                    if (rs.wasNull()) {
                        projeBilgisi.progress = 0.0;
                    }
                    projeBilgisi.proje_yoneticisi_id=rs.getInt("proje_yoneticisi_id");
                    projeBilgisi.gelistirme_modeli=rs.getString("gelistirme_modeli");
                    projeBilgisi.proje_baslama_tarihi=rs.getString("proje_baslama_tarihi");
                    projeBilgisi.proje_bitis_tarihi=rs.getString("proje_bitis_tarihi");

                }
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return projeBilgisi;
    }

    public List<ProjePersonel> projeCalisanListesi(int projeid) {


        List<ProjePersonel> projePersonelList = new ArrayList<>();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query = "select projeAtamalari.personel_id,personel.ad,personel.soyad,personel.pozisyon,projeAtamalari.rol from projeAtamalari right join personel on projeAtamalari.personel_id=personel.id where proje_id=" + projeid ;
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    ProjePersonel projePersonel=new ProjePersonel();
                    projePersonel.personel_id=rs.getInt("personel_id");
                    projePersonel.ad=rs.getString("ad");
                    projePersonel.soyad=rs.getString("soyad");
                    projePersonel.rol=rs.getString("rol");
                    projePersonel.pozisyon=rs.getString("pozisyon");;
                    projePersonelList.add(projePersonel);
                }
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return projePersonelList;
    }

    public List<CalisanGrubu> calisanGruplari() {


        List<CalisanGrubu> projePersonelList = new ArrayList<>();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="select*from calisanGruplari";
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    CalisanGrubu projePersonel=new CalisanGrubu();
                    projePersonel.id=rs.getInt("id");
                    projePersonel.grup_adi=rs.getString("grup_adi");
                    projePersonel.grup_aciklamasi=rs.getString("grup_aciklamasi");
                    projePersonelList.add(projePersonel);
                }
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return projePersonelList;
    }

    public void projeyeGrupEkle(int grupid, int projeid) {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="INSERT INTO projeAtamalari (proje_id, personel_id) SELECT "+projeid+",personel_id FROM grupAtamalari WHERE grup_id="+grupid+"";
                Statement stmt=con.createStatement();
                stmt.execute(query);
                con.close();
                Toast.makeText(context,"Kaydedildi",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            Toast.makeText(context,"hata",Toast.LENGTH_LONG).show();

        }

    }

    public void projeDuzenle(String projeAdi, String projeTanimi, int projeYoneticiId,int projeid,String gelistimeModeli,String baslangicTarihi,String bitisTarihi) {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="update projeler set proje_adi='"+projeAdi+"', proje_tanimi='"+projeTanimi+"', gelistirme_modeli='"+gelistimeModeli+"' , proje_baslama_tarihi='"+baslangicTarihi+"' , proje_bitis_tarihi='"+bitisTarihi+"' ,proje_yoneticisi_id="+projeYoneticiId+" where id="+projeid+"";
                Statement stmt=con.createStatement();
                stmt.executeQuery(query);
                con.close();
            }
            else{
                Toast.makeText(context,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
    }
}
