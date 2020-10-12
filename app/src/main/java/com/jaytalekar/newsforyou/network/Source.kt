package com.jaytalekar.newsforyou.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source (
    @SerializedName("id")
    val id : String?, //can be null

    @SerializedName("name")
    val name : String
): Parcelable