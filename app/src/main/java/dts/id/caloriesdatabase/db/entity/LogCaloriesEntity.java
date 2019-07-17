package dts.id.caloriesdatabase.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Todo 1 Melengkapi entity

@Entity(tableName = "log_calories")
public class LogCaloriesEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name="waktu_makan")
    private String waktuMakan;

    @ColumnInfo(name="makan")
    private String makanan;

    @ColumnInfo(name="kalori_makanan")
    private String kaloriMakanan;

    @ColumnInfo(name="tanggal")
    private String tanggal;

    public LogCaloriesEntity(String waktuMakan, String makanan, String kaloriMakanan, String tanggal) {
        this.waktuMakan = waktuMakan;
        this.makanan = makanan;
        this.kaloriMakanan = kaloriMakanan;
        this.tanggal = tanggal;
    }

    public String getWaktuMakan() {
        return waktuMakan;
    }

    public void setWaktuMakan(String waktuMakan) {
        this.waktuMakan = waktuMakan;
    }

    public String getMakanan() {
        return makanan;
    }

    public void setMakanan(String makanan) {
        this.makanan = makanan;
    }

    public String getKaloriMakanan() {
        return kaloriMakanan;
    }

    public void setKaloriMakanan(String kaloriMakanan) {
        this.kaloriMakanan = kaloriMakanan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
