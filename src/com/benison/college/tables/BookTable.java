package com.benison.college.tables;
import com.benison.college.admin.AdminLogin;
import com.benison.college.common.ComponentFactory;
import com.benison.college.database.DBConnection;
import com.benison.college.library.LibraryEdit;
import com.benison.college.library.LibraryLogin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

public class BookTable {
    static String Data = null;
    static  String type=null;
    private  static LinkedList<String> list;
    private static JFrame frame;
    private static boolean account;
    public static JPanel getTable(String name, JFrame f){
        frame=f;


        JPanel tabelpanel = new JPanel();
        tabelpanel.setBounds(10, 80, 850, 470);
        tabelpanel.setLayout(null);
        tabelpanel.setBackground(new Color(0, 0, 0, 80));

        JLabel emplabel = new JLabel(name);
        emplabel.setFont(new Font("Arial",Font.BOLD,20));
        emplabel.setForeground(Color.RED);
        emplabel.setBounds(20, 10, 200, 30);

        JTable emptable = new JTable();
        DefaultTableModel model = getModel(type);
        emptable.setModel(model);
            emptable.getColumn("EDIT").setCellRenderer(new ButtonRenderer());
            emptable.getColumn("EDIT").setCellEditor(new EditButtonEditor(new JCheckBox(),frame));
            emptable.getColumn("DELETE").setCellRenderer(new ButtonRenderer());
            emptable.getColumn("DELETE").setCellEditor(new DeleteButtonEditor(new JCheckBox(),frame));

        emptable.setRowHeight(25);
        emptable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //Listening selected data
        emptable.setCellSelectionEnabled(true);
        ListSelectionModel select= emptable.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        select.addListSelectionListener(e -> {
            int[] row = emptable.getSelectedRows();
            int[] columns = emptable.getSelectedColumns();
            for (int i = 0; i < row.length; i++){
                Data = (String) emptable.getValueAt(row[i],0);
            }
            System.out.println("Table element selected is: " + Data);
        });
        JScrollPane sp=new JScrollPane(emptable);
        sp.setBounds(20,45,800,500);
        tabelpanel.add(emplabel);
        tabelpanel.add(sp);
        return tabelpanel;
    }
    public static DefaultTableModel getModel(String type){

        LinkedList<String> column=null;
        String sql = null;
        int count = 0;
        column=new LinkedList<String>(Arrays.asList("BID", "TITLE", "AUTHOR", "PUBLICATION", "EDITION","ISBN","PAGES","QTY","PRICE","DATE","EDIT","DELETE"));
        sql = "SELECT *from book ";
        count=10;

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(column.toArray());
        try(Connection con= DBConnection.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            list=new LinkedList<>();
            while(rs.next())
            {
                list=new LinkedList();
                int index=1;
                while(index<=count){
                    list.add(rs.getString(index));
                    index++;
                }
                    list.add("Edit");
                    list.add("Delete");
                    model.addRow(list.stream().toArray());
                    list.removeAll(list);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }

    private static class EditButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String    label;
        private boolean   isPushed;

        public EditButtonEditor(JCheckBox checkBox,JFrame frame) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new LibraryEdit(Data);
                    ComponentFactory.close(frame);
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else{
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value ==null) ? "" : value.toString();
            button.setText( label );
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed)  {

            }
            isPushed = false;
            return new String( label ) ;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    private static class DeleteButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String    label;
        private boolean   isPushed;

        public DeleteButtonEditor(JCheckBox checkBox,JFrame frame) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                try(Connection con= DBConnection.getInstance().getConnection()) {
                    String sql="delete from book where bid='"+Data+"'";
                    PreparedStatement ps = con.prepareStatement(sql);
                    int i = ps.executeUpdate();
                    if(i>0)
                    {
                        JOptionPane.showMessageDialog(null,"Delete Successfully","Success",JOptionPane.PLAIN_MESSAGE);
                        new LibraryLogin();
                        ComponentFactory.close(frame);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

                fireEditingStopped();
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else{
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value ==null) ? "" : value.toString();
            button.setText( label );
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed)  {

            }
            isPushed = false;
            return new String( label ) ;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}


