package com.springtour.otg.interfaces.transacting.facade;

import javax.jws.WebService;

import com.springtour.otg.interfaces.transacting.facade.arg.FindMerchantArgument;
import com.springtour.otg.interfaces.transacting.facade.arg.NextTransactionNoArgument;
import com.springtour.otg.interfaces.transacting.facade.result.FindMerchantResult;
import com.springtour.otg.interfaces.transacting.facade.result.NextTransactionNoResult;

/**
 * 交易接口<br>
 * 该接口提供交易相关服务，如获取可用网关、获取可用的持卡人证件类型、“带卡”交易请求和“无卡”交易请求等。
 * <br> 访问地址：http://192.168.1.13:7001/remote/otg/transactingServiceFacade.htm
 * @author Hippoom
 * @deprecated 
 */
@WebService
public interface TransactingServiceFacade {

    /**
     * @deprecated 查找商户
     * 
     * @param arg
     * @return
     */
    FindMerchantResult findMerchant(FindMerchantArgument arg);
}
