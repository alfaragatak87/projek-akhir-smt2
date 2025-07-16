import java.util.*;

class Pembeli {
    String nama;
    String jenisTiket;
    int nomorAntrian;

    public Pembeli(String nama, String jenisTiket, int nomorAntrian) {
        this.nama = nama;
        this.jenisTiket = jenisTiket;
        this.nomorAntrian = nomorAntrian;
    }

    @Override
    public String toString() {
        return nomorAntrian + " - " + nama + " (" + jenisTiket + ")";
    }
}

public class projek_akhir_sistem_Penjualan_Tiket_konser_albanjari_coding {
    private static Queue<Pembeli> queue = new LinkedList<>();
    private static List<String> riwayatPembatalan = new ArrayList<>();
    private static int sisaTiketVIP = 30;
    private static int sisaTiketReguler = 20;
    private static int nomorTiketBerikutnya = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=================================================|");
            System.out.println("SISTEM PENJUALAN TIKET KONSER AL-BANJARI CODING");
            System.out.println("=================================================|");
            System.out.println("\nMenu:\n");
            System.out.println("1. Beli Tiket");
            System.out.println("2. Daftar Pembeli");
            System.out.println("3. Cancel Pembelian Tiket");
            System.out.println("4. Cari Pembeli");
            System.out.println("5. Lihat Riwayat Pembatalan");
            System.out.println("6. Selesai");
            System.out.println("=================================================|");
            System.out.print("\nPilih menu ==> : ");

            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (pilihan) {
                case 1 -> beliTiket(scanner);
                case 2 -> daftarPembeli();
                case 3 -> cancelPembelian(scanner);
                case 4 -> cariPembeli(scanner);
                case 5 -> lihatRiwayatPembatalan();
                case 6 -> {
                    running = false;
                    System.out.println("Program selesai. Terima kasih telah menggunakan layanan kami!");
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
        scanner.close();
    }

    private static void beliTiket(Scanner scanner) {
        System.out.println("==================================================|");
        System.out.println("\nMENU BELI TIKET");
        System.out.println("Sisa tiket VIP             10rb            : " + sisaTiketVIP + " tiket");
        System.out.println("Sisa tiket REGULER       5jt               : " + sisaTiketReguler + " tiket");
        System.out.println("==================================================|");

        System.out.print("\nMasukkan jumlah pembeli ==>: ");
        int jumlahPembeli = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        for (int i = 0; i < jumlahPembeli; i++) {
            System.out.println((i + 1) + ". - Masukkan nama :");
            String nama = scanner.nextLine();
            System.out.print("   - tiket (vip/reg) :");
            String jenisTiket = scanner.nextLine().toLowerCase();

            if (jenisTiket.equals("vip") && sisaTiketVIP > 0) {
                sisaTiketVIP--;
                queue.add(new Pembeli(nama, "VIP", nomorTiketBerikutnya++));
            } else if (jenisTiket.equals("reg") && sisaTiketReguler > 0) {
                sisaTiketReguler--;
                queue.add(new Pembeli(nama, "Reguler", nomorTiketBerikutnya++));
            } else {
                System.out.println("Tiket tidak tersedia atau jenis tiket tidak valid.");
            }
        }

        System.out.println("\nPembelian berhasil !!!");
        System.out.println("==================================================|");
    }

    private static void daftarPembeli() {
        System.out.println("==================================================|");
        System.out.println("\nDaftar Pembeli (Queue):\n");
        for (Pembeli p : queue) {
            System.out.println(p);
        }
        System.out.println("\nJumlah tiket tersedia:");
        System.out.println("VIP       10rb  : " + sisaTiketVIP);
        System.out.println("Reguler   5jt   : " + sisaTiketReguler);
        System.out.println("");
    }

    private static void cancelPembelian(Scanner scanner) {
        System.out.println("==================================================|");
        System.out.println("\nCancel Pembelian Tiket");
        System.out.print("Masukkan jumlah pembatalan: ");
        int jumlahCancel = scanner.nextInt();
        scanner.nextLine(); // clear buffer

        for (int i = 0; i < jumlahCancel; i++) {
            System.out.print("Masukkan nama pembeli yang ingin dibatalkan: ");
            String namaCancel = scanner.nextLine();
            boolean ditemukan = false;

            Iterator<Pembeli> iterator = queue.iterator();
            while (iterator.hasNext()) {
                Pembeli p = iterator.next();
                if (p.nama.equalsIgnoreCase(namaCancel)) {
                    iterator.remove(); // hapus dari queue
                    if (p.jenisTiket.equals("VIP")) {
                        sisaTiketVIP++;
                    } else {
                        sisaTiketReguler++;
                    }
                    riwayatPembatalan.add(p.nama + " (" + p.jenisTiket + ")");
                    System.out.println("Tiket atas nama " + p.nama + " berhasil dibatalkan.");
                    ditemukan = true;
                    break;
                }
            }

            if (!ditemukan) {
                System.out.println("Nama tidak ditemukan dalam antrian.");
            }
        }
    }

    private static void cariPembeli(Scanner scanner) {
        System.out.println("==================================================|");
        System.out.println("\nCari Pembeli (Search):");
        System.out.print("Masukkan nama yang ingin dicari: ");
        String namaCari = scanner.nextLine().toLowerCase(); // Mengubah input menjadi huruf kecil
        boolean ditemukan = false;
    
        System.out.println("\nHasil pencarian:");
        for (Pembeli p : queue) {
            if (p.nama.toLowerCase().contains(namaCari)) { // Memeriksa apakah namaCari ada di dalam nama pembeli
                System.out.println("Ditemukan: ==> " + p);
                ditemukan = true;
            }
        }
        if (!ditemukan) {
            System.out.println("Pembeli tidak ditemukan.");
        }
    }
    

    private static void lihatRiwayatPembatalan() {
        System.out.println("==================================================|\nLihat Riwayat Pembatalan:");
        for (String riwayat : riwayatPembatalan) {
            System.out.println(riwayat);
        }
        System.out.println("Total: " + riwayatPembatalan.size() + " tiket dibatalkan");
    }
}
