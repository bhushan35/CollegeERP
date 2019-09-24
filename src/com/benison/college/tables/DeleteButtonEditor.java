package com.benison.college.tables;
import com.benison.college.admin.AdminLogin;
import com.benison.college.common.ComponentFactory;
import com.benison.college.database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
class DeleteButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String    label;
    private boolean   isPushed;
    private JFrame f;

    public DeleteButtonEditor(JCheckBox checkBox,JFrame frame) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        f=frame;
        button.addActionListener(e -> {
            deleteButton(TableClass.type);
            fireEditingStopped();
        });
    }

    private void deleteButton(String type) {
        try(Connection con= DBConnection.getInstance().getConnection())
        {
            String Data=TableClass.Data;
            String sql=null;
            String sql1=null;
            if(type.equalsIgnoreCase("Student")) {
                sql = "delete from students where en_no='"+Data+"'";
                sql1="delete from fees where en_no='"+Data+"'";
                System.out.println("Student"+Data);
            }else if(type.equalsIgnoreCase("Employee")) {
                sql = "delete from employee  where eid='"+Data+"'";
                sql1 = "UPDATE employee SET eid='"+Data+"' where eid='"+Data+"'";
                System.out.println("Employee"+Data);
            }else if(type.equalsIgnoreCase("Fees")) {
                sql = "delete from fees where en_no='"+Data+"'";
                sql1 = "delete from students where en_no='"+Data+"'";
                System.out.println("Student Fees"+Data);
            }else if(type.equalsIgnoreCase("Payroll")) {
                sql = "delete from payroll where en_no='"+Data+"'";
                sql1 = "UPDATE employee SET eid='"+Data+"' where eid='"+Data+"'";
                System.out.println("Student Fees"+Data);
            }

            // sql = "delete from employee  where eid='"+Data+"'";
            PreparedStatement ps = con.prepareStatement(sql);
            int i = ps.executeUpdate();
            ps=con.prepareStatement(sql1);
            int j=ps.executeUpdate();
            if(i>0||j>0)
            {
                JOptionPane.showMessageDialog(null,"Delete Successfully","Success",JOptionPane.PLAIN_MESSAGE);
                new AdminLogin();
                ComponentFactory.close(f);
            }

        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

        }
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
        button.setText( "Delete" );
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
