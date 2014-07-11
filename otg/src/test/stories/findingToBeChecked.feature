Feature: 查找准备核对的交易

In order to 处理供应商已处理完毕但未收到供应商通知的交易，并验证交易是否被恶意篡改
As a 运营管理员
I want to 找到将我方的交易信息与供应商的信息进行对比

Remark:
1.我们需要一个机制来区分哪些交易需要被挑选出来准备核对，并且由于为了达到主动接收未收到的通知，整个被挑选出来的集合
应该尽可能的小以便加快处理速度和频率
2.为了尽可能缩小该交易集合，我们可以将一些“可能”还未完成付款的交易剔除在外（因为用户输入交易信息可能会花费一定的时间）
3.为了尽可能缩小该交易集合，我们可以将一些“可能”被用户放弃的交易剔除在外（因为用户可能在下单后放弃交易）
4.为了达到主动接收未收到的通知，整个过程应该尽可能频繁地运行，但对一些“可能”被用户放弃的交易则意义不大，所以我们可以减少它们核对的频率

@FindCheckableSteps
Scenario:  批处理脚本找出所有未核对的交易

Given newBornRound=3
And unlivelyRound=6
And deadRound=12
And unlivelyTurnRounds 
| 7  |
| 10 |
And intervalMinutes=5
When 现在时间=2013-02-18 16:39:30，找到如下交易
| transactionNo | whenRequested       | state             | checkingState | remark                 |
| 1             | 2013-02-18 15:12:12 | RESPONSED_FAILURE | UNCHECKED     | gt DeadRounds                     |
| 2             | 2013-02-18 15:12:25 | REQUESTED         | UNCHECKED     | gt DeadRounds                     |
| 3             | 2013-02-18 15:37:12 | REQUESTED         | UNCHECKED     | eq DeadRounds                     |
| 4             | 2013-02-18 15:57:12 | REQUESTED         | UNCHECKED     | unlively and bye round            |
| 5             | 2013-02-18 15:58:12 | RESPONSED_SUCCESS | UNCHECKED     | unlively and bye round but handled|
| 6             | 2013-02-18 16:02:12 | REQUESTED         | UNCHECKED     | unlively and take turn round      |
| 7             | 2013-02-18 16:07:12 | REQUESTED         | UNCHECKED     | eq unlively round         |
| 8             | 2013-02-18 16:12:12 | REQUESTED         | UNCHECKED     | gt new born round and lt unlively round |
| 9             | 2013-02-18 16:20:21 | REQUESTED         | UNCHECKED     | eq new born round  |
| 10            | 2013-02-18 16:28:12 | CONCLUDED         | UNCHECKED     | eq new born round but handled |
| 11            | 2013-02-18 16:30:12 | REQUESTED         | UNCHECKED     | lt new born round  |
Then 处理结果如下
| transactionNo | action                      |
| 1             | 通知该交易准备核对                              |
| 2             | 通知该交易准备认定为Dead     |
| 3             | 通知该交易准备认定为Dead     |
| 4             | 该交易被认定为unlively且轮空，跳过    |
| 5             | 通知该交易准备核对    |
| 6             | 通知该交易准备核对    |
| 7             | 该交易被认定为unlively且轮空，跳过    |
| 8             | 通知该交易准备核对    |
| 9             | 该交易被认定为new born，跳过    |
| 10            | 通知该交易准备核对    |
| 11            | 该交易被认定为new born，跳过    |
