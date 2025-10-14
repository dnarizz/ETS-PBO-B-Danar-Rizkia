public class DemoMain {

    public static void main(String[] args) {
        // 1. Inisialisasi Sistem
        VendingMachine mesinKopi = new VendingMachine();
        System.out.println("============================================");
        System.out.println(" SIMULASI VENDING COFFEE MACHINE DIMULAI ☕");
        System.out.println("============================================");

        // --- FITUR 1: ADMIN MENAMBAHKAN PRODUK BARU (atau REFILL) ---
        // (Diasumsikan ada metode admin untuk menambah/mengubah menu)
        
        System.out.println("\n--- [1] ADMIN: Tambah Produk Baru ---");
        // Kita bisa buatkan method di VendingMachine untuk ini, tapi untuk
        // demo singkat, kita bisa tambahkan stok yang habis (misal Cappuccino ID 3)
        // Di kelas VendingMachine, kita tambahkan method 'refillStok':
        // public void refillStok(int id, int jumlah) { ... }
        
        // Asumsi VendingMachine memiliki method helper untuk refill:
        mesinKopi.refillStok(3, 10); // Mengisi ulang Cappuccino (ID 3) sebanyak 10
        System.out.println("Produk Cappuccino (ID 3) telah di-refill.");
        
        // 2. Mesin Aktif dan Tampilkan Menu
        mesinKopi.aktifkanMesin(); 
        
        // --- FITUR 2: MELAKUKAN TRANSAKSI PERTAMA ---
        System.out.println("\n--- [2] TRANSAKSI PENGGUNA 1 (Sukses) ---");
        
        // Pengguna memilih produk Latte (ID 2)
        Produk produk1 = mesinKopi.pilihProduk(2); // Harga 20000.0
        
        // Pengguna membayar 25000.0 (Uang lebih)
        if (produk1 != null) {
            mesinKopi.masukkanUang(25000.0);
            mesinKopi.prosesPembelian(produk1);
        }
        
        // --- FITUR 3: MELAKUKAN TRANSAKSI KEDUA (Gagal karena kurang bayar) ---
        System.out.println("\n--- [3] TRANSAKSI PENGGUNA 2 (Gagal) ---");
        
        // Pengguna memilih produk Espresso (ID 1)
        Produk produk2 = mesinKopi.pilihProduk(1); // Harga 15000.0
        
        // Pengguna hanya membayar 10000.0
        if (produk2 != null) {
            mesinKopi.masukkanUang(10000.0);
            mesinKopi.prosesPembelian(produk2); // Akan gagal
            
            // Pengguna menambah uang
            mesinKopi.masukkanUang(5000.0);
            mesinKopi.prosesPembelian(produk2); // Akan sukses
        }

        // --- FITUR 4: MELIHAT LOG TRANSAKSI ---
        System.out.println("\n--- [4] ADMIN: Lihat Log Transaksi ---");
        mesinKopi.tampilkanSemuaLog();

        System.out.println("\n============================================");
        System.out.println("       SIMULASI VENDING SELESAI");
        System.out.println("============================================");
    }
}