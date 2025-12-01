import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Menu[] daftarMenu = {
        new Menu("Carbonara", 45000, "Makanan"),
        new Menu("Nasi Ayam Madu", 50000, "Makanan"),
        new Menu("Choco Waffle", 40000, "Makanan"),
        new Menu("Aglio Olio", 45000, "Makanan"),
        new Menu("Lemonade Yuzu", 20000, "Minuman"),
        new Menu("Ice Latte", 20000, "Minuman"),
        new Menu("Lemon Tea", 10000, "Minuman"),
        new Menu("Air Mineral", 5000, "Minuman")
    };

    static Map<Menu, Integer> pesanan = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Aplikasi Restoran ===");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Manajemen Menu");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih) {
                case 1: menuPelanggan(); break;
                case 2: menuManajemen(); break;
                case 3: System.exit(0);
                default: System.out.println("Pilihan tidak valid!");
            }
        }
    }

    // ================= MENU PELANGGAN =================
    static void menuPelanggan() {
        pesanan.clear();
        while (true) {
            tampilkanMenu();
            System.out.print("Masukkan nama menu (atau 'selesai'): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("selesai")) {
                cetakStruk();
                break;
            }

            Menu item = cariMenu(input);
            if (item != null) {
                System.out.print("Jumlah: ");
                int jumlah = sc.nextInt();
                sc.nextLine();
                pesanan.put(item, pesanan.getOrDefault(item, 0) + jumlah);
            } else {
                System.out.println("Menu tidak ditemukan, coba lagi!");
            }
        }
    }

    static void tampilkanMenu() {
        System.out.println("\n--- Daftar Menu ---");
        System.out.println("Makanan:");
        for (Menu m : daftarMenu) {
            if (m.getKategori().equals("Makanan")) System.out.println("- " + m);
        }
        System.out.println("Minuman:");
        for (Menu m : daftarMenu) {
            if (m.getKategori().equals("Minuman")) System.out.println("- " + m);
        }
    }

    static Menu cariMenu(String nama) {
        for (Menu m : daftarMenu) {
            if (m.getNama().equalsIgnoreCase(nama)) return m;
        }
        return null;
    }

    static void cetakStruk() {
        System.out.println("\n=== Struk Pesanan ===");
        double total = 0;
        for (Menu m : pesanan.keySet()) {
            int jumlah = pesanan.get(m);
            double subtotal = m.getHarga() * jumlah;
            System.out.println(m.getNama() + " x" + jumlah + " = Rp" + subtotal);
            total += subtotal;
        }

        // Pajak & biaya pelayanan
        double pajak = total * 0.10;
        double service = 20000;
        double diskon = 0;

        // Diskon 10% jika total > 100k
        if (total > 100000) {
            diskon = total * 0.10;
            System.out.println("Diskon 10%: -Rp" + diskon);
        }

        // Promo Beli 1 Gratis 1 minuman jika total > 50k
        if (total > 50000) {
            System.out.println("Promo: Beli 1 Gratis 1 Minuman!");
        }

        double grandTotal = total - diskon + pajak + service;
        System.out.println("Pajak (10%): Rp" + pajak);
        System.out.println("Biaya Pelayanan: Rp" + service);
        System.out.println("Total Bayar: Rp" + grandTotal);
    }

    // ================= MENU MANAJEMEN =================
    static void menuManajemen() {
        while (true) {
            System.out.println("\n=== Manajemen Menu ===");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih) {
                case 1: tambahMenu(); break;
                case 2: ubahHarga(); break;
                case 3: hapusMenu(); break;
                case 4: return;
                default: System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void tambahMenu() {
        System.out.print("Nama menu baru: ");
        String nama = sc.nextLine();
        System.out.print("Harga: ");
        double harga = sc.nextDouble();
        sc.nextLine();
        System.out.print("Kategori (Makanan/Minuman): ");
        String kategori = sc.nextLine();

        Menu[] newDaftar = Arrays.copyOf(daftarMenu, daftarMenu.length + 1);
        newDaftar[newDaftar.length - 1] = new Menu(nama, harga, kategori);
        daftarMenu = newDaftar;

        System.out.println("Menu berhasil ditambahkan!");
    }

    static void ubahHarga() {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin diubah: ");
        String nama = sc.nextLine();
        Menu item = cariMenu(nama);
        if (item != null) {
            System.out.print("Harga baru: ");
            double hargaBaru = sc.nextDouble();
            sc.nextLine();
            System.out.print("Konfirmasi ubah harga? (Ya/Tidak): ");
            String konfirmasi = sc.nextLine();
            if (konfirmasi.equalsIgnoreCase("Ya")) {
                item.setHarga(hargaBaru);
                System.out.println("Harga berhasil diubah!");
            } else {
                System.out.println("Perubahan dibatalkan.");
            }
        } else {
            System.out.println("Menu tidak ditemukan!");
        }
    }

    static void hapusMenu() {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin dihapus: ");
        String nama = sc.nextLine();
        Menu item = cariMenu(nama);
        if (item != null) {
            System.out.print("Konfirmasi hapus? (Ya/Tidak): ");
            String konfirmasi = sc.nextLine();
            if (konfirmasi.equalsIgnoreCase("Ya")) {
                List<Menu> list = new ArrayList<>(Arrays.asList(daftarMenu));
                list.remove(item);
                daftarMenu = list.toArray(new Menu[0]);
                System.out.println("Menu berhasil dihapus!");
            } else {
                System.out.println("Penghapusan dibatalkan.");
            }
        } else {
            System.out.println("Menu tidak ditemukan!");
        }
    }
}
