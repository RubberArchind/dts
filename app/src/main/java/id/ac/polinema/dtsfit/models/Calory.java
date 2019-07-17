package id.ac.polinema.dtsfit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// TODO: Implementasikan parcelable
public class Calory implements Parcelable {

    // TODO: Generate model berdasarkan response REST
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("food")
    @Expose
    private String food;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Integer getCalory() {
        return calory;
    }

    public void setCalory(Integer calory) {
        this.calory = calory;
    }

    @SerializedName("calory")
    @Expose
    private Integer calory;


    @Override
    public int describeContents() {
        return 0;
    }

    public Calory() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.food);
        dest.writeValue(this.calory);
    }

    protected Calory(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.food = in.readString();
        this.calory = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Calory> CREATOR = new Creator<Calory>() {
        @Override
        public Calory createFromParcel(Parcel source) {
            return new Calory(source);
        }

        @Override
        public Calory[] newArray(int size) {
            return new Calory[size];
        }
    };
}
