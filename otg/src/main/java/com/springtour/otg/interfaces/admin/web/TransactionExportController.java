package com.springtour.otg.interfaces.admin.web;

import javax.servlet.http.*;
import lombok.Setter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.springtour.otg.infrastructure.security.SecurityGateway;
import com.springtour.otg.interfaces.admin.facade.TransactionAdminServiceFacade;
import com.springtour.otg.interfaces.admin.facade.rq.ListTransactionsRq;
import com.springtour.otg.interfaces.admin.facade.rs.ListTransactionsRs;
import com.springtour.otg.interfaces.admin.web.command.ListTransactionsCommand;

public class TransactionExportController extends MultiActionController{

	private String errorCloseView = "errorClose";
	private final String transactionExportAuth = "/otg/admin/transactionExport.htm";
	private final String reportPath = "com/springtour/otg/infrastructure/excelTemplate/txnsTemplate.xls";
	@Setter
	private TransactionAdminServiceFacade transactionAdminServiceFacade;
	@Setter
	private int maxExportSize;
	@Setter
	private SecurityGateway securityGateway;
	
	@SuppressWarnings("unchecked")
	public ModelAndView exportTransactions(HttpServletRequest request,
			HttpServletResponse response, ListTransactionsCommand command) {
		// 权限验证
		if (!securityGateway.authByLinkPage(request, transactionExportAuth)) {
			return new ModelAndView("authenticationDenied");
		}
		// 不分页
		command.setPageNo(1);
		command.setPageSize(Integer.MAX_VALUE);
		ListTransactionsRs rs = transactionAdminServiceFacade
				.listTransactions(toListTransactionsRq(command));
		if (rs.getTotal() > maxExportSize) {
			request.setAttribute("message", "最大导出不能超过" + maxExportSize
					+ "条，请限制查询条件！");
			return new ModelAndView(errorCloseView);
		}
		String fileName = "transaction_" + command.getWhenRequestedGt() + "-"
				+ command.getWhenRequestedLt();
		new ExcelWriter().readAndWrite(response, reportPath, fileName, rs
				.getTransactions());

		return null;
	}
	

	private ListTransactionsRq toListTransactionsRq(
			ListTransactionsCommand command) {
		ListTransactionsRq rq = new ListTransactionsRq();
		rq.setApplicationEq(command.getApplicationEq().trim());
		rq.setChannelIdEq(command.getChannelIdEq().trim());
		int pageNo = command.getPageNo();
		int pageSize = command.getPageSize();
		rq.setFirstResult(pageSize * (pageNo - 1) + 1);
		rq.setMaxResults(pageSize);
		rq.setMerchantCodeEq(command.getMerchantCodeEq().trim());
		rq.setOrderIdEq(command.getOrderIdEq().trim());
		// rq.setOrderNoEq(command.getOrderNoEq());
		rq.setStateEq(command.getStateEq().trim());
		rq.setTransactionNoEq(command.getTransactionNoEq().trim());
		rq.setWhenRequestedGt(command.getWhenRequestedGt().trim());
		rq.setWhenRequestedLt(command.getWhenRequestedLt().trim());
		rq.setPartnerEq(command.getPartnerId().trim());
		return rq;
	}
}
