Feature: 核对交易信息

In order to 处理供应商已处理完毕但未收到供应商通知的交易，并验证交易是否被恶意篡改
As a 运营管理员
I want to 找到所有未和供应商核对的交易并核对其核心信息

Remark:
1.我们需要一个机制来区分哪些交易需要被挑选出来准备核对，并且由于为了达到主动接收未收到的通知，整个被挑选出来的集合
应该尽可能的小以便加快处理速度和频率
2.为了尽可能缩小该交易集合，我们可以将一些“可能”还未完成付款的交易剔除在外（因为用户输入交易信息可能会花费一定的时间）
3.为了尽可能缩小该交易集合，我们可以将一些“可能”被用户放弃的交易剔除在外（因为用户可能在下单后放弃交易）
4.为了达到主动接收未收到的通知，整个过程应该尽可能频繁地运行，但对一些“可能”被用户放弃的交易则意义不大，所以我们可以减少它们核对的频率

@CheckingSteps
Scenario:  扣款成功，金额、状态相同

Given 一笔交易，金额为CNY100，状态为CONCLUDED
When 核对时，从供应商得知其金额为CNY100，扣款结果为扣款成功
Then 将该交易认定为VALID，完毕

@CheckingSteps
Scenario:  扣款成功，金额、状态相同

Given 一笔交易，金额为CNY100，状态为RESPONSED_SUCCESS
When 核对时，从供应商得知其金额为CNY100，扣款结果为扣款成功
Then 将该交易认定为VALID，完毕

@CheckingSteps
Scenario:  扣款失败，金额、状态相同

Given 一笔交易，金额为CNY100，状态为RESPONSED_FAILURE
When 核对时，从供应商得知其金额为CNY100，扣款结果为扣款失败
Then 将该交易认定为VALID，完毕

@CheckingSteps
Scenario:  扣款成功，金额相同、状态不符

Given 一笔交易，金额为CNY100，状态为RESPONSED_FAILURE
When 核对时，从供应商得知其金额为CNY100，扣款结果为扣款成功
Then 将该交易认定为INVALID，并通知人工处理流程

@CheckingSteps
Scenario:  扣款成功，金额不符、状态相同

Given 一笔交易，金额为CNY100，状态为CONCLUDED
When 核对时，从供应商得知其金额为USD100，扣款结果为扣款成功
Then 将该交易认定为INVALID，并通知人工处理流程

@CheckingSteps
Scenario:  未扣款，金额、状态相同

Given 一笔交易，金额为CNY100，状态为REQUESTED
When 核对时，从供应商得知其金额为CNY100，扣款结果为未扣款
Then 将该交易认定为UNCHECKED，完毕

@CheckingSteps
Scenario:  扣款成功，掉单

Given 一笔交易，金额为CNY100，状态为REQUESTED
When 核对时，从供应商得知其金额为CNY100，扣款结果为扣款成功
Then 将该交易认定为UNCHECKED，并通知执行恢复流程