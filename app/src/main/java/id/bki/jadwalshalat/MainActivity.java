package id.bki.jadwalshalat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

import id.bki.jadwalshalat.model.ListJadwal.ListJadwal;
import id.bki.jadwalshalat.model.ListKota.ListKota;
import id.bki.jadwalshalat.model.SharedPref;
import id.bki.jadwalshalat.util.TimeUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private static final String TAG = "MAPLOCATION";
    private LatLng user;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    List<Address> addresses;
    Geocoder geocoder;
    String address = ""; // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    String city;
    String state;
    String country;
    String postalCode;
    String knownName;
    private SharedPref sharedPref;
    private ListJadwal listJadwal;
    private JadwalService service;
    private TimeUtil timeUtil;
    private String hariIni;
    private ProgressBar progressBar;
    private TextView txtTanggal, txtImsak, txtSubuh, txtTerbit,
            txtDhuha, txtDzuhur, txtAshar, txtMagrib, txtIsya, txtKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = new SharedPref(this);
        listJadwal = sharedPref.getList();
        service = new JadwalService();
        timeUtil = new TimeUtil();
        hariIni = timeUtil.hariIni();

        txtTanggal = findViewById(R.id.txt_tanggal);
        txtImsak = findViewById(R.id.txt_imsak);
        txtSubuh = findViewById(R.id.txt_subuh);
        txtTerbit = findViewById(R.id.txt_terbit);
        txtDhuha = findViewById(R.id.txt_dhuha);
        txtDzuhur = findViewById(R.id.txt_dzuhur);
        txtAshar = findViewById(R.id.txt_ashar);
        txtMagrib = findViewById(R.id.txt_magrib);
        txtIsya = findViewById(R.id.txt_isya);
        txtKota = findViewById(R.id.txt_kota);
        progressBar = findViewById(R.id.progressBar);

//        checkLocationPermission();

        if (listJadwal != null && !listJadwal.equals("")){
            tampilJadwal(listJadwal);
        }else {
            checkLocationPermission();
        }

    }

    public void tampilJadwal(ListJadwal listJadwal){
        progressBar.setVisibility(View.GONE);
        txtTanggal.setText(listJadwal.getJadwal().getData().getTanggal());
        txtImsak.setText(listJadwal.getJadwal().getData().getImsak());
        txtSubuh.setText(listJadwal.getJadwal().getData().getSubuh());
        txtTerbit.setText(listJadwal.getJadwal().getData().getTerbit());
        txtDhuha.setText(listJadwal.getJadwal().getData().getDhuha());
        txtDzuhur.setText(listJadwal.getJadwal().getData().getDzuhur());
        txtAshar.setText(listJadwal.getJadwal().getData().getAshar());
        txtMagrib.setText(listJadwal.getJadwal().getData().getMaghrib());
        txtIsya.setText(listJadwal.getJadwal().getData().getIsya());
        txtKota.setText(sharedPref.getNamaKota());
        checkLocationPermission();
    }

    public void ambilJadwal(final String kode){
        service.getApi().getJadwal(kode, hariIni).enqueue(new Callback<ListJadwal>() {
            @Override
            public void onResponse(Call<ListJadwal> call, Response<ListJadwal> response) {
                if (response.body().getStatus().equals("ok")){
                    listJadwal = response.body();
                    sharedPref.setList(listJadwal);
                    sharedPref.setIdKota(kode);

                    tampilJadwal(listJadwal);
                }else {
                    Toast.makeText(MainActivity.this, "Terjadi Kesalahan Mengambil Jadwal : "+kode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListJadwal> call, Throwable t) {

            }
        });
    }

    public void ambilKodeKota(final String namaKota){
        service.getApi().getKode(namaKota).enqueue(new Callback<ListKota>() {
            @Override
            public void onResponse(Call<ListKota> call, Response<ListKota> response) {
                Log.e("Nama Kota", "kota :"+address);
                Log.e("Nama Kota", "address :"+addresses.get(0).getSubAdminArea());
                Log.e("Nama Kota", "address22 :"+city);

                if (response.body().getStatus().equals("ok")){
                    if (response.body().getKota().size() > 0){
                        ambilJadwal(response.body().getKota().get(0).getId());
                        sharedPref.setNamaKota(namaKota);
                    }else {
                        if (!sharedPref.getNamaKota().equals("")){
                            Toast.makeText(MainActivity.this, "Gagal Mendapatkan Jadwal Kota Anda Sekarang." +
                                    "Kembali Ke Jadwal Sebelumnya.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Gagal Mendapatkan Jadwal Kota Anda." +
                                    "\n Mengambil Jadwal Jakarta", Toast.LENGTH_SHORT).show();
                            ambilKodeKota("jakarta");
                        }
                    }

                }else {
                    Toast.makeText(MainActivity.this, "Kesalahan Mengambil Kode Kota : "+namaKota, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListKota> call, Throwable t) {

            }
        });
    }

    public void ambilNamaKota(){

        if (user != null){
            try {
                Locale localeID = new Locale("in", "ID");

                geocoder = new Geocoder(this, localeID);
                addresses = geocoder.getFromLocation(user.latitude, user.longitude, 1);
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                postalCode = addresses.get(0).getPostalCode();
                knownName = addresses.get(0).getFeatureName();

                ambilKodeKota(city);
            }catch (Exception ie){
                Log.e("Error", "Terjadi Kesalahan : "+addresses+"\n"+user.toString()+"\n"+ie.getMessage());
                Toast.makeText(this, "Terjadi Kesalahan Mendapat Nama ListKota !"+city, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkIzinLokasi(){
        if (mLocationPermissionsGranted) {
            getDeviceLocation();


        }else {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            checkLocationPermission();
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            user = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            /*moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");*/
                            ambilNamaKota();

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(getApplicationContext(), "Gagal Mendapatkan Lokasi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                /*new android.app.AlertDialog.Builder(getApplicationContext())
                        .setTitle("Atur Akses Lokasi")
                        .setMessage("OK untuk atur lokasi")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
*/

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            mLocationPermissionsGranted = true;
            checkIzinLokasi();
        }
    }
}
