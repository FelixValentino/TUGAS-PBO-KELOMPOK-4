public class Mahasiswa extends User {
    private String NIM;
    private int semester;

    public Mahasiswa(String NIM, String nama, int semester) {
        super(nama);
        this.NIM = NIM;
        setSemester(semester);
    }

    public String getNIM() {
        return NIM;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getTahun() {
        return NIM.substring(0, 4);
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("NIM      : " + NIM);
        System.out.println("Nama     : " + getNama());
        System.out.println("Semester : " + semester);
        System.out.println("Tahun    : " + getTahun());
    }
}
