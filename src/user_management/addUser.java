/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_management;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Rakib
 */
public class addUser extends javax.swing.JFrame {
int x,y;
    public addUser() {
        initComponents();
        Date da = new Date();
        SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd");
        lbl_date.setText(si.format(da));
        showTime();
        loadpack();

    }

    void showTime() {

        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat si = new SimpleDateFormat("hh:mm:ss a");
                lbl_time.setText(si.format(d));
            }
        }).start();

    }

    private void saveData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String query = "insert into users(userid, password, protocol, package, billingprofile, amount, fullname, mobile, address, nid, email, Connection_date)values(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username.getText());
            pst.setString(2, pass.getText());

            String protocol;
            protocol = pro.getSelectedItem().toString();
            pst.setString(3, protocol);

            String pakage;
            pakage = pac.getSelectedItem().toString();
            pst.setString(4, pakage);

            String billingpro;
            billingpro = billpro.getSelectedItem().toString();
            pst.setString(5, billingpro);

            bill.enable(false);
            if ((pac.getSelectedItem()) == "4 Mbps") {
                bill.setText("500");
            }
            if ((pac.getSelectedItem()) == "6 Mbps") {
                bill.setText("600");
            }
            if ((pac.getSelectedItem()) == "7 Mbps") {
                bill.setText("700");
            }
            if ((pac.getSelectedItem()) == "8 Mbps") {
                bill.setText("800");
            }
            if ((pac.getSelectedItem()) == "10 Mbps") {
                bill.setText("1000");
            }
            if ((pac.getSelectedItem()) == "12 Mbps") {
                bill.setText("1200");
            }
            if ((pac.getSelectedItem()) == "15 Mbps") {
                bill.setText("1500");
            }
            if ((pac.getSelectedItem()) == "20 Mbps") {
                bill.setText("2000");
            }
            pst.setString(6, bill.getText());

            pst.setString(7, fname.getText());

            pst.setString(8, mob.getText());

            pst.setString(9, ad.getText());

            pst.setString(10, nid.getText());

            pst.setString(11, em.getText());

            pst.setString(12, lbl_date.getText());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Added Sucessfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void clear() {
        username.setText("");
        pass.setText("");
        pro.setSelectedIndex(0);
        pac.setSelectedIndex(0);
        billpro.setSelectedIndex(0);
        bill.setText("");
        fname.setText("");
        mob.setText("");
        ad.setText("");
        nid.setText("");
        em.setText("");
        serch.setText("");
    }

    //userid password protocol package billingprofile amount fullname mobile address nid email
    public void showRecord() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String sql = "select userid, password, protocol, package, billingprofile,amount, fullname, mobile, address, nid, email, Connection_date from users where userid='" + serch.getText() + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                username.setText(rs.getString(1));
                pass.setText(rs.getString(2));
                pro.setSelectedItem(rs.getString(3));
                pac.setSelectedItem(rs.getString(4));
                billpro.setSelectedItem(rs.getString(5));
                bill.setText(rs.getString(6));
                fname.setText(rs.getString(7));
                mob.setText(rs.getString(8));
                ad.setText(rs.getString(9));
                nid.setText(rs.getString(10));
                em.setText(rs.getString(11));
                lbl_date.setText(rs.getString(12));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

    public void UpdateRecord() {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String sql = "update users set userid=?, password=?, protocol=?, package=?, billingprofile=?,"
                    + "amount=?, fullname=?, mobile=?, address=?, nid=?, email=? where userid='" + serch.getText() + "'";
            pst = con.prepareStatement(sql);
            pst.setString(1, username.getText());
            pst.setString(2, pass.getText());
            pst.setString(3, (String) pro.getSelectedItem());
            pst.setString(4, (String) pac.getSelectedItem());
            pst.setString(5, (String) billpro.getSelectedItem());
            pst.setString(6, bill.getText());
            pst.setString(7, fname.getText());
            pst.setString(8, mob.getText());
            pst.setString(9, ad.getText());
            pst.setString(10, nid.getText());
            pst.setString(11, em.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "User Updated Succesfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void deleteRecord() {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String sql = "delete from users where userid='" + serch.getText() + "'";
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "User Deleted Succesfully");
            clear();
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, e);
            e.printStackTrace();
        }

    }

    private void printRecord() {
        String id = username.getText();
        String pas = pass.getText();
        String proto = pro.getSelectedItem().toString();
        String pack = pac.getSelectedItem().toString();
        String profil = billpro.getSelectedItem().toString();
        String bil = bill.getText();
        String fuln = fname.getText();
        String mobile = mob.getText();
        String addr = ad.getText();
        String nd = nid.getText();
        String email = em.getText();
        String condate = lbl_date.getText();

        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        Document doc = new Document();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path + "userinfo.pdf"));

            doc.open();

            doc.add(new Paragraph(CENTER_ALIGNMENT, "                     Mahi Enterprise", FontFactory.getFont(FontFactory.TIMES_BOLD, 30, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("                                                         "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                           Ga-85, Gulshan Badda Link Road, Middle Badda, Dhaka-1212", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.PLAIN, BaseColor.DARK_GRAY)));
            doc.add(new Paragraph("                                                          "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                           Phone: +88 02 9860222 | +88 02 9860456 | +88 09614 820020", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.PLAIN, BaseColor.DARK_GRAY)));
            doc.add(new Paragraph("                                                          "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                                                             E-mail: info@mebd.net", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.PLAIN, BaseColor.DARK_GRAY)));
            doc.add(new Paragraph("                                                          "));
            doc.add(new Paragraph("                                                          "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                            User Information", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.BLACK)));

            doc.add(new Paragraph(new Date().toString()));
            doc.add(new Paragraph("                                                          "));

            PdfPTable tbl = new PdfPTable(2);

            //Adding Header 
            tbl.addCell("UserId:");
            tbl.addCell(id);
            tbl.addCell("Password:");
            tbl.addCell(pas);
            tbl.addCell("Protocol:");
            tbl.addCell(proto);
            tbl.addCell("Package:");
            tbl.addCell(pack);
            tbl.addCell("Billing Profile:");
            tbl.addCell(profil);
            tbl.addCell("Bill Amount:");
            tbl.addCell(bil);
            tbl.addCell("UserName:");
            tbl.addCell(fuln);
            tbl.addCell("Mobile Number:");
            tbl.addCell(mobile);
            tbl.addCell("Address:");
            tbl.addCell(addr);
            tbl.addCell("NID Number:");
            tbl.addCell(nd);
            tbl.addCell("Email Address:");
            tbl.addCell(email);
            tbl.addCell("Connection Date:");
            tbl.addCell(condate);

            doc.add(tbl);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(userList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(userList.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(this, "User Information Print Succesfully");
        doc.close();

    }

    private void loadpack() {
        Connection con = null;
        Statement stm = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ResultSet rs1 = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            stm = con.createStatement();

            //combobox
            String query = "select package from package order by package";
            rs = stm.executeQuery(query);
            List plist = new ArrayList();
            while (rs.next()) {
                plist.add(rs.getString("package"));
            }
            pac.setModel(new javax.swing.DefaultComboBoxModel(plist.toArray()));
            pac.insertItemAt("Select One", 0);
            pac.setSelectedIndex(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void billamnt() {
        if (pac.getSelectedIndex() > 0) {
            // Connection con = null;
            Statement stm = null;
            ResultSet rs = null;
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
                stm = con.createStatement();
                String sql = "select bill from package where package= '" + pac.getSelectedItem().toString() + "' ";
                //System.out.println(sql);
                rs = stm.executeQuery(sql);
                while (rs.next()) {
//                    int s = rs.getInt("salary");
//                    //String salare = Integer.parseInt(s);
//                    amn.setText("s");
                    //System.out.println(rs.getString("salary"));
                    bill.setText(rs.getString("bill"));

                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
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

        jPanel1 = new javax.swing.JPanel();
        lbl_time = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        pass = new javax.swing.JTextField();
        pro = new javax.swing.JComboBox<>();
        pac = new javax.swing.JComboBox<>();
        billpro = new javax.swing.JComboBox<>();
        bill = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        nid = new javax.swing.JTextField();
        mob = new javax.swing.JTextField();
        em = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ad = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        fname = new javax.swing.JTextField();
        lbl_date = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        save = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        serch = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(22, 22, 22));
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

        lbl_time.setBackground(new java.awt.Color(22, 22, 22));
        lbl_time.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_time.setForeground(new java.awt.Color(255, 255, 255));
        lbl_time.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_time.setText("   ");
        lbl_time.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add New User");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("_");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
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
                .addGap(95, 95, 95)
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

        jPanel4.setBackground(new java.awt.Color(16, 15, 15));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Technical Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("UserID:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Package:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Billing Profile:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Bill Amount:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Password:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Protocol:");

        username.setBackground(new java.awt.Color(16, 15, 15));
        username.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        username.setForeground(new java.awt.Color(255, 255, 255));
        username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        username.setCaretColor(new java.awt.Color(255, 255, 255));
        username.setOpaque(false);

        pass.setBackground(new java.awt.Color(16, 15, 15));
        pass.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pass.setForeground(new java.awt.Color(255, 255, 255));
        pass.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        pass.setCaretColor(new java.awt.Color(255, 255, 255));
        pass.setOpaque(false);

        pro.setBackground(new java.awt.Color(16, 15, 15));
        pro.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pro.setForeground(new java.awt.Color(255, 255, 255));
        pro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PPP", "Hotspot" }));
        pro.setOpaque(false);

        pac.setBackground(new java.awt.Color(16, 15, 15));
        pac.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pac.setForeground(new java.awt.Color(255, 255, 255));
        pac.setOpaque(false);
        pac.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pacItemStateChanged(evt);
            }
        });

        billpro.setBackground(new java.awt.Color(16, 15, 15));
        billpro.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        billpro.setForeground(new java.awt.Color(255, 255, 255));
        billpro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 to 7th", "1 to 10th", "1 to 15th", "1 to 20th", "Not Disconected" }));
        billpro.setOpaque(false);

        bill.setEditable(false);
        bill.setBackground(new java.awt.Color(16, 15, 15));
        bill.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        bill.setForeground(new java.awt.Color(255, 255, 255));
        bill.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        bill.setOpaque(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pass, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pro, javax.swing.GroupLayout.Alignment.LEADING, 0, 207, Short.MAX_VALUE)
                    .addComponent(username))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billpro, 0, 209, Short.MAX_VALUE)
                    .addComponent(bill))
                .addGap(12, 12, 12))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pac, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billpro, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(pro, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bill, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(16, 15, 15));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "General Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Fullname:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("NID:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Email:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Mobile No.");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Address:");

        nid.setBackground(new java.awt.Color(16, 15, 15));
        nid.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        nid.setForeground(new java.awt.Color(255, 255, 255));
        nid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        nid.setCaretColor(new java.awt.Color(255, 255, 255));
        nid.setOpaque(false);

        mob.setBackground(new java.awt.Color(16, 15, 15));
        mob.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        mob.setForeground(new java.awt.Color(255, 255, 255));
        mob.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        mob.setCaretColor(new java.awt.Color(255, 255, 255));
        mob.setOpaque(false);

        em.setBackground(new java.awt.Color(16, 15, 15));
        em.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        em.setForeground(new java.awt.Color(255, 255, 255));
        em.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        em.setCaretColor(new java.awt.Color(255, 255, 255));
        em.setOpaque(false);

        ad.setBackground(new java.awt.Color(16, 15, 15));
        ad.setColumns(20);
        ad.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ad.setForeground(new java.awt.Color(255, 255, 255));
        ad.setRows(5);
        ad.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        ad.setCaretColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(ad);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Connection Date:");

        fname.setBackground(new java.awt.Color(16, 15, 15));
        fname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        fname.setForeground(new java.awt.Color(255, 255, 255));
        fname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        fname.setCaretColor(new java.awt.Color(255, 255, 255));
        fname.setOpaque(false);

        lbl_date.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbl_date.setForeground(new java.awt.Color(255, 255, 255));
        lbl_date.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mob, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                    .addComponent(fname))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(39, 39, 39)
                                        .addComponent(em, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nid, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(nid, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(mob, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(em, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel15))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setOpaque(false);

        jButton1.setBackground(new java.awt.Color(16, 15, 15));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Delete");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(16, 15, 15));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Print");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(16, 15, 15));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Cancel");
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        save.setBackground(new java.awt.Color(16, 15, 15));
        save.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        save.setForeground(new java.awt.Color(255, 255, 255));
        save.setText("Submit");
        save.setBorder(null);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(16, 15, 15));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Update");
        jButton5.setBorder(null);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        serch.setBackground(new java.awt.Color(22, 22, 22));
        serch.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        serch.setForeground(new java.awt.Color(255, 255, 255));
        serch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        serch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 0, 0)));
        serch.setCaretColor(new java.awt.Color(255, 255, 255));
        serch.setOpaque(false);
        serch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                serchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                serchFocusLost(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(16, 15, 15));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Search");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_time, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(serch, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_time)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(serch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        setSize(new java.awt.Dimension(721, 741));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        usermanegement um = new usermanegement();
        um.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        saveData();
        clear();
    }//GEN-LAST:event_saveActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        showRecord();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void serchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchFocusLost
        showRecord();
    }//GEN-LAST:event_serchFocusLost

    private void serchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchFocusGained
        clear();
    }//GEN-LAST:event_serchFocusGained

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        UpdateRecord();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        deleteRecord();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        printRecord();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        usermanegement um = new usermanegement();
        um.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void pacItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pacItemStateChanged
        billamnt();
    }//GEN-LAST:event_pacItemStateChanged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        x = getX();
        y = getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
      int kX = evt.getXOnScreen();
        int kY = evt.getYOnScreen();
        this.setLocation(kX-x, kY-y);
    }//GEN-LAST:event_jPanel1MouseDragged

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
            java.util.logging.Logger.getLogger(addUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ad;
    private javax.swing.JTextField bill;
    private javax.swing.JComboBox<String> billpro;
    private javax.swing.JTextField em;
    private javax.swing.JTextField fname;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JTextField mob;
    private javax.swing.JTextField nid;
    private javax.swing.JComboBox<String> pac;
    private javax.swing.JTextField pass;
    private javax.swing.JComboBox<String> pro;
    private javax.swing.JButton save;
    private javax.swing.JTextField serch;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
