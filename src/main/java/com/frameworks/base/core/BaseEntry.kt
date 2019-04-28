package com.frameworks.base.core

import io.rx_cache2.Source
import java.io.Serializable

open class BaseEntry<T> :Serializable{
  var   status               = 0
  var   msg                  = ""
  var   data      : T       ?= null
  var   source    : Source  ?= null
}