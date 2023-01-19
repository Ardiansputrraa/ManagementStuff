package GUI;

/**
 * class ini akan menjadi subclass dari superclass
 * dengan extends Akun
 */
public class Karyawan extends Akun {
    /**
     * variabel ini menggunakan access private
     * yang akan menjadi encapulasi
     */
    private static String jenisAkun, nama;

    /**
     * @param username
     * @param password
     *                 pemanggilan class Akun dengan super
     */
    public Karyawan(String username, String password) {
        super(username, password);
    }

    public Karyawan(String username, String password, String jenisAkun, String nama) {
        super(username, password);
        this.jenisAkun = jenisAkun;
        this.nama = nama;
    }

    public Karyawan() {

    }

    /**
     * ini akan mengambalikan nilai variabel string
     * 
     * @return String
     */
    public String getJenisAkun() {
        return this.jenisAkun;
    }

    /**
     * method ini akan mengganti nilai dari bariabel
     * 
     * @param jenisAkun
     */
    public void setJenisAkun(String jenisAkun) {
        this.jenisAkun = jenisAkun;
    }

    /**
     * method ini akan mengembalikan variabel
     * 
     * @return String
     */
    public String getNama() {
        return this.nama;
    }

    /**
     * method ini akan mengganti nilai dari variabel
     * 
     * @param nama
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

}
