package com.hellofresh.chiragtest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class RecipeData(
        @SerializedName("calories") var calories: String?,
        @SerializedName("carbos") var carbos: String?,
        @SerializedName("card") var card: String?,
        @SerializedName("country") var country: String?,
        @SerializedName("deliverable_ingredients") var deliverableIngredientList: ArrayList<String>?,
        @SerializedName("description") var description: String?,
        @SerializedName("difficulty") var difficulty: String?,
        @SerializedName("fats") var fats: String?,
        @SerializedName("favorites") var favorites: String?,
        @SerializedName("fibers") var fibers: String?,
        @SerializedName("headline") var headline: String?,
        @SerializedName("highlighted") var highlighted: String?,
        @SerializedName("id") var id: String?,
        @SerializedName("image") var image: String?,
        @SerializedName("incompatibilities") var incompatibilities: String?,
        @SerializedName("ingredients") var ingredientList: ArrayList<String>?,
        @SerializedName("keywords") var keywordList: ArrayList<String>?,
        @SerializedName("name") var name: String?,
        @SerializedName("products") var productsList: ArrayList<String>?,
        @SerializedName("proteins") var proteins: String?,
        @SerializedName("rating") var rating: String?,
        @SerializedName("ratings") var ratings: String?,
        @SerializedName("thumb") var thumb: String?,
        @SerializedName("time") var time: String?,
        @SerializedName("undeliverable_ingredients") var unDeliverableIngredientList: ArrayList<String>?,
        @SerializedName("user") var userData: UserData?,
        @SerializedName("weeks") var weeksList: ArrayList<String>?,
         var isFav: Boolean=false


):Parcelable

    @Parcelize
    data class UserData(
        @SerializedName("email") var email: String?,
        @SerializedName("latlng") var coordinates: String?,
        @SerializedName("name") var time: String?
    ) : Parcelable












