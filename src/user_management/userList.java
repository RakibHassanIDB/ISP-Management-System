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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Rakib
 */
public class userList extends javax.swing.JFrame {
int x,y;
    /**
     * Creates new form userList
     */
    public userList() {
        initComponents();
        // showTime();
        displayIntable();
        totaluser();
    }

    private void totaluser() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String query = "select count(userid) from users";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String counter = rs.getString("count(userid)");
                tuser.setText(counter);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void userpack() {
        if (pack.getSelectedIndex() > 0) {
            int counter = 0;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
                String query = "select count(userid) from users where package='" + pack.getSelectedItem().toString() + "'";
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    counter = rs.getRow();
                    String counterr = rs.getString("count(userid)");
                    packuser.setText(counterr);
                    String pu = pack.getSelectedItem().toString();
                    usepack.setText("Total user of "+pu+":");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            if (pack.getSelectedItem() == "--Select One--") {
                packuser.setText("");

            }
        }
    }

    private void displayIntable() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String sql = "select * from users";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
            tm.setRowCount(0);
            //userid, password, username, mobile, address, bill
            while (rs.next()) {
                Object o[] = {rs.getString("userid"), rs.getInt("password"), rs.getString("fullname"), rs.getString("mobile"), rs.getString("address"), rs.getInt("amount"), rs.getString("Connection_date")};
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
            PdfWriter.getInstance(doc, new FileOutputStream(path + "userlist.pdf"));

            doc.open();

            doc.add(new Paragraph(CENTER_ALIGNMENT, "                     Mahi Enterprise", FontFactory.getFont(FontFactory.TIMES_BOLD, 30, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("                                                         "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                           Ga-85, Gulshan Badda Link Road, Middle Badda, Dhaka-1212", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.PLAIN, BaseColor.DARK_GRAY)));
            doc.add(new Paragraph("                                                          "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                           Phone: +88 02 9860222 | +88 02 9860456 | +88 09614 820020", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.PLAIN, BaseColor.DARK_GRAY)));
            doc.add(new Paragraph("                                                          "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                                                             E-mail: info@mebd.net", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.PLAIN, BaseColor.DARK_GRAY)));
            doc.add(new Paragraph("                                                          "));
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                                  User List", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.BLACK)));
            doc.add(new Paragraph("                                                          "));

            PdfPTable tbl = new PdfPTable(6);

            //Adding Header 
            tbl.addCell("UserId");
            tbl.addCell("Password");
            tbl.addCell("Name");
            tbl.addCell("Mobile");
            tbl.addCell("Address");
            tbl.addCell("Bill");

            for (int i = 0; i < tbldis.getRowCount(); i++) {
                String id = tbldis.getValueAt(i, 0).toString();
                String password = tbldis.getValueAt(i, 1).toString();
                String name = tbldis.getValueAt(i, 2).toString();
                String mob = tbldis.getValueAt(i, 3).toString();
                String add = tbldis.getValueAt(i, 4).toString();
                String bil = tbldis.getValueAt(i, 5).toString();

                tbl.addCell(id);
                tbl.addCell(password);
                tbl.addCell(name);
                tbl.addCell(mob);
                tbl.addCell(add);
                tbl.addCell(bil);
            }

            doc.add(tbl);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(userList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(userList.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(this, "Userlist Print Succesfully");
        doc.close();

    }

//    private void packageuser() {
//        if (pack.getSelectedIndex() > 0) {
//            // Connection con = null;
//            Statement stm = null;
//            ResultSet rs = null;
//            try {
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
//                stm = con.createStatement();
//                String sql = "select * from users where package= '" + pack.getSelectedItem().toString() + "' ";
//                //System.out.println(sql);
//                rs = stm.executeQuery(sql);
//                DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
//                tm.setRowCount(0);
//                while (rs.next()) {
//                    Object o[] = {rs.getString("userid"), rs.getInt("password"), rs.getString("fullname"), rs.getString("mobile"), rs.getString("address"), rs.getInt("amount"), rs.getString("Connection_date")};
//                    tm.addRow(o);
//                    //bill.setText(rs.getString("bill"));
//
//                }
//
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//        if (pack.getSelectedItem() == "--Select One--") {
//            displayIntable();
//
//        }
//    }
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
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldis = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        tuser = new javax.swing.JLabel();
        usepack = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        packuser = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        pack = new javax.swing.JComboBox<>();
        day = new javax.swing.JComboBox<>();
        month = new javax.swing.JComboBox<>();
        year = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(21, 21, 21));
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
        jLabel1.setText("User List");
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
                .addGap(259, 259, 259)
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

        tbldis.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tbldis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "userid", "password", "username", "mobile", "address", "bill", "Connection Date"
            }
        ));
        tbldis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldisMouseClicked(evt);
            }
        });
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

        tuser.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        tuser.setForeground(new java.awt.Color(255, 255, 0));
        tuser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tuser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153), 2));

        usepack.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        usepack.setForeground(new java.awt.Color(255, 255, 255));
        usepack.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Total User:");

        packuser.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        packuser.setForeground(new java.awt.Color(255, 255, 0));
        packuser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        packuser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153), 2));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tuser, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(usepack, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(packuser, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tuser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(packuser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(usepack))
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Search By ID:");

        txtid.setBackground(new java.awt.Color(16, 15, 15));
        txtid.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtid.setForeground(new java.awt.Color(255, 255, 255));
        txtid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtid.setOpaque(false);
        txtid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidKeyReleased(evt);
            }
        });

        pack.setBackground(new java.awt.Color(16, 15, 15));
        pack.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        pack.setForeground(new java.awt.Color(255, 255, 255));
        pack.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select One--", "4 Mbps", "6 Mbps", "7 Mbps", "8 Mbps", "10 Mbps", "12 Mbps", "15 Mbps", "18 Mbps", "20 Mbps" }));
        pack.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                packItemStateChanged(evt);
            }
        });

        day.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        day.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day : ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        month.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month : ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        year.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Year : ", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2021", "2023", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));

        jButton1.setBackground(new java.awt.Color(0, 51, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Search");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Search By Package:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(day, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(pack, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(day)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtid))
                    .addComponent(month)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        setSize(new java.awt.Dimension(1100, 479));
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

    private void txtidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidKeyReleased
        DefaultTableModel table = (DefaultTableModel) tbldis.getModel();
        String search = txtid.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
        tbldis.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_txtidKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        printUser();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void packItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_packItemStateChanged
        //packageuser();
        //    private void packageuser() {
        if (pack.getSelectedIndex() > 0) {
            // Connection con = null;
            Statement stm = null;
            ResultSet rs = null;
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
                stm = con.createStatement();
                String sql = "select * from users where package= '" + pack.getSelectedItem().toString() + "' ";
                //System.out.println(sql);
                rs = stm.executeQuery(sql);
                DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
                tm.setRowCount(0);
                while (rs.next()) {
                    Object o[] = {rs.getString("userid"), rs.getInt("password"), rs.getString("fullname"), rs.getString("mobile"), rs.getString("address"), rs.getInt("amount"), rs.getString("Connection_date")};
                    tm.addRow(o);
                    //bill.setText(rs.getString("bill"));

                }

                con.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
//        if (pack.getSelectedItem() == "--Select One--") {
//            displayIntable();
//
//        }
//    }

        userpack();

    }//GEN-LAST:event_packItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (day.getSelectedIndex() == 0 || month.getSelectedIndex() == 0 || year.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Complete Your Information", "Missing Information", 2);
        } else {
            try {
                String sql = "select * from users where Connection_date='" + year.getSelectedItem() + "-" + month.getSelectedItem() + "-" + day.getSelectedItem().toString() + "' ";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
                rs = pst.executeQuery();
                //tbldis.setModel(DbUtils.resultSetToTableModel(res));

                DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
                tm.setRowCount(0);
                while (rs.next()) {
                    Object o[] = {rs.getString("userid"), rs.getInt("password"), rs.getString("fullname"), rs.getString("mobile"), rs.getString("address"), rs.getInt("amount"), rs.getString("Connection_date")};
                    tm.addRow(o);
                    //bill.setText(rs.getString("bill"));

                }

                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 2);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbldisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldisMouseClicked
        viewUser vu = new viewUser();
        int index = tbldis.getSelectedRow();

        TableModel model = tbldis.getModel();

        String id = model.getValueAt(index, 0).toString();
        String password = model.getValueAt(index, 1).toString();
        String usern = model.getValueAt(index, 2).toString();
        String mobile = model.getValueAt(index, 3).toString();
        String address = model.getValueAt(index, 4).toString();
        String amount = model.getValueAt(index, 5).toString();
        String condate = model.getValueAt(index, 6).toString();

        vu.setVisible(true);
        vu.pack();
        vu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        vu.uid.setText(id);
        vu.pas.setText(password);
        vu.un.setText(usern);
        vu.mob.setText(mobile);
        vu.add.setText(address);
        vu.bill.setText(amount);
        vu.cond.setText(condate);
    }//GEN-LAST:event_tbldisMouseClicked

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
      x = evt.getX();
        y = evt.getY();
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
            java.util.logging.Logger.getLogger(userList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new userList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> day;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JComboBox<String> month;
    private javax.swing.JComboBox<String> pack;
    private javax.swing.JLabel packuser;
    private javax.swing.JTable tbldis;
    private javax.swing.JLabel tuser;
    private javax.swing.JTextField txtid;
    private javax.swing.JLabel usepack;
    public static javax.swing.JComboBox<String> year;
    // End of variables declaration//GEN-END:variables
}
