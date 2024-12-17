package com.example.gw_ajo_b2b.networking

import com.example.gw_ajo_b2b.Models.AccountDetailsRepository
import com.example.gw_ajo_b2b.Models.AccountDetailsResponse
import com.example.gw_ajo_b2b.Models.BuyingGroupDetailsRepository
import com.example.gw_ajo_b2b.Models.BuyingGroupRepository
import com.example.gw_ajo_b2b.Models.BuyingGroupResponse
import com.example.gw_ajo_b2b.Models.BuyingGroupsResponse
import com.google.gson.Gson
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

val token = "Bearer eyJhbGciOiJSUzI1NiIsIng1dSI6Imltc19uYTEtc3RnMS1rZXktYXQtMS5jZXIiLCJraWQiOiJpbXNfbmExLXN0ZzEta2V5LWF0LTEiLCJpdHQiOiJhdCJ9.eyJpZCI6IjE3MzQ0NDcyMDcyNTJfZGQ2MWNkZGEtYjg5Mi00MGFmLWJjNTItNjRjN2UwNjJjNDU0X3V3MiIsInR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJjbGllbnRfaWQiOiJleGNfYXBwIiwidXNlcl9pZCI6IjE2MjYxOTgwNjc1MzUwNEEwQTQ5NDIxNUAzZDUzMWFkYTY0M2RiZmIwNDk0MTI4LmUiLCJzdGF0ZSI6IntcImpzbGlidmVyXCI6XCJ2Mi12MC4zMS4wLTItZzFlOGE4YThcIixcIm5vbmNlXCI6XCIxNDIzMDk3ODY5MzMyMzE4XCIsXCJzZXNzaW9uXCI6XCJodHRwczovL2ltcy1uYTEtc3RnMS5hZG9iZWxvZ2luLmNvbS9pbXMvc2Vzc2lvbi92MS9aV0UyWldNd09ETXRaR0V5TkMwME56YzFMV0kwWlRNdFpUaGtOVFF6WmpBM09URXpMUzB3TkRORk1UazVRelkyUWpKRE9FVTJNRUUwT1RRd01VSkFNamszWVRBMlkyRTFaVGszTm1VNFpEQmhORGswTWpBMFwifSIsImFzIjoiaW1zLW5hMS1zdGcxIiwiYWFfaWQiOiIyNDEwMTk3NjY2NEIxOTlGMEE0OTQwMzFAYzYyZjI0Y2M1YjViN2UwZTBhNDk0MDA0IiwiY3RwIjowLCJmZyI6IlpCTElQNlI0M1oyVkI0RFpaR1pNQVNJQU9ZIiwic2lkIjoiMTczNDQ0NjA5OTIyOF9iMDg3NWQ1ZS02N2JlLTQyMGEtYmU1Yy1kMGMzZTc4Nzk2NjZfdXcyIiwibW9pIjoiM2ZjNDI0MGMiLCJwYmEiOiJPUkcsTWVkU2VjTm9FVixMb3dTZWMiLCJleHBpcmVzX2luIjoiMjg4MDAwMDAiLCJjcmVhdGVkX2F0IjoiMTczNDQ0NzIwNzI1MiIsInNjb3BlIjoiYWIubWFuYWdlLGFjY291bnRfY2x1c3Rlci5yZWFkLGFkZGl0aW9uYWxfaW5mbyxhZGRpdGlvbmFsX2luZm8uam9iX2Z1bmN0aW9uLGFkZGl0aW9uYWxfaW5mby5wcm9qZWN0ZWRQcm9kdWN0Q29udGV4dCxhZGRpdGlvbmFsX2luZm8ucm9sZXMsQWRvYmVJRCxhZG9iZWlvLmFwcHJlZ2lzdHJ5LnJlYWQsYWRvYmVpb19hcGksYXVkaWVuY2VtYW5hZ2VyX2FwaSxjcmVhdGl2ZV9jbG91ZCxtcHMsb3BlbmlkLG9yZy5yZWFkLHBwcy5yZWFkLHJlYWRfb3JnYW5pemF0aW9ucyxyZWFkX3BjLHJlYWRfcGMuYWNwLHJlYWRfcGMuZG1hX3RhcnRhbixzZXJ2aWNlX3ByaW5jaXBhbHMud3JpdGUsc2Vzc2lvbiJ9.ao-LFH4FAD4lxXq8YTezIsO9fF4SbCEFWMemOaFXJ4rzmYZsMcbLW7TrjPmtuvXX8K8kag-hlaf0BjrCtU2wur7EG-fd1wRZDcLEk2h7jYkwjEO-HBYlnphvZe6H-UDNpxHMez9Vz2bAb0lzII1-dD6hS0J04uT0sNGywPSIUk_70biQ_dWiqwxuX2BRSPyM-vVQAaVpHstgsezzPrKTS0E6XN_VTvkN3gu3u-I1-qbIDBf3cbyP0lNVatrZq6boeGfTWgA-IFjIO4fy_39aqFFW9LueOXbj9ln7mzNCAzUlOZwGJRSmaEuz2TidVk6yoCKdWtjj1cEzV_MOHLO2Sg"

fun fetchBuyingGroups() {

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

    makeGraphQLRequest(queryPayload, token, object : ApiCallback {
        override fun onSuccess(response: String) {
            try {
                val gson = Gson()
                val parsedResponse = gson.fromJson(response, BuyingGroupsResponse::class.java)
                BuyingGroupRepository.saveBuyingGroups(parsedResponse.data?.buyingGroupsV2?.result)
                println("Response parsed and saved successfully!")
                println("Saved Data: ${BuyingGroupRepository.getBuyingGroups()}")
            } catch (e: Exception) {
                println("Parsing Error: ${e.message}")
            }
            println("Success: $response")
        }

        override fun onError(error: String) {
            println("Error: $error")
        }
    })
}


fun fetchAccountDetail() {

    val queryPayload = """
    {
      "operationName": "getAccountDetailsByAccountId",
      "variables": {
        "id": "830559"
      },
      "query": "query getAccountDetailsByAccountId(${'$'}id: String!) {\n  accountDetailsByIdV2(id: ${'$'}id) {\n    requestId\n    success\n    result {\n      id\n      name\n      industry\n      memberCount\n      opportunityCount\n      buyingGroupCount\n      journeys {\n        id\n        name\n        __typename\n      }\n      createdAt\n      updatedAt\n      __typename\n    }\n    errors {\n      code\n      message\n      __typename\n    }\n    __typename\n  }\n}",
      "extensions": {
        "accessToken": "0b47092b-e9ff-4831-b5cc-7460e8eb6b8f:st:803-STS-739",
        "dataCenter": "st",
        "munchkinId": "803-STS-739",
        "clientContext": {
          "applicationId": "sapphireAccount:qa20241210190159",
          "deviceId": "94f04343-b5fa-11ef-ba4a-017b9394a518",
          "groupId": "22DE1AF9643D97B30A494133@AdobeOrg",
          "historyId": "91aa9560-bc61-11ef-beb6-a5b83058abb1",
          "instanceId": "62687052-b93d-11ef-85db-a5e072192799",
          "sessionId": "https://ims-na1-stg1.adobelogin.com/ims/session/v1/ZWE2ZWMwODMtZGEyNC00Nzc1LWI0ZTMtZThkNTQzZjA3OTEzLS0wNDNFMTk5QzY2QjJDOEU2MEE0OTQwMUJAMjk3YTA2Y2E1ZTk3NmU4ZDBhNDk0MjA0",
          "windowId": "91aa9561-bc61-11ef-beb6-a5b83058abb1"
        }
      },
      "sandboxName": "app-stage",
      "sandboxType": "production",
      "isDefaultSandbox": "false"
    }
    """.trimIndent()

    makeGraphQLRequest(queryPayload, token, object : ApiCallback {
        override fun onSuccess(response: String) {
            try {
                val gson = Gson()
                val parsedResponse = gson.fromJson(response, AccountDetailsResponse::class.java)
                val result = parsedResponse.data?.accountDetailsByIdV2?.result
                val errors = parsedResponse.data?.accountDetailsByIdV2?.errors
                AccountDetailsRepository.saveAccountDetails(result, errors)
                println("Account details saved successfully!")
                println("Account Details: ${AccountDetailsRepository.getAccountDetails()}")
            } catch (e: Exception) {
                println("Parsing Error: ${e.message}")
            }
        }

        override fun onError(error: String) {
            println("Error: $error")
        }
    })
}

fun fetchBuyingGroupDetails() {
    val queryPayload = """
    {
      "operationName": "getBuyingGroupDetailsV2",
      "variables": {
        "id": 212886
      },
      "query": "query getBuyingGroupDetailsV2(${'$'}id: Int!) {\n  buyingGroupByIdV2(id: ${'$'}id) {\n    result {\n      id\n      name\n      engagementScore\n      status\n      stage\n      buyingGroupTemplate {\n        id\n        name\n        __typename\n      }\n      account {\n        id\n        name\n        __typename\n      }\n      completenessScore\n      createdAt\n      updatedAt\n      __typename\n    }\n    __typename\n  }\n}",
      "extensions": {
        "accessToken": "76a0d810-d076-422e-ae84-1353d4dec098:st:803-STS-739",
        "dataCenter": "st",
        "munchkinId": "803-STS-739",
        "clientContext": {
          "applicationId": "sapphireBuyingGroups:stage20241206180737",
          "deviceId": "8a3f9603-bc60-11ef-991d-b9c4e0735b3e",
          "groupId": "22DE1AF9643D97B30A494133@AdobeOrg",
          "historyId": "74738380-bc8e-11ef-9501-d3acdc690893",
          "instanceId": "8a3f9602-bc60-11ef-991d-b9c4e0735b3e",
          "sessionId": "https://ims-na1-stg1.adobelogin.com/ims/session/v1/ZWE2ZWMwODMtZGEyNC00Nzc1LWI0ZTMtZThkNTQzZjA3OTEzLS0wNDNFMTk5QzY2QjJDOEU2MEE0OTQwMUJAMjk3YTA2Y2E1ZTk3NmU4ZDBhNDk0MjA0",
          "windowId": "74738381-bc8e-11ef-9501-d3acdc690893"
        }
      },
      "sandboxName": "app-stage",
      "sandboxType": "production",
      "isDefaultSandbox": "false"
    }
    """.trimIndent()

    makeGraphQLRequest(queryPayload, token, object : ApiCallback {
        override fun onSuccess(response: String) {
            try {
                val gson = Gson()
                val parsedResponse = gson.fromJson(response, BuyingGroupResponse::class.java)

                // Save data to the repository
                val result = parsedResponse.data?.buyingGroupByIdV2?.result
                BuyingGroupDetailsRepository.saveBuyingGroupDetails(result)
                println("Buying Group details saved successfully!")
                println("Buying Group Details: ${BuyingGroupDetailsRepository.getBuyingGroupDetails()}")
            } catch (e: Exception) {
                println("Parsing Error: ${e.message}")
            }
        }

        override fun onError(error: String) {
            println("Error: $error")
        }
    })
}

