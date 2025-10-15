public class Produk {
    private int idProduk;
    private String nama;
    private double harga;
    private int stok;

    public Produk(int id, String nm, double hrg, int stk) {
        this.idProduk = id;
        this.nama = nm;
        this.harga = hrg;
        this.stok = stk;
    }

    public int getIdProduk() { return idProduk; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }

    public void kurangiStok() {
        if (stok > 0) {
            stok--;
        }
    }

    public boolean isTersedia() {
        return stok > 0;
    }

    public String getDetail() {
        return String.format("%d. %-15s (Rp%.0f) - Stok: %d", idProduk, nama, harga, stok);
    }
}