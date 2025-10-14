import java.util.Scanner;

/**
 * Kelas VendingMachineApp adalah program utama yang menjalankan antarmuka
 * interaktif untuk Vending Coffee Machine.
 */
public class VendingMachineApp {
    
    private VendingMachine mesin;
    private Scanner scanner;

    public VendingMachineApp() {
        mesin = new VendingMachine(); // Membuat objek mesin
        scanner = new Scanner(System.in); // Membuat objek scanner untuk input
    }
    
    // --- METODE UTAMA PROGRAM ---
    
    public void jalankanAplikasi() {
        mesin.aktifkanMesin();
        int pilihan = -1;

        while (pilihan != 0) {
            System.out.println("\n============================================");
            System.out.println("           Vending Machine Menu");
            System.out.println("============================================");
            System.out.println("1. Lakukan Transaksi Baru (Lihat Menu)");
            System.out.println("2. [Admin] Tampilkan Log Transaksi");
            System.out.println("0. Matikan Mesin (Keluar)");
            System.out.print("Pilih Aksi (0-2): ");

            if (scanner.hasNextInt()) {
                pilihan = scanner.nextInt();
                scanner.nextLine(); // Membuang newline
                
                switch (pilihan) {
                    case 1:
                        prosesTransaksi();
                        break;
                    case 2:
                        mesin.tampilkanSemuaLog();
                        break;
                    case 0:
                        System.out.println("Mesin dimatikan. Terima kasih!");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } else {
                System.out.println("Input harus berupa angka.");
                scanner.nextLine(); // Membuang input yang salah
            }
        }
        scanner.close();
    }
    
    // --- PROSES TRANSAKSI INTERAKTIF ---
    
    private void prosesTransaksi() {
        mesin.tampilkanMenu();
        System.out.print("Masukkan ID produk yang ingin dibeli (atau 0 untuk batal): ");
        
        if (!scanner.hasNextInt()) {
            System.out.println("ID produk harus berupa angka.");
            scanner.nextLine(); 
            return;
        }
        
        int idProduk = scanner.nextInt();
        scanner.nextLine();
        
        if (idProduk == 0) {
            System.out.println("Transaksi dibatalkan.");
            return;
        }
        
        Produk produkDipilih = null;
        // Cari produk di dalam menu mesin
        for (Produk p : mesin.getMenu()) { // Perlu menambahkan getMenu() di VendingMachine
            if (p.getIdProduk() == idProduk) {
                produkDipilih = p;
                break;
            }
        }
        
        if (produkDipilih == null) {
            System.out.println("ID produk tidak ditemukan atau tidak valid.");
            return;
        }
        
        if (!produkDipilih.isTersedia()) {
            System.out.println("Maaf, stok " + produkDipilih.getNama() + " habis.");
            return;
        }

        // --- PROSES PEMBAYARAN DINAMIS ---
        double harga = produkDipilih.getHarga();
        boolean pembayaranBerhasil = false;
        
        System.out.println("\n--- PROSES PEMBAYARAN ---");
        System.out.println("Total Harga: Rp" + harga);
        System.out.println("Saldo Anda saat ini: Rp" + mesin.getSaldoPengguna());

        while (!pembayaranBerhasil) {
            double kekurangan = harga - mesin.getSaldoPengguna();
            if (kekurangan > 0) {
                System.out.printf("Dibutuhkan tambahan: Rp%.2f\n", kekurangan);
                System.out.print("Masukkan uang (atau -1 untuk batal): ");
                
                if (!scanner.hasNextDouble()) {
                    System.out.println("Input uang harus berupa angka.");
                    scanner.nextLine(); 
                    continue; // Ulangi loop
                }
                
                double inputUang = scanner.nextDouble();
                scanner.nextLine();
                
                if (inputUang == -1) {
                    System.out.println("Pembayaran dibatalkan. Saldo dikembalikan: Rp" + mesin.getSaldoPengguna());
                    // Tambahkan logic untuk mengosongkan saldo jika user batal
                    // (misal: mesin.kembalikanSaldo())
                    break; 
                }
                
                if (inputUang > 0) {
                    mesin.masukkanUang(inputUang);
                } else {
                    System.out.println("Jumlah uang harus positif.");
                }

            }
            
            // Coba verifikasi setelah input uang (atau jika saldo sudah cukup dari awal)
            if (mesin.getSaldoPengguna() >= harga) {
                pembayaranBerhasil = mesin.verifikasiDanProsesPembelian(produkDipilih);
            }
        }
        
        // Jika pembayaran gagal (dibatalkan), reset saldo
        if (!pembayaranBerhasil && mesin.getSaldoPengguna() > 0) {
            System.out.println("Pengembalian sisa saldo: Rp" + mesin.getSaldoPengguna());
            mesin.resetSaldo(); // Perlu menambahkan method ini di VendingMachine
        }
    }
    
    
    // --- MAIN METHOD ---
    public static void main(String[] args) {
        VendingMachineApp app = new VendingMachineApp();
        app.jalankanAplikasi();
    }
}