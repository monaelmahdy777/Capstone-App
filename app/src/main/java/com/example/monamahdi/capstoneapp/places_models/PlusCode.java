package com.example.monamahdi.capstoneapp.places_models;

/**
 * Created by mona.mahdi on 8/18/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlusCode {

    @SerializedName("compound_code")
    @Expose
    private String compoundCode;
    @SerializedName("global_code")
    @Expose
    private String globalCode;

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

}