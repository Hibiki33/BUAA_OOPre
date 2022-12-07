## 第六次作业指导书

### 第一部分：训练目标

 继续利用正则表达式的知识实现更加高级的检索功能。并学会简单的异常处理。

------

### 第二部分：预备知识

 本次作业没有新增的可能使用的知识。

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

 模糊查询只能查询子串显然是不够的，我们需要更加强大的模糊查询功能。

 同时，当下网络环境纷繁错杂，发送的信息中可能包含敏感词汇，为了响应净网行动，我们的程序需要提供关键字屏蔽功能。

 群聊消息格式同前两次作业。

 请编写程序读入消息，并根据输入的查询找出指定了接收者/发送者并且该接收者/发送者的用户名包含指定前缀、后缀、子串或子序列的消息。

 指令相较于上一次作业的改动如下。

- `qsend`和`qrecv`的模糊查询模式新增四个参数`-ssq, -ssr, -pre, -pos`，从左到右分别表示：查询以后续输入字符串为子序列(subsquence)，子串(substring)，前缀(prefix)，后缀(postfix)的发送者/接收者名称的消息。即现在上述两条指令的完整形式为`qsend [-v] [param] "str"`和`qrecv [-v] [param] "str"`。其中`param`为上述四个参数中的一个。当`param`缺省而`-v`存在时，表示含义与`param=-ssr`且`-v`存在时一致。
- **所有指令**新增参数`-c "str"`(-c: clean)表示将所有该指令输出消息中的**消息内容**中的子串`str`（引号是指令格式要求，并非`str`内的内容）改为由字符'*'组成的字符串后再输出，其长度与被修改的字符串长度相同。**该参数永远是所有指令的最后一个参数**。**且子串匹配优先级为从左到右**（具体解释见样例）
- 指令参数之间的顺序关系见后续限制说明部分以及样例。

 这里我们还需要大家实现对指令格式的检查。格式错误的指令只需按照指导书要求输出即可，无需将错误指令执行，具体如下

- `qdate`指令的日期若不符合现实中的日期表示，则需要输出`Command Error!: Wrong Date Format! "cmd"`，其中`cmd`为出错指令。
- `qsend`和`qrecv`中，新增的四个参数只适用于模糊匹配，所以，若`-ssq, -ssr, -pre, -pos`参数在`-v`参数之前出现，或`-v`参数未出现而这四个参数出现了，则需要输出`Command Error!: Not Vague Query! "cmd"`，其中`cmd`为出错指令。
- 显然一条指令至多只有一种错误

------

### 第五部分：输入/输出说明

#### 输入格式

 前若干行为消息内容，以一行END_OF_MESSAGE结尾。其中一行内可能有多条消息，每条消息之间和每行末尾可能存在若干空白字符（空格和制表符\t）。

 其后为多条查询语句，每行一条。

 **正确的**查询指令、参数及格式参考上面。

#### 输出格式

 对于每一条询问，输出指定消息（输入数据中可能存在多条消息符合条件，此时按照**原顺序、原格式**输出全部符合条件的消息）。

 输出中每条消息均单独占据一行。

#### 输入样例

```markdown
2023/12/23-timetraveler:"I am from future!!!";
2022/8/3-fishlifehh:"I am lying flat@kasumi . too tired to tap.";
2021/3/12-meloneater:"@timetraveler so what is happen, maybe I should say what will happen in the future?";
1999/12/31-earthwarrior:"so, do we win?"; 1999/12/31-militaryleader:"hey! @earthwarrior , you should abide by the agreement!"; 1999/12/31-earthwarrior@militaryleader :"aaah! I forget it.";
2022/3/23-ooer:"why you crashed again?";
2022/6/4-urgenter:"hurry! the next class will start immediately.";
0257/5/3-ancienter:"you mean this is the prophecy of a prophet.";
0257/5/4-ancientress@ancienter :"yes, and i dont know why we speak english.";
END_OF_MESSAGE
qdate 2022/2/30
qdate /13/
qsend -v -pos "er"
qrecv -pos "or"
qsend "militaryleader" -c "agreement"
qrecv -v -pre "an" -c "english"
qsend "earthwarrior" -c "aa"
```

#### 输出样例

```txt
Command Error!: Wrong Date Format! "qdate 2022/2/30"
Command Error!: Wrong Date Format! "qdate /13/"
2023/12/23-timetraveler:"I am from future!!!";
2021/3/12-meloneater:"@timetraveler so what is happen, maybe I should say what will happen in the future?";
1999/12/31-militaryleader:"hey! @earthwarrior , you should abide by the agreement!";
2022/3/23-ooer:"why you crashed again?";
2022/6/4-urgenter:"hurry! the next class will start immediately.";
0257/5/3-ancienter:"you mean this is the prophecy of a prophet.";
Command Error!: Not Vague Query! "qrecv -pos "or""
1999/12/31-militaryleader:"hey! @earthwarrior , you should abide by the *********!";
0257/5/4-ancientress@ancienter :"yes, and i dont know why we speak *******.";
1999/12/31-earthwarrior:"so, do we win?";
1999/12/31-earthwarrior@militaryleader :"**ah! I forget it.";
```

#### 样例解释

 对于最后一条指令，忽略`-c`参数时的输出如下：

```txt
1999/12/31-earthwarrior:"so, do we win?";
1999/12/31-earthwarrior@militaryleader:"aaah! I forget it.";
```

 考虑`-c`第一条消息的内容不以`aa`为子串，而第二条消息的内容中存在子串`aa`，根据从左往右的匹配规则，将前两个`aa`替换成`**`后输出。

 若第二条消息的原版是`1999/12/31-earthwarrior@militaryleader:"aaaah! I forget it.";`，则根据从左到右的匹配规则，会先匹配到前两个`aa`并将其替换成`**`，然后匹配到后两个`aa`并替换成`**`，最终输出的是`1999/12/31-earthwarrior@militaryleader:"****h! I forget it.";`

------

### 第六部分：限制说明

- **一行输入中可能包含多条消息，但一条消息只会完整地出现在一行内**。

- 每条消息之间和每行末尾可能存在若干空白字符作为分隔（空格和制表符\t），也可能不存在。

- 保证所有的消息符合格式，指令错误只涉及上面谈到的那些类型。**（这条与上次作业不同）**

- 保证输入的日期、用户名、正文都非空。

- 对于日期，保证year \isin [1, 9999], month \isin [0, 99], day \isin [0,99]*y**e**a**r*∈[1,9999],*m**o**n**t**h*∈[0,99],*d**a**y*∈[0,99]。日期中可能存在前导`0`，比如`1`月**可以**表示为`01`月，`258`年**可以**表示为`0258`年。且包括前导0在内，年份的位数不超过4位，月、日的位数不超过两位**（这条与上次作业不同）**

- 发送者和接收者的用户名仅由**大小写英文字母、数字**组成。

- `qdate`指令保证输入的日期中年月日三个参数不同时缺省

- `qsend`和`qrecv`指令中，保证`-ssq, -ssr, -pre, -pos`四个参数至多出现其中一个

- 正文内容仅由**大小写英文字母、数字、空格、四种标点符号（? ! , .）构成**。

- 输入数据中所有内容均对大小写**敏感**。

- 如果一条消息中存在@用户的情况（对应前两种消息模式），则保证该信息中`@+用户名`结构后面一定**有一个空格**。而且@用户最多只会在一个消息中出现一次。

- 如果有`-c`参数的话，其一定是所有指令的最后一个参数。

- 指令涉及到的异常类型一定是上面提到的几个类型之一

- 日期格式异常只需考虑月，日大小以及平闰年，无需考虑1582年的10月份少了10天等类似的非常偏门的错误。

- 当日期发生参数缺省时，只需考虑已给定参数的合法性

  ，具体而言。若在固定已有参数值的情况下，存在缺省参数的一组取值，使得日期合法，则认为该日期参数合法。

  - 例子1：指令`qdate /2/29`合法，因为2月可以有29日，且当年份为闰年时，整个日期合法，所以该日期参数合法
  - 例子2：指令`qdate /9/31`非法，因为9月不可能有31日，这与缺省的年份无关。
  - 例子3：指令`qdate 2022//31`合法，因为存在有31号的月份。
  - 例子4：指令`qdate //32`非法，因为不存在有32号的月份。这与缺省的年、月份无关
  - 例子5：指令`qdate /13/`非法，因为不存在有13月的年份，这与缺省的年、日无关
  - 例子6：指令`qdate //0`非法，因为不存在0号。

- 输入数据不超过300行

- 输入数据每行不超过10个消息

- 输入数据总询问数不超过100条

------

### 第七部分：提示与警示

#### 提示

 日期的前导0并不影响日期本身的含义，查询时请按照**日期本身的语义**进行查询。