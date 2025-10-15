import java.util.ArrayList;
import java.util.List; 

public class VendingMachine {
    private List<Produk> menu;
    private List<Transaksi> logTransaksi;
    private double saldoPengguna;
    private boolean isAktif;

    public VendingMachine() {
        menu = new ArrayList<>();
        logTransaksi = new ArrayList<>();
        saldoPengguna = 0.0;
        isAktif = false;
        
        menu.add(new Produk(1, "Burger", 15000.0, 5));
        menu.add(new Produk(2, "Lays", 20000.0, 3));
        menu.add(new Produk(3, "Pizza", 22000.0, 0));
        menu.add(new Produk(4, "Gary", 7000.0, 2));
        menu.add(new Produk(5, "Twistko", 13000.0, 4));
        menu.add(new Produk(6, "Oreo", 5000.0, 1));
    }

    public List<Produk> getMenu() { return menu; }
    public double getSaldoPengguna() { return saldoPengguna; }
    public void resetSaldo() { this.saldoPengguna = 0.0; }


    public void aktifkanMesin() {
        isAktif = true;
        System.out.println("Vending Snack Aktif!");
        tampilkanMenu();
    }

    public void tampilkanMenu() {
        System.out.println("\n--- MENU SNACK ---");
        for (Produk p : menu) {
            System.out.println(p.getDetail());
        }
        System.out.println("-----------------");
        System.out.println("Saldo Anda saat ini: Rp" + String.format("%,.0f", saldoPengguna));
    }

    public Produk cariProduk(int idProduk) {
        for (Produk p : menu) {
            if (p.getIdProduk() == idProduk) {
                return p;
            }
        }
        return null;
    }

    public void masukkanUang(double jumlahUang) {
        if (jumlahUang > 0) {
            saldoPengguna += jumlahUang;
            System.out.println("Uang diterima. Saldo saat ini: Rp" + String.format("%,.0f", saldoPengguna));
        } else {
            System.out.println("Anda salah memasukkan uang.");
        }
    }

   
    public boolean verifikasiDanProsesPembelian(Produk produkDipilih) {
        if (produkDipilih == null || !produkDipilih.isTersedia()) {
            return false;
        }

        if (saldoPengguna >= produkDipilih.getHarga()) {
            double harga = produkDipilih.getHarga();
            double kembalian = saldoPengguna - harga;
            
            produkDipilih.kurangiStok();
            System.out.println("\nPembelian berhasil! Silakan ambil " + produkDipilih.getNama() + " Anda.");
            
            Transaksi logBaru = new Transaksi(produkDipilih.getNama(), harga, saldoPengguna, kembalian);
            logTransaksi.add(logBaru);
            
            if (kembalian > 0) {
                System.out.println("Kembalian Anda: Rp" + String.format("%,.0f", kembalian));
            }
            saldoPengguna = 0.0;
            
            cekStok();
            return true;
        } else {
            return false;
        }
    }

    private void cekStok() {
        for (Produk p : menu) {
            if (p.getStok() == 0) {
                System.out.println("\n*** PERINGATAN ADMIN ***");
                System.out.println("Stok produk '" + p.getNama() + "' habis. Mohon lakukan refill!");
                System.out.println("************************\n");
            }
        }
    }
    
    public void tampilkanSemuaLog() {
        System.out.println("\n====== RIWAYAT TRANSAKSI (" + logTransaksi.size() + " Total) ======");
        if (logTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        for (Transaksi t : logTransaksi) {
            t.cetakLog();
        }
    }
}