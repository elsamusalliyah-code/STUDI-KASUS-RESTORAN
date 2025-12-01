public class Menu {
    String nama;
    int harga;
    String kategori;

    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public void setHarga(int hargaBaru) {
        this.harga = hargaBaru;
    }

    public String toString() {
        return nama + " - Rp" + harga + " [" + kategori + "]";
    }
}












