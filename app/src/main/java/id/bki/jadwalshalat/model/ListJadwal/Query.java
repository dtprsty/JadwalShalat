
package id.bki.jadwalshalat.model.ListJadwal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}
