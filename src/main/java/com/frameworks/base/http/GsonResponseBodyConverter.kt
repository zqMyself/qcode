package com.frameworks.base.http

/**
 * <pre>
 * author   : zengqiang
 * e-mail   : 972022400@qq.com
 * time     : 2019/04/11
 * desc     :网络响应数据处理
 * version  : 1.0
</pre> *
 */

import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

internal class GsonResponseBodyConverter<T>(private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val response = value.string()//只能对ResponseBody读取一次
        val inputStream = ByteArrayInputStream(response.toString().toByteArray())
        val reader = InputStreamReader(inputStream)
        val jsonReaderFactory = JsonReaderFactory(reader)
        return adapter.read(jsonReaderFactory)

    }

}
