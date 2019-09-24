package com.benison.college.admin;

import com.benison.college.common.ComponentFactory;
import com.benison.college.common.Login;
import com.benison.college.registartion.*;
import com.benison.college.tables.TableClass;

import javax.swing.*;
import java.awt.*;

public class AdminEditor extends JFrame implements Login {

    private JPanel header;
    private JLabel bg;
    static public JPanel main;
    private String data=null;
    String type;

    public AdminEditor(String type, String data){
        this.type=type;
        this.data=data;
        bg=ComponentFactory.getBackground();
        header= ComponentFactory.getHeader(this);
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
        ComponentFactory.setFrame("Admin Page",this);
        setLayout(null);
    }
    private void getEditPanel() {
        JPanel editPanel=null;
        if(type.equalsIgnoreCase("Student")){
            editPanel= Students.getReg(true,data,this);
            editPanel.setBounds(10,10,820,620);
        } else if(type.equalsIgnoreCase("EMPLOYEE")){
            editPanel= Employee.getEmpReg(true,data,this);
            editPanel.setBounds(10,10,820,620);
        }else if(type.equalsIgnoreCase("Fees")){
            editPanel= Fees.getFeesPanel(true,data,this,"admin");
            editPanel.setBounds(10,10,820,620);
        }else if(type.equalsIgnoreCase("Payroll")) {
            editPanel = Payroll.getSalPanel(true, data,this,"admin");
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
        JMenu home, reg,details,account,actions;
        JMenuItem studReg, empReg, studDetails, empDetails, feesDetails,feesEntry,payrollDetails,user,changePass;
        JMenuBar mb=new JMenuBar();
        home=new JMenu("Home");
        reg=new JMenu("Register");
        details=new JMenu("Report");
        account=new JMenu("Account");
        actions=new JMenu("Actions");
        studReg=new JMenuItem("Student");
        empReg=new JMenuItem("Employee");
        studDetails=new JMenuItem("Students");
        empDetails=new JMenuItem("Employees");
        payrollDetails=new JMenuItem("Payroll");
        feesDetails=new JMenuItem("Students Fees");
        feesEntry=new JMenuItem("Fees Entry");
        user=new JMenuItem("Add User");
        changePass=new JMenuItem("Change Password");

        reg.add(studReg);reg.add(new JSeparator()); reg.add(empReg);
        details.add(studDetails);details.add(new JSeparator()); details.add(empDetails);details.add(new JSeparator()); details.add(payrollDetails);
        account.add(feesEntry);account.add(new JSeparator()); account.add(feesDetails);
        actions.add(user);actions.add(new JSeparator());actions.add(changePass);
        mb.add(home);
        mb.add(reg);
        mb.add(details);
        mb.add(account);
        mb.add(actions);
        mb.setBounds(0,0,900,30);

        // Adding ActionListener
        studReg.addActionListener(X->{
            JPanel studRegPanel= Students.getReg(false," ",this);
            studRegPanel.setBounds(0,0,850,650);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(studRegPanel);
            main.repaint();
            main.revalidate();
        });
        empReg.addActionListener(X->{
            JPanel empRegPanel= Employee.getEmpReg(false,"", this);
            empRegPanel.setBounds(0,0,850,650);


            main.removeAll();
            main.repaint();
            main.revalidate();

            main.add(empRegPanel);
            main.repaint();
            main.revalidate();
        });
        user.addActionListener(X->{
            JPanel userPanel= User.getUserReg(this);
            userPanel.setBounds(50,50,700,400);

            main.removeAll();
            main.repaint();
            main.revalidate();

            main.add(userPanel);
            main.repaint();
            main.revalidate();
        });
        changePass.addActionListener(X->{
            JPanel userPanel= User.passChange(this,"admin");
            userPanel.setBounds(50,50,700,400);

            main.removeAll();
            main.repaint();
            main.revalidate();

            main.add(userPanel);
            main.repaint();
            main.revalidate();
        });

        studDetails.addActionListener(X->{
            JPanel table= TableClass.getTable("Students Records :","Student",this, false,false);
            table.setBounds(0,0,850,650);
            main.removeAll();
            main.repaint();
            main.revalidate();

            main.add(table);
            main.repaint();
            main.revalidate();
        });
        empDetails.addActionListener(X->{
            JPanel table=TableClass.getTable("Employee Records :","Employee",this, false, false);
            table.setBounds(0,0,850,600);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(table);
            main.repaint();
            main.revalidate();
        });
        feesDetails.addActionListener(X->{
            JPanel table=TableClass.getTable("Fees Records :","Fees",this, false, false);
            table.setBounds(0,0,850,600);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(table);
            main.repaint();
            main.revalidate();
        });
        feesEntry.addActionListener(X->{
            JPanel feesRegPanel= Fees.getFeesPanel(false,"",this,"admin");
            feesRegPanel.setBounds(30,30,800,600);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(feesRegPanel);
            main.repaint();
            main.revalidate();
        });
        payrollDetails.addActionListener(X->{
            JPanel table=TableClass.getTable("Payroll Records :","Payroll",this, false, false);
            table.setBounds(0,0,850,650);
            main.removeAll();
            main.repaint();
            main.revalidate();

            main.add(table);
            main.repaint();
            main.revalidate();

        });
      
        return mb;
    }
}
