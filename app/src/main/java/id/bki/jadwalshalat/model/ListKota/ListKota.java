package id.bki.jadwalshalat.model.ListKota;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ListKota {
    @Expose
    private String status;
    @Expose
    private Query query;
    @Expose
    private List<Kota> kota = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<Kota> getKota() {
        return kota;
    }

    public void setKota(List<Kota> kota) {
        this.kota = kota;
    }

}
