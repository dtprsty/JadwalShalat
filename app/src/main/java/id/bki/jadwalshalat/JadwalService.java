package id.bki.jadwalshalat;

import id.bki.jadwalshalat.model.Konstanta;
import id.bki.jadwalshalat.model.ListJadwal.ListJadwal;
import id.bki.jadwalshalat.model.ListKota.ListKota;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class JadwalService {
    public interface JadwalApi{

//        https://api.banghasan.com/sholat/format/json/kota/nama/a
//        https://api.banghasan.com/sholat/format/json/jadwal/kota/703/tanggal/2017-02-07

        @POST("kota/nama/{namaKota}")
        Call<ListKota> getKode(@Path("namaKota") String namaKota);

        @POST("jadwal/kota/{kodeKota}/tanggal/{tanggal}")
        Call<ListJadwal> getJadwal(@Path("kodeKota") String  kodeKota,
                                   @Path("tanggal") String tanggal);
    }

    public JadwalApi getApi(){
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(Konstanta.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(JadwalApi.class);
    }
}
