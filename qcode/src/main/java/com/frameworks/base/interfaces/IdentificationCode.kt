package com.frameworks.base.interfaces

interface IdentificationCode {
    companion object {
        val SUCCESS                             = 200
        val CODE_201                            = 201//新版本
        val CODE_203                            = 203//没有更多数据
        val CODE_204                            = 204//无更新数据
        val CODE_400                            = 400//请求失败,参数错误
        val CODE_500                            = 500//请求超时
        val CODE_202                            = 202//设置交易密码
        val CODE_100                            = 100
        val CODE_104                            = 104//登录过期
        val CODE_105                            = 105//签名过期
        val CODE_106                            = 106//操作过时，请重新操作
        val CODE__200                           = -200//时间戳过期
        val CODE__100                           = -100//未登录
        val CODE__400                           = -400//签名过期
        val CODE__000                           = 0x000//请求activity
        val CODE__001                           = 0x001//返回退出activity
        val CODE__002                           = 0x002


        val DATA_EMPTY                          = "数据为空"
        val DATA_NO_MORE                        = "没有更多数据了"
        val DATA_ERROR                          = "数据加载失败"
        val SERVER_EXCEPTION                    = "网络异常或数据加载失败"
        val SERVER_NO_ADDERSS                   = "服务器地址不存在"
        val PHOTO_UPLOAD_ERROR                  = "图片上传失败"
    }
}
