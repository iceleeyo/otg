Feature: 将交易掉单恢复或状态置为DEAD

In order to 更新TXN为DEAD之前需要再次检验交易在第三方状态，状态为 未付款 则 更新TXN为DEAD，状态为 已付款 进行掉单恢复


@MarkingSteps
Scenario:  扣款成功，掉单

Given 一笔交易，金额CNY100，状态REQUESTED
When 核对时，从供应商得知金额为CNY100，扣款结果为扣款成功
Then 将交易认定为UNCHECKED，并通知执行恢复流程

@MarkingSteps
Scenario:  未扣款

Given 一笔交易，金额CNY100，状态REQUESTED
When 核对时，从供应商得知金额为CNY100，扣款结果为未扣款
Then 将交易认定为DEAD，状态改为DEAD