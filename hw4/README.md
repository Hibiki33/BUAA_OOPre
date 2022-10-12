## 第四次作业指导书

> 第二单元第一次作业主要训练字符串的处理能力，以及正则表达式相关知识。本单元作业同样需要同学们进行迭代，**希望同学们在动笔之前先完整阅读一遍指导书，**并在做题的同时思考如何给后续的迭代留下修改的空间，而不至于每一个新的 Task 都要重新写一份代码。

### 第一部分：训练目标

 学习正则表达式等相关知识，掌握基本的处理字符串的方式和能力，学会从字符串中提取出有用的信息，并根据信息进行检索。

------

### 第二部分：预备知识

 本次作业的完成可能需要用到的知识如下。

#### Java中String类库

 Java类库中的String类提供了很多可以帮助你完成这几个Task的辅助方法。例如：

- split方法，可以根据给定的分隔符来将字符串分割为若干个字符串；
- indexOf方法，可以在给定的字符串中搜索给定字符串出现的位置；
- substring方法，可以按照下表位置区间来从给定字符串截取子字符串;
- trim方法，可以移除给定字符串开头和结尾处的空格以及换行符。

 下面以一个简单的例子来辅助说明以上所提及的各个方法。

 假设我们已经得到了含有如下信息的字符串：

```json
  {
  "code":"iVBORw@0KGgoAAAANSU#hEUgAAAAgAAA$AECAYAAACzzX7wAAAAGUlEQVQImW%NggID/DKjgPzYOLpqwAr^xWAAAbkwv1&EmB71QAAAABJRU5*ErkJggg==",
  "key":"64",
  "type":"png"
  }
```

 现在我们想要将其中的code部分提取出来进行进一步的处理。

 我们可以通过字符串查找的方式来完成该任务：

```java
  public static String extractCode(String jsonString) {
      int start = jsonString.indexOf("\"code\"");           //找到"code"所在之处；
      int end = jsonString.indexOf("\n", start);            //从上一次搜索结果开始向后搜索换行符
      return jsonString.substring(start + 8, end - 2);      //根据以上信息截取子串并返回
  }
```

 当然，这种方法并没有对字符串进行实际的解析，仅仅是进行了提取。

 我们也可以利用split方法来完成这一任务：

```java
  public static String extractCode(String jsonString) {
      String[] records = jsonString.substring(1, jsonString.length() - 1).split(","); //将输入的字符串按照逗号分割开
      for (String record : records) {
          String[] details = record.split(":");                                       //分离每个分割出的字符串中的名称与值（按照冒号进一步分割）
          if ("\"code\"".equals(details[0])) {                                        //判断数据名称是否为"code"
              return details[1].substring(1, details[1].length() - 1);                //返回该数据对应的值。
          }
      }
      throw new RuntimeException("keyword code not found!");                          //未找到，报错
  }
```

 除此之外，String类库中提供了其他字符串相关操作，这里不多做赘述。**在各主流IDE中，你可以通过将鼠标悬停在方法名上来阅读方法的文档。这可以让你精确地了解该方法的具体作用，为熟悉java类库的使用提供很大的帮助。**这里也鼓励大家通过这种方式或其他方式来更多地了解java库的相关知识。

#### 正则表达式

- 正则表达式是一种强力的字符串结构化处理工具。正则表达式具有以字符串的形式描述一个字符串集合的能力，被频繁地用在字符串匹配等任务中。
  - 正则表达式预定义了很多字符集合：如`\d`代表所有数字字符，`\s`代表所有空白字符，`\w`代表字母、数字或下划线字符。
  - 正则表达式具有灵活的特性。`*`符号与`+`符号提供了不定次数重复功能，`{}`则可以重复指定次数；`[]`提供了构造自己的字符集合的方法，而`()`则可以将字符串进行划分。
- Java语言对正则表达式提供了多方面的支持。
- 你可以使用字符串的matches方法来检验字符串是否属于正则表达式所表示的集合；split函数中的分割符也可以填入正则表达式；
- 此外Java中还有**Pattern类**专门用来表示正则表达式。你可以使用Pattern.complie()方法将字符串转换为正则表达式。
- 由Pattern类的matcher方法构造的**Matcher类**则提供了多种多样的使用正则表达式对字符串进行匹配的方法：
  - 使用find方法可以实现在同一个字符串中使用正则表达式连续的进行匹配；
  - 使用group方法则可以从匹配中提取出与部分正则表达式相对应的子串；
  - 使用start、end方法则可以获取匹配在目标字符串中的位置信息；
  - 使用region方法还可以限定正则表达式进行匹配的范围；

 下面以一个例子来简单介绍正则表达式的使用：

 我们知道，java语言中存在着“包”这一概念。使用package关键字可以轻松的将java程序划分为不同的包，并建立起一套层次结构。

 一般来说，包会使用倒置的域名作为其名字，如`com.oocourse.spec1.io`, `org.apache.catalina`, `com.xiaoming.log`等；

 给定一个带有package关键字的java文件的内容(保证包名仅大由小写字母、数字、下划线构成)，请你输出该java文件所在的包以及该包的二级域名。

 首先，写出package语句所对应的正则表达式如下：

 `^package \w+(\.\w+)+;`

 其中+表示至少匹配1次;^表示匹配行首。

 得到了对应的表达式，我们就可以对字符串进行匹配。具体的程序如下：

```java
    public static final String PACKAEG_PATTERN = "^package \\w+(\\.\\w+)+";

    public static String getSecondDomain(String javaFileContent) {
        Pattern pattern = Pattern.compile(PACKAEG_PATTERN);         //将定义好的正则表达式字符串转换为正则表达式对象
        Matcher matcher = pattern.matcher(javaFileContent);         //将正则表达式结合到输入数据上
        if (!matcher.find()) {                                      //尝试进行匹配
            throw new RuntimeException("Invalid input!");           //失配，报错
        }
        String[] urlParts = matcher.group()                         //匹配成功，获得匹配到的字符串
                            .split("\\.");                          //对匹配进行切分。注意split方法的参数也是正则表达式
        if (urlParts.length < 2) {
            System.err.println("package name's too simple!");
        }
        return urlParts[1];
    }
```

 值得注意的是，为了简化处理流程，该程序中的正则表达式并没有使用分号；但是由于正则表达式默认的**非贪婪匹配**特性，不会出现包名没有完全匹配到的结果。

 此外，本任务也可以使用功能更强大的*捕获组*来完成，这里不多做赘述。同样地，这里鼓励大家自行去搜集指导书以外的资料去获取正则表达式的相关知识。

------

### 第三部分：题目描述

 小明发现在此聊天软件的群聊功能中，如果不是发给某个特定的人的消息，**群聊**消息中不会注明消息的接收者。

 因为很多用户希望在**群聊**中让某位用户特别注意到某消息，所以他们喜欢在群聊中发送@某人的信息，这些信息会被系统识别为群聊中发送给相应用户的消息，会提醒被所@的人注意到。

形式化来说，消息可能被表现成以下三种模式：

- 给个人的消息：`yyyy/MM/dd-{sender}@{receiver }:"{message_content}";`

```
  2021/07/12-student@teacher :"can i pass the exam?";
```

本消息为发送给个人的消息，指定了接收者teacher。

- 群聊中的消息，指定了接收者：`yyyy/MM/dd-{sender}:"{message_content}";`，其中`message_content`内包含`@{receiver }`

```
  2021/07/12-student:"can i pass the exam?@teacher ";
```

本消息为在群聊中发送的消息，指定了接收者teacher。

- 群聊中的消息，未指定接收者：`yyyy/MM/dd-{sender}:"{message_content}";`

```
  2021/07/12-student:"can i pass the exam?";
```

本消息为在群聊中发送的消息，未指定接收者。

输入是具有上述模式的聊天消息，并以指定的形式输入多条询问。

请编写程序，根据输入的查询输出指定发送者、接收者、日期的消息。

------

### 第四部分：输入/输出说明

#### 输入格式

 前若干行为消息内容，以一行END_OF_MESSAGE结尾。其中一行内可能有多条消息，每条消息之间和每行末尾可能存在若干空白字符（空格和制表符\t）。

 其后为多条询问，所有可能出现的询问格式如下：

- `qdate year/month/day` : 查询某日期的消息
- `qsend "username"` : 查询某用户名发送的消息
- `qrecv "username"` : 查询某用户名接收的消息。请注意，**所有“群聊中@某用户的消息”均算作该用户接收的消息，即接受的消息包括：私聊该用户的消息和群聊中@某用户的消息**。

#### 输出格式

 对于每一条询问，输出指定消息（输入数据中可能存在多条消息符合条件，此时按照**原顺序、原格式**输出全部符合条件的消息）。

 输出中每条消息均单独占据一行。

#### 输入样例1

```
2021/7/1-Jack@JayChou :"Hello!";2021/7/3-JayChou@buaaer :"Hahaha";
2021/7/5-JayChou@Mike :"emmmm";         2021/7/8-JayChou@buaaer :"Hahaha";
2021/7/8-JayChou:"Hahaha"; 2021/5/3-Mike:"he@buaaer is unhappy";
END_OF_MESSAGE
qdate 2021/7/1
qsend "JayChou"
qrecv "buaaer"
```

#### 输出样例1

```
2021/7/1-Jack@JayChou :"Hello!";
2021/7/3-JayChou@buaaer :"Hahaha";
2021/7/5-JayChou@Mike :"emmmm";
2021/7/8-JayChou@buaaer :"Hahaha";
2021/7/8-JayChou:"Hahaha";
2021/7/3-JayChou@buaaer :"Hahaha";
2021/7/8-JayChou@buaaer :"Hahaha";
2021/5/3-Mike:"he@buaaer is unhappy";
```

#### 输入样例2

```
0233/2/0-Mike:"he@buaaer is happy";
0233/2/31-Mike:"he@buaaer is very happy";
233/2/0-Mike:"he@buaaer is very very happy";
END_OF_MESSAGE
qdate 233/02/0
```

#### 输出样例2

```
0233/2/0-Mike:"he@buaaer is happy";
233/2/0-Mike:"he@buaaer is very very happy";
```

------

### 第五部分：限制说明

- **一行输入中可能包含多条消息，但一条消息只会完整地出现在一行内**。
- 每条消息之间和每行末尾可能存在若干空白字符作为分隔（空格和制表符\t），也可能不存在。
- 保证所有的消息和指令符合格式。
- 保证输入的日期、用户名、正文都非空。
- 日期**仅**以 **year/month/day** 形式给出，year \isin [0, 9999], mounth \isin [1, 12], day \isin [0,31]*y**e**a**r*∈[0,9999],*m**o**u**n**t**h*∈[1,12],*d**a**y*∈[0,31]。日期中可能存在前导`0`，比如`1`月**可以**表示为`01`月，`258`年**可以**表示为`0258`年。且保证合法，以及包括前导0在内，年份的位数不超过4位，月、日的位数不超过两位
- 发送者和接收者的用户名仅由**大小写英文字母、数字**组成。
- 正文内容仅由**大小写英文字母、数字、空格、四种标点符号（? ! , .）构成**。
- 输入数据中所有内容均对大小写**敏感**。
- 如果一条消息中存在@用户的情况（对应前两种消息模式），则保证该信息中`@+用户名`结构后面一定**有一个空格**，而且@用户最多只会在一个消息中出现一次。
- 不超过300行
- 每行不超过10个消息
- 总询问数不超过100条

------

### 第六部分：警示

不要试图Hack评测机，不要抄袭。如发现其他人的代码疑似存在上述行为，可向课程组举报。课程组感谢同学们为课程建设所作出的贡献。