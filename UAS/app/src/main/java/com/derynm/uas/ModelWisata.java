package com.derynm.uas;

public class ModelWisata {
    String nama,harga,gambar,deskripsi;

    public ModelWisata(String nama, String harga, String gambar, String deskripsi){
        this.nama = nama;
        this.harga = harga;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
    }

    public String getNama() {
        return nama;
    }

    public String getHarga() {
        return harga;
    }

    public String getGambar() {
//        return "http://10.0.2.2/wisata_tempelmahbang/img/"+gambar;
        return "https://derynm.000webhostapp.com/img/"+gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}
