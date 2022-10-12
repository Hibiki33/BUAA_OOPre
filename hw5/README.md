## 第五次作业指导书

### 第一部分：训练目标

 学习正则表达式捕获组和模糊匹配，非贪婪匹配等知识，并依此实现更加高级的检索功能。

------

### 第二部分：预备知识

 本次作业的完成可能需要用到的知识如下。

#### 捕获组

- 关于正则表达式的捕获：当我们想要获取正则表达式里的某些指定的内容时，可以利用**捕获组**来进行操作，通过group()方法捕获想要的内容。

 详细内容可以[参考](https://www.runoob.com/w3cnote/java-capture-group.html)

#### 贪婪匹配和非贪婪匹配

- 关于模糊匹配和非贪婪匹配模式：对于表达式中间可能出现的无关紧要的部分，我们通常采用模糊匹配来进行处理。同时，由于性能原因，我们建议大家使用**非贪婪模式**进行匹配。

  我们通常使用`.*?`来表示非贪婪模式，其中`.`表示任意字符，`*?`表示重复任意次，但尽可能少重复。顾名思义，非贪婪模式就是在能使整个匹配成功的前提下匹配最少的字符，比如如果将`a.*?b`应用在`aabab`中，我们匹配到的会是`aab`和`ab`。

- 关于非贪婪匹配模式还有一些其他表示方式可供参考：

| 代码   | 说明                            |
| ------ | ------------------------------- |
| *？    | 重复任意次，但尽可能少重复      |
| +？    | 重复1次或更多次，但尽可能少重复 |
| ？？   | 重复0次或1次，但尽可能少重复    |
| {n,m}? | 重复n到m次，但尽可能少重复      |
| {n,}?  | 重复n次以上，但尽可能少重复     |

相关详细内容可以参考 [参考资料1](https://www.runoob.com/java/java-regular-expressions.html) [参考资料2](https://www.jb51.net/article/183106.htm)

------

### 第三部分：基本概念

| 概念   | 定义                                                         |
| ------ | ------------------------------------------------------------ |
| 子序列 | 将字符串中零个或多个字符去掉后所得结果，一个字符串 abc 的子序列有 a, b, c, ab, ac, bc, abc |
| 子串   | 字符串中任意个连续的字符组成的子序列                         |
| 前缀   | 包含第0个字符的子串                                          |
| 后缀   | 包含最后一个字符的子串                                       |

------

### 第四部分：题目描述

 形式化来说，消息可能被表现成以下三种模式：

- 给个人的消息：`yyyy/MM/dd-sender@receiver :"message_content";`

  ```
  2021/07/12-student@teacher :"can i pass the exam?";
  ```

  本消息为发送给个人的消息，指定了接收者teacher。

- 群聊中的消息，指定了接收者：`yyyy/MM/dd-sender:"message_content";`，其中`message_content`内包含`@receiver`

  ```
  2021/07/12-student:"can i pass the exam?@teacher ";
  ```

  本消息为在群聊中发送的消息，指定了接收者teacher。

- 群聊中的消息，未指定接收者：`yyyy/MM/dd-sender:"message_content";`

  ```
  2021/07/12-student:"can i pass the exam?";
  ```

  本消息为在群聊中发送的消息，未指定接收者。

 有些用户喜欢在群聊或聊天中使用昵称，比如名为 “小枫”的用户可以有昵称 “小小枫”、“小枫枫”。

 用户名或备注的前缀或后缀可能含有一些特殊含义，比如“2006 张一一”、“2006 刘一一”、“王一一 19级”、“陈一一 20级”，其中的数字代表着年，隐含着当年入学或入职等信息。

 在查询时，我们同样不可能每次都能精准地记得所需消息的日期，接收方以及发送方的姓名，因此在这里我们需要实现模糊查询的功能。

 请编写程序读入消息，并根据输入的查询找出对应的消息。

 查询指令将在原有基础上作如下调整。

- `qdate`指令输入的日期可能不是完整的日期，具体地，所有可能的日期格式有`yyyy/MM/dd, yyyy/MM/, yyyy//dd, /MM/dd, //dd, /MM/, yyyy//`，你需要输出所有符合输入日期的消息。例如若输入`qdate /07/03`，则你需要输出所有在07/03发布的消息，无论消息在哪一年发出。其余格式同理。
- `qsend`和`qrecv`将加入参数`-v`表示模糊搜索，类似于我们的命令行，当输入`qsend -v`和`qrecv -v`时表示对后面输入的内容进行模糊搜索，**该次作业中，我们规定，输入 `qsend -v "str"`或`qrecv -v "str"`，表示检索以`str`为子串的名称，具体详见样例。当`-v`缺省时表示含义与第一次作业相同，即”精确匹配“。**

------

### 第五部分：输入/输出说明

#### 输入格式

 前若干行为消息内容，以一行END_OF_MESSAGE结尾。其中一行内可能有多条消息，每条消息之间和每行末尾可能存在若干空白字符（空格和制表符\t）。

 其后为多条查询语句，每行一条。

 查询指令有且仅有`qdate,qrecv,qsend`，格式参照上述。

#### 输出格式

 对于每一条询问，输出指定消息（输入数据中可能存在多条消息符合条件，此时按照**原顺序、原格式**输出全部符合条件的消息）。

输出中每条消息均单独占据一行。

#### 输入样例1

```markdown
2021/7/1-Jack@JayChou :"Hello! mydear.";2021/7/3-JayChou@buaaer :"nice to meet you.";
2021/7/5-JayChou@Mike :"emmmm..., I am sorry.";
2021/7/6-JayChou@Mike :"emmmm..., I do not want to talk to you.";         2020/7/8-JayChou@buaaer :"Hahaha! see you again";
2020/7/8-JayChou@Sabbaty :"Hahaha! Sabbaty, come to the center!";
2021/6/8-JayChou:"I am JayChou!";
2021/7/11-JayChou:"I love you!";
2021/1/1-JayChou:"Hi@c ";
END_OF_MESSAGE
qsend "Jack"
qsend -v "Ja"
qrecv "buaaer"
qrecv -v "e"
```

#### 输出样例1

```markdown
2021/7/1-Jack@JayChou :"Hello! mydear.";
2021/7/1-Jack@JayChou :"Hello! mydear.";
2021/7/3-JayChou@buaaer :"nice to meet you.";
2021/7/5-JayChou@Mike :"emmmm..., I am sorry.";
2021/7/6-JayChou@Mike :"emmmm..., I do not want to talk to you.";         
2020/7/8-JayChou@buaaer :"Hahaha! see you again";
2020/7/8-JayChou@Sabbaty :"Hahaha! Sabbaty, come to the center!";
2021/6/8-JayChou:"I am JayChou!";
2021/7/11-JayChou:"I love you!";
2021/1/1-JayChou:"Hi@c ";
2021/7/3-JayChou@buaaer :"nice to meet you.";
2020/7/8-JayChou@buaaer :"Hahaha! see you again";
2021/7/3-JayChou@buaaer :"nice to meet you.";
2021/7/5-JayChou@Mike :"emmmm..., I am sorry.";
2021/7/6-JayChou@Mike :"emmmm..., I do not want to talk to you."; 
2020/7/8-JayChou@buaaer :"Hahaha! see you again";
```

#### 输入样例2

```markdown
2021/7/1-Jack@JayChou :"Hello!";2021/7/3-JayChou@buaaer :"Hahaha";
2021/7/5-JayChou@Mike :"emmmm";         2020/7/8-JayChou@buaaer :"Hahaha";
2021/6/8-JayChou:"Hahaha";
END_OF_MESSAGE
qdate 2021/7/1
qdate 2021//
qdate /7/
qdate //1
qdate 2021/7/
qdate /7/1
```

#### 输出样例2

```markdown
2021/7/1-Jack@JayChou :"Hello!";
2021/7/1-Jack@JayChou :"Hello!";
2021/7/3-JayChou@buaaer :"Hahaha";
2021/7/5-JayChou@Mike :"emmmm";
2021/6/8-JayChou:"Hahaha";
2021/7/1-Jack@JayChou :"Hello!";
2021/7/3-JayChou@buaaer :"Hahaha";
2021/7/5-JayChou@Mike :"emmmm";
2020/7/8-JayChou@buaaer :"Hahaha";
2021/7/1-Jack@JayChou :"Hello!";
2021/7/1-Jack@JayChou :"Hello!";
2021/7/3-JayChou@buaaer :"Hahaha";
2021/7/5-JayChou@Mike :"emmmm"; 
2021/7/1-Jack@JayChou :"Hello!";
```

#### 输入样例3

```markdown
2022/07/04-Jack:"I have a leading zero.";
2022/7/4-Jack:"I dont have leading zero";
2022/7/04-Jack:"We have the same meaning!";
END_OF_MESSAGE
qdate 2022//4
qdate /07/04
```

#### 输出样例3

```markdown
2022/07/04-Jack:"I have a leading zero.";
2022/7/4-Jack:"I dont have leading zero";
2022/7/04-Jack:"We have the same meaning!";
2022/07/04-Jack:"I have a leading zero.";
2022/7/4-Jack:"I dont have leading zero";
2022/7/04-Jack:"We have the same meaning!";
```

------

### 第六部分：限制说明

- **一行输入中可能包含多条消息，但一条消息只会完整地出现在一行内**。
- 每条消息之间和每行末尾可能存在若干空白字符作为分隔（空格和制表符\t），也可能不存在。
- 保证所有的消息和指令符合格式。
- 保证输入的日期、用户名、正文都非空。
- 对于日期，保证year \isin [0, 9999], month \isin [1, 12], day \isin [1,31]*y**e**a**r*∈[0,9999],*m**o**n**t**h*∈[1,12],*d**a**y*∈[1,31]。日期中可能存在前导`0`，比如`1`月**可以**表示为`01`月，`258`年**可以**表示为`0258`年，且保证日期合法。以及包括前导0在内，年份的位数不超过4位，月、日的位数不超过两位**（这条与上次作业不同）**
- `qdate`指令保证输入的日期中年月日三个参数不同时缺省
- 发送者和接收者的用户名仅由**大小写英文字母、数字**组成。
- 正文内容仅由**大小写英文字母、数字、空格、四种标点符号（? ! , .）构成**。
- 输入数据中所有内容均对大小写**敏感**。
- 如果一条消息中存在@用户的情况（对应前两种消息模式），则保证该信息中`@+用户名`结构后面一定**有一个空格**，而且@用户最多只会在一个消息中出现一次。
- 输入数据不超过300行
- 输入数据每行不超过10个消息
- 输入数据总询问数不超过100条

------

### 第七部分：提示与警示

#### 提示

 日期的前导0并不影响日期本身的含义，查询时请按照**日期本身的语义**进行查询。具体可见我们给出的样例。

#### 警示

不要试图Hack评测机，不要抄袭。如发现其他人的代码疑似存在上述行为，可向课程组举报。课程组感谢同学们为课程建设所作出的贡献。