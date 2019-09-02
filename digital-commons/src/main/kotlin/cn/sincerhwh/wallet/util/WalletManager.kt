package cn.sincerhwh.wallet.util

import cn.sincerhwh.wallet.base.params.WaykiMainNetParams
import cn.sincerhwh.wallet.base.params.WaykiTestNetParams
import org.bitcoinj.core.DumpedPrivateKey
import org.bitcoinj.core.LegacyAddress
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.crypto.MnemonicCode
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.TestNet3Params
import java.lang.Exception


/**
 * @author     ：louis
 * @date       ：Created in 2019-09-02 13:36
 * @description：钱包工具
 * @modified By：
 * @version    ：1.0
 */
enum class NetType(val chainSymbol: String, val netSymbol: String,
                   val netParams: NetworkParameters, val description: String) {
    BTC_MAIN("BTC", "main", MainNetParams(), "BTC主网"),
    BTC_TEST("BTC", "test", TestNet3Params(),"BTC测试"),
//    ETH_MAIN("ETH", "main", "ETH主网"),
//    ETH_TEST("ETH", "test", "ETH测试网"),
//    EOS_MAIN("EOS", "main", "EOS主网"),
//    EOS_TEST("EOS", "test", "EOS测试网"),
//    USDT_MAIN("USDT", "main", "USDT主网"),
//    USDT_TEST("USDT", "test", "USDT测试网"),
    WICC_MAIN("WICC", "main", WaykiMainNetParams(), "WICC主网"),
    WICC_TEST("WICC", "test", WaykiTestNetParams(), "WICC测试网")
}

/** 钱包的核心 */
data class WalletCore(var privateKey: String, var net: NetType, var tag: String)

/** 详细的钱包 */
data class Wallet(var address: String, var mnemonic: String, var core: WalletCore)


class WalletManager {

    companion object {

        /******************* 助记词相关 ******************/

        /** 生成助记词 **/
        fun generateMnemonic(): List<String> {
            return MnemonicManager.generateMnemonic()
        }

        /** 校验助记词有效 **/
        fun isValidateMnemonic(mnemonicsList: List<String>): Boolean {
            return MnemonicManager.isValidateMnemonic(mnemonicsList)
        }

        /******************* 校验 ******************/

        /** 校验地址私钥有效 **/
        fun isValidatePrivateKey(key: String): Boolean {
            return false
        }

        /** 校验地址是否有效 **/
        fun isValidateAddress(address: String): Boolean {
            return true
        }

        /******************* 助记词-> 私钥 ->地址：转换 ******************/

        /** 助记词 --> 私钥 **/
        fun transformMnemonicIntoPrivateKey(mnemonic: List<String>, netType: NetType): String {
            return ""
        }

        /** 助记词 --> 地址 **/
        fun transformMnemonicIntoAddress(mnemonic: List<String>, netType: NetType): String {
            val privatekey = transformMnemonicIntoPrivateKey(mnemonic, netType)
            return ""
        }

        /** 助记词 --> 核心钱包 **/
        fun transformMnemonicIntoWalletCore(mnemonic: List<String>, netType: NetType): WalletCore {
            val privatekey = transformMnemonicIntoPrivateKey(mnemonic, netType)
            return WalletCore("", netType, "")
        }

        /** 私钥 --> 地址 **/
        fun transformPrivateKeyIntoAddress(key: String, netType: NetType): String {
            return ""
        }

        /** 私钥 --> 核心钱包 **/
        fun transformPrivateKeyIntoWalletCore(key: String, netType: NetType): String {

            return ""
        }


        /******************* 高阶方法 *******************/

//        fun generateWallet(): Wallet {
//
//            return Wallet()
//        }

        fun generateWalletList(): List<Wallet> {

            return arrayListOf()
        }

        fun generateCommonTx(wallet: WalletCore, address: String, amount: Long, fee: Long=10000): String {
            val dumpedKey = DumpedPrivateKey.fromBase58(wallet.net.netParams, wallet.privateKey).key
            val address = LegacyAddress.fromPubKeyHash(wallet.net.netParams, dumpedKey.pubKeyHash).toString()
            return ""
        }

        fun generateContractTx(): String {

            return ""
        }


    }

}

fun main(args: Array<String>) {
    val a = WalletManager.generateMnemonic()
    println(a)
}


