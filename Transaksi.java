import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Kelas Transaksi merekam detail setiap penjualan yang berhasil.
 */
public class Transaksi {
    private String namaProduk;
    private double hargaJual;
    private double uangDibayar;
    private double kembalian;
    private String waktuTransaksi;

    /**
     * Konstruktor untuk membuat objek Transaksi.
     */
    public Transaksi(String namaProduk, double hargaJual, double uangDibayar, double kembalian) {
        this.namaProduk = namaProduk;
        this.hargaJual = hargaJual;
        this.uangDibayar = uangDibayar;
        this.kembalian = kembalian;
        
        // Mengambil waktu transaksi saat ini
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.waktuTransaksi = LocalDateTime.now().format(formatter);
    }

    /**
     * Menampilkan detail log transaksi.
     */
    public void cetakLog() {
        System.out.println("--- LOG TRANSAKSI ---");
        System.out.println("Waktu   : " + waktuTransaksi);
        System.out.println("Produk  : " + namaProduk);
        System.out.println("Harga   : Rp" + hargaJual);
        System.out.println("Dibayar : Rp" + uangDibayar);
        System.out.println("Kembalian: Rp" + kembalian);
        System.out.println("---------------------");
    }
}