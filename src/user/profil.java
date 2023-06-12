/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

/**
 * class profil merepresentasikan profil pengguna dengan atribut seperti id, nama, username, password, dan nomor telepon.
 * class ini menyediakan metode untuk mengambil dan mengatur nilai-nilai atribut profil pengguna.
 * 
 * <p>Contoh penggunaan:</p>
 * <pre>{@code
 * Profil profil = new Profil("ID-001", "Taufiqul Maha", "king.taufiq", "password", "123456789");
 * String nama = profil.getNama();
 * profil.setPhone_number("987654321");
 * }</pre>
 * 
 * @author 
 */
public class profil {
    
    String id;
    String nama;
    String username;
    String password;
    String phone_number;
    
    /**
     * Konstruktor untuk membuat objek profil dengan id, nama, username, password, dan nomor telepon yang ditentukan.
     * 
     * @param id           id profil
     * @param nama         nama profil
     * @param username     username profil
     * @param password     password profil
     * @param phone_number nomor telepon profil
     */
    
    public profil(String id, String nama, String username, String password, String phone_number){
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
    }
    
    /**
     * method setter & getter dari class profil
     * @return 
     */
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getNama(){
        return nama;
    }
    public void setNama(String nama){
        this.nama = nama;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPhone_number(){
        return phone_number;
    }
    public void setPhone_number(String phone_number){
        this.phone_number = phone_number;
    }
}
