package com.benison.college.registartion;

import com.benison.college.account.AccountLogin;
import com.benison.college.admin.AdminLogin;
import com.benison.college.common.ComponentFactory;
import com.benison.college.database.DBConnection;
import com.benison.college.library.LibraryLogin;

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

public class Book {
    JLabel header,bid,name,author,isbn,publication,edition,pages,qty,price,purchaseDate;
   private static JTextField bidtxt,nametxt,authortxt,isbntxt,publicationtxt,editiontxt,pricetxt,datetxt,pagestxt,qtytxt;
    LinkedList<String> list;
    JFrame frame;
        public JPanel getBookPanel(String data,JFrame f,boolean edit){
        frame=f;
        JPanel regPanel=new JPanel();
        regPanel.setLayout(null);
        regPanel.setBackground(new Color(100,10,02,80));

        //defile labels
        header= ComponentFactory.getLabel("Book Entry");
        header.setBounds(300,0,400,50);
        header.setForeground(Color.RED);
        header.setFont(new Font("Arial",Font.BOLD,26));
        regPanel.add(header);

        bid=ComponentFactory.getLabel("Book Id. :");
        bid.setBounds(200,60,150,30);

        bidtxt=ComponentFactory.getTextFiled();
        bidtxt.setBounds(360,60,250,30);
       // bidtxt.addActionListener(X->viewFees(edit,"data"));
        regPanel.add(bid);
        regPanel.add(bidtxt);

        name=ComponentFactory.getLabel("Book Title :   ");
        name.setBounds(200,100,150,30);

        nametxt=ComponentFactory.getTextFiled();
        nametxt.setBounds(360,100,250,30);
        regPanel.add(name);
        regPanel.add(nametxt);

        author=ComponentFactory.getLabel("Author :  ");
        author.setBounds(200,140,150,30);

        authortxt=ComponentFactory.getTextFiled();
        authortxt.setBounds(360,140,250,30);
        regPanel.add(author);
        regPanel.add(authortxt);

        publication=new ComponentFactory().getLabel("Publication :");
        publication.setBounds(200,180,150,30);

        publicationtxt =ComponentFactory.getTextFiled();
        publicationtxt.setBounds(360,180,250,30);
        regPanel.add(publication);
        regPanel.add(publicationtxt);

        edition=ComponentFactory.getLabel("Edition :");
        edition.setBounds(200,220,150,30);

        editiontxt=ComponentFactory.getTextFiled();
        editiontxt.setBounds(360,220,250,30);
        regPanel.add(edition);
        regPanel.add(editiontxt);

        isbn=ComponentFactory.getLabel("Isbn No. : ");
        isbn.setBounds(200,260,150,30);

        isbntxt=ComponentFactory.getTextFiled();;
        isbntxt.setBounds(360,260,250,30);
        regPanel.add(isbn);
        regPanel.add(isbntxt);

        pages=ComponentFactory.getLabel("Pages : ");
        pages.setBounds(200,300,150,30);

        pagestxt=ComponentFactory.getTextFiled();;
        pagestxt.setBounds(360,300,250,30);
        regPanel.add(pages);
        regPanel.add(pagestxt);

        qty=ComponentFactory.getLabel("Quantity : ");
        qty.setBounds(200,340,150,30);

        qtytxt=ComponentFactory.getTextFiled();;
        qtytxt.setBounds(360,340,250,30);
        regPanel.add(qty);
        regPanel.add(qtytxt);

        price=ComponentFactory.getLabel("Price : ");
        price.setBounds(200,380,150,30);

        pricetxt=ComponentFactory.getTextFiled();;
        pricetxt.setBounds(360,380,250,30);
        regPanel.add(price);
        regPanel.add(pricetxt);

        purchaseDate = ComponentFactory.getLabel("Purchase Date : ");
        purchaseDate.setBounds(200,420,150,30);
        datetxt=ComponentFactory.getTextFiled();;
        datetxt.setBounds(360,420,250,30);
        regPanel.add(purchaseDate);
        regPanel.add(datetxt);

        JButton submit=new JButton("Add Entry");
        submit.setBounds(400,460,100,30);
        submit.addActionListener(X->addEntry(edit));
      if(edit){
            submit.setText("Update");
            viewFees(edit,data);
            bidtxt.setEditable(false);
            System.out.println(data);
        }
        regPanel.add(submit);
        return regPanel;
    }

    private void viewFees(boolean edit, String data) {
        try(Connection con= DBConnection.getInstance().getConnection())
        {
            String en_no=data;
            String sql = "select *from book where bid='" + en_no + "'";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {

                bidtxt.setText(rs.getString(1));
                nametxt.setText(rs.getString(2));
                authortxt.setText(rs.getString(3));;
                publicationtxt.setText(rs.getString(4));
                editiontxt.setText(rs.getString(5));
                isbntxt.setText(rs.getString(6));
                pagestxt.setText(rs.getString(7));
                qtytxt.setText(rs.getString(8));
                pricetxt.setText(rs.getString(9));
                datetxt.setText(rs.getString(10));

            }else{
                JOptionPane.showMessageDialog(null,"Invalid book Id","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }

    private void  addEntry(boolean edit){

       list=new LinkedList<>();
        list.add(bidtxt.getText());
        list.add(nametxt.getText());
        list.add(authortxt.getText());
        list.add(publicationtxt.getText());
        list.add(editiontxt.getText());
        list.add(isbntxt.getText());
        list.add(pagestxt.getText());
        list.add(qtytxt.getText());
        list.add(pricetxt.getText());
        list.add(datetxt.getText());
        AtomicInteger index= new AtomicInteger(1);
        if(validation(list)){
            try(Connection con= DBConnection.getInstance().getConnection()) {
                String sql=null;
                if(edit){

                    sql="UPDATE book SET bid =?,title=?,author=?,publication=?,edition=?,isbn=?,pages=?,qty=?,price=?,purchase_date=? WHERE bid=?";
                    System.out.println("In Edit");
                }else{
                    sql="insert into book values(?,?,?,?,?,?,?,?,?,?)";
                }
                PreparedStatement ps=con.prepareStatement(sql);
                list.stream().forEach(X->{
                    try {

                        if(index.get()<=list.size()){
                            ps.setString(index.get(),X);
                            index.getAndIncrement();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                if(edit){

                    String bid=bidtxt.getText();
                    ps.setString(11,bid);
                }
                int i=ps.executeUpdate();
                if(i>0)
                {
                    if(edit){
                        JOptionPane.showMessageDialog(null,"Update Successfully");
                    }else{
                        JOptionPane.showMessageDialog(null,"Register Successfully");
                    }
                    new LibraryLogin();
                    ComponentFactory.close(frame);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    public static boolean validation(LinkedList<String> list) {

        String bid1=list.get(0);
        bid1 = bid1.replace(" ", "");
        boolean idvalid =bid1.matches("[0-9]{1,10}");
        if(idvalid==false)
        {
            JOptionPane.showMessageDialog(null, " Enter Book ID in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String total=pricetxt.getText();
        total = total.replace(" ", "");
        boolean tvalid =total.matches("[0-9]{1,10}");
        if(tvalid==false)
        {
            JOptionPane.showMessageDialog(null, " Enter Price in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String qty=qtytxt.getText();
        qty = qty.replace(" ", "");
        boolean qvalid =qty.matches("[0-9]{1,10}");
        if(qvalid==false)
        {
            JOptionPane.showMessageDialog(null, " Enter Quantity in Numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
