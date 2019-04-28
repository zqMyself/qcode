package com.xiangjiale.zaoplanet.module.welcome.bean

import android.support.annotation.Nullable
import android.text.TextUtils
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


@Suppress("UNREACHABLE_CODE")
open class ProfileBean : RealmObject() {


    val itemType: Int
        get() = 0

    @PrimaryKey
    private var id: String = ""
    private var userId: String = ""
    private var face: String = ""
    private var age: Int = 0
    private var birthday: String? = null
    private var constellation: String? = null
    private var signature: String? = null
    private var lat: Double = 0.toDouble()
    private var lon: Double = 0.toDouble()
    private var province: String? = null
    private var city: String? = null
    private var distict: String? = null
    private var address: String? = null
    private var contribute: Int = 0
    private var medals: Int = 0
    private var fans: String = ""
    private var follows: String = ""
    private var likes: String? = null
    private var likeMes: String? = null
    private var geohash: String? = null




    fun getId(): String {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getUserId(): String {
        return userId
    }

    fun setUserId(userId: String) {
        this.userId = userId
//               return  if(TextUtils.isEmpty(face)) "" else  face!!
    }
    fun getFace(): String{
        return face
    }

    fun setFace(face: String) {
        if(TextUtils.isEmpty(face)) {
            this.face = ""
        } else this.face = face
    }

    fun getAge(): Int {
        return age
    }

    fun setAge(age: Int) {
        this.age = age
    }

    fun getBirthday(): String {
        return  if(TextUtils.isEmpty(birthday)) "" else birthday!!
    }

    fun setBirthday(birthday: String) {
        this.birthday = birthday
    }

    fun getConstellation(): String {
        return  if(TextUtils.isEmpty(constellation)) "" else constellation!!
    }

    fun setConstellation(constellation: String) {
        this.constellation = constellation
    }

    fun getSignature(): String {
        return  if(TextUtils.isEmpty(signature)) {
            ""
        } else signature!!
    }

    fun setSignature(signature: String) {
        if(TextUtils.isEmpty(signature)) {
            this.signature = ""
        } else {
            this.signature = signature
        }
    }

    fun getLat(): Double {
        return lat
    }

    fun setLat(lat: Double) {
        this.lat = lat
    }

    fun getLon(): Double {
        return lon
    }

    fun setLon(lon: Double) {
        this.lon = lon
    }

    fun getProvince(): String {
        return  if(TextUtils.isEmpty(province)) {
            ""
        } else province!!
    }

    fun setProvince(province: String) {
        if(TextUtils.isEmpty(province)) {
            this.province = ""
        } else {
            this.province = province
        }
    }

    fun getCity(): String {
        return  if(TextUtils.isEmpty(city)) "" else city!!
    }

    fun setCity(city: String) {
        if(TextUtils.isEmpty(city)) {
            this.city
        } else   this.city = city
    }

    fun getDistict(): String {
        return  if(TextUtils.isEmpty(distict)) {
            ""
        } else {
            distict!!
        }
    }

    fun setDistict(distict: String) {
        if(TextUtils.isEmpty(distict)) {
            this.distict = ""
        } else {
            this.distict = distict
        }

    }

    fun getAddress(): String {
        return    if(TextUtils.isEmpty(address)) {
            ""
        } else {
            address!!
        }
    }

    fun setAddress(address: String) {
        if(TextUtils.isEmpty(address)) {
            this.address = ""
        } else {
            this.address = address
        }

    }

    fun getContribute(): Int {
        return contribute
    }

    fun setContribute(contribute: Int) {
        this.contribute = contribute
    }

    fun getMedals(): Int {
        return medals
    }

    fun setMedals(medals: Int) {
        this.medals = medals
    }

    fun getFans(): String {
         return  return  if(TextUtils.isEmpty(fans)) {
            "0"
        } else {
             fans
        }
    }

    fun setFans(fans: String) {
        if(TextUtils.isEmpty(fans)) {
            this.fans = ""
        } else {
            this.fans = fans
        }
    }

    fun getFollows(): String {
        return  if(TextUtils.isEmpty(follows)) {
            "0"
        } else follows
    }

    fun setFollows(follows: String) {
        if(TextUtils.isEmpty(follows)) {
            this.follows = ""
        } else {
            this.follows = follows
        }
    }

    fun getLikes(): String {
        return   if(TextUtils.isEmpty(likes)) {
            "0"
        } else {
            likes!!

        }

    }

    fun setLikes(likes: String) {
        if(TextUtils.isEmpty(likes)) {
            this.likes =   ""
        } else {
            this.likes = likes
        }

    }

    fun getLikeMes(): String {
        return   if(TextUtils.isEmpty(likeMes)) {
            "0"
        } else {
            likeMes!!
        }
    }

    fun setLikeMes(likeMes: String) {
        if(TextUtils.isEmpty(likeMes)) {
            this.likeMes = ""
        } else {
            this.likeMes = likeMes
        }
    }

    fun getGeohash(): String {
        return   if(TextUtils.isEmpty(geohash)) {
            ""
        } else {
            geohash!!
        }

    }

    fun setGeohash(geohash: String) {
        if(TextUtils.isEmpty(geohash)) {
            this.geohash = ""
        } else {
            this.geohash = geohash
        }
    }

}

