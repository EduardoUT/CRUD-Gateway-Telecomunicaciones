package VentanasOperadores;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mcore
 */
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

public final class OperadorSalidasAlmacen extends javax.swing.JFrame {

    int xMouse;
    int yMouse;
    ConexionBD cnc = new ConexionBD();
    Connection cc = ConexionBD.getcon();
    DefaultTableModel model1;
    Statement sent;

    public OperadorSalidasAlmacen() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        LlenarTablaSalidas();
        ColumnasAutoajustadas(TablaSalidasOperador, margin);
        Key.requestFocus();
    }
    
    @Override
    public Image getIconImage(){
        Image retValue = Toolkit.getDefaultToolkit().
        getImage(ClassLoader.getSystemResource("Imagenes/Logo.png"));
        return retValue;
        
    }

    int vColIndex = 1;
    int margin = 2;

    public void ColumnasAutoajustadas(JTable TablaSalidasOperador, int margin) {
        for (int c = 0; c < TablaSalidasOperador.getColumnCount(); c++) {
            packColumnTablaFacturas(TablaSalidasOperador, c, 2);
        }
    }

    public void packColumnTablaFacturas(JTable TablaSalidasOperador, int vColIndex, int margin) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) TablaSalidasOperador.getColumnModel();
        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = TablaSalidasOperador.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(TablaSalidasOperador, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < TablaSalidasOperador.getRowCount(); r++) {
            renderer = TablaSalidasOperador.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(TablaSalidasOperador, TablaSalidasOperador.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public boolean DatosLlenos() {
        return !Key.getText().equals("")
                && !Dsc.getText().equals("")
                && !DateOutOp.getDateFormatString().equals("")
                && !StockOut.getText().equals("")
                && !LastPriceOut.getText().equals("");
    }

    public void LlenarTablaSalidas() {
        try {
            String[] titulos = {"Clave", "Descripcion", "Unidad de medida",
                "Line", "Fecha entrada", "Stock", "Ultimo Costo"};
            String SQL = "SELECT * FROM almacen";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            try (ResultSet rs = sent.executeQuery(SQL)) {
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
                TablaSalidasOperador.setModel(model1);
                rs.close();
                sent.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al llenar la tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void LimpiarDatosSalidas() {
        Key.setText("");
        Dsc.setText("");
        DateOutOp.setCalendar(null);
        StockOut.setText("");
        LastPriceOut.setText("");
    }

    public void Buscar() {
        String buscar = String.valueOf(Key.getText());
        try {
            String[] titulos = {"Clave", "Descripcion", "Unidad de medida",
                "Line", "Fecha entrada", "Stock", "Ultimo Costo"};
            String SQL = "SELECT * FROM almacen WHERE clave='" + buscar + "'";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            try (ResultSet rs = sent.executeQuery(SQL)) {
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
                TablaSalidasOperador.setModel(model1);
                rs.close();
                sent.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al buscar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Filtrar() {
        String filtrar = String.valueOf(LineOut.getSelectedItem());
        try {
            String[] titulos = {"Clave", "Descripcion", "Unidad de medida",
                "Line", "Fecha entrada", "Stock", "Ultimo Costo"};
            String SQL = "SELECT * FROM almacen WHERE line='" + filtrar + "'";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            try (ResultSet rs = sent.executeQuery(SQL)) {
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
                TablaSalidasOperador.setModel(model1);
                rs.close();
                sent.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al filtrar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabel10 = new javax.swing.JLabel();
        Key = new javax.swing.JTextField();
        SearchKeyOut = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        Dsc = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        Unit = new javax.swing.JComboBox<>();
        FilterOutButton = new javax.swing.JButton();
        StockOutButton = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new FondoPanelTitulo();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Nombre_UsuarioAdmin = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        ID_Usuario = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        UpdateOutButton = new javax.swing.JButton();
        StockOut = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        MinusOutButton = new javax.swing.JButton();
        LineOut = new javax.swing.JComboBox<>();
        DateOutOp = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaSalidasOperador = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        LastPriceOut = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Fondo = new FondoPanelPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Clave:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 73, -1, -1));

        Key.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Key.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                KeyKeyTyped(evt);
            }
        });
        getContentPane().add(Key, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 70, 100, -1));

        SearchKeyOut.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        SearchKeyOut.setText("Buscar");
        SearchKeyOut.setToolTipText("Buscar Clave");
        SearchKeyOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchKeyOutActionPerformed(evt);
            }
        });
        getContentPane().add(SearchKeyOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 69, 75, -1));

        jLabel11.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Descripción");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 114, -1, -1));

        Dsc.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        getContentPane().add(Dsc, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 111, 185, -1));

        jLabel12.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Unidad de Medida:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 155, -1, -1));

        Unit.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Unit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PIEZAS", "METROS", "KIT'S" }));
        getContentPane().add(Unit, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 152, 185, -1));

        FilterOutButton.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        FilterOutButton.setText("FILTRAR");
        FilterOutButton.setToolTipText("Filtrar Registro");
        FilterOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilterOutButtonActionPerformed(evt);
            }
        });
        getContentPane().add(FilterOutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 190, -1, -1));

        StockOutButton.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        StockOutButton.setText("Stock");
        StockOutButton.setToolTipText("Mostrar Stock Completo");
        StockOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StockOutButtonActionPerformed(evt);
            }
        });
        getContentPane().add(StockOutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 95, -1));

        jLabel13.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Line:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 194, -1, -1));

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
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 10, -1, -1));

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
        jPanel3.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, -1, -1));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Operador Salidas Almacen");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 60));

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

        UpdateOutButton.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        UpdateOutButton.setText("Actualizar");
        UpdateOutButton.setToolTipText("Actualizar Registro");
        UpdateOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateOutButtonActionPerformed(evt);
            }
        });
        getContentPane().add(UpdateOutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 95, -1));

        StockOut.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        StockOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                StockOutKeyTyped(evt);
            }
        });
        getContentPane().add(StockOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 271, 81, -1));

        jLabel17.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Último Costo");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 317, -1, -1));

        MinusOutButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        MinusOutButton.setText("-");
        MinusOutButton.setToolTipText("Restar Existencias");
        MinusOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinusOutButtonActionPerformed(evt);
            }
        });
        getContentPane().add(MinusOutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(363, 270, -1, -1));

        LineOut.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        LineOut.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "AUT", "CEL", "CONS", "EC", "EP", "EPP", "HERR", "MAT", "MATER", "OFIC", "RAU", "SERV" }));
        getContentPane().add(LineOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 191, 111, -1));

        DateOutOp.setDateFormatString("yyyy/MM/dd");
        DateOutOp.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        DateOutOp.setOpaque(false);
        getContentPane().add(DateOutOp, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 232, 171, -1));

        jLabel16.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Stock (Existencias):");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 274, -1, -1));

        TablaSalidasOperador.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        TablaSalidasOperador.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaSalidasOperador.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        TablaSalidasOperador.getTableHeader().setResizingAllowed(false);
        TablaSalidasOperador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TablaSalidasOperadorMousePressed(evt);
            }
        });
        TablaSalidasOperador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaSalidasOperadorKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(TablaSalidasOperador);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 69, 500, 268));

        jLabel14.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Fecha de Entrega:");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 232, -1, -1));

        LastPriceOut.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        LastPriceOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                LastPriceOutKeyTyped(evt);
            }
        });
        getContentPane().add(LastPriceOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 311, 111, -1));

        jLabel15.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("(AAAA-MM-DD)");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 232, -1, 20));

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Formato(Sin Comas) Ej:3000000.00 ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, -1, -1));

        jButton1.setText("Cerrar Sesión");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, -1, -1));

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
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 360));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void FilterOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilterOutButtonActionPerformed
        Filtrar();
        ColumnasAutoajustadas(TablaSalidasOperador, margin);
    }//GEN-LAST:event_FilterOutButtonActionPerformed

    private void StockOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StockOutButtonActionPerformed
        LlenarTablaSalidas();
        ColumnasAutoajustadas(TablaSalidasOperador, margin);
        Key.requestFocus();
    }//GEN-LAST:event_StockOutButtonActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            cc.close();
            System.exit(0);
        } catch (SQLException ex) {
            Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void UpdateOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateOutButtonActionPerformed
        try {
            if (DatosLlenos()) {
                int fila = TablaSalidasOperador.getSelectedRow();
                String dato = (String) TablaSalidasOperador.getValueAt(fila, 0);
                String SQL = "update almacen set clave=?,descrip=?,medida=?,line=?,"
                        + "f_ent=?,stock=?,ult_cost=? where clave=?";
                try (PreparedStatement p = cc.prepareStatement(SQL)) {
                    p.setString(1, Key.getText());
                    p.setString(2, Dsc.getText());
                    p.setString(3, Unit.getSelectedItem().toString());
                    p.setString(4, LineOut.getSelectedItem().toString());
                    p.setString(5, ((JTextField) DateOutOp.getDateEditor().getUiComponent()).getText());
                    p.setString(6, StockOut.getText());
                    p.setString(7, LastPriceOut.getText());
                    p.setString(8, dato);
                    int n = p.executeUpdate();
                    if (n > 0) {
                        LlenarTablaSalidas();
                        ColumnasAutoajustadas(TablaSalidasOperador, margin);
                        LimpiarDatosSalidas();
                        JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
                        Key.requestFocus();
                    }
                    p.close();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al actualizar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_UpdateOutButtonActionPerformed

    private void SearchKeyOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchKeyOutActionPerformed
        Buscar();
        ColumnasAutoajustadas(TablaSalidasOperador, margin);
    }//GEN-LAST:event_SearchKeyOutActionPerformed

    private void StockOutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StockOutKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_StockOutKeyTyped

    private void MinusOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinusOutButtonActionPerformed
        try {
            try {
                String buscar = Key.getText();
                int b = Integer.parseInt(StockOut.getText());
                int min;
                min = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad "
                        + "a restar del Stock:"));
                if (min >= 0) {
                    if (min < Integer.parseInt(StockOut.getText())) {
                        try {
                            if (DatosLlenos()) {
                                int d;
                                d = b - min;
                                JOptionPane.showMessageDialog(null, "Quedan " + d + " productos en existencia");
                                String SQL = "update almacen set clave=?,descrip=?,medida=?,line=?,"
                                        + "f_ent=?,stock=" + d + ",ult_cost=?  where clave='" + buscar + "'";
                                try (PreparedStatement p = cc.prepareStatement(SQL)) {
                                    p.setString(1, Key.getText());
                                    p.setString(2, Dsc.getText());
                                    p.setString(3, Unit.getSelectedItem().toString());
                                    p.setString(4, LineOut.getSelectedItem().toString());
                                    p.setString(5, ((JTextField) DateOutOp.getDateEditor().getUiComponent()).getText());
                                    p.setString(6, LastPriceOut.getText());
                                    int n = p.executeUpdate();
                                    if (n > 0) {
                                        LlenarTablaSalidas();
                                        ColumnasAutoajustadas(TablaSalidasOperador, margin);
                                        LimpiarDatosSalidas();
                                        JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
                                        Key.requestFocus();
                                    }
                                    p.close();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Llena todos los campos");
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "Hubo un error al reducir las existencias del Almacèn: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No puedes restar valores que excedan el limite actual", "Error de entrada de datos", 0);
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


    }//GEN-LAST:event_MinusOutButtonActionPerformed

    private void TablaSalidasOperadorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaSalidasOperadorMousePressed
        if (evt.getClickCount() == 1) {
            int fila = TablaSalidasOperador.getSelectedRow();
            try {
                String SQL = "SELECT * FROM almacen WHERE clave='" + TablaSalidasOperador.getValueAt(fila, 0) + "'";
                sent = cc.createStatement();
                try (ResultSet rs = sent.executeQuery(SQL)) {
                    rs.next();
                    Key.setText(rs.getString("clave"));
                    Dsc.setText(rs.getString("descrip"));
                    Unit.setSelectedItem(rs.getString("medida"));
                    LineOut.setSelectedItem(rs.getString("line"));
                    DateOutOp.setDate(rs.getTimestamp("f_ent"));
                    StockOut.setText(rs.getString("stock"));
                    LastPriceOut.setText(rs.getString("ult_cost"));
                    rs.close();
                    sent.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Hubo un error al seleccionar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_TablaSalidasOperadorMousePressed

    private void LastPriceOutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LastPriceOutKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car != '.')) {
            evt.consume();
        }
    }//GEN-LAST:event_LastPriceOutKeyTyped

    private void FondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_FondoMousePressed

    private void FondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_FondoMouseDragged

    private void TablaSalidasOperadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaSalidasOperadorKeyReleased
        if ((evt.getKeyCode() == 38) || (evt.getKeyCode() == 40) || (evt.getKeyCode() == 33) || (evt.getKeyCode() == 34)) {
            int fila = TablaSalidasOperador.getSelectedRow();
            try {
                String SQL = "SELECT * FROM almacen WHERE clave='" + TablaSalidasOperador.getValueAt(fila, 0) + "'";
                sent = cc.createStatement();
                try (ResultSet rs = sent.executeQuery(SQL)) {
                    rs.next();
                    Key.setText(rs.getString("clave"));
                    Dsc.setText(rs.getString("descrip"));
                    Unit.setSelectedItem(rs.getString("medida"));
                    LineOut.setSelectedItem(rs.getString("line"));
                    DateOutOp.setDate(rs.getTimestamp("f_ent"));
                    StockOut.setText(rs.getString("stock"));
                    LastPriceOut.setText(rs.getString("ult_cost"));
                    rs.close();
                    sent.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Hubo un error al seleccionar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_TablaSalidasOperadorKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            cc.close();
            dispose();
            Login log = new Login();
            log.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void KeyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeyKeyTyped
        char cTeclaPresionada = evt.getKeyChar();
        if (cTeclaPresionada == KeyEvent.VK_ENTER) {
            SearchKeyOut.doClick();
        }
    }//GEN-LAST:event_KeyKeyTyped

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OperadorSalidasAlmacen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OperadorSalidasAlmacen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateOutOp;
    private javax.swing.JTextField Dsc;
    private javax.swing.JButton FilterOutButton;
    private javax.swing.JLabel Fondo;
    public static javax.swing.JLabel ID_Usuario;
    private javax.swing.JTextField Key;
    private javax.swing.JTextField LastPriceOut;
    private javax.swing.JComboBox<String> LineOut;
    private javax.swing.JButton MinusOutButton;
    public javax.swing.JLabel Nombre_UsuarioAdmin;
    private javax.swing.JButton SearchKeyOut;
    private javax.swing.JTextField StockOut;
    private javax.swing.JButton StockOutButton;
    private javax.swing.JTable TablaSalidasOperador;
    private javax.swing.JComboBox<String> Unit;
    private javax.swing.JButton UpdateOutButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
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
