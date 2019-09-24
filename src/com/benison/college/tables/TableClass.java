package com.benison.college.tables;

import com.benison.college.database.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.LinkedList;

public class TableClass {
    static String Data = null;
    static  String type=null;
    private  static LinkedList<String> list;
    private static JFrame frame;
    private static boolean account;
    public static JPanel getTable(String name, String t, JFrame f, boolean b, boolean b1){
        frame=f;
        type=t;
        account=b;
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
       if(!account) {

           emptable.getColumn("EDIT").setCellRenderer(new ButtonRenderer());
           emptable.getColumn("EDIT").setCellEditor(new EditButtonEditor(new JCheckBox(), f,b1));
           emptable.getColumn("DELETE").setCellRenderer(new ButtonRenderer());
           emptable.getColumn("DELETE").setCellEditor(new DeleteButtonEditor(new JCheckBox(), f));
       }
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
        if(type.equalsIgnoreCase("Student")) {

           if(account) {
               column=new LinkedList<String>(Arrays.asList("En_No", "NAME", "CLASS", "GENDER", "DOB", "DOA", "EMAIL", "ADDRESS", "CASTE", "MOBILE"));
           }else{
               column=new LinkedList<String>(Arrays.asList("En_No", "NAME", "CLASS", "GENDER", "DOB", "DOA", "EMAIL", "ADDRESS", "CASTE", "MOBILE","EDIT","DELETE"));
           }

           sql = "select * from students";
            count=10;
        }else  if(type.equalsIgnoreCase("Employee")) {
            if(account) {
                column=new LinkedList<String>(Arrays.asList("Eid", "NAME", "DEPT", "GENDER", "DOB", "DOJ", "QUALIFICATION", "EXPERIANCE", "SALARY", "DESIGNATION", "ADDRESS","MOBILE","MAIL"));
            }else{
                column=new LinkedList<String>(Arrays.asList("Eid", "NAME", "DEPT", "GENDER", "DOB", "DOJ", "QUALIFICATION", "EXPERIANCE", "SALARY", "DESIGNATION", "ADDRESS","MOBILE","MAIL","EDIT", "DELETE"));
            }

             sql = "select * from employee";
             count=13;
        }else if(type.equalsIgnoreCase("Fees")) {
            column = new LinkedList<String>(Arrays.asList("En_No", "NAME", "CLASS", "MOBILE", "MAIL", "TOTAL_FEES", "PAID_FEES", "REMAINING_FEES", "EDIT", "DELETE"));
            sql = "select en_no,name,class,mobile_no,email,total_fees,paid_fees,rem_fees from students natural join fees";
            count = 8;
        }else if(type.equalsIgnoreCase("Payroll")) {
            column=new LinkedList<String>(Arrays.asList("EID", "NAME", "DEPT", "GENDER", "DESIGNATION","MOBILE","MONTH","SALARY","DEDUCTION","TOTAL PAID","DATE","EDIT","DELETE"));
            sql = "SELECT eid,name,dept,gender,designation,mobile,month_year,tot_sal,deduction,tot_paid,date_of_pay FROM employee natural join payroll ";
            count=11;
        }

      /*  if(!type.equalsIgnoreCase("admin")){
            model = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }else{}*/
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
                if(account){

                }else{
                    list.add("Edit");
                    list.add("Delete");
                }
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

}

