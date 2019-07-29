package id.primadev.recyclerview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeroesToPopulate {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("desc")
    @Expose
    private String desc;

    public HeroesToPopulate() {
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

