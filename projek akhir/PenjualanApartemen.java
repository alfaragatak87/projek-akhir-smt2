import java.util.*;

class Pembeli {
    String nama;
    String jenisApartemen;
    int nomorAntrian;

    public Pembeli(String nama, String jenisApartemen, int nomorAntrian) {
        this.nama = nama;
        this.jenisApartemen = jenisApartemen;
        this.nomorAntrian = nomorAntrian;
    }

    @Override
    public String toString() {
        return nomorAntrian + " - " + nama + " (" + jenisApartemen + ")";
    }
}

public class PenjualanApartemen {
    private static Queue<Pembeli> queue = new LinkedList<>();
    private static List<String> riwayatPembatalan = new ArrayList<>();
    private static int sisaApartemenStudio = 10;
    private static int sisaApartemen1BR = 8;
    private static int sisaApartemen2BR = 5;
    private static int nomorApartemenBerikutnya = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=================================================|");
            System.out.println("SISTEM PENJUALAN APARTEMEN");
            System.out.println("=================================================|");
            System.out.println("\nMenu:\n");
            System.out.println("1. Beli Apartemen");
            System.out.println("2. Daftar Pembeli");
            System.out.println("3. Cancel Pembelian Apartemen");
            System.out.println("4. Cari Pembeli");
            System.out.println("5. Lihat Riwayat Pembatalan");
            System.out.println("6. Selesai");
            System.out.println("=================================================|");
            System.out.print("\nPilih menu ==> : ");

            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (pilihan) {
                case 1 -> beliApartemen(scanner);
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

    private static void beliApartemen(Scanner scanner) {
        System.out.println("==================================================\nMENU BELI APARTEMEN");
        System.out.println("==================================================\n");
        System.out.println("Sisa apartemen Studio       : " + sisaApartemenStudio + " unit");
        System.out.println("Sisa apartemen 1BR         : " + sisaApartemen1BR + " unit");
        System.out.println("Sisa apartemen 2BR         : " + sisaApartemen2BR + " unit");
        System.out.println("==================================================");

        System.out.print("\nMasukkan jumlah pembeli ==>: ");
        int jumlahPembeli = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        for (int i = 0; i < jumlahPembeli; i++) {
            System.out.println((i + 1) + ". - Masukkan nama :");
            String nama = scanner.nextLine();
            System.out.print("   - jenis apartemen (studio/1br/2br) :");
            String jenisApartemen = scanner.nextLine().toLowerCase();

            switch (jenisApartemen) {
                case "studio":
                    if (sisaApartemenStudio > 0) {
                        sisaApartemenStudio--;
                        queue.add(new Pembeli(nama, "Studio", nomorApartemenBerikutnya++));
                    } else {
                        System.out.println("Apartemen Studio tidak tersedia.");
                    }
                    break;
                case "1br":
                    if (sisaApartemen1BR > 0) {
                        sisaApartemen1BR--;
                        queue.add(new Pembeli(nama, "1BR", nomorApartemenBerikutnya++));
                    } else {
                        System.out.println("Apartemen 1BR tidak tersedia.");
                    }
                    break;
                case "2br":
                    if (sisaApartemen2BR > 0) {
                        sisaApartemen2BR--;
                        queue.add(new Pembeli(nama, "2BR", nomorApartemenBerikutnya++));
                    } else {
                        System.out.println("Apartemen 2BR tidak tersedia.");
                    }
                    break;
                default:
                    System.out.println("Jenis apartemen tidak valid.");
            }
        }

        System.out.println("\nPembelian berhasil !!!");
        System.out.println("==================================================");
    }

    private static void daftarPembeli() {
        System.out.println("==================================================");
        System.out.println("Daftar Pembeli (Queue):\n");
        for (Pembeli p : queue) {
            System.out.println(p);
        }
        System.out.println("\nJumlah apartemen tersedia:");
        System.out.println("Studio    : " + sisaApartemenStudio);
        System.out.println("1BR       : " + sisaApartemen1BR);
        System.out.println("2BR       : " + sisaApartemen2BR);
    }

    private static void cancelPembelian(Scanner scanner) {
        System.out.println("==================================================");
        System.out.println("\nCancel Pembelian Apartemen");
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
                    switch (p.jenisApartemen) {
                        case "Studio" -> sisaApartemenStudio++;
                        case "1BR" -> sisaApartemen1BR++;
                        case "2BR" -> sisaApartemen2BR++;
                    }
                    riwayatPembatalan.add(p.nama + " (" + p.jenisApartemen + ")");
                    System.out.println("Pembelian atas nama " + p.nama + " berhasil dibatalkan.");
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
        System.out.println("===============================");
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
        System.out.println("===============================\nLihat Riwayat Pembatalan:");
        for (String riwayat : riwayatPembatalan) {
            System.out.println(riwayat);
        }
        System.out.println("Total: " + riwayatPembatalan.size() + " pembatalan");
    }
}
