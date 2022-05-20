package com.example.tankerallocation.Adapter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TankerList {

    @SerializedName("TankerDetails")
    private List<TankerResult> TankerDetails;

    public List<TankerResult> getTankerno() {
        return TankerDetails;
    }
}
