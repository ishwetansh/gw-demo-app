package com.example.gw_ajo_b2b.Models

import com.google.gson.annotations.SerializedName

data class AccountDetailsResponse(
    val data: AccountDetailsData?
)

data class AccountDetailsData(
    @SerializedName("accountDetailsByIdV2") val accountDetailsByIdV2: AccountDetailsByIdV2?
)

data class AccountDetailsByIdV2(
    val requestId: String?,
    val success: Boolean?,
    val result: AccountResult?,
    val errors: List<ErrorObject>?,
    @SerializedName("__typename") val typename: String?
)

data class AccountResult(
    val id: Int?,
    val name: String?,
    val industry: String?,
    val memberCount: Int?,
    val opportunityCount: Int?,
    val buyingGroupCount: Int?,
    val journeys: List<Any>?,
    val createdAt: String?,
    val updatedAt: String?,
    @SerializedName("__typename") val typename: String?
)


object AccountDetailsRepository {

    private var accountDetails: AccountResult? = null
    private var errors: List<ErrorObject>? = null

    fun saveAccountDetails(details: AccountResult?, errorList: List<ErrorObject>?) {
        accountDetails = details
        errors = errorList
    }

    fun getAccountDetails(): AccountResult? {
        return accountDetails
    }

    fun getErrors(): List<ErrorObject>? {
        return errors
    }

    fun clear() {
        accountDetails = null
        errors = null
    }
}
