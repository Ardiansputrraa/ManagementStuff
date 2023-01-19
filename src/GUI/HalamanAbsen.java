package GUI;

import Koneksi.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.Timer;

public class HalamanAbsen {

    JFrame frame = new JFrame();
    static final ImageIcon logo = new ImageIcon("src/img/logo.jpg");
    JLabel title = new JLabel();
    JTextField namaJT = new JTextField();
    JLabel tanggalJL = new JLabel();
    JLabel waktuJL = new JLabel();
    JDateChooser tanggalJD = new JDateChooser();
    JCheckBox absenJC = new JCheckBox();
    JButton absenJB = new JButton("Accept");
    Karyawan karyawan = new Karyawan();
    private String jam, tanggal;
    private String absen;

    public HalamanAbsen() {
        initComponents();
        namaJT.setText(karyawan.getNama());
        tampilkanJam();
        tampilkanTanggal();
        accept();
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getJam() {
        return this.jam;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return this.tanggal;
    }

    public void setAbsen(String absen) {
        this.absen = absen;
    }

    public String getAbsen() {
        return this.absen;
    }

    public void initComponents() {
        frame.setSize(350, 400);
        frame.setIconImage(logo.getImage());
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setTitle("Halaman Absen");
        frame.getContentPane().setBackground(new Color(0, 102, 102));
        frame.setVisible(true);

        title.setText("Silahkan Isi Absensi");
        title.setBounds(-7, 30, 350, 40);
        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(JLabel.CENTER);
        frame.add(title);

        namaJT.setBounds(45, 110, 250, 35);
        namaJT.setFont(new Font("Times New Roman", Font.BOLD, 20));
        namaJT.setEditable(false);
        namaJT.setHorizontalAlignment(JLabel.CENTER);
        frame.add(namaJT);

        tanggalJL.setText("Tanggal");
        tanggalJL.setBounds(40, 165, 100, 40);
        tanggalJL.setFont(new Font("Times New Roman", Font.BOLD, 20));
        tanggalJL.setForeground(Color.WHITE);
        tanggalJL.setHorizontalAlignment(JLabel.CENTER);
        frame.add(tanggalJL);

        waktuJL.setText("Waktu");
        waktuJL.setBounds(150, 165, 170, 40);
        waktuJL.setFont(new Font("Times New Roman", Font.BOLD, 20));
        waktuJL.setForeground(Color.WHITE);
        waktuJL.setHorizontalAlignment(JLabel.CENTER);
        frame.add(waktuJL);

        absenJC.setText("Hadir");
        absenJC.setFont(new Font("Times New Roman", Font.BOLD, 18));
        absenJC.setBounds(45, 220, 250, 35);
        absenJC.setBackground(new Color(0, 102, 102));
        absenJC.setForeground(Color.WHITE);
        frame.add(absenJC);

        absenJB.setBounds(185, 270, 110, 35);
        absenJB.setFont(new Font("Times New Roman", Font.BOLD, 20));
        absenJB.setHorizontalAlignment(JLabel.CENTER);
        frame.add(absenJB);
    }

    public void tampilkanJam() {
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nolJam = "", nolMenit = "", nolDetik = "";
                java.util.Date dateTime = new java.util.Date();
                int nilaiJam = dateTime.getHours();
                int nilaiMenit = dateTime.getMinutes();
                int nilaiDetik = dateTime.getSeconds();

                if (nilaiJam <= 9)
                    nolJam = "0";
                if (nilaiMenit <= 9)
                    nolMenit = "0";
                if (nilaiDetik <= 9)
                    nolDetik = "0";

                String jam = nolJam + Integer.toString(nilaiJam);
                String menit = nolMenit + Integer.toString(nilaiMenit);
                String detik = nolDetik + Integer.toString(nilaiDetik);

                setJam(jam + " : " + menit + " : " + detik);
                waktuJL.setText(getJam());
            }
        };
        new Timer(1000, taskPerformer).start();
    }

    public void tampilkanTanggal() {
        java.util.Date tanggalSekarang = new java.util.Date();
        SimpleDateFormat formatTanggal = new SimpleDateFormat("dd-mm-yyyy");
        setTanggal(formatTanggal.format(tanggalSekarang));
        tanggalJL.setText(getTanggal());
    }

    public void accept() {
        absenJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == absenJB) {
                    // TODO add your handling code here:
                    try {
                        if (absenJC.isSelected()) {
                            setAbsen("Hadir");
                        } else {
                            JOptionPane.showMessageDialog(null, "Anda Belum Menceklis Absen", "Messsage",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String sql = "INSERT INTO absensi_karyawan VALUES ('" + namaJT.getText() + "','"
                                + getTanggal() + "','" + getJam() + "','" + absen + "')";
                        java.sql.Connection conn = (Connection) KonfigDB.configDB();
                        java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                        pstm.execute();
                        JOptionPane.showMessageDialog(null, namaJT.getText() + " Berhasil Absen", "Messsage",
                                JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        new HalamanKaryawan();
                    } catch (HeadlessException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}
