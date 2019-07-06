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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Rakib
 */
public class addEmp extends javax.swing.JFrame {
    File file = null;
    int x,y;
    /**
     * Creates new form addEmp
     */
    public addEmp() {
        initComponents();
        showTime();
        randomnumber();
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
    
    
    private void saveData(){
         try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11","root","123");
            String query = "insert into employee(empid,name, fathernm, mothernm, parmanentadd, presentadd, dob, mobile, email, nid, designation, hiredate, salary,photo)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, empid.getText());
            pst.setString(2, name.getText());
            pst.setString(3, fname.getText());
            pst.setString(4, mname.getText());
            pst.setString(5, paradd.getText());
            pst.setString(6, preadd.getText());
            pst.setString(7, dob.getText());
            pst.setString(8, mob.getText());
            pst.setString(9, em.getText());
            pst.setString(10, nid.getText());
            pst.setString(11, deg.getText());
            pst.setString(12, hd.getText());
            pst.setString(13, sa.getText());
            //save image
            FileInputStream fin = new FileInputStream(file);
            int len = (int) file.length();
            pst.setBinaryStream(14, fin, len);
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Employee Added Sucessfully!");
        }
         catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    
    }
    private void clear(){
    //empid.setText("");
    name.setText("");
    fname.setText("");
    mname.setText("");
    paradd.setText("");
    preadd.setText("");
    dob.setText("");
    mob.setText("");
    em.setText("");
    nid.setText("");
    deg.setText("");
    hd.setText("");
    sa.setText("");
    lbl_im.setIcon(null);
    sarch.setText("");
    }
    
    public void showRecord(){
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11","root","123");
            String sql = "select empid, name, fathernm, mothernm, parmanentadd, presentadd,dob, mobile, email, nid, designation, hiredate, salary,photo from employee where name='"+sarch.getText() +"'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                empid.setText(rs.getString(1));
                name.setText(rs.getString(2));
                fname.setText(rs.getString(3));
                mname.setText(rs.getString(4));
                paradd.setText(rs.getString(5));
                preadd.setText(rs.getString(6));
                dob.setText(rs.getString(7));
                mob.setText(rs.getString(8));
                em.setText(rs.getString(9));
                nid.setText(rs.getString(10));
                deg.setText(rs.getString(11));
                hd.setText(rs.getString(12));
                sa.setText(rs.getString(13));
                //show image
               byte[] img = rs.getBytes("photo");
		ImageIcon image = new ImageIcon(img);
		Image im = image.getImage();
		Image myImg = im.getScaledInstance(128,128, Image.SCALE_DEFAULT);
		ImageIcon newImg = new ImageIcon(myImg);
		lbl_im.setIcon(newImg);

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
            String sql = "update employee set name=?, fathernm=?, mothernm=?, parmanentadd=?, presentadd=?,dob=?, mobile=?, email=?, nid=?, designation=?, hiredate=?, salary=? photo=? where name='"+sarch.getText()+"'";
            pst = con.prepareStatement(sql);
            pst.setString(1, name.getText());
            pst.setString(2, fname.getText());
            pst.setString(3, mname.getText());
            pst.setString(4, paradd.getText());
            pst.setString(5, preadd.getText());
            pst.setString(6, dob.getText());
            pst.setString(7, mob.getText());
            pst.setString(8, em.getText());
            pst.setString(9, nid.getText());
            pst.setString(10, deg.getText());
            pst.setString(11, hd.getText());
            int salary = Integer.parseInt(sa.getText());
            pst.setInt(12, salary);
            //update image
            FileInputStream fin = new FileInputStream(file);
            int len = (int) file.length();
            pst.setBinaryStream(13, fin, len);
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee Updated Succesfully");
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    public void deleteRecord(){
    Connection con = null;
        PreparedStatement pst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String sql = "delete from employee where name='"+sarch.getText()+"'";
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee Deleted Succesfully");
            clear();
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, e);
            e.printStackTrace();
        }
    
    }
    
    private void printRecord(){
     String id = empid.getText();
     String na = name.getText();
     String fn = fname.getText();
     String mn = mname.getText();
     String par = paradd.getText();
     String pre = preadd.getText();
     String bd = dob.getText();
     String mobile = mob.getText();
     String mail = em.getText();
     String nd = nid.getText();
     String desig = deg.getText();
     String hired = hd.getText();
     String salary = sa.getText();
        
     String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        Document doc = new Document();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path + "empinfo.pdf"));

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
            doc.add(new Paragraph(CENTER_ALIGNMENT, "                                        Employee Information", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.BLACK)));
            
            doc.add(new Paragraph(new Date().toString()));
            doc.add(new Paragraph("                                                          "));

            PdfPTable tbl = new PdfPTable(2);

            //Adding Header
            tbl.addCell("Employee ID:");
            tbl.addCell(id);
            tbl.addCell("Employee Name:");
            tbl.addCell(na);
            tbl.addCell("Father Name:");
            tbl.addCell(fn);
            tbl.addCell("Mother Name:");
            tbl.addCell(mn);
            tbl.addCell("Parmanent Address:");
            tbl.addCell(par);
            tbl.addCell("Present Address:");
            tbl.addCell(pre);
            tbl.addCell("Date of Birth:");
            tbl.addCell(bd);
            tbl.addCell("Mobile Number:");
            tbl.addCell(mobile);
            tbl.addCell("Email Address:");
            tbl.addCell(mail);
            tbl.addCell("National ID Number:");
            tbl.addCell(nd);
            tbl.addCell("Designation:");
            tbl.addCell(desig);
            tbl.addCell("Hire Date:");
            tbl.addCell(hired);
            tbl.addCell("Salary:");
            tbl.addCell(salary);

            

            doc.add(tbl);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(userList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(userList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JOptionPane.showMessageDialog(this, "Employee Information Print Succesfully");
        doc.close();
        

    }
    
    public void randomnumber(){
        Random ran = new Random();
        int n = ran.nextInt(10000)+1;
        String val = String.valueOf(n);
        
        Random ran1 = new Random();
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 1; i++) {
            char numGen = alpha.charAt(ran1.nextInt(alpha.length()));
            Object o = numGen;
            String number = o.toString();
            empid.setText(number+val);
            
        }
    
    
    
    }
    
    public void saveimage(){
        try {
            FileInputStream fin = new FileInputStream(file);
            int len = (int) file.length();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123");
            PreparedStatement pst = con.prepareStatement("insert into image values(?)");
            pst.setBinaryStream(1, fin, len);
            pst.executeUpdate();
        } catch (Exception e) {

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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        fname = new javax.swing.JTextField();
        mname = new javax.swing.JTextField();
        dob = new javax.swing.JTextField();
        mob = new javax.swing.JTextField();
        em = new javax.swing.JTextField();
        nid = new javax.swing.JTextField();
        deg = new javax.swing.JTextField();
        hd = new javax.swing.JTextField();
        sa = new javax.swing.JTextField();
        paradd = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        preadd = new javax.swing.JTextField();
        empid = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        lbl_im = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        lbl_time = new javax.swing.JLabel();
        sarch = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

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

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add New Employee");
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
                .addGap(350, 350, 350)
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Employee Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Name:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 76, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Father Name:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 130, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Parmanent Address:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 257, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Date of Birth");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 364, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Hire Date:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 310, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Salary:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 367, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Mother Name:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 200, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Designation:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 257, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("NID:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 190, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Email:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 130, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Mobile:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 73, -1, -1));

        name.setBackground(new java.awt.Color(22, 22, 22));
        name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        name.setOpaque(false);
        jPanel3.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 76, 218, 31));

        fname.setBackground(new java.awt.Color(22, 22, 22));
        fname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        fname.setForeground(new java.awt.Color(255, 255, 255));
        fname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        fname.setOpaque(false);
        jPanel3.add(fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 130, 218, 32));

        mname.setBackground(new java.awt.Color(22, 22, 22));
        mname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mname.setForeground(new java.awt.Color(255, 255, 255));
        mname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        mname.setOpaque(false);
        jPanel3.add(mname, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 190, 218, 32));

        dob.setBackground(new java.awt.Color(22, 22, 22));
        dob.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dob.setForeground(new java.awt.Color(255, 255, 255));
        dob.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        dob.setOpaque(false);
        jPanel3.add(dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 364, 218, 32));

        mob.setBackground(new java.awt.Color(22, 22, 22));
        mob.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mob.setForeground(new java.awt.Color(255, 255, 255));
        mob.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        mob.setOpaque(false);
        jPanel3.add(mob, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 73, 244, 32));

        em.setBackground(new java.awt.Color(22, 22, 22));
        em.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        em.setForeground(new java.awt.Color(255, 255, 255));
        em.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        em.setOpaque(false);
        jPanel3.add(em, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 130, 244, 32));

        nid.setBackground(new java.awt.Color(22, 22, 22));
        nid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nid.setForeground(new java.awt.Color(255, 255, 255));
        nid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        nid.setOpaque(false);
        jPanel3.add(nid, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 190, 244, 32));

        deg.setBackground(new java.awt.Color(22, 22, 22));
        deg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        deg.setForeground(new java.awt.Color(255, 255, 255));
        deg.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        deg.setOpaque(false);
        jPanel3.add(deg, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 257, 244, 32));

        hd.setBackground(new java.awt.Color(22, 22, 22));
        hd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hd.setForeground(new java.awt.Color(255, 255, 255));
        hd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        hd.setOpaque(false);
        jPanel3.add(hd, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 310, 244, 32));

        sa.setBackground(new java.awt.Color(22, 22, 22));
        sa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sa.setForeground(new java.awt.Color(255, 255, 255));
        sa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        sa.setOpaque(false);
        jPanel3.add(sa, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 364, 244, 32));

        paradd.setBackground(new java.awt.Color(22, 22, 22));
        paradd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        paradd.setForeground(new java.awt.Color(255, 255, 255));
        paradd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        paradd.setOpaque(false);
        jPanel3.add(paradd, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 257, 218, 32));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Present Address:");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 310, -1, -1));

        preadd.setBackground(new java.awt.Color(22, 22, 22));
        preadd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        preadd.setForeground(new java.awt.Color(255, 255, 255));
        preadd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        preadd.setOpaque(false);
        jPanel3.add(preadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 307, 218, 32));

        empid.setEditable(false);
        empid.setBackground(new java.awt.Color(16, 15, 15));
        empid.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        empid.setForeground(new java.awt.Color(255, 255, 255));
        empid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        empid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        empid.setCaretColor(new java.awt.Color(255, 255, 255));
        empid.setOpaque(false);
        jPanel3.add(empid, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 34, 124, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Employee ID:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 34, -1, -1));
        jPanel3.add(lbl_im, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 40, 160, 170));

        jButton6.setText("Photo");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 230, -1, -1));

        lbl_time.setBackground(new java.awt.Color(22, 22, 22));
        lbl_time.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_time.setForeground(new java.awt.Color(255, 255, 255));
        lbl_time.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_time.setText("   ");
        lbl_time.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        sarch.setBackground(new java.awt.Color(22, 22, 22));
        sarch.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        sarch.setForeground(new java.awt.Color(255, 255, 255));
        sarch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sarch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 51)));
        sarch.setCaretColor(new java.awt.Color(255, 255, 255));
        sarch.setOpaque(false);
        sarch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sarchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sarchFocusLost(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(22, 22, 22));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Search");
        jLabel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.orange, java.awt.Color.white, java.awt.Color.black));
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton1.setBackground(new java.awt.Color(22, 22, 22));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Submit");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(22, 22, 22));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Update");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(22, 22, 22));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Cancel");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(22, 22, 22));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Print");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(22, 22, 22));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Delete");
        jButton5.setOpaque(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_time, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sarch, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_time)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sarch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1128, 604));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        usermanegement um = new usermanegement();
        um.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        saveData();
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void sarchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sarchFocusLost
        showRecord();
    }//GEN-LAST:event_sarchFocusLost

    private void sarchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sarchFocusGained
        clear();
    }//GEN-LAST:event_sarchFocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        UpdateRecord();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        usermanegement um = new usermanegement();
        um.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        deleteRecord();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        printRecord();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(this);
        file = fileChooser.getSelectedFile();
        String path = file.getAbsolutePath();
        ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
        lbl_im.setIcon(icon);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
       int kX = evt.getXOnScreen();
        int kY = evt.getYOnScreen();
        this.setLocation(kX-x, kY-y);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
       x = getX();
       y = getY();
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
            java.util.logging.Logger.getLogger(addEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addEmp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField deg;
    private javax.swing.JTextField dob;
    private javax.swing.JTextField em;
    private javax.swing.JTextField empid;
    private javax.swing.JTextField fname;
    private javax.swing.JTextField hd;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JLabel lbl_im;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JTextField mname;
    private javax.swing.JTextField mob;
    private javax.swing.JTextField name;
    private javax.swing.JTextField nid;
    private javax.swing.JTextField paradd;
    private javax.swing.JTextField preadd;
    private javax.swing.JTextField sa;
    private javax.swing.JTextField sarch;
    // End of variables declaration//GEN-END:variables
}
