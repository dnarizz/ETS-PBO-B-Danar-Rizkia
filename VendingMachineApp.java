import java.util.Scanner;
public class VendingMachineApp {
    
    private VendingMachine mesin;
    private Scanner scanner;

    public VendingMachineApp() {
        mesin = new VendingMachine();
        scanner = new Scanner(System.in);
    
    }
    
    public void jalankanAplikasi() {
        mesin.aktifkanMesin();
        int pilihan = -1;

        while (pilihan != 0) {
            System.out.println("\n============================================");
            System.out.println("1. Lakukan Transaksi Baru");
            System.out.println("2. Tampilkan Menu & Saldo");
            System.out.println("3. [Admin] Tampilkan Log Transaksi");
            System.out.println("0. Matikan Mesin (Keluar)");
            System.out.print("Pilih Aksi (0-3): ");

            try {
                pilihan = scanner.nextInt();
                scanner.nextLine();
                
                switch (pilihan) {
                    case 1: prosesTransaksi(); break;
                    case 2: mesin.tampilkanMenu(); break;
                    case 3: mesin.tampilkanSemuaLog(); break;
                    case 0: System.out.println("Mesin dimatikan. Sampai jumpa!"); break;
                    default: System.out.println("Pilihan tidak valid.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Input harus berupa angka.");
                scanner.nextLine();
                pilihan = -1;
            }
        }
        scanner.close();
    }
    
    private void prosesTransaksi() {
        mesin.tampilkanMenu();
        System.out.print("Masukkan ID produk (0 untuk batal): ");
        
        int idProduk = -1;
        try {
            idProduk = scanner.nextInt();
            scanner.nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Input ID harus berupa angka.");
            scanner.nextLine(); 
            return;
        }
        
        if (idProduk == 0) return;
        
        Produk produkDipilih = mesin.cariProduk(idProduk);
        
        if (produkDipilih == null) {
            System.out.println("ID produk tidak ditemukan.");
            return;
        }
        
        if (!produkDipilih.isTersedia()) {
            System.out.println("Maaf, stok " + produkDipilih.getNama() + " habis.");
            return;
        }
        
        double harga = produkDipilih.getHarga();
        boolean pembayaranBerhasil = false;
        
        System.out.println("\n--- PEMBAYARAN: " + produkDipilih.getNama() + " (Rp" + String.format("%,.0f", harga) + ") ---");
        System.out.println("Silahkan Masukkan Uang");
        
        while (!pembayaranBerhasil) {
            double kekurangan = harga - mesin.getSaldoPengguna();
            
            if (kekurangan > 0) {
                System.out.printf("Dibutuhkan tambahan: Rp%,.0f\n", kekurangan);
                System.out.print("Masukkan uang (atau 0 untuk batal): ");
                
                double inputUang = 0;
                try {
                    inputUang = scanner.nextDouble();
                    scanner.nextLine();
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Input uang tidak valid.");
                    scanner.nextLine();
                    continue; 
                }
                
                if (inputUang == 0) {
                    System.out.println("Pembayaran dibatalkan. Sisa saldo Rp" + String.format("%,.0f", mesin.getSaldoPengguna()) + " dikembalikan.");
                    mesin.resetSaldo();
                    return;
                }
                
                if (inputUang > 0) {
                    mesin.masukkanUang(inputUang);
                } else {
                    System.out.println("Jumlah uang harus positif.");
                }
            }
            if (mesin.getSaldoPengguna() >= harga) {
                pembayaranBerhasil = mesin.verifikasiDanProsesPembelian(produkDipilih);
            }
        }
    }
    
    public static void main(String[] args) {
        VendingMachineApp app = new VendingMachineApp();
        app.jalankanAplikasi();
    }
}