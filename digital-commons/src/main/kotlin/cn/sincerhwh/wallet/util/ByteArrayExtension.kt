package cn.sincerhwh.wallet.util

import sun.security.provider.SecureRandom


/**
 * @author     ：louis
 * @date       ：Created in 2019-09-02 11:59
 * @description：字节数组
 * @modified By：
 * @version    ：1.0
 */


fun Byte.Companion.randomByteArray(size: Int): ByteArray {
    val seed = SecureRandom()
    val bytes = ByteArray(size)
    seed.engineNextBytes(bytes)
    return bytes
}

