package com.example.gw_ajo_b2b.networking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface GraphQLService {
    @POST("api/gql/app/sapphireBuyingGroups/graphql")
    fun postGraphQLRequest(
        @Query("appId") appId: String,
        @Header("Authorization") token: String,
        @Header("x-api-key") apiKey: String,
        @Header("x-gw-ims-org-id") orgId: String,
        @Header("content-type") contentType: String,
        @Body requestBody: String
    ): Call<String>
}
