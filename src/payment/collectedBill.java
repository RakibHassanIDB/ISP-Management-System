/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import user_management.userList;

/**
 *
 * @author Rakib
 */
public class collectedBill extends javax.swing.JFrame {
    int x,y;
    /**
     * Creates new form collectedBill
     */
    public collectedBill() {
        initComponents();
        displayIntable();
        totalexpense();
    }

    private void displayIntable() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String sql = "select * from billpay order by userid";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
            tm.setRowCount(0);
            //userid, username, paymonth, collamount, due
            while (rs.next()) {
                Object o[] = {rs.getString("userid"), rs.getString("username"), rs.getString("paymonth"),rs.getString("year"), rs.getString("payamount"), rs.getString("collamount")};
                tm.addRow(o);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

    private void printUser() {
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        Document doc = new Document();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path + "colbill_list.pdf"));

            doc.open();

            doc.add(new Paragraph(CENTER_ALIGNMENT, "                     Mahi Enterprise", FontFactory.getFont(FontFactory.TIMES_BOLD, 30, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("                                                         "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                           Ga-85, Gulshan Badda Link Road, Middle Badda, Dhaka-1212", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.PLAIN, BaseColor.DARK_GRAY)));
            doc.add(new Paragraph("                                                          "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                           Phone: +88 02 9860222 | +88 02 9860456 | +88 09614 820020", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.PLAIN, BaseColor.DARK_GRAY)));
            doc.add(new Paragraph("                                                          "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                                                             E-mail: info@mebd.net", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.PLAIN, BaseColor.DARK_GRAY)));
            doc.add(new Paragraph("                                                          "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                           Collected Bill List", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("                                                          "));

            PdfPTable tbl = new PdfPTable(5);

            //Adding Header 
            tbl.addCell("UserId");
            tbl.addCell("User Name");
            tbl.addCell("Billing Month");
            tbl.addCell("Bill Amount");
            tbl.addCell("Collected Amount");

            for (int i = 0; i < tbldis.getRowCount(); i++) {
                String id = tbldis.getValueAt(i, 0).toString();
                String uname = tbldis.getValueAt(i, 1).toString();
                String billmon = tbldis.getValueAt(i, 2).toString();
                String mob = tbldis.getValueAt(i, 3).toString();
                String billam = tbldis.getValueAt(i, 4).toString();

                tbl.addCell(id);
                tbl.addCell(uname);
                tbl.addCell(billmon);
                tbl.addCell(mob);
                tbl.addCell(billam);

            }

            doc.add(tbl);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(userList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(userList.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(this, "Collected Bill List Printed Succesfully");
        doc.close();

    }

//    private void expenses() {
//        if (mon.getSelectedIndex() > 0) {
//            // Connection con = null;
//            Statement stm = null;
//            ResultSet rs = null;
//            try {
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
//                stm = con.createStatement();
//                String sql = "select * from billpay where paymonth= '" + mon.getSelectedItem().toString() + "' ";
//                //System.out.println(sql);
//                rs = stm.executeQuery(sql);
//                DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
//                tm.setRowCount(0);
//                while (rs.next()) {
//                    Object o[] = {rs.getString("userid"), rs.getString("username"), rs.getString("paymonth"), rs.getString("payamount"), rs.getString("collamount")};
//                    tm.addRow(o);
//
//                    monthexpense();
//
//                }
//
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//        if (mon.getSelectedItem() == "--Select One--") {
//            displayIntable();
//            totalexpense();
//
//        }
//
////        if (expense.getSelectedItem() == "--Select One--") {
////            displayIntable();
////            totalexpense();
////
////        }
//    }

    private void monthexpense() {
        int counter = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String query = "select sum(collamount) from billpay where paymonth='" + exp.getSelectedItem().toString() + "' and year='" + yar.getSelectedItem().toString() + "'";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            String sm = "";
            
            if (rs.next()) {
                sm = rs.getString("sum(collamount)");
                moncoll.setText(sm);
                String m = exp.getSelectedItem().toString();
                String y = yar.getSelectedItem().toString();
                month.setText("Total Collection of " + m + " " + y + ":");

            }
            
            if (counter < 0) {
                // if result not found               
                sm = "---";
                moncoll.setText(sm);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void totalexpense() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String query = "select sum(collamount) from billpay";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String sm = rs.getString("sum(collamount)");
                yearcol.setText(sm);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    private void yearexpense() {
        int counter = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String query = "select sum(collamount) from billpay where year='" + yar.getSelectedItem().toString() + "' ";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            String sm = "";

            if (rs.next()) {
                counter = rs.getRow();
                sm = rs.getString("sum(collamount)");
                yearcol.setText(sm);
              
                String y = yar.getSelectedItem().toString();
                yarex.setText("Total Collection of " + y + ":");
            }

            if (counter < 0) {
                // if result not found               
                sm = "---";
                yearcol.setText(sm);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        total1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        yarex = new javax.swing.JLabel();
        month = new javax.swing.JLabel();
        yearcol = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldis = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        exp = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        yar = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        moncoll = new javax.swing.JLabel();

        total1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        total1.setForeground(new java.awt.Color(0, 151, 230));
        total1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 255, 255)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(16, 15, 15));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Collected Bill");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("_");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("X");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        yarex.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        yarex.setForeground(new java.awt.Color(255, 255, 255));
        yarex.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        month.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        month.setForeground(new java.awt.Color(255, 255, 255));
        month.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        yearcol.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        yearcol.setForeground(new java.awt.Color(0, 151, 230));
        yearcol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yearcol.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 255, 255)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));

        tbldis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Userid", "Username", "Billing Month", "Billing Year", "Bill Amount", "Collected Amount"
            }
        ));
        jScrollPane1.setViewportView(tbldis);

        jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.white, java.awt.Color.black), new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true)));
        jPanel3.setOpaque(false);

        jButton3.setBackground(new java.awt.Color(0, 51, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Print");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(323, 323, 323)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(344, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel4.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Search By ID:");

        txtid.setBackground(new java.awt.Color(16, 15, 15));
        txtid.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtid.setForeground(new java.awt.Color(255, 255, 255));
        txtid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtid.setCaretColor(new java.awt.Color(255, 255, 255));
        txtid.setOpaque(false);
        txtid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Month:");

        exp.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        exp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select One--", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        exp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                expItemStateChanged(evt);
            }
        });
        exp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Year:");

        yar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        yar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select One--", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025" }));
        yar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                yarItemStateChanged(evt);
            }
        });
        yar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yarActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(exp, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(yar, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtid)
                    .addComponent(jLabel7)
                    .addComponent(exp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(yar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        moncoll.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        moncoll.setForeground(new java.awt.Color(0, 151, 230));
        moncoll.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        moncoll.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 255, 255)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(moncoll, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(yarex, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearcol, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearcol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yarex, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moncoll, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(953, 504));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        payment pay = new payment();
        pay.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        printUser();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidKeyReleased
        DefaultTableModel table = (DefaultTableModel) tbldis.getModel();
        String search = txtid.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
        tbldis.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_txtidKeyReleased

    private void expItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_expItemStateChanged
        //        int counter = 0;
        //        Connection con = null;
        //        PreparedStatement pst = null;
        //        ResultSet rs = null;
        //        try {
            //            Class.forName("com.mysql.jdbc.Driver");
            //            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            //            String query = "select sum(amount) from expense where Payment_month='" + exp.getSelectedItem().toString() + "'";
            //            pst = con.prepareStatement(query);
            //            rs = pst.executeQuery();
            //
            //            String sm = "";
            //
            //            if (rs.next()) {
                //                counter += rs.getRow();
                //                sm = rs.getString("sum(amount)");
                //                total.setText(sm);
                //
                //            }
            //
            //            if (counter == 0) {
                //                // if result not found
                //
                //                total.setText(sm);
                //
                //            }
            //
            //        } catch (Exception e) {
            //            JOptionPane.showMessageDialog(null, e);
            //        } finally {
            //            try {
                //                con.close();
                //                pst.close();
                //                rs.close();
                //            } catch (SQLException ex) {
                //                Logger.getLogger(Total_Expense.class.getName()).log(Level.SEVERE, null, ex);
                //            }
            //
            //        }
        //
        //        expenses();

    }//GEN-LAST:event_expItemStateChanged

    private void expActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expActionPerformed

    private void yarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_yarItemStateChanged
        if (yar.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Complete Your Information", "Missing Information", 2);
        } else {
            try {
                String sql = "select * from billpay where year='" + yar.getSelectedItem().toString() + "' ";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
                rs = pst.executeQuery();
                //tbldis.setModel(DbUtils.resultSetToTableModel(res));

                DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
                tm.setRowCount(0);
                while (rs.next()) {
                    Object o[] = {rs.getString("userid"), rs.getString("username"), rs.getString("paymonth"),rs.getString("year"), rs.getString("payamount"), rs.getString("collamount")};
                    tm.addRow(o);
                    //bill.setText(rs.getString("bill"));

                }

                yearexpense();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 2);
            }
        }
    }//GEN-LAST:event_yarItemStateChanged

    private void yarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (exp.getSelectedIndex() == 0 || yar.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Complete Your Information", "Missing Information", 2);
        } else {
            try {
                String sql = "select * from billpay where paymonth='" + exp.getSelectedItem().toString() + "' and year='" + yar.getSelectedItem().toString() + "' ";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
                rs = pst.executeQuery();
                //tbldis.setModel(DbUtils.resultSetToTableModel(res));

                DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
                tm.setRowCount(0);
                while (rs.next()) {
                    Object o[] = {rs.getString("userid"), rs.getString("username"), rs.getString("paymonth"),rs.getString("year"), rs.getString("payamount"), rs.getString("collamount")};
                    tm.addRow(o);
                    //bill.setText(rs.getString("bill"));

                }

                monthexpense();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 2);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
       int kX = evt.getXOnScreen();
        int kY = evt.getYOnScreen();
        this.setLocation(kX-x, kY-y);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

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
            java.util.logging.Logger.getLogger(collectedBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(collectedBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(collectedBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(collectedBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new collectedBill().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> exp;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel moncoll;
    private javax.swing.JLabel month;
    private javax.swing.JTable tbldis;
    private javax.swing.JLabel total1;
    private javax.swing.JTextField txtid;
    private javax.swing.JComboBox<String> yar;
    private javax.swing.JLabel yarex;
    private javax.swing.JLabel yearcol;
    // End of variables declaration//GEN-END:variables
}
