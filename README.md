## 基本思路
分为Server端和Client端
两程序相互独立

***

### 对象流传输
建立一个`DataPackage`类，使用`socket`传送的内容为`DataPackage`对象

数据包可用于**登录**，**发送消息**，**判断在线情况**

#### 对象流注意点
在网络通讯中，主机与客户端若使用`ObjectInputStream`与`ObjectOutputStream`建立对象通时，有时会发生线程阻塞问题。

这是因为当从`InputStream`创建一个`ObjectInputStream`时，需要从流中读入并验证一个`Header`，这时如果对方的`ObjectOutputStream`没有写入一个`Header`，`ObjectInputStream`的构造函数便会**阻塞（block）**。

解决这个问题的方法是调整`ObjectInputStream`与`ObjectOutputStream`的声明顺序。比如：主机端先建立`ObjectInputStream`后建立`ObjectOutputStream`，则对应地客户端要先建立`ObjectOutputStream`后建立`ObjectInputStream`


#### Client 登录
1. Client发送一个`LOGIN`类型的数据包，并用`setLogin()`函数设定用于验证的用户名和密码
2. Sever收到后，用`getUserID()`和`getUserPassword()`，并调用公共类`CheckLogin`检查用户信息是否合法
3. 返回检验结果，`setStat()`函数设定数据包作用结果。
4. 若结果通过将对应`userID`和`socket`加入到`matchList`中，若不通过则无其他操作
5. 最后将此`DataPackage`包传回Client，Client通过`getStat()`做出登录成功或失败的对应操作

*ps: 可能需要添加若登录成功，向所有在线用户广播此用户上线消息，以实现在线用户展示功能。实现方式大致构想一下：在Client端不单独开判断分支，而是当数据包的类型为`LOGIN`、`packageStat`为`LOGIN_SUCCESSFUL`、最后`userID`和本机的不同时，那么判断有新用户上线，并且对其进行展示*

#### Client 发送消息
*经过反复思考，还是决定把向离线用户发送消息的功能取消掉。感觉实现略复杂，且不实用*
1. Client发送一个`SEND_MESSAGE`类型的数据包，并调用`sendMessage`设置接收者和消息内容
2. Server收到数据包后，先提取接收者的`userID`，再到`matchList`中进行匹配得到对应的`socket`，向此`socket`写入这个数据包，由于接收端有一个线程在不断从对象流中提取数据包，所以写入后不用担心接收者接受数据的问题
3. 成功将数据包传送到接受者后，在对此数据包`setStat()`，并传回发送者，便于发送者显示消息记录等

#### 离线
//TODO