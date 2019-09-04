package cn.sincerhwh.wallet.util

import cn.sincerhwh.wallet.base.params.*
import org.bitcoinj.core.DumpedPrivateKey
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.LegacyAddress
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.crypto.MnemonicCode
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.wallet.DeterministicKeyChain
import org.bitcoinj.wallet.DeterministicSeed
import org.waykichain.wallet.util.BIP44Util
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
data class WalletCore(var publicKey: String,var privateKey: String,var net: NetType, var password: String)

/** 详细的钱包 */
data class Wallet(var address: String, var mnemonic: List<String>, var core: WalletCore)


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

        /** 生成钱包 **/
        fun generateWallet(net: NetType, password: String=""): Wallet {
            val mnemonicList = generateMnemonic()
            return generateWalletFromMnemonic(mnemonicList, net, password)
        }

        fun generateWalletFromMnemonic(mnemonicsList: List<String>, net: NetType, password: String=""): Wallet {
            if (MnemonicManager.isValidateMnemonic(mnemonicsList)) {
                val mnemonicList = generateMnemonic()
                val seed = DeterministicSeed(mnemonicList, null, password, 0L)
                val keyChain = DeterministicKeyChain.builder().seed(seed).build()
                val networkParameters = WaykiTestNetParams.instance
                val mainKey = keyChain.getKeyByPath(BIP44Util.generatePath(BIP44Util.WAYKICHAIN_WALLET_PATH + "/0/0"), true)
                val address = LegacyAddress.fromPubKeyHash(networkParameters, mainKey.pubKeyHash).toString()
                val ecKey = ECKey.fromPrivate(mainKey.privKey)
                val publicKey = ecKey.publicKeyAsHex
                val privateKey = ecKey.getPrivateKeyAsWiF(networkParameters)
                return Wallet(address, mnemonicList, WalletCore(publicKey, privateKey, net, password))
            }
            return Wallet("", arrayListOf(), WalletCore("", "", net, password))
        }

        /******************* 校验 ******************/

        /** 校验地址私钥有效 **/
        fun isValidatePrivateKey(privateKey: String, netType: NetType): Boolean {
            try {
                LegacyAddress.fromBase58(netType.netParams, privateKey)
            } catch (e: Exception) {
                return false
            }
            return true
        }

        /** 校验地址是否有效 **/
        fun isValidateAddress(address: String): Boolean {
            return true
        }


        /******************* 助记词-> 私钥 ->地址：转换 ******************/

        /** 助记词 --> 私钥 **/
        fun transformMnemonicIntoPrivateKey(mnemonic: List<String>, netType: NetType, password: String=""): String {
            val wallet = generateWalletFromMnemonic(mnemonic, netType, password)
            return wallet.core.privateKey
        }

        /** 助记词 --> 地址 **/
        fun transformMnemonicIntoAddress(mnemonic: List<String>, netType: NetType, password: String=""): String {
            return generateWalletFromMnemonic(mnemonic, netType, password).address
        }

        /** 助记词 --> 核心钱包 **/
        fun transformMnemonicIntoWalletCore(mnemonic: List<String>, netType: NetType, password: String=""): WalletCore {
            return generateWalletFromMnemonic(mnemonic, netType, password).core
        }

        /** 私钥 --> 地址 **/
        fun transformPrivateKeyIntoAddress(privateKey: String, netType: NetType, password: String=""): String {
            if (!isValidatePrivateKey(privateKey)) {
                return transformPrivateKeyIntoWallet(privateKey, netType, password).address
            }
            return ""
        }

        /** 私钥 --> 核心钱包 **/
        fun transformPrivateKeyIntoWallet(privateKey: String, netType: NetType, password: String=""): Wallet {
            if (isValidatePrivateKey(privateKey)) {
                val eCKey = DumpedPrivateKey.fromBase58(netType.netParams, privateKey).key
                val address = LegacyAddress.fromPubKeyHash(netType.netParams, eCKey.pubKeyHash).toString()
                val core = WalletCore(eCKey.publicKeyAsHex, privateKey, netType, password)
                return Wallet(address, arrayListOf(), core)
            }
            return Wallet("", arrayListOf(), WalletCore("", "", netType, password))
        }


        /******************* 签名： 激活-转账-合约 *******************/

        fun generateAccountTx(wallet: WalletCore, params: TransferParams): String {
            val dumpedKey = DumpedPrivateKey.fromBase58(wallet.net.netParams, wallet.privateKey).key
            val address = LegacyAddress.fromPubKeyHash(wallet.net.netParams, dumpedKey.pubKeyHash).toString()
            return ""
        }

        fun generateCommonTx(wallet: WalletCore, params: TransferParams): String {
            val dumpedKey = DumpedPrivateKey.fromBase58(wallet.net.netParams, wallet.privateKey).key
            val address = LegacyAddress.fromPubKeyHash(wallet.net.netParams, dumpedKey.pubKeyHash).toString()
            return ""
        }

        fun generateContractTx(wallet: WalletCore, contractParams: ContractParams): String {

            return ""
        }

    }

}

fun main(args: Array<String>) {
    val mnemonicsList = WalletManager.generateMnemonic()

    val wordList = mnemonicsList.reduce{ acc, s -> "$acc $s"}
    println("--$wordList--")
}


