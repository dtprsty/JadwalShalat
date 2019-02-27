package id.bki.jadwalshalat.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rumahsinggah on 18/02/18.
 */

public class TimeUtil {

    @SuppressLint("SimpleDateFormat")
    public String hariIni(){
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public String jam(){
        return new SimpleDateFormat("hh:mm").format(Calendar.getInstance().getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public String timeStamp(){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
    }

    public String hari(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date); //2016/11/16 12:08:43
    }

    public String bulan(){
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date); //11
    }

    public String namaBulan(){
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        switch (dateFormat.format(date)){
            case "01":
                return "Januari";
            case "02":
                return "Februari";
            case "03":
                return "Maret";
            case "04":
                return "April";
            case "05":
                return "Mei";
            case "06":
                return "Juni";
            case "07":
                return "Juli";
            case "08":
                return "Agustus";
            case "09":
                return "September";
            case "10":
                return "Oktober";
            case "11":
                return "November";
            case "12":
                return "Desember";
            default:
                return "";
        }
    }

    public String tahun(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date); //2016
    }

    public String tanggal(){
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        return dateFormat.format(date); //2016/11/16 12:08:43
    }

    public String tanggal(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date); //2016/11/16 12:08:43
    }
}
