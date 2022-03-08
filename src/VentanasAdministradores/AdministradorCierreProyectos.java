/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VentanasAdministradores;

import Conexion.ConexionBD;
import Login.Login;
import java.awt.*;
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
 * @author EDOMEX
 */
public final class AdministradorCierreProyectos extends javax.swing.JFrame {

    /**
     * Creates new form formulario
     */
    private Statement sent = null;
    private Connection cc = null;
    private ResultSet rs = null;
    private PreparedStatement p = null;
    int xMouse;
    int yMouse;
    DefaultTableModel model1;

    public AdministradorCierreProyectos() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        LlenarTabla();
        ColumnasAutoajustadas(jTable1, margin);
        id.requestFocus();
        jLabel35.setVisible(false);
        jLabel36.setVisible(false);
        jLabel37.setVisible(false);
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/Logo.png"));
        return retValue;

    }

    /**
     * El bloque DatosLlenosFacturacion o DatosLlenos en general funciona con la
     * finalidad de controlar las restricciones de llenado de los formularios en
     * todas las ventanas comprueba si el contenido de los JTextField es
     * Diferente de vacio "!=", es decir si los campos estan completamente
     * llenos, proceder con la función a ejecutar, ya sea Guardar, Eliminar o
     * Actualizar un registro, más adelante se observa su uso dentro de las
     * funciones que ejecutan dichos botones.
     *
     * @return
     */
    int vColIndex = 1;
    int margin = 2;

    public void ColumnasAutoajustadas(JTable jTable1, int margin) {
        for (int c = 0; c < jTable1.getColumnCount(); c++) {
            packColumnTablaCierre(jTable1, c, 2);
        }
    }

    public void packColumnTablaCierre(JTable jTable1, int vColIndex, int margin) {
        //model1 = (DefaultTableModel) jTable1.getModel();
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) jTable1.getColumnModel();

        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = jTable1.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(jTable1, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < jTable1.getRowCount(); r++) {
            renderer = jTable1.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(jTable1, jTable1.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public boolean DatosLlenosCierre() {
        return !stat_cierre.getText().equals("");
    }

    /**
     * El siguiente bloque "LimpiarDatos()" de igual manera ha sido utilizado en
     * todos los formularios a fin de poder ser utilizado una vez se haya
     * ejecutao las funciones de Guardar, Actualizar y Eliminar, este evento
     * permite que los registros en el formulario vuelvan a su estado inciail
     * vacio.
     */
    public void LimpiarDatos() {
        id.setText("");
        pjcode.setText("");
        pjname.setText("");
        customer.setText("");
        postatus.setText("");
        pon.setText("");
        poline.setText("");
        shipment.setText("");
        sitecode.setText("");
        sitename.setText("");
        itemcode.setText("");
        itemdsc.setText("");
        requestedqty.setText("");
        dueqty.setText("");
        billedqty.setText("");
        unitprice.setText("");
        amount.setText("");
        unit.setText("");
        payment.setText("");
        category.setText("");
        bidding.setText("");
        pdate.setText("");
        numordenc.setText("");
        contratista.setText("");
        ocompradt.setText("");
        importe.setText("");
        total.setText("");
        stat.setText("");
        stat_cierre.setText("");
    }

    /**
     * Si bien en inicio de este código se observa delante de initcomponents();
     * la función LlenarTabla(), esto nos permitirá hacer el llenado de la misma
     * desde el inicio de la ejecución de este jFrame. En este evento podemos
     * observar que se realiza una consulta SQL donde se obtienen todos los
     * datos de la tabla, además se construye por medio de un arreglo de tipo
     * String los encabezados que se visualizarán en la tabla, la finalidad
     * fundamental de esta función es de poder visualizar todos los registros de
     * la base de datos en un jTable, pero de esta función dependerán otras más
     * adelante.
     */
    public void LlenarTabla() {
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID", "Project Code", "Project Name", "Customer", "PO Status", "PO NO", "PO Line NO",
                "Shipment NO", "Site Code", "Site Name", "Item Code",
                "Item Description", "Requested Qty", "Due Qty", "Billed Quantity",
                "Unit Price", "Line Amount", "Unit", "Payment Terms", "Category",
                "Bidding Area", "Publish Date", "Status Cierre", "ID_Usuario", "Nombre", "Apellido Paterno", "Apellido Materno",
                "Orden de Compra DT", "Importe", "Total", "Status Facturación"};
            String SQL = "select facturacion.id,facturacion.project_code,facturacion.project_name,facturacion.customer,facturacion.PO_status,facturacion.PO_NO,\n"
                    + "facturacion.PO_Line_NO,facturacion.shipment_NO,facturacion.site_code,facturacion.site_name,facturacion.item_code,facturacion.item_desc,\n"
                    + "facturacion.requested_qty,facturacion.due_qty,facturacion.billed_qty,facturacion.unit_price,facturacion.line_amount,facturacion.unit,\n"
                    + "facturacion.payment_terms,facturacion.category,facturacion.bidding_area,facturacion.publish_date,facturacion.status_cierre,\n"
                    + "asignaciones.id_usuario,usuarios.nombre,usuarios.ape_pat,usuarios.ape_mat,asignaciones.orden_compra_dt,asignaciones.importe,\n"
                    + "asignaciones.total_pagar,asignaciones.status_facturacion \n"
                    + "from facturacion, asignaciones, usuarios\n"
                    + "where asignaciones.id_usuario =usuarios.id_usuario && facturacion.id = asignaciones.id";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[31];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("project_code");
                fila[2] = rs.getString("project_name");
                fila[3] = rs.getString("customer");
                fila[4] = rs.getString("PO_status");
                fila[5] = rs.getString("PO_NO");
                fila[6] = rs.getString("PO_Line_NO");
                fila[7] = rs.getString("shipment_NO");
                fila[8] = rs.getString("site_code");
                fila[9] = rs.getString("site_name");
                fila[10] = rs.getString("item_code");
                fila[11] = rs.getString("item_desc");
                fila[12] = rs.getString("requested_qty");
                fila[13] = rs.getString("due_qty");
                fila[14] = rs.getString("billed_qty");
                fila[15] = rs.getString("unit_price");
                fila[16] = rs.getString("line_amount");
                fila[17] = rs.getString("unit");
                fila[18] = rs.getString("payment_terms");
                fila[19] = rs.getString("category");
                fila[20] = rs.getString("bidding_area");
                fila[21] = rs.getString("publish_date");
                fila[22] = rs.getString("status_cierre");
                fila[23] = rs.getString("id_usuario");
                fila[24] = rs.getString("nombre");
                fila[25] = rs.getString("ape_pat");
                fila[26] = rs.getString("ape_mat");
                fila[27] = rs.getString("orden_compra_dt");
                fila[28] = rs.getString("importe");
                fila[29] = rs.getString("total_pagar");
                fila[30] = rs.getString("status_facturacion");
                model1.addRow(fila);
            }
            jTable1.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al buscar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Esta función permite realizar una busqueda acorde al contenido de el
     * JTextField
     */
    public void Buscar() {
        String buscar = buscarf.getText();
        String Fecha = ((JTextField) PublishDate.getDateEditor().getUiComponent()).getText();
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID", "Project Code", "Project Name", "Customer", "PO Status", "PO NO", "PO Line NO",
                "Shipment NO", "Site Code", "Site Name", "Item Code",
                "Item Description", "Requested Qty", "Due Qty", "Billed Quantity",
                "Unit Price", "Line Amount", "Unit", "Payment Terms", "Category",
                "Bidding Area", "Publish Date", "Status Cierre", "ID_Usuario", "Nombre", "Apellido Paterno", "Apellido Materno",
                "Orden de Compra DT", "Importe", "Total", "Status Facturación"};
            String SQL = "select facturacion.id,facturacion.project_code,facturacion.project_name,facturacion.customer,facturacion.PO_status,facturacion.PO_NO,\n"
                    + "facturacion.PO_Line_NO,facturacion.shipment_NO,facturacion.site_code,facturacion.site_name,facturacion.item_code,facturacion.item_desc,\n"
                    + "facturacion.requested_qty,facturacion.due_qty,facturacion.billed_qty,facturacion.unit_price,facturacion.line_amount,facturacion.unit,\n"
                    + "facturacion.payment_terms,facturacion.category,facturacion.bidding_area,facturacion.publish_date,facturacion.status_cierre,\n"
                    + "asignaciones.id_usuario,usuarios.nombre,usuarios.ape_pat,usuarios.ape_mat,asignaciones.orden_compra_dt,asignaciones.importe,\n"
                    + "asignaciones.total_pagar,asignaciones.status_facturacion \n"
                    + "from facturacion, asignaciones, usuarios\n"
                    + "where facturacion.PO_NO ='" + buscar + "' && facturacion.publish_date like '%" + Fecha + "%' && asignaciones.id_usuario =usuarios.id_usuario && facturacion.id = asignaciones.id";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[31];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("project_code");
                fila[2] = rs.getString("project_name");
                fila[3] = rs.getString("customer");
                fila[4] = rs.getString("PO_status");
                fila[5] = rs.getString("PO_NO");
                fila[6] = rs.getString("PO_Line_NO");
                fila[7] = rs.getString("shipment_NO");
                fila[8] = rs.getString("site_code");
                fila[9] = rs.getString("site_name");
                fila[10] = rs.getString("item_code");
                fila[11] = rs.getString("item_desc");
                fila[12] = rs.getString("requested_qty");
                fila[13] = rs.getString("due_qty");
                fila[14] = rs.getString("billed_qty");
                fila[15] = rs.getString("unit_price");
                fila[16] = rs.getString("line_amount");
                fila[17] = rs.getString("unit");
                fila[18] = rs.getString("payment_terms");
                fila[19] = rs.getString("category");
                fila[20] = rs.getString("bidding_area");
                fila[21] = rs.getString("publish_date");
                fila[22] = rs.getString("status_cierre");
                fila[23] = rs.getString("id_usuario");
                fila[24] = rs.getString("nombre");
                fila[25] = rs.getString("ape_pat");
                fila[26] = rs.getString("ape_mat");
                fila[27] = rs.getString("orden_compra_dt");
                fila[28] = rs.getString("importe");
                fila[29] = rs.getString("total_pagar");
                fila[30] = rs.getString("status_facturacion");
                model1.addRow(fila);
            }
            jTable1.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al buscar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new FondoPanelTitulo();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        Nombre_UsuarioAdmin = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        ID_Usuario = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new FondoPanelesCentrales();
        jLabel1 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pjcode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pjname = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customer = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        postatus = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pon = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        shipment = new javax.swing.JTextField();
        poline = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jPanel4 = new FondoPanelesCentrales();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sitecode = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        sitename = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        itemcode = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        itemdsc = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        requestedqty = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        dueqty = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        billedqty = new javax.swing.JTextField();
        jPanel5 = new FondoPanelesCentrales();
        jLabel16 = new javax.swing.JLabel();
        unitprice = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        amount = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        unit = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        payment = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        category = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        bidding = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        pdate = new javax.swing.JTextField();
        jPanel6 = new FondoPanelesCentrales();
        jLabel24 = new javax.swing.JLabel();
        numordenc = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        contratista = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        ocompradt = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        importe = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        stat = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        stat_cierre = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        update = new javax.swing.JButton();
        buscarf = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        PublishDate = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        Fondo = new FondoPanelPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setLocationByPlatform(true);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(1329, 60));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton7.setText("-");
        jButton7.setToolTipText("Minimizar");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 10, -1, -1));

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
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 10, -1, -1));

        jLabel34.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Bienvenido(a):");
        jPanel1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        Nombre_UsuarioAdmin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Nombre_UsuarioAdmin.setForeground(new java.awt.Color(255, 255, 255));
        Nombre_UsuarioAdmin.setText("Nombre");
        jPanel1.add(Nombre_UsuarioAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jLabel39.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("ID:");
        jPanel1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        ID_Usuario.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ID_Usuario.setForeground(new java.awt.Color(255, 255, 255));
        ID_Usuario.setText("ID");
        jPanel1.add(ID_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cierre de Proyectos");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, -1));

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 1", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 29, -1, -1));

        id.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        id.setEnabled(false);
        jPanel3.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 27, 202, -1));

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Project Code:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 60, -1, -1));

        pjcode.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        pjcode.setEnabled(false);
        jPanel3.add(pjcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 58, 202, -1));

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Project Name:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 89, -1, -1));

        pjname.setColumns(20);
        pjname.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        pjname.setLineWrap(true);
        pjname.setRows(3);
        pjname.setEnabled(false);
        jScrollPane3.setViewportView(pjname);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 89, 202, -1));

        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Customer:\t");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 148, -1, -1));

        customer.setColumns(20);
        customer.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        customer.setLineWrap(true);
        customer.setRows(3);
        customer.setEnabled(false);
        jScrollPane1.setViewportView(customer);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 148, 202, -1));

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PO Status:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 209, -1, -1));

        postatus.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        postatus.setEnabled(false);
        jPanel3.add(postatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 207, 202, -1));

        jLabel7.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("PO NO:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 240, -1, -1));

        pon.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        pon.setEnabled(false);
        jPanel3.add(pon, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 238, 202, -1));

        jLabel9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Shipment NO:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 302, -1, -1));

        shipment.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        shipment.setEnabled(false);
        jPanel3.add(shipment, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 300, 202, -1));

        poline.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        poline.setEnabled(false);
        jPanel3.add(poline, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 269, 202, -1));

        jLabel30.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("PO_Line_NO:");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 271, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 320, 380));

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 2", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Site Code:");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 27, -1, -1));

        sitecode.setColumns(20);
        sitecode.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        sitecode.setLineWrap(true);
        sitecode.setRows(3);
        sitecode.setEnabled(false);
        jScrollPane2.setViewportView(sitecode);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 27, 202, -1));

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Site Name:");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 86, -1, -1));

        sitename.setColumns(20);
        sitename.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        sitename.setLineWrap(true);
        sitename.setRows(3);
        sitename.setEnabled(false);
        jScrollPane4.setViewportView(sitename);

        jPanel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 86, 202, -1));

        jLabel11.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Item Code:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 147, -1, -1));

        itemcode.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        itemcode.setEnabled(false);
        jPanel4.add(itemcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 145, 202, -1));

        jLabel12.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Item Desc:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 176, -1, -1));

        itemdsc.setColumns(20);
        itemdsc.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        itemdsc.setLineWrap(true);
        itemdsc.setRows(3);
        itemdsc.setText("\n");
        itemdsc.setEnabled(false);
        jScrollPane5.setViewportView(itemdsc);

        jPanel4.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 176, 202, 57));

        jLabel14.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Requested Qty:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        requestedqty.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        requestedqty.setEnabled(false);
        jPanel4.add(requestedqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 244, 202, -1));

        jLabel13.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Due Qty:");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 277, -1, -1));

        dueqty.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        dueqty.setEnabled(false);
        jPanel4.add(dueqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 275, 202, -1));

        jLabel15.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Billed Qty:");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 308, -1, -1));

        billedqty.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        billedqty.setEnabled(false);
        jPanel4.add(billedqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 306, 202, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 340, 380));

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 3", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Unit Price:");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));

        unitprice.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        unitprice.setEnabled(false);
        jPanel5.add(unitprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 27, 202, -1));

        jLabel17.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Line Amount:");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        amount.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        amount.setEnabled(false);
        jPanel5.add(amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 58, 202, -1));

        jLabel19.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Unit:");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, -1, -1));

        unit.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        unit.setEnabled(false);
        jPanel5.add(unit, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 89, 202, -1));

        jLabel18.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Payment Terms:");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        payment.setColumns(20);
        payment.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        payment.setLineWrap(true);
        payment.setRows(3);
        payment.setText("\n");
        payment.setEnabled(false);
        jScrollPane6.setViewportView(payment);

        jPanel5.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 120, 202, 57));

        jLabel20.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Category:");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));

        category.setColumns(20);
        category.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        category.setLineWrap(true);
        category.setRows(3);
        category.setText("\n");
        category.setEnabled(false);
        jScrollPane7.setViewportView(category);

        jPanel5.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 188, 202, 57));

        jLabel21.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Bidding Area:");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        bidding.setColumns(20);
        bidding.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        bidding.setLineWrap(true);
        bidding.setRows(3);
        bidding.setText("\n");
        bidding.setEnabled(false);
        jScrollPane8.setViewportView(bidding);

        jPanel5.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 256, 202, 57));

        jLabel22.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Publish Date:");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 326, -1, -1));

        pdate.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        pdate.setEnabled(false);
        jPanel5.add(pdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 324, 202, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 340, 380));

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 4", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("No. Orden de Compra:");
        jPanel6.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        numordenc.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        numordenc.setEnabled(false);
        jPanel6.add(numordenc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 248, -1));

        jLabel23.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Nombre Contratista:");
        jPanel6.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        contratista.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        contratista.setEnabled(false);
        jPanel6.add(contratista, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 248, -1));

        jLabel25.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Orden de Compra DT:");
        jPanel6.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        ocompradt.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ocompradt.setEnabled(false);
        jPanel6.add(ocompradt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 248, -1));

        jLabel26.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Importe:");
        jPanel6.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        importe.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        importe.setEnabled(false);
        jPanel6.add(importe, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 248, -1));

        jLabel27.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Total:");
        jPanel6.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        total.setEditable(false);
        total.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        total.setEnabled(false);
        jPanel6.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 167, -1));

        jLabel29.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Status:");
        jPanel6.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        stat.setEditable(false);
        stat.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jPanel6.add(stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 167, -1));

        jLabel31.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Status Cierre:");
        jPanel6.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("$");
        jPanel6.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 260, -1, -1));

        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("%");
        jPanel6.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, -1, -1));

        stat_cierre.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        stat_cierre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stat_cierreKeyTyped(evt);
            }
        });
        jPanel6.add(stat_cierre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 210, -1));

        jLabel35.setText("jLabel35");
        jPanel6.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, -1, -1));

        jLabel36.setText("jLabel36");
        jPanel6.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 16, -1, -1));

        jLabel37.setText("jLabel37");
        jPanel6.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 16, -1, -1));

        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("%");
        jPanel6.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, -1, -1));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1038, 70, 280, 380));

        update.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10)); // NOI18N
        update.setText("ACTUALIZAR STATUS");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        getContentPane().add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 470, -1, -1));
        getContentPane().add(buscarf, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 470, 238, -1));

        jButton1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10)); // NOI18N
        jButton1.setText("BUSCAR PO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        jLabel28.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Busque el registro deseado en la tabla aquí:");
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane9.setViewportView(jTable1);

        getContentPane().add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 1310, 170));

        jButton2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10)); // NOI18N
        jButton2.setText("MOSTRAR TODO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 470, -1, -1));

        PublishDate.setDateFormatString("yyyy/MM/dd");
        PublishDate.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        getContentPane().add(PublishDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 470, 170, -1));

        jButton5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jButton5.setText("Cerrar Sesión");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 470, -1, -1));

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
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, 680));

        setSize(new java.awt.Dimension(1329, 680));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        /**
         * En esta acción veremos el uso de la funición DatosLlenosFacturacion()
         * dentro de un if, esta restricción permite que el formulario no
         * devuelva a la Base de Datos, información vacia o una generación de
         * errores en MySQL, si el usuario da clic en el botón actualizar o
         * cualquier otro que implique una interacción con la Base de Datos y
         * los campos del formulario estan vacios, el sistema no ejecutará el
         * try que realiza la acción deseada, y le envierá un mensaje al usuario
         * indicandole que es necesario llenar los campos vacios.
         */
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosCierre()) {
                int fila = jTable1.getSelectedRow();
                String SQL = "update facturacion set status_cierre=? where id=" + jTable1.getValueAt(fila, 0) + "";
                p = cc.prepareStatement(SQL);
                p.setString(1, stat_cierre.getText());
                int n = p.executeUpdate();
                if (n > 0) {
                    /**
                     * En caso de que la acción sea completada exitosamente
                     * ejecutará los siguientes parametros, esto se utilizó en
                     * todas las funciones del proyecto. Podremos encontrarla en
                     * Guardar, Actualizar y Eliminar
                     */
                    LlenarTabla();
                    ColumnasAutoajustadas(jTable1, margin);
                    LimpiarDatos();
                    JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
                    id.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos: En caso de no tener el valor coloque el número 0");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Usted ha superado el límite de digitos en el código" + e.getMessage());
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_updateActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Buscar();
        ColumnasAutoajustadas(jTable1, margin);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        /**
         * En este bloque de código nos encontramos de un evento de tipo
         * MousePressed lo que indica que dentro de esta acción podremos
         * seleccionar un registro de la tabla en la posición deseada con tan
         * solo un clic, así facilitando al usuario la forma de gestionar la
         * información los datos contenidos en la tabla en toda la fila, pasarán
         * al formulario, en lugar de ser gestionados dentro de la tabla.
         */
        int fila = jTable1.getSelectedRow();
        try {
            cc = ConexionBD.getcon();
            String SQL = "select facturacion.id,facturacion.project_code,facturacion.project_name,facturacion.customer,facturacion.PO_status,facturacion.PO_NO,\n"
                    + "facturacion.PO_Line_NO,facturacion.shipment_NO,facturacion.site_code,facturacion.site_name,facturacion.item_code,facturacion.item_desc,\n"
                    + "facturacion.requested_qty,facturacion.due_qty,facturacion.billed_qty,facturacion.unit_price,facturacion.line_amount,facturacion.unit,\n"
                    + "facturacion.payment_terms,facturacion.category,facturacion.bidding_area,facturacion.publish_date,facturacion.status_cierre,\n"
                    + "asignaciones.id_usuario,usuarios.nombre,usuarios.ape_pat,usuarios.ape_mat,asignaciones.orden_compra_dt,asignaciones.importe,\n"
                    + "asignaciones.total_pagar,asignaciones.status_facturacion \n"
                    + "from facturacion, asignaciones, usuarios\n"
                    + "where facturacion.id =" + jTable1.getValueAt(fila, 0) + " && asignaciones.id_usuario = usuarios.id_usuario && facturacion.id = asignaciones.id";
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            rs.next();
            id.setText(rs.getString("id"));
            pjcode.setText(rs.getString("project_code"));
            pjname.setText(rs.getString("project_name"));
            customer.setText(rs.getString("customer"));
            postatus.setText(rs.getString("PO_status"));
            pon.setText(rs.getString("PO_NO"));
            poline.setText(rs.getString("PO_Line_NO"));
            shipment.setText(rs.getString("shipment_NO"));
            sitecode.setText(rs.getString("site_code"));
            sitename.setText(rs.getString("site_name"));
            itemcode.setText(rs.getString("item_code"));
            itemdsc.setText(rs.getString("item_desc"));
            requestedqty.setText(rs.getString("requested_qty"));
            dueqty.setText(rs.getString("due_qty"));
            billedqty.setText(rs.getString("billed_qty"));
            unitprice.setText(rs.getString("unit_price"));
            amount.setText(rs.getString("line_amount"));
            unit.setText(rs.getString("unit"));
            payment.setText(rs.getString("payment_terms"));
            category.setText(rs.getString("category"));
            bidding.setText(rs.getString("bidding_area"));
            pdate.setText(rs.getString("publish_date"));
            stat_cierre.setText(rs.getString("status_cierre"));
            jLabel35.setText(rs.getString("nombre") + " ");
            jLabel36.setText(rs.getString("ape_pat") + " ");
            jLabel37.setText(rs.getString("ape_mat"));
            contratista.setText(jLabel35.getText().concat(jLabel36.getText()).concat(jLabel37.getText()));
            ocompradt.setText(rs.getString("orden_compra_dt"));
            importe.setText(rs.getString("importe"));
            total.setText(rs.getString("total_pagar"));
            stat.setText(rs.getString("status_facturacion"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Oops: xd" + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jTable1MousePressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LlenarTabla();
        ColumnasAutoajustadas(jTable1, margin);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void stat_cierreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stat_cierreKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car != '.')) {
            evt.consume();
        }
    }//GEN-LAST:event_stat_cierreKeyTyped

    private void FondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_FondoMousePressed

    private void FondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_FondoMouseDragged

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if ((evt.getKeyCode() == 38) || (evt.getKeyCode() == 40) || (evt.getKeyCode() == 33) || (evt.getKeyCode() == 34)) {
            int fila = jTable1.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "select facturacion.id,facturacion.project_code,facturacion.project_name,facturacion.customer,facturacion.PO_status,facturacion.PO_NO,\n"
                        + "facturacion.PO_Line_NO,facturacion.shipment_NO,facturacion.site_code,facturacion.site_name,facturacion.item_code,facturacion.item_desc,\n"
                        + "facturacion.requested_qty,facturacion.due_qty,facturacion.billed_qty,facturacion.unit_price,facturacion.line_amount,facturacion.unit,\n"
                        + "facturacion.payment_terms,facturacion.category,facturacion.bidding_area,facturacion.publish_date,facturacion.status_cierre,\n"
                        + "asignaciones.id_usuario,usuarios.nombre,usuarios.ape_pat,usuarios.ape_mat,asignaciones.orden_compra_dt,asignaciones.importe,\n"
                        + "asignaciones.total_pagar,asignaciones.status_facturacion \n"
                        + "from facturacion, asignaciones, usuarios\n"
                        + "where facturacion.id =" + jTable1.getValueAt(fila, 0) + " && asignaciones.id_usuario = usuarios.id_usuario && facturacion.id = asignaciones.id";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                id.setText(rs.getString("id"));
                pjcode.setText(rs.getString("project_code"));
                pjname.setText(rs.getString("project_name"));
                customer.setText(rs.getString("customer"));
                postatus.setText(rs.getString("PO_status"));
                pon.setText(rs.getString("PO_NO"));
                poline.setText(rs.getString("PO_Line_NO"));
                shipment.setText(rs.getString("shipment_NO"));
                sitecode.setText(rs.getString("site_code"));
                sitename.setText(rs.getString("site_name"));
                itemcode.setText(rs.getString("item_code"));
                itemdsc.setText(rs.getString("item_desc"));
                requestedqty.setText(rs.getString("requested_qty"));
                dueqty.setText(rs.getString("due_qty"));
                billedqty.setText(rs.getString("billed_qty"));
                unitprice.setText(rs.getString("unit_price"));
                amount.setText(rs.getString("line_amount"));
                unit.setText(rs.getString("unit"));
                payment.setText(rs.getString("payment_terms"));
                category.setText(rs.getString("category"));
                bidding.setText(rs.getString("bidding_area"));
                pdate.setText(rs.getString("publish_date"));
                stat_cierre.setText(rs.getString("status_cierre"));
                jLabel35.setText(rs.getString("nombre") + " ");
                jLabel36.setText(rs.getString("ape_pat") + " ");
                jLabel37.setText(rs.getString("ape_mat"));
                contratista.setText(jLabel35.getText().concat(jLabel36.getText()).concat(jLabel37.getText()));
                ocompradt.setText(rs.getString("orden_compra_dt"));
                importe.setText(rs.getString("importe"));
                total.setText(rs.getString("total_pagar"));
                stat.setText(rs.getString("status_facturacion"));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Oops: xd" + e.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_jTable1KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        dispose();
        Login log = new Login();
        log.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(AdministradorCierreProyectos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdministradorCierreProyectos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    public static javax.swing.JLabel ID_Usuario;
    public javax.swing.JLabel Nombre_UsuarioAdmin;
    private com.toedter.calendar.JDateChooser PublishDate;
    private javax.swing.JTextField amount;
    private javax.swing.JTextArea bidding;
    private javax.swing.JTextField billedqty;
    private javax.swing.JTextField buscarf;
    private javax.swing.JTextArea category;
    private javax.swing.JTextField contratista;
    private javax.swing.JTextArea customer;
    private javax.swing.JTextField dueqty;
    private javax.swing.JTextField id;
    private javax.swing.JTextField importe;
    private javax.swing.JTextField itemcode;
    private javax.swing.JTextArea itemdsc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField numordenc;
    private javax.swing.JTextField ocompradt;
    private javax.swing.JTextArea payment;
    private javax.swing.JTextField pdate;
    private javax.swing.JTextField pjcode;
    private javax.swing.JTextArea pjname;
    private javax.swing.JTextField poline;
    private javax.swing.JTextField pon;
    private javax.swing.JTextField postatus;
    private javax.swing.JTextField requestedqty;
    private javax.swing.JTextField shipment;
    private javax.swing.JTextArea sitecode;
    private javax.swing.JTextArea sitename;
    private javax.swing.JTextField stat;
    private javax.swing.JTextField stat_cierre;
    private javax.swing.JTextField total;
    private javax.swing.JTextField unit;
    private javax.swing.JTextField unitprice;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables

    /**
     * En estas clases se define un fondo para los paneles a fin de dar un mejor
     * diseño a la interfaz del usuario.
     */
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

    class FondoPanelesCentrales extends JPanel {

        private Image imagen;

        @Override
        public void paint(Graphics g) {
            imagen = new ImageIcon(getClass().getResource("/Imagenes/Clear Sky.jpg")).getImage();
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
