/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daftarMenu;

/**
 *
 * @author ACER
 */

public class makanan extends menu{
    
    private String nama;
    private int jumlah;
    private int harga;
    
    //Constructor adalah method khusus yang akan dieksekusi/dipanggil pada saat instansiasi objek menggunakan operator new.
    //Pada umumnya, constructor berisi inisialisasi instance variable dari objek pada suatu class.
    
    public makanan(String jenis, String nama, int jumlah, int harga){
        super(jenis);
        this.nama = nama;
        this.jumlah = jumlah;
        this.harga = harga;
    }
    
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
