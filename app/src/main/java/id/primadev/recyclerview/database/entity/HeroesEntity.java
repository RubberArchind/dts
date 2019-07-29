package id.primadev.recyclerview.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "heroes_table")
public class HeroesEntity implements Serializable {
    @PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo(name = "name")
    private String heroesName;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name = "imgURL")
    private String imgUrl;

    @ColumnInfo(name = "side")
    private String side;

    @ColumnInfo(name = "favorite")
    @NonNull
    private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeroesName() {
        return heroesName;
    }

    public void setHeroesName(String heroesName) {
        this.heroesName = heroesName;
    }

    public HeroesEntity(int id,String heroesName, String desc, String imgUrl, String side,boolean favorite) {
        this.id = id;
        this.heroesName = heroesName;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.side = side;
        this.favorite = favorite;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static HeroesEntity[] populateData(HeroesEntity data){
        return new HeroesEntity[]{data};
    }
}
