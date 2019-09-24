package com.benison.college.registartion;

import com.benison.college.account.AccountLogin;
import com.benison.college.admin.AdminLogin;
import com.benison.college.common.ComponentFactory;
import com.benison.college.common.Login;
import com.benison.college.common.ObjectFactory;
import com.benison.college.database.DBConnection;
import com.benison.college.main.Main;
import com.benison.college.security.AES;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
   private static JLabel  title,id, name, uname, pass, role,oldPass,conformPass;
   private static  JTextField idtxt,nametxt,unametxt,roletxt;
   private static JPasswordField oldPasstxt,passtxt,conformPasstxt;
   private static JFrame frame;
    public static JPanel getUserReg(JFrame f){
        frame=f;
        JPanel regPanel=new JPanel();
        regPanel.setLayout(null);
        regPanel.setBackground(new Color(100,10,02,80));

        //defile labels
        title= ComponentFactory.getLabel("User Registration");
        title.setBounds(250,0,400,50);
        title.setForeground(Color.RED);
        title.setFont(new Font("Arial",Font.BOLD,26));
        regPanel.add(title);

        id=ComponentFactory.getLabel("User Id. :");
        id.setBounds(200,60,150,30);

        idtxt=ComponentFactory.getTextFiled();
        idtxt.setBounds(360,60,250,30);
        regPanel.add(id);
        regPanel.add(idtxt);

        name=ComponentFactory.getLabel(" Name :   ");
        name.setBounds(200,100,150,30);

        nametxt=ComponentFactory.getTextFiled();
        nametxt.setBounds(360,100,250,30);
        regPanel.add(name);
        regPanel.add(nametxt);

        uname=ComponentFactory.getLabel("Username:  ");
        uname.setBounds(200,140,150,30);

        unametxt=ComponentFactory.getTextFiled();
        unametxt.setBounds(360,140,250,30);
        regPanel.add(uname);
        regPanel.add(unametxt);

        pass=new ComponentFactory().getLabel("Password :");
        pass.setBounds(200,180,150,30);

        passtxt =new JPasswordField();
        passtxt.setBounds(360,180,250,30);
        regPanel.add(pass);
        regPanel.add(passtxt);

        role=ComponentFactory.getLabel("role:");
        role.setBounds(200,220,150,30);

        roletxt=ComponentFactory.getTextFiled();
        roletxt.setBounds(360,220,250,30);
        regPanel.add(role);
        regPanel.add(roletxt);

        JButton submit=new JButton("Register");
        submit.setBounds(400,260,100,30);
        submit.addActionListener(X->userReg());
        regPanel.add(submit);

        return regPanel;
    }

    private static void userReg() {
        LinkedList<String> list=new LinkedList<>();
        list.add(idtxt.getText());
        list.add(nametxt.getText());
        list.add(unametxt.getText());
        String encryptPass= AES.encrypt(passtxt.getText(),"Benison");
        list.add(encryptPass);
        list.add(roletxt.getText());
        try(Connection con= DBConnection.getInstance().getConnection()){
            String sql=null;
            sql="insert into login values(?,?,?,?,?)";

            PreparedStatement ps=con.prepareStatement(sql);
            AtomicInteger index= new AtomicInteger(1);
            list.stream().forEach(X->{
                try {

                    if(index.get() <=list.size()){
                        ps.setString(index.get(),X);
                        index.getAndIncrement();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            int i=ps.executeUpdate();
            if(i>0)
            {
                JOptionPane.showMessageDialog(null,"Registration Successfully");
                new AdminLogin();
                ComponentFactory.close(frame);

            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public static JPanel passChange(JFrame f,String type){
        frame=f;
        JPanel regPanel=new JPanel();
        regPanel.setLayout(null);
        regPanel.setBackground(new Color(100,10,02,80));

        //defile labels
        title= ComponentFactory.getLabel("Change Password");
        title.setBounds(250,0,400,50);
        title.setForeground(Color.RED);
        title.setFont(new Font("Arial",Font.BOLD,26));
        regPanel.add(title);

        oldPass=ComponentFactory.getLabel("Old Password :");
        oldPass.setBounds(200,60,150,30);

        oldPasstxt=new JPasswordField();
        oldPasstxt.setBounds(360,60,250,30);
        regPanel.add(oldPass);
        regPanel.add(oldPasstxt);

        pass=ComponentFactory.getLabel("New Password :");
        pass.setBounds(200,100,150,30);

        passtxt=new JPasswordField();
        passtxt.setBounds(360,100,250,30);
        regPanel.add(pass);
        regPanel.add(passtxt);

        conformPass=ComponentFactory.getLabel("Conform Password:  ");
        conformPass.setBounds(200,140,150,30);

        conformPasstxt=new JPasswordField();
        conformPasstxt.setBounds(360,140,250,30);
        regPanel.add(conformPass);
        regPanel.add(conformPasstxt);

        JButton submit=new JButton("Change");
        submit.setBounds(400,200,100,30);
        submit.addActionListener(X->changePass(type));
        regPanel.add(submit);

        return regPanel;
    }

    private static void changePass(String type) {
        String old=oldPasstxt.getText();
        String newPass=passtxt.getText();
        String conform=conformPasstxt.getText();
        
        if(!newPass.equals(conform)){
            JOptionPane.showMessageDialog(null,"Password Does not Match");
            passtxt.setText("");
            conformPasstxt.setText("");
        }else {
            try (Connection con= DBConnection.getInstance().getConnection()) {
                String sql;
                String uid= Main.user_id;
                String encryptPass= AES.encrypt(old,"Benison");
                sql = "update login set password=? where user_id=? and password=?";
                String encPass= AES.encrypt(newPass,"Benison");
                System.out.println(encPass);
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, encPass);
                ps.setString(2, uid);
                ps.setString(3, encryptPass);
                int i = ps.executeUpdate();
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "Password Change Successfully");
                    Login l= ObjectFactory.getLogin(type);
                    ComponentFactory.close(frame);
                }else
                {
                    JOptionPane.showMessageDialog(null,"Old Password Does not Match","Error",JOptionPane.ERROR_MESSAGE);
                    oldPasstxt.setText("");
                    passtxt.setText("");
                    conformPasstxt.setText("");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private User() {

    }
}
