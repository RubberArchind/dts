package id.primadev.recyclerview.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Heroes {
   @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String desc;

    public Heroes(String name, String desc, String imgURL) {
        this.name = name;
        this.desc = desc;
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @SerializedName("imgURL")
    private String imgURL;

}
