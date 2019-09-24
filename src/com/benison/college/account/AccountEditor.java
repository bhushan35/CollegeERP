package com.benison.college.account;

import com.benison.college.registartion.Fees;
import com.benison.college.registartion.Payroll;
import com.benison.college.registartion.User;
import com.benison.college.tables.TableClass;
import com.benison.college.common.ComponentFactory;
import com.benison.college.common.Login;
import javax.swing.*;
import java.awt.*;

public class AccountEditor extends JFrame implements Login {
    JPanel main;
    private String type,data;
    public AccountEditor(String type, String data){
        this.data=data;
        this.type=type;
        JLabel bg=ComponentFactory.getBackground();
        JPanel header= ComponentFactory.getHeader(this);
        JRootPane rootPane=new JRootPane();
        rootPane.setBounds(0,60,900,28);

        main=new JPanel();
        main.setBounds(20,100,850,650);
        main.setBackground(new Color(0,0,0,80));
        main.setLayout(null);

        getEditPanel();

        bg.add(header);
        rootPane.setJMenuBar(menubar());
        bg.add(rootPane);
        bg.add(main);
        add(bg);
        ComponentFactory.setFrame("Account Page",this);
        setLayout(null);
    }

    private void getEditPanel() {
        JPanel editPanel=null;
        if(type.equalsIgnoreCase("Fees")){
            editPanel= Fees.getFeesPanel(true,data,this,"account");
            editPanel.setBounds(10,10,820,620);
        }else if(type.equalsIgnoreCase("Payroll")){
            editPanel = Payroll.getSalPanel(true, data,this,"account");
            editPanel.setBounds(10, 10, 820, 620);
        }
        main.removeAll();
        main.repaint();
        main.revalidate();
        main.add(editPanel);
        main.repaint();
        main.revalidate();
    }

    @Override
    public JMenuBar menubar(){
        JMenu home,details,account,payroll,action;
        JMenuItem  studDetails, empDetails,feesDetails,feesEntry,payrollDetails,salaryEntry,changePass;
        JMenuBar mb=new JMenuBar();
        home=new JMenu("Home");
        details=new JMenu("Report");
        account=new JMenu("Account");
        payroll=new JMenu("Payroll");
        action=new JMenu("Actions");
        studDetails=new JMenuItem("Students");
        empDetails=new JMenuItem("Employees");
        feesDetails=new JMenuItem("Students Fees");
        feesEntry=new JMenuItem("Fees Entry");
        payrollDetails=new JMenuItem("Employee Payroll");
        salaryEntry=new JMenuItem("Pay Salary");
        changePass=new JMenuItem("Change Password");

        details.add(studDetails);details.add(new JSeparator()); details.add(empDetails);
        account.add(feesEntry);account.add(new JSeparator());account.add(feesDetails);
        payroll.add(salaryEntry);account.add(new JSeparator());payroll.add(payrollDetails);
        action.add(changePass);
        mb.add(home);
        mb.add(details);
        mb.add(account);
        mb.add(payroll);
        mb.add(action);
        mb.setBounds(0,0,900,30);

        // add Action Listener
        studDetails.addActionListener(X->{
            JPanel table=TableClass.getTable("Students Records :","Student",this, true, false);
            table.setBounds(0,0,850,650);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(table);
            main.repaint();
            main.revalidate();

        });
        empDetails.addActionListener(X->{
            JPanel table=TableClass.getTable("Employee Records :","Employee",this, true, false);
            table.setBounds(0,0,850,600);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(table);
            main.repaint();
            main.revalidate();
        });
        payrollDetails.addActionListener(X->{
            JPanel table=TableClass.getTable("Payroll Records :","Payroll",this, false, true);
            table.setBounds(0,0,850,650);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(table);
            main.repaint();
            main.revalidate();

        });
        changePass.addActionListener(X->{
            JPanel userPanel= User.passChange(this,"account");
            userPanel.setBounds(50,50,700,400);

            main.removeAll();
            main.repaint();
            main.revalidate();

            main.add(userPanel);
            main.repaint();
            main.revalidate();
        });
        feesDetails.addActionListener(X->{
            JPanel table=TableClass.getTable("Fees Records :","Fees",this, false, true);
            table.setBounds(0,0,850,600);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(table);
            main.repaint();
            main.revalidate();
        });
        feesEntry.addActionListener(X->{
            JPanel feesRegPanel= Fees.getFeesPanel(false,"",this,"account");
            feesRegPanel.setBounds(30,30,800,600);

            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(feesRegPanel);
            main.repaint();
            main.revalidate();

            JPanel table=TableClass.getTable("Fees Records :","Fees",this, false, true);
            table.setBounds(0,0,850,600);
        });
        salaryEntry.addActionListener(X->{
            JPanel salPanel= Payroll.getSalPanel(false,"",this,"admin");
            salPanel.setBounds(30,30,800,600);

            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(salPanel);
            main.repaint();
            main.revalidate();

            JPanel table=TableClass.getTable("Fees Records :","Fees",this, false, true);
            table.setBounds(0,0,850,600);
        });
        return mb;
    }

}

