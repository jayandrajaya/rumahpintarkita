package com.jayandra.rumahpintar;

public class DataTransaksi {
    String kodePeminjam, kodeBuku,lama,jumlah;

    public String getKodePeminjam() {
        return kodePeminjam;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public String getLama() {
        return lama;
    }

    public String getJumlah() {
        return jumlah;
    }


    public DataTransaksi(String kodePeminjam, String kodeBuku, String lama, String jumlah) {
        this.kodePeminjam = kodePeminjam;
        this.kodeBuku = kodeBuku;
        this.lama = lama;
        this.jumlah = jumlah;
    }
}
