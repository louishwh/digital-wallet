package cn.sincerhwh.wallet.util

import org.bitcoinj.crypto.MnemonicCode
import java.lang.Exception


/**
 * @author     ：louis
 * @date       ：Created in 2019-09-02 20:19
 * @description：助记词
 * @modified By：
 * @version    ：1.0
 */
class MnemonicManager {

    companion object {

        fun generateMnemonic(): List<String> {
            val byteArray = Byte.randomByteArray(16)
            return try {
                MnemonicCode.INSTANCE.toMnemonic(byteArray)
            } catch (e: Exception) {
                arrayListOf()
            }
        }

        fun isValidateMnemonic(mnemonicsList: List<String>): Boolean {
            return try {
                MnemonicCode.INSTANCE.check(mnemonicsList)
                true
            } catch (e: Exception) {
                false
            }
        }

    }
}