package GUI;

import javax.swing.*;
import java.awt.*;

import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    static final ImageIcon logo = new ImageIcon("src/img/logo.jpg");
    String[] cek = { "Owner", "Karyawan" };
    JComboBox loginCB = new JComboBox<>(cek);
    JFrame frame = new JFrame();
    JButton loginJB = new JButton();

    public Login() {
        initComponents();
    }

    public void initComponents() {

        frame.add(loginCB);

        frame.setResizable(false);
        frame.setTitle("Halaman Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setIconImage(logo.getImage());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (loginCB.getSelectedIndex() == 0) {
                    frame.dispose();
                    new LoginOwner();
                } else if (loginCB.getSelectedIndex() == 1) {
                    frame.dispose();
                    new LoginKaryawan();
                }
            }
        });
    }
}
