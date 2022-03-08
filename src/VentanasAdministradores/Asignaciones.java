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
 * @author mcore
 */
public final class Asignaciones extends javax.swing.JFrame {

    /**
     * Creates new form Asignaciones
     */
    private Statement sent = null;
    private Connection cc = null;
    private ResultSet rs = null;
    private PreparedStatement p = null;
    private DefaultTableModel model1;
    private int xMouse;
    private int yMouse;

    public Asignaciones() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        LlenarTablaUsuarios();
        LlenarTablaProyectos();
        LlenarTablaAsignaciones();
        LlenarTablaHistorialAsignaciones();
        Tabla_Usuarios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }
    /*
    Código para Autoajustar las Columnas acorde al resultado del dato más largo de una tabla SQL
    en un JTable
     */
    int vColIndex = 1;
    int margin = 2;

    public void ColumnasAutoajustadas(JTable Tabla_Usuarios, JTable Tabla_Proyectos, JTable Tabla_Asignaciones, JTable Tabla_Historial, int margin) {
        for (int c = 0; c < Tabla_Usuarios.getColumnCount(); c++) {
            packColumnTablaUsuarios(Tabla_Usuarios, c, 2);
        }
        for (int c = 0; c < Tabla_Proyectos.getColumnCount(); c++) {
            packColumnTablaProyectos(Tabla_Proyectos, c, 2);
        }
        for (int c = 0; c < Tabla_Asignaciones.getColumnCount(); c++) {
            packColumnTablaAsignaciones(Tabla_Asignaciones, c, 2);
        }
        for (int c = 0; c < Tabla_Historial.getColumnCount(); c++) {
            packColumnTablaHistorial(Tabla_Asignaciones, c, 2);
        }
    }

    public void packColumnTablaUsuarios(JTable Tabla_Usuarios, int vColIndex, int margin) {
        //model1 = (DefaultTableModel) jTable1.getModel();
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) Tabla_Usuarios.getColumnModel();

        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = Tabla_Usuarios.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(Tabla_Usuarios, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < Tabla_Usuarios.getRowCount(); r++) {
            renderer = Tabla_Usuarios.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(Tabla_Usuarios, Tabla_Usuarios.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public void packColumnTablaProyectos(JTable Tabla_Proyectos, int vColIndex, int margin) {
        //model1 = (DefaultTableModel) jTable1.getModel();
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) Tabla_Proyectos.getColumnModel();

        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = Tabla_Proyectos.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(Tabla_Proyectos, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < Tabla_Proyectos.getRowCount(); r++) {
            renderer = Tabla_Proyectos.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(Tabla_Proyectos, Tabla_Proyectos.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public void packColumnTablaAsignaciones(JTable Tabla_Asignaciones, int vColIndex, int margin) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) Tabla_Asignaciones.getColumnModel();

        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = Tabla_Asignaciones.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(Tabla_Asignaciones, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < Tabla_Asignaciones.getRowCount(); r++) {
            renderer = Tabla_Asignaciones.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(Tabla_Asignaciones, Tabla_Asignaciones.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public void packColumnTablaHistorial(JTable Tabla_Historial, int vColIndex, int margin) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) Tabla_Historial.getColumnModel();
        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = Tabla_Historial.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(Tabla_Historial, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < Tabla_Historial.getRowCount(); r++) {
            renderer = Tabla_Historial.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(Tabla_Historial, Tabla_Historial.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public boolean DatosLlenosAsignacion() {

        return !id_user.getText().equals("") && !nombre.getText().equals("")
                && !ape_p.getText().equals("") && !ape_m.getText().equals("") && !id_line.getText().equals("");
    }

    public void LimpiarDatosUser() {
        id_asignacion.setText("");
        datestamp.setCalendar(null);
        id_user.setText("");
        name_user.setText("");
        nombre.setText("");
        ape_p.setText("");
        ape_m.setText("");
    }

    public void LimpiarDatosProyecto() {
        id_line.setText("");
        po_no.setText("");
        importe.setText("");
        total.setText("");
        status_fact.setText("");
    }

    public void LlenarTablaUsuarios() {
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID Usuario", "Nombre Usuario", "Nombre", "Apellido Paterno", "Apellido Materno"};
            String SQL = "SELECT * FROM usuarios WHERE cat_usuario='Administrador Facturacion' or cat_usuario='Operador Facturacion'";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("id_usuario");
                fila[1] = rs.getString("nombre_usuario");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getString("ape_pat");
                fila[4] = rs.getString("ape_mat");
                model1.addRow(fila);
            }
            Tabla_Usuarios.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void LlenarTablaHistorialAsignaciones() {
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID Historial", "ID Asignación", "Fecha Asignación", "ID", "ID Usuario", "Orden Compra DT", "Importe", "Total a Pagar", "Status Facturación"};
            String SQL = "SELECT * FROM historial_asignaciones";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[9];
            while (rs.next()) {
                fila[0] = rs.getString("id_historial");
                fila[1] = rs.getString("id_asignacion");
                fila[2] = rs.getString("fecha_asignacion");
                fila[3] = rs.getString("id");
                fila[4] = rs.getString("id_usuario");
                fila[5] = rs.getString("orden_compra_dt");
                fila[6] = rs.getString("importe");
                fila[7] = rs.getString("total_pagar");
                fila[8] = rs.getString("status_facturacion");
                model1.addRow(fila);
            }
            Tabla_Historial.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarFechaAsignacionHistorial() {
        try {
            cc = ConexionBD.getcon();
            String Fecha_Asignacion = ((JTextField) Fecha_AS.getDateEditor().getUiComponent()).getText();
            String[] titulos = {"ID Historial", "ID Asignación", "Fecha Asignación", "ID", "ID Usuario", "Orden Compra DT", "Importe", "Total a Pagar", "Status Facturación"};
            String SQL = "SELECT * FROM historial_asignaciones WHERE fecha_asignacion like '%" + Fecha_Asignacion + "%'";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[9];
            while (rs.next()) {
                fila[0] = rs.getString("id_historial");
                fila[1] = rs.getString("id_asignacion");
                fila[2] = rs.getString("fecha_asignacion");
                fila[3] = rs.getString("id");
                fila[4] = rs.getString("id_usuario");
                fila[5] = rs.getString("orden_compra_dt");
                fila[6] = rs.getString("importe");
                fila[7] = rs.getString("total_pagar");
                fila[8] = rs.getString("status_facturacion");
                model1.addRow(fila);
            }
            Tabla_Historial.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarTablaHistorialIDAsignacion() {
        try {
            cc = ConexionBD.getcon();
            String ID_Asignacion = String.valueOf(id_AS.getText());
            String[] titulos = {"ID Historial", "ID Asignación", "Fecha Asignación", "ID", "ID Usuario", "Orden Compra DT", "Importe", "Total a Pagar", "Status Facturación"};
            String SQL = "SELECT * FROM historial_asignaciones WHERE id_asignacion=" + ID_Asignacion + "";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[9];
            while (rs.next()) {
                fila[0] = rs.getString("id_historial");
                fila[1] = rs.getString("id_asignacion");
                fila[2] = rs.getString("fecha_asignacion");
                fila[3] = rs.getString("id");
                fila[4] = rs.getString("id_usuario");
                fila[5] = rs.getString("orden_compra_dt");
                fila[6] = rs.getString("importe");
                fila[7] = rs.getString("total_pagar");
                fila[8] = rs.getString("status_facturacion");
                model1.addRow(fila);
            }
            Tabla_Historial.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarIDProyectoHistorial() {
        try {
            cc = ConexionBD.getcon();
            String ID_Proyecto = String.valueOf(ID_PR.getText());
            String[] titulos = {"ID Historial", "ID Asignación", "Fecha Asignación", "ID", "ID Usuario", "Orden Compra DT", "Importe", "Total a Pagar", "Status Facturación"};
            String SQL = "SELECT * FROM historial_asignaciones WHERE id=" + ID_Proyecto + "";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[9];
            while (rs.next()) {
                fila[0] = rs.getString("id_historial");
                fila[1] = rs.getString("id_asignacion");
                fila[2] = rs.getString("fecha_asignacion");
                fila[3] = rs.getString("id");
                fila[4] = rs.getString("id_usuario");
                fila[5] = rs.getString("orden_compra_dt");
                fila[6] = rs.getString("importe");
                fila[7] = rs.getString("total_pagar");
                fila[8] = rs.getString("status_facturacion");
                model1.addRow(fila);
            }
            Tabla_Historial.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarIDUsuarioHistorial() {
        try {
            cc = ConexionBD.getcon();
            String ID_Usuario = String.valueOf(ID_US.getText());
            String[] titulos = {"ID Historial", "ID Asignación", "Fecha Asignación", "ID", "ID Usuario", "Orden Compra DT", "Importe", "Total a Pagar", "Status Facturación"};
            String SQL = "SELECT * FROM historial_asignaciones WHERE id_usuario=" + ID_Usuario + "";
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[9];
            while (rs.next()) {
                fila[0] = rs.getString("id_historial");
                fila[1] = rs.getString("id_asignacion");
                fila[2] = rs.getString("fecha_asignacion");
                fila[3] = rs.getString("id");
                fila[4] = rs.getString("id_usuario");
                fila[5] = rs.getString("orden_compra_dt");
                fila[6] = rs.getString("importe");
                fila[7] = rs.getString("total_pagar");
                fila[8] = rs.getString("status_facturacion");
                model1.addRow(fila);
            }
            Tabla_Historial.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void LlenarTablaProyectos() {
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID", "PO NO", "Publish Date", "Project Code", "Project Name", "Customer", "PO Status", "PO Line NO",
                "Shipment NO", "Site Code", "Site Name", "Item Code",
                "Item Description", "Requested Qty", "Due Qty", "Billed Quantity",
                "Unit Price", "Line Amount", "Unit", "Payment Terms", "Category",
                "Bidding Area"};
            String SQL = "select id,PO_NO,publish_date,project_code,project_name,customer,PO_status,PO_Line_NO,"
                    + "shipment_NO,site_code,site_name,item_code,item_desc,\n"
                    + "requested_qty,due_qty,billed_qty,unit_price,line_amount,unit,payment_terms,category,bidding_area from facturacion \n"
                    + "where PO_status like '%NEW%'";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[22];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("PO_NO");
                fila[2] = rs.getString("publish_date");
                fila[3] = rs.getString("project_code");
                fila[4] = rs.getString("project_name");
                fila[5] = rs.getString("customer");
                fila[6] = rs.getString("PO_status");
                fila[7] = rs.getString("PO_Line_NO");
                fila[8] = rs.getString("shipment_NO");
                fila[9] = rs.getString("site_code");
                fila[10] = rs.getString("site_name");
                fila[11] = rs.getString("item_code");
                fila[12] = rs.getString("item_desc");
                fila[13] = rs.getString("requested_qty");
                fila[14] = rs.getString("due_qty");
                fila[15] = rs.getString("billed_qty");
                fila[16] = rs.getString("unit_price");
                fila[17] = rs.getString("line_amount");
                fila[18] = rs.getString("unit");
                fila[19] = rs.getString("payment_terms");
                fila[20] = rs.getString("category");
                fila[21] = rs.getString("bidding_area");
                model1.addRow(fila);
            }
            Tabla_Proyectos.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarTablaProyectosPO() {
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID", "PO NO", "Publish Date", "Project Code", "Project Name", "Customer", "PO Status", "PO Line NO",
                "Shipment NO", "Site Code", "Site Name", "Item Code",
                "Item Description", "Requested Qty", "Due Qty", "Billed Quantity",
                "Unit Price", "Line Amount", "Unit", "Payment Terms", "Category",
                "Bidding Area"};
            String SQL = "select id,PO_NO,publish_date,project_code,project_name,customer,PO_status,PO_Line_NO, \n"
                    + "shipment_NO,site_code,site_name,item_code,item_desc, \n"
                    + "requested_qty,due_qty,billed_qty,unit_price,line_amount,unit,payment_terms,category, \n"
                    + "bidding_area from facturacion \n"
                    + "where PO_NO='" + jTextField1.getText() + "'";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[22];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("PO_NO");
                fila[2] = rs.getString("publish_date");
                fila[3] = rs.getString("project_code");
                fila[4] = rs.getString("project_name");
                fila[5] = rs.getString("customer");
                fila[6] = rs.getString("PO_status");
                fila[7] = rs.getString("PO_Line_NO");
                fila[8] = rs.getString("shipment_NO");
                fila[9] = rs.getString("site_code");
                fila[10] = rs.getString("site_name");
                fila[11] = rs.getString("item_code");
                fila[12] = rs.getString("item_desc");
                fila[13] = rs.getString("requested_qty");
                fila[14] = rs.getString("due_qty");
                fila[15] = rs.getString("billed_qty");
                fila[16] = rs.getString("unit_price");
                fila[17] = rs.getString("line_amount");
                fila[18] = rs.getString("unit");
                fila[19] = rs.getString("payment_terms");
                fila[20] = rs.getString("category");
                fila[21] = rs.getString("bidding_area");
                model1.addRow(fila);
            }
            Tabla_Proyectos.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarTablaProyectosPOPublishDate() {
        try {
            cc = ConexionBD.getcon();
            String fecha = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
            String[] titulos = {"ID", "PO NO", "Publish Date", "Project Code", "Project Name", "Customer", "PO Status", "PO Line NO",
                "Shipment NO", "Site Code", "Site Name", "Item Code",
                "Item Description", "Requested Qty", "Due Qty", "Billed Quantity",
                "Unit Price", "Line Amount", "Unit", "Payment Terms", "Category",
                "Bidding Area"};
            String SQL = "select id,PO_NO,publish_date,project_code,project_name,customer,PO_status,PO_Line_NO, \n"
                    + "shipment_NO,site_code,site_name,item_code,item_desc, \n"
                    + "requested_qty,due_qty,billed_qty,unit_price,line_amount,unit,payment_terms,category, \n"
                    + "bidding_area from facturacion \n"
                    + "where PO_NO='" + jTextField1.getText() + "' && publish_date like '%" + fecha + "%'";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[22];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("PO_NO");
                fila[2] = rs.getString("publish_date");
                fila[3] = rs.getString("project_code");
                fila[4] = rs.getString("project_name");
                fila[5] = rs.getString("customer");
                fila[6] = rs.getString("PO_status");
                fila[7] = rs.getString("PO_Line_NO");
                fila[8] = rs.getString("shipment_NO");
                fila[9] = rs.getString("site_code");
                fila[10] = rs.getString("site_name");
                fila[11] = rs.getString("item_code");
                fila[12] = rs.getString("item_desc");
                fila[13] = rs.getString("requested_qty");
                fila[14] = rs.getString("due_qty");
                fila[15] = rs.getString("billed_qty");
                fila[16] = rs.getString("unit_price");
                fila[17] = rs.getString("line_amount");
                fila[18] = rs.getString("unit");
                fila[19] = rs.getString("payment_terms");
                fila[20] = rs.getString("category");
                fila[21] = rs.getString("bidding_area");
                model1.addRow(fila);
            }
            Tabla_Proyectos.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarTablaProyectosID() {
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID", "PO NO", "Publish Date", "Project Code", "Project Name", "Customer", "PO Status", "PO Line NO",
                "Shipment NO", "Site Code", "Site Name", "Item Code",
                "Item Description", "Requested Qty", "Due Qty", "Billed Quantity",
                "Unit Price", "Line Amount", "Unit", "Payment Terms", "Category",
                "Bidding Area"};
            String SQL = "select id,PO_NO,publish_date,project_code,project_name,customer,PO_status,PO_Line_NO, \n"
                    + "shipment_NO,site_code,site_name,item_code,item_desc, \n"
                    + "requested_qty,due_qty,billed_qty,unit_price,line_amount,unit,payment_terms,category, \n"
                    + "bidding_area from facturacion \n"
                    + "where id=" + jTextField2.getText() + "";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[22];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("PO_NO");
                fila[2] = rs.getString("publish_date");
                fila[3] = rs.getString("project_code");
                fila[4] = rs.getString("project_name");
                fila[5] = rs.getString("customer");
                fila[6] = rs.getString("PO_status");
                fila[7] = rs.getString("PO_Line_NO");
                fila[8] = rs.getString("shipment_NO");
                fila[9] = rs.getString("site_code");
                fila[10] = rs.getString("site_name");
                fila[11] = rs.getString("item_code");
                fila[12] = rs.getString("item_desc");
                fila[13] = rs.getString("requested_qty");
                fila[14] = rs.getString("due_qty");
                fila[15] = rs.getString("billed_qty");
                fila[16] = rs.getString("unit_price");
                fila[17] = rs.getString("line_amount");
                fila[18] = rs.getString("unit");
                fila[19] = rs.getString("payment_terms");
                fila[20] = rs.getString("category");
                fila[21] = rs.getString("bidding_area");
                model1.addRow(fila);
            }
            Tabla_Proyectos.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void ActualizarStatus() {
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosAsignacion()) {
                String NewStatus = "ASIGNED";
                String campo = id_line.getText();
                String SQL = "update facturacion set PO_status=? where id=?";
                //p.setString(2, ((JTextField) datestamp.getDateEditor().getUiComponent()).getText());
                p = cc.prepareStatement(SQL);
                //p.setString(2, ((JTextField) datestamp.getDateEditor().getUiComponent()).getText());
                p.setString(1, NewStatus);
                p.setString(2, campo);
                int n = p.executeUpdate();
                if (n > 0) {
                    LlenarTablaProyectos();
                    ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
                    JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la actualización de los datos de Asignaciones: " + e.getMessage());
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void ActualizarAsignacion() {
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosAsignacion()) {
                String campo = id_line.getText();
                String SQL = "update asignaciones set fecha_asignacion=now(),id=?,id_usuario=? where id=?";
                //p.setString(2, ((JTextField) datestamp.getDateEditor().getUiComponent()).getText());
                p = cc.prepareStatement(SQL);
                //p.setString(2, ((JTextField) datestamp.getDateEditor().getUiComponent()).getText());
                p.setString(1, id_line.getText());
                p.setString(2, id_user.getText());
                p.setString(3, campo);
                int n = p.executeUpdate();
                if (n > 0) {
                    LlenarTablaAsignaciones();
                    ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
                    JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la actualización de los datos de Asignaciones: " + e.getMessage());
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void LlenarTablaAsignaciones() {
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID Asignación", "Fecha de Asignación", "ID", "PO NO", "ID Usuario",
                "Nombre Usuario", "Nombre", "Apellido Paterno", "Apellido Materno",
                "Orden Compra DT", "Importe", "Total Pagar", "Status Facturación", "Project Code",
                "Project Name", "Customer", "Shipment NO", "Site Code", "Site Name", "Item Code", "Item Desc",
                "Requested Qty", "Due Qty", "Billed Qty", "Unit Price", "Line Amount", "Unit", "Payment Terms",
                "Category", "Bidding Area", "Publish Date"};
            String SQL = "select asignaciones.id_asignacion, asignaciones.fecha_asignacion, asignaciones.id, \n"
                    + "facturacion.PO_NO, asignaciones.id_usuario, usuarios.nombre_usuario, usuarios.nombre, \n"
                    + "usuarios.ape_pat, usuarios.ape_mat, asignaciones.orden_compra_dt, asignaciones.importe, \n"
                    + "asignaciones.total_pagar, asignaciones.status_facturacion, facturacion.project_code, \n"
                    + "facturacion.project_name, facturacion.customer, facturacion.shipment_NO, \n"
                    + "facturacion.site_code, facturacion.site_name, facturacion.item_code, facturacion.item_desc, \n"
                    + "facturacion.requested_qty, facturacion.due_qty, facturacion.billed_qty, \n"
                    + "facturacion.unit_price, facturacion.line_amount, facturacion.unit, facturacion.payment_terms, \n"
                    + "facturacion.category, facturacion.bidding_area, facturacion.publish_date \n"
                    + "from asignaciones, usuarios, facturacion \n"
                    + "where usuarios.cat_usuario like '%Facturacion%' && \n"
                    + "asignaciones.id_usuario = usuarios.id_usuario && facturacion.id = asignaciones.id \n"
                    + "order by id_asignacion asc;";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[31];
            while (rs.next()) {
                fila[0] = rs.getString("id_asignacion");
                fila[1] = rs.getString("fecha_asignacion");
                fila[2] = rs.getString("id");
                fila[3] = rs.getString("PO_NO");
                fila[4] = rs.getString("id_usuario");
                fila[5] = rs.getString("nombre_usuario");
                fila[6] = rs.getString("nombre");
                fila[7] = rs.getString("ape_pat");
                fila[8] = rs.getString("ape_mat");
                fila[9] = rs.getString("orden_compra_dt");
                fila[10] = rs.getString("importe");
                fila[11] = rs.getString("total_pagar");
                fila[12] = rs.getString("status_facturacion");
                fila[13] = rs.getString("project_code");
                fila[14] = rs.getString("project_name");
                fila[15] = rs.getString("customer");
                fila[16] = rs.getString("shipment_NO");
                fila[17] = rs.getString("site_code");
                fila[18] = rs.getString("site_name");
                fila[19] = rs.getString("item_code");
                fila[20] = rs.getString("item_desc");
                fila[21] = rs.getString("requested_qty");
                fila[22] = rs.getString("due_qty");
                fila[23] = rs.getString("billed_qty");
                fila[24] = rs.getString("unit_price");
                fila[25] = rs.getString("line_amount");
                fila[26] = rs.getString("unit");
                fila[27] = rs.getString("payment_terms");
                fila[28] = rs.getString("category");
                fila[29] = rs.getString("bidding_area");
                fila[30] = rs.getString("publish_date");
                model1.addRow(fila);
            }
            Tabla_Asignaciones.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarIDAsignacion() {
        String idasignacion = search_idasign.getText();
        String fechaasignacion = ((JTextField) search_dt.getDateEditor().getUiComponent()).getText();
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID Asignación", "Fecha de Asignación", "ID", "PO NO", "ID Usuario",
                "Nombre Usuario", "Nombre", "Apellido Paterno", "Apellido Materno",
                "Orden Compra DT", "Importe", "Total Pagar", "Status Facturación", "Project Code",
                "Project Name", "Customer", "Shipment NO", "Site Code", "Site Name", "Item Code", "Item Desc",
                "Requested Qty", "Due Qty", "Billed Qty", "Unit Price", "Line Amount", "Unit", "Payment Terms",
                "Category", "Bidding Area", "Publish Date"};
            String SQL = "select asignaciones.id_asignacion, asignaciones.fecha_asignacion, asignaciones.id, \n"
                    + "facturacion.PO_NO, asignaciones.id_usuario, usuarios.nombre_usuario, usuarios.nombre, \n"
                    + "usuarios.ape_pat, usuarios.ape_mat, asignaciones.orden_compra_dt, asignaciones.importe, \n"
                    + "asignaciones.total_pagar, asignaciones.status_facturacion, facturacion.project_code, \n"
                    + "facturacion.project_name, facturacion.customer, facturacion.shipment_NO, \n"
                    + "facturacion.site_code, facturacion.site_name, facturacion.item_code, facturacion.item_desc, \n"
                    + "facturacion.requested_qty, facturacion.due_qty, facturacion.billed_qty, \n"
                    + "facturacion.unit_price, facturacion.line_amount, facturacion.unit, facturacion.payment_terms, \n"
                    + "facturacion.category, facturacion.bidding_area, facturacion.publish_date \n"
                    + "from asignaciones, usuarios, facturacion \n"
                    + "where id_asignacion=" + idasignacion + " && fecha_asignacion like '%" + fechaasignacion + "%' && \n"
                    + "usuarios.cat_usuario = 'Administrador Facturacion' && \n"
                    + "asignaciones.id_usuario = usuarios.id_usuario && facturacion.id = asignaciones.id \n"
                    + "order by id_asignacion asc;";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[31];
            while (rs.next()) {
                fila[0] = rs.getString("id_asignacion");
                fila[1] = rs.getString("fecha_asignacion");
                fila[2] = rs.getString("id");
                fila[3] = rs.getString("PO_NO");
                fila[4] = rs.getString("id_usuario");
                fila[5] = rs.getString("nombre_usuario");
                fila[6] = rs.getString("nombre");
                fila[7] = rs.getString("ape_pat");
                fila[8] = rs.getString("ape_mat");
                fila[9] = rs.getString("orden_compra_dt");
                fila[10] = rs.getString("importe");
                fila[11] = rs.getString("total_pagar");
                fila[12] = rs.getString("status_facturacion");
                fila[13] = rs.getString("project_code");
                fila[14] = rs.getString("project_name");
                fila[15] = rs.getString("customer");
                fila[16] = rs.getString("shipment_NO");
                fila[17] = rs.getString("site_code");
                fila[18] = rs.getString("site_name");
                fila[19] = rs.getString("item_code");
                fila[20] = rs.getString("item_desc");
                fila[21] = rs.getString("requested_qty");
                fila[22] = rs.getString("due_qty");
                fila[23] = rs.getString("billed_qty");
                fila[24] = rs.getString("unit_price");
                fila[25] = rs.getString("line_amount");
                fila[26] = rs.getString("unit");
                fila[27] = rs.getString("payment_terms");
                fila[28] = rs.getString("category");
                fila[29] = rs.getString("bidding_area");
                fila[30] = rs.getString("publish_date");
                model1.addRow(fila);
            }
            Tabla_Asignaciones.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarIDProyecto() {
        String id_proyecto = search_id.getText();
        String fechaasignacion = ((JTextField) search_dt.getDateEditor().getUiComponent()).getText();
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID Asignación", "Fecha de Asignación", "ID", "PO NO", "ID Usuario",
                "Nombre Usuario", "Nombre", "Apellido Paterno", "Apellido Materno",
                "Orden Compra DT", "Importe", "Total Pagar", "Status Facturación", "Project Code",
                "Project Name", "Customer", "Shipment NO", "Site Code", "Site Name", "Item Code", "Item Desc",
                "Requested Qty", "Due Qty", "Billed Qty", "Unit Price", "Line Amount", "Unit", "Payment Terms",
                "Category", "Bidding Area", "Publish Date"};
            String SQL = "select asignaciones.id_asignacion, asignaciones.fecha_asignacion, asignaciones.id, \n"
                    + "facturacion.PO_NO, asignaciones.id_usuario, usuarios.nombre_usuario, usuarios.nombre, \n"
                    + "usuarios.ape_pat, usuarios.ape_mat, asignaciones.orden_compra_dt, asignaciones.importe, \n"
                    + "asignaciones.total_pagar, asignaciones.status_facturacion, facturacion.project_code, \n"
                    + "facturacion.project_name, facturacion.customer, facturacion.shipment_NO, \n"
                    + "facturacion.site_code, facturacion.site_name, facturacion.item_code, facturacion.item_desc, \n"
                    + "facturacion.requested_qty, facturacion.due_qty, facturacion.billed_qty, \n"
                    + "facturacion.unit_price, facturacion.line_amount, facturacion.unit, facturacion.payment_terms, \n"
                    + "facturacion.category, facturacion.bidding_area, facturacion.publish_date \n"
                    + "from asignaciones, usuarios, facturacion \n"
                    + "where fecha_asignacion like '%" + fechaasignacion + "%' && \n"
                    + "usuarios.cat_usuario = 'Administrador Facturacion' && \n"
                    + "asignaciones.id_usuario = usuarios.id_usuario && facturacion.id =" + id_proyecto + " && \n"
                    + "asignaciones.id=" + id_proyecto + " \n"
                    + "order by id_asignacion asc;";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[31];
            while (rs.next()) {
                fila[0] = rs.getString("id_asignacion");
                fila[1] = rs.getString("fecha_asignacion");
                fila[2] = rs.getString("id");
                fila[3] = rs.getString("PO_NO");
                fila[4] = rs.getString("id_usuario");
                fila[5] = rs.getString("nombre_usuario");
                fila[6] = rs.getString("nombre");
                fila[7] = rs.getString("ape_pat");
                fila[8] = rs.getString("ape_mat");
                fila[9] = rs.getString("orden_compra_dt");
                fila[10] = rs.getString("importe");
                fila[11] = rs.getString("total_pagar");
                fila[12] = rs.getString("status_facturacion");
                fila[13] = rs.getString("project_code");
                fila[14] = rs.getString("project_name");
                fila[15] = rs.getString("customer");
                fila[16] = rs.getString("shipment_NO");
                fila[17] = rs.getString("site_code");
                fila[18] = rs.getString("site_name");
                fila[19] = rs.getString("item_code");
                fila[20] = rs.getString("item_desc");
                fila[21] = rs.getString("requested_qty");
                fila[22] = rs.getString("due_qty");
                fila[23] = rs.getString("billed_qty");
                fila[24] = rs.getString("unit_price");
                fila[25] = rs.getString("line_amount");
                fila[26] = rs.getString("unit");
                fila[27] = rs.getString("payment_terms");
                fila[28] = rs.getString("category");
                fila[29] = rs.getString("bidding_area");
                fila[30] = rs.getString("publish_date");
                model1.addRow(fila);
            }
            Tabla_Asignaciones.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void BuscarPO() {
        String buscarpo = search_PO.getText();
        String fechapublicacion = ((JTextField) search_dt.getDateEditor().getUiComponent()).getText();
        try {
            cc = ConexionBD.getcon();
            String[] titulos = {"ID Asignación", "Fecha de Asignación", "ID", "PO NO", "ID Usuario",
                "Nombre Usuario", "Nombre", "Apellido Paterno", "Apellido Materno",
                "Orden Compra DT", "Importe", "Total Pagar", "Status Facturación", "Project Code",
                "Project Name", "Customer", "Shipment NO", "Site Code", "Site Name", "Item Code", "Item Desc",
                "Requested Qty", "Due Qty", "Billed Qty", "Unit Price", "Line Amount", "Unit", "Payment Terms",
                "Category", "Bidding Area", "Publish Date"};
            String SQL = "select asignaciones.id_asignacion, asignaciones.fecha_asignacion, asignaciones.id, \n"
                    + "facturacion.PO_NO, asignaciones.id_usuario, usuarios.nombre_usuario, usuarios.nombre, \n"
                    + "usuarios.ape_pat, usuarios.ape_mat, asignaciones.orden_compra_dt, asignaciones.importe, \n"
                    + "asignaciones.total_pagar, asignaciones.status_facturacion, facturacion.project_code, \n"
                    + "facturacion.project_name, facturacion.customer, facturacion.shipment_NO, \n"
                    + "facturacion.site_code, facturacion.site_name, facturacion.item_code, facturacion.item_desc, \n"
                    + "facturacion.requested_qty, facturacion.due_qty, facturacion.billed_qty, \n"
                    + "facturacion.unit_price, facturacion.line_amount, facturacion.unit, facturacion.payment_terms, \n"
                    + "facturacion.category, facturacion.bidding_area, facturacion.publish_date \n"
                    + "from asignaciones, usuarios, facturacion \n"
                    + "where PO_NO='" + buscarpo + "' && publish_date like '%" + fechapublicacion + "%' && \n"
                    + "usuarios.cat_usuario = 'Administrador Facturacion' && \n"
                    + "asignaciones.id_usuario = usuarios.id_usuario && facturacion.id = asignaciones.id \n"
                    + "order by id_asignacion asc;";
            //tabla1.setModel(model);
            model1 = new DefaultTableModel(null, titulos);
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            String[] fila = new String[31];
            while (rs.next()) {
                fila[0] = rs.getString("id_asignacion");
                fila[1] = rs.getString("fecha_asignacion");
                fila[2] = rs.getString("id");
                fila[3] = rs.getString("PO_NO");
                fila[4] = rs.getString("id_usuario");
                fila[5] = rs.getString("nombre_usuario");
                fila[6] = rs.getString("nombre");
                fila[7] = rs.getString("ape_pat");
                fila[8] = rs.getString("ape_mat");
                fila[9] = rs.getString("orden_compra_dt");
                fila[10] = rs.getString("importe");
                fila[11] = rs.getString("total_pagar");
                fila[12] = rs.getString("status_facturacion");
                fila[13] = rs.getString("project_code");
                fila[14] = rs.getString("project_name");
                fila[15] = rs.getString("customer");
                fila[16] = rs.getString("shipment_NO");
                fila[17] = rs.getString("site_code");
                fila[18] = rs.getString("site_name");
                fila[19] = rs.getString("item_code");
                fila[20] = rs.getString("item_desc");
                fila[21] = rs.getString("requested_qty");
                fila[22] = rs.getString("due_qty");
                fila[23] = rs.getString("billed_qty");
                fila[24] = rs.getString("unit_price");
                fila[25] = rs.getString("line_amount");
                fila[26] = rs.getString("unit");
                fila[27] = rs.getString("payment_terms");
                fila[28] = rs.getString("category");
                fila[29] = rs.getString("bidding_area");
                fila[30] = rs.getString("publish_date");
                model1.addRow(fila);
            }
            Tabla_Asignaciones.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        Asignaciones = new javax.swing.JPanel();
        jPanel2 = new FondoPanelesCentrales();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        id_asignacion = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        datestamp = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        id_user = new javax.swing.JTextField();
        clear_userform = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        name_user = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        ape_p = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        ape_m = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jPanel3 = new FondoPanelesCentrales();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        po_no = new javax.swing.JTextField();
        importe = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        status_fact = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        orden_dt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        id_line = new javax.swing.JTextField();
        clear_orderform = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jPanel4 = new FondoPanelTitulo();
        jLabel39 = new javax.swing.JLabel();
        Nombre_Usuario_Facturas = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        ID_Usuario_Facturas = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Usuarios = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla_Proyectos = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Tabla_Asignaciones = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        search_id = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        search_PO = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        search_dt = new com.toedter.calendar.JDateChooser();
        update_asignacion = new javax.swing.JButton();
        save_asignacion = new javax.swing.JButton();
        show_all = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        search_idasign = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        Historial = new javax.swing.JPanel();
        jPanel5 = new FondoPanelTitulo();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla_Historial = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        id_AS = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        Fecha_AS = new com.toedter.calendar.JDateChooser();
        jButton7 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        ID_US = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        ID_PR = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        CerrarSesion = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        Fondo = new FondoPanelPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        Asignaciones.setBackground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Datos Contratista");

        jLabel12.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("ID Asignación:");

        id_asignacion.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        id_asignacion.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Fecha de Asignación:");

        datestamp.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        datestamp.setEnabled(false);
        datestamp.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("ID Usuario:");

        id_user.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        id_user.setEnabled(false);

        clear_userform.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        clear_userform.setText("Limpiar");
        clear_userform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_userformActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Nombre:");

        name_user.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        name_user.setEnabled(false);

        nombre.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        nombre.setEnabled(false);

        jLabel29.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Apellido Paterno:");

        ape_p.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ape_p.setEnabled(false);

        jLabel30.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Apellido Materno:");

        ape_m.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ape_m.setEnabled(false);

        jLabel31.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Nombre Usuario:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(id_asignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel31))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(name_user)
                                    .addComponent(datestamp, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                    .addComponent(id_user)
                                    .addComponent(nombre)
                                    .addComponent(ape_p)
                                    .addComponent(ape_m)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(clear_userform)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clear_userform)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id_asignacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(datestamp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(id_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(ape_p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(ape_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setPreferredSize(new java.awt.Dimension(370, 227));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Datos Orden de Compra");

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("No.Orden Compra (PO_NO):");

        po_no.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        po_no.setEnabled(false);

        importe.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        importe.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Importe:");

        total.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        total.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Total:");

        status_fact.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        status_fact.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Status Facturación:");

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Orden De Compra DT:");

        orden_dt.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        orden_dt.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ID Linea:");

        id_line.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        id_line.setEnabled(false);

        clear_orderform.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        clear_orderform.setText("Limpiar");
        clear_orderform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_orderformActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("(Line Amount)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(orden_dt, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9))
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clear_orderform)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(total, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                .addComponent(importe)
                                .addComponent(po_no)
                                .addComponent(id_line)
                                .addComponent(status_fact)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(4, 4, 4)
                .addComponent(clear_orderform)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(id_line, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(po_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(orden_dt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(importe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(status_fact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setPreferredSize(new java.awt.Dimension(1329, 60));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Bienvenido (a):");
        jPanel4.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        Nombre_Usuario_Facturas.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Nombre_Usuario_Facturas.setForeground(new java.awt.Color(255, 255, 255));
        Nombre_Usuario_Facturas.setText("Nombre");
        jPanel4.add(Nombre_Usuario_Facturas, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jLabel40.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("ID:");
        jPanel4.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        ID_Usuario_Facturas.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ID_Usuario_Facturas.setForeground(new java.awt.Color(255, 255, 255));
        ID_Usuario_Facturas.setText("ID");
        jPanel4.add(ID_Usuario_Facturas, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        jLabel11.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Asignación de Proyectos");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1320, 60));

        Tabla_Usuarios.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Tabla_Usuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabla_Usuarios.getTableHeader().setResizingAllowed(false);
        Tabla_Usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Tabla_UsuariosMousePressed(evt);
            }
        });
        Tabla_Usuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_UsuariosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_Usuarios);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setAutoscrolls(true);

        Tabla_Proyectos.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Tabla_Proyectos.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabla_Proyectos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Tabla_Proyectos.getTableHeader().setResizingAllowed(false);
        Tabla_Proyectos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Tabla_ProyectosMousePressed(evt);
            }
        });
        Tabla_Proyectos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_ProyectosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla_Proyectos);

        jLabel15.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("<html>Seleccione en la tabla el usuario que desee asignar. Tabla de Empleados</html>");

        jLabel16.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("<html> Seleccione en la tabla el proyecto que desee asignar. Formulario de Proyecto. </html>");

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setAutoscrolls(true);

        Tabla_Asignaciones.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Tabla_Asignaciones.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabla_Asignaciones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Tabla_Asignaciones.getTableHeader().setResizingAllowed(false);
        Tabla_Asignaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Tabla_AsignacionesMousePressed(evt);
            }
        });
        Tabla_Asignaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_AsignacionesKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(Tabla_Asignaciones);

        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tabla de Asignaciones Generadas.");

        search_id.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Buscar por ID PO:");

        jButton12.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton12.setText("Buscar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Buscar por PO_NO:");

        search_PO.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        jButton14.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton14.setText("Buscar");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        search_dt.setToolTipText("2020-03-07 00:00:00");
        search_dt.setDateFormatString("yyyy-MM-dd");
        search_dt.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        update_asignacion.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        update_asignacion.setText("Actualizar Asignación");
        update_asignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_asignacionActionPerformed(evt);
            }
        });

        save_asignacion.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        save_asignacion.setText("Guardar Asignación");
        save_asignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_asignacionActionPerformed(evt);
            }
        });

        show_all.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        show_all.setText("Mostrar Todo");
        show_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show_allActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        jButton1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Buscar por PO:");

        jButton4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Buscar por ID:");

        jTextField2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Publish Date:");

        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("<html>1. Seleccione el Día, Mes y Año, después</html>");

        jLabel33.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("2. Modifique la hora generada a la deseada.");

        jLabel34.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("yyyy-MM-dd");

        jLabel35.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("2020-03-23");

        jButton10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton10.setText("Buscar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        search_idasign.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Buscar por ID Asignación:");

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Buscar por P.O y Publish Date (Llene ambos campos)");

        jButton11.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton11.setText("Buscar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Para uso de Fechas:");

        jButton2.setText("Mostrar Todo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AsignacionesLayout = new javax.swing.GroupLayout(Asignaciones);
        Asignaciones.setLayout(AsignacionesLayout);
        AsignacionesLayout.setHorizontalGroup(
            AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1323, Short.MAX_VALUE)
            .addGroup(AsignacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AsignacionesLayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AsignacionesLayout.createSequentialGroup()
                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AsignacionesLayout.createSequentialGroup()
                                    .addComponent(update_asignacion)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(save_asignacion)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(show_all))
                                .addGroup(AsignacionesLayout.createSequentialGroup()
                                    .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17)
                                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(AsignacionesLayout.createSequentialGroup()
                                                .addComponent(search_id, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton12))
                                            .addComponent(jLabel5)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AsignacionesLayout.createSequentialGroup()
                                                .addComponent(search_idasign)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton10)))
                                        .addComponent(jLabel36))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel33)
                                        .addGroup(AsignacionesLayout.createSequentialGroup()
                                            .addComponent(jLabel34)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel35))
                                        .addComponent(jLabel42))))
                            .addGroup(AsignacionesLayout.createSequentialGroup()
                                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(AsignacionesLayout.createSequentialGroup()
                                        .addComponent(jLabel25)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                        .addComponent(jButton4))
                                    .addGroup(AsignacionesLayout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1))
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField1))
                                .addGap(29, 29, 29)
                                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel1)
                                    .addGroup(AsignacionesLayout.createSequentialGroup()
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton11)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(AsignacionesLayout.createSequentialGroup()
                        .addComponent(search_PO, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(search_dt, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton14)
                        .addGap(67, 67, 67))))
        );
        AsignacionesLayout.setVerticalGroup(
            AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AsignacionesLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AsignacionesLayout.createSequentialGroup()
                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(AsignacionesLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton10)
                            .addComponent(search_idasign, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42))
                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AsignacionesLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel17)
                                .addGap(6, 6, 6)
                                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(search_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton12))
                                .addGap(12, 12, 12))
                            .addGroup(AsignacionesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AsignacionesLayout.createSequentialGroup()
                                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(search_PO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton14, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(show_all)
                                    .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(update_asignacion)
                                        .addComponent(save_asignacion))))
                            .addComponent(search_dt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AsignacionesLayout.createSequentialGroup()
                                .addGroup(AsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel25)
                                    .addComponent(jButton4)
                                    .addComponent(jLabel27))
                                .addGap(6, 6, 6)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton11, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(91, 91, 91))))
        );

        jTabbedPane1.addTab("Asignaciones", Asignaciones);

        Historial.setBackground(new java.awt.Color(0, 0, 0));

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setPreferredSize(new java.awt.Dimension(1329, 60));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Historial de Asignaciones");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1320, 60));

        Tabla_Historial.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Tabla_Historial.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabla_Historial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Tabla_HistorialMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(Tabla_Historial);

        jLabel19.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Panel de Búsqueda de Asignación de Proyectos");

        jLabel20.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("ID Asignación:");

        id_AS.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        id_AS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_ASActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton6.setText("Buscar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Fecha Asignación:");

        Fecha_AS.setDateFormatString("yyyy-MM-dd");
        Fecha_AS.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N

        jButton7.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton7.setText("Buscar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("ID Usuario:");

        ID_US.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ID_US.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ID_USActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton8.setText("Buscar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("ID Proyecto:");

        ID_PR.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ID_PR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ID_PRActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton9.setText("Buscar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jButton5.setText("Mostrar Todo");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HistorialLayout = new javax.swing.GroupLayout(Historial);
        Historial.setLayout(HistorialLayout);
        HistorialLayout.setHorizontalGroup(
            HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HistorialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1303, Short.MAX_VALUE)
                    .addGroup(HistorialLayout.createSequentialGroup()
                        .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addGroup(HistorialLayout.createSequentialGroup()
                                .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(id_AS)
                                    .addComponent(Fecha_AS, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                    .addComponent(ID_US)
                                    .addComponent(ID_PR))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton7)
                                    .addComponent(jButton8)
                                    .addComponent(jButton9)
                                    .addComponent(jButton6)))
                            .addComponent(jButton5))
                        .addGap(0, 948, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        HistorialLayout.setVerticalGroup(
            HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HistorialLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(id_AS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6))
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HistorialLayout.createSequentialGroup()
                        .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Fecha_AS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(ID_US, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8)))
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(HistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(ID_PR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Historial de Asignaciones", Historial);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1328, 652));

        jButton17.setText("Regresar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        CerrarSesion.setText("Cerrar Sesión");
        CerrarSesion.setToolTipText("Cerrar Sesión: Esto lo Re-digirá al Login");
        CerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarSesionActionPerformed(evt);
            }
        });
        getContentPane().add(CerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton15.setText("-");
        jButton15.setToolTipText("Minimizar");
        jButton15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 10, -1, -1));

        jButton16.setBackground(new java.awt.Color(169, 7, 6));
        jButton16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton16.setText("x");
        jButton16.setToolTipText("Cerrar");
        jButton16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 10, -1, -1));

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
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1350, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void clear_userformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_userformActionPerformed
        LimpiarDatosUser();
    }//GEN-LAST:event_clear_userformActionPerformed

    private void clear_orderformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_orderformActionPerformed
        LimpiarDatosProyecto();
    }//GEN-LAST:event_clear_orderformActionPerformed

    private void Tabla_UsuariosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_UsuariosMousePressed
        int fila = Tabla_Usuarios.getSelectedRow();
        try {
            cc = ConexionBD.getcon();
            String SQL = "SELECT * FROM usuarios WHERE id_usuario='" + Tabla_Usuarios.getValueAt(fila, 0) + "'";
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            rs.next();
            id_user.setText(rs.getString("id_usuario"));
            name_user.setText(rs.getString("nombre_usuario"));
            nombre.setText(rs.getString("nombre"));
            ape_p.setText(rs.getString("ape_pat"));
            ape_m.setText(rs.getString("ape_mat"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la selección de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_Tabla_UsuariosMousePressed

    private void Tabla_UsuariosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_UsuariosKeyReleased
        if ((evt.getKeyCode() == 38) || (evt.getKeyCode() == 40) || (evt.getKeyCode() == 33) || (evt.getKeyCode() == 34)) {
            int fila = Tabla_Usuarios.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "SELECT * FROM usuarios WHERE id_usuario='" + Tabla_Usuarios.getValueAt(fila, 0) + "'";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                id_user.setText(rs.getString("id_usuario"));
                name_user.setText(rs.getString("nombre_usuario"));
                nombre.setText(rs.getString("nombre"));
                ape_p.setText(rs.getString("ape_pat"));
                ape_m.setText(rs.getString("ape_mat"));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error en la selección de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_Tabla_UsuariosKeyReleased

    private void Tabla_ProyectosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_ProyectosMousePressed
        int fila = Tabla_Proyectos.getSelectedRow();
        try {
            cc = ConexionBD.getcon();
            String SQL = "SELECT id, PO_NO, line_amount FROM facturacion WHERE id='" + Tabla_Proyectos.getValueAt(fila, 0) + "'";
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            rs.next();
            id_line.setText(rs.getString("id"));
            po_no.setText(rs.getString("PO_NO"));
            total.setText(rs.getString("line_amount"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la selección de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_Tabla_ProyectosMousePressed

    private void Tabla_ProyectosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_ProyectosKeyReleased
        if ((evt.getKeyCode() == 38) || (evt.getKeyCode() == 40) || (evt.getKeyCode() == 33) || (evt.getKeyCode() == 34)) {
            int fila = Tabla_Proyectos.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "SELECT id, PO_NO FROM facturacion WHERE id='" + Tabla_Proyectos.getValueAt(fila, 0) + "'";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                id_line.setText(rs.getString("id"));
                po_no.setText(rs.getString("PO_NO"));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error en la selección de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_Tabla_ProyectosKeyReleased

    private void Tabla_AsignacionesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_AsignacionesMousePressed
        int fila = Tabla_Asignaciones.getSelectedRow();
        try {
            cc = ConexionBD.getcon();
            String SQL = "select asignaciones.id_asignacion, asignaciones.fecha_asignacion, \n"
                    + "asignaciones.id_usuario, usuarios.nombre_usuario, usuarios.nombre, \n"
                    + "usuarios.ape_pat, usuarios.ape_mat, asignaciones.id, facturacion.PO_NO, \n"
                    + "asignaciones.orden_compra_dt, asignaciones.importe, asignaciones.total_pagar, \n"
                    + "asignaciones.status_facturacion \n"
                    + "from asignaciones, facturacion, usuarios \n"
                    + "where id_asignacion='" + Tabla_Asignaciones.getValueAt(fila, 0) + "' \n"
                    + "&& asignaciones.id_usuario = usuarios.id_usuario && \n"
                    + "asignaciones.id = facturacion.id;";
            sent = cc.createStatement();
            rs = sent.executeQuery(SQL);
            rs.next();
            id_asignacion.setText(rs.getString("id_asignacion"));
            datestamp.setDate(rs.getTimestamp("fecha_asignacion"));
            id_user.setText(rs.getString("id_usuario"));
            name_user.setText(rs.getString("nombre_usuario"));
            nombre.setText(rs.getString("nombre"));
            ape_p.setText(rs.getString("ape_pat"));
            ape_m.setText(rs.getString("ape_mat"));
            id_line.setText(rs.getString("id"));
            po_no.setText(rs.getString("PO_NO"));
            orden_dt.setText(rs.getString("orden_compra_dt"));
            importe.setText(rs.getString("importe"));
            total.setText(rs.getString("total_pagar"));
            status_fact.setText(rs.getString("status_facturacion"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la selección de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_Tabla_AsignacionesMousePressed

    private void Tabla_AsignacionesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_AsignacionesKeyReleased
        if ((evt.getKeyCode() == 38) || (evt.getKeyCode() == 40) || (evt.getKeyCode() == 33) || (evt.getKeyCode() == 34)) {
            int fila = Tabla_Asignaciones.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "select asignaciones.id_asignacion, asignaciones.fecha_asignacion, \n"
                        + "asignaciones.id_usuario, usuarios.nombre_usuario, usuarios.nombre, \n"
                        + "usuarios.ape_pat, usuarios.ape_mat, asignaciones.id, facturacion.PO_NO, \n"
                        + "asignaciones.orden_compra_dt, asignaciones.importe, asignaciones.total_pagar, \n"
                        + "asignaciones.status_facturacion \n"
                        + "from asignaciones, facturacion, usuarios \n"
                        + "where id_asignacion='" + Tabla_Asignaciones.getValueAt(fila, 0) + "' \n"
                        + "&& asignaciones.id_usuario = usuarios.id_usuario && \n"
                        + "asignaciones.id = facturacion.id;";
                sent = cc.createStatement();
                rs = sent.executeQuery(SQL);
                rs.next();
                id_asignacion.setText(rs.getString("id_asignacion"));
                datestamp.setDate(rs.getTimestamp("fecha_asignacion"));
                id_user.setText(rs.getString("id_usuario"));
                name_user.setText(rs.getString("nombre_usuario"));
                nombre.setText(rs.getString("nombre"));
                ape_p.setText(rs.getString("ape_pat"));
                ape_m.setText(rs.getString("ape_mat"));
                id_line.setText(rs.getString("id"));
                po_no.setText(rs.getString("PO_NO"));
                orden_dt.setText(rs.getString("orden_compra_dt"));
                importe.setText(rs.getString("importe"));
                total.setText(rs.getString("total_pagar"));
                status_fact.setText(rs.getString("status_facturacion"));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error en la selección de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_Tabla_AsignacionesKeyReleased

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        BuscarIDProyecto();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        BuscarPO();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void update_asignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_asignacionActionPerformed
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosAsignacion()) {
                int fila2 = Tabla_Asignaciones.getSelectedRow();
                if (Tabla_Asignaciones.getValueAt(fila2, 4).equals(id_user.getText())) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un nuevo "
                            + "usuario", "Usted esta seleccionando al usuario ya asignado", JOptionPane.ERROR_MESSAGE);
                } else {
                    int fila = Tabla_Asignaciones.getSelectedRow();
                    String SQL = "insert into historial_asignaciones (id_asignacion,fecha_asignacion,"
                            + "id,id_usuario,orden_compra_dt,importe,total_pagar,status_facturacion)"
                            + " values(?,?,?,?,?,?,?,?)";
                    p = cc.prepareStatement(SQL);
                    p.setString(1, Tabla_Asignaciones.getValueAt(fila, 0).toString());
                    p.setString(2, Tabla_Asignaciones.getValueAt(fila, 1).toString());
                    p.setString(3, Tabla_Asignaciones.getValueAt(fila, 2).toString());
                    p.setString(4, Tabla_Asignaciones.getValueAt(fila, 4).toString());//id_usuario
                    p.setString(5, Tabla_Asignaciones.getValueAt(fila, 9).toString());
                    p.setString(6, Tabla_Asignaciones.getValueAt(fila, 10).toString());
                    p.setString(7, Tabla_Asignaciones.getValueAt(fila, 11).toString());
                    p.setString(8, Tabla_Asignaciones.getValueAt(fila, 12).toString());
                    int n = p.executeUpdate();
                    if (n > 0) {
                        //JOptionPane.showMessageDialog(null, "Datos Guardados");
                        ActualizarAsignacion();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos en el historial" + e.getMessage());
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_update_asignacionActionPerformed

    private void save_asignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_asignacionActionPerformed
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosAsignacion()) {
                //int fila Tabla_Proyectos.getSelectedRow();
                String SQL = "insert into asignaciones (id,id_usuario,total_pagar)"
                        + " values(?,?,?)";
                p = cc.prepareStatement(SQL);
                p.setString(1, id_line.getText());
                p.setString(2, id_user.getText());
                p.setString(3, total.getText());
                int n = p.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos Guardados");
                    ActualizarStatus();
                    LlenarTablaAsignaciones();
                    LimpiarDatosProyecto();
                    ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException t) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + t.getMessage() + " Este proyecto \n"
                    + "ya ha sido asignado", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(Asignaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_save_asignacionActionPerformed

    private void show_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show_allActionPerformed
        LlenarTablaAsignaciones();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_show_allActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        BuscarTablaProyectosID();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        BuscarTablaProyectosPO();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        BuscarIDAsignacion();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        BuscarTablaProyectosPOPublishDate();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void id_ASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_ASActionPerformed
        jButton6.doClick();
    }//GEN-LAST:event_id_ASActionPerformed

    private void ID_PRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ID_PRActionPerformed
        jButton9.doClick();
    }//GEN-LAST:event_ID_PRActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        dispose();
        AdministradorFacturacion AF = new AdministradorFacturacion();
        AF.setVisible(true);
        AdministradorFacturacion.ID_Usuario.setText(ID_Usuario_Facturas.getText());
        AF.Nombre_UsuarioAdmin.setText(Nombre_Usuario_Facturas.getText());
    }//GEN-LAST:event_jButton17ActionPerformed

    private void CerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarSesionActionPerformed
        dispose();
        Login log = new Login();
        log.setVisible(true);
    }//GEN-LAST:event_CerrarSesionActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void FondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_FondoMousePressed

    private void FondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_FondoMouseDragged

    private void Tabla_HistorialMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_HistorialMousePressed

    }//GEN-LAST:event_Tabla_HistorialMousePressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        BuscarTablaHistorialIDAsignacion();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        BuscarFechaAsignacionHistorial();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        BuscarIDUsuarioHistorial();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        BuscarIDProyecto();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void ID_USActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ID_USActionPerformed
        jButton8.doClick();
    }//GEN-LAST:event_ID_USActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        LlenarTablaHistorialAsignaciones();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LlenarTablaProyectos();
        ColumnasAutoajustadas(Tabla_Usuarios, Tabla_Proyectos, Tabla_Asignaciones, Tabla_Historial, margin);
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Asignaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Asignaciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Asignaciones;
    private javax.swing.JButton CerrarSesion;
    private com.toedter.calendar.JDateChooser Fecha_AS;
    private javax.swing.JLabel Fondo;
    private javax.swing.JPanel Historial;
    private javax.swing.JTextField ID_PR;
    private javax.swing.JTextField ID_US;
    public static javax.swing.JLabel ID_Usuario_Facturas;
    public javax.swing.JLabel Nombre_Usuario_Facturas;
    private javax.swing.JTable Tabla_Asignaciones;
    private javax.swing.JTable Tabla_Historial;
    private javax.swing.JTable Tabla_Proyectos;
    private javax.swing.JTable Tabla_Usuarios;
    private javax.swing.JTextField ape_m;
    private javax.swing.JTextField ape_p;
    private javax.swing.JButton clear_orderform;
    private javax.swing.JButton clear_userform;
    private com.toedter.calendar.JDateChooser datestamp;
    private javax.swing.JTextField id_AS;
    private javax.swing.JTextField id_asignacion;
    private javax.swing.JTextField id_line;
    private javax.swing.JTextField id_user;
    private javax.swing.JTextField importe;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JDateChooser jDateChooser1;
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
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField name_user;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField orden_dt;
    private javax.swing.JTextField po_no;
    private javax.swing.JButton save_asignacion;
    private javax.swing.JTextField search_PO;
    private com.toedter.calendar.JDateChooser search_dt;
    private javax.swing.JTextField search_id;
    private javax.swing.JTextField search_idasign;
    private javax.swing.JButton show_all;
    private javax.swing.JTextField status_fact;
    private javax.swing.JTextField total;
    private javax.swing.JButton update_asignacion;
    // End of variables declaration//GEN-END:variables
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
}
