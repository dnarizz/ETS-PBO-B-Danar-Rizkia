/**
 * Kelas Produk merepresentasikan item yang dijual dalam Vending Machine.
 */
public class Produk {
    private int idProduk;
    private String nama;
    private double harga;
    private int stok;

    /**
     * Konstruktor untuk membuat objek Produk baru.
     */
    public Produk(int idProduk, String nama, double harga, int stok) {
        this.idProduk = idProduk;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // --- GETTERS ---
    public int getIdProduk() {
        return idProduk;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    // --- METODE PERILAKU ---
    /**
     * Mengurangi jumlah stok produk.
     */
    public void kurangiStok() {
        if (stok > 0) {
            stok--;
        }
    }

    /**
     * Menambahkan stok (untuk keperluan admin/refill).
     */
    public void tambahStok(int jumlah) {
        stok += jumlah;
    }

    /**
     * Memeriksa apakah produk masih tersedia.
     */
    public boolean isTersedia() {
        return stok > 0;
    }

    /**
     * Format tampilan produk untuk menu.
     */
    public String getDetail() {
        return idProduk + ". " + nama + " (Rp" + harga + ") - Stok: " + stok;
    }
}