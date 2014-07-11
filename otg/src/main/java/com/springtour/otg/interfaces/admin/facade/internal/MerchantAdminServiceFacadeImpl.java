/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.internal;

import com.springtour.otg.application.MerchantAdminService;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.merchant.MerchantRepository;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.interfaces.admin.facade.MerchantAdminServiceFacade;
import com.springtour.otg.interfaces.admin.facade.dto.MerchantDto;
import com.springtour.otg.interfaces.admin.facade.internal.assembler.MerchantDtoAssembler;
import com.springtour.otg.interfaces.admin.facade.rq.FindMerchantRq;
import com.springtour.otg.interfaces.admin.facade.rq.ListMerchantsRq;
import com.springtour.otg.interfaces.admin.facade.rq.RegisterMerchantRq;
import com.springtour.otg.interfaces.admin.facade.rq.UpdateMerchantInfoRq;
import com.springtour.otg.interfaces.admin.facade.rs.FindMerchantRs;
import com.springtour.otg.interfaces.admin.facade.rs.ListMerchantsRs;
import com.springtour.otg.interfaces.admin.facade.rs.RegisterMerchantRs;
import com.springtour.otg.interfaces.admin.facade.rs.UpdateMerchantInfoRs;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author 006874
 */
public class MerchantAdminServiceFacadeImpl implements MerchantAdminServiceFacade {

    @Override
    public RegisterMerchantRs register(RegisterMerchantRq rq) {
        logger.info(rq);
        try {
            merchantAdminService.register(rq.getName(), rq.getChannelId(), rq.getOrgId(), rq.getCode(),
                    rq.getKey());
            RegisterMerchantRs rs = new RegisterMerchantRs(StatusCode.SUCCESS);
            logger.info(rq);
            return rs;
        } catch (Exception e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
            return new RegisterMerchantRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    @Override
    public UpdateMerchantInfoRs updateInfo(UpdateMerchantInfoRq rq) {
        logger.info(rq);
        try {
            merchantAdminService.updateInfo(rq.getMerchantId(), rq.getName(), rq.getCode(), rq.getKey(), rq.isEnabled());
            UpdateMerchantInfoRs rs = new UpdateMerchantInfoRs(StatusCode.SUCCESS);
            logger.info(rq);
            return rs;
        } catch (Exception e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
            return new UpdateMerchantInfoRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    @Override
    public ListMerchantsRs list(ListMerchantsRq rq) {
        try {
            Long total = merchantAdminService.count(rq.getChannelId(), rq.getOrgId());
            List<Merchant> merchants = merchantAdminService.list(rq.getChannelId(), rq.getOrgId(), rq.getFirstResult(), rq.getMaxResults());
            List<MerchantDto> dtos = MerchantDtoAssembler.toDtos(merchants);
            return new ListMerchantsRs(StatusCode.SUCCESS, dtos, total);
        } catch (Exception e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
            return new ListMerchantsRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }

    }

    @Override
    public FindMerchantRs findById(FindMerchantRq rq) {
        try {
            Merchant merchant = merchantRepository.find(rq.getMerchantId());
            return new FindMerchantRs(StatusCode.SUCCESS, MerchantDtoAssembler.toDto(merchant));
        } catch (Exception e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
            return new FindMerchantRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }
    }
    private MerchantAdminService merchantAdminService;
    private MerchantRepository merchantRepository;
    private Logger logger = LoggerFactory.getLogger();

    public void setMerchantRepository(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public void setMerchantAdminService(MerchantAdminService merchantAdminService) {
        this.merchantAdminService = merchantAdminService;
    }
}
