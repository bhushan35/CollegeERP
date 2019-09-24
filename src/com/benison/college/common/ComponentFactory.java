package com.benison.college.common;

import com.benison.college.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class ComponentFactory {

    public static  JButton getLogout(JFrame f){
        JButton logout=new JButton("Logout");
        logout.setBounds(780,20,100,20);
        logout.addActionListener(X->{new Main();
            ComponentFactory.close(f);});
        return logout;
    }
    public static Image getIcon(){
        Image icon = Toolkit.getDefaultToolkit().getImage("E:\\REST API\\Benison\\ssiems.jpg");
        Image logo=icon.getScaledInstance(100,60,Image.SCALE_SMOOTH);
        return logo;
    }
    public static JLabel getLogo(){
        ImageIcon logo_img= new ImageIcon(getIcon());
        JLabel logo=new JLabel("",logo_img,JLabel.CENTER);
        logo.setBounds(0,0,100,60);
        logo.setBackground(new Color(0,0,0,80));
        return logo;
    }
    public static void close(JFrame obj){
        WindowEvent we=new WindowEvent(obj,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(we);
    }
    public static JPanel getHeader(JFrame frame){
        JPanel header=new JPanel();
        header.setBackground(new Color(120,20,50,80));
        header.setBounds(0,0,900,60);
        header.setLayout(null);
        Font f = new Font("Serif", Font.BOLD, 30);
        JLabel title=new JLabel("SSIEMS");
        title.setBounds(100, 0, 300, 40);
        title.setFont(f);
        title.setForeground(Color.red);

        JButton logout=new JButton("Logout");
        logout.setBounds(780,20,100,30);
        logout.addActionListener(X->{
            new Main();
            ComponentFactory.close(frame);});
        header.add(getLogo());
        header.add(title);
        header.add(logout);
        return header;
    }
    public static JPanel getLoginHeader(){
        JPanel header=new JPanel();
        header.setBackground(new Color(120,20,50,60));
        header.setBounds(0,0,900,80);
        header.setLayout(null);
        Font f = new Font("Serif", Font.BOLD, 30);

    /* Image icon = Toolkit.getDefaultToolkit().getImage("E:\\REST API\\Benison\\slogo.jpg");
        Image logo1=icon.getScaledInstance(900,80,Image.SCALE_SMOOTH);
        ImageIcon logo_img= new ImageIcon(logo1);*/

        JLabel title=new JLabel("SSIEMS");
        title.setBounds(100, 0, 900, 60);
        title.setFont(f);
        title.setForeground(Color.red);
        header.add(getLogo());
     header.add(title);


        return header;
    }
    public static JLabel getLabel(String name){
        JLabel lable=new JLabel(name);
        lable.setFont(new Font("arial",Font.BOLD,16));
        lable.setForeground(Color.black);
        return  lable;
    }
    public static JTextField getTextFiled(){
        JTextField text=new JTextField();
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Times New Roman",Font.PLAIN,16));
        return text;
    }
    public static void setFrame(String title,JFrame f){
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setSize(900,800);
        f.setIconImage(getIcon());
        f.setVisible(true);
        f.setTitle(title);
        f.setResizable(false);
    }
    public static JLabel getBackground(){
        ImageIcon bg_img = new ImageIcon("E:\\REST API\\Benison\\bg5.jpg");
        Image i=bg_img.getImage();
        Image icon=i.getScaledInstance(900,800,Image.SCALE_SMOOTH);
        bg_img=new ImageIcon(icon);
        JLabel background=new JLabel("",bg_img,JLabel.CENTER);
        background.setBounds(0,0,900,800);
        return background;
    }

}
