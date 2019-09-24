package com.benison.college.library;

import com.benison.college.common.ComponentFactory;
import com.benison.college.common.Login;
import com.benison.college.registartion.Book;
import com.benison.college.registartion.Fees;
import com.benison.college.registartion.Payroll;
import com.benison.college.registartion.User;
import com.benison.college.tables.BookTable;
import com.benison.college.tables.TableClass;

import javax.swing.*;
import java.awt.*;

public class LibraryLogin extends JFrame implements Login {
    JPanel main;

    public LibraryLogin() {
        JLabel bg = ComponentFactory.getBackground();
        JPanel header = ComponentFactory.getHeader(this); // pass frame object to close frame
        JRootPane rootPane = new JRootPane();
        rootPane.setBounds(0, 60, 900, 28);

        main = new JPanel();
        main.setBounds(20, 100, 850, 650);
        main.setBackground(new Color(0, 0, 0, 80));
        main.setLayout(null);
        JPanel bookTable=BookTable.getTable("Book Records",this);
        bookTable.setBounds(0,0,850,600);
        main.removeAll();
        main.repaint();
        main.revalidate();
        main.add(bookTable);
        main.repaint();
        main.revalidate();

        bg.add(header);
        rootPane.setJMenuBar(menubar());
        bg.add(rootPane);
        bg.add(main);
        add(bg);
        ComponentFactory.setFrame("Library Page", this);
        setLayout(null);
    }

    @Override
    public JMenuBar menubar() {
        JMenu home, details, book, operation, action;
        JMenuItem studDetails, empDetails,issueBook,returnBook,bookDetails, bookEntry, changePass;
        JMenuBar mb = new JMenuBar();
        home = new JMenu("Home");
        details = new JMenu("Report");
        book = new JMenu("Books");
        operation = new JMenu("Operation");
        action = new JMenu("Actions");
        studDetails = new JMenuItem("Students");
        empDetails = new JMenuItem("Employees");
        bookDetails = new JMenuItem("Books Records");
        bookEntry = new JMenuItem("Book Entry");
        issueBook = new JMenuItem("Issue");
        returnBook = new JMenuItem("Return");
        changePass = new JMenuItem("Change Password");

        details.add(studDetails); details.add(new JSeparator()); details.add(empDetails);
        book.add(bookEntry); book.add(new JSeparator());book.add(bookDetails);
        action.add(changePass);
        operation.add(issueBook); operation.add(new JSeparator());operation.add(returnBook);
        mb.add(home);
        mb.add(details);
        mb.add(book);
        mb.add(operation);
        mb.add(action);
        mb.setBounds(0, 0, 900, 30);

        // add Action Listener
        studDetails.addActionListener(X -> {
            JPanel table = TableClass.getTable("Students Records :", "Student", this, true, false);
            table.setBounds(0, 0, 850, 650);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(table);
            main.repaint();
            main.revalidate();

        });
        empDetails.addActionListener(X -> {
            JPanel table = TableClass.getTable("Employee Records :", "Employee", this, true, false);
            table.setBounds(0, 0, 850, 600);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(table);
            main.repaint();
            main.revalidate();
        });
        bookDetails.addActionListener(X -> {
            JPanel table = BookTable.getTable("Book Records",this);
            table.setBounds(0, 0, 850, 600);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(table);
            main.repaint();
            main.revalidate();
        });
        changePass.addActionListener(X -> {
            JPanel userPanel = User.passChange(this,"library");
            userPanel.setBounds(50, 50, 700, 400);

            main.removeAll();
            main.repaint();
            main.revalidate();

            main.add(userPanel);
            main.repaint();
            main.revalidate();
        });
        bookEntry.addActionListener(X -> {
            JPanel bookReg=new Book().getBookPanel("",this,false);
            bookReg.setBounds(0, 0, 850, 600);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(bookReg);
            main.repaint();
            main.revalidate();
        });
        issueBook.addActionListener(X -> {
            JPanel bookReg=new BookIssue().getBookIssue("",this,false);
            bookReg.setBounds(0, 0, 850, 600);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(bookReg);
            main.repaint();
            main.revalidate();
        });
        returnBook.addActionListener(X -> {
            JPanel bookReg=new BookIssue().getBookIssue("",this,true);
            bookReg.setBounds(0, 0, 850, 600);
            main.removeAll();
            main.repaint();
            main.revalidate();
            main.add(bookReg);
            main.repaint();
            main.revalidate();
        });

        return mb;
    }

}

