/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

/**
 * class profil_login merupakan subkelas dari class profil yang digunakan untuk representasi profil pengguna saat proses login.
 * class ini mewarisi atribut dan metode dari class profil.
 * 
 * <p>Contoh penggunaan:</p>
 * <pre>{@code
 * Profil_Login profilLogin = new Profil_Login("ID-001", "Taufiqul Maha", "king.taufiq", "password", "123456789");
 * }</pre>
 * 
 * <p>Kelas ini tidak memiliki metode tambahan yang ditambahkan kecuali konstruktor yang memanggil konstruktor superkelas.
 * 
 * @author 
 */
public class profil_login extends profil {
    
    public profil_login(String id, String nama, String username, String password, String phone_number){
        super(id, nama, username, password, phone_number);
    }
    
    //kumpulan method setter & getter yang meng override dari parent class
    @Override
    public String getId(){
        return id;
    }
    @Override
    public void setId(String id){
        this.id = id;
    }
    @Override
    public String getNama(){
        return nama;
    }
    @Override
    public void setNama(String nama){
        this.nama = nama;
    }
    @Override
    public String getUsername(){
        return username;
    }
    @Override
    public void setUsername(String username){
        this.username = username;
    }
    @Override
    public String getPassword(){
        return password;
    }
    @Override
    public void setPassword(String password){
        this.password = password;
    }
    @Override
    public String getPhone_number(){
        return phone_number;
    }
    @Override
    public void setPhone_number(String phone_number){
        this.phone_number = phone_number;
    }
}
