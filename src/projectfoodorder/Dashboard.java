/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectfoodorder;

import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import static projectfoodorder.UserProfile.hp_label;
import static projectfoodorder.UserProfile.nama_label;
import static projectfoodorder.UserProfile.password_label;
import static projectfoodorder.UserProfile.username_label;
import static projectfoodorder.UserProfile.id_akun_label;
import java.awt.Desktop;
import java.net.URI;
import java.net.URL;

import daftarMenu.menu;
import daftarMenu.makanan;
import daftarMenu.minuman;
import java.awt.Color;
import static projectfoodorder.StrukPage.struk;



interface campuran{
    void kembalian();
    void KosongkanForm();
    void kode_barang_otomatis();
}

/**
 *
 * @author DELL
 */
public class Dashboard extends javax.swing.JFrame implements campuran{
        
    
    public static int baris;
    makanan daftar = new makanan();
    minuman daftar2 = new minuman();
    
    private int waktumulai = 0;
    
    public Connection connect;
    public Statement stm;
    String url = "jdbc:mysql://localhost/projectfoodorder";
    String user = "root";
    String pass = "";
    
     public static String  kode_transaksi = "";
    String cek_id = "";
    
    
    public void koneksi(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user,pass);
            stm = connect.createStatement();
            JOptionPane.showMessageDialog(null, "Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, "Koneksi Gagal");
        }
    }
    
    
    @Override
    public void kode_barang_otomatis(){
        
        try{
            connect = DriverManager.getConnection(url, user,pass);
            stm = connect.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM datapembelian order by kode_transaksi desc");
            if(rs.next()){
                String kode = rs.getString("kode_transaksi").substring(3);
                String AN = "" +(Integer.parseInt(kode)+1);
                String Nol = "";
                
                if(AN.length() == 1)
                {Nol = "00";}
                else if(AN.length() == 2)
                {Nol = "0";}
                else if(AN.length() == 3)
                {Nol = "";}
                
                kode_transaksi = "KT-" + Nol + AN;
                
            }else{
                kode_transaksi = "KT-001";
            }    
        }catch(Exception ex){
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void JamRealTime(){
        new Thread(){
            @Override
            public void run(){
                while(waktumulai == 0){
                    Calendar kalender = new GregorianCalendar();
                    int jam = kalender.get(Calendar.HOUR);
                    int menit = kalender.get(Calendar.MINUTE);
                    int detik = kalender.get(Calendar.SECOND);
                    int AMPM = kalender.get(Calendar.AM_PM);
                    String SiangMalam;
                    if(AMPM == 1){
                        SiangMalam = "PM";
                    }else{
                       SiangMalam = "AM";
                    }                  
                    String JamRealTime = jam + ":" + menit + ":" + detik + " " + SiangMalam;
                    LabelJamRealTime.setText("Jam: " + JamRealTime);
                }
            }
        }.start();
    }
    
    
    
    public static void struk_print(){
        
            struk.setText("                                       The Food Order APP \n");
            struk.setText(struk.getText() + "\t          Blok 4A/ Senopati, \n");
            struk.setText(struk.getText() + "\t          DKI Jakarta, Jakarta Selatan, \n");
            struk.setText(struk.getText() + "\t          +086755492822, \n");
            struk.setText(struk.getText() + "-------------------------------------------------------------------------------\n");
            struk.setText(struk.getText() + "\tid pembeli:       "+id_akun_label.getText()+" \n");
            struk.setText(struk.getText() + "\tkode transaksi        "+kode_transaksi+" \n");
            struk.setText(struk.getText() + "-------------------------------------------------------------------------------\n");
            struk.setText(struk.getText() + " Iteam                     \tQty \tPrice \n");
            struk.setText(struk.getText() + "-------------------------------------------------------------------------------\n");
            
            
            DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                
                String name = df.getValueAt(i, 0).toString();
                String qt = df.getValueAt(i, 1).toString();
                String prc = df.getValueAt(i, 2).toString();
                
                struk.setText(struk.getText() + name+"                     \t"+qt+"\t"+prc+" \n");   
            }
            
            int total = 0;
            for (int i =0; i < jTable1.getRowCount(); i++){
                double amount = (Integer)jTable1.getValueAt(i, 2);
                total += amount;
            }
            int x = Integer.parseInt(txt_inputUang.getText());
            int balance = x - total;
            struk.setText(struk.getText() + "--------------------------------------------------------------------------------\n");
            struk.setText(struk.getText() + "SubTotal Item :                     \t"+txt_totalItem.getText()+"\n");
            struk.setText(struk.getText() + "SubTotal Harga :                     \t"+txt_totalHarga.getText()+"\n");
            struk.setText(struk.getText() + "Cash :                     \t"+txt_inputUang.getText()+"\n");
            struk.setText(struk.getText() + "Ballance :                     \t"+balance+"\n");
            struk.setText(struk.getText() + "================================================\n");
            struk.setText(struk.getText() +"                                Thanks For Your Business...!"+"\n");
            struk.setText(struk.getText() + "-------------------------------------------------------------------------------\n");
            struk.setText(struk.getText() +"                         Software by Kelompok penerbang roket"+"\n");
            struk.setText(struk.getText() +"                                                     Original"+"\n");
            
 }

    
    @Override
    public void kembalian(){
        String x = txt_totalHarga.getText();
        String y = txt_inputUang.getText();
        int a = Integer.parseInt(x);
        int b = Integer.parseInt(y);
        int total = b - a;
    }
    
   
    public Dashboard() {
        initComponents();
        JamRealTime();
        kode_barang_otomatis();
//        this.setExtendedState(MAXIMIZED_BOTH);
    }
    
    @Override
    public void KosongkanForm(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Pesanan");
        model.addColumn("QTY");
        model.addColumn("Total");
        jTable1.setModel(model);
        txt_totalItem.setText("0");
        txt_totalHarga.setText("Rp. 0,00");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LabelJamRealTime = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cb_Paincake = new javax.swing.JCheckBox();
        cb_chikenBurger = new javax.swing.JCheckBox();
        cb_cheeseFries = new javax.swing.JCheckBox();
        cb_chikenNuggets = new javax.swing.JCheckBox();
        cb_sweetPotato = new javax.swing.JCheckBox();
        cb_pepperoniPizza = new javax.swing.JCheckBox();
        cb_Hamburger = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        sp_sweetPotato = new javax.swing.JSpinner();
        sp_cheeseFries = new javax.swing.JSpinner();
        sp_chikenBurger = new javax.swing.JSpinner();
        sp_pepperoniPizza = new javax.swing.JSpinner();
        sp_Hamburger = new javax.swing.JSpinner();
        sp_chikenNuggets = new javax.swing.JSpinner();
        sp_Paincake = new javax.swing.JSpinner();
        cb_creamSoup = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        sp_creamSoup = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cb_esCampur = new javax.swing.JCheckBox();
        cb_esDoger = new javax.swing.JCheckBox();
        cb_Cendol = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        cb_airMineral = new javax.swing.JCheckBox();
        cb_tehManis = new javax.swing.JCheckBox();
        cb_podeng = new javax.swing.JCheckBox();
        cb_jeruk = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        sp_esCampur = new javax.swing.JSpinner();
        cb_kelapaMuda = new javax.swing.JCheckBox();
        sp_esDoger = new javax.swing.JSpinner();
        sp_kelapaMuda = new javax.swing.JSpinner();
        sp_airMineral = new javax.swing.JSpinner();
        sp_tehManis = new javax.swing.JSpinner();
        sp_podeng = new javax.swing.JSpinner();
        sp_jeruk = new javax.swing.JSpinner();
        sp_Cendol = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btn_delete = new javax.swing.JButton();
        btn_checkout = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        btn_status = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btn_bayar = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        btn_exit = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(2, 48, 71));
        jPanel1.setPreferredSize(new java.awt.Dimension(1045, 900));

        jPanel2.setBackground(new java.awt.Color(242, 100, 25));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 3, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FOOD ORDER APP");

        LabelJamRealTime.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LabelJamRealTime.setForeground(new java.awt.Color(255, 255, 255));
        LabelJamRealTime.setText("Jam: 0:00:00 AM");

        jButton1.setBackground(new java.awt.Color(255, 183, 3));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/user (5).png"))); // NOI18N
        jButton1.setText("PROFILE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 183, 3));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/calculator (1).png"))); // NOI18N
        jButton2.setText("CALCULATOR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelJamRealTime)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelJamRealTime)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(142, 202, 230));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cb_Paincake.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_Paincake.setText("Paincake");

        cb_chikenBurger.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_chikenBurger.setText("Chicken burger");

        cb_cheeseFries.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_cheeseFries.setText("Cheese fries");

        cb_chikenNuggets.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_chikenNuggets.setText("Chicken nuggets");

        cb_sweetPotato.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_sweetPotato.setText("Sweet potato");

        cb_pepperoniPizza.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_pepperoniPizza.setText("Pepperoni pizza");

        cb_Hamburger.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_Hamburger.setText("Hamburger");

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 23)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MEAL & VEGETARIAN");

        sp_sweetPotato.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_cheeseFries.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_chikenBurger.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_pepperoniPizza.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_Hamburger.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_chikenNuggets.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_Paincake.setMinimumSize(new java.awt.Dimension(64, 14));

        cb_creamSoup.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_creamSoup.setText("Cream Soup");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setText("QTY.");

        sp_creamSoup.setMinimumSize(new java.awt.Dimension(64, 14));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel10.setText("Price");

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel11.setText("Rp. 15.000");

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel12.setText("Rp. 15.000");

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel13.setText("Rp. 20.000");

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel14.setText("Rp. 15.000");

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel15.setText("Rp. 15.000");

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel16.setText("Rp. 30.000");

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel17.setText("Rp. 25.000");

        jLabel18.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel18.setText("Rp. 12.000");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cb_chikenNuggets)
                                            .addComponent(cb_creamSoup)
                                            .addComponent(jLabel2)
                                            .addComponent(cb_Hamburger)
                                            .addComponent(cb_pepperoniPizza)
                                            .addComponent(cb_sweetPotato))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel10)
                                        .addGap(113, 113, 113)))
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cb_cheeseFries)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sp_cheeseFries, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cb_Paincake)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sp_Paincake, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(sp_sweetPotato, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sp_chikenNuggets, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sp_pepperoniPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sp_Hamburger, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sp_creamSoup, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cb_chikenBurger)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sp_chikenBurger, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_Paincake)
                            .addComponent(sp_Paincake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_chikenNuggets)
                            .addComponent(sp_chikenNuggets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_chikenBurger)
                            .addComponent(sp_chikenBurger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_cheeseFries)
                            .addComponent(sp_cheeseFries, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_sweetPotato)
                            .addComponent(sp_sweetPotato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_pepperoniPizza)
                            .addComponent(sp_pepperoniPizza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_Hamburger)
                    .addComponent(sp_Hamburger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_creamSoup)
                    .addComponent(sp_creamSoup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(33, 158, 188));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cb_esCampur.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_esCampur.setText("Es Campur");

        cb_esDoger.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_esDoger.setText("Es Doger");

        cb_Cendol.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_Cendol.setText("Es Cendol");

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 23)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("DESSERT & DRINK");

        cb_airMineral.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_airMineral.setText("Air Mineral Botol");

        cb_tehManis.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_tehManis.setText("Es Teh Manis");

        cb_podeng.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_podeng.setText("Es Podeng");

        cb_jeruk.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_jeruk.setText("Es Jeruk");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setText("QTY.");

        sp_esCampur.setMinimumSize(new java.awt.Dimension(64, 14));

        cb_kelapaMuda.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cb_kelapaMuda.setText("Es Kelapa Muda");

        sp_esDoger.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_kelapaMuda.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_airMineral.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_tehManis.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_podeng.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_jeruk.setMinimumSize(new java.awt.Dimension(64, 14));

        sp_Cendol.setMinimumSize(new java.awt.Dimension(64, 14));

        jLabel19.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel19.setText("Price");

        jLabel20.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel20.setText("Rp. 10.000");

        jLabel21.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel21.setText("Rp. 10.000");

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel22.setText("Rp. 10.000");

        jLabel23.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel23.setText("Rp. 10.000");

        jLabel24.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel24.setText("Rp. 5.000");

        jLabel25.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel25.setText("Rp. 5.000");

        jLabel26.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel26.setText("Rp. 5.000");

        jLabel27.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel27.setText("Rp. 5.000");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cb_esCampur)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sp_esCampur, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cb_esDoger)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sp_esDoger, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cb_jeruk)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sp_jeruk, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cb_podeng)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sp_podeng, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cb_tehManis)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sp_tehManis, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cb_airMineral)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                                .addComponent(sp_airMineral, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cb_kelapaMuda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sp_kelapaMuda, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cb_Cendol)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sp_Cendol, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel19)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27))))
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel19))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_esCampur)
                    .addComponent(sp_esCampur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_esDoger)
                    .addComponent(sp_esDoger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_Cendol)
                    .addComponent(sp_Cendol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_kelapaMuda)
                    .addComponent(sp_kelapaMuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_airMineral)
                    .addComponent(sp_airMineral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_tehManis)
                    .addComponent(sp_tehManis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_podeng)
                    .addComponent(sp_podeng, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_jeruk)
                    .addComponent(sp_jeruk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 183, 3));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pesanan", "QTY", "Total"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 23)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("PESANAN ANDA ");

        btn_delete.setBackground(new java.awt.Color(188, 0, 0));
        btn_delete.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("DELETE");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_checkout.setBackground(new java.awt.Color(51, 204, 0));
        btn_checkout.setFont(new java.awt.Font("Segoe UI Black", 1, 22)); // NOI18N
        btn_checkout.setForeground(new java.awt.Color(255, 255, 255));
        btn_checkout.setText("CHECKOUT");
        btn_checkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_checkoutActionPerformed(evt);
            }
        });

        btn_reset.setBackground(new java.awt.Color(74, 86, 149));
        btn_reset.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btn_reset.setForeground(new java.awt.Color(255, 255, 255));
        btn_reset.setText("RESET");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        btn_status.setBackground(new java.awt.Color(0, 102, 255));
        btn_status.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btn_status.setForeground(new java.awt.Color(255, 255, 255));
        btn_status.setText("STATUS PEMBAYARAN");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btn_checkout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btn_reset, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_reset)
                    .addComponent(btn_delete))
                .addGap(18, 18, 18)
                .addComponent(btn_checkout, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btn_status, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(18, 103, 130));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 23)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DATA PEMBAYARAN");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TOTAL ITEM");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("TOTAL HARGA");

        btn_bayar.setBackground(new java.awt.Color(51, 255, 0));
        btn_bayar.setFont(new java.awt.Font("Segoe UI Black", 1, 20)); // NOI18N
        btn_bayar.setForeground(new java.awt.Color(255, 255, 255));
        btn_bayar.setText("BAYAR");
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("MASUKAN UANG ANDA");

        txt_totalHarga.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_totalHarga.setForeground(new java.awt.Color(255, 255, 255));
        txt_totalHarga.setText("Rp. 0,00");

        txt_totalItem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_totalItem.setForeground(new java.awt.Color(255, 255, 255));
        txt_totalItem.setText("0");

        btn_showStruk.setBackground(new java.awt.Color(242, 226, 0));
        btn_showStruk.setFont(new java.awt.Font("Segoe UI Black", 1, 20)); // NOI18N
        btn_showStruk.setForeground(new java.awt.Color(255, 255, 255));
        btn_showStruk.setText("STRUK");
        btn_showStruk.setEnabled(false);
        btn_showStruk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showStrukActionPerformed(evt);
            }
        });

        btn_exit.setBackground(new java.awt.Color(204, 0, 51));
        btn_exit.setFont(new java.awt.Font("Segoe UI Black", 1, 20)); // NOI18N
        btn_exit.setForeground(new java.awt.Color(255, 255, 255));
        btn_exit.setText("EXIT");
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel28))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(txt_totalItem, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_inputUang, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_totalHarga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btn_bayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_showStruk, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_totalItem))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_totalHarga))
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_inputUang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(27, 27, 27))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_showStruk, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jPanel8.setBackground(new java.awt.Color(142, 202, 230));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel30.setFont(new java.awt.Font("Segoe UI Black", 3, 20)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/rocket-lunch.png"))); // NOI18N
        jLabel30.setText("KELOMPOK PENERBANG ROKET");

        jLabel31.setFont(new java.awt.Font("Segoe UI Black", 3, 20)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("ABOUT US");

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/facebook.png"))); // NOI18N

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/instagram.png"))); // NOI18N
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/twitter.png"))); // NOI18N

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/whatsapp.png"))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Segoe UI Black", 3, 20)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/rocket-lunch.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel36)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel37)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel30)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addGap(16, 16, 16)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(jLabel30))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_checkoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_checkoutActionPerformed

        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if(cb_Paincake.isSelected() && (Integer)sp_Paincake.getValue()>0){
            model.addRow(new Object[]{cb_Paincake.getText(), (Integer)sp_Paincake.getValue(), (Integer)sp_Paincake.getValue()*15000});
            cb_Paincake.setSelected(false);
            sp_Paincake.setValue(0);
        }
        if(cb_chikenNuggets.isSelected() && (Integer)sp_chikenNuggets.getValue()>0){
            model.addRow(new Object[]{cb_chikenNuggets.getText(), (Integer)sp_chikenNuggets.getValue(), (Integer)sp_chikenNuggets.getValue()*15000});
            cb_chikenNuggets.setSelected(false);
            sp_chikenNuggets.setValue(0);
        }
        if(cb_chikenBurger.isSelected() && (Integer)sp_chikenBurger.getValue()>0){
            model.addRow(new Object[]{cb_chikenBurger.getText(), (Integer)sp_chikenBurger.getValue(), (Integer)sp_chikenBurger.getValue()*20000});
            cb_chikenBurger.setSelected(false);
            sp_chikenBurger.setValue(0);
        }
        if(cb_cheeseFries.isSelected() && (Integer)sp_cheeseFries.getValue()>0){
            model.addRow(new Object[]{cb_cheeseFries.getText(), (Integer)sp_cheeseFries.getValue(), (Integer)sp_cheeseFries.getValue()*15000});
            cb_cheeseFries.setSelected(false);
            sp_cheeseFries.setValue(0);
        }
        if(cb_sweetPotato.isSelected() && (Integer)sp_sweetPotato.getValue()>0){
            model.addRow(new Object[]{cb_sweetPotato.getText(), (Integer)sp_sweetPotato.getValue(), (Integer)sp_sweetPotato.getValue()*15000});
            cb_sweetPotato.setSelected(false);
            sp_sweetPotato.setValue(0);
        }
        if(cb_pepperoniPizza.isSelected() && (Integer)sp_pepperoniPizza.getValue()>0){
            model.addRow(new Object[]{cb_pepperoniPizza.getText(), (Integer)sp_pepperoniPizza.getValue(), (Integer)sp_pepperoniPizza.getValue()*30000});
            cb_pepperoniPizza.setSelected(false);
            sp_pepperoniPizza.setValue(0);
        }
        if(cb_Hamburger.isSelected() && (Integer)sp_Hamburger.getValue()>0){
            model.addRow(new Object[]{cb_Hamburger.getText(), (Integer)sp_Hamburger.getValue(), (Integer)sp_Hamburger.getValue()*25000});
            cb_Hamburger.setSelected(false);
            sp_Hamburger.setValue(0);
        }
        if(cb_creamSoup.isSelected() && (Integer)sp_creamSoup.getValue()>0){
            model.addRow(new Object[]{cb_creamSoup.getText(), (Integer)sp_creamSoup.getValue(), (Integer)sp_creamSoup.getValue()*12000});
            cb_creamSoup.setSelected(false);
            sp_creamSoup.setValue(0);
        }
        
        
        ////checkout pesanan minuman
        if(cb_esCampur.isSelected() && (Integer)sp_esCampur.getValue()>0){
            model.addRow(new Object[]{cb_esCampur.getText(), (Integer)sp_esCampur.getValue(), (Integer)sp_esCampur.getValue()*10000});
            cb_esCampur.setSelected(false);
            sp_esCampur.setValue(0);
        }
        if(cb_esDoger.isSelected() && (Integer)sp_esDoger.getValue()>0){
            model.addRow(new Object[]{cb_esDoger.getText(), (Integer)sp_esDoger.getValue(), (Integer)sp_esDoger.getValue()*10000});
            cb_esDoger.setSelected(false);
            sp_esDoger.setValue(0);
        }
        if(cb_Cendol.isSelected() && (Integer)sp_Cendol.getValue()>0){
            model.addRow(new Object[]{cb_Cendol.getText(), (Integer)sp_Cendol.getValue(), (Integer)sp_Cendol.getValue()*10000});
            cb_Cendol.setSelected(false);
            sp_Cendol.setValue(0);
        }
        if(cb_kelapaMuda.isSelected() && (Integer)sp_kelapaMuda.getValue()>0){
            model.addRow(new Object[]{cb_kelapaMuda.getText(), (Integer)sp_kelapaMuda.getValue(), (Integer)sp_kelapaMuda.getValue()*10000});
            cb_kelapaMuda.setSelected(false);
            sp_kelapaMuda.setValue(0);
        }
        if(cb_airMineral.isSelected() && (Integer)sp_airMineral.getValue()>0){
            model.addRow(new Object[]{cb_airMineral.getText(), (Integer)sp_airMineral.getValue(), (Integer)sp_airMineral.getValue()*5000});
            cb_airMineral.setSelected(false);
            sp_airMineral.setValue(0);
        }
        if(cb_tehManis.isSelected() && (Integer)sp_tehManis.getValue()>0){
            model.addRow(new Object[]{cb_tehManis.getText(), (Integer)sp_tehManis.getValue(), (Integer)sp_tehManis.getValue()*5000});
            cb_tehManis.setSelected(false);
            sp_tehManis.setValue(0);
        }
        if(cb_podeng.isSelected() && (Integer)sp_podeng.getValue()>0){
            model.addRow(new Object[]{cb_podeng.getText(), (Integer)sp_podeng.getValue(), (Integer)sp_podeng.getValue()*5000});
            cb_podeng.setSelected(false);
            sp_podeng.setValue(0);
        }
        if(cb_jeruk.isSelected() && (Integer)sp_jeruk.getValue()>0){
            model.addRow(new Object[]{cb_jeruk.getText(), (Integer)sp_jeruk.getValue(), (Integer)sp_jeruk.getValue()*5000});
            cb_jeruk.setSelected(false);
            sp_jeruk.setValue(0);
        }
        
        ////menambil nilai penjumlahan dari kolom qty di jTable dan masuk ke txt_totalItem
        int total1 = 0;
        baris = jTable1.getRowCount();
        for (int i =0; i < jTable1.getRowCount(); i++){
            int amount = (Integer)jTable1.getValueAt(i, 1);
            total1 += amount;
        }
        txt_totalItem.setText(""+total1);
        ///
        ////menambil nilai penjumlahan dari kolom Harga di jTable dan masuk ke txt_totalHarga
        double total = 0;
        for (int i =0; i < jTable1.getRowCount(); i++){
            double amount = (Integer)jTable1.getValueAt(i, 2);
            total += amount;
        }
        txt_totalHarga.setText("Rp. "+ total);
        ///
        
        btn_status.setText("BELUM DIBAYAR");
        btn_status.setBackground(Color.red);
        
    }//GEN-LAST:event_btn_checkoutActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
        KosongkanForm();
        ////menambil nilai penjumlahan dari kolom qty di jTable dan masuk ke txt_totalItem
        int total1 = 0;
        for (int i =0; i < jTable1.getRowCount(); i++){
            int amount = (Integer)jTable1.getValueAt(i, 1);
            total1 += amount;
        }
        txt_totalItem.setText(""+total1);
        ///
        ////menambil nilai penjumlahan dari kolom Harga di jTable dan masuk ke txt_totalHarga
        double total = 0;
        for (int i =0; i < jTable1.getRowCount(); i++){
            double amount = (Integer)jTable1.getValueAt(i, 2);
            total += amount;
        }
        txt_totalHarga.setText("Rp. "+ total);
        ///
        txt_inputUang.setText("");
        btn_status.setText("STATUS PEMBAYARAN");
        btn_status.setBackground(Color.blue);
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        int a = jTable1.getSelectedRow();
        int b = a + 1;
        DefaultTableModel dm = (DefaultTableModel)jTable1.getModel();
        dm.removeRow(a);
        ////menambil nilai penjumlahan dari kolom qty di jTable dan masuk ke txt_totalItem
        int total1 = 0;
        for (int i =0; i < jTable1.getRowCount(); i++){
            int amount = (Integer)jTable1.getValueAt(i, 1);
            total1 += amount;
        }
        txt_totalItem.setText(""+total1);
        ///
        ////menambil nilai penjumlahan dari kolom Harga di jTable dan masuk ke txt_totalHarga
        double total = 0;
        for (int i =0; i < jTable1.getRowCount(); i++){
            double amount = (Integer)jTable1.getValueAt(i, 2);
            total += amount;
        }
        txt_totalHarga.setText("Rp. "+ total);
        ///
        
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        // TODO add your handling code here:
        double total = 0;
        for (int i =0; i < jTable1.getRowCount(); i++){
            double amount = (Integer)jTable1.getValueAt(i, 2);
            total += amount;
        }
        
        int input_uang = (Integer)Integer.parseInt(txt_inputUang.getText());
        if(txt_inputUang.getText().equals("") || input_uang < total){
            JOptionPane.showMessageDialog(null, "Uang anda tidak mencukupi untuk melakukan transaksi ini");
//        }else if(txt_inputUang.getText().isEmpty()  || input_uang == 0){
//            JOptionPane.showMessageDialog(null, "Uang anda tidak mencukupi untuk melakukan transaksi ini");
        }else if(txt_inputUang.getText().isEmpty()){
//            JOptionPane.showMessageDialog(null, "Uang anda tidak mencukupi untuk melakukan transaksi ini");
        }
        else{
            JOptionPane.showMessageDialog(null, "Selamat pembelian anda berhasil silahkan cetak struk");
            try {
                btn_showStruk.setEnabled(true);
                btn_status.setText("LUNAS");
                btn_status.setBackground(Color.green);
                ///menginput data dari jTable ke database
                int b = 0;
                int barisTable = jTable1.getRowCount();
                String sql, sqla, sqlb;
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection(url, user,pass);
                stm = connect.createStatement();
                PreparedStatement pStatement = null;
                
                String userName = username_label.getText();
                String password = password_label.getText();
                String cekid = null;
                
                sql = "SELECT * FROM datauser WHERE username = '"+userName+"' AND password = '"+password+"'";
                ResultSet r = stm.executeQuery(sql);
                while(r.next()){
                    cekid = r.getString (1);
                }
                r.close();
                stm.close();
                
                
                sqla = "INSERT INTO datapembelian(kode_transaksi, id_user)" + "VALUES (?,?);";
                pStatement = connect.prepareStatement(sqla);
                pStatement.setString(1, kode_transaksi);
                pStatement.setString(2, cekid);
                pStatement.executeUpdate();
               
                for(int a = 0; a <= barisTable; a++){
                    sqlb = "INSERT INTO datatransaksi(kode_transaksi, nama_pesanan, quantity, total_harga)" + "VALUES (?,?,?,?);";
                    
                    pStatement = connect.prepareStatement(sqlb);

                    pStatement.setString(1, kode_transaksi);
                    pStatement.setString(2, jTable1.getValueAt(a,0).toString());
                    pStatement.setString(3, jTable1.getValueAt(a,1).toString());
                    pStatement.setString(4, jTable1.getValueAt(a,2).toString());

                    int intBaris = pStatement.executeUpdate();
                    if (intBaris>0) {
                        System.out.println("Berhasil menambahkan data");
                        JOptionPane.showMessageDialog(null, "Pembelian anda berhasil");
                    } else {
                        System.out.println("Penambahan data gagal");
                    }
                }
                btn_showStruk.setEnabled(true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        // TODO add your handling code here:
        new LoginPage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_exitActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new UserProfile().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void btn_showStrukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showStrukActionPerformed
        new StrukPage().setVisible(true);
        txt_inputUang.setText("");
        btn_showStruk.setEnabled(false);
    }//GEN-LAST:event_btn_showStrukActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new CalculatorPage().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        // TODO add your handling code here:
        openInstagram();
    }//GEN-LAST:event_jLabel36MouseClicked
    
         private void openInstagram() {
            if (Desktop.isDesktopSupported()) {
        try {
            Desktop.getDesktop().browse(new URI("https://www.instagram.com/qulhak/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
   

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelJamRealTime;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_checkout;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_reset;
    public static final javax.swing.JButton btn_showStruk = new javax.swing.JButton();
    private javax.swing.JButton btn_status;
    private javax.swing.JCheckBox cb_Cendol;
    private javax.swing.JCheckBox cb_Hamburger;
    private javax.swing.JCheckBox cb_Paincake;
    private javax.swing.JCheckBox cb_airMineral;
    private javax.swing.JCheckBox cb_cheeseFries;
    private javax.swing.JCheckBox cb_chikenBurger;
    private javax.swing.JCheckBox cb_chikenNuggets;
    private javax.swing.JCheckBox cb_creamSoup;
    private javax.swing.JCheckBox cb_esCampur;
    private javax.swing.JCheckBox cb_esDoger;
    private javax.swing.JCheckBox cb_jeruk;
    private javax.swing.JCheckBox cb_kelapaMuda;
    private javax.swing.JCheckBox cb_pepperoniPizza;
    private javax.swing.JCheckBox cb_podeng;
    private javax.swing.JCheckBox cb_sweetPotato;
    private javax.swing.JCheckBox cb_tehManis;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    private javax.swing.JSpinner sp_Cendol;
    private javax.swing.JSpinner sp_Hamburger;
    private javax.swing.JSpinner sp_Paincake;
    private javax.swing.JSpinner sp_airMineral;
    private javax.swing.JSpinner sp_cheeseFries;
    private javax.swing.JSpinner sp_chikenBurger;
    private javax.swing.JSpinner sp_chikenNuggets;
    private javax.swing.JSpinner sp_creamSoup;
    private javax.swing.JSpinner sp_esCampur;
    private javax.swing.JSpinner sp_esDoger;
    private javax.swing.JSpinner sp_jeruk;
    private javax.swing.JSpinner sp_kelapaMuda;
    private javax.swing.JSpinner sp_pepperoniPizza;
    private javax.swing.JSpinner sp_podeng;
    private javax.swing.JSpinner sp_sweetPotato;
    private javax.swing.JSpinner sp_tehManis;
    public static final javax.swing.JTextField txt_inputUang = new javax.swing.JTextField();
    public static final javax.swing.JLabel txt_totalHarga = new javax.swing.JLabel();
    public static final javax.swing.JLabel txt_totalItem = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables

    private static class object {

        public object() {
        }
    }
}
