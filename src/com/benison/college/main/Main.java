package com.benison.college.main;

import com.benison.college.common.ComponentFactory;
import com.benison.college.common.Login;
import com.benison.college.common.ObjectFactory;
import com.benison.college.database.DBConnection;
import com.benison.college.security.AES;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends JFrame
{
    public static String user_id;
    private   JPanel header,loginpanel;
    private JLabel uname,pass,bg;
    private JTextField unametxt;
    private   JPasswordField passtxt;
    private JCheckBox show;
    private JButton login;

   public Main(){
       setLayout(null);
       Font f1=new Font("arial",Font.PLAIN,16);
       bg=ComponentFactory.getBackground();
       header= ComponentFactory.getLoginHeader();

       // Login panel
       loginpanel=new JPanel();
       loginpanel.setBackground(new Color(0,0,0,80));
       loginpanel.setBounds(180,200,500,400);
       loginpanel.setLayout(null);

       JLabel loginbg=ComponentFactory.getLabel("Login ");
       loginbg.setBounds(200,50,200,50);
       loginbg.setFont(new Font("Arial",Font.BOLD,25));

       uname=ComponentFactory.getLabel("User Name:");
       uname.setBounds(100,130,150,30);

       unametxt=ComponentFactory.getTextFiled();
       unametxt.setBounds(250,130,150,30);


       pass=ComponentFactory.getLabel("Passwords:");
       pass.setBounds(100,180,150,30);

       passtxt=new JPasswordField();
       passtxt.setBounds(250,180,150,30);
       passtxt.setForeground(Color.BLACK);
       passtxt.setFont(new Font("Times New Roman",Font.PLAIN,16));

       show=new JCheckBox();
       show.setBounds(130,05,20,20);
       show.setToolTipText("Show Passwords");
       show.addActionListener(X->{ if (show.isSelected()){passtxt.setEchoChar((char) 0);} else {passtxt.setEchoChar('*');}});

       passtxt.add(show);

       login=new JButton("Login");
       login.setBounds(200,260,100,30);
       login.setFont(f1);
       login.addActionListener(X->loginProcess());

       loginpanel.add(loginbg);
       loginpanel.add(uname);loginpanel.add(unametxt);
       loginpanel.add(pass);loginpanel.add(passtxt);
       loginpanel.add(login);

        bg.add(header);
        bg.add(loginpanel);
        add(bg);
        ComponentFactory.setFrame("College ERP",this);

   }

    private void loginProcess() {

        String s1=unametxt.getText();
        String s2=passtxt.getText();
        if(s1.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please Enter Username","Error",JOptionPane.ERROR_MESSAGE);
        }else if(s2.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please Enter Password","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
               try(Connection con= DBConnection.getInstance().getConnection()) {
                   String pass=passtxt.getText();
                   String encryptPass= AES.encrypt(pass,"Benison");
                       Statement st = con.createStatement();
                       ResultSet rs = st.executeQuery("select  *from login where username='" + s1 + "'and password='"+encryptPass+"'");
                       if (rs.next()) {
                           String decryptPass=AES.decrypt(rs.getString(4),"Benison");
                           user_id=rs.getString(1);
                           if(s1.equals(rs.getString("username"))&&pass.equals(decryptPass)) {
                               JOptionPane.showMessageDialog(null, "Login Successfully", "Success", JOptionPane.PLAIN_MESSAGE);
                               Login l=ObjectFactory.getLogin(rs.getString("role"));
                               ComponentFactory.close(this);
                           }else{
                               JOptionPane.showMessageDialog(null,"Opps..! Invalid Credential","Error",JOptionPane.ERROR_MESSAGE);
                           }
                       }else{
                           JOptionPane.showMessageDialog(null,"Sorry No Entry Available","Error",JOptionPane.ERROR_MESSAGE);
                       }
               } catch (SQLException e) {
                   e.printStackTrace();
               } catch (ClassNotFoundException e) {
                   e.printStackTrace();
               }
      }
    }
    public static void main(String[] args) {
        new Main();
    }
}
