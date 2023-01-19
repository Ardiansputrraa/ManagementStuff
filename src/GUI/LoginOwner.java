package GUI;

import Koneksi.*;
import Interface.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.*;

// FocusListener, WindowFocusListener

public class LoginOwner {
    static final ImageIcon logo = new ImageIcon("src/img/logo.jpg");
    static final ImageIcon icon = new ImageIcon("src/img/background.png");
    Login login = new Login();
    JTextField usernameJT;
    JPasswordField passJP;
    JProgressBar bar;
    JButton loginJB;
    JPanel panel = new JPanel();
    JLabel helloJL;
    JLabel label;
    JLabel iconJL;
    JLayeredPane layer = new JLayeredPane();
    JFrame frame = new JFrame();
    String nama;

    public LoginOwner() {
        initComponents();
        login.frame.dispose();
        addPlaceholderStyle(usernameJT);
        addPlaceholderStyle(passJP);
        implementsInterface();
    }

    public void initComponents() {
        frame.setSize(700, 400);
        frame.setIconImage(logo.getImage());
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setTitle("Login Owner");
        frame.getContentPane().setBackground(new Color(0, 153, 153));
        frame.setVisible(true);

        usernameJT = new JTextField("Username");
        usernameJT.setForeground(Color.BLACK);
        usernameJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        usernameJT.setBounds(390, 70, 257, 35);
        frame.add(usernameJT);

        passJP = new JPasswordField("Password");
        passJP.setForeground(Color.BLACK);
        passJP.setEchoChar('\u0000');
        passJP.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        passJP.setBounds(390, 150, 257, 35);
        frame.add(passJP);

        loginJB = new JButton();
        loginJB.setText("Login");
        loginJB.setForeground(Color.BLACK);
        loginJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        loginJB.setBounds(545, 220, 100, 35);
        frame.add(loginJB);

        panel = new JPanel();
        panel.setBounds(0, 0, 350, 450);
        panel.setBackground(new Color(0, 102, 102));
        panel.setLayout(null);
        frame.add(panel);

        helloJL = new JLabel("HELLO");
        helloJL.setForeground(Color.white);
        helloJL.setFont(new Font("Times New Roman", Font.BOLD, 35));
        helloJL.setBounds(0, 100, 350, 35);
        helloJL.setHorizontalAlignment(JLabel.CENTER);
        panel.add(helloJL);

        label = new JLabel("Have a Nice Day <3");
        label.setForeground(Color.white);
        label.setFont(new Font("Times New Roman", Font.BOLD, 25));
        label.setBounds(0, 150, 350, 35);
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        iconJL = new JLabel();
        iconJL.setIcon(icon);
        iconJL.setSize(350, 450);
        iconJL.setBounds(-170, 25, 550, 450);
        panel.add(iconJL);
    }

    public void progresBar() {
        bar = new JProgressBar(0, 100);
        bar.setValue(0);
        bar.setBounds(350, 300, 350, 40);
        bar.setStringPainted(true);
        bar.setFont(new Font("Times New Roman", Font.BOLD, 20));
        bar.setVisible(true);
        frame.add(bar);
    }

    public void addPlaceholderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.BLACK);
    }

    public void removePlaceholderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.PLAIN | Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.black);
    }

    public void implementsInterface() {
        usernameJT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource() == usernameJT) {
                    if (usernameJT.getText().equals("Username")) {
                        usernameJT.setText("");
                        usernameJT.requestFocus();
                        removePlaceholderStyle(usernameJT);
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource() == usernameJT) {
                    if (usernameJT.getText().length() == 0) {
                        addPlaceholderStyle(usernameJT);
                        usernameJT.setText("Username");
                    }
                }
            }
        });
        passJP.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource() == passJP) {
                    if (passJP.getText().equals("Password")) {
                        passJP.setText("");
                        passJP.requestFocus();
                        passJP.setEchoChar('*');
                        removePlaceholderStyle(passJP);
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource() == passJP) {
                    if (passJP.getText().length() == 0) {
                        addPlaceholderStyle(passJP);
                        passJP.setText("Password");
                        passJP.setEchoChar((char) 0);
                    }
                }
            }

        });

        InterfaceLogin interfaceLogin = () -> {
            loginJB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == loginJB) {
                        try {
                            // TODO add your handling code here:
                            int count = 0;
                            java.sql.Connection conn = (Connection) KonfigDB.configDB();
                            java.sql.Statement stm = conn.createStatement();
                            String sql = "SELECT * FROM akun_owner WHERE username='" +
                                    usernameJT.getText() + "' AND password='"
                                    + passJP.getText() + "'";
                            java.sql.ResultSet res = stm.executeQuery(sql);
                            if (res.next()) {
                                if (usernameJT.getText().equals(res.getString("username"))
                                        && passJP.getText().equals(res.getString("password"))) {
                                    progresBar();
                                    bar.setForeground(Color.GREEN);
                                    bar.setBackground(Color.BLACK);
                                    while (count <= 100) {
                                        bar.setValue(count);
                                        try {
                                            Thread.sleep(200);
                                        } catch (InterruptedException ex) {
                                            ex.printStackTrace();
                                        }
                                        count += 10;
                                    }
                                    bar.setString("Successful Login");
                                    JOptionPane.showMessageDialog(null,
                                            "Access Received",
                                            "Login", JOptionPane.INFORMATION_MESSAGE);
                                    frame.dispose();
                                    new HalamanOwner();
                                }
                            } else {
                                progresBar();
                                bar.setForeground(Color.RED);
                                bar.setBackground(Color.BLACK);
                                while (count <= 100) {
                                    bar.setValue(count);
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                    count += 10;
                                }
                                bar.setString("Login Failed");
                                JOptionPane.showMessageDialog(null,
                                        "Access Denied",
                                        "Login", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
            });
        };
        interfaceLogin.loginDB();
    }
}
