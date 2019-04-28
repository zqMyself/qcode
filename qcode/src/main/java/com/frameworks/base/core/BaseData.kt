package com.frameworks.base.core

import java.io.Serializable

open class BaseData<T>  : Serializable {

     var currentPage          : Int = 0
     var limit                : Int = 0
     var totalPage            : Int = 0
     var totalNum             : Int = 0
     var rows                 : List<T>? = null
}