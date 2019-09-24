package com.benison.college.registartion;

import com.benison.college.admin.AdminLogin;
import com.benison.college.common.ComponentFactory;
import com.benison.college.database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Students {

    private static JLabel title,id,name,sclass,paidFees,totalFees,mobile,mail,gender,doa,dob,address,caste,remFees;
    private static JTextField idtxt,nametxt,classtxt,paidFeestxt,totalFeestxt,mobiletxt,mailtxt,remFeestxt,doatxt,dobtxt,castetxt;
    private static   JTextArea addresstxt;
    private  static ButtonGroup bg;
    private static JRadioButton male,female,other;
    private static String Data = null;
    private  static LinkedList<String> list;
    private static JFrame frame;
    public static JPanel getReg(boolean edit,String data,JFrame f){
        frame=f;
        Data=data;
        // reg panel
        JPanel regPanel=new JPanel();
        regPanel.setLayout(null);
        regPanel.setBackground(new Color(100,10,02,80));

        //defile labels
        title= ComponentFactory.getLabel("Student Registration Form");
        title.setBounds(250,0,400,50);
        title.setForeground(Color.RED);
        title.setFont(new Font("Arial",Font.BOLD,26));
        regPanel.add(title);

        id=ComponentFactory.getLabel("Enrollment No. :");
        id.setBounds(200,60,150,30);

        idtxt=ComponentFactory.getTextFiled();
        idtxt.setBounds(360,60,250,30);
        regPanel.add(id);
        regPanel.add(idtxt);

        name=ComponentFactory.getLabel("Student Name :      ");
        name.setBounds(200,100,150,30);

        nametxt=ComponentFactory.getTextFiled();
        nametxt.setBounds(360,100,250,30);
        regPanel.add(name);
        regPanel.add(nametxt);

        sclass=ComponentFactory.getLabel("Class :  ");
        sclass.setBounds(200,140,150,30);

        classtxt=ComponentFactory.getTextFiled();
        classtxt.setBounds(360,140,250,30);
        regPanel.add(sclass);
        regPanel.add(classtxt);

        gender=new ComponentFactory().getLabel("Gender :");
        gender.setBounds(200,180,150,30);

        male = new JRadioButton("Male");
        male.setBounds(360,180,80,30);
        female = new JRadioButton("Female");
        female.setBounds(450,180,80,30);

        other = new JRadioButton("Other");
        other.setBounds(540,180,80,30);

        bg=new ButtonGroup();
        bg.add(male);bg.add(female);bg.add(other);
        regPanel.add(gender);
        regPanel.add(male);regPanel.add(female);regPanel.add(other);

        dob=ComponentFactory.getLabel("Date Of Birth :");
        dob.setBounds(200,220,150,30);

        dobtxt=ComponentFactory.getTextFiled();
        dobtxt.setBounds(360,220,250,30);
        regPanel.add(dob);
        regPanel.add(dobtxt);

        doa=ComponentFactory.getLabel("Admission Date : ");
        doa.setBounds(200,260,150,30);

        doatxt=ComponentFactory.getTextFiled();;
        doatxt.setBounds(360,260,250,30);
        regPanel.add(doa);
        regPanel.add(doatxt);


        mail=ComponentFactory.getLabel("Mail Id");
        mail.setBounds(200,300,150,30);

        mailtxt=ComponentFactory.getTextFiled();
        mailtxt.setBounds(360,300,250,30);
        regPanel.add(mail);
        regPanel.add(mailtxt);

        address=ComponentFactory.getLabel("Address :");
        address.setBounds(200,340,150,30);

        addresstxt=new JTextArea();
        addresstxt.setBounds(360,340,250,30);
        regPanel.add(address);
        regPanel.add(addresstxt);

        caste=ComponentFactory.getLabel("Caste :");
        caste.setBounds(200,380,150,30);

        castetxt=ComponentFactory.getTextFiled();
        castetxt.setBounds(360,380,250,30);
        regPanel.add(caste);
        regPanel.add(castetxt);

        mobile=ComponentFactory.getLabel("Mobile No. :");
        mobile.setBounds(200,420,150,30);

        mobiletxt=ComponentFactory.getTextFiled();
        mobiletxt.setBounds(360,420,250,30);
        regPanel.add(mobile);
        regPanel.add(mobiletxt);

        totalFees=ComponentFactory.getLabel("totalFees :");
        totalFees.setBounds(200,460,150,30);

        totalFeestxt=ComponentFactory.getTextFiled();
        totalFeestxt.setBounds(360,460,250,30);
        totalFeestxt.setText("0");


        paidFees=ComponentFactory.getLabel("Paid Fees");
        paidFees.setBounds(200,500,150,30);

        paidFeestxt=ComponentFactory.getTextFiled();
        paidFeestxt.setBounds(360,500,250,30);
        paidFeestxt.setText("0");

        remFees=ComponentFactory.getLabel("Remaining Fees");
        remFees.setBounds(200,540,150,20);

        remFeestxt=ComponentFactory.getTextFiled();
        remFeestxt.setBounds(360,540,250,30);
        remFeestxt.setEditable(false);
        int total=Integer.parseInt(totalFeestxt.getText());
        int paid=Integer.parseInt(paidFeestxt.getText());
        int rem=total-paid;
        String rem1=String.valueOf(rem);
        remFeestxt.setText(rem1);

        JButton submit=new JButton("Submit");
        submit.setBounds(400,580,100,30);
        submit.addActionListener(X->{
            if(edit){
                updateStudent();
            }else{
                regProcess();
                System.out.println("Test Success");
            }
        });
        if(edit){
            submit.setText("Update");
            submit.setBounds(400,500,100,30);
            viewStudent();
        }else{
            regPanel.add(totalFees);
            regPanel.add(totalFeestxt);
            regPanel.add(paidFees);
            regPanel.add(paidFeestxt);
            regPanel.add(remFees);
            regPanel.add(remFeestxt);
        }
        regPanel.add(submit);
        return regPanel;
    }
    private static void updateStudent() {
        String gender = null;
        if(male.isSelected()){
            gender="Male";
        }
        if(female.isSelected()){
            gender="Female";
        }
        if(other.isSelected()){
            gender="Other";
        }

        list=new LinkedList<>();
        list.add(idtxt.getText());
        list.add(nametxt.getText());
        list.add(classtxt.getText());
        list.add(gender);
        list.add(dobtxt.getText());
        list.add(doatxt.getText());
        list.add(mailtxt.getText());
        list.add(addresstxt.getText());
        list.add(castetxt.getText());
        list.add(mobiletxt.getText());

        AtomicInteger index= new AtomicInteger(1);
       if(validation(list)) {
           try (Connection con = DBConnection.getInstance().getConnection()) {
               String en_no = idtxt.getText();
               String sql = "UPDATE students SET en_no=?,name=?,class=?,gender=?,dob=?,date_of_addmission=?,email=?,address=?,caste=?,mobile_no=? WHERE en_no=?";
               PreparedStatement ps = con.prepareStatement(sql);
               list.stream().forEach(X -> {
                   try {

                       if (index.get() <= list.size()) {
                           ps.setString(index.get(), X);
                           index.getAndIncrement();
                       }

                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               });
               ps.setString(11, en_no);
               int i = ps.executeUpdate();

               if (i > 0) {
                   JOptionPane.showMessageDialog(new JFrame(), "Updated  successfully", "Success", JOptionPane.PLAIN_MESSAGE);
                   new AdminLogin();
                   ComponentFactory.close(frame);
               }
           } catch (SQLException | ClassNotFoundException ex) {
               JOptionPane.showMessageDialog(null,ex.getMessage());
           }
       }
       }
    private static void regProcess(){
        String gender = null;
        if(male.isSelected()){
            gender="Male";
        }
        if(female.isSelected()){
            gender="Female";
        }
        if(other.isSelected()){
            gender="Other";
        }
        int total=Integer.parseInt(totalFeestxt.getText());
        int paid=Integer.parseInt(paidFeestxt.getText());
        int rem=total-paid;
        String rem1=String.valueOf(rem);
        System.out.print(rem);
        remFeestxt.setText(rem1);
        list=new LinkedList<>();
        list.add(idtxt.getText());
        list.add(nametxt.getText());
        list.add(classtxt.getText());
        list.add(gender);
        list.add(dobtxt.getText());
        list.add(doatxt.getText());
        list.add(mailtxt.getText());
        list.add(addresstxt.getText());
        list.add(castetxt.getText());
        list.add(mobiletxt.getText());
        LinkedList<String> list1=new LinkedList<>();
        list1.add(idtxt.getText());
        list1.add(totalFeestxt.getText());
        list1.add(paidFeestxt.getText());
        list1.add(remFeestxt.getText());
        list1.add("NA");
       AtomicInteger index= new AtomicInteger(1);
       if(validation(list)){
        try(Connection con= DBConnection.getInstance().getConnection()){
            PreparedStatement ps=con.prepareStatement("insert into students values(?,?,?,?,?,?,?,?,?,?)");
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

            PreparedStatement ps1=con.prepareStatement("insert into fees values(?,?,?,?,?)");
            AtomicInteger index1= new AtomicInteger(1);
            list1.stream().forEach(X->{
                try {
                    if(index1.get() <=list1.size()){
                        ps1.setString(index1.get(),X);
                        System.out.println(index1+"/"+X);
                        index1.getAndIncrement();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            int i=ps.executeUpdate();
            int j=ps1.executeUpdate();
            if(i>0&&j>0)
            {
                JOptionPane.showMessageDialog(null,"Registration successfully","Success",JOptionPane.PLAIN_MESSAGE);
                new AdminLogin();
                ComponentFactory.close(frame);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Success",JOptionPane.PLAIN_MESSAGE);
        }
       }
    }

    public static boolean validation(LinkedList<String> list) {

        String id1=list.get(0);
        id1 = id1.replace(" ", "");
        boolean idvalid =id1.matches("[0-9]{1,10}");
        if(idvalid==false)
        {
            JOptionPane.showMessageDialog(null, " Enter ID in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        String mail = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(mail);
        Matcher matcher = pattern.matcher(mailtxt.getText());
        Boolean evalid= matcher.matches();
        if(evalid==false){
            JOptionPane.showMessageDialog(null, "Enter Mail in correct (like abc@xyz.com)", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String mobile1=mobiletxt.getText();
        mobile1 = mobile1.replace(" ", "");
        boolean valid =mobile1.matches("[0-9]{10}");
        if(valid==false)
        {
            JOptionPane.showMessageDialog(null, " Enter 10 Digit Mobile Number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String total=totalFeestxt.getText();
        total = total.replace(" ", "");
        boolean tvalid =total.matches("[0-9]{1,10}");
        if(tvalid==false)
        {
            JOptionPane.showMessageDialog(null, " Enter total fees in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String paid=paidFeestxt.getText();
        paid = paid.replace(" ", "");
        boolean pvalid =paid.matches("[0-9]{1,10}");
        if(pvalid==false)
        {
            JOptionPane.showMessageDialog(null, " Enter paid Fees in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private static void viewStudent() {
        try(Connection con= DBConnection.getInstance().getConnection())
        {
            String en_no=Data;
            String sql = "select *from students where en_no='" + en_no + "'";
            int count = 10;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            list=new LinkedList<>();

            if(rs.next())
            {
                if (rs.getString("gender").equals("Male")) {
                    male.setSelected(true);
                } else if(rs.getString("gender").equals("Female")) {
                    female.setSelected(true);
                }else{
                    other.setSelected(true);
                }
                idtxt.setText(rs.getString(1));
                nametxt.setText(rs.getString(2));
                classtxt.setText(rs.getString(3));;

                dobtxt.setText(rs.getString(5));
                doatxt.setText(rs.getString(6));
                mailtxt.setText(rs.getString(7));
                addresstxt.setText(rs.getString(8));
                castetxt.setText(rs.getString(9));
                mobiletxt.setText(rs.getString(10));


            }else{
                JOptionPane.showMessageDialog(null,"Invalid Employee Id","Error",JOptionPane.ERROR_MESSAGE);
            }

        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }

}
