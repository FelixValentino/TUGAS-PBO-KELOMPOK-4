public class Dosen extends User {
    private String NIDN;
    private String inisialMengajar;

    public Dosen(String NIDN, String nama, String inisialMengajar) {
        super(nama);
        this.NIDN = NIDN;
        this.inisialMengajar = inisialMengajar;
    }

    public String getNIDN() {
        return NIDN;
    }

    public String getInisialMengajar() {
        return inisialMengajar;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("NIDN                : " + NIDN);
        System.out.println("Nama                : " + getNama());
    }
}
