package GUI;

import Interface.*;
import javax.swing.*;
import java.awt.*;
import Koneksi.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.awt.event.*;

public class DataProduk implements InterfaceButton, InterfaceOptionPane {

    JLayeredPane layer = new JLayeredPane();
    JPanel layerPanel = new JPanel();
    JTable tabelProduk = new JTable();
    JScrollPane scrollPaneProduk;
    String[] columProduk = { "Kode Items", "Items", "Harga Items", "Stock Items" };
    JTextField kodeJT, itemJT, hargaJT, stockJT, searchJT;
    JButton tambahJB, editJB, hapusJB, refreshJB, searchJB;

    DataProduk() {
        initComponents();
        tampilkanData();
        sum();
        implementsInterface();
        addPlaceholderStyle(kodeJT);
        addPlaceholderStyle(itemJT);
        addPlaceholderStyle(hargaJT);
        addPlaceholderStyle(stockJT);
        tambahDB();
        editDB();
        hapusDB();
        refreshDB();
        searchDB();

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

        scrollPaneProduk = new JScrollPane(tabelProduk);
        scrollPaneProduk.setBounds(20, 20, 700, 200);
        layerPanel.add(scrollPaneProduk);

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
            this.pesanError(e);
        }
    }

    public void kosongkanForm() {
        kodeJT.setText("Kode Items");
        itemJT.setText("Items");
        hargaJT.setText("Harga Items");
        stockJT.setText("Stock Items");
    }

    public void sum() {
        kodeJT = new JTextField("Kode Items");
        kodeJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        kodeJT.setBounds(20, 250, 300, 35);
        layerPanel.add(kodeJT);

        itemJT = new JTextField("Items");
        itemJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        itemJT.setBounds(20, 305, 300, 35);
        layerPanel.add(itemJT);

        hargaJT = new JTextField("Harga Items");
        hargaJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        hargaJT.setBounds(20, 360, 300, 35);
        layerPanel.add(hargaJT);

        stockJT = new JTextField("Stock Items");
        stockJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        stockJT.setBounds(20, 415, 300, 35);
        layerPanel.add(stockJT);

        searchJT = new JTextField();
        searchJT.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        searchJT.setBounds(350, 250, 250, 35);
        layerPanel.add(searchJT);

        searchJB = new JButton("Search");
        searchJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        searchJB.setBounds(620, 250, 100, 35);
        layerPanel.add(searchJB);

        tambahJB = new JButton("Tambah");
        tambahJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        tambahJB.setBounds(350, 305, 170, 35);
        layerPanel.add(tambahJB);

        hapusJB = new JButton("Hapus");
        hapusJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        hapusJB.setBounds(550, 305, 170, 35);
        layerPanel.add(hapusJB);

        editJB = new JButton("Edit");
        editJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        editJB.setBounds(350, 360, 170, 35);
        layerPanel.add(editJB);

        refreshJB = new JButton("Refresh");
        refreshJB.setFont(new Font("Times New Roman", Font.BOLD, 18));
        refreshJB.setBounds(550, 360, 170, 35);
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
            kodeJT.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == kodeJT) {
                        if (kodeJT.getText().equals("Kode Items")) {
                            kodeJT.setText("");
                            kodeJT.requestFocus();
                            removePlaceholderStyle(kodeJT);
                        }
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == kodeJT) {
                        if (kodeJT.getText().length() == 0) {
                            addPlaceholderStyle(kodeJT);
                            kodeJT.setText("Kode Items");
                        }
                    }
                }
            });

            itemJT.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == itemJT) {
                        if (itemJT.getText().equals("Items")) {
                            itemJT.setText("");
                            itemJT.requestFocus();
                            removePlaceholderStyle(itemJT);
                        }
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == itemJT) {
                        if (itemJT.getText().length() == 0) {
                            addPlaceholderStyle(itemJT);
                            itemJT.setText("Items");
                        }
                    }
                }
            });

            hargaJT.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == hargaJT) {
                        if (hargaJT.getText().equals("Harga Items")) {
                            hargaJT.setText("");
                            hargaJT.requestFocus();
                            removePlaceholderStyle(hargaJT);
                        }
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == hargaJT) {
                        if (hargaJT.getText().length() == 0) {
                            addPlaceholderStyle(hargaJT);
                            hargaJT.setText("Harga Items");
                        }
                    }
                }
            });

            stockJT.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == stockJT) {
                        if (stockJT.getText().equals("Stock Items")) {
                            stockJT.setText("");
                            stockJT.requestFocus();
                            removePlaceholderStyle(stockJT);
                        }
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == stockJT) {
                        if (stockJT.getText().length() == 0) {
                            addPlaceholderStyle(stockJT);
                            stockJT.setText("Stock Items");
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
                    try {
                        if (kodeJT.getText().equals("Kode Items") || itemJT.getText().equals("Items")
                                || hargaJT.getText().equals("Harga Items") || stockJT.getText().equals("Stock_items")) {
                            JOptionPane.showMessageDialog(null, "Data Belum Lengkap", "Messsage",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        String sql = "INSERT INTO tabel_produk VALUES ('" + kodeJT.getText() + "','" + itemJT.getText()
                                + "','"
                                + hargaJT.getText() + "','" + stockJT.getText() + "')";
                        java.sql.Connection conn = (Connection) KonfigDB.configDB();
                        java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                        pstm.execute();
                        JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan", "Messsage",
                                JOptionPane.INFORMATION_MESSAGE);
                        tampilkanData();
                        kosongkanForm();
                    } catch (HeadlessException | SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Message", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    @Override
    public void editDB() {
        // TODO Auto-generated method stub
        InterfaceClicked click = () -> {
            tabelProduk.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == tabelProduk) {
                        int baris = tabelProduk.rowAtPoint(e.getPoint());

                        String kode = tabelProduk.getValueAt(baris, 0).toString();
                        kodeJT.setText(kode);

                        String items = tabelProduk.getValueAt(baris, 1).toString();
                        itemJT.setText(items);

                        String harga = tabelProduk.getValueAt(baris, 2).toString();
                        hargaJT.setText(harga);

                        String stock = tabelProduk.getValueAt(baris, 3).toString();
                        stockJT.setText(stock);
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
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = "UPDATE tabel_produk SET kode_items='" + kodeJT.getText() + "',items='"
                            + itemJT.getText()
                            + "',harga_items='"
                            + hargaJT.getText() + "',stock_items='" + stockJT.getText() + "' WHERE kode_items = '"
                            + kodeJT.getText()
                            + "'";
                    java.sql.Connection conn = (Connection) KonfigDB.configDB();
                    java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.execute();
                    tampilkanData();
                    kosongkanForm();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate", "Messsage",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (HeadlessException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                }
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
                        String sql = "DELETE FROM tabel_produk WHERE kode_items='" + kodeJT.getText() + "'";
                        java.sql.Connection conn = (Connection) KonfigDB.configDB();
                        java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                        pstm.execute();
                        tampilkanData();
                        kosongkanForm();
                        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                    } catch (HeadlessException | SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                    }
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
