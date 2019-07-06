package expense;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.exp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import static user_management.userList.day;
import static user_management.userList.month;
import static user_management.userList.year;
import user_management.usermanegement;

/**
 *
 * @author NNC
 */
public class Total_Expense extends javax.swing.JFrame {
int x,y;
    /**
     * Creates new form Total_Expense
     */
    public Total_Expense() {
        initComponents();
        showTime();
        displayIntable();
        totalexpense();
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

    private void displayIntable() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String sql = "select * from expense";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object o[] = {rs.getString("expense"), rs.getString("Employee_name"), rs.getString("Payment_month"), rs.getInt("year"), rs.getInt("amount")};
                tm.addRow(o);

            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    private void totalexpense() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String query = "select sum(amount) from expense";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String sm = rs.getString("sum(amount)");
                total.setText(sm);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void monthexpense() {
        int counter = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
            String query = "select sum(amount) from expense where Payment_month='" + exp.getSelectedItem().toString() + "' and year='" + yar.getSelectedItem().toString() + "' ";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            String sm = "";

            if (rs.next()) {
                counter = rs.getRow();
                sm = rs.getString("sum(amount)");
                total1.setText(sm);
                String m = exp.getSelectedItem().toString();
                String y = yar.getSelectedItem().toString();
                yarex.setText("Total Expense of " + m + " " + y + ":");
            }

            if (counter < 0) {
                // if result not found               
                sm = "---";
                total1.setText(sm);
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
            String query = "select sum(amount) from expense where year='" + yar.getSelectedItem().toString() + "' ";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            String sm = "";

            if (rs.next()) {
                counter = rs.getRow();
                sm = rs.getString("sum(amount)");
                total.setText(sm);
              
                String y = yar.getSelectedItem().toString();
                month.setText("Total Expense of " + y + ":");
            }

            if (counter < 0) {
                // if result not found               
                sm = "---";
                total.setText(sm);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void expenses() {
        if (exp.getSelectedIndex() > 0) {
            Connection con = null;
            Statement stm = null;
            ResultSet rs = null;
            int count = 0;
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
                stm = con.createStatement();
                String sql = "select * from expense where Payment_month= '" + exp.getSelectedItem().toString() + "' ";
                //System.out.println(sql);
                rs = stm.executeQuery(sql);
                DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
                tm.setRowCount(0);
                while (rs.next()) {
                    count = rs.getRow();
                    Object o[] = {rs.getString("expense"), rs.getString("Employee_name"), rs.getString("Payment_month"), rs.getInt("year"), rs.getInt("amount")};
                    tm.addRow(o);
                    String mon = exp.getSelectedItem().toString() + " =";
                    month.setText("Total Expense of " + mon);
                    //monthexpense();
                    //System.out.println("count1111: " +count);

                }

                if (exp.getSelectedItem() == "--Select One--") {
                    totalexpense();
                    displayIntable();
                    month.setText("");

                }

                if (count == 0) {
                    total.setText("---");
                    month.setText("");
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                try {
                    con.close();
                    stm.close();
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Total_Expense.class.getName()).log(Level.SEVERE, null, ex);
                }

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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldis = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        lbl_time = new javax.swing.JLabel();
        exp = new javax.swing.JComboBox<>();
        yar = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        total = new javax.swing.JLabel();
        month = new javax.swing.JLabel();
        yarex = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(245, 246, 250));
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
        jLabel1.setText("Total Expense");
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
                .addContainerGap(290, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbldis.setBackground(new java.awt.Color(220, 221, 225));
        tbldis.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tbldis.setForeground(new java.awt.Color(0, 0, 0));
        tbldis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Expense Name", "Employee Name", "Payment Month", "Year", "Amount"
            }
        ));
        jScrollPane1.setViewportView(tbldis);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setOpaque(false);

        lbl_time.setBackground(new java.awt.Color(22, 22, 22));
        lbl_time.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_time.setForeground(new java.awt.Color(0, 0, 0));
        lbl_time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_time.setText("   ");
        lbl_time.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

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

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Month:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Year:");

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
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exp, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yar, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_time, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(yar)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(exp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_time, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jButton3.setBackground(new java.awt.Color(0, 51, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Print");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        total.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        total.setForeground(new java.awt.Color(0, 151, 230));
        total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 255, 255)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));

        month.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        month.setForeground(new java.awt.Color(25, 42, 86));
        month.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        yarex.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        yarex.setForeground(new java.awt.Color(25, 42, 86));
        yarex.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        total1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        total1.setForeground(new java.awt.Color(0, 151, 230));
        total1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 255, 255)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(411, 411, 411)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(yarex, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(111, 111, 111))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yarex, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jButton3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 979, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(977, 511));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        expense um = new expense();
        um.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //printUser();
    }//GEN-LAST:event_jButton3ActionPerformed

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
                String sql = "select * from expense where year='" + yar.getSelectedItem().toString() + "' ";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
                rs = pst.executeQuery();
                //tbldis.setModel(DbUtils.resultSetToTableModel(res));

                DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
                tm.setRowCount(0);
                while (rs.next()) {
                    Object o[] = {rs.getString("expense"), rs.getString("Employee_name"), rs.getString("Payment_month"), rs.getInt("year"), rs.getInt("amount")};
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
                String sql = "select * from expense where Payment_month='" + exp.getSelectedItem().toString() + "' and year='" + yar.getSelectedItem().toString() + "' ";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project11", "root", "123");
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
                rs = pst.executeQuery();
                //tbldis.setModel(DbUtils.resultSetToTableModel(res));

                DefaultTableModel tm = (DefaultTableModel) tbldis.getModel();
                tm.setRowCount(0);
                while (rs.next()) {
                    Object o[] = {rs.getString("expense"), rs.getString("Employee_name"), rs.getString("Payment_month"), rs.getInt("year"), rs.getInt("amount")};
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
            java.util.logging.Logger.getLogger(Total_Expense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Total_Expense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Total_Expense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Total_Expense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Total_Expense().setVisible(true);
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel month;
    private javax.swing.JTable tbldis;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total1;
    private javax.swing.JComboBox<String> yar;
    private javax.swing.JLabel yarex;
    // End of variables declaration//GEN-END:variables
}
