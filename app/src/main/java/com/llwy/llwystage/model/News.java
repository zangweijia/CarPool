package com.llwy.llwystage.model;

/**
 * Created by ZWJ on 2018/3/9.
 */

public class News {


    /**
     * CId : 102
     * CName : 母婴玩具
     * CCode : 003
     * PCode : 0
     * Thumbnail :
     * Picture : http://images.bopinjia.com/ProductCategory/20161108/20161108113606_8930.png
     */

    private String CId;
    private String CName;
    private String CCode;
    private String PCode;
    private String Thumbnail;
    private String Picture;

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getCCode() {
        return CCode;
    }

    public void setCCode(String CCode) {
        this.CCode = CCode;
    }

    public String getPCode() {
        return PCode;
    }

    public void setPCode(String PCode) {
        this.PCode = PCode;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String Thumbnail) {
        this.Thumbnail = Thumbnail;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String Picture) {
        this.Picture = Picture;
    }
}
