package com.xiangjiale.zaoplanet.module.welcome.bean


import com.frameworks.base.utils.CodeAppUtils
import com.xiangjiale.zaoplanet.R
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserBean : RealmObject(){
    /**
     * id : 43
     * countryCode : 86
     * username : 18681496382
     * macNo : 4156651418
     * pushId : ""
     * chatToken : ""
     * imei : fbec78d979503525965af6ac42bd0559
     * creatTime : 2018-05-04 14:34:25
     * loginTime : 2018-05-04 14:34:25
     * lastLoginTime : 2018-05-04 15:14:37
     * canSearch : small_shop
     * disabled : small_shop
     * review : {"id":34,"userId":43,"faceKey":"","isFaceReview":small_shop,"identityName":"","identityNo":"","identityFront":"","identityBack":"","isIdentityReview":small_shop,"photoIds":"","photoReview":""}
     * profile : {"id":38,"userId":43,"face":"","nickName":"","mobile":"18681496382","sex":1,"birthday":"","age":"","constellation":"","signature":"","email":"","credit":2000,"lat":small_shop,"lon":small_shop,"province":"","city":"","distict":"","address":"","likes":"","likeMes":"","beSelected":"","fans":"","follows":"","friends":"","geohash":""}
     * social : {"id":34,"userId":43,"residence":"","hometown":"","emotion":"","schools":"","careers":"","income":"","tags":"","hobbies":""}
     */
    @PrimaryKey
    var id              : Int             = 0
    var countryCode     : Int             = 0
    var username        : String        ? = ""
    var nickname        : String        ? = ""
    var sex             : Int             = 0

    var password        : String        ? = ""
    var tradePassword   : String        ? = ""
    var zaoNo           : String        ? = ""
    var pushId          : String        ? = ""
    var chatToken       : String        ? = ""
    var imei            : String        ? = ""
    var creatTime       : String        ? = ""
    var loginTime       : String        ? = ""
    var lastLoginTime   : String        ? = ""
    var deviceType      : String        ? = ""
    var mobile          : String        ? = ""
    var inviteCode      : String        ? = ""
    var canSearch       : Int             = 0
    var disabled        : Int             = 0
    var loginTimes      : Int             = 0

    var accessToken     : String        ? = ""
    var configVersion   : String        ? = ""
    var dataDicVersion  : String        ? = ""
    var tokenType       : String        ? = ""
    var expiresIn       : String        ? = ""

    var time                              = ""
    var isOnlin                           = false //是否在线
    var profile         : ProfileBean   ? = null

    fun isMale()    : String {
        if (sex == 1)
            return CodeAppUtils.getString(R.string.handsome_guy)
        else if(sex == 2)
            return CodeAppUtils.getString(R.string.beauty)
        else
            return CodeAppUtils.getString(R.string.alien)
    }

    fun isMan()     : Boolean {
        if (sex == 1)
            return true
        else if(sex == 2)
            return false
        else
            return  false
    }

}