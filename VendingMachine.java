import java.util.ArrayList;

/**
 * Kelas VendingMachine mengelola operasi mesin kopi, dari pemilihan produk
 * hingga penyimpanan log transaksi.
 */
public class VendingMachine {
    private ArrayList<Produk> menu;
    private ArrayList<Transaksi> logTransaksi;
    private double saldoPengguna;
    private boolean isAktif;

    /**
     * Konstruktor VendingMachine. Menginisialisasi menu dan log.
     */
    public VendingMachine() {
        menu = new ArrayList<>();
        logTransaksi = new ArrayList<>();
        saldoPengguna = 0.0;
        isAktif = false;
        
        // Inisialisasi Menu Awal
        menu.add(new Produk(1, "Espresso", 15000.0, 5));
        menu.add(new Produk(2, "Latte", 20000.0, 3));
        menu.add(new Produk(3, "Cappuccino", 22000.0, 0)); // Stok 0 untuk tes refill
    }

    // --- ALUR KERJA MESIN ---

    /**
     * 1. Mengaktifkan mesin dan menampilkan menu.
     */
    public void aktifkanMesin() {
        isAktif = true;
        System.out.println("☕ Vending Coffee Machine Aktif! Selamat datang.");
        tampilkanMenu();
    }

    /**
     * 2. Menampilkan daftar produk, harga, dan stok.
     */
    public void tampilkanMenu() {
        if (!isAktif) {
            System.out.println("Mesin tidak aktif.");
            return;
        }
        System.out.println("\n--- MENU KOPI ---");
        for (Produk p : menu) {
            System.out.println(p.getDetail());
        }
        System.out.println("-----------------");
        System.out.println("Saldo Anda saat ini: Rp" + saldoPengguna);
    }

    /**
     * 3. Pengguna memilih produk dan total harga ditampilkan.
     */
    public Produk pilihProduk(int idProduk) {
        if (!isAktif) {
            System.out.println("Mesin tidak aktif.");
            return null;
        }
        for (Produk p : menu) {
            if (p.getIdProduk() == idProduk) {
                if (p.isTersedia()) {
                    System.out.println("Anda memilih: " + p.getNama());
                    System.out.println("Total Harga: Rp" + p.getHarga());
                    return p;
                } else {
                    System.out.println("❌ Maaf, stok " + p.getNama() + " habis.");
                    return null;
                }
            }
        }
        System.out.println("❌ ID Produk tidak valid.");
        return null;
    }

    // ... di dalam kelas VendingMachine ...

/**
 * Mendapatkan saldo pengguna saat ini.
 */
public double getSaldoPengguna() {
    return saldoPengguna;
}

/**
 * Memverifikasi dan memproses pembelian.
 * Mengembalikan true jika transaksi berhasil, false jika uang tidak cukup.
 */
public boolean verifikasiDanProsesPembelian(Produk produkDipilih) {
    if (produkDipilih == null) {
        System.out.println("Pembelian dibatalkan.");
        return false;
    }
    
    // Verifikasi Pembayaran
    if (saldoPengguna >= produkDipilih.getHarga()) {
        double harga = produkDipilih.getHarga();
        double kembalian = saldoPengguna - harga;
        
        // Mengeluarkan produk
        produkDipilih.kurangiStok();
        System.out.println("\n🎉 Pembelian berhasil! Silakan ambil " + produkDipilih.getNama() + " Anda.");
        
        // Transaksi disimpan ke log
        Transaksi logBaru = new Transaksi(produkDipilih.getNama(), harga, saldoPengguna, kembalian);
        simpanLog(logBaru);
        
        // Reset saldo pengguna
        if (kembalian > 0) {
            System.out.println("Kembalian Anda: Rp" + kembalian);
        }
        saldoPengguna = 0.0;
        
        // Cek Stok (dan notifikasi Admin jika habis)
        cekStok();
        
        return true; // Transaksi berhasil
    } else {
        // Jika uang tidak cukup
        return false; // Transaksi gagal
    }
}
// ...
    /**
     * 4. Pengguna melakukan pembayaran.
     */
    public void masukkanUang(double jumlahUang) {
        if (!isAktif) {
            System.out.println("Mesin tidak aktif.");
            return;
        }
        if (jumlahUang > 0) {
            saldoPengguna += jumlahUang;
            System.out.println("✅ Uang diterima. Saldo saat ini: Rp" + saldoPengguna);
        } else {
            System.out.println("Masukan uang harus positif.");
        }
    }

    /**
     * 5. Memverifikasi pembayaran dan mengeluarkan produk.
     */
    public void prosesPembelian(Produk produkDipilih) {
        if (produkDipilih == null) {
            System.out.println("Pembelian dibatalkan.");
            return;
        }
        
        // Verifikasi Pembayaran
        if (saldoPengguna >= produkDipilih.getHarga()) {
            double harga = produkDipilih.getHarga();
            double kembalian = saldoPengguna - harga;
            
            // 6. Mengeluarkan produk
            produkDipilih.kurangiStok();
            System.out.println("\n🎉 Pembelian berhasil! Silakan ambil " + produkDipilih.getNama() + " Anda.");
            
            // 7. Transaksi disimpan ke log
            Transaksi logBaru = new Transaksi(produkDipilih.getNama(), harga, saldoPengguna, kembalian);
            simpanLog(logBaru);
            
            // Reset saldo pengguna
            if (kembalian > 0) {
                System.out.println("Kembalian Anda: Rp" + kembalian);
            }
            saldoPengguna = 0.0;
            
            // 8. Cek Stok (dan notifikasi Admin jika habis)
            cekStok();
            
        } else {
            System.out.println("❌ Pembayaran gagal. Uang tidak cukup. Diperlukan Rp" + (produkDipilih.getHarga() - saldoPengguna) + " lagi.");
        }
    }

    /**
     * Menyimpan objek Transaksi ke daftar log.
     */
    private void simpanLog(Transaksi transaksi) {
        logTransaksi.add(transaksi);
        System.out.println("Log transaksi berhasil disimpan.");
    }

    /**
     * Mengecek semua stok dan memberikan peringatan jika ada yang habis.
     */
    public void cekStok() {
        for (Produk p : menu) {
            if (p.getStok() == 0) {
                System.out.println("\n*** PERINGATAN ADMIN ***");
                System.out.println("⚠️ Stok produk '" + p.getNama() + "' habis. Mohon lakukan refill!");
                System.out.println("************************\n");
            }
        }
    }
    
    /**
     * Menampilkan semua log transaksi (akses admin).
     */
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
    
    // ... di dalam kelas VendingMachine ...

    // Getter yang diperlukan untuk VendingMachineApp
    public ArrayList<Produk> getMenu() {
        return menu;
    }

    public void resetSaldo() {
        this.saldoPengguna = 0.0;
    }
// ...
    // ... di dalam kelas VendingMachine ...

    /**
     * Menambahkan stok (Refill) untuk produk tertentu (akses admin).
     */
    public void refillStok(int idProduk, int jumlah) {
    for (Produk p : menu) {
        if (p.getIdProduk() == idProduk) {
            p.tambahStok(jumlah);
            return;
        }
    }
    System.out.println("ID Produk tidak ditemukan untuk refill.");
    }
    
    // ... di dalam kelas VendingMachine ...

    /**
     * Metode untuk menambahkan objek Produk baru ke daftar menu (Akses Admin).
     */
    public void tambahProdukBaru(Produk produkBaru) {
        // Cek apakah ID produk sudah ada (opsional, tapi disarankan)
        for (Produk p : menu) {
            if (p.getIdProduk() == produkBaru.getIdProduk()) {
                System.out.println("❌ Gagal: Produk dengan ID " + produkBaru.getIdProduk() + " sudah ada.");
                return;
            }
        }
    
        // Jika ID unik, tambahkan produk
        menu.add(produkBaru);
        System.out.println("✅ Produk '" + produkBaru.getNama() + "' berhasil ditambahkan ke menu.");
    }

// ...
// ...
}