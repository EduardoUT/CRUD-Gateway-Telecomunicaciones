/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VentanasAdministradores;

import Conexion.ConexionBD;
import java.sql.*;
import javax.swing.*;
import Login.Login;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Frame.ICONIFIED;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public final class AdministradorPrincipal extends javax.swing.JFrame {

    private Statement sent = null;
    private Connection cc = null;
    private ResultSet rs = null;
    private PreparedStatement p = null;
    int xMouse;
    int yMouse;
    public String Pass;

    DefaultTableModel model1;

    public AdministradorPrincipal() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        LlenarTablaUsuarios();
        LlenarTablaAdmin();
        ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
        pk.setVisible(false);
        Confirm.setVisible(false);
        jLabel20.setVisible(false);
        pk1.setVisible(false);
        Confirm1.setVisible(false);
        jLabel21.setVisible(false);
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/Logo.png"));
        return retValue;

    }

    int vColIndex = 1;
    int margin = 2;

    public void ColumnasAutoajustadas(JTable TablaUsuarios, JTable TablaAdmin, int margin) {
        for (int c = 0; c < this.TablaUsuarios.getColumnCount(); c++) {
            packColumnTablaUsuarios(this.TablaUsuarios, c, 2);
        }
        for (int c = 0; c < this.TablaAdmin.getColumnCount(); c++) {
            packColumnTablaAdmin(this.TablaAdmin, c, 2);
        }
    }

    public void packColumnTablaUsuarios(JTable TablaUsuarios, int vColIndex, int margin) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) TablaUsuarios.getColumnModel();

        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = TablaUsuarios.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(TablaUsuarios, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < TablaUsuarios.getRowCount(); r++) {
            renderer = TablaUsuarios.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(TablaUsuarios, TablaUsuarios.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public void packColumnTablaAdmin(JTable TablaAdmin, int vColIndex, int margin) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) TablaAdmin.getColumnModel();
        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = TablaAdmin.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(TablaAdmin, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < TablaAdmin.getRowCount(); r++) {
            renderer = TablaAdmin.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(TablaAdmin, TablaAdmin.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public boolean DatosLlenosAdministrador() {
        return !nombre_ad.getText().equals("") && !ape_pat_ad.getText().equals("")
                && !ape_mat_ad.getText().equals("")
                && !nombread.getText().equals("") && !passad.getText().equals("");
    }

    public boolean DatosLlenosBuscadores() {
        return !BuscarUser.getText().equals("") || !BuscarName.getText().equals("");
    }

    public boolean DatosLlenosUser() {
        return !nombre_us.getText().equals("") && !ape_pat_us.getText().equals("")
                && !ape_mat_us.getText().equals("")
                && !nombreus.getText().equals("") && !passus.getText().equals("");
    }

    public void LimpiarDatosAdmin() {
        idad.setText("");
        nombre_ad.setText("");
        ape_pat_ad.setText("");
        ape_mat_ad.setText("");
        nombread.setText("");
        passad.setText("");
    }

    public void LimpiarDatosUser() {
        idus.setText("");
        nombre_us.setText("");
        ape_pat_us.setText("");
        ape_mat_us.setText("");
        nombreus.setText("");
        passus.setText("");
    }

    public void LlenarTablaUsuarios() {
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Nombre de Usuario", "Categoría Usuario"};
            String SQL = "SELECT id_usuario,nombre,ape_pat,ape_mat, nombre_usuario, cat_usuario FROM usuarios where cat_usuario!='Administrador Principal'";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[6];
            while (rs.next()) {
                fila[0] = rs.getString("id_usuario");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("ape_pat");
                fila[3] = rs.getString("ape_mat");
                fila[4] = rs.getString("nombre_usuario");
                fila[5] = rs.getString("cat_usuario");
                model1.addRow(fila);
                TablaUsuarios.setModel(model1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al llenar la tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void LlenarTablaAdmin() {
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Nombre de Usuario", "Categoría Usuario"};
            String SQL = "SELECT id_usuario,nombre,ape_pat,ape_mat, nombre_usuario, cat_usuario FROM usuarios where cat_usuario='Administrador Principal'";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[6];
            while (rs.next()) {
                fila[0] = rs.getString("id_usuario");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("ape_pat");
                fila[3] = rs.getString("ape_mat");
                fila[4] = rs.getString("nombre_usuario");
                fila[5] = rs.getString("cat_usuario");
                model1.addRow(fila);
            }
            TablaAdmin.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al llenar la tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarId() {
        try {
            cc = ConexionBD.getcon();
            String BuscarID = BuscarUser.getText();
            if (DatosLlenosBuscadores()) {
                String[] titulos = {"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Nombre de Usuario", "Categoría Usuario"};
                String SQL = "SELECT id_usuario,nombre,ape_pat,ape_mat,nombre_usuario,cat_usuario FROM usuarios WHERE id_usuario =" + BuscarID + "";
                model1 = new DefaultTableModel(null, titulos);
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                String[] fila = new String[6];
                while (rs.next()) {
                    fila[0] = rs.getString("id_usuario");
                    fila[1] = rs.getString("nombre");
                    fila[2] = rs.getString("ape_pat");
                    fila[3] = rs.getString("ape_mat");
                    fila[4] = rs.getString("nombre_usuario");
                    fila[5] = rs.getString("cat_usuario");
                    model1.addRow(fila);
                }
                if (fila[5].equals("Administrador Principal")) {
                    /**
                     * En esta condición if, para evitar la alteración de la
                     * información del AdministradorPrincipal, si la categoría
                     * es igual a "Administrador Principal" los siguientes
                     * componentes del formulario se ocultarán., de lo contrario
                     * permanecerán visibles.
                     */
                    GuardarUs.setVisible(false);
                    DeleteUs.setVisible(false);
                    UpdateUs.setVisible(false);
                    jCheckBox2.setVisible(false);
                    catus.setEnabled(false);
                } else {
                    GuardarUs.setVisible(true);
                    DeleteUs.setVisible(true);
                    UpdateUs.setVisible(true);
                    jCheckBox2.setVisible(true);
                    catus.setEnabled(true);
                }
                TablaUsuarios.setModel(model1);
            } else {
                JOptionPane.showMessageDialog(null, "Llena el campo de búsqueda");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarNombre() {
        try {
            cc = ConexionBD.getcon();
            String BuscarNombre = BuscarName.getText();
            if (DatosLlenosBuscadores()) {
                String[] titulos = {"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Nombre de Usuario", "Categoría Usuario"};
                String SQL = "SELECT id_usuario,nombre,ape_pat,ape_mat,nombre_usuario,cat_usuario FROM usuarios WHERE nombre_usuario like '%" + BuscarNombre + "%'";
                model1 = new DefaultTableModel(null, titulos);
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                String[] fila = new String[6];
                while (rs.next()) {
                    fila[0] = rs.getString("id_usuario");
                    fila[1] = rs.getString("nombre");
                    fila[2] = rs.getString("ape_pat");
                    fila[3] = rs.getString("ape_mat");
                    fila[4] = rs.getString("nombre_usuario");
                    fila[5] = rs.getString("cat_usuario");
                    model1.addRow(fila);
                }
                if (fila[5].equals("Administrador Principal")) {
                    GuardarUs.setVisible(false);
                    DeleteUs.setVisible(false);
                    UpdateUs.setVisible(false);
                    jCheckBox1.setVisible(false);
                    catus.setEnabled(false);
                } else {
                    GuardarUs.setVisible(true);
                    DeleteUs.setVisible(true);
                    UpdateUs.setVisible(true);
                    jCheckBox1.setVisible(true);
                    catus.setEnabled(true);
                }
                TablaUsuarios.setModel(model1);
            } else {
                JOptionPane.showMessageDialog(null, "Llena el campo de búsqueda");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al buscar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void Filtrar() {
        String filtrar = String.valueOf(Filtro.getSelectedItem());
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Nombre de Usuario", "Categoría Usuario"};
            String SQL = "SELECT * FROM usuarios WHERE cat_usuario='" + filtrar + "'";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[6];
            while (rs.next()) {
                fila[0] = rs.getString("id_usuario");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("ape_pat");
                fila[3] = rs.getString("ape_mat");
                fila[4] = rs.getString("nombre_usuario");
                fila[5] = rs.getString("cat_usuario");
                model1.addRow(fila);
            }
            TablaUsuarios.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al fltrar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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

        jTabbedPane2 = new javax.swing.JTabbedPane();
        Admin = new javax.swing.JPanel();
        jPanel4 = new FondoPanelTitulo();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nombread = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        passad = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        idad = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        catad = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaAdmin = new javax.swing.JTable();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        nombre_ad = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        ape_pat_ad = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        ape_mat_ad = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        pk1 = new javax.swing.JPasswordField();
        Confirm1 = new javax.swing.JButton();
        Usuarios = new javax.swing.JPanel();
        jPanel3 = new FondoPanelTitulo();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        idus = new javax.swing.JTextField();
        nombreus = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        passus = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        catus = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        BuscarUs = new javax.swing.JButton();
        BuscarUser = new javax.swing.JTextField();
        GuardarUs = new javax.swing.JButton();
        NuevoUs = new javax.swing.JButton();
        UpdateUs = new javax.swing.JButton();
        DeleteUs = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaUsuarios = new javax.swing.JTable();
        jCheckBox2 = new javax.swing.JCheckBox();
        Filtro = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        BuscarName = new javax.swing.JTextField();
        BuscarN = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        nombre_us = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        ape_pat_us = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        ape_mat_us = new javax.swing.JTextField();
        pk = new javax.swing.JPasswordField();
        Confirm = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        CerrarSesion = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        Nombre_UsuarioAdmin = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        ID_Usuario = new javax.swing.JLabel();
        Fondo = new FondoPanelPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        Admin.setBackground(new java.awt.Color(0, 0, 0));
        Admin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(38, 101, 233));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(0, 102, 102));
        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Gestión de Administrador");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 60));

        Admin.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, -1));

        jLabel7.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nombre de Usuario:");
        Admin.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, -1, -1));

        nombread.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        nombread.setToolTipText("Nombre del Usuario");
        Admin.add(nombread, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 245, -1));

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Contraseña:");
        Admin.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, -1, -1));

        passad.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        passad.setToolTipText("Contraseña del Usuario");
        Admin.add(passad, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, 245, -1));

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ID Administrador:");
        Admin.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        idad.setEditable(false);
        idad.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        idad.setToolTipText("ID del Usuario");
        Admin.add(idad, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 245, -1));

        jButton1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton1.setText("Actualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Admin.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 273, -1, -1));

        jLabel9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Categoría de Usuario:");
        Admin.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, -1, -1));

        catad.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        catad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador Principal" }));
        catad.setToolTipText("Categoría del Usuario");
        catad.setEnabled(false);
        Admin.add(catad, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 245, -1));

        TablaAdmin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        TablaAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TablaAdmin.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        TablaAdmin.getTableHeader().setResizingAllowed(false);
        TablaAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TablaAdminMousePressed(evt);
            }
        });
        TablaAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaAdminKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(TablaAdmin);

        Admin.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 650, 91));

        jCheckBox1.setBorder(null);
        jCheckBox1.setContentAreaFilled(false);
        jCheckBox1.setFocusPainted(false);
        jCheckBox1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/oj.png"))); // NOI18N
        jCheckBox1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/o.png"))); // NOI18N
        jCheckBox1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lu.png"))); // NOI18N
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        Admin.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, -1, 26));

        jButton2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        Admin.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 273, -1, -1));

        jButton3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        Admin.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(438, 273, -1, -1));

        jButton4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton4.setText("Nuevo");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        Admin.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 273, -1, -1));

        jLabel14.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nombre:");
        Admin.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        nombre_ad.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        nombre_ad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombre_adKeyTyped(evt);
            }
        });
        Admin.add(nombre_ad, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 245, -1));

        jLabel15.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Apellido Paterno:");
        Admin.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        ape_pat_ad.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ape_pat_ad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ape_pat_adKeyTyped(evt);
            }
        });
        Admin.add(ape_pat_ad, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 245, -1));

        jLabel16.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Apellido Materno:");
        Admin.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        ape_mat_ad.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ape_mat_ad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ape_mat_adKeyTyped(evt);
            }
        });
        Admin.add(ape_mat_ad, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 245, -1));

        jLabel21.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Ingrese la contraseña del Administrador:");
        Admin.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, -1, -1));

        pk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pk1ActionPerformed(evt);
            }
        });
        Admin.add(pk1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 240, 210, -1));

        Confirm1.setText("Confirmar");
        Confirm1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Confirm1ActionPerformed(evt);
            }
        });
        Admin.add(Confirm1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, -1, -1));

        jTabbedPane2.addTab("Administrador", Admin);

        Usuarios.setBackground(new java.awt.Color(0, 0, 0));
        Usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsuariosMouseClicked(evt);
            }
        });
        Usuarios.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(38, 101, 233));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Gestión de Usuarios");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 60));

        Usuarios.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID Usuario:");
        Usuarios.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        idus.setEditable(false);
        idus.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        idus.setToolTipText("ID del Usuario");
        Usuarios.add(idus, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 245, -1));

        nombreus.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        nombreus.setToolTipText("Nombre del Usuario");
        Usuarios.add(nombreus, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 245, -1));

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nombre de Usuario:");
        Usuarios.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, -1, -1));

        passus.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        passus.setToolTipText("Contraseña del Usuario");
        Usuarios.add(passus, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, 245, -1));

        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Contraseña:");
        Usuarios.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, -1, -1));

        catus.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        catus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador Facturacion", "Administrador Cierre", "Administrador Entradas Almacen", "Administrador Salidas Almacen", "Operador Entradas Almacen", "Operador Salidas Almacen" }));
        catus.setToolTipText("Categoría del Usuario");
        Usuarios.add(catus, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 245, -1));

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Categoría de Usuario:");
        Usuarios.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, -1, -1));

        BuscarUs.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        BuscarUs.setText("Buscar");
        BuscarUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarUsActionPerformed(evt);
            }
        });
        Usuarios.add(BuscarUs, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 334, -1, -1));

        BuscarUser.setToolTipText("Puede buscar un usuario por medio de su \"ID de Usuario\". ");
        BuscarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarUserActionPerformed(evt);
            }
        });
        BuscarUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BuscarUserKeyTyped(evt);
            }
        });
        Usuarios.add(BuscarUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 335, 215, -1));

        GuardarUs.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        GuardarUs.setText("Guardar");
        GuardarUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarUsActionPerformed(evt);
            }
        });
        Usuarios.add(GuardarUs, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 273, -1, -1));

        NuevoUs.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        NuevoUs.setText("Nuevo");
        NuevoUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoUsActionPerformed(evt);
            }
        });
        Usuarios.add(NuevoUs, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 273, -1, -1));

        UpdateUs.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        UpdateUs.setText("Actualizar");
        UpdateUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateUsActionPerformed(evt);
            }
        });
        Usuarios.add(UpdateUs, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 273, -1, -1));

        DeleteUs.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        DeleteUs.setText("Eliminar");
        DeleteUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteUsActionPerformed(evt);
            }
        });
        Usuarios.add(DeleteUs, new org.netbeans.lib.awtextra.AbsoluteConstraints(438, 273, -1, -1));

        TablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TablaUsuarios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        TablaUsuarios.getTableHeader().setResizingAllowed(false);
        TablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TablaUsuariosMousePressed(evt);
            }
        });
        TablaUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaUsuariosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(TablaUsuarios);

        Usuarios.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 650, 91));

        jCheckBox2.setToolTipText("Use esta opción si el usuario olvidó su contraseña.");
        jCheckBox2.setBorder(null);
        jCheckBox2.setContentAreaFilled(false);
        jCheckBox2.setFocusPainted(false);
        jCheckBox2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/oj.png"))); // NOI18N
        jCheckBox2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/o.png"))); // NOI18N
        jCheckBox2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lu.png"))); // NOI18N
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        Usuarios.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, -1, 26));

        Filtro.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Filtro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador Facturacion", "Administrador Cierre", "Administrador Ventas", "Administrador Almacen", "Operador Facturacion", "Operador Cierre", "Operador Ventas", "Operador Almacen" }));
        Filtro.setToolTipText("Puede seleccionar la Categoría a la que Pertenece.");
        Usuarios.add(Filtro, new org.netbeans.lib.awtextra.AbsoluteConstraints(363, 335, 193, -1));

        jButton5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton5.setText("Mostrar Todo");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        Usuarios.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(449, 388, 107, -1));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Buscar por ID:");
        Usuarios.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 314, -1, -1));

        jButton6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton6.setText("Buscar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        Usuarios.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(574, 334, -1, -1));

        BuscarName.setToolTipText("Puede buscar un usuario por medio de su \"Nombre de Usuario\". ");
        BuscarName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarNameActionPerformed(evt);
            }
        });
        BuscarName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BuscarNameKeyTyped(evt);
            }
        });
        Usuarios.add(BuscarName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 389, 215, -1));

        BuscarN.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        BuscarN.setText("Buscar");
        BuscarN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarNActionPerformed(evt);
            }
        });
        Usuarios.add(BuscarN, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 388, -1, -1));

        jLabel13.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Buscar por Categoría:");
        Usuarios.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(363, 314, -1, -1));

        jLabel12.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Buscar por Nombre deUsuario:");
        Usuarios.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 368, -1, -1));

        jLabel17.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Nombre:");
        Usuarios.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        nombre_us.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        nombre_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombre_usKeyTyped(evt);
            }
        });
        Usuarios.add(nombre_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 245, -1));

        jLabel18.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Apellido Paterno:");
        Usuarios.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        ape_pat_us.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ape_pat_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ape_pat_usKeyTyped(evt);
            }
        });
        Usuarios.add(ape_pat_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 245, -1));

        jLabel19.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Apellido Materno:");
        Usuarios.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        ape_mat_us.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ape_mat_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ape_mat_usKeyTyped(evt);
            }
        });
        Usuarios.add(ape_mat_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 245, -1));

        pk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pkActionPerformed(evt);
            }
        });
        Usuarios.add(pk, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 240, 210, -1));

        Confirm.setText("Confirmar");
        Confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmActionPerformed(evt);
            }
        });
        Usuarios.add(Confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, -1, -1));

        jLabel20.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Ingrese la contraseña del Administrador:");
        Usuarios.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, -1, -1));

        jTabbedPane2.addTab("Usuarios", Usuarios);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 680, 580));

        CerrarSesion.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        CerrarSesion.setText("Cerrar Sesión");
        CerrarSesion.setToolTipText("Cerrar Sesión: Esto lo Re-digirá al Login");
        CerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarSesionActionPerformed(evt);
            }
        });
        getContentPane().add(CerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton7.setText("-");
        jButton7.setToolTipText("Minimizar");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

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
        getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, -1, -1));

        Nombre_UsuarioAdmin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Nombre_UsuarioAdmin.setForeground(new java.awt.Color(255, 255, 255));
        Nombre_UsuarioAdmin.setText("Nombre");
        getContentPane().add(Nombre_UsuarioAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        jLabel33.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("ID:");
        getContentPane().add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, -1));

        jLabel28.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Bienvenido(a):");
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        ID_Usuario.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ID_Usuario.setForeground(new java.awt.Color(255, 255, 255));
        ID_Usuario.setText("ID");
        getContentPane().add(ID_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

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
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 630));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BuscarUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarUsActionPerformed
        BuscarId();
        ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
        BuscarUser.setText("");
        BuscarUser.requestFocus();
    }//GEN-LAST:event_BuscarUsActionPerformed

    private void BuscarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarUserActionPerformed
        BuscarUs.doClick();
        ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
    }//GEN-LAST:event_BuscarUserActionPerformed

    private void BuscarUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarUserKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_BuscarUserKeyTyped

    private void GuardarUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarUsActionPerformed
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosUser()) {
                String SQL = "insert into usuarios (nombre,ape_pat,ape_mat,nombre_usuario,password,cat_usuario)"
                        + " values(?,?,?,?,?,?)";
                p = cc.prepareStatement(SQL);
                p.setString(1, nombre_us.getText());
                p.setString(2, ape_pat_us.getText());
                p.setString(3, ape_mat_us.getText());
                p.setString(4, nombreus.getText());
                p.setString(5, String.valueOf(passus.getPassword()));
                p.setString(6, String.valueOf(catus.getSelectedItem()));
                int n = p.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos Guardados");
                    LlenarTablaUsuarios();
                    ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
                    LimpiarDatosUser();
                    idus.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_GuardarUsActionPerformed

    private void NuevoUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoUsActionPerformed
        LimpiarDatosUser();
        nombreus.requestFocus();
    }//GEN-LAST:event_NuevoUsActionPerformed

    private void UpdateUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateUsActionPerformed
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosUser()) {
                int fila = TablaUsuarios.getSelectedRow();
                String dato = (String) TablaUsuarios.getValueAt(fila, 0);
                String SQL = "update usuarios set id_usuario=?,nombre=?,ape_pat=?,ape_mat=?,nombre_usuario=?,password=?,cat_usuario=? where id_usuario=?";
                p = cc.prepareStatement(SQL);
                p.setString(1, idus.getText());
                p.setString(2, nombre_us.getText());
                p.setString(3, ape_pat_us.getText());
                p.setString(4, ape_mat_us.getText());
                p.setString(5, nombreus.getText());
                p.setString(6, String.valueOf(passus.getPassword()));
                p.setString(7, String.valueOf(catus.getSelectedItem()));
                p.setString(8, dato);
                int n = p.executeUpdate();
                if (n > 0) {
                    LlenarTablaUsuarios();
                    ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
                    LimpiarDatosUser();
                    JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
                    idus.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la actualización de los datos del Usuario: " + e.getMessage());
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_UpdateUsActionPerformed

    private void DeleteUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteUsActionPerformed
        int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar este usuario?"
                + "\n**Selecciona con el Mouse**"
                + "\n Se perderá la información de forma definitiva.",
                "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        if (JOptionPane.OK_OPTION == confirmar) {
            try {
                cc = ConexionBD.getcon();
                if (DatosLlenosUser()) {
                    int fila = TablaUsuarios.getSelectedRow();
                    String SQL = "delete from usuarios where id_usuario='" + TablaUsuarios.getValueAt(fila, 0) + "'";
                    sent = cc.createStatement();
                    int n = sent.executeUpdate(SQL);
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "Datos Eliminados Correctamente");
                        LimpiarDatosUser();
                        ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
                        LlenarTablaUsuarios();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Llena todos los campos");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar los datos: Este usuario posee asignaciones"
                        + "actualmente, pida que se reasignen los proyectos a otro usuario para poder eliminarlo." + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else if (JOptionPane.OK_OPTION != confirmar) {
            JOptionPane.showMessageDialog(null, "Datos no Eliminados");
        }
    }//GEN-LAST:event_DeleteUsActionPerformed

    private void TablaUsuariosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaUsuariosMousePressed
        if (evt.getClickCount() == 1) {
            int fila = TablaUsuarios.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "SELECT * FROM usuarios where id_usuario =" + TablaUsuarios.getValueAt(fila, 0) + "";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                idus.setText(rs.getString("id_usuario"));
                nombre_us.setText(rs.getString("nombre"));
                ape_pat_us.setText(rs.getString("ape_pat"));
                ape_mat_us.setText(rs.getString("ape_mat"));
                nombreus.setText(rs.getString("nombre_usuario"));
                passus.setText(rs.getString("password"));
                catus.setSelectedItem(rs.getString("cat_usuario"));
                passus.setEchoChar('*');
                pk.setVisible(false);
                Confirm.setVisible(false);
                jLabel20.setVisible(false);
                pk.setText("");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al traer los datos del Usuario:" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_TablaUsuariosMousePressed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        if (jCheckBox2.isSelected()) {
            pk.setVisible(true);
            Confirm.setVisible(true);
            jLabel20.setVisible(true);
        } else {
            passus.setEchoChar('*');
            pk.setVisible(false);
            Confirm.setVisible(false);
            jLabel20.setVisible(false);
            pk.setText("");
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        LlenarTablaUsuarios();
        ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
        GuardarUs.setVisible(true);
        DeleteUs.setVisible(true);
        UpdateUs.setVisible(true);
        jCheckBox2.setVisible(true);
        nombreus.requestFocus();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Filtrar();
        ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void BuscarNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarNameActionPerformed
        BuscarN.doClick();
    }//GEN-LAST:event_BuscarNameActionPerformed

    private void BuscarNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarNameKeyTyped
        /**
         * En esta función el usuario solo podrá ingresar letras correspondiendo
         * al campo de búsqueda por Nombre de Usuario.
         */
        char car = evt.getKeyChar();
        if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z') && (car < 'á' || car > 'ú')) {
            evt.consume();
        }
    }//GEN-LAST:event_BuscarNameKeyTyped

    private void BuscarNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarNActionPerformed
        BuscarNombre();
        ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
        BuscarName.setText("");
        BuscarName.requestFocus();
    }//GEN-LAST:event_BuscarNActionPerformed

    private void UsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsuariosMouseClicked

    }//GEN-LAST:event_UsuariosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosAdministrador()) {
                int fila = TablaAdmin.getSelectedRow();
                String dato = (String) TablaAdmin.getValueAt(fila, 0);
                String SQL = "update usuarios set id_usuario=?,nombre=?,ape_pat=?,ape_mat=?,nombre_usuario=?,password=?,cat_usuario=? where id_usuario=?";
                p = cc.prepareStatement(SQL);
                p.setString(1, idad.getText());
                p.setString(2, nombre_ad.getText());
                p.setString(3, ape_pat_ad.getText());
                p.setString(4, ape_mat_ad.getText());
                p.setString(5, nombread.getText());
                p.setString(6, String.valueOf(passad.getPassword()));
                p.setString(7, String.valueOf(catad.getSelectedItem()));
                p.setString(8, dato);
                int n = p.executeUpdate();
                if (n > 0) {
                    LlenarTablaAdmin();
                    ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
                    LimpiarDatosAdmin();
                    JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
                    idad.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la actualización de los datos: " + e.getMessage());
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void TablaAdminMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaAdminMousePressed
        if (evt.getClickCount() == 1) {
            int fila = TablaAdmin.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "SELECT * FROM usuarios WHERE id_usuario=" + TablaAdmin.getValueAt(fila, 0) + "";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                idad.setText(rs.getString("id_usuario"));
                nombre_ad.setText(rs.getString("nombre"));
                ape_pat_ad.setText(rs.getString("ape_pat"));
                ape_mat_ad.setText(rs.getString("ape_mat"));
                nombread.setText(rs.getString("nombre_usuario"));
                passad.setText(rs.getString("password"));
                passad.setEchoChar('*');
                pk1.setVisible(false);
                Confirm1.setVisible(false);
                jLabel21.setVisible(false);
                pk1.setText("");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al traer los datos del Administrador:" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_TablaAdminMousePressed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        /*
         * En el caso del icono de Ver Contraseña, al dar clic en el se ejecuta
         * este bloque de código a fin de dar un nivel de seguridad mayor.
         * Dentro de este bloque se pide comprobar los usuarios que solo sean
         * Administradores Principales de esta manera si desea ver su contraseña
         * solo el usuario que está dentro de la sesión podrá acceder a ella, si
         * un usuario ajeno intenta ver este campo el sistema restringirá dicha
         * acción.
         */
        if (jCheckBox1.isSelected()) {
            pk1.setVisible(true);
            Confirm1.setVisible(true);
            jLabel21.setVisible(true);
        } else {
            passad.setEchoChar('*');
            pk1.setVisible(false);
            Confirm1.setVisible(false);
            jLabel21.setVisible(false);
            pk1.setText("");
        }

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosAdministrador()) {
                String SQL = "insert into usuarios (nombre,ape_pat,ape_mat,nombre_usuario,password,cat_usuario)"
                        + " values(?,?,?,?,?,?)";
                p = cc.prepareStatement(SQL);
                p.setString(1, nombre_ad.getText());
                p.setString(2, ape_pat_ad.getText());
                p.setString(3, ape_mat_ad.getText());
                p.setString(4, nombread.getText());
                p.setString(5, String.valueOf(passad.getPassword()));
                p.setString(6, String.valueOf(catad.getSelectedItem()));
                int n = p.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos Guardados");
                    LlenarTablaAdmin();
                    ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
                    LimpiarDatosAdmin();
                    idad.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar este usuario?"
                + "\n**Selecciona con el Mouse**"
                + "\n Se perderá la información de forma definitiva.",
                "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        if (JOptionPane.OK_OPTION == confirmar) {
            try {
                cc = ConexionBD.getcon();
                if (DatosLlenosAdministrador()) {
                    int fila = TablaAdmin.getSelectedRow();
                    String SQL = "delete from usuarios where id_usuario='" + TablaAdmin.getValueAt(fila, 0) + "'";
                    sent = cc.createStatement();
                    int n = sent.executeUpdate(SQL);
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "Datos Eliminados Correctamente");
                        LimpiarDatosAdmin();
                        LlenarTablaAdmin();
                        ColumnasAutoajustadas(TablaUsuarios, TablaAdmin, margin);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Llena todos los campos");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar los datos: Este usuario posee asignaciones"
                        + "actualmente, pida que se reasignen los proyectos a otro usuario para poder eliminarlo." + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else if (JOptionPane.OK_OPTION != confirmar) {
            JOptionPane.showMessageDialog(null, "Datos no Eliminados");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        LimpiarDatosAdmin();
        nombread.requestFocus();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void CerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarSesionActionPerformed
        dispose();
        Login log = new Login();
        log.setVisible(true);
    }//GEN-LAST:event_CerrarSesionActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        System.exit(0);

    }//GEN-LAST:event_jButton8ActionPerformed

    private void FondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_FondoMousePressed

    private void FondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_FondoMouseDragged

    private void ConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmActionPerformed
        if (!pk.getPassword().equals("")) {
            String idusuario;
            String passwordad;
            String ID;
            ID = String.valueOf(ID_Usuario.getText());
            try {
                cc = ConexionBD.getcon();
                String SQL = "select * from usuarios where id_usuario=" + ID + " && cat_usuario = 'Administrador Principal'";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                idusuario = rs.getString("id_usuario");
                passwordad = rs.getString("password");
                Pass = String.valueOf(pk.getPassword());
                if (passwordad.equals(Pass) && idusuario.equals(ID)) {
                    passus.setEchoChar((char) 0);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al mostrar la contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Para ver la contraseña seleccione sus datos en la tabla." + e.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, llenar el campo Contraseña");
        }
    }//GEN-LAST:event_ConfirmActionPerformed

    private void pkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pkActionPerformed
        Confirm.doClick();
    }//GEN-LAST:event_pkActionPerformed

    private void pk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pk1ActionPerformed
        Confirm1.doClick();
    }//GEN-LAST:event_pk1ActionPerformed

    private void Confirm1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Confirm1ActionPerformed
        if (!pk1.getPassword().equals("")) {
            String passwordusuario;
            String idusuario;
            try {
                cc = ConexionBD.getcon();
                String SQL = "select * from usuarios where id_usuario=" + idad.getText() + " && password='" + passad.getText() + "'";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                idusuario = rs.getString("id_usuario");
                passwordusuario = rs.getString("password");
                Pass = String.valueOf(pk1.getPassword());
                if (passwordusuario.equals(Pass) && idusuario.equals(idad.getText())) {
                    passad.setEchoChar((char) 0);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al mostrar la contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Para ver la contraseña seleccione sus datos en la tabla.");
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, llena el campo Contraseña.");
        }
    }//GEN-LAST:event_Confirm1ActionPerformed

    private void nombre_usKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombre_usKeyTyped
        char car = evt.getKeyChar();
        if ((car < 'A' || car > 'Z') && (car < 'Á' || car > 'Ú') && (car < 'a' || car > 'z') && (car < 'á' || car > 'ú')) {
            evt.consume();
        }
    }//GEN-LAST:event_nombre_usKeyTyped

    private void ape_pat_usKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ape_pat_usKeyTyped
        char car = evt.getKeyChar();
        if ((car < 'A' || car > 'Z') && (car < 'Á' || car > 'Ú') && (car < 'a' || car > 'z') && (car < 'á' || car > 'ú')) {
            evt.consume();
        }
    }//GEN-LAST:event_ape_pat_usKeyTyped

    private void ape_mat_usKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ape_mat_usKeyTyped
        char car = evt.getKeyChar();
        if ((car < 'A' || car > 'Z') && (car < 'Á' || car > 'Ú') && (car < 'a' || car > 'z') && (car < 'á' || car > 'ú')) {
            evt.consume();
        }
    }//GEN-LAST:event_ape_mat_usKeyTyped

    private void nombre_adKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombre_adKeyTyped
        char car = evt.getKeyChar();
        if ((car < 'A' || car > 'Z') && (car < 'Á' || car > 'Ú') && (car < 'a' || car > 'z') && (car < 'á' || car > 'ú')) {
            evt.consume();
        }
    }//GEN-LAST:event_nombre_adKeyTyped

    private void ape_pat_adKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ape_pat_adKeyTyped
        char car = evt.getKeyChar();
        if ((car < 'A' || car > 'Z') && (car < 'Á' || car > 'Ú') && (car < 'a' || car > 'z') && (car < 'á' || car > 'ú')) {
            evt.consume();
        }
    }//GEN-LAST:event_ape_pat_adKeyTyped

    private void ape_mat_adKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ape_mat_adKeyTyped
        char car = evt.getKeyChar();
        if ((car < 'A' || car > 'Z') && (car < 'Á' || car > 'Ú') && (car < 'a' || car > 'z') && (car < 'á' || car > 'ú')) {
            evt.consume();
        }
    }//GEN-LAST:event_ape_mat_adKeyTyped

    private void TablaAdminKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaAdminKeyReleased
        if ((evt.getKeyCode() == 38) || (evt.getKeyCode() == 40) || (evt.getKeyCode() == 33) || (evt.getKeyCode() == 34)) {
            int fila = TablaAdmin.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "SELECT * FROM usuarios WHERE id_usuario=" + TablaAdmin.getValueAt(fila, 0) + "";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                idad.setText(rs.getString("id_usuario"));
                nombre_ad.setText(rs.getString("nombre"));
                ape_pat_ad.setText(rs.getString("ape_pat"));
                ape_mat_ad.setText(rs.getString("ape_mat"));
                nombread.setText(rs.getString("nombre_usuario"));
                passad.setText(rs.getString("password"));
                passad.setEchoChar('*');
                pk1.setVisible(false);
                Confirm1.setVisible(false);
                jLabel21.setVisible(false);
                pk1.setText("");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al traer los datos del Administrador:" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_TablaAdminKeyReleased

    private void TablaUsuariosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaUsuariosKeyReleased
        if ((evt.getKeyCode() == 38) || (evt.getKeyCode() == 40) || (evt.getKeyCode() == 33) || (evt.getKeyCode() == 34)) {
            int fila = TablaUsuarios.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "SELECT * FROM usuarios where id_usuario =" + TablaUsuarios.getValueAt(fila, 0) + "";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                idus.setText(rs.getString("id_usuario"));
                nombre_us.setText(rs.getString("nombre"));
                ape_pat_us.setText(rs.getString("ape_pat"));
                ape_mat_us.setText(rs.getString("ape_mat"));
                nombreus.setText(rs.getString("nombre_usuario"));
                passus.setText(rs.getString("password"));
                catus.setSelectedItem(rs.getString("cat_usuario"));
                passus.setEchoChar('*');
                pk.setVisible(false);
                Confirm.setVisible(false);
                jLabel20.setVisible(false);
                pk.setText("");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al traer los datos del Usuario:" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_TablaUsuariosKeyReleased

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
            java.util.logging.Logger.getLogger(VentanasAdministradores.AdministradorPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanasAdministradores.AdministradorPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Admin;
    private javax.swing.JButton BuscarN;
    private javax.swing.JTextField BuscarName;
    private javax.swing.JButton BuscarUs;
    private javax.swing.JTextField BuscarUser;
    private javax.swing.JButton CerrarSesion;
    private javax.swing.JButton Confirm;
    private javax.swing.JButton Confirm1;
    private javax.swing.JButton DeleteUs;
    private javax.swing.JComboBox<String> Filtro;
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton GuardarUs;
    public static javax.swing.JLabel ID_Usuario;
    public javax.swing.JLabel Nombre_UsuarioAdmin;
    private javax.swing.JButton NuevoUs;
    private javax.swing.JTable TablaAdmin;
    private javax.swing.JTable TablaUsuarios;
    private javax.swing.JButton UpdateUs;
    private javax.swing.JPanel Usuarios;
    private javax.swing.JTextField ape_mat_ad;
    private javax.swing.JTextField ape_mat_us;
    private javax.swing.JTextField ape_pat_ad;
    private javax.swing.JTextField ape_pat_us;
    public javax.swing.JComboBox<String> catad;
    private javax.swing.JComboBox<String> catus;
    public javax.swing.JTextField idad;
    public javax.swing.JTextField idus;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField nombre_ad;
    private javax.swing.JTextField nombre_us;
    public javax.swing.JTextField nombread;
    private javax.swing.JTextField nombreus;
    public javax.swing.JPasswordField passad;
    public javax.swing.JPasswordField passus;
    private javax.swing.JPasswordField pk;
    private javax.swing.JPasswordField pk1;
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

    class FondoPaneles extends JPanel {

        private Image imagen;

        @Override
        public void paint(Graphics g) {
            imagen = new ImageIcon(getClass().getResource("/Imagenes/Deep Space.jpg")).getImage();
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
