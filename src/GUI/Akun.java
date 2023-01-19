package GUI;

/**
 * class Akun
 * untuk membuat dan menyimpan username dan password
 */
public class Akun {

    /**
     * variabel ini menggunakan access private
     * yang akan menjadi encapulasi
     */
    private String username, password;

    // constructor dan overloading constructor
    public Akun(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Akun() {

    }

    /**
     * method ini untuk mengambibil variabel username
     * yang akan mengembalikan
     * 
     * @return String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * method ini untuk mengset
     * 
     * @param username
     *                 yang akan mengganti nilai
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * method ini untuk mengambibil variabel password
     * yang akan mengembalikan
     * 
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * method ini untuk mengset
     * 
     * @param password
     *                 yang akan mengganti nilai
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
