package com.benison.college.library;

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

public class BookIssue {
    private  JLabel header,bid,name,author,sname,publication,id,sclass,reternDate,status,purchaseDate;
   private JTextField bidtxt,nametxt,authortxt,snametxt,publicationtxt,idtxt,statustxt,datetxt,sclasstxt,reternDatetxt;
    LinkedList<String> list;
    JFrame frame;
    public JPanel getBookIssue(String data,JFrame f,boolean edit){
        frame=f;
        JPanel regPanel=new JPanel();
        regPanel.setLayout(null);
        regPanel.setBackground(new Color(100,10,02,80));

        //defile labels
        header= ComponentFactory.getLabel("Book Issue");
        header.setBounds(300,0,400,50);
        header.setForeground(Color.RED);
        header.setFont(new Font("Arial",Font.BOLD,26));
        regPanel.add(header);

        bid=ComponentFactory.getLabel("Book Id. :");
        bid.setBounds(200,60,150,30);
        bidtxt=ComponentFactory.getTextFiled();
        bidtxt.setBounds(360,60,250,30);

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

        id=ComponentFactory.getLabel("Student Id :");
        id.setBounds(200,220,150,30);

        idtxt=ComponentFactory.getTextFiled();
        idtxt.setBounds(360,220,250,30);
        regPanel.add(id);
        regPanel.add(idtxt);

        sname=ComponentFactory.getLabel("Name : ");
        sname.setBounds(200,260,150,30);

        snametxt=ComponentFactory.getTextFiled();;
        snametxt.setBounds(360,260,250,30);
        regPanel.add(sname);
        regPanel.add(snametxt);

        sclass=ComponentFactory.getLabel("Class : ");
        sclass.setBounds(200,300,150,30);

        sclasstxt=ComponentFactory.getTextFiled();;
        sclasstxt.setBounds(360,300,250,30);
        regPanel.add(sclass);
        regPanel.add(sclasstxt);

        purchaseDate = ComponentFactory.getLabel("Issue Date : ");
        purchaseDate.setBounds(200,340,150,30);
        datetxt=ComponentFactory.getTextFiled();
        datetxt.setBounds(360,340,250,30);
        regPanel.add(purchaseDate);
        regPanel.add(datetxt);

        status = ComponentFactory.getLabel("Status: ");
        status.setBounds(200,380,150,30);
        statustxt=ComponentFactory.getTextFiled();;
        statustxt.setBounds(360,380,250,30);
        regPanel.add(status);
        regPanel.add(statustxt);
        JButton submit=new JButton("Issue");
        submit.setBounds(400,460,100,30);

        regPanel.add(submit);
        if(edit){
            bidtxt.addActionListener(X->viewBook(bidtxt.getText()));
            reternDate = ComponentFactory.getLabel("Return Date: ");
            reternDate.setBounds(200,420,150,30);
            reternDatetxt=ComponentFactory.getTextFiled();;
            reternDatetxt.setBounds(360,420,250,30);
            regPanel.add(reternDate);
            regPanel.add(reternDatetxt);
            submit.setText("Return");
            header.setText("Book Return");
            submit.addActionListener(X->returnBook());
        }else {
            submit.addActionListener(X->addEntry(edit));
            bidtxt.addActionListener(X->viewDetails(bidtxt.getText()));
            idtxt.addActionListener(X->viewStud(idtxt.getText()));
            nametxt.setEditable(false);
            authortxt.setEditable(false);
            publicationtxt.setEditable(false);
            snametxt.setEditable(false);
            sclasstxt.setEditable(false);
        }
        return regPanel;
    }

    private void viewStud(String en_no) {
        String id1=idtxt.getText();
        id1 = id1.replace(" ", "");
        boolean sdvalid =id1.matches("[0-9]{1,10}");
      if(sdvalid){
          try (Connection con = DBConnection.getInstance().getConnection()) {
              PreparedStatement ps = con.prepareStatement("select *from students where en_no='" + en_no + "'");
              ResultSet rs = ps.executeQuery();
              if (rs.next()) {
                  idtxt.setText(rs.getString("en_no"));

                  snametxt.setText(rs.getString("name"));
                  snametxt.setEditable(false);
                  sclasstxt.setText(rs.getString("class"));
                  sclasstxt.setEditable(false);
              }else {
                  JOptionPane.showMessageDialog(null, " Not found Student for Such id", "Error", JOptionPane.ERROR_MESSAGE);
                    snametxt.setText(" ");
                    sclasstxt.setText("");
              }
          }catch (SQLException e) {
              e.printStackTrace();
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
          }
      }
    }

    private void viewDetails(String bid) {
        String bid1=bidtxt.getText();
        bid1 = bid1.replace(" ", "");
        boolean edvalid =bid1.matches("[0-9]{1,10}");
       if(edvalid){
           try(Connection con= DBConnection.getInstance().getConnection()){
               PreparedStatement ps=con.prepareStatement("select *from book where bid='"+bid+"'");
               ResultSet rs=ps.executeQuery();
               if(rs.next())
               {
                   bidtxt.setText(rs.getString("bid"));
                   nametxt.setText(rs.getString("title"));
                   authortxt.setText(rs.getString("author"));
                   publicationtxt.setText(rs.getString("publication"));
                   nametxt.setEditable(false);
                   authortxt.setEditable(false);
                   publicationtxt.setEditable(false);

               }else {
                   JOptionPane.showMessageDialog(null, " Not found book for Such id", "Error", JOptionPane.ERROR_MESSAGE);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }

       }
    }

    private void returnBook() {
        String bid=bidtxt.getText();
        String en_no=idtxt.getText();
        String date=reternDatetxt.getText();
        String  status=statustxt.getText();
    String sql="UPDATE `issue` SET `return_date` = ?, `remarks` =? WHERE en_no=? and bid=?";
    try(Connection con= DBConnection.getInstance().getConnection()){
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,date);
        ps.setString(2,status);
        ps.setString(3,en_no);
        ps.setString(4,bid);
        int i=ps.executeUpdate();
        if(i>0){
            JOptionPane.showMessageDialog(null,"Returned Successfully","Success",JOptionPane.PLAIN_MESSAGE);
            new LibraryLogin();
            ComponentFactory.close(frame);
        }else {
            JOptionPane.showMessageDialog(null,"Problem to Return","Error",JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    }

    private void viewBook(String bid) {
       try(Connection con= DBConnection.getInstance().getConnection()){
           String sql = "select * from issue where bid='" + bid + "'";
           String en_no=null;
           PreparedStatement ps = con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();

           if(rs.next())
           {
               idtxt.setText(rs.getString("en_no"));
               en_no=rs.getString("en_no");
               datetxt.setText(rs.getString("issue_date"));
               datetxt.setEditable(false);
               statustxt.setText(rs.getString("remarks"));
           }
           ps=con.prepareStatement("select *from students where en_no='"+en_no+"'");
           rs=ps.executeQuery();
           if(rs.next())
           {
               idtxt.setText(rs.getString("en_no"));
               snametxt.setText(rs.getString("name"));
               snametxt.setEditable(false);
               sclasstxt.setText(rs.getString("class"));
               sclasstxt.setEditable(false);
           }
           ps=con.prepareStatement("select *from book where bid='"+bid+"'");
           rs=ps.executeQuery();
           if(rs.next())
           {
               bidtxt.setText(rs.getString("bid"));
               bidtxt.setEditable(false);
               nametxt.setText(rs.getString("title"));
               nametxt.setEditable(false);
               authortxt.setText(rs.getString("author"));
               authortxt.setEditable(false);
               publicationtxt.setText(rs.getString("publication"));
               publicationtxt.setEditable(false);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
    }

    private void addEntry(boolean edit) {
        list=new LinkedList<>();
        list.add(idtxt.getText());
        list.add(bidtxt.getText());
        list.add(nametxt.getText());
        list.add(datetxt.getText());
        if(edit){
            list.add(reternDatetxt.getText());
        }else{
        list.add("");
        }
        list.add(statustxt.getText());
        AtomicInteger index= new AtomicInteger(1);
        try(Connection con= DBConnection.getInstance().getConnection()) {
            String sql="insert into issue values(?,?,?,?,?,?)";
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
            int i=ps.executeUpdate();
            if(i>0) {
                JOptionPane.showMessageDialog(null, "Issued Successfully");
                new LibraryLogin();
                ComponentFactory.close(frame);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
private boolean validation(){
    String bid1=bidtxt.getText();
    bid1 = bid1.replace(" ", "");
    boolean idvalid =bid1.matches("[0-9]{1,10}");
    if(!idvalid)
    {
        JOptionPane.showMessageDialog(null, " Enter Book ID in Numbers", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    String id1=bidtxt.getText();
    bid1 = bid1.replace(" ", "");
    boolean edvalid =bid1.matches("[0-9]{1,10}");
    if(!edvalid)
    {
        JOptionPane.showMessageDialog(null, " Enter Students ID in digits/Numbers", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
        return  true;
}
}
