/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daftarMenu;

/**
 *
 * @author ACER
 */

interface method{
    String getNama();
    void setNama(String a);
    int getHarga();
    void setHarga(int a);
}
public class minuman extends menu implements method{
    private String nama;
    private int harga;
    
    @Override
    public String getNama(){
        return nama;
    }
    @Override
    public void setNama(String nama){
        this.nama = nama;
    }
    @Override
    public int getHarga(){
        return harga;
    }
    @Override
    public void setHarga(int harga){
        this.harga = harga;
    }
}
