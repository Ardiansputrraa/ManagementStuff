package GUI;

import javax.swing.*;
import Koneksi.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class HalamanKaryawan {

    static final ImageIcon logo = new ImageIcon("src/img/logo.jpg");
    static final File file = new File("src/Sound/sound.wav");
    JFrame frame = new JFrame();
    JLabel namaJL = new JLabel();
    Akun karyawan = new Karyawan();
    Border border = BorderFactory.createLineBorder(Color.WHITE, 4);
    Karyawan downKaryawan;
    JButton logout = new JButton();
    JPanel tabelJP = new JPanel();
    JTextField searchJT = new JTextField();
    JLabel dataJL = new JLabel("Data Produk");
    JButton searchJB = new JButton();
    String[] columProduk = { "Kode Items", "Items", "Harga Items", "Stock Items" };
    JTable tabelProduk = new JTable();
    JScrollPane scrollPaneProduk;
    JButton musicOn, musicOff;

    public HalamanKaryawan() {
        initComponents();
        if (karyawan instanceof Karyawan) {
            downKaryawan = (Karyawan) karyawan;
        }
        namaJL.setText(downKaryawan.getNama());
        backsound();
        tampilkanData();
        fiture();
    }

    public void initComponents() {
        frame.setTitle("Halaman Karyawan");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(logo.getImage());
        frame.setLayout(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(0, 102, 102));
        frame.setVisible(true);

        namaJL.setBounds(30, 30, 350, 100);
        namaJL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        namaJL.setForeground(Color.WHITE);
        namaJL.setHorizontalAlignment(JLabel.CENTER);
        namaJL.setBorder(border);
        namaJL.setHorizontalAlignment(JLabel.CENTER);
        frame.add(namaJL);

        logout.setText("LOGOUT");
        logout.setBounds(600, 40, 150, 35);
        logout.setFont(new Font("Times New Roman", Font.BOLD, 15));
        frame.add(logout);

        musicOn = new JButton("MUSIC ON");
        musicOn.setBounds(600, 90, 150, 35);
        musicOn.setFont(new Font("Times New Roman", Font.BOLD, 15));
        musicOn.setVisible(false);
        frame.add(musicOn);

        musicOff = new JButton("MUSIC OFF");
        musicOff.setBounds(600, 90, 150, 35);
        musicOff.setFont(new Font("Times New Roman", Font.BOLD, 15));
        frame.add(musicOff);

        tabelJP.setBackground(new Color(0, 153, 153));
        tabelJP.setLayout(null);
        tabelJP.setBounds(0, 170, 800, 600);
        frame.add(tabelJP);

        dataJL.setBounds(50, 20, 350, 50);
        dataJL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        dataJL.setForeground(Color.WHITE);
        tabelJP.add(dataJL);

        searchJT.setBounds(370, 30, 250, 35);
        searchJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        tabelJP.add(searchJT);

        searchJB.setText("Search");
        searchJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        searchJB.setBounds(650, 30, 100, 35);
        tabelJP.add(searchJB);

        scrollPaneProduk = new JScrollPane(tabelProduk);
        scrollPaneProduk.setBounds(50, 100, 700, 250);
        tabelJP.add(scrollPaneProduk);
    }

    public void backsound() {
        try {
            // Memuat file suara ke dalam AudioInputStream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            musicOn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == musicOn) {
                        musicOn.setVisible(false);
                        musicOff.setVisible(true);
                        clip.start();
                    }
                }
            });
            musicOff.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == musicOff) {
                        musicOff.setVisible(false);
                        musicOn.setVisible(true);
                        clip.stop();
                    }
                }
            });
            logout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == logout) {
                        frame.dispose();
                        new Login();
                        clip.stop();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tampilkanData() {
        try {
            DefaultTableModel modelProduk = new DefaultTableModel();
            for (int i = 0; i < columProduk.length; i++) {
                modelProduk.addColumn(columProduk[i]);
            }
            String sql = "SELECT * FROM tabel_produk";
            java.sql.Connection conn = (Connection) KonfigDB.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                modelProduk.addRow(new Object[] { res.getString(1), res.getString(2), res.getInt(3),
                        res.getInt(4) });
            }
            tabelProduk.setModel(modelProduk);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void fiture() {
        searchJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == searchJB) {
                    // TODO add your handling code here:
                    DefaultTableModel cek = new DefaultTableModel();
                    for (int i = 0; i < columProduk.length; i++) {
                        cek.addColumn(columProduk[i]);
                    }
                    String cari = searchJT.getText();
                    tabelProduk.setModel(cek);
                    try {
                        String sql = "SELECT * FROM tabel_produk WHERE kode_items LIKE '%" + cari
                                + "%' OR items LIKE '%"
                                + cari + "%' OR harga_items LIKE '%" + cari + "%' OR stock_items LIKE '%" + cari + "%'";
                        java.sql.Connection conn = (Connection) KonfigDB.configDB();
                        java.sql.Statement stm = conn.createStatement();
                        java.sql.ResultSet res = stm.executeQuery(sql);
                        while (res.next()) {
                            String[] row = { res.getString(1), res.getString(2), res.getString(3), res.getString(4) };
                            cek.addRow(row);
                        }
                        tabelProduk.setModel(cek);
                    } catch (HeadlessException | SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
