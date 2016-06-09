import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class Koneksidatabase
{
  public Connection koneksi;
  String host;
  String user;
  String pass;
 
  public Koneksidatabase(){
    host = "jdbc:mysql://localhost/db_mahasiswa";
    user = "root";
    pass = "";
    try{
       if (cekDriver()){
        koneksi = DriverManager.getConnection(host, user, pass);
        //System.out.println("connection to database established");
      }
    } catch (SQLException e){
      System.out.println("Connection failed " + e.getMessage());
    }
  }
 
  public final boolean cekDriver(){
    boolean ada = false;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      //System.out.println("Driver oke");
      ada = true;
    } catch (ClassNotFoundException c){
      System.out.println("Driver tidak ada");
    }
    return ada;
  }
}