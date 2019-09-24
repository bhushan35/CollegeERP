package com.benison.college.admin;

import com.benison.college.common.ComponentFactory;
import com.benison.college.common.Login;
import com.benison.college.main.Main;
import com.benison.college.registartion.Employee;
import com.benison.college.registartion.Fees;
import com.benison.college.registartion.Students;
import com.benison.college.registartion.User;
import com.benison.college.tables.TableClass;

import javax.swing.*;
import java.awt.*;

public class AdminLogin extends JFrame implements Login {
    private JPanel main,header;
    private JLabel bg;
    public AdminLogin(){
        bg=ComponentFactory.getBackground();
        header= ComponentFactory.getHeader(this);
        JRootPane rootPane=new JRootPane();
        rootPane.setBounds(0,80,900,28);

        main=new JPanel();
        main.setBounds(20,100,850,650);
        main.setBackground(new Color(0,0,0,80));
        main.setLayout(null);
        System.out.println(Main.user_id);
        JPanel studtable=TableClass.getTable("Student Records:","Student",this, false, false);
        studtable.setBounds(0,0,850,600);

        main.add(studtable);

        bg.add(header);
        rootPane.setJMenuBar(menubar());
        bg.add(rootPane);
        bg.add(main);
        add(bg);
        ComponentFactory.setFrame("Admin Page",this);
        setLayout(null);
    }

    @Override
    public JMenuBar menubar(){
        JMenu home, reg,details,account,actions;
        JMenuItem studReg, empReg, studDetails, empDetails, feesDetails,feesEntry,payrollDetals,user,changePass;
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
        payrollDetals=new JMenuItem("Payroll");
        feesDetails=new JMenuItem("Students Fees");
        feesEntry=new JMenuItem("Fees Entry");
        user=new JMenuItem("Add User");
        changePass=new JMenuItem("Change Password");

        reg.add(studReg);reg.add(new JSeparator()); reg.add(empReg);
        details.add(studDetails);details.add(new JSeparator()); details.add(empDetails);details.add(new JSeparator()); details.add(payrollDetals);
        account.add(feesEntry);account.add(new JSeparator());account.add(feesDetails);
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
            JPanel empRegPanel= Employee.getEmpReg(false," ", this);
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
            JPanel table= TableClass.getTable("Students Records :","Student",this, false, false);
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

            JPanel table=TableClass.getTable("Fees Records :","Fees",this, false, true);
            table.setBounds(0,0,850,600);
        });
        payrollDetals.addActionListener(X->{
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
