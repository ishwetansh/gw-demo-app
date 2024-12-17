package com.example.gw_ajo_b2b.Models

import com.google.gson.annotations.SerializedName

data class BuyingGroupResponse(
    val data: BuyingGroupData?
)

data class BuyingGroupData(
    @SerializedName("buyingGroupByIdV2") val buyingGroupByIdV2: BuyingGroupByIdV2?
)

data class BuyingGroupByIdV2(
    val result: List<BuyingGroupDetail>?,
    @SerializedName("__typename") val typename: String?
)

data class BuyingGroupDetail(
    val id: String?,
    val name: String?,
    val engagementScore: Any?, // Nullable
    val status: String?,
    val stage: String?,
    val buyingGroupTemplate: BuyingGroupTemplate?,
    val account: AccountDetail?,
    val completenessScore: Any?, // Nullable
    val createdAt: String?,
    val updatedAt: String?,
    @SerializedName("__typename") val typename: String?
)

data class AccountDetail(
    val id: String?,
    val name: String?,
    @SerializedName("__typename") val typename: String?
)

object BuyingGroupDetailsRepository {
    private var buyingGroupDetails: List<BuyingGroupDetail>? = null

    fun saveBuyingGroupDetails(details: List<BuyingGroupDetail>?) {
        buyingGroupDetails = details
    }

    fun getBuyingGroupDetails(): List<BuyingGroupDetail>? {
        return buyingGroupDetails
    }
}
