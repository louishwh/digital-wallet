package cn.sincerhwh.wallet.base.params


/**
 * @author     ：louis
 * @date       ：Created in 2019-09-02 20:44
 * @description：普通转账的参数
 * @modified By：
 * @version    ：1.0
 */
open class TransferParams {

    /** 转账地址 */
    var address: String? = null

    /** 转账金额 */
    var amount: Long? = null

    /** 转账手续费 */
    var fee: Long? = 10000

}