package com.muki;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserForm extends Frame implements ActionListener {
    TextField nameField, emailField;
    Button submitButton;

    public UserForm() {
        setLayout(new FlowLayout());

        Label nameLabel = new Label("Name:");
        nameField = new TextField(20);
        add(nameLabel);
        add(nameField);

        Label emailLabel = new Label("Email:");
        emailField = new TextField(20);
        add(emailLabel);
        add(emailField);

        submitButton = new Button("Submit");
        submitButton.addActionListener(this);
        add(submitButton);

        setTitle("User Form");
        setSize(300, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameField.getText();
            String email = emailField.getText();
            saveUser(name, email);
            JOptionPane.showMessageDialog(this, "User saved successfully!");
        }
    }

    private void saveUser(String name, String email) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class).buildSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            session.save(user);
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    public static void main(String[] args) {
        new UserForm();
    }
}
