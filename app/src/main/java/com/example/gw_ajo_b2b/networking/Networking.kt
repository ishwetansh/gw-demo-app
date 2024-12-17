package com.example.gw_ajo_b2b.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitClient {

    private const val BASE_URL = "https://exc-unifiedcontent.experience-stage.adobe.net/"

    val instance: Retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()
    }
}

interface ApiCallback {
    fun onSuccess(response: String)
    fun onError(error: String)
}

fun makeGraphQLRequest(
    requestBody: String,
    token: String,
    apiCallback: ApiCallback
) {
    val retrofit = RetrofitClient.instance
    val service = retrofit.create(GraphQLService::class.java)

    val call = service.postGraphQLRequest(
        appId = "sapphireBuyingGroups",
        token = "Bearer $token",
        apiKey = "exc_app",
        orgId = "22DE1AF9643D97B30A494133@AdobeOrg",
        contentType = "application/json",
        requestBody = requestBody
    )

    call.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                apiCallback.onSuccess(response.body().toString())
            } else {
                apiCallback.onError("Error: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            apiCallback.onError("Failure: ${t.message}")
        }
    })
}


fun makeAPICall() {

    val queryPayload = """
    {
      "operationName": "getAllBuyingGroupsV2",
      "variables": {
        "filter": "",
        "maxReturn": 50,
        "next": "",
        "order": "desc",
        "search": "",
        "sortBy": "id"
      },
      "query": "query getAllBuyingGroupsV2(${'$'}search: String, ${'$'}sortBy: String, ${'$'}order: String, ${'$'}filter: String, ${'$'}next: String, ${'$'}maxReturn: Int) {\n  buyingGroupsV2(\n    search: ${'$'}search\n    sortBy: ${'$'}sortBy\n    order: ${'$'}order\n    filter: ${'$'}filter\n    next: ${'$'}next\n    maxReturn: ${'$'}maxReturn\n  ) {\n    result {\n      id\n      name\n      status\n      buyingGroupTemplate {\n        id\n        name\n        __typename\n      }\n      account {\n        id\n        name\n        __typename\n      }\n      solutionInterest {\n        id\n        name\n        __typename\n      }\n      completenessScore\n      engagementScore\n      createdAt\n      updatedAt\n      __typename\n    }\n    totalItems\n    next\n    errors {\n      code\n      message\n      __typename\n    }\n    __typename\n  }\n}",
      "extensions": {
        "accessToken": "821834e5-62ef-4eb8-a1a9-37e0e1ed6248:st:842-STS-238",
        "dataCenter": "st",
        "munchkinId": "842-STS-238",
        "clientContext": {
          "applicationId": "sapphireBuyingGroups:stage20241206180737",
          "deviceId": "8a3f9603-bc60-11ef-991d-b9c4e0735b3e",
          "groupId": "22DE1AF9643D97B30A494133@AdobeOrg",
          "historyId": "9b6f2710-bc60-11ef-991d-b9c4e0735b3e",
          "instanceId": "8a3f9602-bc60-11ef-991d-b9c4e0735b3e",
          "sessionId": "https://ims-na1-stg1.adobelogin.com/ims/session/v1/ZWE2ZWMwODMtZGEyNC00Nzc1LWI0ZTMtZThkNTQzZjA3OTEzLS0wNDNFMTk5QzY2QjJDOEU2MEE0OTQwMUJAMjk3YTA2Y2E1ZTk3NmU4ZDBhNDk0MjA0",
          "windowId": "9b6f2711-bc60-11ef-991d-b9c4e0735b3e"
        }
      },
      "sandboxName": "prod",
      "sandboxType": "production",
      "isDefaultSandbox": "true"
    }
""".trimIndent()

    val token = "Bearer eyJhbGciOiJSUzI1NiIsIng1dSI6Imltc19uYTEtc3RnMS1rZXktYXQtMS5jZXIiLCJraWQiOiJpbXNfbmExLXN0ZzEta2V5LWF0LTEiLCJpdHQiOiJhdCJ9.eyJpZCI6IjE3MzQ0MzA4MzA1MzNfYjRiY2UzMjYtMjRlNC00OGE5LTlmNGItYzc4MmI0ODcwNWE2X3V3MiIsInR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJjbGllbnRfaWQiOiJleGNfYXBwIiwidXNlcl9pZCI6IjE2MjYxOTgwNjc1MzUwNEEwQTQ5NDIxNUAzZDUzMWFkYTY0M2RiZmIwNDk0MTI4LmUiLCJzdGF0ZSI6IntcInNlc3Npb25cIjpcImh0dHBzOi8vaW1zLW5hMS1zdGcxLmFkb2JlbG9naW4uY29tL2ltcy9zZXNzaW9uL3YxL1pXRTJaV013T0RNdFpHRXlOQzAwTnpjMUxXSTBaVE10WlRoa05UUXpaakEzT1RFekxTMHdORE5GTVRrNVF6WTJRakpET0VVMk1FRTBPVFF3TVVKQU1qazNZVEEyWTJFMVpUazNObVU0WkRCaE5EazBNakEwXCJ9IiwiYXMiOiJpbXMtbmExLXN0ZzEiLCJhYV9pZCI6IjI0MTAxOTc2NjY0QjE5OUYwQTQ5NDAzMUBjNjJmMjRjYzViNWI3ZTBlMGE0OTQwMDQiLCJjdHAiOjAsImZnIjoiWkJLWVo2UjQ3WjJYQjREWjNHWk1BMklBVTQ9PT09PT0iLCJzaWQiOiIxNzM0NDMwNjk2NzUzX2JiYzI4ZmVhLTdjMjEtNGExMS05NjNkLTc5NTc5ZGEyMTgxMl91dzIiLCJtb2kiOiJmMWZjNGYiLCJwYmEiOiJPUkcsTWVkU2VjTm9FVixMb3dTZWMiLCJleHBpcmVzX2luIjoiMjg4MDAwMDAiLCJzY29wZSI6ImFiLm1hbmFnZSxhY2NvdW50X2NsdXN0ZXIucmVhZCxhZGRpdGlvbmFsX2luZm8sYWRkaXRpb25hbF9pbmZvLmpvYl9mdW5jdGlvbixhZGRpdGlvbmFsX2luZm8ucHJvamVjdGVkUHJvZHVjdENvbnRleHQsYWRkaXRpb25hbF9pbmZvLnJvbGVzLEFkb2JlSUQsYWRvYmVpby5hcHByZWdpc3RyeS5yZWFkLGFkb2JlaW9fYXBpLGF1ZGllbmNlbWFuYWdlcl9hcGksY3JlYXRpdmVfY2xvdWQsbXBzLG9wZW5pZCxvcmcucmVhZCxwcHMucmVhZCxyZWFkX29yZ2FuaXphdGlvbnMscmVhZF9wYyxyZWFkX3BjLmFjcCxyZWFkX3BjLmRtYV90YXJ0YW4sc2VydmljZV9wcmluY2lwYWxzLndyaXRlLHNlc3Npb24iLCJjcmVhdGVkX2F0IjoiMTczNDQzMDgzMDUzMyJ9.WoBOdeffWy5eJPcCMS7TBYw0hO-N7PgXq1HtOlddMgXT8JJ2DxAi_WMZlUpLW12x22caAOGwEKBosuy_ETFHAem3YnUskLLFo6p4lg9qy6WfG7-sqiIAdo5SrCV89HHZUR3qPQIQj8X04VVqUSjeTJKywRrsW3BLJjIakBrTsranYZ8Dh2bBXpHLxmM77a8YRLr9wwAHJ3oqDSiJgG9jNMT2ekMx6BRiUKENg8sXsZLwk0ESEaEMSTIJve6VqWAGMLELikkEfUSEI1gXeO82SCA44GExcNxhBhj0VNQNWzkR6CUKcu-XA7boebPlTwcoEoCeoo5A6kZ7yF4mJTjIHA"

    makeGraphQLRequest(queryPayload, token, object : ApiCallback {
        override fun onSuccess(response: String) {
            println("Success: $response")
        }

        override fun onError(error: String) {
            println("Error: $error")
        }
    })
}



