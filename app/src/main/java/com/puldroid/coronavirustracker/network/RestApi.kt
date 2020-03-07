package com.puldroid.coronavirustracker.network

import com.puldroid.coronavirustracker.modals.CoronaCases
import com.puldroid.coronavirustracker.modals.CoronaCount
import retrofit2.Response
import retrofit2.http.GET

interface RestApi {


    @GET("query?f=json&where=Recovered%20%3E%200&returnGeometry=false&spatialRef=esriSpatialRelIntersects&outFields=*&outStatistics=%5B%7B%22statisticType%22:%22sum%22,%22onStatisticField%22:%22Recovered%22,%22outStatisticFieldName%22:%22value%22%7D%5D&cacheHint=true")
    suspend fun getRecovered(): Response<CoronaCount>

    @GET("query?f=json&where=Confirmed%20%3E%200&returnGeometry=false&spatialRef=esriSpatialRelIntersects&outFields=*&outStatistics=%5B%7B%22statisticType%22:%22sum%22,%22onStatisticField%22:%22Deaths%22,%22outStatisticFieldName%22:%22value%22%7D%5D&cacheHint=true")
    suspend fun getDeaths(): Response<CoronaCount>

    @GET("query?f=json&where=Confirmed%20%3E%200&returnGeometry=false&spatialRef=esriSpatialRelIntersects&outFields=*&outStatistics=%5B%7B%22statisticType%22:%22sum%22,%22onStatisticField%22:%22Confirmed%22,%22outStatisticFieldName%22:%22value%22%7D%5D&cacheHint=true")
    suspend fun getConfirmed(): Response<CoronaCount>

    @GET("query?f=json&where=(Confirmed%3E%200)%20OR%20(Deaths%3E0)%20OR%20(Recovered%3E0)&returnGeometry=false&spatialRef=esriSpatialRelIntersects&outFields=*&orderByFields=Country_Region%20asc,Province_State%20asc&resultOffset=0&resultRecordCount=250&cacheHint=true")
    suspend fun getCases(): Response<CoronaCases>
}
