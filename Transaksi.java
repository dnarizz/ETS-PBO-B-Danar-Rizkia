import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaksi {
    private String namaProduk;
    private double hargaJual;
    private double uangDibayar;
    private double kembalian;
    private String waktuTransaksi;

    public Transaksi(String namaProduk, double hargaJual, double uangDibayar, double kembalian) {
        this.namaProduk = namaProduk;
        this.hargaJual = hargaJual;
        this.uangDibayar = uangDibayar;
        this.kembalian = kembalian;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.waktuTransaksi = LocalDateTime.now().format(formatter);
    }

    public void cetakLog() {
        System.out.println("--- LOG TRANSAKSI (" + waktuTransaksi + ") ---");
        System.out.println("Produk  : " + namaProduk);
        System.out.println("Harga   : Rp" + String.format("%,.0f", hargaJual));
        System.out.println("Dibayar : Rp" + String.format("%,.0f", uangDibayar));
        System.out.println("Kembalian: Rp" + String.format("%,.0f", kembalian));
        System.out.println("---------------------");
    }
}