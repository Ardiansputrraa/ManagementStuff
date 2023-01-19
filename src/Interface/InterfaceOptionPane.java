package Interface;

import javax.swing.JOptionPane;

import GUI.LoginKaryawan;

public interface InterfaceOptionPane {

    public default void pesanError(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
    }
}
