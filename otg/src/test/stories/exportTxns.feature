Feature: 将交易信息导出到excel中

In order to 方便交易统计
As a 运营管理员
I want to 导出交易信息到excel中

@ExportTxnSteps
Scenario: 固定导出一条交易到excel中

Given 一笔交易，流水号为201012120001
When 导出交易
Then excel中有且仅有一条数据,如下
| 订单号 	|对接伙伴	| 交易流水号		| 金额		|状态	|请求时间	|完成时间	|渠道		|核对状态	|
| DMS12456 	|移动		| 201012120001	| 100.00	|已完成	|2011-04-29	|2011-04-29	|支付宝B2C	|VALID		|																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																						
