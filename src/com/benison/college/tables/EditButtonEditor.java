package com.benison.college.tables;

import com.benison.college.account.AccountEditor;
import com.benison.college.admin.AdminEditor;
import com.benison.college.common.ComponentFactory;

import javax.swing.*;
import java.awt.*;

public class EditButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String    label;
    private boolean   isPushed;

    public EditButtonEditor(JCheckBox checkBox, JFrame frame, boolean account) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> {
            String Data=TableClass.Data;
            if(account){
                new AccountEditor(TableClass.type,Data);
                System.out.println(TableClass.type);
                ComponentFactory.close(frame);
            }else{
             new AdminEditor(TableClass.type,Data);
             ComponentFactory.close(frame);
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
        button.setText("Edit");
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
