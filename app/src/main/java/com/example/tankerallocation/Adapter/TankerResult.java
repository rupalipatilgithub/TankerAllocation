package com.example.tankerallocation.Adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TankerResult {

    @SerializedName("tanker_no")
    @Expose
    private String tankerno;

    public void settankerno(String tankerno) {
        this.tankerno = tankerno;
    }

    public String getTankerno() {
        return tankerno;
    }

}
