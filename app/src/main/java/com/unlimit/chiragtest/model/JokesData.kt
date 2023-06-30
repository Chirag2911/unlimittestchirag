package com.unlimit.chiragtest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize



@Parcelize
data class JokesData ( @SerializedName("joke") var joke: String?) : Parcelable