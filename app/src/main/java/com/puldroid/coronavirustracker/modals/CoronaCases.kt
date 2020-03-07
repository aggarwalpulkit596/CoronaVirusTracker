package com.puldroid.coronavirustracker.modals

import com.google.gson.annotations.SerializedName

data class CoronaCases(
    val features: List<FeaturesItem>? = null
)

data class FeaturesItem(
    val attributes: Attributes? = null
)

data class Attributes(

    @field:SerializedName("OBJECTID")
    val oBJECTID: Int? = null,

    @field:SerializedName("Long_")
    val long: Double? = null,

    @field:SerializedName("Recovered")
    val recovered: Int? = null,

    @field:SerializedName("Country_Region")
    val countryRegion: String? = null,

    @field:SerializedName("Last_Update")
    val lastUpdate: Long? = null,

    @field:SerializedName("Deaths")
    val deaths: Int? = null,

    @field:SerializedName("Confirmed")
    val confirmed: Int? = null,

    @field:SerializedName("Province_State")
    val provinceState: Any? = null,

    @field:SerializedName("Lat")
    val lat: Double? = null
)
