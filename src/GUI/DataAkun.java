package GUI;

import Koneksi.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Interface.InterfaceOptionPane;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataAkun implements InterfaceOptionPane {

    JLayeredPane layer = new JLayeredPane();
    JPanel layerPanel = new JPanel();
    JTable tabelAkun = new JTable();
    JScrollPane scrollPaneAkun;
    String[] columAkun = { "Username", "Passwoard", "Jenis User", "Nama" };
    JTable tabelAbsen = new JTable();
    JScrollPane scrollPaneAbsen;
    String[] columAbsen = { "nama", "tanggal", "Jam", "Absensi" };
    JLabel akunJL, absenJL;
    JTextField searchAkun, searchAbsen;
    JButton searchAkunJB, searchAbsenJB, refreshAkun, refreshAbsen;

    public DataAkun() {
        initComponents();
        tampilkanDataAkun();
        tampilkanDataAbsen();
        search();
        refresh();
    }

    public void initComponents() {
        layer.setBounds(0, 0, 750, 460);
        layer.setLayout(null);
        layer.setVisible(false);
        layerPanel.setBounds(0, 0, 750, 460);
        layerPanel.setBackground(new Color(0, 153, 153));
        layerPanel.setLayout(null);
        layerPanel.setVisible(true);
        layer.add(layerPanel);

        akunJL = new JLabel("Akun Karyawan");
        akunJL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        akunJL.setForeground(Color.WHITE);
        akunJL.setBounds(20, 20, 300, 35);
        layerPanel.add(akunJL);

        searchAkun = new JTextField();
        searchAkun.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        searchAkun.setBounds(300, 20, 200, 35);
        layerPanel.add(searchAkun);

        searchAkunJB = new JButton("Search");
        searchAkunJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        searchAkunJB.setBounds(510, 20, 100, 35);
        layerPanel.add(searchAkunJB);

        refreshAkun = new JButton("Refresh");
        refreshAkun.setFont(new Font("Times New Roman", Font.BOLD, 18));
        refreshAkun.setBounds(620, 20, 100, 35);
        layerPanel.add(refreshAkun);

        scrollPaneAkun = new JScrollPane(tabelAkun);
        scrollPaneAkun.setBounds(20, 75, 700, 150);
        layerPanel.add(scrollPaneAkun);

        absenJL = new JLabel("Absensi Karyawan");
        absenJL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        absenJL.setForeground(Color.WHITE);
        absenJL.setBounds(20, 250, 300, 35);
        layerPanel.add(absenJL);

        searchAbsen = new JTextField();
        searchAbsen.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        searchAbsen.setBounds(300, 250, 200, 35);
        layerPanel.add(searchAbsen);

        searchAbsenJB = new JButton("Search");
        searchAbsenJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        searchAbsenJB.setBounds(510, 250, 100, 35);
        layerPanel.add(searchAbsenJB);

        refreshAbsen = new JButton("Refresh");
        refreshAbsen.setFont(new Font("Times New Roman", Font.BOLD, 18));
        refreshAbsen.setBounds(620, 250, 100, 35);
        layerPanel.add(refreshAbsen);

        scrollPaneAbsen = new JScrollPane(tabelAbsen);
        scrollPaneAbsen.setBounds(20, 305, 700, 150);
        layerPanel.add(scrollPaneAbsen);

    }

    public void tampilkanDataAkun() {
        try {
            DefaultTableModel modelAkun = new DefaultTableModel();
            for (int i = 0; i < columAkun.length; i++) {
                modelAkun.addColumn(columAkun[i]);
            }
            String sql = "SELECT * FROM akun_karyawan";
            java.sql.Connection conn = (Connection) KonfigDB.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                modelAkun.addRow(new Object[] { res.getString(1), res.getString(2), res.getString(3),
                        res.getString(4) });
            }
            tabelAkun.setModel(modelAkun);
        } catch (SQLException e) {
            this.pesanError(e);
        }
    }

    public void tampilkanDataAbsen() {
        try {
            DefaultTableModel modelAbsen = new DefaultTableModel();
            for (int i = 0; i < columAbsen.length; i++) {
                modelAbsen.addColumn(columAbsen[i]);
            }
            String sql = "SELECT * FROM absensi_karyawan";
            java.sql.Connection conn = (Connection) KonfigDB.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                modelAbsen.addRow(new Object[] { res.getString(1), res.getString(2), res.getString(3),
                        res.getString(4) });
            }
            tabelAbsen.setModel(modelAbsen);
        } catch (SQLException e) {
            this.pesanError(e);
        }
    }

    public void search() {
        searchAkunJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == searchAkunJB) {
                    // TODO add your handling code here:
                    DefaultTableModel cekAkun = new DefaultTableModel();
                    for (int i = 0; i < columAkun.length; i++) {
                        cekAkun.addColumn(columAkun[i]);
                    }
                    String cari = searchAkun.getText();
                    tabelAkun.setModel(cekAkun);
                    try {
                        String sql = "SELECT * FROM akun_karyawan WHERE username LIKE '%" + cari
                                + "%' OR password LIKE '%"
                                + cari + "%' OR nama LIKE '%" + cari + "%' OR jenis_akun LIKE '%" + cari + "%'";
                        java.sql.Connection conn = (Connection) KonfigDB.configDB();
                        java.sql.Statement stm = conn.createStatement();
                        java.sql.ResultSet res = stm.executeQuery(sql);
                        while (res.next()) {
                            String[] row = { res.getString(1), res.getString(2), res.getString(3), res.getString(4) };
                            cekAkun.addRow(row);
                        }
                        tabelAkun.setModel(cekAkun);
                    } catch (HeadlessException | SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        searchAbsenJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == searchAbsenJB) {
                    // TODO add your handling code here:
                    DefaultTableModel cekAbsen = new DefaultTableModel();
                    for (int i = 0; i < columAbsen.length; i++) {
                        cekAbsen.addColumn(columAbsen[i]);
                    }
                    String cari = searchAbsen.getText();
                    tabelAbsen.setModel(cekAbsen);
                    try {
                        String sql = "SELECT * FROM absensi_karyawan WHERE nama LIKE '%" + cari
                                + "%' OR tanggal LIKE '%"
                                + cari + "%' OR jam LIKE '%" + cari + "%' OR absensi LIKE '%" + cari + "%'";
                        java.sql.Connection conn = (Connection) KonfigDB.configDB();
                        java.sql.Statement stm = conn.createStatement();
                        java.sql.ResultSet res = stm.executeQuery(sql);
                        while (res.next()) {
                            String[] row = { res.getString(1), res.getString(2), res.getString(3), res.getString(4) };
                            cekAbsen.addRow(row);
                        }
                        tabelAbsen.setModel(cekAbsen);
                    } catch (HeadlessException | SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public void refresh() {
        refreshAkun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == refreshAkun) {
                    tampilkanDataAkun();
                }
            }
        });
        refreshAbsen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == refreshAbsen) {
                    tampilkanDataAbsen();
                }
            }
        });
    }
}
