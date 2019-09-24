package com.benison.college.registartion;

import com.benison.college.account.AccountLogin;
import com.benison.college.admin.AdminLogin;
import com.benison.college.common.ComponentFactory;
import com.benison.college.common.Login;
import com.benison.college.common.ObjectFactory;
import com.benison.college.database.DBConnection;
import com.benison.college.exceptions.SalaryException;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Payroll {

    private static JLabel title,id,name,dept,paidSal,totalSal,paidBy,mobile,gender,date,month,wday,deduct;
    private static JTextField idtxt,nametxt,classtxt,paidBytxt,gendertxt,paidSaltxt,datetxt,totalSaltxt,mobiletxt,remFeestxt,monthtxt,wdaytxt,deducttxt;
    private static String Data = null;
    private boolean edit;
    private static  JFrame frame;
    public static JPanel getSalPanel(boolean edit,String data,JFrame f,String type){
        frame=f;
        JPanel regPanel=new JPanel();
        regPanel.setLayout(null);
        regPanel.setBackground(new Color(100,10,02,80));

        //defile labels
        title= ComponentFactory.getLabel("Salary Pay");
        title.setBounds(300,0,400,50);
        title.setForeground(Color.RED);
        title.setFont(new Font("Arial",Font.BOLD,26));
        regPanel.add(title);

        id=ComponentFactory.getLabel("Employee Id. :");
        id.setBounds(200,60,150,30);

        idtxt=ComponentFactory.getTextFiled();
        idtxt.setBounds(360,60,250,30);
        idtxt.addActionListener(X->viewEmp(edit,"data"));
        regPanel.add(id);
        regPanel.add(idtxt);

        name=ComponentFactory.getLabel("Name :   ");
        name.setBounds(200,100,150,30);

        nametxt=ComponentFactory.getTextFiled();
        nametxt.setBounds(360,100,250,30);
        regPanel.add(name);
        regPanel.add(nametxt);

        dept=ComponentFactory.getLabel("Department :  ");
        dept.setBounds(200,140,150,30);

        classtxt=ComponentFactory.getTextFiled();
        classtxt.setBounds(360,140,250,30);
        regPanel.add(dept);
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

        totalSal=ComponentFactory.getLabel("Total Salary : ");
        totalSal.setBounds(200,260,150,30);

        totalSaltxt=ComponentFactory.getTextFiled();;
        totalSaltxt.setBounds(360,260,250,30);
        regPanel.add(totalSal);
        regPanel.add(totalSaltxt);

        month=ComponentFactory.getLabel("Month  : ");
        month.setBounds(200,300,150,30);

        monthtxt=ComponentFactory.getTextFiled();;
        monthtxt.setBounds(360,300,250,30);
        regPanel.add(month);
        regPanel.add(monthtxt);

        wday=ComponentFactory.getLabel("Working Days : ");
        wday.setBounds(200,340,150,30);

        wdaytxt=ComponentFactory.getTextFiled();;
        wdaytxt.setBounds(360,340,250,30);
        regPanel.add(wday);
        regPanel.add(wdaytxt);

        deduct = ComponentFactory.getLabel("Deduction : ");
        deduct.setBounds(200,380,150,30);

        deducttxt=ComponentFactory.getTextFiled();;
        deducttxt.setBounds(360,380,250,30);
        regPanel.add(deduct);
        regPanel.add(deducttxt);

        paidSal = ComponentFactory.getLabel("Paid Salary : ");
        paidSal.setBounds(200,420,150,30);

        paidSaltxt=ComponentFactory.getTextFiled();;
        paidSaltxt.setBounds(360,420,250,30);
        regPanel.add(paidSal);
        regPanel.add(paidSaltxt);

        paidBy = ComponentFactory.getLabel("Paid By : ");
        paidBy.setBounds(200,460,150,30);

        paidBytxt=ComponentFactory.getTextFiled();;
        paidBytxt.setBounds(360,460,250,30);
        regPanel.add(paidBy);
        regPanel.add(paidBytxt);

        date = ComponentFactory.getLabel("Date : ");
        date.setBounds(200,510,150,30);

        datetxt=ComponentFactory.getTextFiled();;
        datetxt.setBounds(360,510,250,30);
        regPanel.add(date);
        regPanel.add(datetxt);

        JButton submit=new JButton("Pay");
        submit.setBounds(400,550,100,30);
        submit.addActionListener(X->updateEntry(edit,type));
        if(edit){
            submit.setText("Update");
            viewEmp(edit,data);
            idtxt.setEditable(false);
            System.out.println(data);
        }
        regPanel.add(submit);
        return regPanel;
    }
    private static void updateEntry(boolean edit,String role) {

        if(validation()) {
            try (Connection con = DBConnection.getInstance().getConnection()) {
                String eid = idtxt.getText();
                String month=monthtxt.getText();
                AtomicInteger index = new AtomicInteger(1);
                int sal = Integer.parseInt(totalSaltxt.getText());
                int paid = Integer.parseInt(paidSaltxt.getText());
                if (paid > sal) {
                    try {
                        throw new SalaryException("Paid Salary greater Than Total");
                    } catch (SalaryException e) {
                        JOptionPane.showMessageDialog(null, "you enter greater than remaining fees");
                        Login l = ObjectFactory.getLogin(role);
                        ComponentFactory.close(frame);
                        return;
                    }
                }

                LinkedList<String> list = new LinkedList<>();
                list.add(idtxt.getText());
                list.add(monthtxt.getText());
                list.add(wdaytxt.getText());
                list.add(totalSaltxt.getText());
                list.add(deducttxt.getText());
                list.add(paidSaltxt.getText());
                list.add(paidBytxt.getText());
                list.add(datetxt.getText());


                String sql = null;
                if (edit) {
                    sql = "UPDATE payroll SET eid=?,month_year=?,Working_day=?,tot_sal=?,deduction=?,tot_paid=?,paid_by=?,date_of_pay=? WHERE month_year=?";
                } else {
                    sql = "insert into payroll values(?,?,?,?,?,?,?,?)";
                }
                PreparedStatement ps = con.prepareStatement(sql);
                list.stream().forEach(X -> {
                    try {
                        if (index.get() <= list.size()) {
                            ps.setString(index.get(), X);
                            index.getAndIncrement();
                        }
                        if (edit) {
                            ps.setString(9, month);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                int i = ps.executeUpdate();

                if (i > 0) {
                    if (edit) {
                        JOptionPane.showMessageDialog(null, "Update Successfully", "Success", JOptionPane.PLAIN_MESSAGE);
                        Login l = ObjectFactory.getLogin(role);
                        ComponentFactory.close(frame);
                    } else {
                        JOptionPane.showMessageDialog(null, "Successfully Paid", "Success", JOptionPane.PLAIN_MESSAGE);
                        Login l = ObjectFactory.getLogin(role);
                        ComponentFactory.close(frame);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Employee id", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        }

    private static void viewEmp(boolean edit, String data) {

          try(Connection con= DBConnection.getInstance().getConnection())
          {

              String eid=null;
              if(edit){
                  eid=data;
              }else{
                  eid=idtxt.getText();
              }

              String sql = "select*from employee natural join payroll where eid='" + eid + "'";
              int  count=8;
              PreparedStatement ps = con.prepareStatement(sql);
              ResultSet rs = ps.executeQuery();
              if(rs.next())
              {
                  idtxt.setText(rs.getString("eid"));
                  nametxt.setText(rs.getString("name"));
                  classtxt.setText(rs.getString("dept"));
                  gendertxt.setText(rs.getString("gender"));
                  mobiletxt.setText(rs.getString("mobile"));
                  totalSaltxt.setText(rs.getString("salary"));

                  if(edit){
                      monthtxt.setText(rs.getString("month_year"));
                      wdaytxt.setText(rs.getString("Working_day"));
                      deducttxt.setText(rs.getString("deduction"));
                      paidSaltxt.setText(rs.getString("tot_paid"));
                      paidBytxt.setText(rs.getString("paid_by"));
                      datetxt.setText(rs.getString("date_of_pay"));
                  }

                  nametxt.setEditable(false);
                  classtxt.setEditable(false);
                  gendertxt.setEditable(false);
                  mobiletxt.setEditable(false);
                  totalSaltxt.setEditable(false);


              }else{
                  JOptionPane.showMessageDialog(null,"Invalid Employee ID","Error",JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, " Enter employee ID in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String paid=paidSaltxt.getText();
        paid = paid.replace(" ", "");
        boolean pvalid =paid.matches("[0-9]{1,10}");
        if(pvalid==false)
        {
            JOptionPane.showMessageDialog(null, " Enter paid Salary in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String paidamt=wdaytxt.getText();
        paidamt = paidamt.replace(" ", "");
        boolean pvalidamt =paidamt.matches("[0-9]{1,10}");
        if(pvalidamt==false)
        {
            JOptionPane.showMessageDialog(null, " Enter  working days in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String de=deducttxt.getText();
        de = de.replace(" ", "");
        boolean dvalidamt =de.matches("[0-9]{1,10}");
        if(dvalidamt==false)
        {
            JOptionPane.showMessageDialog(null, " Enter  deducted amount in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}
