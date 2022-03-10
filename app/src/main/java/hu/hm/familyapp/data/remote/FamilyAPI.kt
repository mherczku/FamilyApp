package hu.hm.familyapp.data.remote

import hu.hm.familyapp.data.model.Family
import retrofit2.http.GET


interface FamilyAPI {

    @GET("")
    suspend fun getFamily(): Family

}