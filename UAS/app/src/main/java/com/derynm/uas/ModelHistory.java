package com.derynm.uas;

public class ModelHistory {
    String id_pembayaran,username,waktu,total_pembayaran,total_paket,status;

    public ModelHistory (String id_pembayaran,String username,String waktu,String total_pembayaran,String total_paket,String status){
        this.id_pembayaran = id_pembayaran;
        this.username = username;
        this.waktu = waktu;
        this.total_pembayaran = total_pembayaran;
        this.total_paket = total_paket;
        this.status = status;
    }

    public String getId_pembayaran() {
        return id_pembayaran;
    }

    public String getUsername() {
        return username;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getTotal_pembayaran() {
        return total_pembayaran;
    }

    public String getTotal_paket() {
        return total_paket;
    }

    public String getStatus() {
        return status;
    }
}
