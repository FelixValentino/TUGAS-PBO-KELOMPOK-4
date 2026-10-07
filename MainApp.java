import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class MainApp {
    private static final Scanner input = new Scanner(System.in);
    public static String getHariIni() {
        LocalDate hariIni = LocalDate.now();
        int hari = hariIni.getDayOfWeek().getValue(); 

        if (hari == 1) {
            return "Senin";
        }
        else if (hari == 2) {
            return "Selasa";
        }
        else if (hari == 3) {
            return "Rabu";
        }
        else if (hari == 4) {
            return "Kamis";
        }
        else if (hari == 5) {
            return "Jumat";
        }
        else if (hari == 6) {
            return "Sabtu";
        }
        else if (hari == 7) {
            return "Minggu";
        }
        else {
            return "";
        }
    }

    public static void cetakJadwalDSN(Dosen dsn) { // hari ini
        String hariIni = getHariIni();
        System.out.println("\n===== Jadwal Hari Ini =====");
        System.out.println("Hari : " + hariIni);
        bacaFileJadwalDSN(dsn.getInisialMengajar(), "hari", hariIni, null);
    }

    public static void cetakJadwalDSN(Dosen dsn, int kodeMinggu) { // minggu ini
        if (kodeMinggu == 1) {
            System.out.println("\n===== Jadwal Minggu Ini =====");
            bacaFileJadwalDSN(dsn.getInisialMengajar(), "minggu", null, null);
        }
    }

    public static void cetakJadwalDSN(Dosen dsn, boolean satuSemesterPenuh) { // semester ini
        if (satuSemesterPenuh) {
            System.out.println("\n===== Jadwal Semester Ini =====");
            bacaFileJadwalDSN(dsn.getInisialMengajar(), "semester_ini", null, null);
        }
    }

    public static void cetakJadwalDSN(Dosen dsn, String semesterLain) { // semester lain
        System.out.println("\n===== Jadwal Semester Lain =====");
        bacaFileJadwalDSN(dsn.getInisialMengajar(), "semester_lain", null, semesterLain);
    }

    private static void bacaFileJadwalDSN(String inisialDosen, String tipeFilter, String filterHari, String inputSemesterLain) {
        boolean ditemukan = false;
        String currentHeader = ""; 
        boolean headerSudahDicetak = false;
        boolean statusBlokValid = false;

        int basisSemester2026 = 1; 
        
        if (tipeFilter.equalsIgnoreCase("semester_lain") && inputSemesterLain != null) {
            basisSemester2026 = Integer.parseInt(inputSemesterLain);
        }

        try (BufferedReader br = new BufferedReader(new FileReader("Jadwal.txt"))) {
            String baris;
            
            while ((baris = br.readLine()) != null) {
                baris = baris.trim(); 

                if (baris.contains("JADWAL KULIAH INFORMATIKA")) {
                    currentHeader = baris;
                    headerSudahDicetak = false;
                    
                    String[] parts = baris.split(" ");
                    String tahunDiFile = parts[3];    
                    String semesterDiFile = parts[5]; 

                    if (tahunDiFile.equals("2026") && semesterDiFile.equals(String.valueOf(basisSemester2026))) {
                        statusBlokValid = true;
                    } 
                    else if (tahunDiFile.equals("2025") && semesterDiFile.equals(String.valueOf(basisSemester2026 + 2))) {
                        statusBlokValid = true;
                    } 
                    else if (tahunDiFile.equals("2024") && semesterDiFile.equals(String.valueOf(basisSemester2026 + 4))) {
                        statusBlokValid = true;
                    } 
                    else if (tahunDiFile.equals("2023") && semesterDiFile.equals(String.valueOf(basisSemester2026 + 6))) {
                        statusBlokValid = true;
                    } 
                    else {
                        statusBlokValid = false; 
                    }
                    continue; 
                }

                if (statusBlokValid && !baris.isEmpty() && baris.contains(inisialDosen)) {
                    String[] detail = baris.split(" - ");

                    if (tipeFilter.equalsIgnoreCase("hari")) {
                        if (baris.contains(filterHari)) {
                            if (!headerSudahDicetak) {
                                System.out.println("\n" + currentHeader);
                                headerSudahDicetak = true;
                            }
                            if (detail.length >= 4) {
                                System.out.println(detail[0] + " - " + detail[1] + " - " + detail[2] + " - " + detail[3]);
                            }
                            ditemukan = true;
                        }
                    } 
                    else if (tipeFilter.equalsIgnoreCase("minggu")) {
                        if (baris.contains("Senin") || baris.contains("Selasa") || 
                            baris.contains("Rabu") || baris.contains("Kamis") || 
                            baris.contains("Jumat") || baris.contains("Sabtu")) {
                            
                            if (!headerSudahDicetak) {
                                System.out.println("\n" + currentHeader);
                                headerSudahDicetak = true;
                            }
                            if (detail.length >= 4) {
                                System.out.println(detail[0] + " - " + detail[1] + " - " + detail[2] + " - " + detail[3]);
                            }
                            ditemukan = true;
                        }
                    }
                    else if (tipeFilter.equalsIgnoreCase("semester_ini") || tipeFilter.equalsIgnoreCase("semester_lain")) {
                        if (!headerSudahDicetak) {
                            System.out.println("\n" + currentHeader);
                            headerSudahDicetak = true;
                        }
                        if (detail.length >= 7) {
                            System.out.println(detail[0] + " - " + detail[4] + " - " + detail[5] + " - " + detail[6]);
                        } else {
                            System.out.println(baris);
                        }
                        ditemukan = true;
                    }
                }
            }

            if (!ditemukan) {
                System.out.println("\nTidak ada jadwal mengajar yang ditemukan pada pola semester ini.");
            }

        } 
        catch (IOException e) {
            System.out.println("[Error: File Jadwal.txt tidak ditemukan atau tidak bisa dibaca!]");
        }
    }

    public static void cetakJadwalMHS(Mahasiswa mhs) { // hari ini
        String hariIni = getHariIni();
        System.out.println("\n===== Jadwal Hari Ini =====");
        System.out.println("Hari : " + hariIni);
        bacaFileJadwalMHS(mhs.getTahun(), String.valueOf(mhs.getSemester()), hariIni);
    }

    public static void cetakJadwalMHS(Mahasiswa mhs, int kodeMinggu) { // minggu ini
        if (kodeMinggu == 1) {
            System.out.println("\n===== Jadwal Minggu Ini =====");
            bacaFileJadwalMHS(mhs.getTahun(), String.valueOf(mhs.getSemester()), "minggu");
        }
    }

    public static void cetakJadwalMHS(Mahasiswa mhs, boolean satuSemesterPenuh) { // semester ini
        if (satuSemesterPenuh) {
            System.out.println("\n===== Jadwal Semester Ini =====");
            bacaFileJadwalMHS(mhs.getTahun(), String.valueOf(mhs.getSemester()), null);
        }
    }

    public static void cetakJadwalMHS(Mahasiswa mhs, String semesterLain) { // semester lain
        System.out.println("\n===== Jadwal Semester Lain =====");
        bacaFileJadwalMHS(mhs.getTahun(), semesterLain, null);
    }

    private static void bacaFileJadwalMHS(String tahun, String semester, String filterHari) {
        String targetHeader = "JADWAL KULIAH INFORMATIKA " + tahun + " SEMESTER " + semester;
        boolean status = false;

        try (BufferedReader br = new BufferedReader(new FileReader("Jadwal.txt"))) {
            String baris;
        
            while ((baris = br.readLine()) != null) {
                baris = baris.trim(); 

                if (baris.equals(targetHeader)) {
                    status = true;
                    System.out.println(baris);
                    continue; 
                }

                if (status) {
                    if (baris.isEmpty() || baris.contains("JADWAL KULIAH INFORMATIKA")) {
                        break;
                    }

                    String[] detail = baris.split(" - ");

                    if (filterHari == null) {
                        if (detail.length >= 7) {
                            System.out.println(detail[0] + " - " + detail[4] + " - " + detail[5] + " - " + detail[6]);
                        } else {
                            System.out.println(baris);
                        }
                    } 

                    else if (filterHari.equalsIgnoreCase("minggu")) {
                        if (baris.contains("Senin") || baris.contains("Selasa") || 
                            baris.contains("Rabu") || baris.contains("Kamis") || 
                            baris.contains("Jumat") || baris.contains("Sabtu")) {
                            if (detail.length >= 4) {
                                System.out.println(detail[0] + " - " + detail[1] + " - " + detail[2] + " - " + detail[3]);
                            }
                        }
                    }

                    else if (filterHari != null && baris.contains(filterHari)) {
                        if (detail.length >= 4) {
                            System.out.println(detail[0] + " - " + detail[1] + " - " + detail[2] + " - " + detail[3]);
                        } else {
                            System.out.println(baris); 
                        }
                    }
                }
            }

            if (!status) {
                System.out.println("Jadwal tidak ditemukan untuk angkatan " + tahun + " semester " + semester);
            }

        } 
        catch (IOException e) {
            System.out.println("[Error: File Jadwal.txt tidak ditemukan atau tidak bisa dibaca!]");
        }
    }

    public static void menuDashboardDSN(Dosen dsn) {
        while (true) { 
            System.out.println("\n===== DASHBOARD DOSEN =====");
            dsn.tampilkanInfo();

            System.out.println("1. Lihat Jadwal Hari ini");
            System.out.println("2. Lihat Jadwal Minggu ini");
            System.out.println("3. Lihat Jadwal Semester Ini");
            System.out.println("4. Lihat Jadwal Semester Lain");
            System.out.println("5. Back");

            int pilihan;
            System.out.print("Masukkan pilihan : ");
            pilihan = input.nextInt();

            if (pilihan == 1) {
                cetakJadwalDSN(dsn);
            }
            else if (pilihan == 2) {
                cetakJadwalDSN(dsn, 1);
            }
            else if (pilihan == 3) {
                cetakJadwalDSN(dsn, true);
            }
            else if (pilihan == 4) {
                String semesterLain;
                System.out.print("Masukkan semester yang ingin dilihat : ");
                semesterLain = input.next();
                cetakJadwalDSN(dsn, semesterLain);
            }
            else if (pilihan == 5) {
                break;
            }
        }
    }

    public static void menuDashboardMHS(Mahasiswa mhs) {
        while (true) { 
            System.out.println("\n===== DASHBOARD MAHASISWA =====");
            mhs.tampilkanInfo();

            System.out.println("1. Lihat Jadwal Hari ini");
            System.out.println("2. Lihat Jadwal Minggu ini");
            System.out.println("3. Lihat Jadwal Semester Ini");
            System.out.println("4. Lihat Jadwal Semester Lain");
            System.out.println("5. Back");

            int pilihan;
            System.out.print("Masukkan pilihan : ");
            pilihan = input.nextInt();

            if (pilihan == 1) {
                cetakJadwalMHS(mhs);
            }
            else if (pilihan == 2) {
                cetakJadwalMHS(mhs, 1);
            }
            else if (pilihan == 3) {
                cetakJadwalMHS(mhs, true);
            }
            else if (pilihan == 4) {
                String semesterLain;
                System.out.print("Masukkan semester yang ingin dilihat : ");
                semesterLain = input.next();
                cetakJadwalMHS(mhs, semesterLain);
            }
            else if (pilihan == 5) {
                break;
            }
        }
    }

    public static void login() {
        String NIMNIDN;
        String nama;

        System.out.println("\n===== LOGIN =====");
        System.out.print("Masukkan NIM/NIDN : ");
        NIMNIDN = input.next();
        input.nextLine();

        System.out.print("Masukkan nama : ");
        nama = input.nextLine();

        String empatDigitAwal = "";
        if (NIMNIDN.length() >= 4) {
            empatDigitAwal = NIMNIDN.substring(0,4);
        }

        if (empatDigitAwal.equals("2023") || 
            empatDigitAwal.equals("2024") || 
            empatDigitAwal.equals("2025") || 
            empatDigitAwal.equals("2026")) {

            int semester;
            System.out.print("Masukkan semester saat ini : ");
            semester = input.nextInt();

            Mahasiswa mhs = new Mahasiswa(NIMNIDN, nama, semester);
            User userAktifMHS = mhs;
            System.out.println("Login berhasil untuk user : " + userAktifMHS.getNama());
            menuDashboardMHS(mhs);
        }
        else if (empatDigitAwal.equals("2000")) {
            String inisial = "";

            if (NIMNIDN.endsWith("10")) {
                inisial = "Budi Santoso, S.Kom.";
            }
            else if (NIMNIDN.endsWith("20")) {
                inisial = "Andi Wijaya, M.Kom.";
            }
            else if (NIMNIDN.endsWith("30")) {
                inisial = "Rina Permata, M.Kom.";
            }
            else if (NIMNIDN.endsWith("40")) {
                inisial = "Yohanes Setiawan, M.Pd.";
            }
            else if (NIMNIDN.endsWith("50")) {
                inisial = "Siti Rahma, M.Hum.";
            }
            else if (NIMNIDN.endsWith("60")) {
                inisial = "Dedi Kurniawan, M.Kom.";
            }
            else if (NIMNIDN.endsWith("70")) {
                inisial = "Agus Pratama, M.Kom.";
            }
            else if (NIMNIDN.endsWith("80")) {
                inisial = "Maria Lestari, M.Pd.";
            }

            Dosen dsn = new Dosen(NIMNIDN, nama, inisial);
            User userAktifDSN = dsn;
            System.out.println("Login berhasil untuk user : " + userAktifDSN.getNama());
            menuDashboardDSN(dsn);
        }
    }

    public static void menu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Login");
        System.out.println("2. Exit");
    }

    public static void main(String[] args) {
        while (true) {
            menu();

            int pilihan;
            System.out.print("Masukkan pilihan : ");
            pilihan = input.nextInt();

            if (pilihan == 1) {
                login();
            }
            else if (pilihan == 2) {
                System.out.println("Thankyou");
                System.exit(0);
            }
        }
    }
}
