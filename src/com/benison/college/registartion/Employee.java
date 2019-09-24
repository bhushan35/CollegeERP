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

public class Employee {
    private static JLabel title,id,name,sclass,mobile,mail,gender,doa,dob,address,qualy,ex,sal,des;
    private static JTextField idtxt,nametxt,classtxt,gendertxt,mobiletxt,mailtxt,doatxt,dobtxt,destxt,qualytxt,extxt,saltxt;
    private static   JTextArea addresstxt;
    private  static ButtonGroup bg;
    private static JRadioButton male,female,other;
    private static String Data = null;
    private  static LinkedList<String> list;
    private static JFrame frame;
    public static JPanel getEmpReg(boolean edit, String data, JFrame f){
        frame=f;
        Data=data;
        // reg panel
        JPanel regPanel=new JPanel();
        regPanel.setLayout(null);
        regPanel.setBackground(new Color(100,10,02,80));

        //defile labels
        title= ComponentFactory.getLabel("Employee Registration Form");
        title.setBounds(250,0,400,50);
        title.setForeground(Color.RED);
        title.setFont(new Font("Arial",Font.BOLD,26));
        regPanel.add(title);

        id=ComponentFactory.getLabel("Employee Id :");
        id.setBounds(200,60,150,30);

        idtxt=ComponentFactory.getTextFiled();
        idtxt.setBounds(360,60,250,30);
        regPanel.add(id);
        regPanel.add(idtxt);

        name=ComponentFactory.getLabel("Employee Name :");
        name.setBounds(200,100,150,30);

        nametxt=ComponentFactory.getTextFiled();
        nametxt.setBounds(360,100,250,30);
        regPanel.add(name);
        regPanel.add(nametxt);

        sclass=ComponentFactory.getLabel("Department :  ");
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

        doa=ComponentFactory.getLabel("Joining Date : ");
        doa.setBounds(200,260,150,30);

        doatxt=ComponentFactory.getTextFiled();;
        doatxt.setBounds(360,260,250,30);
        regPanel.add(doa);
        regPanel.add(doatxt);


        qualy=ComponentFactory.getLabel(" Qualifications : ");
        qualy.setBounds(200,300,150,30);

        qualytxt=ComponentFactory.getTextFiled();
        qualytxt.setBounds(360,300,250,30);
        regPanel.add(qualy);
        regPanel.add(qualytxt);

        ex=ComponentFactory.getLabel("Experience :");
        ex.setBounds(200,340,150,30);

        extxt=ComponentFactory.getTextFiled();
        extxt.setBounds(360,340,250,30);
        regPanel.add(ex);
        regPanel.add(extxt);

        sal=ComponentFactory.getLabel("Salary :");
        sal.setBounds(200,380,150,30);

        saltxt=ComponentFactory.getTextFiled();
        saltxt.setBounds(360,380,250,30);
        regPanel.add(sal);
        regPanel.add(saltxt);

        des=ComponentFactory.getLabel("Designation:");
        des.setBounds(200,420,150,30);

        destxt= ComponentFactory.getTextFiled();
        destxt.setBounds(360,420,250,30);
        regPanel.add(des);
        regPanel.add(destxt);

        address=ComponentFactory.getLabel("Address :");
        address.setBounds(200,460,150,30);

        addresstxt=new JTextArea();
        addresstxt.setBounds(360,460,250,30);
        regPanel.add(address);
        regPanel.add(addresstxt);

        mobile=ComponentFactory.getLabel("Mobile No. :");
        mobile.setBounds(200,500,150,30);

        mobiletxt=ComponentFactory.getTextFiled();
        mobiletxt.setBounds(360,500,250,30);
        regPanel.add(mobile);
        regPanel.add(mobiletxt);

        mail=ComponentFactory.getLabel("EMail Id. :");
        mail.setBounds(200,540,150,20);

        mailtxt=ComponentFactory.getTextFiled();
        mailtxt.setBounds(360,540,250,30);
        regPanel.add(mail);
        regPanel.add(mailtxt);


        JButton submit=new JButton("Submit");
        submit.setBounds(400,580,100,30);
        submit.addActionListener(X->{
            empRegProcess(edit);
        });
        if(edit){
            submit.setText("Update");
            viewEmployee();
        }else{

        }

        regPanel.add(submit);
        return regPanel;
    }
    private static void viewEmployee() {
        try(Connection con= DBConnection.getInstance().getConnection())
        {
            String en_no=Data;
            System.out.println(Data);
            String sql = "select *from employee where eid='" + en_no + "'";

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
                qualytxt.setText(rs.getString(7));
                extxt.setText(rs.getString(8));
                saltxt.setText(rs.getString(9));
                destxt.setText(rs.getString(10));
                addresstxt.setText(rs.getString(11));
                mobiletxt.setText(rs.getString(12));
                mailtxt.setText(rs.getString(13));

            }else{
                JOptionPane.showMessageDialog(null,"Invallid Employee Id","Error",JOptionPane.ERROR_MESSAGE);
            }

        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }
    private static void empRegProcess(boolean edit){
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
        list.add(qualytxt.getText());
        list.add(extxt.getText());
        list.add(saltxt.getText());
        list.add(destxt.getText());
        list.add(addresstxt.getText());
        list.add(mobiletxt.getText());
        list.add(mailtxt.getText());
        AtomicInteger index= new AtomicInteger(1);
        if(validation(list)){try(Connection con= DBConnection.getInstance().getConnection()){
            String sql=null;
            if(edit){
                sql="UPDATE `employee` SET `eid` = ?, `name` = ?, `dept` = ?, `gender` = ?, `dob` = ?, `date-of-joining` = ?, `qualification` = ?, `experience` = ?, `salary` = ?, `designation` = ?, `address` = ?, `mobile` = ?, `email` = ? WHERE `eid` = ?";
            }else{
                sql="insert into employee values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }
            PreparedStatement ps=con.prepareStatement(sql);
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
            if(edit){
                String eid=idtxt.getText();
                ps.setString(14,eid);
            }
            int i=ps.executeUpdate();
            if(i>0)
            {
                if(edit){
                    JOptionPane.showMessageDialog(null,"Update Sucessfully");
                }else{
                    JOptionPane.showMessageDialog(null,"Register Sucessfully");
                }
                new AdminLogin();
                ComponentFactory.close(frame);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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

        String sal=saltxt.getText();
        id1 = id1.replace(" ", "");
        boolean svalid =sal.matches("[0-9]{1,10}");
        if(svalid==false)
        {
            JOptionPane.showMessageDialog(null, " Enter Salary in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
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
        return true;
    }

}
