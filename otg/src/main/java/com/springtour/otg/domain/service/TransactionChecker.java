package com.springtour.otg.domain.service;

import java.util.*;

import com.springtour.otg.domain.model.transaction.*;

public interface TransactionChecker {
	/**
	 * 
	 * | 交易金额核对结果 | 交易状态 核对结果 | 核对结果 |<br>
	 * | 一致 | 已完成/交易成功 | VALID |<br>
	 * | 一致 | 已完成/未处理 | INVALID |<br>
	 * | 一致 | 已响应_扣款成功/交易成功 | VALID |<br>
	 * | 一致 | 已响应_扣款失败/交易失败 | VALID |<br>
	 * | 不同 | …… | INVALID |<br>
	 * | 一致 | 请求中/交易成功 | NOTIFICATION_LOST |<br>
	 * | 一致 | 请求中/交易失败 | NOTIFICATION_LOST |<br>
	 * | 一致 | 请求中/未处理 | UNHANDLED |<br>
	 */
	CheckResult check(Transaction transaction);

	/**
	 * 返回当前支持核对的渠道列表
	 */
	Set<String> supportedChannels();

	ToBeCheckedSpecification aToBeCheckedSpec();
}
