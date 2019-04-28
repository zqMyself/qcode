/**
 * Copyright 2016 JustWayward Team
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.frameworks.base.utils


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.SystemClock
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.text.format.Formatter
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import java.io.*
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*
import kotlin.experimental.and

/**
 * @author yuyh.
 * @date 16/4/9.
 */
object CodeDeviceUtils {

    private val TAG = CodeDeviceUtils::class.java.simpleName
    private val CMCC_ISP = "46000"//中国移动
    private val CMCC2_ISP = "46002"//中国移动
    private val CU_ISP = "46001"//中国联通
    private val CT_ISP = "46003"//中国电信

    /**
     * 获取设备的系统版本号
     */
    val deviceSDK: Int
        get() = Build.VERSION.SDK_INT
    /**
     * 获取设备的系统版本号
     */
    val deviceRELEASE: String
        get() {
            val release = Build.VERSION.RELEASE
            CodeLogUtils.debugInfo("release = $release")
            return release
        }
    /**
     * 获取设备的型号
     */
    val deviceName: String
        get() {
            val model = Build.MODEL
            CodeLogUtils.debugInfo("model =$model")
            return model
        }
    /**
     * 获取设备的品牌
     */
    val deviceBrand: String
        get() {
            val brand = Build.BRAND
            CodeLogUtils.debugInfo("model =$brand")
            return brand
        }
    /**
     * 获取设备的型号
     */
    val deviceModel: String
        get() {
            val model = Build.MODEL
            CodeLogUtils.debugInfo("model =$model")
            return model
        }
    /**
     * 获取设备当前语言
     *
     * @return
     */
    val language: String
        get() = Locale.getDefault().language

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    // 获得IpD地址
    val macAddress: String?
        get() {
            var strMacAddr: String? = null
            try {
                val ip = localInetAddress
                val b = NetworkInterface.getByInetAddress(ip)
                        .hardwareAddress
                val buffer = StringBuffer()
                for (i in b.indices) {
                    if (i != 0) {
                        buffer.append(':')
                    }
                    val str = Integer.toHexString((b[i] and 0xFF.toByte()).toInt())
                    buffer.append(if (str.length == 1) "0$str" else str)
                }
                strMacAddr = buffer.toString().toUpperCase()
            } catch (e: Exception) {
            }

            return strMacAddr
        }
    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private// 列举
    // 是否还有元素
    // 得到下一个元素
    // 得到一个ip地址的列举
    val localInetAddress: InetAddress?
        get() {
            var ip: InetAddress? = null
            try {
                val en_netInterface = NetworkInterface
                        .getNetworkInterfaces()
                while (en_netInterface.hasMoreElements()) {
                    val ni = en_netInterface
                            .nextElement() as NetworkInterface
                    val en_ip = ni.inetAddresses
                    while (en_ip.hasMoreElements()) {
                        ip = en_ip.nextElement()
                        if (!ip!!.isLoopbackAddress && ip.hostAddress.indexOf(":") == -1)
                            break
                        else
                            ip = null
                    }

                    if (ip != null) {
                        break
                    }
                }
            } catch (e: SocketException) {

                e.printStackTrace()
            }

            return ip
        }

    /**
     * 获取本地IP
     *
     * @return
     */
    private val localIpAddress: String?
        get() {
            try {
                val en = NetworkInterface
                        .getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    val enumIpAddr = intf
                            .inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress) {
                            return inetAddress.hostAddress.toString()
                        }
                    }
                }
            } catch (ex: SocketException) {
                ex.printStackTrace()
            }

            return null
        }

    /**
     * 获取设备HardwareAddress地址
     *
     * @return
     */
    val machineHardwareAddress: String?
        get() {
            var interfaces: Enumeration<NetworkInterface>? = null
            try {
                interfaces = NetworkInterface.getNetworkInterfaces()
            } catch (e: SocketException) {
                e.printStackTrace()
            }

            var hardWareAddress: String? = null
            var iF: NetworkInterface? = null
            if (interfaces == null) {
                return null
            }
            while (interfaces.hasMoreElements()) {
                iF = interfaces.nextElement()
                try {
                    hardWareAddress = bytesToString(iF!!.hardwareAddress)
                    if (hardWareAddress != null)
                        break
                } catch (e: SocketException) {
                    e.printStackTrace()
                }

            }
            return hardWareAddress
        }
    /**
     * android 7.0及以上 （3）通过busybox获取本地存储的mac地址
     *
     */

    /**
     * 根据busybox获取本地Mac
     *
     * @return
     */
    // 如果返回的result == null，则说明网络不可取
    // 对该行数据进行解析
    // 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
    val localMacAddressFromBusybox: String
        get() {
            var result: String? = ""
            var Mac = ""
            result = callCmd("busybox ifconfig", "HWaddr")
            if (result == null) {
                return "网络异常"
            }
            if (result.length > 0 && result.contains("HWaddr") == true) {
                Mac = result.substring(result.indexOf("HWaddr") + 6,
                        result.length - 1)
                result = Mac
            }
            return result
        }


    //    /**
    //     * 获取 MAC 地址
    //     * 须配置android.permission.ACCESS_WIFI_STATE权限
    //     */
    //    public static String getMacAddress(Context context) {
    //        try {
    //            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
    //            for (NetworkInterface nif : all) {
    //                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
    //
    //                byte[] macBytes = nif.getHardwareAddress();
    //                if (macBytes == null) {
    //                    return "";
    //                }
    //
    //                StringBuilder res1 = new StringBuilder();
    //                for (byte b : macBytes) {
    //                    res1.append(String.format("%02X:", b));
    //                }
    //
    //                if (res1.length() > 0) {
    //                    res1.deleteCharAt(res1.length() - 1);
    //                }
    //                CodeLogUtils.debugInfo"res1 ="+res1.toString());
    //                return res1.toString();
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        return "02:00:00:00:00:00";
    //    }

    /**
     * 获取 开机时间
     */
    val bootTimeString: String
        get() {
            val ut = SystemClock.elapsedRealtime() / 1000
            val h = (ut / 3600).toInt()
            val m = (ut / 60 % 60).toInt()
            CodeLogUtils.debugInfo(TAG, "$h:$m")
            return "$h:$m"
        }

    @SuppressLint("MissingPermission")
    fun getIMSI(context: Context): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.subscriberId
    }


    /**
     * 获取手机网络运营商类型
     *
     * @param context
     * @return
     */
    fun getPhoneISP(context: Context?): String {
        if (context == null) {
            return ""
        }
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var teleCompany = ""
        val np = manager.networkOperator

        if (np != null) {
            if (np == CMCC_ISP || np == CMCC2_ISP) {
                teleCompany = "中国移动"
            } else if (np.startsWith(CU_ISP)) {
                teleCompany = "中国联通"
            } else if (np.startsWith(CT_ISP)) {
                teleCompany = "中国电信"
            }
        }
        return teleCompany
    }

    /**
     * 获取屏幕信息
     *
     * @param context
     * @return
     */
    fun getDisplayMetrics(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }

    /**
     * 获取/打印屏幕信息
     *
     * @param context
     * @return
     */
    fun printDisplayInfo(context: Context): DisplayMetrics {
        val dm = getDisplayMetrics(context)
        val sb = StringBuilder()
        sb.append("\ndensity         :").append(dm.density)
        sb.append("\ndensityDpi      :").append(dm.densityDpi)
        sb.append("\nheightPixels    :").append(dm.heightPixels)
        sb.append("\nwidthPixels     :").append(dm.widthPixels)
        sb.append("\nscaledDensity   :").append(dm.scaledDensity)
        sb.append("\nxdpi            :").append(dm.xdpi)
        sb.append("\nydpi            :").append(dm.ydpi)
        CodeLogUtils.debugInfo(TAG, sb.toString())
        return dm
    }

    /**
     * 获取系统当前可用内存大小
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    fun getAvailMemory(context: Context): String {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        return Formatter.formatFileSize(context, mi.availMem)// 将获取的内存大小规格化
    }

    fun getMac(context: Context): String? {

        var strMac: String? = null

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.e("=====", "6.0以下")
            Toast.makeText(context, "6.0以下", Toast.LENGTH_SHORT).show()
            strMac = getLocalMacAddressFromWifiInfo(context)
            return strMac
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e("=====", "6.0以上7.0以下")
            //            Toast.makeText(context, "6.0以上7.0以下", Toast.LENGTH_SHORT).show();
            strMac = getMacAddress(context)
            return strMac
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e("=====", "7.0以上")
            if (!TextUtils.isEmpty(macAddress)) {
                Log.e("=====", "7.0以上1")
                //                Toast.makeText(context, "7.0以上1", Toast.LENGTH_SHORT).show();
                strMac = macAddress
                return strMac
            } else if (!TextUtils.isEmpty(machineHardwareAddress)) {
                Log.e("=====", "7.0以上2")
                //                Toast.makeText(context, "7.0以上2", Toast.LENGTH_SHORT).show();
                strMac = machineHardwareAddress
                return strMac
            } else {
                Log.e("=====", "7.0以上3")
                //                Toast.makeText(context, "7.0以上3", Toast.LENGTH_SHORT).show();
                strMac = localMacAddressFromBusybox
                return strMac
            }
        }

        return "02:00:00:00:00:00"
    }

    /**
     * 根据wifi信息获取本地mac
     * @param context
     * @return
     */
    fun getLocalMacAddressFromWifiInfo(context: Context): String {
        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val winfo = wifi.connectionInfo
        return winfo.macAddress
    }

    /**
     * android 6.0及以上、7.0以下 获取mac地址
     *
     * @param context
     * @return
     */
    fun getMacAddress(context: Context): String {

        // 如果是6.0以下，直接通过wifimanager获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val macAddress0 = getMacAddress0(context)
            if (!TextUtils.isEmpty(macAddress0)) {
                return macAddress0
            }
        }
        var str: String? = ""
        var macSerial: String? = ""
        try {
            val pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address")
            val ir = InputStreamReader(pp.inputStream)
            val input = LineNumberReader(ir)
            while (null != str) {
                str = input.readLine()
                if (str != null) {
                    macSerial = str.trim { it <= ' ' }// 去空格
                    break
                }
            }
        } catch (ex: Exception) {
            Log.e("----->" + "NetInfoManager", "getMacAddress:$ex")
        }

        if (macSerial == null || "" == macSerial) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress:$e")
            }

        }
        return macSerial!!
    }

    private fun getMacAddress0(context: Context): String {
        if (isAccessWifiStateAuthorized(context)) {
            val wifiMgr = context
                    .getSystemService(Context.WIFI_SERVICE) as WifiManager
            var wifiInfo: WifiInfo? = null
            try {
                wifiInfo = wifiMgr.connectionInfo
                return wifiInfo!!.macAddress
            } catch (e: Exception) {
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress0:$e")
            }

        }
        return ""

    }

    /**
     * Check whether accessing wifi state is permitted
     *
     * @param context
     * @return
     */
    private fun isAccessWifiStateAuthorized(context: Context): Boolean {
        if (PackageManager.PERMISSION_GRANTED == context
                        .checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
            Log.e("----->" + "NetInfoManager", "isAccessWifiStateAuthorized:" + "access wifi state is enabled")
            return true
        } else
            return false
    }

    @Throws(Exception::class)
    private fun loadFileAsString(fileName: String): String {
        val reader = FileReader(fileName)
        val text = loadReaderAsString(reader)
        reader.close()
        return text
    }

    @Throws(Exception::class)
    private fun loadReaderAsString(reader: Reader): String {
        val builder = StringBuilder()
        val buffer = CharArray(4096)
        var readLength = reader.read(buffer)
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength)
            readLength = reader.read(buffer)
        }
        return builder.toString()
    }

    /***
     * byte转为String
     *
     * @param bytes
     * @return
     */
    private fun bytesToString(bytes: ByteArray?): String? {
        if (bytes == null || bytes.size == 0) {
            return null
        }
        val buf = StringBuilder()
        for (b in bytes) {
            buf.append(String.format("%02X:", b))
        }
        if (buf.length > 0) {
            buf.deleteCharAt(buf.length - 1)
        }
        return buf.toString()
    }

    private fun callCmd(cmd: String, filter: String): String? {
        var result = ""
        var line = ""
        try {
            val proc = Runtime.getRuntime().exec(cmd)
            val `is` = InputStreamReader(proc.inputStream)
            val br = BufferedReader(`is`)
            line = br.readLine()
            while (line != null && !line.contains(filter)) {
                result += line
                line = br.readLine()
            }

            result = line
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }
}
