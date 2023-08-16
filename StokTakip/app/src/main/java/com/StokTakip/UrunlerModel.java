package com.StokTakip;

public class UrunlerModel {

    int urunId;
    String urunAdi;
    int urunUcreti;

    public UrunlerModel() {
    }

    public UrunlerModel(int urunId, String urunAdi, int urunUcreti) {
        this.urunId = urunId;
        this.urunAdi = urunAdi;
        this.urunUcreti = urunUcreti;
    }

    public int getUrunId() {
        return urunId;
    }

    public void setUrunId(int urunId) {
        this.urunId = urunId;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public int getUrunUcreti() {
        return urunUcreti;
    }

    public void setUrunUcreti(int urunUcreti) {
        this.urunUcreti = urunUcreti;
    }
}
