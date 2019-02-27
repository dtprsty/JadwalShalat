package id.bki.jadwalshalat.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import id.bki.jadwalshalat.model.ListJadwal.ListJadwal;

public class SharedPref {
    Context context;
    SharedPreferences sharedPreferences;

    public SharedPref(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("JADWAL", Context.MODE_PRIVATE);
    }

    /*public <Pesanan> void setList(List<Pesanan> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(json);
    }

    public void set(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LIST", value);
        editor.commit();
    }*/

    /*public void setList(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER", json);
        editor.commit();
    }

    public User getList(){
        Gson gson = new Gson();
        List<User> list = new ArrayList<>();
        String jsonPref = sharedPreferences.getString("USER", "");

        Type type = new TypeToken<List<User>>() {}.getType();
        User p = gson.fromJson(jsonPref, User.class);
        return p;
    }
    */

    public void setList(ListJadwal koin) {
        Gson gson = new Gson();
        String json = gson.toJson(koin);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LIST", json);
        editor.commit();
    }

    public ListJadwal getList(){
        Gson gson = new Gson();
        List<ListJadwal> list = new ArrayList<>();
        String jsonPref = sharedPreferences.getString("LIST", "");

        Type type = new TypeToken<List<ListJadwal>>() {}.getType();
        ListJadwal p = gson.fromJson(jsonPref, ListJadwal.class);
        return p;
    }

    public void setNamaKota(String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("NAMA", id);
        editor.commit();
    }

    public String getNamaKota(){
        return sharedPreferences.getString("NAMA", "");
    }

    public void setIdKota(String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ID", id);
        editor.commit();
    }

    public String getIdKota(){
        return sharedPreferences.getString("ID", "");
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
