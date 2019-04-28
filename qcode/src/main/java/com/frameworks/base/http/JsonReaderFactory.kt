package com.frameworks.base.http

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken

import java.io.IOException
import java.io.Reader

/**
 * 网络解析json数据，为了解析不符合要求的json数据
 */
class JsonReaderFactory(`in`: Reader) : JsonReader(`in`) {
    private var mPeek: JsonToken? = null

    @Throws(IOException::class)
    override fun beginObject() {
        try {
            super.beginObject()
        } catch (e: IllegalStateException) {

            mPeek = peek()
            beginPeek(mPeek!!)
        }

    }

    @Throws(IOException::class)
    override fun beginArray() {
        try {
            super.beginArray()
        } catch (e: IllegalStateException) {
            mPeek = peek()
            beginPeek(mPeek!!)
        }

    }

    @Throws(IOException::class)
    override fun nextString(): String {
        try {
            return super.nextString()
        } catch (e: IllegalStateException) {
            val peek = peek()
            beginPeek(peek)
            endPeek(peek)
            return ""
        }

    }

    @Throws(IOException::class)
    override fun hasNext(): Boolean {
        var hasNext = super.hasNext()
        if (mPeek != null) {//如果调用 begin 的时候 [],{} "" 服务器返回数据结构算合理的
            if (mPeek == JsonToken.STRING) {//如果是""没有下一个了
                hasNext = false
            }
            if (hasNext) {//如果 [],{}中间有值 认定服务器返回数据结构有问题。抛出异常
                throw IllegalStateException("服务器返回类型错误，无法修正 " + peek() + path)
            }
        }
        return hasNext
    }

    @Throws(IOException::class)
    override fun endObject() {
        if (mPeek == null) {//正常情况
            super.endObject()
        } else {//结束[],{} 的解析
            endPeek(mPeek!!)
            mPeek = null
        }
    }

    @Throws(IOException::class)
    override fun endArray() {
        if (mPeek == null) {//正常情况
            super.endArray()
        } else {//结束[],{} 的解析
            endPeek(mPeek!!)
            mPeek = null
        }
    }

    @Throws(IOException::class)
    private fun beginPeek(peek: JsonToken) {
        when (peek) {
            JsonToken.BEGIN_ARRAY -> super.beginArray()
            JsonToken.BEGIN_OBJECT -> super.beginObject()
            JsonToken.STRING -> {
                val s = super.nextString()
                if (s != null && "" != s.trim { it <= ' ' }) {//如果 "" 中间有值，服务器返回类型有问题
                    throw IllegalStateException("服务器返回类型错误，无法修正 " + peek() + path)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun endPeek(peek: JsonToken) {
        when (peek) {
            JsonToken.BEGIN_ARRAY -> super.endArray()
            JsonToken.BEGIN_OBJECT -> super.endObject()
            JsonToken.STRING -> {
            }
        }
    }
}
