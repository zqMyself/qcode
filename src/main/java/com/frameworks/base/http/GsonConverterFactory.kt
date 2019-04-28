package com.frameworks.base.http

/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * <pre>
 * author   : zengqiang
 * e-mail   : 972022400@qq.com
 * time     : 2019/04/11
 * desc     :
 * version  : 1.0
 * </pre>
 *
 */

class GsonConverterFactory private constructor(private val gson: Gson?) : Converter.Factory() {

    init {
        if (gson == null) throw NullPointerException("gson == null")
    }

    override fun responseBodyConverter(
            type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *> {
        val adapter = gson!!.getAdapter(TypeToken.get(type!!))
        return GsonResponseBodyConverter(adapter)
    }

    override fun requestBodyConverter(
            type: Type?, parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody> {
        val adapter = gson!!.getAdapter(TypeToken.get(type!!))
        return GsonRequestBodyConverter(gson, adapter)
    }

    companion object {

        /**
         * Create an instance using `gson` for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         */
        @JvmOverloads
        fun create(gson: Gson = Gson()): GsonConverterFactory {
            return GsonConverterFactory(gson)
        }
    }
}

