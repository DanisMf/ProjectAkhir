/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daftarMenu;

/**
 *
 * @author ACER
 */
public abstract class menu {
    /** The type of the menu item. */
    String jenis;
    
    /**
     * Constructor a new menu item with the specified type.
     * 
     * @param jenis the type of the menu item
     */
    
    public menu(String jenis){
        this.jenis = jenis;
    }
     /**
     * Returns the type of the menu item.
     * 
     * @return the type of the menu item
     */
    public String getJenis(){
        return jenis;
    }
    /**
     * Sets the type of the menu item.
     * 
     * @param jenis the type of the menu item
     */
    public void setJenis(String jenis){
        this.jenis = jenis;
    }
    
}
