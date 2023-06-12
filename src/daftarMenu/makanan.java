/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daftarMenu;

/**
 * class makanan merupakan subkelas dari class menu yang merepresentasikan sebuah makanan.
 * makanan memiliki atribut nama, jumlah, dan harga.
 * 
 * @author 
 */

public class makanan extends menu{
    
    private String nama;
    private int jumlah;
    private int harga;
    
    //Constructor adalah method khusus yang akan dieksekusi/dipanggil pada saat instansiasi objek menggunakan operator new.
    //Pada umumnya, constructor berisi inisialisasi instance variable dari objek pada suatu class.
    
    /**
     * Konstruktor untuk membuat objek makanan dengan jenis, nama, jumlah, dan harga yang ditentukan.
     * 
     * @param jenis  jenis makanan
     * @param nama   nama makanan
     * @param jumlah jumlah makanan
     * @param harga  harga makanan
     */
    
    public makanan(String jenis, String nama, int jumlah, int harga){
        super(jenis);
        this.nama = nama;
        this.jumlah = jumlah;
        this.harga = harga;
    }
    
    /**
     * method setter & getter dari class makanan
     * @return 
     */
    
    //mengoverride dari parent class
    @Override
    public String getJenis(){
        return jenis;
    }
    @Override
    public void setJenis(String jenis){
        this.jenis = jenis;
    }
    public String getNama(){
        return nama;
    }
    public void setNama(String nama){
        this.nama = nama;
    }
    public int getJumlah(){
        return jumlah;
    }
    public void setJumlah(int jumlah){
        this.jumlah = jumlah;
    }
    public int getHarga(){
        return harga;
    }
    public void setHarga(int harga){
        this.harga = harga;
    }
}
