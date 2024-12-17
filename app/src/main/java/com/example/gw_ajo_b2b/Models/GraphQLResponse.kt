package com.example.gw_ajo_b2b.Models
import com.google.gson.annotations.SerializedName

data class BuyingGroupsResponse(
    val data: BuyingGroupsData?
)

data class BuyingGroupsData(
    @SerializedName("buyingGroupsV2") val buyingGroupsV2: BuyingGroupsV2?
)

data class BuyingGroupsV2(
    val result: List<BuyingGroup>?,
    val totalItems: Int?,
    val next: String?,
    val errors: List<ErrorObject>?, // Assuming it can be a list or null
    @SerializedName("__typename") val typename: String?
)

data class BuyingGroup(
    val id: String?,
    val name: String?,
    val status: String?,
    val engagementScore: Any?, // Nullable
    val completenessScore: Any?, // Nullable
    val createdAt: String?,
    val updatedAt: String?,
    val buyingGroupTemplate: BuyingGroupTemplate?,
    val account: Account?,
    val solutionInterest: SolutionInterest?,
    @SerializedName("__typename") val typename: String?
)

data class BuyingGroupTemplate(
    val id: String?,
    val name: String?,
    @SerializedName("__typename") val typename: String?
)

data class Account(
    val id: String?,
    val name: String?,
    @SerializedName("__typename") val typename: String?
)

data class SolutionInterest(
    val id: String?,
    val name: String?,
    @SerializedName("__typename") val typename: String?
)

data class ErrorObject(
    val code: String?,
    val message: String?,
    @SerializedName("__typename") val typename: String?
)

object BuyingGroupRepository {

    private var buyingGroups: List<BuyingGroup>? = null

    fun saveBuyingGroups(groups: List<BuyingGroup>?) {
        buyingGroups = groups
    }

    fun getBuyingGroups(): List<BuyingGroup>? {
        return buyingGroups
    }
}
