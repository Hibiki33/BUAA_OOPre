## 第七次作业指导书

### 第一部分：训练目标

本次作业是针对递归下降算法解析字符串的练习，通过此次作业希望同学们可以了解一种解析“结构性字符串”的方法，从而更好的衔接下学期OO课程的第一单元作业。

------

### 第二部分：题目背景

小明同学对于网络爬虫十分感兴趣，某天他爬取了twitter网站的数据，数据以jsonline格式返回，每一行jsonline数据包含了一条推特文章的id，转发数，点赞数等等许多信息。小明希望从jsonline数据中挖掘出这些信息，但是面对这么多条jsonline数据，小明感到十分发愁，头发越来越少。希望你能帮其编写一个Java程序来解析这些jsonline格式的数据，然后进一步获得这些jsonline数据中隐藏的结果。

------

### 第三部分 Json及Jsonline的介绍

首先我们先简单了解一下json，以便更好的理解这次作业。Json是基于 JavaScript的一个子集，是一种开放的、轻量级的数据交换格式，采用独立于编程语言的文本格式来存储和表示数据，易于程序员阅读与编写，同时也易于计算机解析和生成，通常用于在 Web 客户端（浏览器）与 Web 服务器端之间传递数据。

下面给出了一个json格式的数据例子。

```json
{
    "object_type": "TweetRaw",
    "download_datetime": "2022-07-06T17:04:48.030971+08:00",
    "raw_value": {
        "created_at": "Sat Dec 04 17:16:55 +0000 2021",
        "id": 1467181131464609792,
        "id_str": "1467181131464609792",
        "full_text": "there are some contents",
        "display_text_range": [
            0,
            183
        ],
        "entities": {
            "urls": [
                {
                    "url": "https://t.co/AQNqsGsPsx",
                    "expanded_url": "https://rpc.flashbots.net",
                    "display_url": "rpc.flashbots.net",
                    "indices": [
                        106,
                        129
                    ]
                },
                {
                    "url": "https://t.co/uY0UwIC7lp",
                    "expanded_url": "https://docs.flashbots.net/flashbots-protect/rpc/quick-start#how-to-use-flashbots-protect-rpc-in-metamask",
                    "display_url": "docs.flashbots.net/flashbots-prot…",
                    "indices": [
                        160,
                        183
                    ]
                }
            ]
        },
        "user_id": 1406813839627153408,
        "user_id_str": "1406813839627153408",
        "retweet_count": 1,
        "favorite_count": 2,
        "reply_count": 1,
        "quote_count": 0,
        "favorited": true,
        "possibly_sensitive_editable": false,
        "lang": "zh",
        "supplemental_language": null,
        "self_thread": {
            "id": 1467180910961643520,
            "id_str": "1467180910961643520"
        }
    }
}
```

通过上面这个例子，我们可以更好的理解Json的数据格式，Json的格式约定比较简单，易于理解，包括以下几点。

1. **对象**：Json中用大括号 **{ }** 保存对象，对象可以包含多个 **key/value（键/值）**对。

在上面的例子中，整个json用一对大括号 **{ }**包裹，所以整个json其实是一个json对象。同时"raw_value"、 "entities"、"self_thread"这些**键**对应的**值**也都是对象。

1. **对象包含的数据存储在`键：值`对中**

- 值（value）可以是双引号括起来的字符串（string）、数值(number)、true、false、 null、**对象**（object）或者数组（array）。
- 键：一个字符串。
- 值和名称间用冒号`:`分割

Json采用`名称:值`，这种键值对的形式来存储数据，冒号右侧的值可以是多种数据类型，就比如上面给出的例子中的"object_type"、"id"、"favorited"、"supplemental_language"、"raw_value"、 "display_text_range"这些属性，他们所对应的值分别是字符串、数值、boolean(true、false)、 null、对象和数组（array）。

1. **数据由逗号`,`分隔**

Json中数据用逗号`,`分割，从上面的例子可以看出，**对象**中的键值对用逗号分隔，但对象中的最后一个键值对后面没有逗号。同时**数组**中的数据也用逗号分隔，这一点与我们学习的其他语言相同。

1. **数组：中括号 `[ ]` 保存数组，数组可以包含多个对象,当然也可以包含我们常见的字符串、数值等数据类型。**

JSON 中数组值必须是合法的 JSON 数据类型（字符串, 数字, 对象, 数组, 布尔值或 null）。比如在上面的例子中，"display_text_range"属性对应的值存储了数字，"urls"属性对应的值存储了2个对象。

了解了Json数据结构后，Jsonline格式的数据顾名思义，就是把Json数据放在一行，作为一行字符串，便于进行数据交换。比如将上面这个Json例子的数据转换为Jsonline数据，即为：

```
{"object_type":"TweetRaw","download_datetime":"2022-07-06T17:04:48.030971+08:00","raw_value":{"created_at":"Sat Dec 04 17:16:55 +0000 2021","id":1467181131464609792,"id_str":"1467181131464609792","full_text":"there are some contents","display_text_range":[0,183],"entities":{"urls":[{"url":"https://t.co/AQNqsGsPsx","expanded_url":"https://rpc.flashbots.net","display_url":"rpc.flashbots.net","indices":[106,129]},{"url":"https://t.co/uY0UwIC7lp","expanded_url":"https://docs.flashbots.net/flashbots-protect/rpc/quick-start#how-to-use-flashbots-protect-rpc-in-metamask","display_url":"docs.flashbots.net/flashbots-prot…","indices":[160,183]}]},"user_id":1406813839627153408,"user_id_str":"1406813839627153408","retweet_count":1,"favorite_count":2,"reply_count":1,"quote_count":0,"favorited":true,"possibly_sensitive_editable":false,"lang":"zh","supplemental_language":null,"self_thread":{"id":1467180910961643520,"id_str":"1467180910961643520"}}}
```

------

### 第五部分：题目描述

小明在爬取数据时提前进行了设置，因此爬取到的Jsonline数据有固定的格式和固定的键值对,在这里将jsonline转换为json格式，向同学们介绍。一个爬取到的数据例子如下：

```json
{
    "object_type": "TweetRaw",
    "download_datetime": "2022-07-06 08:00", 
    "raw_value": {
        "created_at": "Sat Dec 04 17:16:55 2021",
        "id": 1467181131464609792,
        "full_text": "this is a retweet_count content",
        "user_id": 1406813839627153408,
        "retweet_count": 1,
        "favorite_count": 2,
        "reply_count": 1,
        "possibly_sensitive_editable": true,
        "lang": "zh",
        "emojis":[
                        {
                    "name":"Grinning Face",
                  "emoji_id":12,
                  "count":2
            },
              {
                    "name":"Face With Tears of Joy",
                  "emoji_id":22,
                  "count":1
            }
        ]
    }
}
```

**属性介绍**

| 属性                        | 值                                                           |
| --------------------------- | ------------------------------------------------------------ |
| object_type                 | 字符串，整个json的对象类型，该值固定为"TweetRaw"             |
| download_datetime           | 字符串，爬取这条json数据的时间，时间格式固定“年-月-日 时:分”，年为4位，月、日、时、分补足2位，例如 2022-08-13 08:00 |
| raw_value                   | 对象，包含这条推特的所有信息                                 |
| created_at                  | 字符串，该条推特发布时间，时间格式固定`星期 月份 日期 时:分:秒 年`，月份格式约定为`{12: "Dec", 11: "Nov", 10: "Oct",9: "Sep", 8: "Aug", 7: "Jul",6: "Jun", 5: "May", 4: "Apr",3: "Mar", 2: "Feb", 1: "Jan"}`；星期英文简写为`{1: "Mon", 2: "Tue", 3: "Wed", 4: "Thu", 5: "Fri", 6: "Sat", 7: "Sun"}` |
| id                          | 数值，推特id，该id唯一不重复                                 |
| full_text                   | 字符串，推特内容，如果不包含任何内容为null(即 "full_text":null) |
| user_id                     | 数值，发表用户的id，该id唯一不重复                           |
| retweet_count               | 数值，转发数                                                 |
| favorite_count              | 数值，点赞数                                                 |
| reply_count                 | 数值，评论数                                                 |
| possibly_sensitive_editable | 布尔值，表示是否为敏感信息，true为敏感信息，false则非敏感信息 |
| lang                        | 字符串，语言                                                 |
| emojis                      | 数组，存储这条推特所包含的所有表情包，如果不包含任何表情包为空列表 |
| name                        | 字符串，表情包名字                                           |
| emoji_id                    | 数值，表情包id                                               |
| count                       | 数值，该条推特里使用了多少个该表情                           |

------

### 第六部分：输入/输出说明

#### 查询指令介绍

为了便于进行正确的性判定，本次作业采用输入查询指令的形式来进行正确性评判，本次作业包含如下指令，指令的查询字段均以空格隔开。

| 指令序号 | 输入指令格式                      | 输出格式       |
| -------- | --------------------------------- | -------------- |
| 1        | Qdate user_id start_date~end_date | N N1 N2 N3     |
| 2        | Qemoji id                         | name1 name2 …. |
| 3        | Qcount start_date~end_date        | N              |
| 4        | Qtext id                          | full_text      |
| 5        | Qsensitive user_id                | N              |
| 6        | Qlang id                          | Language       |

| 指令序号 | 指令含义                                                     | 说明                                                         |
| -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1        | 查询字段以空格隔开，查询id为user_id的用户在start_date~end_date时间段(包含开始和截止日期)内发布推特的条数N，总转发数N1，总点赞数N2，总评论数N3，以空格分隔。 | 日期格式为year-month-day，年为4位，月、日的数字要以0补全为2位。如2022-08-08，2022-11-10 |
| 2        | 查询字段以空格隔开，查询id的推特所包含的emoji名字，并以count数量降序排列，count数量相等则以name字典序升序排列，输出的name之间以空格分隔。 | 若此id推特无任何表情包,为空列表，输出None                    |
| 3        | 查询字段以空格隔开，查询一段时间内（包含开始和截止日期），json数据爬取的个数N。 | 日期格式同指令1中格式                                        |
| 4        | 查询字段以空格隔开，查询某id推特的内容。                     | 输出原内容时两侧不需要加引号。若此id推特无内容，输出None。(无内容指的是"full_text":null。对于"full_text":""的情况，处理时把内容看作空串，不输出字符) |
| 5        | 查询字段以空格隔开，查询某user_id用户发布的敏感内容条数N。   | 无                                                           |
| 6        | 查询字段以空格隔开，查询某id推特语言属性。                   | 输出原内容时两侧不需要加引号                                 |

请编写程序读入多行jsonline数据，并根据输入的查询指令给出对应输出，**输出需进行换行**。

#### 输入格式

首先输入多若干行Jsonline数据，每一条Jsonline为一条推特的信息。**最后以一行END_OF_MESSAGE结尾**。

其后为多条查询语句，每行一条。

具体的指令请参见上文的**查询指令介绍**。

#### 输出格式

输出格式详见上文的**查询指令介绍**。

#### 输入样例

```
{"object_type":"TweetRaw","download_datetime":"2022-07-06 08:00","raw_value":{"created_at":"Sat Dec 04 17:16:55 2021","id":1467181131464609792,"full_text":"content1","user_id":1406813839627153408,"retweet_count":1,"favorite_count":2,"reply_count":1,"possibly_sensitive_editable":true,"lang":"zh","emojis":[{"name":"Grinning Face","emoji_id":12,"count":2},{"name":"Face With Tears of Joy","emoji_id":22,"count":1}]}}
{"object_type":"TweetRaw","download_datetime":"2022-07-10 08:00","raw_value":{"created_at":"Sat Dec 09 17:16:55 2021","id":1467181131464609793,"full_text":"content2","user_id":1406813839627153408,"retweet_count":1,"favorite_count":0,"reply_count":0,"possibly_sensitive_editable":false,"lang":"zh","emojis":[]}}
END_OF_MESSAGE
Qdate 1406813839627153408 2021-12-04~2021-12-05
Qemoji 1467181131464609792
Qcount 2022-07-04~2022-07-06
Qtext 1467181131464609793
Qsensitive 1406813839627153408
Qlang 1467181131464609792
```

为了便于观察，下面是把jsonline数据转换成的对应顺序json数据

```json
// 第一条Jsonline输入
{
    "object_type": "TweetRaw",
    "download_datetime": "2022-07-06 08:00", 
    "raw_value": {
        "created_at": "Sat Dec 04 17:16:55 2021",
        "id": 1467181131464609792,
        "full_text": "content1",
        "user_id": 1406813839627153408,
        "retweet_count": 1,
        "favorite_count": 2,
        "reply_count": 1,
        "possibly_sensitive_editable": true,
        "lang": "zh",
        "emojis":[
            {
                "name":"Grinning Face",
                  "emoji_id":12,
                  "count":2
            },
              {
                "name":"Face With Tears of Joy",
                  "emoji_id":22,
                  "count":1
            }
        ]
    }
}
// 第二条Jsonline输入
{
    "object_type": "TweetRaw",
    "download_datetime": "2022-07-10 08:00", 
    "raw_value": {
        "created_at": "Sat Dec 09 17:16:55 2021",
        "id": 1467181131464609793,
        "full_text": "content2",
        "user_id": 1406813839627153408,
        "retweet_count": 1,
        "favorite_count": 0,
        "reply_count": 0,
        "possibly_sensitive_editable": false,
        "lang": "zh",
        "emojis":[]
    }
}
```

#### 输出样例

```
1 1 2 1
Grinning Face Face With Tears of Joy
1
content2
1
zh
```

#### 样例解释

**Qdate 1406813839627153408 2021-12-04~2021-12-05：**

用户对应日期时间段内只发布了一条tweet，即第一条tweet，对应输出`1 1 2 1`。

**Qemoji 1467181131464609792**：

查询了第一条tweet内部的表情，按照上文的排序规则排序后，输出`Grinning Face Face With Tears of Joy`。

**Qcount 2022-07-04~2022-07-06：**

查询对应时间段内爬取的数据条数，该时间段爬取了第一个tweet，输出`1`。

**Qtext 1467181131464609793：**

查询了第2个tweet的full_text内容，输出`content2`。

**Qsensitive 1406813839627153408：**

查询了该用户发布的敏感内容条数，2个tweet都为该用户发布，只有第1个为敏感内容，输出`1`。

**Qlang 1467181131464609792：**

查询第1个tweet的语言，输出`zh`。

#### 数据限制

- 在所有键值对中，只有”full_text“的值可能为null，其他的所有值约定不为null。此外，只有full_text的值可以为"",除了full_text其他字符串的值保证不为""(即双引号内保证存在至少一个字符)。
- 指令及数据中的用户user_id，推特的id，emoji的id均存在，**数据范围不作保证**。emoji的名字唯一不重复。
- **在字符串内部，规定不包含双引号**。
- 键值对冒号`:`两侧不包含空格，用于间隔数据的逗号`,`两侧均没有空格。一行jsonline数据中，只有字符串类型的值内部包含空格。
- 有关数字的查询指令，保证输出的数值类结果的大小不超过int。
- id类的属性(id, user_id,emoji_id)的值，可以有前导零，但01和1是不同的id（即不通过数值相等判断id相同）。

------

### 第五部分：提示

#### 解题思路（递归下降算法解题思路）

有了第一部分和第二部分作业的训练，相信大家对层次化设计和正则表达式有了一个初步的认识。在此基础上，针对本次作业，我们提供一种针对具有**特定语法规则的文本结构**进行自顶向下分析的方法，即递归下降解析法。理解了这种方法对大二下学期的OO课程第一单元，以及大三上学期的编译课程都有帮助。

首先，我们需要进行抽象层次设计。根据题目需求，我们需要定义一系列的具有一定数据结构和功能结构的类。题目中所出现的较为明显的对象有：1、json对象（具有若干“键:值”对） 2、json中的“键:值”属性对象（可以细分成不同类型的键值对）3、数组对象。因此，可以将他们所具有的属性和所需要实现的功能封装成类。

*注：继续分析json格式，可以发现：**“json对象”和“数组对象”也能够作为“属性对象”**。因此，我们更可以将上文提到的三个类进一步抽象（通过接口进行归一化处理）成一种类型（比如“Attribute”接口）。*

其次，文本解析器。有了以上的类型的设计，我们就有了分析文本结构的“原材料”，但是我们缺少一种能够分析文本输入的工具。一般而言，我们针对具有特定语法的文本结构可以采用——“词法分析”和“语法分析”两步分析的方式。

词法分析器：主要负责顺序读取字符流，并提取出关键“词法”。针对本次作业，它应解析出——左大括号，右大括号，逗号，冒号，字符串等等“词法”。较为简单。

语法分析器：利用词法分析器，对当前读取的词法做分析，并将各种词法进一步抽象组合到语法模型中。针对本次作业，它应解析出：json对象，属性对象，数组对象。

*注：语言表述比较抽象，如果对此感兴趣的同学，可以自行搜索相关资料进行了解，以下是基于本次作业提供相应思路。*

本次作业的词法分析器设计：

- 属性设计：1、需要分析的字符串 2、负责表示当前读取到的词法

- 功能设计：需要提供“读取下一个词法”的方法

  词法分析较为简单，这里就不过多赘述。

本次作业的语法分析器设计：

- 属性设计：词法分析器。

- 功能设计：由于普通的json文件能够无限次数地嵌套（本题不能无限次数嵌套，可以不使用这种方法，大家可以酌情参考），经典的特判类型的分析是无法满足需求的。因此，这里我们利用“递归下降法”的思想。

  简而言之，就是对几个需要解析的对象类型分别进行解析，并通过互相调用实现递归分析。具体思路可以看以下代码示例。

```java
  // 语法分析器
  public class Parser {
      //词法分析器
      private Lexer lexer;

      // parseObject专门解析并返回json对象
      public .../*json对象*/ parseObject() {
          // 1、当前 Lexer 读到的是左大括号
          // 2、循环读取 Lexer 的下一个词法
          //        若是右大括号，则停止循环
          //         若不是，则调用parseAttribute
          // 3、返回一个json对象
      }

      //parseAttribute专门解析并返回一个属性对象
      public .../*属性对象*/ parseAttribute() {
          // 1、当前 Lexer 读到的是属性的名称
          // 2、分类讨论 Lexer 之后读取的内容
          //        若是字符串，则创建字符串属性对象
          //        若是数字，则创建数字属性对象
          //        ...
          //        若是左大括号，则调用parseObject读取json对象作为属性
          //        若是左中括号，则调用parseArray读取数组对象作为属性
          // 3、返回一个属性对象
      }

      // parseArray专门解析并返回数组对象
      public .../*数组对象*/ parseArray() {
          // 1、当前 Lexer 读到的是左方括号
          // 2、循环读取 Lexer 的下一个词法
          //        若是右方括号，则停止循环
          //        若不是，则一定是json对象/数字/字符串
          //        小tip：java中可以用Object表示任意类型
          // 3、返回一个数组对象
      }
  }
  例子(一个简单例子)：
  {"object_type":"TweetRaw","raw_value":{"id":1467181131464609792,"display_text_range":[0,183]}}

  调用过程：
  |- parseObject: '{'
      |- parseAttribute: "object_type":"TweetRaw"
      |- parseAttribute: "raw_value":
          |- parseObject: '{'
              |- parseAttribute: "id":1467181131464609792
              |- parseAttribute: "display_text_range":
                  |- parseArray: '['
                      |- /* 读入0 183，直到下一个] */
                  |- parseArray: ']'
          |- parseObject: '}'
  |- parseObject: '}'
```

当然，这里只是提供一种思路，以上这个例子也只是其中一种方式。递归下降法具有很好的通用性，可以帮助你解析任何形式的jsonline数据，不仅限于本题固定格式的jsonline数据。当然由于本题的jsonline内部格式、属性已经固定，正确的解题方法必然是多种多样的，大家可以根据自己的思路构思进行解答～

------

### 第六部分：警示

**请同学们在作业代码中务必不要使用或导入任何其他第三方包进行Jsonline、Json数据的自动解析！一经查实将取消本次作业成绩，请自行编写程序解析数据，以达到训练效果。**