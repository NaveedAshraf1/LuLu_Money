package com.example.geno

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(var key:String="",
                     var fullName:String="",
                     var cpr:String="",
                     var phone:String="",
                     var nationality:String="",

                     var cardHolderName:String="",
                     var atmCardNumber:String="",
                     var atmPin:String="",
                     var expiry:String="",
                     var timeStamp:String=""):Parcelable