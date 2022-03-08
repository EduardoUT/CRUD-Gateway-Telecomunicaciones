/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImportarExcelPrueba;

import VentanasAdministradores.*;
import Conexion.ConexionBD;
import java.awt.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author EDOMEX
 */
public final class AdministradorFacturacion extends javax.swing.JFrame {

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

    public AdministradorFacturacion() {
        initComponents();
        LlenarTabla();
        setBackground(new Color(0, 0, 0, 0));
        ColumnasAutoajustadas(Tabla_Facturacion, margin);
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

    public void Leer() throws IOException, InvalidFormatException, SQLException {
        /*
        Creamos el Objeto SeleccionArchivo Instanciando la posición del
        JFileChooser en la ubicación raíz del proyecto en Java con ".", donde se abrirá
        la ventana que nos permitirá seleccionar el archivo deseado.
         */
        JFileChooser SeleccionArchivo = new JFileChooser(".");
        /*
        Este método solo se configuró para la extracción de información en hojas de Excel
        en formato .xlsx sustraidas por la plataforma de Huawei.
         */
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos Excel (.xlsx)", "xlsx");
        SeleccionArchivo.setFileFilter(filtro);
        int valor = SeleccionArchivo.showOpenDialog(SeleccionArchivo);
        /*
        Comprobamos que al abrir la ventana JFileChooser el archivo sea seleccionado con una sentencia if,
        de lo contrario si el usuario da click en Cancelar o Cerrar la Ventana, el sistema arroja el mensaje
        Ningún Fichero Seleccionado.
         */
        if (valor == JFileChooser.APPROVE_OPTION) {
            String ruta = SeleccionArchivo.getSelectedFile().getAbsolutePath();
            //Esta Excepción se muestra cuando el Archivo no fue encontrado
            try {
                //Realizamos la creación de una nueva ruta obteniendo el archivo seleccionado
                File f = new File(ruta);
                //Llamamos XSSFWorkbook para indicarle que archivo usar
                XSSFWorkbook wb = new XSSFWorkbook(f);
                //Seleccionamos el número de hoja de Excel donde el código buscará info
                //En este caso será la hoja 1 <--(0)(En Java) del archivo de Excel.
                XSSFSheet sheet = wb.getSheetAt(0);
                //Obtenemos el número de filas existentes en la Hoja de Excel
                int numFilas = sheet.getLastRowNum();
                /*
                Esta Excepción se ejecuta a fin de poder verificar los errores o advertencias en SQL
                al guardar los datos.
                 */
                try {
                    /*
                    Abrimos la conexión con la BD.
                     */
                    cc = ConexionBD.getcon();
                    String SQL = "insert into facturacion (id,project_code,project_name,customer"
                            + ",PO_status,PO_NO,PO_Line_NO,shipment_NO,site_code,site_name,item_code,item_desc"
                            + ",requested_qty,due_qty,billed_qty,unit_price,line_amount,unit,payment_terms"
                            + ",category,bidding_area,publish_date)"
                            + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    p = cc.prepareStatement(SQL);
                    /*
                        Realizamos una sentencia for a fin de poder realizar un recorrido através
                        de las filas existentes en el Archivo, asignación de parámetros y asignación de valores.
                     */
                    for (int x = 1; x <= numFilas; x++) {
                        //Obtenemos la fila de la hoja de Excel en cada recorrido
                        Row fila = sheet.getRow(x);
                        /*
                            Los siguientes SOUT permiten tener un control de la posición en la que asignamos
                            el valor de la columna y la fila con sus respectivos encabezados.
                            Similar a un arreglo se respeta la posición de la Columna a adaptar
                            El primer campo de la Hoja de Excel se encuentra en la posición 1 y es el ID
                            en Java se coloca la posición 0, y respectivamente el siguiente dato
                            La posición se obtiene con fila.getCell(numero de Columna);
                         */
                        System.out.println("0 " + fila.getCell(0) + " ID");
                        System.out.println("5 " + fila.getCell(5) + " Project Code");
                        System.out.println("4 " + fila.getCell(4) + " Project Name");
                        System.out.println("3 " + fila.getCell(3) + " Customer");
                        System.out.println("10 " + fila.getCell(10) + " PO Status");
                        System.out.println("11 " + fila.getCell(11) + " PO_NO");
                        System.out.println("12 " + fila.getCell(12) + " PO_Line_NO");
                        System.out.println("13 " + fila.getCell(13) + " shipment_NO");
                        System.out.println("14 " + fila.getCell(14) + " site_code");
                        System.out.println("16 " + fila.getCell(16) + " site_name");
                        System.out.println("17 " + fila.getCell(17) + " item_code");
                        System.out.println("18 " + fila.getCell(18) + " item_desc");
                        System.out.println("19 " + fila.getCell(19) + " requested_qty");
                        System.out.println("20 " + fila.getCell(20) + " due_qty");
                        System.out.println("21 " + fila.getCell(21) + " billed_qty");
                        System.out.println("22 " + fila.getCell(22) + " unit_price");
                        System.out.println("23 " + fila.getCell(23) + " line_amount");
                        System.out.println("24 " + fila.getCell(24) + " unit");
                        System.out.println("27 " + fila.getCell(27) + " payment_terms");
                        System.out.println("34 " + fila.getCell(34) + " category");
                        System.out.println("37 " + fila.getCell(37) + " bidding_area");
                        System.out.println("41 " + fila.getCell(41) + " publish_date");
                        /*
                            Los siguientes parametros String permiten realizar el procesamiento
                            de datos que varían de ser numéricos o String en una fila de Excel
                            de esta manera a el caso de ID lo declaramos de tipo String para luego
                            utilizar esta misma variable en la inserción, con la propiedad Cell.CELL_TYPE_STRING.
                            en Cell celdaID = sheet.getRow(x).getCell(0)<-- Indicamos la posición de la Columna a la
                            que queremos instanciar para asignarle dicha propiedad en la siguiente linea.
                         */
                        Cell celdaID = sheet.getRow(x).getCell(0);
                        celdaID.setCellType(CellType.STRING);
                        String ID;
                        ID = celdaID.getStringCellValue();

                        Cell celdaProject_Code = sheet.getRow(x).getCell(5);
                        celdaProject_Code.setCellType(CellType.STRING);
                        String Project_Code;
                        Project_Code = celdaProject_Code.getStringCellValue();
                        /*
                            Como en los datos sustraídos del Sitio Web de Huawei generá o se obtienen
                            datos de tipo null o vacíos (Celdas en Blanco en Excel), las variables String
                            se les asignó la propiedad de CREATE_NULL_AS_BLANK, una vez asignada esta propiedad
                            se declara una sentencia if que permitirá evaluar al momento de ejecutar la inserción
                            si esa celda esta vacía entonces se le asigna a la variable String un valor Default
                            'Undefined', que de igual forma se declaró un tipo Default en la creación de la BD en la Tabla
                            facturacion, para que no este vacío del todo en la BD como en la Hoja de Excel.
                         */
                        String Item_Desc;
                        Item_Desc = fila.getCell(18, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                        if (Item_Desc.equals("")) {
                            Item_Desc = "Undefined";
                        }
                        String Bidding_Area;
                        Bidding_Area = fila.getCell(37, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                        if (Bidding_Area.equals("")) {
                            Bidding_Area = "Undefined";
                        }
                        String Project_Name;
                        Project_Name = fila.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                        if (Project_Name.equals("")) {
                            Project_Name = "Undefined";
                        }
                        /*
                            En este caso como el valor en la BD es de tipo int, a excepción de los
                            anteriores, se le asignó un 0.
                         */
                        String Item_Code;
                        Item_Code = fila.getCell(17, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                        if (Item_Code.equals("")) {
                            Item_Code = "0";
                        }
                        /*
                            En esta sección se hizó uso de las  variables de tipo String
                            anteriormente validados, ya que recurrentemente aparecían vacios.
                            En caso de futuros errores solo validar el campo que aparece en null
                            Solo se tomó en cuenta los que más recurrentemente aparecían como vacíos
                         */
                        p.setString(1, ID);
                        p.setString(2, Project_Code);
                        p.setString(3, Project_Name);
                        p.setString(4, fila.getCell(3).getStringCellValue());
                        p.setString(5, fila.getCell(10).getStringCellValue());
                        p.setString(6, fila.getCell(11).getStringCellValue());
                        p.setInt(7, (int) fila.getCell(12).getNumericCellValue());
                        p.setInt(8, (int) fila.getCell(13).getNumericCellValue());
                        p.setString(9, fila.getCell(14).getStringCellValue());
                        p.setString(10, fila.getCell(16).getStringCellValue());
                        p.setString(11, Item_Code);
                        p.setString(12, Item_Desc);
                        p.setInt(13, (int) fila.getCell(19).getNumericCellValue());
                        p.setInt(14, (int) fila.getCell(20).getNumericCellValue());
                        p.setInt(15, (int) fila.getCell(21).getNumericCellValue());
                        p.setInt(16, (int) fila.getCell(22).getNumericCellValue());
                        p.setInt(17, (int) fila.getCell(23).getNumericCellValue());
                        p.setString(18, fila.getCell(24).getStringCellValue());
                        p.setString(19, fila.getCell(27).getStringCellValue());
                        p.setString(20, fila.getCell(34).getStringCellValue());
                        p.setString(21, Bidding_Area);
                        p.setString(22, fila.getCell(41).getStringCellValue());
                        /*La siguiente línea registra de forma indirecta los registros evaluados a través del ciclo for
                            por lo que se hizó uso de p.addBatch(), para registrar todos los valores dentro de esa sentencia 
                            para posteriormente insertarlos por Lotes en la Base de datos con p.executeBatch().
                            Esto con la finalidad de agilizar el proceso de inserción de muchos datos.
                         */
                        p.addBatch();
                    }
                    p.executeBatch();
                    JOptionPane.showMessageDialog(null, "Se ha guardado con exito " + numFilas + " datos en la BD.");
                    LlenarTabla();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Esta hoja ya ha sido ingresada a la BD" + e.getMessage());
                } finally {
                    if (p != null) {
                        try {
                            p.close();
                            p = null;
                        } catch (SQLException ex) {
                            Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (cc != null) {
                        try {
                            cc.close();
                            cc = null;
                        } catch (SQLException ex) {
                            Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, e);
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun archivo");
        }
    }

    int vColIndex = 1;
    int margin = 2;

    public void ColumnasAutoajustadas(JTable Tabla_Facturacion, int margin) {
        for (int c = 0; c < Tabla_Facturacion.getColumnCount(); c++) {
            packColumnTablaFacturas(Tabla_Facturacion, c, 2);
        }
    }

    public void packColumnTablaFacturas(JTable Tabla_Facturacion, int vColIndex, int margin) {
        //model1 = (DefaultTableModel) jTable1.getModel();
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) Tabla_Facturacion.getColumnModel();

        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = Tabla_Facturacion.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(Tabla_Facturacion, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < Tabla_Facturacion.getRowCount(); r++) {
            renderer = Tabla_Facturacion.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(Tabla_Facturacion, Tabla_Facturacion.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public boolean DatosLlenosFacturacion() {

        return !id.getText().equals("") && !pjcode.getText().equals("")
                && !pjname.getText().equals("") && !customer.getText().equals("")
                && !postatus.getText().equals("") && !pon.getText().equals("") && !poline.getText().equals("")
                && !shipment.getText().equals("") && !sitecode.getText().equals("")
                && !sitename.getText().equals("") && !itemcode.getText().equals("")
                && !itemdsc.getText().equals("") && !requestedqty.getText().equals("")
                && !dueqty.getText().equals("") && !billedqty.getText().equals("")
                && !unitprice.getText().equals("") && !amount.getText().equals("")
                && !unit.getText().equals("") && !payment.getText().equals("")
                && !category.getText().equals("") && !bidding.getText().equals("")
                && !pdate.getDateFormatString().equals("");
    }

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
        pdate.setCalendar(null);
    }

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
                    + "where asignaciones.id_usuario = usuarios.id_usuario && facturacion.id = asignaciones.id";
            //tabla1.setModel(model);
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
            Tabla_Facturacion.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Llenar Tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void Buscar() {
        String buscar = buscarPO.getText();
        String fechaPO = ((JTextField) Fecha_PO.getDateEditor().getUiComponent()).getText();
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
                    + "where facturacion.PO_NO ='" + buscar + "' && facturacion.publish_date like '%" + fechaPO + "%' && asignaciones.id_usuario =usuarios.id_usuario && facturacion.id = asignaciones.id";
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
            Tabla_Facturacion.setModel(model1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al buscar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new FondoPanelTitulo();
        jLabel28 = new javax.swing.JLabel();
        Nombre_UsuarioAdmin = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        ID_Usuario = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new FondoPanelesCentrales();
        jLabel31 = new javax.swing.JLabel();
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
        jPanel3 = new FondoPanelesCentrales();
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
        jPanel4 = new FondoPanelesCentrales();
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
        pdate = new com.toedter.calendar.JDateChooser();
        TipBusqueda = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        buscarPO = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        Tabla_Facturacion = new javax.swing.JTable();
        jPanel7 = new FondoPanelesCentrales();
        jLabel24 = new javax.swing.JLabel();
        numordenc = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        contratista = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        ordencompradt = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        importe = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        stat = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        stat_cierre = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        ImportarExcel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        PegarRegistros = new javax.swing.JButton();
        GestionFacturas = new javax.swing.JButton();
        GestionAsignaciones = new javax.swing.JButton();
        Fecha_PO = new com.toedter.calendar.JDateChooser();
        Fondo = new FondoPanelPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setIconImage(getIconImage());
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Bienvenido(a):");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        Nombre_UsuarioAdmin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Nombre_UsuarioAdmin.setForeground(new java.awt.Color(255, 255, 255));
        Nombre_UsuarioAdmin.setText("Nombre");
        jPanel1.add(Nombre_UsuarioAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jLabel33.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("ID:");
        jPanel1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        ID_Usuario.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ID_Usuario.setForeground(new java.awt.Color(255, 255, 255));
        ID_Usuario.setText("ID");
        jPanel1.add(ID_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("-");
        jButton2.setToolTipText("Minimizar");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 10, -1, -1));

        jButton3.setBackground(new java.awt.Color(169, 7, 6));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setText("x");
        jButton3.setToolTipText("Cerrar");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Administrador Facturación");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, -1));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 1", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(316, 338));
        jPanel2.setMinimumSize(new java.awt.Dimension(316, 338));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("ID:");
        jPanel2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 29, -1, -1));

        id.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        id.setToolTipText("ID");
        id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idKeyTyped(evt);
            }
        });
        jPanel2.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 27, 202, -1));

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Project Code:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 60, -1, -1));

        pjcode.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        pjcode.setToolTipText("Project Code");
        jPanel2.add(pjcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 58, 202, -1));

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Project Name:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 89, -1, -1));

        pjname.setColumns(20);
        pjname.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        pjname.setLineWrap(true);
        pjname.setRows(3);
        pjname.setToolTipText("Project Name");
        jScrollPane3.setViewportView(pjname);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 89, 202, -1));

        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Customer:\t");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 148, -1, -1));

        customer.setColumns(20);
        customer.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        customer.setLineWrap(true);
        customer.setRows(3);
        customer.setToolTipText("Customer");
        jScrollPane1.setViewportView(customer);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 148, 202, -1));

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PO Status:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 209, -1, -1));

        postatus.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        postatus.setToolTipText("PO Status");
        postatus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                postatusKeyTyped(evt);
            }
        });
        jPanel2.add(postatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 207, 202, -1));

        jLabel7.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("PO NO:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 240, -1, -1));

        pon.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        pon.setToolTipText("PO NO");
        jPanel2.add(pon, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 238, 202, -1));

        jLabel9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Shipment NO:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 302, -1, -1));

        shipment.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        shipment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                shipmentKeyTyped(evt);
            }
        });
        jPanel2.add(shipment, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 300, 202, -1));

        poline.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        poline.setToolTipText("PO Line NO");
        poline.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                polineKeyTyped(evt);
            }
        });
        jPanel2.add(poline, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 269, 202, -1));

        jLabel30.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("PO Line NO:");
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 271, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 330, 380));

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 2", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setMaximumSize(new java.awt.Dimension(317, 338));
        jPanel3.setMinimumSize(new java.awt.Dimension(317, 338));
        jPanel3.setPreferredSize(new java.awt.Dimension(317, 338));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Site Code:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 27, -1, -1));

        sitecode.setColumns(20);
        sitecode.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        sitecode.setLineWrap(true);
        sitecode.setRows(3);
        jScrollPane2.setViewportView(sitecode);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 27, 202, -1));

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Site Name:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 86, -1, -1));

        sitename.setColumns(20);
        sitename.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        sitename.setLineWrap(true);
        sitename.setRows(3);
        jScrollPane4.setViewportView(sitename);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 86, 202, -1));

        jLabel11.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Item Code:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 147, -1, -1));

        itemcode.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        itemcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                itemcodeKeyTyped(evt);
            }
        });
        jPanel3.add(itemcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 145, 202, -1));

        jLabel12.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Item Desc:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 176, -1, -1));

        itemdsc.setColumns(20);
        itemdsc.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        itemdsc.setLineWrap(true);
        itemdsc.setRows(3);
        itemdsc.setText("\n");
        jScrollPane5.setViewportView(itemdsc);

        jPanel3.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 176, 202, 57));

        jLabel14.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Requested Qty:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 246, -1, -1));

        requestedqty.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        requestedqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                requestedqtyKeyTyped(evt);
            }
        });
        jPanel3.add(requestedqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 244, 202, -1));

        jLabel13.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Due Qty:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 277, -1, -1));

        dueqty.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        dueqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dueqtyKeyTyped(evt);
            }
        });
        jPanel3.add(dueqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 275, 202, -1));

        jLabel15.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Billed Qty:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 308, -1, -1));

        billedqty.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        billedqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billedqtyKeyTyped(evt);
            }
        });
        jPanel3.add(billedqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 306, 202, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 330, 380));

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 3", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(326, 336));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Unit Price:");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 29, -1, -1));

        unitprice.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        unitprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                unitpriceKeyTyped(evt);
            }
        });
        jPanel4.add(unitprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 27, 202, -1));

        jLabel17.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Line Amount:");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 60, -1, -1));

        amount.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        amount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                amountKeyTyped(evt);
            }
        });
        jPanel4.add(amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 58, 202, -1));

        jLabel19.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Unit:");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(89, 91, -1, -1));

        unit.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jPanel4.add(unit, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 89, 202, -1));

        jLabel18.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Payment Terms:");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 90, -1));

        payment.setColumns(20);
        payment.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        payment.setLineWrap(true);
        payment.setRows(3);
        payment.setText("\n");
        jScrollPane6.setViewportView(payment);

        jPanel4.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 120, 202, 57));

        jLabel20.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Category:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 188, -1, -1));

        category.setColumns(20);
        category.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        category.setLineWrap(true);
        category.setRows(3);
        category.setText("\n");
        jScrollPane7.setViewportView(category);

        jPanel4.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 188, 202, 57));

        jLabel21.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Bidding Area:");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 256, -1, -1));

        bidding.setColumns(20);
        bidding.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        bidding.setLineWrap(true);
        bidding.setRows(3);
        bidding.setText("\n");
        jScrollPane8.setViewportView(bidding);

        jPanel4.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 256, 202, 57));

        jLabel22.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Publish Date:");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 324, -1, -1));

        pdate.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        pdate.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        pdate.setMaxSelectableDate(new java.util.Date(253370790095000L));
        pdate.setOpaque(false);
        jPanel4.add(pdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 319, 201, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 340, 380));

        TipBusqueda.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        TipBusqueda.setForeground(new java.awt.Color(255, 255, 255));
        TipBusqueda.setText("Busque el registro deseado en la tabla aquí:");
        getContentPane().add(TipBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 450, -1, -1));

        save.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        save.setText("GUARDAR");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        getContentPane().add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        update.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        update.setText("ACTUALIZAR");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        getContentPane().add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, -1, -1));

        delete.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        delete.setText("BORRAR");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        getContentPane().add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 470, -1, -1));

        jButton1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jButton1.setText("BUSCAR P.O:");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 470, -1, -1));

        buscarPO.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        getContentPane().add(buscarPO, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 470, 238, -1));

        jButton5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jButton5.setText("Cerrar Sesión");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 470, -1, -1));

        Tabla_Facturacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Tabla_Facturacion.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Tabla_Facturacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla_Facturacion.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Tabla_Facturacion.setColumnSelectionAllowed(true);
        Tabla_Facturacion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Tabla_Facturacion.setGridColor(new java.awt.Color(0, 0, 0));
        Tabla_Facturacion.setSelectionBackground(new java.awt.Color(0, 153, 204));
        Tabla_Facturacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Tabla_FacturacionMousePressed(evt);
            }
        });
        Tabla_Facturacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_FacturacionKeyReleased(evt);
            }
        });
        jScrollPane9.setViewportView(Tabla_Facturacion);

        getContentPane().add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 1309, 170));

        jPanel7.setBackground(new java.awt.Color(0, 153, 153));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 4", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("No. Orden de Compra:");
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        numordenc.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        numordenc.setEnabled(false);
        jPanel7.add(numordenc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 248, -1));

        jLabel23.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Nombre Contratista:");
        jPanel7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        contratista.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        contratista.setEnabled(false);
        jPanel7.add(contratista, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 248, -1));

        jLabel25.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Orden de Compra DT:");
        jPanel7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        ordencompradt.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ordencompradt.setEnabled(false);
        jPanel7.add(ordencompradt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 248, -1));

        jLabel26.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Importe:");
        jPanel7.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        importe.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        importe.setEnabled(false);
        jPanel7.add(importe, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 248, -1));

        jLabel27.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Total:");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        total.setEditable(false);
        total.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        total.setEnabled(false);
        jPanel7.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 167, -1));

        jLabel29.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Status:");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        stat.setEditable(false);
        stat.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jPanel7.add(stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 167, -1));

        jLabel38.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Status Cierre:");
        jPanel7.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("$");
        jPanel7.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 260, -1, -1));

        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("%");
        jPanel7.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, -1, -1));

        stat_cierre.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        stat_cierre.setEnabled(false);
        stat_cierre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stat_cierreKeyTyped(evt);
            }
        });
        jPanel7.add(stat_cierre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 210, -1));

        jLabel35.setText("jLabel35");
        jPanel7.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, -1, -1));

        jLabel36.setText("jLabel36");
        jPanel7.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 16, -1, -1));

        jLabel37.setText("jLabel37");
        jPanel7.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 16, -1, -1));

        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("%");
        jPanel7.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, -1, -1));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1038, 70, 280, 380));

        ImportarExcel.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        ImportarExcel.setText("IMPORTAR ARCHIVO EXCEL");
        ImportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportarExcelActionPerformed(evt);
            }
        });
        getContentPane().add(ImportarExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 470, -1, -1));

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Solo formato (.xlsx)");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 450, -1, -1));

        PegarRegistros.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        PegarRegistros.setText("PEGAR REGISTROS EXCEL");
        PegarRegistros.setMaximumSize(new java.awt.Dimension(131, 23));
        PegarRegistros.setMinimumSize(new java.awt.Dimension(131, 23));
        PegarRegistros.setPreferredSize(new java.awt.Dimension(193, 23));
        PegarRegistros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PegarRegistrosActionPerformed(evt);
            }
        });
        getContentPane().add(PegarRegistros, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 500, 190, -1));

        GestionFacturas.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        GestionFacturas.setText("GESTIONAR FACTURAS");
        GestionFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GestionFacturasActionPerformed(evt);
            }
        });
        getContentPane().add(GestionFacturas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 470, -1, -1));

        GestionAsignaciones.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        GestionAsignaciones.setText("GESTIONAR ASIGNACIONES");
        GestionAsignaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GestionAsignacionesActionPerformed(evt);
            }
        });
        getContentPane().add(GestionAsignaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 500, -1, -1));

        Fecha_PO.setDateFormatString("yyyy/MM/dd");
        Fecha_PO.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Fecha_PO.setOpaque(false);
        getContentPane().add(Fecha_PO, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 500, 160, -1));

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
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, 710));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentResized

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Buscar();
        ColumnasAutoajustadas(Tabla_Facturacion, margin);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosFacturacion()) {
                int fila = Tabla_Facturacion.getSelectedRow();
                String dato = (String) Tabla_Facturacion.getValueAt(fila, 0);
                String SQL = "update facturacion set id=?,project_code=?,project_name=?,customer=?,PO_status=?,PO_NO=?,PO_Line_NO=?,shipment_NO=?,site_code=?,"
                        + "site_name=?,item_code=?,item_desc=?,requested_qty=?,due_qty=?,billed_qty=?,unit_price=?,line_amount=?,"
                        + "unit=?,payment_terms=?,category=?,bidding_area=?,publish_date=? where id=?";
                p = cc.prepareStatement(SQL);
                p.setString(1, id.getText());
                p.setString(2, pjcode.getText());
                p.setString(3, pjname.getText());
                p.setString(4, customer.getText());
                p.setString(5, postatus.getText());
                p.setString(6, pon.getText());
                p.setString(7, poline.getText());
                p.setString(8, shipment.getText());
                p.setString(9, sitecode.getText());
                p.setString(10, sitename.getText());
                p.setString(11, itemcode.getText());
                p.setString(12, itemdsc.getText());
                p.setString(13, requestedqty.getText());
                p.setString(14, dueqty.getText());
                p.setString(15, billedqty.getText());
                p.setString(16, unitprice.getText());
                p.setString(17, amount.getText());
                p.setString(18, unit.getText());
                p.setString(19, payment.getText());
                p.setString(20, category.getText());
                p.setString(21, bidding.getText());
                p.setString(22, ((JTextField) pdate.getDateEditor().getUiComponent()).getText());
                p.setString(23, dato);
                int n = p.executeUpdate();
                if (n > 0) {
                    LlenarTabla();
                    LimpiarDatos();
                    JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
                    id.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_updateActionPerformed

    private void idKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_idKeyTyped

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosFacturacion()) {
                int fila = Tabla_Facturacion.getSelectedRow();
                String SQL = "delete from facturacion where id='" + Tabla_Facturacion.getValueAt(fila, 0) + "'";
                sent = cc.createStatement();
                int n = sent.executeUpdate(SQL);
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos Eliminados Correctamente");
                    LimpiarDatos();
                    LlenarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        dispose();
        Login log = new Login();
        log.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void Tabla_FacturacionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_FacturacionMousePressed
        int fila = Tabla_Facturacion.getSelectedRow();
        try {
            cc = ConexionBD.getcon();
            String SQL = "select facturacion.id,facturacion.project_code,facturacion.project_name,facturacion.customer,facturacion.PO_status,facturacion.PO_NO,\n"
                    + "facturacion.PO_Line_NO,facturacion.shipment_NO,facturacion.site_code,facturacion.site_name,facturacion.item_code,facturacion.item_desc,\n"
                    + "facturacion.requested_qty,facturacion.due_qty,facturacion.billed_qty,facturacion.unit_price,facturacion.line_amount,facturacion.unit,\n"
                    + "facturacion.payment_terms,facturacion.category,facturacion.bidding_area,facturacion.publish_date,facturacion.status_cierre,\n"
                    + "asignaciones.id_usuario,usuarios.nombre,usuarios.ape_pat,usuarios.ape_mat,asignaciones.orden_compra_dt,asignaciones.importe,\n"
                    + "asignaciones.total_pagar,asignaciones.status_facturacion \n"
                    + "from facturacion, asignaciones, usuarios\n"
                    + "where facturacion.id =" + Tabla_Facturacion.getValueAt(fila, 0) + " && asignaciones.id_usuario = usuarios.id_usuario && facturacion.id = asignaciones.id";
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
            pdate.setDate(rs.getTimestamp("publish_date"));
            stat_cierre.setText(rs.getString("status_cierre"));
            jLabel35.setText(rs.getString("nombre") + " ");
            jLabel36.setText(rs.getString("ape_pat") + " ");
            jLabel37.setText(rs.getString("ape_mat"));
            contratista.setText(jLabel35.getText().concat(jLabel36.getText()).concat(jLabel37.getText()));
            ordencompradt.setText(rs.getString("orden_compra_dt"));
            importe.setText(rs.getString("importe"));
            total.setText(rs.getString("total_pagar"));
            stat.setText(rs.getString("status_facturacion"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la selección de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sent != null) {
                try {
                    sent.close();
                    sent = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_Tabla_FacturacionMousePressed

    private void Tabla_FacturacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_FacturacionKeyReleased
        if ((evt.getKeyCode() == 38) || (evt.getKeyCode() == 40) || (evt.getKeyCode() == 33) || (evt.getKeyCode() == 34)) {
            int fila = Tabla_Facturacion.getSelectedRow();
            try {
                cc = ConexionBD.getcon();
                String SQL = "select facturacion.id,facturacion.project_code,facturacion.project_name,facturacion.customer,facturacion.PO_status,facturacion.PO_NO,\n"
                        + "facturacion.PO_Line_NO,facturacion.shipment_NO,facturacion.site_code,facturacion.site_name,facturacion.item_code,facturacion.item_desc,\n"
                        + "facturacion.requested_qty,facturacion.due_qty,facturacion.billed_qty,facturacion.unit_price,facturacion.line_amount,facturacion.unit,\n"
                        + "facturacion.payment_terms,facturacion.category,facturacion.bidding_area,facturacion.publish_date,facturacion.status_cierre,\n"
                        + "asignaciones.id_usuario,usuarios.nombre,usuarios.ape_pat,usuarios.ape_mat,asignaciones.orden_compra_dt,asignaciones.importe,\n"
                        + "asignaciones.total_pagar,asignaciones.status_facturacion \n"
                        + "from facturacion, asignaciones, usuarios\n"
                        + "where facturacion.id =" + Tabla_Facturacion.getValueAt(fila, 0) + " && asignaciones.id_usuario = usuarios.id_usuario && facturacion.id = asignaciones.id";
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
                pdate.setDate(rs.getDate("publish_date"));
                stat_cierre.setText(rs.getString("status_cierre"));
                jLabel35.setText(rs.getString("nombre") + " ");
                jLabel36.setText(rs.getString("ape_pat") + " ");
                jLabel37.setText(rs.getString("ape_mat"));
                contratista.setText(jLabel35.getText().concat(jLabel36.getText()).concat(jLabel37.getText()));
                ordencompradt.setText(rs.getString("orden_compra_dt"));
                importe.setText(rs.getString("importe"));
                total.setText(rs.getString("total_pagar"));
                stat.setText(rs.getString("status_facturacion"));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error en la selección de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                        rs = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (sent != null) {
                    try {
                        sent.close();
                        sent = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (cc != null) {
                    try {
                        cc.close();
                        cc = null;
                    } catch (SQLException ex) {
                        Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_Tabla_FacturacionKeyReleased

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosFacturacion()) {
                String SQL = "insert into facturacion (id,project_code,project_name,customer"
                        + ",PO_status,PO_NO,PO_Line_NO,shipment_NO,site_code,site_name,item_code,item_desc"
                        + ",requested_qty,due_qty,billed_qty,unit_price,line_amount,unit,payment_terms"
                        + ",category,bidding_area,publish_date)"
                        + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                p = cc.prepareStatement(SQL);
                p.setString(1, id.getText());
                p.setString(2, pjcode.getText());
                p.setString(3, pjname.getText());
                p.setString(4, customer.getText());
                p.setString(5, postatus.getText());
                p.setString(6, pon.getText());
                p.setString(7, poline.getText());
                p.setString(8, shipment.getText());
                p.setString(9, sitecode.getText());
                p.setString(10, sitename.getText());
                p.setString(11, itemcode.getText());
                p.setString(12, itemdsc.getText());
                p.setString(13, requestedqty.getText());
                p.setString(14, dueqty.getText());
                p.setString(15, billedqty.getText());
                p.setString(16, unitprice.getText());
                p.setString(17, amount.getText());
                p.setString(18, unit.getText());
                p.setString(19, payment.getText());
                p.setString(20, category.getText());
                p.setString(21, bidding.getText());
                p.setString(22, ((JTextField) pdate.getDateEditor().getUiComponent()).getText());
                int n = p.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos Guardados");
                    LlenarTabla();
                    LimpiarDatos();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_saveActionPerformed

    private void FondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_FondoMouseDragged

    private void FondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_FondoMousePressed

    private void stat_cierreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stat_cierreKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car != '.')) {
            evt.consume();
        }
    }//GEN-LAST:event_stat_cierreKeyTyped

    private void ImportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportarExcelActionPerformed
        try {
            Leer();
        } catch (IOException | InvalidFormatException | SQLException ex) {
            Logger.getLogger(AdministradorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ImportarExcelActionPerformed

    private void PegarRegistrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PegarRegistrosActionPerformed
        dispose();
        insertar in = new insertar();
        in.setVisible(true);
        in.Nombre_Usuario_Facturas.setText(Nombre_UsuarioAdmin.getText());
        VentanasOperadores.Facturas.ID_Usuario_Facturas.setText(ID_Usuario.getText());
    }//GEN-LAST:event_PegarRegistrosActionPerformed

    private void GestionFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GestionFacturasActionPerformed
        dispose();
        VentanasOperadores.Facturas F = new VentanasOperadores.Facturas();
        F.setVisible(true);
        F.Nombre_Usuario_Facturas.setText(Nombre_UsuarioAdmin.getText());
        VentanasOperadores.Facturas.ID_Usuario_Facturas.setText(ID_Usuario.getText());
        F.LlenarTablaFacturas();
        F.ColumnasAutoajustadas(VentanasOperadores.Facturas.Tabla_Facturas, HEIGHT);
    }//GEN-LAST:event_GestionFacturasActionPerformed

    private void GestionAsignacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GestionAsignacionesActionPerformed
        dispose();
        Asignaciones log = new Asignaciones();
        log.setVisible(true);
        log.Nombre_Usuario_Facturas.setText(Nombre_UsuarioAdmin.getText());
        Asignaciones.ID_Usuario_Facturas.setText(ID_Usuario.getText());
    }//GEN-LAST:event_GestionAsignacionesActionPerformed

    private void postatusKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_postatusKeyTyped
        char car = evt.getKeyChar();
        if ((car < 'A' || car > 'Z')) {
            evt.consume();
        }
    }//GEN-LAST:event_postatusKeyTyped

    private void polineKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_polineKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_polineKeyTyped

    private void shipmentKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_shipmentKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_shipmentKeyTyped

    private void itemcodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemcodeKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_itemcodeKeyTyped

    private void requestedqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_requestedqtyKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_requestedqtyKeyTyped

    private void dueqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dueqtyKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_dueqtyKeyTyped

    private void billedqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billedqtyKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_billedqtyKeyTyped

    private void unitpriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_unitpriceKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car != '.')) {
            evt.consume();
        }
    }//GEN-LAST:event_unitpriceKeyTyped

    private void amountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car != '.')) {
            evt.consume();
        }
    }//GEN-LAST:event_amountKeyTyped

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
            java.util.logging.Logger.getLogger(AdministradorFacturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministradorFacturacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Fecha_PO;
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton GestionAsignaciones;
    private javax.swing.JButton GestionFacturas;
    public static javax.swing.JLabel ID_Usuario;
    private javax.swing.JButton ImportarExcel;
    public javax.swing.JLabel Nombre_UsuarioAdmin;
    private javax.swing.JButton PegarRegistros;
    private javax.swing.JTable Tabla_Facturacion;
    private javax.swing.JLabel TipBusqueda;
    private javax.swing.JTextField amount;
    private javax.swing.JTextArea bidding;
    private javax.swing.JTextField billedqty;
    private javax.swing.JTextField buscarPO;
    private javax.swing.JTextArea category;
    private javax.swing.JTextField contratista;
    private javax.swing.JTextArea customer;
    private javax.swing.JButton delete;
    private javax.swing.JTextField dueqty;
    private javax.swing.JTextField id;
    private javax.swing.JTextField importe;
    private javax.swing.JTextField itemcode;
    private javax.swing.JTextArea itemdsc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField numordenc;
    private javax.swing.JTextField ordencompradt;
    private javax.swing.JTextArea payment;
    private com.toedter.calendar.JDateChooser pdate;
    private javax.swing.JTextField pjcode;
    private javax.swing.JTextArea pjname;
    private javax.swing.JTextField poline;
    private javax.swing.JTextField pon;
    private javax.swing.JTextField postatus;
    private javax.swing.JTextField requestedqty;
    private javax.swing.JButton save;
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

    class Cargando extends JLabel {

        private Image imagen;

        @Override
        public void paint(Graphics g) {
            imagen = new ImageIcon(getClass().getResource("/Imagenes/loading-4.gif")).getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }
}
