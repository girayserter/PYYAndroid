package com.girayserter.pyyandroid;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import com.girayserter.pyyandroid.databinding.ListItemGorevListesiBinding;
import com.girayserter.pyyandroid.models.Asama;
import com.girayserter.pyyandroid.models.CalisanGrubu;
import com.girayserter.pyyandroid.models.Gorev;
import com.girayserter.pyyandroid.models.GorevListe;
import com.girayserter.pyyandroid.models.GorevidTamamlandi;
import com.girayserter.pyyandroid.models.Kullanici;
import com.girayserter.pyyandroid.models.Mesaj;
import com.girayserter.pyyandroid.models.Personel;
import com.girayserter.pyyandroid.models.Proje;
import com.girayserter.pyyandroid.models.ProjeMesaj;
import com.girayserter.pyyandroid.models.ProjePersonel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            Toast.makeText(context,"Ba??lant?? Hatas??",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return personelList;
    }

    public int yeniProje(String projeadi, String projetanimi, int yoneticiId, String gelistirmeModeli, String baslangicTarihi, String bitisTarihi)
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
                    if(projeyePersonelAta(projeid,yoneticiId,"Y??netici"))
                        return projeid;
                }
                return projeid;
            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
                return projeid;
            }
        }
        catch(Exception ex){
            Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            return projeid;
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
                Toast.makeText(context,"Ekleme Ba??ar??l??",Toast.LENGTH_LONG).show();
                con.close();
                return true;
            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();

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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
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
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
    }

    public void yeniGorevListesi(String listeadi,int personelid,int projeid, String tarih)
    {
        try{
            String query="";
            Connection con=connectionclass();
            if(con!=null) {
                if(tarih.equals("")){
                    query="insert into gorev_listesi (projeid,personelid,liste_adi) values ("+projeid+","+personelid+",'"+listeadi+"')";
                }
                else{
                    query="insert into gorev_listesi (projeid,personelid,liste_adi,deadline) values ('"+projeid+"','"+personelid+"','"+listeadi+"','"+tarih+"')";
                }
                Statement stmt=con.createStatement();
                stmt.execute(query);
                con.close();

            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception ex){
            Toast.makeText(context,"Hata",Toast.LENGTH_LONG).show();

        }
    }

    public void gorevListesiGuncelle(ArrayList<GorevidTamamlandi> al, int gorevListesiId) {
        try{
            int bit;
            int toplam=0;
            int projeid = 0;
            float projelerToplamTamamlanma=0;
            float projelerToplamKatsayi=0;
            String query;
            Connection con=connectionclass();
            if(con!=null) {
                for(int i=0;i<al.size();i++){
                    if(al.get(i).tamamlandi==true){
                        bit=1;
                    }
                    else{
                        bit=0;
                    }
                    query="update gorevler set tamamlandi="+bit+" where id="+al.get(i).gorevid+"";
                    Statement stmt=con.createStatement();
                    stmt.execute(query);
                }

                query="select zorluk_derecesi from gorevler where gorevlist_id="+gorevListesiId+" and tamamlandi =1 ";
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    toplam += rs.getInt("zorluk_derecesi");
                }

                query="select projeid from gorev_listesi where id="+gorevListesiId+"";
                stmt=con.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()){
                    projeid += rs.getInt("projeid");
                }

                query="UPDATE gorev_listesi SET tamamlanan_gorev_katsayisi = "+toplam+" ,tamamlanma_yuzdesi=(100* "+toplam+")/toplam_gorev_katsayisi WHERE id = "+gorevListesiId;
                stmt.execute(query);

                query="select tamamlanan_gorev_katsayisi,toplam_gorev_katsayisi from gorev_listesi where projeid="+projeid+"";
                rs = stmt.executeQuery(query);
                while (rs.next()){
                    projelerToplamTamamlanma += rs.getInt("tamamlanan_gorev_katsayisi");
                    projelerToplamKatsayi += rs.getInt("toplam_gorev_katsayisi");
                }

                query="UPDATE projeler SET progress = (100* "+projelerToplamTamamlanma+")/"+projelerToplamKatsayi+" WHERE id = "+projeid;
                stmt.execute(query);

                con.close();
                Toast.makeText(context,"Kaydedildi",Toast.LENGTH_LONG).show();
            }
            else{

                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            Toast.makeText(context,"Hata",Toast.LENGTH_LONG).show();

        }
    }

    public List<Gorev> gorevListesiDetay(int listeid)
    {
        List<Gorev> gorevList = new ArrayList<>();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query = "select*from gorevler where gorevlist_id=" +listeid;
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Gorev gorev=new Gorev();
                    gorev.id=rs.getInt("id");
                    gorev.gorevlist_id=rs.getInt("gorevlist_id");
                    gorev.gorev_tanimi=rs.getString("gorev_tanimi");
                    gorev.tamamlandi=rs.getBoolean("tamamlandi");
                    gorev.zorluk_derecesi=rs.getInt("zorluk_derecesi");
                    gorevList.add(gorev);
                }
                con.close();
            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return gorevList;
    }

    public void yeniGorev(int listeid, String gorevtanimi,String zorlukderecesi)
    {
        try{
            String query="";
            Connection con=connectionclass();
            if(con!=null) {
                query="insert into gorevler (gorevlist_id,gorev_tanimi,zorluk_derecesi) values ("+listeid+",'"+gorevtanimi+"',"+Integer.parseInt(zorlukderecesi)+")";
                Statement stmt=con.createStatement();
                stmt.execute(query);
                query="UPDATE gorev_listesi SET toplam_gorev_katsayisi = toplam_gorev_katsayisi + "+Integer.parseInt(zorlukderecesi)+" WHERE id = "+listeid;
                stmt.execute(query);
                con.close();

            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception ex){
        }
    }

    public List<Proje> kullaniciPanelProje(int personelid) {
        List<Proje> projeList = new ArrayList<>();
        try {
            Connection con = connectionclass();
            if (con != null) {
                String query = "select projeAtamalari.proje_id,projeler.proje_adi,projeler.proje_tanimi,projeler.progress,projeAtamalari.rol from projeAtamalari right join projeler on projeAtamalari.proje_id=projeler.id where personel_id="+personelid;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Proje proje=new Proje();
                    proje.id=rs.getInt("proje_id");
                    proje.proje_adi=rs.getString("proje_adi");
                    proje.proje_tanimi=rs.getString("proje_tanimi");
                    proje.progress=rs.getDouble("progress");
                    proje.rol=rs.getString("rol");
                    projeList.add(proje);
                }

                con.close();
            } else {
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return projeList;
    }

    public List<Mesaj> gelenKutusuAl(int personelid) {
        List<Mesaj> mesajList = new ArrayList<>();
        try {
            Connection con = connectionclass();
            if (con != null) {
                String query = "select mesajlar.id as mesajid,personel.id as personelid,mesajlar.mesaj_konusu from mesajlar left join personel on mesajlar.gonderen=personel.id where alici=" + personelid ;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Mesaj mesaj=new Mesaj();
                    mesaj.id=rs.getInt("mesajid");
                    mesaj.gonderen=rs.getInt("personelid");
                    mesaj.konu=rs.getString("mesaj_konusu");
                    mesaj.adSoyad=iddenAdSoyadAl(mesaj.gonderen);
                    mesajList.add(mesaj);
                }

                con.close();
            } else {
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return mesajList;
    }

    public List<Mesaj> gidenKutusuAl(int personelid) {

        List<Mesaj> mesajList = new ArrayList<>();
        try {
            Connection con = connectionclass();
            if (con != null) {
                String query = "select mesajlar.id as mesajid,personel.id as personelid,mesajlar.mesaj_konusu from mesajlar left join personel on mesajlar.alici=personel.id where gonderen=" + personelid ;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Mesaj mesaj=new Mesaj();
                    mesaj.id=rs.getInt("mesajid");
                    mesaj.gonderen=rs.getInt("personelid");
                    mesaj.konu=rs.getString("mesaj_konusu");
                    mesaj.adSoyad=iddenAdSoyadAl(mesaj.gonderen);
                    mesajList.add(mesaj);
                }

                con.close();
            } else {
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return mesajList;
    }

    public void mesajGonder(int personelid, int aliciid, String mesaj,String konu) {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="INSERT INTO mesajlar (gonderen,alici,mesaj,mesaj_konusu) values ("+personelid+","+aliciid+",'"+mesaj+"','"+konu+"')";
                Statement stmt=con.createStatement();
                stmt.execute(query);
                con.close();
                Toast.makeText(context,"G??nderildi",Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception ex){
            Toast.makeText(context,"Hata",Toast.LENGTH_LONG).show();

        }
    }

    public Mesaj mesajAl(int mesajid){
        Mesaj mesaj=new Mesaj();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="select * from mesajlar where id="+mesajid+"";
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    mesaj.gonderen=rs.getInt("gonderen");
                    mesaj.alici=rs.getInt("alici");
                    mesaj.mesaj=rs.getString("mesaj");
                    mesaj.konu=rs.getString("mesaj_konusu");

                }
                con.close();
            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception ex){

        }
        return mesaj;
    }

    public void mesajSil(int mesajid){
        Boolean bool;
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="delete from mesajlar where id="+mesajid+"";
                Statement stmt=con.createStatement();
                bool = stmt.execute(query);

                con.close();
            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception ex){

        }

    }


    public List<Asama> asamalarAl(int projeid) {
        List<Asama> asamaList = new ArrayList<>();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="select*from projeAsamalari where proje_id="+projeid+" ORDER BY baslangic_tarihi";
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Asama asama=new Asama();
                    asama.id=rs.getInt("id");
                    asama.asamaAdi=rs.getString("asama_adi");
                    asama.projeId=rs.getInt("proje_id");
                    asama.baslangicTarihi=rs.getString("baslangic_tarihi");
                    asama.bitisTarihi=rs.getString("bitis_tarihi");;
                    asamaList.add(asama);
                }

                con.close();
            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return asamaList;
    }

    public void yeniAsama(int projeid, String asamaAdi,String baslangicTarihi, String bitisTarihi)
    {
        try{
            String query="";
            Connection con=connectionclass();
            if(con!=null) {
                query="insert into projeAsamalari (asama_adi,proje_id,baslangic_tarihi,bitis_tarihi) values ('"+asamaAdi+"',"+projeid+",'"+baslangicTarihi+"','"+bitisTarihi+"')";
                Statement stmt=con.createStatement();
                stmt.execute(query);
                con.close();

            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception ex){
        }
    }

    public void asamaSil(int asamaid) {
        try{
            Connection con=connectionclass();
            if(con!=null) {

                String query="delete from projeAsamalari where id="+asamaid+"";
                Statement stmt=con.createStatement();
                stmt.execute(query);
                con.close();
                Toast.makeText(context,"A??ama Silindi",Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            Toast.makeText(context,"hata",Toast.LENGTH_LONG).show();
        }
    }

    public Asama asamaAl(int projeid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String tarih = sdf.format(new Date());
        Asama asama=new Asama();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="select*from projeAsamalari where proje_id="+projeid+" and baslangic_tarihi<='"+tarih+"' and bitis_tarihi>='"+tarih+"' ORDER BY baslangic_tarihi";
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    asama.id=rs.getInt("id");
                    asama.asamaAdi=rs.getString("asama_adi");
                    asama.projeId=rs.getInt("proje_id");
                    asama.baslangicTarihi=rs.getString("baslangic_tarihi");
                    asama.bitisTarihi=rs.getString("bitis_tarihi");;
                }
                con.close();
            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return asama;
    }

    public List<ProjeMesaj> projeMesajlarAl(int projeid) {
        List<ProjeMesaj> projeMesajList = new ArrayList<>();
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="select*from projeMesajlar where proje_id="+projeid+" ORDER BY id";
                Statement stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    ProjeMesaj mesaj=new ProjeMesaj();
                    mesaj.id=rs.getInt("id");
                    mesaj.projeId=rs.getInt("proje_id");
                    mesaj.personelId=rs.getInt("personel_id");
                    mesaj.timestamp=rs.getString("timestamp");
                    mesaj.mesaj=rs.getString("mesaj");
                    mesaj.personelAdi=rs.getString("personel_adi");
                    projeMesajList.add(mesaj);
                }

                con.close();
            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){

        }
        return projeMesajList;
    }

    public void yeniProjeMesaj(int projeId, String mesaj, int personelId, String timestamp, String personelAdi)
    {
        try{
            Connection con=connectionclass();
            if(con!=null) {
                String query="insert into projeMesajlar (proje_id,personel_id,timestamp,mesaj,personel_adi) values ("+projeId+","+personelId+",'"+timestamp+"','"+mesaj+"','"+personelAdi+"')";
                PreparedStatement stmt=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs=stmt.getGeneratedKeys();
                con.close();
            }
            else{
                Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            Toast.makeText(context,"??nternet Ba??lant??n??z?? Kontrol Ediniz",Toast.LENGTH_LONG).show();
        }
    }
}
