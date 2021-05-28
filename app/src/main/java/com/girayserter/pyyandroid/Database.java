package com.girayserter.pyyandroid;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.view.ViewParent;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
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

    public List<Proje> tabloyuAl()
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
}