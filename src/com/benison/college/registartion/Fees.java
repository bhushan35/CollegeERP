package com.benison.college.registartion;

import com.benison.college.account.AccountLogin;
import com.benison.college.admin.AdminLogin;
import com.benison.college.common.ComponentFactory;
import com.benison.college.common.Login;
import com.benison.college.common.ObjectFactory;
import com.benison.college.database.DBConnection;
import com.benison.college.exceptions.FeesPaidGreater;

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

public class Fees {

    private static JLabel title,id,name,sclass,paidFees,totalFees,paidAmt,mobile,gender,remFees;
    private static JTextField idtxt,nametxt,classtxt,paidAmttxt,gendertxt,paidFeestxt,totalFeestxt,mobiletxt,remFeestxt;
    private static String Data = null;
    private static JFrame frame;
    public static JPanel getFeesPanel(boolean edit,String data,JFrame f,String type){
        frame=f;
        JPanel regPanel=new JPanel();
        regPanel.setLayout(null);
        regPanel.setBackground(new Color(100,10,02,80));

        //defile labels
        title= ComponentFactory.getLabel("Student Fees Entry");
        title.setBounds(250,0,400,50);
        title.setForeground(Color.RED);
        title.setFont(new Font("Arial",Font.BOLD,26));
        regPanel.add(title);

        id=ComponentFactory.getLabel("Enrollment No. :");
        id.setBounds(200,60,150,30);

        idtxt=ComponentFactory.getTextFiled();
        idtxt.setBounds(360,60,250,30);
        idtxt.addActionListener(X->viewFees(edit,"data"));
        regPanel.add(id);
        regPanel.add(idtxt);

        name=ComponentFactory.getLabel("Student Name :   ");
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

        gendertxt =ComponentFactory.getTextFiled();
        gendertxt.setBounds(360,180,250,30);
        regPanel.add(gender);
        regPanel.add(gendertxt);

        mobile=ComponentFactory.getLabel("Mobile No. :");
        mobile.setBounds(200,220,150,30);

        mobiletxt=ComponentFactory.getTextFiled();
        mobiletxt.setBounds(360,220,250,30);
        regPanel.add(mobile);
        regPanel.add(mobiletxt);

        totalFees=ComponentFactory.getLabel("Total Fees : ");
        totalFees.setBounds(200,260,150,30);

        totalFeestxt=ComponentFactory.getTextFiled();;
        totalFeestxt.setBounds(360,260,250,30);
        regPanel.add(totalFees);
        regPanel.add(totalFeestxt);

        paidFees=ComponentFactory.getLabel("Paid Fees : ");
        paidFees.setBounds(200,300,150,30);

        paidFeestxt=ComponentFactory.getTextFiled();;
        paidFeestxt.setBounds(360,300,250,30);
        regPanel.add(paidFees);
        regPanel.add(paidFeestxt);

        remFees=ComponentFactory.getLabel("Remaining Fees : ");
        remFees.setBounds(200,340,150,30);

        remFeestxt=ComponentFactory.getTextFiled();;
        remFeestxt.setBounds(360,340,250,30);
        regPanel.add(remFees);
        regPanel.add(remFeestxt);

        paidAmt = ComponentFactory.getLabel("Pay Amount : ");
        paidAmt.setBounds(200,380,150,30);
        paidAmttxt=ComponentFactory.getTextFiled();;
        paidAmttxt.setBounds(360,380,250,30);
        regPanel.add(paidAmt);
        regPanel.add(paidAmttxt);

        JButton submit=new JButton("Add Entry");
        submit.setBounds(400,450,100,30);
        submit.addActionListener(X->updateEntry(edit,type));
        if(edit){
            submit.setText("Update");
            viewFees(edit,data);

            System.out.println(data);
        }
        regPanel.add(submit);
        return regPanel;
    }
    private static void updateEntry(boolean edit, String role) {

        if(validation()){
            try (Connection con = DBConnection.getInstance().getConnection()) {
            int total = Integer.parseInt(totalFeestxt.getText());
            String en_no = idtxt.getText();
            int paid = Integer.parseInt(paidFeestxt.getText()) + Integer.parseInt(paidAmttxt.getText());
            int rem = total - paid;
            int tol = paid + rem;
            System.out.println(tol + " " + rem);
            if (paid > total) {
                try {
                    throw new FeesPaidGreater("Sorry You Enter Greater Amount than Total");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "you enter greater than remaining fees");
                    Login l = ObjectFactory.getLogin(role);
                    ComponentFactory.close(frame);
                    return;

                }
            }
            String paidFees = String.valueOf(paid);
            String remFees = String.valueOf(rem);

            //   String sql = ;
            PreparedStatement ps = con.prepareStatement("UPDATE fees  SET paid_fees=?,rem_fees=? WHERE en_no =?");
            ps.setString(1, paidFees);
            ps.setString(2, remFees);
            ps.setString(3, en_no);
            int i = ps.executeUpdate();

            if (i > 0) {
                if (edit) {
                    JOptionPane.showMessageDialog(null, "Update Successfully", "Success", JOptionPane.PLAIN_MESSAGE);
                    Login l = ObjectFactory.getLogin(role);
                    ComponentFactory.close(frame);
                } else {
                    JOptionPane.showMessageDialog(null, "Entry Added Successfully", "Success", JOptionPane.PLAIN_MESSAGE);
                    Login l = ObjectFactory.getLogin(role);
                    ComponentFactory.close(frame);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Enrollment No", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    } }
    private static void viewFees(boolean edit, String data) {
        try(Connection con= DBConnection.getInstance().getConnection())
        {
            String en_no=null;
            if(edit){
                en_no=data;
            }else{
                en_no=idtxt.getText();
            }

            String sql = "select en_no,name,class,gender,mobile_no,total_fees,paid_fees,rem_fees from students natural join fees where en_no='" + en_no + "'";
          int  count=8;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                idtxt.setText(rs.getString(1));
                nametxt.setText(rs.getString(2));
                classtxt.setText(rs.getString(3));
                gendertxt.setText(rs.getString(4));
                mobiletxt.setText(rs.getString(5));
                totalFeestxt.setText(rs.getString(6));
                paidFeestxt.setText(rs.getString(7));
                remFeestxt.setText(rs.getString(8));
                nametxt.setEditable(false);
                classtxt.setEditable(false);
                gendertxt.setEditable(false);
                mobiletxt.setEditable(false);
                totalFeestxt.setEditable(false);
                paidFeestxt.setEditable(false);
                remFeestxt.setEditable(false);


            }else{
                JOptionPane.showMessageDialog(null,"Invalid Enrollment No","Error",JOptionPane.ERROR_MESSAGE);
            }

        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }
    public static boolean validation() {

        String id1=idtxt.getText();
        id1 = id1.replace(" ", "");
        boolean idvalid =id1.matches("[0-9]{1,10}");
        if(idvalid==false)
        {
            JOptionPane.showMessageDialog(null, " Enter ID in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
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
        String paidamt=paidAmttxt.getText();
        paidamt = paidamt.replace(" ", "");
        boolean pvalidamt =paidamt.matches("[0-9]{1,10}");
        if(pvalidamt==false)
        {
            JOptionPane.showMessageDialog(null, " Enter  Fees in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    }
