/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VentanasAdministradores;

import Conexion.ConexionBD;
import Login.Login;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.*;
import javax.swing.*;
import java.awt.datatransfer.*;
import java.util.*;

/**
 *
 * @author EDOMEX
 */
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public final class insertar extends javax.swing.JFrame {

    private Connection cc = null;
    private PreparedStatement p = null;
    int xMouse;
    int yMouse;
    int leer;
    String valor;
    /**
     * Creates new form formulario
     */

    DefaultTableModel model1;

    public insertar() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        ColumnasAutoajustadas(registros, margin);
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/Logo.png"));
        return retValue;

    }

    int vColIndex = 1;
    int margin = 2;

    public void ColumnasAutoajustadas(JTable registros, int margin) {
        for (int c = 0; c < this.registros.getColumnCount(); c++) {
            packColumnTablaFacturas(registros, c, 2);
        }
    }

    public void packColumnTablaFacturas(JTable registros, int vColIndex, int margin) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) registros.getColumnModel();
        TableColumn col = colModel.getColumn(vColIndex);
        int width;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = registros.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(registros, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int r = 0; r < registros.getRowCount(); r++) {
            renderer = registros.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(registros, registros.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin;
        col.setPreferredWidth(width);
    }

    public class ExcelAdapter implements ActionListener {

        private String rowstring, value;
        private Clipboard system;
        private StringSelection stsel;
        private JTable jTable1;

        /**
         * The Excel Adapter is constructed with a JTable on which it enables
         * Copy-Paste and acts as a Clipboard listener.
         *
         * @param myJTable
         */
        public ExcelAdapter(JTable myJTable) {
            jTable1 = myJTable;
            KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK, false);
            // Identifying the copy KeyStroke user can modify this
            // to copy on some other Key combination.
            KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK, false);
            // Identifying the Paste KeyStroke user can modify this
            //to copy on some other Key combination.
            jTable1.registerKeyboardAction(this, "Copy", copy, JComponent.WHEN_FOCUSED);
            jTable1.registerKeyboardAction(this, "Paste", paste, JComponent.WHEN_FOCUSED);
            system = Toolkit.getDefaultToolkit().getSystemClipboard();
        }

        /**
         * Public Accessor methods for the Table on which this adapter acts.
         *
         * @return
         */
        public JTable getJTable() {
            return jTable1;
        }

        public void setJTable(JTable jTable1) {
            this.jTable1 = jTable1;
        }

        /**
         * This method is activated on the Keystrokes we are listening to in
         * this implementation. Here it listens for Copy and Paste
         * ActionCommands. Selections comprising non-adjacent cells result in
         * invalid selection and then copy action cannot be performed. Paste is
         * done by aligning the upper left corner of the selection with the 1st
         * element in the current selection of the JTable.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().compareTo("Copy") == 0) {
                StringBuilder sbf = new StringBuilder();
                // Check to ensure we have selected only a contiguous block of
                // cells
                int numcols = jTable1.getSelectedColumnCount();
                int numrows = jTable1.getSelectedRowCount();
                int[] rowsselected = jTable1.getSelectedRows();
                int[] colsselected = jTable1.getSelectedColumns();
                if (!((numrows - 1 == rowsselected[rowsselected.length - 1] - rowsselected[0]
                        && numrows == rowsselected.length)
                        && (numcols - 1 == colsselected[colsselected.length - 1] - colsselected[0]
                        && numcols == colsselected.length))) {
                    JOptionPane.showMessageDialog(null, "Invalid Copy Selection",
                            "Invalid Copy Selection",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int i = 0; i < numrows; i++) {
                    for (int j = 0; j < numcols; j++) {
                        sbf.append(jTable1.getValueAt(rowsselected[i], colsselected[j]));
                        if (j < numcols - 1) {
                            sbf.append("\t");
                        }
                    }
                    sbf.append("\n");
                }
                stsel = new StringSelection(sbf.toString());
                system = Toolkit.getDefaultToolkit().getSystemClipboard();
                system.setContents(stsel, stsel);
            }
            if (e.getActionCommand().compareTo("Paste") == 0) {
                System.out.println("Trying to Paste");
                int startRow = (jTable1.getSelectedRows())[0];
                int startCol = (jTable1.getSelectedColumns())[0];
                try {
                    String trstring = (String) (system.getContents(this).getTransferData(DataFlavor.stringFlavor));
                    System.out.println("String is:" + trstring);
                    StringTokenizer st1 = new StringTokenizer(trstring, "\n");
                    for (int i = 0; st1.hasMoreTokens(); i++) {
                        rowstring = st1.nextToken();
                        StringTokenizer st2 = new StringTokenizer(rowstring, "\t");
                        for (int j = 0; st2.hasMoreTokens(); j++) {
                            value = (String) st2.nextToken();
                            if (startRow + i < jTable1.getRowCount()
                                    && startCol + j < jTable1.getColumnCount()) {
                                jTable1.setValueAt(value, startRow + i, startCol + j);
                            }
                            System.out.println("Putting " + value + "at row=" + startRow + i + "column=" + startCol + j);

                        }
                    }
                } catch (UnsupportedFlavorException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public class PegarExcel implements ActionListener {

        private String rowstring, value;
        private Clipboard system;
        private StringSelection stringSelection, stsel;
        private JTable jTable1;
//----------------------------------------------------------------------------------------------------------------------

        public PegarExcel(JTable myJTable) {
            jTable1 = myJTable;

            KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK, false);

            jTable1.registerKeyboardAction(this, "Paste", paste, JComponent.WHEN_FOCUSED);

            system = Toolkit.getDefaultToolkit().getSystemClipboard();
        }
//----------------------------------------------------------------------------------------------------------------------

        public JTable getJTable() {
            return jTable1;
        }
//----------------------------------------------------------------------------------------------------------------------

        public void setJTable(JTable jTable1) {
            this.jTable1 = jTable1;
        }
//----------------------------------------------------------------------------------------------------------------------

        void showErrorMessage(String msg) {
            JOptionPane.showMessageDialog(null, msg,
                    msg,
                    JOptionPane.ERROR_MESSAGE);
        }
//----------------------------------------------------------------------------------------------------------------------

        void pasteAction() {
            system = Toolkit.getDefaultToolkit().getSystemClipboard();

            try {
                String data = (String) system.getData(DataFlavor.stringFlavor);
                if (data == null) {
                    showErrorMessage("No data on clipboard");
                    return;
                }

                int selectCol = jTable1.getSelectedColumn();
                int selectRow = jTable1.getSelectedRow();
                if (selectCol < 0 || selectRow < 0) {
                    showErrorMessage("Please Select cell");
                    return;
                }
//devuelve clipboard contenido

                StringTokenizer st, stTmp;
                st = new StringTokenizer(data, "\n");
                int pasteRows = st.countTokens();
                st = new StringTokenizer(st.nextToken().trim(), "\t");
                int pasteCols = st.countTokens();
                int marginCols = jTable1.getColumnCount() - selectCol;
                int marginRows = jTable1.getRowCount() - selectRow;
//revisa espacio disponible
                if (marginCols < pasteCols || marginRows < pasteRows) {
                    showErrorMessage("La tabla no posee el espacio suficiente para pegar los datos");
                    return;
                }
                st = new StringTokenizer(data, "\n");
                int rowCount = 0, colCount;
//copia a la tabla
                while (st.hasMoreTokens()) {
                    stTmp = new StringTokenizer(st.nextToken(), "\t");
                    colCount = 0;
                    while (stTmp.hasMoreTokens()) {
                        jTable1.setValueAt(stTmp.nextToken(), rowCount + selectRow, colCount + selectCol);
                        colCount++;
                    }

                    rowCount++;
                }
            } catch (UnsupportedFlavorException uf) {
                System.out.println("uf=" + uf.getMessage());
            } catch (IOException io) {
                System.out.println("io=" + io.getMessage());
            }

        }
//----------------------------------------------------------------------------------------------------------------------

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().compareTo("Paste") == 0) {
                pasteAction();
            }

        }
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
                && !pdate.getText().equals("") /**
                 * && !numordenc.getText().equals("") &&
                 * !contratista.getText().equals("") &&
                 * !ocompradt.getText().equals("") &&
                 * !importe.getText().equals("") && !total.getText().equals("")
                 * && !stat.getText().equals("")*
                 */
                ;
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
        pdate.setText("");
        /**
         * numordenc.setText(""); contratista.setText("");
         * ocompradt.setText(""); importe.setText(""); total.setText("");
         * stat.setText("");
         *
         */

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new FondoPanelTitulo();
        jLabel31 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        Nombre_Usuario_Facturas = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        ID_Usuario_Facturas = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new FondoPanelesCentrales();
        jLabel1 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        pjcode = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        pjname = new javax.swing.JTextArea();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        customer = new javax.swing.JTextArea();
        jLabel35 = new javax.swing.JLabel();
        postatus = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        pon = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        shipment = new javax.swing.JTextField();
        poline = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jPanel3 = new FondoPanelesCentrales();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        sitecode = new javax.swing.JTextArea();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        sitename = new javax.swing.JTextArea();
        jLabel42 = new javax.swing.JLabel();
        itemcode = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        itemdsc = new javax.swing.JTextArea();
        jLabel44 = new javax.swing.JLabel();
        requestedqty = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        dueqty = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        billedqty = new javax.swing.JTextField();
        jPanel4 = new FondoPanelesCentrales();
        jLabel47 = new javax.swing.JLabel();
        unitprice = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        amount = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        unit = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        payment = new javax.swing.JTextArea();
        jLabel51 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        category = new javax.swing.JTextArea();
        jLabel52 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        bidding = new javax.swing.JTextArea();
        jLabel53 = new javax.swing.JLabel();
        pdate = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        borrar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        registros = new javax.swing.JTable();
        Fondo = new FondoPanelPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(1329, 705));
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setMaximumSize(new java.awt.Dimension(1330, 60));
        jPanel1.setMinimumSize(new java.awt.Dimension(1330, 60));
        jPanel1.setPreferredSize(new java.awt.Dimension(1330, 60));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Bienvenido (a):");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

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

        Nombre_Usuario_Facturas.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        Nombre_Usuario_Facturas.setForeground(new java.awt.Color(255, 255, 255));
        Nombre_Usuario_Facturas.setText("Nombre");
        jPanel1.add(Nombre_Usuario_Facturas, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        jLabel40.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("ID:");
        jPanel1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        ID_Usuario_Facturas.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        ID_Usuario_Facturas.setForeground(new java.awt.Color(255, 255, 255));
        ID_Usuario_Facturas.setText("ID");
        jPanel1.add(ID_Usuario_Facturas, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Pegar Facturación (Excel)");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, 0, 1330, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 1", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(316, 338));
        jPanel2.setMinimumSize(new java.awt.Dimension(316, 338));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 29, -1, -1));

        id.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                idComponentResized(evt);
            }
        });
        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });
        jPanel2.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 27, 202, -1));

        jLabel32.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Project Code:");
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 60, -1, -1));

        pjcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pjcodeActionPerformed(evt);
            }
        });
        jPanel2.add(pjcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 58, 202, -1));

        jLabel33.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Project Name:");
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 89, -1, -1));

        pjname.setColumns(20);
        pjname.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        pjname.setLineWrap(true);
        pjname.setRows(3);
        jScrollPane10.setViewportView(pjname);

        jPanel2.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 89, 202, -1));

        jLabel34.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Customer:\t");
        jPanel2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 148, -1, -1));

        customer.setColumns(20);
        customer.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        customer.setLineWrap(true);
        customer.setRows(3);
        jScrollPane11.setViewportView(customer);

        jPanel2.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 148, 202, -1));

        jLabel35.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("PO Status:");
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 209, -1, -1));
        jPanel2.add(postatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 207, 202, -1));

        jLabel36.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("PO NO:");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 240, -1, -1));

        pon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ponActionPerformed(evt);
            }
        });
        jPanel2.add(pon, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 238, 202, -1));

        jLabel37.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Shipment NO:");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 302, -1, -1));

        shipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shipmentActionPerformed(evt);
            }
        });
        jPanel2.add(shipment, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 300, 202, -1));
        jPanel2.add(poline, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 269, 202, -1));

        jLabel38.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("PO_Line_NO:");
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 271, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 330, 370));

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 2", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setMaximumSize(new java.awt.Dimension(317, 338));
        jPanel3.setMinimumSize(new java.awt.Dimension(317, 338));
        jPanel3.setPreferredSize(new java.awt.Dimension(317, 338));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Site Code:");
        jPanel3.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 27, -1, -1));

        sitecode.setColumns(20);
        sitecode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        sitecode.setLineWrap(true);
        sitecode.setRows(3);
        jScrollPane13.setViewportView(sitecode);

        jPanel3.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 27, 202, -1));

        jLabel41.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Site Name:");
        jPanel3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 86, -1, -1));

        sitename.setColumns(20);
        sitename.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        sitename.setLineWrap(true);
        sitename.setRows(3);
        jScrollPane14.setViewportView(sitename);

        jPanel3.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 86, 202, -1));

        jLabel42.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Item Code:");
        jPanel3.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 147, -1, -1));
        jPanel3.add(itemcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 145, 202, -1));

        jLabel43.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Item Desc:");
        jPanel3.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 176, -1, -1));

        itemdsc.setColumns(20);
        itemdsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        itemdsc.setLineWrap(true);
        itemdsc.setRows(3);
        itemdsc.setText("\n");
        jScrollPane15.setViewportView(itemdsc);

        jPanel3.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 176, 202, 57));

        jLabel44.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Requested Qty:");
        jPanel3.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 246, -1, -1));

        requestedqty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestedqtyActionPerformed(evt);
            }
        });
        jPanel3.add(requestedqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 244, 202, -1));

        jLabel45.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Due Qty:");
        jPanel3.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 277, -1, -1));
        jPanel3.add(dueqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 275, 202, -1));

        jLabel46.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Billed Qty:");
        jPanel3.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 308, -1, -1));

        billedqty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billedqtyActionPerformed(evt);
            }
        });
        jPanel3.add(billedqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 306, 202, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 340, 370));

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sección 3", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel47.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Unit Price:");
        jPanel4.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 29, -1, -1));
        jPanel4.add(unitprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 202, -1));

        jLabel48.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Line Amount:");
        jPanel4.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 60, -1, -1));
        jPanel4.add(amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 202, -1));

        jLabel49.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Unit:");
        jPanel4.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 91, -1, -1));

        unit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unitActionPerformed(evt);
            }
        });
        jPanel4.add(unit, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 202, -1));

        jLabel50.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Payment Terms:");
        jPanel4.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 120, -1, -1));

        payment.setColumns(20);
        payment.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        payment.setLineWrap(true);
        payment.setRows(3);
        payment.setText("\n");
        jScrollPane16.setViewportView(payment);

        jPanel4.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 202, 57));

        jLabel51.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Category:");
        jPanel4.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 188, -1, -1));

        category.setColumns(20);
        category.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        category.setLineWrap(true);
        category.setRows(3);
        category.setText("\n");
        jScrollPane17.setViewportView(category);

        jPanel4.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 202, 57));

        jLabel52.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Bidding Area:");
        jPanel4.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 256, -1, -1));

        bidding.setColumns(20);
        bidding.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        bidding.setLineWrap(true);
        bidding.setRows(3);
        bidding.setText("\n");
        jScrollPane18.setViewportView(bidding);

        jPanel4.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 202, 57));

        jLabel53.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Publish Date:");
        jPanel4.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        pdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdateActionPerformed(evt);
            }
        });
        jPanel4.add(pdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 202, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 70, 340, 370));

        jLabel28.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("PEGUE LA INFORMACION DE EXCEL EN LA TABLA, AL HACER CLICK EN UN REGISTRO LA INFORMACION PASARA A LOS FORMULARIOS (SOLO 100 REGISTROS MAX)");
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));

        save.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10)); // NOI18N
        save.setText("GUARDAR");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        getContentPane().add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, -1, -1));

        borrar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10)); // NOI18N
        borrar.setText("BORRAR");
        borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarActionPerformed(evt);
            }
        });
        getContentPane().add(borrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 480, -1, -1));

        jButton4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10)); // NOI18N
        jButton4.setText("REGRESAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 480, -1, -1));

        jButton5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10)); // NOI18N
        jButton5.setText("CERRAR SESIÓN");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 480, -1, -1));

        registros.setAutoCreateRowSorter(true);
        registros.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        registros.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        registros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, ""},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "project_code", "project_name", "customer", "PO_status", "PO_NO", "PO_Line_NO", "shipment_NO", "site_code", "site_name", "item_code", "item_desc", "requested_qty", "due_qty double", "billed_qty", "unit_price", "line_amount", "unit", "payment_terms", "category", "bidding_area", "publish_date"
            }
        ));
        registros.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        registros.setSelectionBackground(new java.awt.Color(0, 153, 204));
        registros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                registrosMousePressed(evt);
            }
        });
        jScrollPane12.setViewportView(registros);

        getContentPane().add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 1310, 180));

        Fondo.setText("jLabel32");
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

        setSize(new java.awt.Dimension(1330, 711));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentResized

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void idComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_idComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_idComponentResized

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:nbmbmbmnbnmbmnj
    }//GEN-LAST:event_idActionPerformed

    private void pjcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pjcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pjcodeActionPerformed

    private void ponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ponActionPerformed

    private void shipmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shipmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shipmentActionPerformed

    private void billedqtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billedqtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_billedqtyActionPerformed

    private void requestedqtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestedqtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_requestedqtyActionPerformed

    private void unitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unitActionPerformed

    private void pdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pdateActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed

        try {
            cc = ConexionBD.getcon();
            if (DatosLlenosFacturacion()) {
                String SQL = "INSERT INTO facturacion (id,project_code,project_name,customer,PO_status,PO_NO,PO_Line_NO,shipment_NO,site_code,site_name,item_code,item_desc,requested_qty,due_qty,billed_qty,unit_price,line_amount,unit,payment_terms,category,bidding_area,publish_date) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
                p.setString(22, pdate.getText());
                /**
                 * p.setString(23, numordenc.getText()); p.setString(24,
                 * contratista.getText()); p.setString(25, ocompradt.getText());
                 * p.setString(26, importe.getText()); p.setString(27,
                 * total.getText()); p.setString(28, stat.getText());
                 *
                 */
                int n = p.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos Guardados");
                    LimpiarDatos();
                    id.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llena todos los campos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "El proyecto que usted intenta guardar ya existe, pruebe con otro o actualize el código" + e);
        } finally {
            if (p != null) {
                try {
                    p.close();
                    p = null;
                } catch (SQLException ex) {
                    Logger.getLogger(insertar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cc != null) {
                try {
                    cc.close();
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(insertar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_saveActionPerformed

    private void borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarActionPerformed
        int filaseleccionada;
        try {
            filaseleccionada = registros.getSelectedRow();
            if (filaseleccionada == -1) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila");
            } else {
                DefaultTableModel modelo = (DefaultTableModel) registros.getModel();
                modelo.removeRow(filaseleccionada);
                LimpiarDatos();
            }
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex + "\nInténtelo nuevamente", " .::Error En la Operacion::.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_borrarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
        AdministradorFacturacion AF = new AdministradorFacturacion();
        AF.setVisible(true);
        AdministradorFacturacion.ID_Usuario.setText(ID_Usuario_Facturas.getText());
        AF.Nombre_UsuarioAdmin.setText(Nombre_Usuario_Facturas.getText());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        dispose();
        Login log = new Login();
        log.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void registrosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registrosMousePressed
        int filaseleccionada;
        try {
            PegarExcel myAd = new PegarExcel(registros);
            filaseleccionada = registros.getSelectedRow();
            if (filaseleccionada == -1) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila");
            } else {
                DefaultTableModel modelotabla = (DefaultTableModel) registros.getModel();
                ColumnasAutoajustadas(registros, margin);
                String idd = (String) modelotabla.getValueAt(filaseleccionada, 0);
                String pjcodee = (String) modelotabla.getValueAt(filaseleccionada, 1);
                String pjnamee = (String) modelotabla.getValueAt(filaseleccionada, 2);
                String customerr = (String) modelotabla.getValueAt(filaseleccionada, 3);
                String postatuss = (String) modelotabla.getValueAt(filaseleccionada, 4);
                String ponn = (String) modelotabla.getValueAt(filaseleccionada, 5);
                String polinee = (String) modelotabla.getValueAt(filaseleccionada, 6);
                String shipmentt = (String) modelotabla.getValueAt(filaseleccionada, 7);
                String sitecodee = (String) modelotabla.getValueAt(filaseleccionada, 8);
                String sitenamee = (String) modelotabla.getValueAt(filaseleccionada, 9);
                String itemcodee = (String) modelotabla.getValueAt(filaseleccionada, 10);
                String itemdscc = (String) modelotabla.getValueAt(filaseleccionada, 11);
                String requestedqtyy = (String) modelotabla.getValueAt(filaseleccionada, 12);
                String dueqtyy = (String) modelotabla.getValueAt(filaseleccionada, 13);
                String billedqtyy = (String) modelotabla.getValueAt(filaseleccionada, 14);
                String unitpricee = (String) modelotabla.getValueAt(filaseleccionada, 15);
                String amountt = (String) modelotabla.getValueAt(filaseleccionada, 16);
                String unitt = (String) modelotabla.getValueAt(filaseleccionada, 17);
                String paymentt = (String) modelotabla.getValueAt(filaseleccionada, 18);
                String categoryy = (String) modelotabla.getValueAt(filaseleccionada, 19);
                String biddingg = (String) modelotabla.getValueAt(filaseleccionada, 20);
                String pdatee = (String) modelotabla.getValueAt(filaseleccionada, 21);
                /**
                 * String numordencc = (String)
                 * modelotabla.getValueAt(filaseleccionada, 22); String
                 * contratistaa = (String)
                 * modelotabla.getValueAt(filaseleccionada, 23); String
                 * ocompradtt = (String)
                 * modelotabla.getValueAt(filaseleccionada, 24); String importee
                 * = (String) modelotabla.getValueAt(filaseleccionada, 25);
                 * String totall = (String)
                 * modelotabla.getValueAt(filaseleccionada, 26); String statt =
                 * (String) modelotabla.getValueAt(filaseleccionada, 27);
                 *
                 */
                id.setText(idd);
                pjcode.setText(pjcodee);
                pjname.setText(pjnamee);
                customer.setText(customerr);
                postatus.setText(postatuss);
                pon.setText(ponn);
                poline.setText(polinee);
                shipment.setText(shipmentt);
                sitecode.setText(sitecodee);
                sitename.setText(sitenamee);
                itemcode.setText(itemcodee);
                itemdsc.setText(itemdscc);
                requestedqty.setText(requestedqtyy);
                dueqty.setText(dueqtyy);
                billedqty.setText(billedqtyy);
                unitprice.setText(unitpricee);
                amount.setText(amountt);
                unit.setText(unitt);
                payment.setText(paymentt);
                category.setText(categoryy);
                bidding.setText(biddingg);
                pdate.setText(pdatee);
                /**
                 * numordenc.setText(numordencc);
                 * contratista.setText(contratistaa);
                 * ocompradt.setText(ocompradtt); importe.setText(importee);
                 * total.setText(totall); stat.setText(statt);
                 *
                 */
            }
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex + "\nInténtelo nuevamente", " .::Error En la Operacion::.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_registrosMousePressed

    private void FondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_FondoMousePressed

    private void FondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_FondoMouseDragged

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
            java.util.logging.Logger.getLogger(insertar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new insertar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    public static javax.swing.JLabel ID_Usuario_Facturas;
    public javax.swing.JLabel Nombre_Usuario_Facturas;
    private javax.swing.JTextField amount;
    private javax.swing.JTextArea bidding;
    private javax.swing.JTextField billedqty;
    private javax.swing.JButton borrar;
    private javax.swing.JTextArea category;
    private javax.swing.JTextArea customer;
    private javax.swing.JTextField dueqty;
    private javax.swing.JTextField id;
    private javax.swing.JTextField itemcode;
    private javax.swing.JTextArea itemdsc;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JTextArea payment;
    private javax.swing.JTextField pdate;
    private javax.swing.JTextField pjcode;
    private javax.swing.JTextArea pjname;
    private javax.swing.JTextField poline;
    private javax.swing.JTextField pon;
    private javax.swing.JTextField postatus;
    public javax.swing.JTable registros;
    private javax.swing.JTextField requestedqty;
    private javax.swing.JButton save;
    private javax.swing.JTextField shipment;
    private javax.swing.JTextArea sitecode;
    private javax.swing.JTextArea sitename;
    private javax.swing.JTextField unit;
    private javax.swing.JTextField unitprice;
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
