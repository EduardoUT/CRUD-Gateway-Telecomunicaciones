/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VentanasOperadores;

import Conexion.ConexionBD;
import Login.Login;
import VentanasAdministradores.Asignaciones;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author mcore
 */
public final class OperadorEntradasAlmacen extends javax.swing.JFrame {

    private Statement sent = null;
    private Connection cc = null;
    private ResultSet rs = null;
    private PreparedStatement p = null;
    int xMouse;
    int yMouse;
    DefaultTableModel model1;

    public OperadorEntradasAlmacen() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        LlenarTablaEntradas();
        ColumnasAutoajustadas(TablaEntradasOperador, margin);
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/Logo.png"));
        return retValue;

    }

    int vColIndex = 1;
    int margin = 2;

    public void ColumnasAutoajustadas(JTable TablaEntradasOperador, int margin) {
        for (int c = 0; c < TablaEntradasOperador.getColumnCount(); c++) {
            packColumnTablaEntradasOperador(TablaEntradasOperador, c, 2);
        }
    }

    public void packColumnTablaEntradasOperador(JTable TablaEntradasOperador, int vColIndex, int margin) {
        //model1 = (DefaultTableModel) jTable1.getModel();
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) TablaEntradasOperador.getColumnModel();

        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = TablaEntradasOperador.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(TablaEntradasOperador, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < TablaEntradasOperador.getRowCount(); r++) {
            renderer = TablaEntradasOperador.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(TablaEntradasOperador, TablaEntradasOperador.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public boolean DatosLlenos() {
        return !ET1.getText().equals("") && !ET2.getText().equals("")
                && !DateInOp.getDateFormatString().equals("")
                && !ET5.getText().equals("") && !ET6.getText().equals("");
    }

    public void LlenarTablaEntradas() {
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"Clave", "Descripcion", "Unidad de medida",
                "Line", "Fecha entrada", "Stock", "Ultimo Costo"};
            String SQL = "SELECT * FROM almacen";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[7];
            while (rs.next()) {
                fila[0] = rs.getString("clave");
                fila[1] = rs.getString("descrip");
                fila[2] = rs.getString("medida");
                fila[3] = rs.getString("line");
                fila[4] = rs.getString("f_ent");
                fila[5] = rs.getString("stock");
                fila[6] = rs.getString("ult_cost");
                model1.addRow(fila);
            }
            TablaEntradasOperador.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error a llenar la tabla" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void LimpiarDatosEntradas() {
        ET1.setText("");
        ET2.setText("");
        DateInOp.setCalendar(null);
        ET5.setText("");
        ET6.setText("");
    }

    public void Buscar() {
        String buscar = String.valueOf(ET1.getText());
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"Clave", "Descripcion", "Unidad de medida",
                "Line", "Fecha entrada", "Stock", "Ultimo Costo"};
            String SQL = "SELECT * FROM almacen WHERE clave='" + buscar + "'";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[7];
            while (rs.next()) {
                fila[0] = rs.getString("clave");
                fila[1] = rs.getString("descrip");
                fila[2] = rs.getString("medida");
                fila[3] = rs.getString("line");
                fila[4] = rs.getString("f_ent");
                fila[5] = rs.getString("stock");
                fila[6] = rs.getString("ult_cost");
                model1.addRow(fila);
            }
            TablaEntradasOperador.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al buscar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void Filtrar() {
        String filtrar = String.valueOf(CB3.getSelectedItem());
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"Clave", "Descripcion", "Unidad de medida",
                "Line", "Fecha entrada", "Stock", "Ultimo Costo"};
            String SQL = "SELECT * FROM almacen WHERE line='" + filtrar + "'";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[7];
            while (rs.next()) {
                fila[0] = rs.getString("clave");
                fila[1] = rs.getString("descrip");
                fila[2] = rs.getString("medida");
                fila[3] = rs.getString("line");
                fila[4] = rs.getString("f_ent");
                fila[5] = rs.getString("stock");
                fila[6] = rs.getString("ult_cost");
                model1.addRow(fila);
            }
            TablaEntradasOperador.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al filtrar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel3 = new FondoPanelTitulo();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Nombre_UsuarioAdmin = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        ID_Usuario = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ET1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaEntradasOperador = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        ET2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        CB2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        CB3 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ET5 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ET6 = new javax.swing.JTextField();
        DateInOp = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        Fondo = new FondoPanelPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(38, 101, 233));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton7.setText("-");
        jButton7.setToolTipText("Minimizar");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, -1, -1));

        jButton8.setBackground(new java.awt.Color(169, 7, 6));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton8.setText("x");
        jButton8.setToolTipText("Cerrar");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, -1, -1));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Operador Entradas Almacen");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 60));

        Nombre_UsuarioAdmin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Nombre_UsuarioAdmin.setForeground(new java.awt.Color(255, 255, 255));
        Nombre_UsuarioAdmin.setText("Nombre");
        jPanel3.add(Nombre_UsuarioAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jLabel34.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Bienvenido(a):");
        jPanel3.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        ID_Usuario.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ID_Usuario.setForeground(new java.awt.Color(255, 255, 255));
        ID_Usuario.setText("ID");
        jPanel3.add(ID_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        jLabel39.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("ID:");
        jPanel3.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Clave:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 73, -1, -1));

        ET1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ET1KeyTyped(evt);
            }
        });
        getContentPane().add(ET1, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 70, 100, -1));

        jButton3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 69, 75, -1));

        TablaEntradasOperador.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        TablaEntradasOperador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "CLAVE", "DESCRIPCION", "LINE", "FECHA_ENT", "STOCK", "ULT COSTO"
            }
        ));
        TablaEntradasOperador.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        TablaEntradasOperador.getTableHeader().setResizingAllowed(false);
        TablaEntradasOperador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TablaEntradasOperadorMousePressed(evt);
            }
        });
        TablaEntradasOperador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaEntradasOperadorKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(TablaEntradasOperador);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(478, 69, 476, 268));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Descripción");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 114, -1, -1));
        getContentPane().add(ET2, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 111, 185, -1));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Unidad de Medida:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 155, -1, -1));

        CB2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PIEZAS", "METROS", "KIT'S" }));
        getContentPane().add(CB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 152, 185, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Line:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 194, -1, -1));

        CB3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "AUT", "CEL", "CONS", "EC", "EP", "EPP", "HERR", "MAT", "MATER", "OFIC", "RAU", "SERV" }));
        getContentPane().add(CB3, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 191, 111, -1));

        jButton4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton4.setText("FILTRAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 190, -1, -1));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Fecha de Entrega:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 232, -1, -1));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("(AAAA-MM-DD)");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 232, -1, 20));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Stock (Existencias):");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 274, -1, -1));

        ET5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ET5KeyTyped(evt);
            }
        });
        getContentPane().add(ET5, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 271, 81, -1));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton6.setText("+");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(231, 270, -1, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Último Costo");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 317, -1, -1));

        ET6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ET6KeyTyped(evt);
            }
        });
        getContentPane().add(ET6, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 311, 111, -1));

        DateInOp.setDateFormatString("yyy/MM/dd");
        DateInOp.setOpaque(false);
        getContentPane().add(DateInOp, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 232, 177, -1));

        jButton2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton2.setText("Actualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 95, -1));

        jButton5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton5.setText("Stock");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 95, -1));

        jButton9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton9.setText("Cerrar Sesión");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, -1, -1));

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Formato(Sin Comas) Ej:3000000.00 ");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, -1, -1));

        Fondo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                FondoMouseDragged(evt);
            }
        });
        Fondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                FondoMousePressed(evt);
            }
        });
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 360));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void FondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_FondoMousePressed

    private void FondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_FondoMouseDragged

    private void ET1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ET1KeyTyped
        char cTeclaPresionada = evt.getKeyChar();
        if (cTeclaPresionada == KeyEvent.VK_ENTER) {
            jButton3.doClick();
        }
    }//GEN-LAST:event_ET1KeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Buscar();
        ColumnasAutoajustadas(TablaEntradasOperador, margin);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void TablaEntradasOperadorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaEntradasOperadorMousePressed
        if (evt.getClickCount() == 1) {
            int fila = TablaEntradasOperador.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "SELECT * FROM almacen WHERE clave='" + TablaEntradasOperador.getValueAt(fila, 0) + "'";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                ET1.setText(rs.getString("clave"));
                ET2.setText(rs.getString("descrip"));
                CB2.setSelectedItem(rs.getString("medida"));
                CB3.setSelectedItem(rs.getString("line"));
                DateInOp.setDate(rs.getTimestamp("f_ent"));
                ET5.setText(rs.getString("stock"));
                ET6.setText(rs.getString("ult_cost"));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Hubo un error al seleccionar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_TablaEntradasOperadorMousePressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Filtrar();
        ColumnasAutoajustadas(TablaEntradasOperador, margin);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void ET5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ET5KeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_ET5KeyTyped

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            try {
                String buscar = ET1.getText();
                int b = Integer.parseInt(ET5.getText());
                int sum;
                sum = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad a agregar:"));
                if (sum >= 0) {
                    if (sum < Integer.parseInt(ET5.getText())) {
                        try {
                            cc = ConexionBD.getcon();
                            if (DatosLlenos()) {
                                int d;
                                d = b + sum;
                                JOptionPane.showMessageDialog(null, d);
                                String SQL = "update almacen set clave=?,descrip=?,medida=?,line=?,"
                                        + "f_ent=?,stock=" + d + ",ult_cost=? where clave='" + buscar + "'";
                                p = cc.prepareStatement(SQL);
                                p.setString(1, ET1.getText());
                                p.setString(2, ET2.getText());
                                p.setString(3, CB2.getSelectedItem().toString());
                                p.setString(4, CB3.getSelectedItem().toString());
                                p.setString(5, ((JTextField) DateInOp.getDateEditor().getUiComponent()).getText());
                                p.setString(6, ET6.getText());
                                int n = p.executeUpdate();
                                if (n > 0) {
                                    LlenarTablaEntradas();
                                    LimpiarDatosEntradas();
                                    JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
                                    ET1.requestFocus();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Llena todos los campos");
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "Hubo un error al reducir las existencias del Almacèn: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        } finally {
                            if (p != null) {
                                try {
                                    p.close();
                                    p = null;
                                } catch (SQLException ex) {
                                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if (cc != null) {
                                try {
                                    cc.close();
                                    cc = null;
                                } catch (SQLException ex) {
                                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No puedes sumar valores que excedan el limite actual");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El valor no puede ser menor que cero.", "Error de entrada de datos", 0);
                }
            } catch (HeadlessException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El valor ingresado no es un número válido." + e.getMessage(), "Error de Entrada de Datos", 0);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void ET6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ET6KeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car != '.')) {
            evt.consume();
        }
    }//GEN-LAST:event_ET6KeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        LlenarTablaEntradas();
        ColumnasAutoajustadas(TablaEntradasOperador, margin);
        ET1.requestFocus();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void TablaEntradasOperadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaEntradasOperadorKeyReleased
        if ((evt.getKeyCode() == 38) || (evt.getKeyCode() == 40) || (evt.getKeyCode() == 33) || (evt.getKeyCode() == 34)) {
            int fila = TablaEntradasOperador.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "SELECT * FROM almacen WHERE clave='" + TablaEntradasOperador.getValueAt(fila, 0) + "'";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                ET1.setText(rs.getString("clave"));
                ET2.setText(rs.getString("descrip"));
                CB2.setSelectedItem(rs.getString("medida"));
                CB3.setSelectedItem(rs.getString("line"));
                DateInOp.setDate(rs.getTimestamp("f_ent"));
                ET5.setText(rs.getString("stock"));
                ET6.setText(rs.getString("ult_cost"));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Hubo un error al seleccionar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_TablaEntradasOperadorKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenos()) {
                int fila = TablaEntradasOperador.getSelectedRow();
                String dato = (String) TablaEntradasOperador.getValueAt(fila, 0);
                String SQL = "update almacen set clave=?,descrip=?,medida=?,line=?,f_ent=?,stock=?,ult_cost=? where clave=?";
                p = cc.prepareStatement(SQL);
                p.setString(1, ET1.getText());
                p.setString(2, ET2.getText());
                p.setString(3, CB3.getSelectedItem().toString());
                p.setString(4, CB3.getSelectedItem().toString());
                p.setString(5, ((JTextField) DateInOp.getDateEditor().getUiComponent()).getText());
                p.setString(6, ET5.getText());
                p.setString(7, ET6.getText());
                p.setString(8, dato);
                int n = p.executeUpdate();
                if (n > 0) {
                    LlenarTablaEntradas();
                    ColumnasAutoajustadas(TablaEntradasOperador, margin);
                    LimpiarDatosEntradas();
                    JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
                    ET1.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al actualizar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        dispose();
        Login log = new Login();
        log.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

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
            java.util.logging.Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OperadorEntradasAlmacen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OperadorEntradasAlmacen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CB2;
    private javax.swing.JComboBox<String> CB3;
    private com.toedter.calendar.JDateChooser DateInOp;
    private javax.swing.JTextField ET1;
    private javax.swing.JTextField ET2;
    private javax.swing.JTextField ET5;
    private javax.swing.JTextField ET6;
    private javax.swing.JLabel Fondo;
    public static javax.swing.JLabel ID_Usuario;
    public javax.swing.JLabel Nombre_UsuarioAdmin;
    private javax.swing.JTable TablaEntradasOperador;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    class FondoPanelPrincipal extends JLabel {

        private Image imagen;

        @Override
        public void paint(Graphics g) {
            imagen = new ImageIcon(getClass().getResource("/Imagenes/Background2.png")).getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class FondoPanelTitulo extends JPanel {

        private Image imagen;

        @Override
        public void paint(Graphics g) {
            imagen = new ImageIcon(getClass().getResource("/Imagenes/Clear Sky.jpg")).getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }
}
