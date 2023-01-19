package GUI;

import Interface.*;
import javax.xml.crypto.Data;
import Koneksi.*;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.*;

public class DataKaryawan implements InterfaceButton, InterfaceOptionPane {

    JLayeredPane layer = new JLayeredPane();
    JPanel layerPanel = new JPanel();
    JTable tabelKaryawan = new JTable();
    JScrollPane scrollPaneKaryawan;
    JDateChooser tanggalJD = new JDateChooser();
    String[] columKaryawan = { "NIK", "Nama", "Jenis Kelamin", "Tanggal Lahir", "jabatan", "Email", "No Telpon" };
    JTextField nikJT, namaJT, emailJT, noTelponJT, searchJT;
    JButton tambahJB, editJB, hapusJB, refreshJB, searchJB;
    JComboBox jabatanJC;
    String[] jabatan = { "Admin Social Media", "Customer Service", "Packing Barang", "Admin Marketplace" };
    JRadioButton priaJR, wanitaJR;
    ButtonGroup genderGroup;
    String gender;
    private static String username, password;
    Akun akun = new Karyawan();
    Karyawan karyawan;
    DataAkun dataAkun = new DataAkun();

    public DataKaryawan() {
        initComponents();
        sum();
        implementsInterface();
        addPlaceholderStyle(nikJT);
        addPlaceholderStyle(namaJT);
        addPlaceholderStyle(emailJT);
        addPlaceholderStyle(noTelponJT);
        tampilkanData();
        kosongkanForm();
        tambahDB();
        editDB();
        hapusDB();
        refreshDB();
        searchDB();
        priaJR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == priaJR) {
                    gender = "Pria";
                }
            }
        });
        wanitaJR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == wanitaJR) {
                    gender = "Wanita";
                }
            }
        });

    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void password(String password) {
        this.password = password;
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

        scrollPaneKaryawan = new JScrollPane(tabelKaryawan);
        scrollPaneKaryawan.setBounds(20, 20, 700, 200);
        layerPanel.add(scrollPaneKaryawan);

    }

    public void tampilkanData() {
        try {
            DefaultTableModel modelKaryawan = new DefaultTableModel();
            for (int i = 0; i < columKaryawan.length; i++) {
                modelKaryawan.addColumn(columKaryawan[i]);
            }

            String sql = "SELECT * FROM tabel_Karyawan";
            java.sql.Connection conn = (Connection) KonfigDB.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                modelKaryawan.addRow(new Object[] { res.getString(1), res.getString(2), res.getString(3),
                        res.getString(4), res.getString(5), res.getString(6), res.getString(7) });
            }
            tabelKaryawan.setModel(modelKaryawan);

        } catch (SQLException e) {
            this.pesanError(e);
        }
    }

    public void kosongkanForm() {
        nikJT.setText("NIK");
        namaJT.setText("Nama");
        tanggalJD.setDate(null);
        jabatanJC.setSelectedIndex(0);
        genderGroup.clearSelection();
        priaJR.setSelected(false);
        wanitaJR.setSelected(false);
        emailJT.setText("Email");
        noTelponJT.setText("Nomer Telpon");

    }

    public void sum() {
        nikJT = new JTextField("NIK");
        nikJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        nikJT.setBounds(20, 250, 230, 35);
        layerPanel.add(nikJT);

        namaJT = new JTextField("Nama");
        namaJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        namaJT.setBounds(270, 250, 230, 35);
        layerPanel.add(namaJT);

        tanggalJD.setDateFormatString("dd-MM-yyyy");
        tanggalJD.setBounds(20, 305, 230, 35);
        layerPanel.add(tanggalJD);

        jabatanJC = new JComboBox<>(jabatan);
        jabatanJC.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        jabatanJC.setBounds(270, 305, 230, 35);
        layerPanel.add(jabatanJC);

        emailJT = new JTextField("Email");
        emailJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        emailJT.setBounds(20, 360, 230, 35);
        layerPanel.add(emailJT);

        noTelponJT = new JTextField("Nomer Telpon");
        noTelponJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        noTelponJT.setBounds(270, 360, 230, 35);
        layerPanel.add(noTelponJT);

        priaJR = new JRadioButton("Pria");
        priaJR.setBounds(20, 415, 70, 40);
        priaJR.setBackground(new Color(0, 153, 153));
        priaJR.setFont(new Font("Times New Roman", Font.BOLD, 22));
        priaJR.setSelected(false);
        layerPanel.add(priaJR);

        wanitaJR = new JRadioButton("Wanita");
        wanitaJR.setBounds(120, 415, 100, 40);
        wanitaJR.setBackground(new Color(0, 153, 153));
        wanitaJR.setFont(new Font("Times New Roman", Font.BOLD, 22));
        wanitaJR.setSelected(false);
        layerPanel.add(wanitaJR);

        genderGroup = new ButtonGroup();
        genderGroup.add(priaJR);
        genderGroup.add(wanitaJR);

        searchJT = new JTextField();
        searchJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        searchJT.setBounds(410, 415, 200, 35);
        layerPanel.add(searchJT);

        searchJB = new JButton("Search");
        searchJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        searchJB.setBounds(620, 415, 100, 35);
        layerPanel.add(searchJB);

        tambahJB = new JButton("Tambah");
        tambahJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        tambahJB.setBounds(520, 250, 200, 35);
        layerPanel.add(tambahJB);

        editJB = new JButton("Edit");
        editJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        editJB.setBounds(520, 305, 200, 35);
        layerPanel.add(editJB);

        hapusJB = new JButton("Hapus");
        hapusJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        hapusJB.setBounds(520, 360, 200, 35);
        layerPanel.add(hapusJB);

        refreshJB = new JButton("Refresh");
        refreshJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        refreshJB.setBounds(240, 415, 150, 35);
        layerPanel.add(refreshJB);

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
        InterfaceFocus focus = () -> {
            nikJT.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == nikJT) {
                        if (nikJT.getText().equals("NIK")) {
                            nikJT.setText("");
                            nikJT.requestFocus();
                            removePlaceholderStyle(nikJT);
                        }
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == nikJT) {
                        if (nikJT.getText().length() == 0) {
                            addPlaceholderStyle(nikJT);
                            nikJT.setText("NIK");
                        }
                    }
                }
            });

            namaJT.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == namaJT) {
                        if (namaJT.getText().equals("Nama")) {
                            namaJT.setText("");
                            namaJT.requestFocus();
                            removePlaceholderStyle(namaJT);
                        }
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == namaJT) {
                        if (namaJT.getText().length() == 0) {
                            addPlaceholderStyle(namaJT);
                            namaJT.setText("Nama");
                        }
                    }
                }
            });

            emailJT.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == emailJT) {
                        if (emailJT.getText().equals("Email")) {
                            emailJT.setText("");
                            emailJT.requestFocus();
                            removePlaceholderStyle(emailJT);
                        }
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == emailJT) {
                        if (emailJT.getText().length() == 0) {
                            addPlaceholderStyle(emailJT);
                            emailJT.setText("Email");
                        }
                    }
                }
            });

            noTelponJT.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == noTelponJT) {
                        if (noTelponJT.getText().equals("Nomer Telpon")) {
                            noTelponJT.setText("");
                            noTelponJT.requestFocus();
                            removePlaceholderStyle(noTelponJT);
                        }
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == noTelponJT) {
                        if (noTelponJT.getText().length() == 0) {
                            addPlaceholderStyle(noTelponJT);
                            noTelponJT.setText("Nomer Telpon");
                        }
                    }
                }
            });
        };
        focus.focus();
    }

    @Override
    public void tambahDB() {
        // TODO Auto-generated method stub
        tambahJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == tambahJB) {
                    // TODO add your handling code here:
                    if (nikJT.getText().length() == 10) {
                        try {
                            try {
                                if (nikJT.getText().equals("NIK") || namaJT.getText().equals("Nama")
                                        || gender.equals(null)
                                        || tanggalJD.getDate().equals(null) || emailJT.getText().equals("Email")
                                        || noTelponJT.getText().equals("Nomer Telpon")) {
                                    JOptionPane.showMessageDialog(null, "Data Belum Lengkap", "Messsage",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                            } catch (Exception a) {
                                JOptionPane.showMessageDialog(null, "Data Belum Lengkap", "Messsage",
                                        JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }

                            String formatDate = "dd-MM-yyyy";
                            SimpleDateFormat fm = new SimpleDateFormat(formatDate);
                            String date = fm.format(tanggalJD.getDate());
                            String sql = "INSERT INTO tabel_karyawan VALUES ('" + nikJT.getText() + "','"
                                    + namaJT.getText()
                                    + "','"
                                    + gender + "','" + date + "','" + jabatanJC.getSelectedItem() + "','"
                                    + emailJT.getText() + "','"
                                    + noTelponJT.getText() + "')";
                            java.sql.Connection conn = (Connection) KonfigDB.configDB();
                            java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                            pstm.execute();
                            username = namaJT.getText();
                            username = username.replaceAll("\\s+", "").substring(0, 5) + randomUsername(3);
                            akun.setUsername(username);
                            randomAngka(6);
                            String sqlAkun = "INSERT INTO akun_karyawan VALUES ('" + akun.getUsername() + "','"
                                    + akun.getPassword()
                                    + "','"
                                    + jabatanJC.getSelectedItem() + "','" + namaJT.getText() + "')";
                            java.sql.Connection connAkun = (Connection) KonfigDB.configDB();
                            java.sql.PreparedStatement pstmAkun = conn.prepareStatement(sqlAkun);
                            pstmAkun.execute();
                            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan", "Messsage",
                                    JOptionPane.INFORMATION_MESSAGE);
                            gender = null;
                            tampilkanData();
                            kosongkanForm();
                        } catch (HeadlessException | SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Message",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "NIK Tidak Sesuai.", "Messsage",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    @Override
    public void editDB() {
        // TODO Auto-generated method stub
        InterfaceClicked click = () -> {
            tabelKaryawan.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == tabelKaryawan) {
                        int baris = tabelKaryawan.rowAtPoint(e.getPoint());

                        String nik = tabelKaryawan.getValueAt(baris, 0).toString();
                        nikJT.setText(nik);

                        String nama = tabelKaryawan.getValueAt(baris, 1).toString();
                        namaJT.setText(nama);

                        String gender = tabelKaryawan.getValueAt(baris, 2).toString();
                        if (gender.equals("Pria")) {
                            priaJR.setSelected(true);
                        } else {
                            wanitaJR.setSelected(true);
                        }
                        try {
                            java.util.Date date = new SimpleDateFormat("dd-MM-yyyy")
                                    .parse((String) tabelKaryawan.getValueAt(baris, 3));
                            tanggalJD.setDate(date);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }

                        String jabatan = tabelKaryawan.getValueAt(baris, 4).toString();
                        jabatanJC.setSelectedItem(jabatan);

                        String email = tabelKaryawan.getValueAt(baris, 5).toString();
                        emailJT.setText(email);

                        String telpon = tabelKaryawan.getValueAt(baris, 6).toString();
                        noTelponJT.setText(telpon);
                    }
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });
        };

        click.clicked();
        editJB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String formatDate = "dd-MM-yyyy";
                    SimpleDateFormat fm = new SimpleDateFormat(formatDate);
                    String date = fm.format(tanggalJD.getDate());
                    String sql = "UPDATE tabel_karyawan SET nik='" + nikJT.getText() + "',nama='"
                            + namaJT.getText()
                            + "',jenis_kelamin='"
                            + gender + "',tanggal_lahir='" + date + "',jabatan='" + jabatanJC.getSelectedItem()
                            + "',email='" + emailJT.getText() + "',no_telpon='" + noTelponJT.getText() + "' WHERE nik='"
                            + nikJT.getText()
                            + "'";
                    java.sql.Connection conn = (Connection) KonfigDB.configDB();
                    java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.execute();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate", "Messsage",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (HeadlessException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                }
                tampilkanData();
                kosongkanForm();
            }

        });
    }

    @Override
    public void hapusDB() {
        // TODO Auto-generated method stub
        hapusJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == hapusJB) {
                    try {
                        String sql = "DELETE FROM tabel_karyawan WHERE nik='" + nikJT.getText() + "'";
                        java.sql.Connection conn = (Connection) KonfigDB.configDB();
                        java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                        pstm.execute();
                        String sqlAkun = "DELETE FROM akun_karyawan WHERE nama='" + namaJT.getText() + "'";
                        java.sql.Connection connAkun = (Connection) KonfigDB.configDB();
                        java.sql.PreparedStatement pstmAkun = connAkun.prepareStatement(sqlAkun);
                        pstmAkun.execute();
                        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                    } catch (HeadlessException | SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                    }
                    tampilkanData();
                    kosongkanForm();
                }
            }
        });
    }

    @Override
    public void refreshDB() {
        // TODO Auto-generated method stub
        refreshJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == refreshJB) {
                    kosongkanForm();
                    tampilkanData();
                }
            }
        });
    }

    @Override
    public void searchDB() {
        // TODO Auto-generated method stub
        searchJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == searchJB) {
                    // TODO add your handling code here:
                    DefaultTableModel cek = new DefaultTableModel();
                    for (int i = 0; i < columKaryawan.length; i++) {
                        cek.addColumn(columKaryawan[i]);
                    }
                    String cari = searchJT.getText();
                    tabelKaryawan.setModel(cek);
                    try {
                        String sql = "SELECT * FROM tabel_karyawan WHERE nik like '%" + cari
                                + "%' OR nama LIKE '%"
                                + cari + "%' OR jenis_kelamin LIKE '%" + cari + "%' OR tanggal_lahir LIKE '%" + cari
                                + "%' OR jabatan LIKE '%" + cari + "%' OR email LIKE '%" + cari
                                + "%' OR no_telpon LIKE '%" + cari + "%'";
                        java.sql.Connection conn = (Connection) KonfigDB.configDB();
                        java.sql.Statement stm = conn.createStatement();
                        java.sql.ResultSet res = stm.executeQuery(sql);
                        while (res.next()) {
                            String[] row = { res.getString(1), res.getString(2), res.getString(3), res.getString(4),
                                    res.getString(5), res.getString(6), res.getString(7) };
                            cek.addRow(row);
                        }
                        tabelKaryawan.setModel(cek);
                    } catch (HeadlessException | SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public void randomAngka(int panjang) {
        String randomPassword = "";
        int random = 0;
        for (int i = 0; i < panjang; i++) {
            random = (int) (Math.random() * 10);
            randomPassword += "" + random;
        }
        akun.setPassword(randomPassword);
    }

    public static String randomUsername(int n) {
        Random random = new Random(62);
        String character = "0123456789";

        String angkaUser = "";
        while (n-- > 0) {
            int index = (int) (Math.random() * 9);
            angkaUser += character.charAt(index);
        }
        return angkaUser;
    }
}
