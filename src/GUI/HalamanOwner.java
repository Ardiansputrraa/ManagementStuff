package GUI;

import Interface.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;

import org.w3c.dom.events.MouseEvent;
import java.awt.event.*;

import java.awt.*;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class HalamanOwner {
    static final ImageIcon logo = new ImageIcon("src/img/logo.jpg");
    static final ImageIcon icon = new ImageIcon("src/img/logo2.png");
    static final ImageIcon dashboardIcon = new ImageIcon("src/img/dashboard.png");
    static final File file = new File("src/Sound/sound.wav");
    LoginOwner halamanLogin;
    JFrame frame = new JFrame();
    JPanel panelA, panelB, panelC, menuJP, dashboardJP, produkJP, karyawanJP, akunJP;
    JButton logout = new JButton();
    JButton musicOn, musicOff;
    JLabel iconJL, titleJL, menuJL, dashboardJL, produkJL, karywanJL, akunJL, dashboardLabel;
    DataProduk dataProduk = new DataProduk();
    DataKaryawan dataKaryawan = new DataKaryawan();
    DataAkun dataAkun = new DataAkun();

    public HalamanOwner() {
        initComponents();
        menuNavigation();
        dashboard();
        implementsInterface();
        backsound();
    }

    public void initComponents() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 650);
        frame.setLayout(new BorderLayout(5, 5));
        frame.setIconImage(logo.getImage());
        // frame.setResizable(false);
        frame.setTitle("Halaman Owner");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panelA = new JPanel();
        panelA.setBackground(new Color(0, 102, 102));
        panelA.setLayout(null);
        panelA.setPreferredSize(new Dimension(760, 150));
        frame.add(panelA, BorderLayout.NORTH);

        panelB = new JPanel();
        panelB.setBackground(new Color(0, 102, 102));
        panelB.setLayout(null);
        panelB.setPreferredSize(new Dimension(190, 650));
        frame.add(panelB, BorderLayout.WEST);

        iconJL = new JLabel();
        iconJL.setIcon(icon);
        iconJL.setBounds(-240, -25, 500, 200);
        panelA.add(iconJL);

        titleJL = new JLabel("MY COMPANY");
        titleJL.setForeground(Color.white);
        titleJL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        titleJL.setBounds(140, 60, 350, 35);
        panelA.add(titleJL);

        logout.setText("LOGOUT");
        logout.setBounds(750, 55, 120, 35);
        logout.setFont(new Font("Times New Roman", Font.BOLD, 15));
        panelA.add(logout);

        musicOn = new JButton("MUSIC ON");
        musicOn.setBounds(0, 0, 190, 35);
        musicOn.setFont(new Font("Times New Roman", Font.BOLD, 18));
        musicOn.setVisible(false);
        panelB.add(musicOn);

        musicOff = new JButton("MUSIC OFF");
        musicOff.setBounds(0, 0, 190, 35);
        musicOff.setFont(new Font("Times New Roman", Font.BOLD, 18));
        panelB.add(musicOff);

        menuJP = new JPanel();
        menuJP.setBounds(0, 70, 190, 35);
        menuJP.setBackground(new Color(0, 153, 153));
        panelB.add(menuJP);

        menuJL = new JLabel("MENU NAVIGATION");
        menuJL.setForeground(Color.WHITE);
        menuJL.setFont(new Font("Times New Roman", Font.BOLD, 16));
        menuJL.setBounds(0, 70, 190, 35);
        menuJL.setHorizontalTextPosition(JLabel.CENTER);
        menuJP.add(menuJL);

    }

    public void menuNavigation() {
        dashboardJP = new JPanel();
        dashboardJP.setBounds(0, 140, 190, 35);
        dashboardJP.setBackground(new Color(0, 153, 153));
        panelB.add(dashboardJP);

        dashboardJL = new JLabel("DASHBOARD");
        dashboardJL.setForeground(Color.WHITE);
        dashboardJL.setFont(new Font("Times New Roman", Font.BOLD, 16));
        dashboardJL.setBounds(0, 0, 190, 35);
        dashboardJL.setHorizontalTextPosition(JLabel.CENTER);
        dashboardJP.add(dashboardJL);

        produkJP = new JPanel();
        produkJP.setBounds(0, 210, 190, 35);
        produkJP.setBackground(new Color(0, 153, 153));
        panelB.add(produkJP);

        produkJL = new JLabel("DATA PRODUK");
        produkJL.setForeground(Color.white);
        produkJL.setFont(new Font("Times New Roman", Font.BOLD, 16));
        produkJL.setBounds(0, 0, 190, 35);
        produkJL.setHorizontalTextPosition(JLabel.CENTER);
        produkJP.add(produkJL);

        karyawanJP = new JPanel();
        karyawanJP.setBounds(0, 280, 190, 35);
        karyawanJP.setBackground(new Color(0, 153, 153));
        panelB.add(karyawanJP);

        karywanJL = new JLabel("DATA KARYAWAN");
        karywanJL.setForeground(Color.white);
        karywanJL.setFont(new Font("Times New Roman", Font.BOLD, 16));
        karywanJL.setBounds(0, 0, 190, 35);
        karywanJL.setHorizontalTextPosition(JLabel.CENTER);
        karyawanJP.add(karywanJL);

        akunJP = new JPanel();
        akunJP.setBounds(0, 350, 190, 35);
        akunJP.setBackground(new Color(0, 153, 153));
        panelB.add(akunJP);

        akunJL = new JLabel("AKUN & ABSENSI");
        akunJL.setForeground(Color.white);
        akunJL.setFont(new Font("Times New Roman", Font.BOLD, 16));
        akunJL.setBounds(0, 0, 190, 35);
        akunJL.setHorizontalTextPosition(JLabel.CENTER);
        akunJP.add(akunJL);

        panelC = new JPanel();
        panelC.setLayout(null);
        panelC.setBackground(new Color(0, 153, 153));
        panelC.setPreferredSize(new Dimension(700, 460));
        frame.add(panelC, BorderLayout.CENTER);

        panelC.add(dataProduk.layer);
        panelC.add(dataKaryawan.layer);
        panelC.add(dataAkun.layer);
    }

    public void dashboard() {
        dashboardLabel = new JLabel();
        dashboardLabel.setIcon(dashboardIcon);
        dashboardLabel.setBounds(0, -30, 750, 500);
        panelC.add(dashboardLabel);
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

    public void implementsInterface() {
        InterfaceClicked click = () -> {
            produkJL.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == produkJL) {
                        dataProduk.layer.setVisible(true);
                        dashboardLabel.setVisible(false);
                        dataKaryawan.layer.setVisible(false);
                        dataAkun.layer.setVisible(false);
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
            dashboardJL.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == dashboardJL) {
                        dataProduk.layer.setVisible(false);
                        dataKaryawan.layer.setVisible(false);
                        dashboardLabel.setVisible(true);
                        dataAkun.layer.setVisible(false);
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
            karywanJL.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == karywanJL) {
                        dataProduk.layer.setVisible(false);
                        dashboardLabel.setVisible(false);
                        dataKaryawan.layer.setVisible(true);
                        dataAkun.layer.setVisible(false);
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
            akunJL.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getSource() == akunJL) {
                        dataProduk.layer.setVisible(false);
                        dashboardLabel.setVisible(false);
                        dataKaryawan.layer.setVisible(false);
                        dataAkun.layer.setVisible(true);
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
    }
}
