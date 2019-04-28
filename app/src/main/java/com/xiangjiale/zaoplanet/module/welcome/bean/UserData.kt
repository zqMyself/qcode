package com.xiangjiale.zaoplanet.module.welcome.bean



open  class  UserData  {

    var user: UserBean? = null
    var rememberMe : String ? = null
    var configVersion : String? = null
    var dataDicVersion : String? = null
    var tokenType : String? = null
    var expiresIn : String? = null
    var session : String? = null
    var accessToken : String? = null
    var rewardConfigIsOn = false
    var loginTimes = 0
    //额外属性
    var time = ""
}