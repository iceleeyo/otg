package com.springtour.otg.interfaces.transacting.facade;

import javax.jws.WebService;

import com.springtour.otg.interfaces.transacting.facade.rq.*;
import com.springtour.otg.interfaces.transacting.facade.rs.*;

/**
 * 交易接口<br>
 * 该接口提供交易相关服务，如获取可用网关、获取可用的持卡人证件类型、“带卡”交易请求和“无卡”交易请求等。
 * <br>访问地址：http://192.168.1.13:7001/remote/otg/requestHandlerFacade.htm
 * 
 * @author Hippoom
 * 
 */
@WebService
public interface RequestHandlerFacade {
    /**
     * 生成一笔交易
     * 
     * @param rq 交易数据
     * @return 一笔已向支付渠道请求支付的交易
     */
    NewTransactionRs newTransaction(NewTransactionRq rq);

    /**
     * 获取可用的网关,网关是指该支付渠道支持的银行或其他发卡机构。
     * 
     * @param rq 可用网关查询条件
     * @return 可用网关数据
     */
    AvailableGatewaysRs availableGateways(AvailableGatewaysRq rq);

    /**
     * 获取渠道可用的持卡人证件号码类型
     * 
     * @param rq 可用的持卡人证件号码类型查询条件
     * @return 可用的持卡人证件号码类型
     */
    AvailableCardHolderIdTypesRs availableCardHolderIdTypes(AvailableCardHolderIdTypesRq rq);

    /**
     * 请求“带卡”交易,其中"带卡"指由合作伙伴收集卡信息。
     * 
     * @param rq 交易请求参数
     * @return  一笔已向支付渠道请求支付的交易，其中包含交易请求处理结果
     */
    RequestWithCardInfoRs requestWithCardInfo(RequestWithCardInfoRq rq);

    /**
     * 请求“无卡”交易，其中“无卡”指由渠道商收集卡信息。
     * 
     * @param rq 交易请求参数
     * @return  一笔已向支付渠道请求支付的交易，其中含有一个与交易有关的URL
     */
    RequestWithoutCardInfoRs requestWithoutCardInfo(RequestWithoutCardInfoRq rq);
    
    ListTransactionsRs listTransactions(ListTransactionsRq rq);

    /**
     * 根据原交易流水号复制一笔新的交易状态为撤销的新交易
     * @param rq
     * @return
     */
	RequestWithoutCardInfoRs requestForCancelTransaction(CancelTransactionRq rq);
	RequestWithoutCardInfoRs requestWithoutCardInfoForBill99Pos(
			RequestWithoutCardInfoRq rq);
}
