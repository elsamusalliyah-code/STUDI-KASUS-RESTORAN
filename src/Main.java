import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);
    static ArrayList<Menu> daftarMenu = new ArrayList<>();
    static ArrayList<String> pesanan = new ArrayList<>();
    static ArrayList<Integer> jumlahPesanan = new ArrayList<>();

    public static void main(String[] args) {
        inisialisasiMenu();
        menuUtama();
    }

    // Inisialisasi menu awal
    static void inisialisasiMenu() {
        daftarMenu.add(new Menu("Carbonara", 45000, "Makanan"));
        daftarMenu.add(new Menu("Nasi Ayam Madu", 50000, "Makanan"));
        daftarMenu.add(new Menu("Choco Waffle", 40000, "Makanan"));
        daftarMenu.add(new Menu("Aglio Olio", 45000, "Makanan"));
        daftarMenu.add(new Menu("Lemonade Yuzu", 20000, "Minuman"));
        daftarMenu.add(new Menu("Ice Latte", 20000, "Minuman"));
        daftarMenu.add(new Menu("Lemon Tea", 10000, "Minuman"));
        daftarMenu.add(new Menu("Air Mineral", 5000, "Minuman"));
    }

    // Menu utama
    static void menuUtama() {
        while (true) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Manajemen Menu");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");
            String pilihan = input.nextLine();

            switch (pilihan) {
                case "1": menuPelanggan(); break;
                case "2": menuManajemen(); break;
                case "3": System.out.println("Terima kasih!"); return;
                default: System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // Menu pelanggan
    static void menuPelanggan() {
        tampilkanMenu();
        while (true) {
            System.out.print("Masukkan nama menu (atau 'selesai'): ");
            String nama = input.nextLine();
            if (nama.equalsIgnoreCase("selesai")) break;

            boolean ditemukan = false;
            for (Menu m : daftarMenu) {
                if (m.nama.equalsIgnoreCase(nama)) {
                    System.out.print("Jumlah: ");
                    int jumlah = Integer.parseInt(input.nextLine());
                    pesanan.add(m.nama);
                    jumlahPesanan.add(jumlah);
                    ditemukan = true;
                    break;
                }
            }
            if (!ditemukan) System.out.println("Menu tidak ditemukan, coba lagi.");
        }
        hitungTotalDanCetakStruk();
        pesanan.clear(); jumlahPesanan.clear();
    }

    // Tampilkan menu
    static void tampilkanMenu() {
        System.out.println("\n--- MENU MAKANAN ---");
        for (Menu m : daftarMenu) {
            if (m.kategori.equalsIgnoreCase("Makanan")) System.out.println("- " + m);
        }
        System.out.println("\n--- MENU MINUMAN ---");
        for (Menu m : daftarMenu) {
            if (m.kategori.equalsIgnoreCase("Minuman")) System.out.println("- " + m);
        }
    }

    // Hitung total dan cetak struk
    static void hitungTotalDanCetakStruk() {
        int total = 0;
        Map<String, Integer> hargaMap = new HashMap<>();
        for (Menu m : daftarMenu) {
            hargaMap.put(m.nama, m.harga);
        }

        System.out.println("\n========== STRUK PEMESANAN ==========");
        System.out.printf("%-20s %-10s %-10s\n", "Item", "Jumlah", "Harga");
        System.out.println("----------------------------------------------");

        for (int i = 0; i < pesanan.size(); i++) {
            String nama = pesanan.get(i);
            int jumlah = jumlahPesanan.get(i);
            int harga = hargaMap.get(nama);
            int subtotal = harga * jumlah;
            System.out.printf("%-20s %-10d Rp%-9d Rp%-9d\n", nama, jumlah, harga, subtotal);
            total += subtotal;
        }

        double pajak = total * 0.10;
        int pelayanan = 20000;
        double diskon = (total > 100000) ? total * 0.10 : 0;
        boolean promoMinuman = total > 50000;

        System.out.println("----------------------------------------------");
        System.out.printf("%-30s Rp%-9d\n", "Subtotal", total);
        System.out.printf("%-30s Rp%-9d\n", "Pajak (10%)", (int)pajak);
        System.out.printf("%-30s Rp%-9d\n", "Biaya Pelayanan", pelayanan);
        if (diskon > 0)
            System.out.printf("%-30s -Rp%-9d\n", "Diskon 10%", (int)diskon);
        if (promoMinuman)
            System.out.printf("%-30s %s\n", "Promo Minuman", "Beli 1 Gratis 1");

        double totalAkhir = total + pajak + pelayanan - diskon;
        System.out.println("----------------------------------------------");
        System.out.printf("%-30s Rp%-9d\n", "TOTAL BAYAR", (int)totalAkhir);
        System.out.println("==============================================");
        System.out.println("Terima kasih telah memesan di restoran kami!");
    }

    // Menu manajemen
    static void menuManajemen() {
        while (true) {
            System.out.println("\n=== MANAJEMEN MENU ===");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");
            String pilihan = input.nextLine();

            switch (pilihan) {
                case "1": tambahMenu(); break;
                case "2": ubahHarga(); break;
                case "3": hapusMenu(); break;
                case "4": return;
                default: System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static void tambahMenu() {
        System.out.print("Nama Menu: ");
        String nama = input.nextLine();
        System.out.print("Harga: ");
        int harga = Integer.parseInt(input.nextLine());
        System.out.print("Kategori (Makanan/Minuman): ");
        String kategori = input.nextLine();
        daftarMenu.add(new Menu(nama, harga, kategori));
        System.out.println("Menu berhasil ditambahkan.");
    }

    static void ubahHarga() {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin diubah: ");
        String nama = input.nextLine();
        for (Menu m : daftarMenu) {
            if (m.nama.equalsIgnoreCase(nama)) {
                System.out.print("Harga baru: ");
                int hargaBaru = Integer.parseInt(input.nextLine());
                System.out.print("Yakin ubah harga? (Ya/Tidak): ");
                if (input.nextLine().equalsIgnoreCase("Ya")) {
                    m.setHarga(hargaBaru);
                    System.out.println("Harga berhasil diubah.");
                }
                return;
            }
        }
        System.out.println("Menu tidak ditemukan.");
    }

    static void hapusMenu() {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin dihapus: ");
        String nama = input.nextLine();
        for (Menu m : daftarMenu) {
            if (m.nama.equalsIgnoreCase(nama)) {
                System.out.print("Yakin hapus menu? (Ya/Tidak): ");
                if (input.nextLine().equalsIgnoreCase("Ya")) {
                    daftarMenu.remove(m);
                    System.out.println("Menu berhasil dihapus.");
                }
                return;
            }
        }
        System.out.println("Menu tidak ditemukan.");
    }
}
