## 第一次作业指导书

欢迎大家来到本课程的第一次正式作业！

本课程共有7次作业，他们被分为三个单元，每个单元有独立的题目背景，单元内部的若干次作业以**迭代**的形式展开，即后一次作业是在前一次作业的基础上迭代完成的，这意味着大家需要在保证代码正确性的同时，尽量提高可扩展性，以便于后续迭代开发。不过，请同学们不必担心，在第一单元中，我们不会对代码的可扩展性提出过高的要求，代码架构也将会有足够的提示。

**前三次作业 (homework1 - homework3) 为第一单元**，在本单元中大家将学习基本的 Java 知识、面向对象知识，同时也会进一步学习 git 的基础知识。以下是第一单元三次作业的基本内容：

- **Homework1** 引导同学们实现一系列基础的类，并且熟悉类、属性和方法的使用，引导大家向面向对象的思维方式转变。
- **Homework2** 涉及方法的重写和复用，并引入异常处理机制，希望同学们可以感性地体会到层次化设计的好处，了解并简单应用异常处理（异常处理在之后也常会用到）。
- **Homework3** 涉及接口，需要同学们在之前 Task 的基础上完成更加复杂的操作。如果此时仍然使用原来的编码习惯，会在这个 Task 中遇到巨大困难，而严格按照我们的提示去做的同学会体会到好处。同时，为了顺利的完成本次作业，同学们也需要了解 git 分支的相关知识。

在本单元中，同学们还将继续学习 git 版本管理工具的相关知识，每次作业中均将有所体现。**请大家在本单元持续的三周时间内，至少完成 [Git Pro](https://www.progit.cn/) 前三章内容的阅读（起步、Git 基础、Git 分支）。** 这不仅会让大家更顺利的完成本单元的代码作业，同时也可以真正基本掌握 git 这一强大工具的用法，而并不仅仅是使用 git 上传代码进行评测。

本单元每一次作业的指导书，将先为大家简要地介绍本次作业所需的知识，之后给出本次作业的具体要求以及示例，最后还会给出架构和编码方面的一些提示，以辅助同学们完成代码架构，增加代码的扩展性。为了更加严谨地描述题目要求，以及为大家提供更详细的指导，指导书可能较长，希望同学们可以耐心读完。如果在阅读指导书的过程出遇到疑问，欢迎大家在讨论区中提出。

本单元每一次作业中，同学们需要完成**测验题目**和**代码任务**。为了增加同学们对 git 版本管理工具的理解，同时也为了帮助同学们完成 Homework3，每次作业均设置了一些测验题，以帮助大家更好的掌握 git 用法、理解 git 原理。题目形式可能包括选择、填空等，有多次提交机会，回答正误均没有影响，不计入成绩，我们将在提交正确或提交机会用尽时给出题目的详细解析，希望同学们阅读后能有所收获。**测验题目均可以在上述的 [Git Pro](https://www.progit.cn/) 中找到答案**，因此希望大家认真阅读。完成全部测验题目后，大家便可根据指导书的要求编写代码并提交评测。

为了更高效的答疑，我们鼓励大家使用**讨论区**进行提问交流，助教也将在讨论区回答相关问题。课程的其他详细说明见[gitlab 公共发布区](http://gitlab.oo.buaa.edu.cn/2022_cpp_public/course_system_guidebook)。

------

### 第一部分：训练目标

- **学会构建构造方法**

  Java 类使用变量定义数据状态，使用方法定义行为。除此之外，类还提供了一种称为构造方法(constructor)的特殊方法，用于创建新对象。作为类的方法，构造方法虽然可以完成任何动作，但是构造方法的目标是为了完成初始化，因此构造方法的实现代码具有显著的特征，即对类中定义的成员变量进行初始化。按照变量的类型要区分两种情况：

  - 原子类型的成员变量：一般是直接使用编程语言内置的数据类型所声明的变量，如 int ，boolean 等。这种成员变量一般可以直接赋值，如 `int price = 10`。
  - 复合类型的成员变量：一般是使用编程语言类库或用户自定义的类(class)来声明的成员变量，如 `ArrayList myList` 。这种成员变量无法直接指定一个赋值结果，通常需要调用相应类型的构造方法来获得相应的初始值，如 `myList = new ArrayList()` 。

- **对类进行封装，理解封装的作用**

  封装是面向对象方法的一个重要特征，强调对外隐藏对象的内部属性和实现细节，仅对外提供公共访问方式。这样做的优点是提高类的可复用性、安全性。

  关键字 private 、protected 和 public 可以进一步对类的成员（包括变量和方法）的可见范围，被 private 修饰的成员只能在本类中使用；protected修饰的成员可以在本类及其直接子类使用；public修饰的成员可以在任意类中直接使用。一般而言，根据封装原则，如果没有特别的针对性考虑，建议对所有的成员变量使用private进行限定。

- **建立一个对象的集合，实现向集合中增加对象和访问集合中对象的操作，学习容器的使用和选择，熟悉对容器的操作**

- **学习 git 版本管理工具的基础知识**

  [Git Pro](https://www.progit.cn/) 是 Git 官方推荐的资料，其中介绍了 git 工具的基本用法和原理。本单元要求大家阅读前三章（起步、Git 基础、Git 分支），并根据从中学到的知识完成测验题目。

  本次作业中，我们希望大家可以阅读 [Git 基础 - 撤销操作](https://www.progit.cn/#_undoing) 一节及其之前的内容，以及 [Git 工具 - 重置揭秘](https://www.progit.cn/#_git_reset) 一节，并完成测试题目。

- **阅读官方文档** JDK 提供了官方文档 [JDK Document](https://docs.oracle.com/en/java/javase/18/docs/api/)，大家可以从中找到所有 Java 提供的类的详细介绍。该文档右上角提供了搜索框，输入想要查询的类或方法名，即可找到相关介绍。

### 第二部分：预备知识

关于 Java：

> Java 是一门十分强大的语言，具有跨平台、安全等特点。Java语言得到广泛使用的一个重要原因是提供了丰富的类库，作为初学者你要养成多查阅和使用 Java 所提供的类的习惯，不要重复造轮子。在 pre 训练中，大家可以使用 Java 提供的相关容器，如 ArrayList、HashMap 等，会取得事半功倍的效果。

关于面向对象：

> 面向对象是一种主流的软件开发方法，也是一种思维方式，其核心是识别类，并在类之间建立层次式的协作关系。面向对象思维需要逐步养成，是本课程的核心目标。作为 pre ，主要还是通过一些小的迭代式练习来初步感受面向对象方法。因为 pre 训练题目相对比较简单，通过传统上的结构化程序开发方法也可以实现代码，甚至可以只写一个函数就能完成任务，但希望大家不要这样做。千里之行，始于足下，希望大家从一开始就体会面向对象开发的特点，这也是 pre 训练的重要目标。

关于容器：

> 在 Java 中，我们有更强大的“数组”——**容器**，它提供了更多管理多个对象的方法。和数组相比，容器可以动态控制容量、方便地增加或删除元素、方便地对元素进行进一步的管理（以 `ArrayList` 为例，更多操作可以参考 [ArrayList - JDK Documentation](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/ArrayList.html)）

在开始前，你需要先了解 java 的基础语法，包括表达式、for 循环、if 语句、输入输出、类，并学会编译运行 java 程序，Java 语言的很多成分都和 C 语言相似。

在任务迭代的开发过程中，会不可避免的涉及到变量名更改的问题，请尽量利用 IDEA 提供的重命名工具(Shift + F6)，不要自己手动一个个改。

### 第三部分：基本概念

#### 一、从 C 到 Java

其实无论是 while、for 还是 if， switch，Java 与 C 基本上都是相同的，在你的简单预习中相信也发现了这个现象。在我们正式开始完成任务之前，再在 Java 与 C 关联的方面做一些简单介绍。

如下例子所示，Java 中的方法 `public static void main(String[] argv)`，就相当于C语言的入口函数`main()` 。这个 `main` 是你 Java 主程序的执行入口，当运行 Java 程序时，你可以理解成将会从此处开始执行。事实上，可以有多个类中包含 `main` 方法，我们可以手动制定一个类中的 `main` 方法作为程序入口。因此，`main`方法所在类的类名称也可以不是例子中的 `MainClass`。 为了方便评测，在 OO 课程中，**请你保证 `main` 方法出现且只出现在一个类中**。

```java
import java.util.Scanner;

public class MainClass {
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        double b = scanner.nextDouble();
        String c = scanner.next();
        System.out.println("Hello world!");
        System.out.println(c + b + a);
    }
}
```

现在我们可以运行一下这个程序，程序中已经包含了 Java 程序的输入输出方式，你也可以换一些组合方式，来进一步体会 Java 的输入输出。

#### 二、构造一个类

现在我们要正式开始本次任务了，在本次任务中我们希望构造一个 **Bottle** 类，来表示冒险者需要的物品，要求 **Bottle** 类包含以下属性：标识(`id`: 整数)，名字(`name`: 字符串)，价格(`price`: 整数)，容量(`capacity`: 浮点数)，和表达瓶子是否装满的标志量(`filled`: 布尔值)。 从某种意义上来说，只包含属性的类其实与 C 语言的结构体是很相似的。

以 Bottle 举例，构造一个类，代码如下：

```java
class Bottle {
    private int id;
    private String name;
    private long price;
    private double capacity;
    private boolean filled;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id=id;
    }
}
```

我们会发现，所有属性均是私有的，外部完全看不到它们，这时，如果在主类中声明了一个 Bottle 的实例 bottle ，无法对 name 进行 `bottle.name` 的操作。如前所述，面向对象开发强调封装和私有保护，我们一般不允许把属性定义成 public 的。面向对象方法的基本特点是私有化保护内部数据，暴露对数据的必要操作接口，多数情况下可以提供 `setter-getter` 方法。但是需要注意，如果某个属性的取值不能允许外部进行无限制的修改，就不能提供公开的 setter 方法。

那么现在就是练习时间啦，请你把所有属性都私有化，将 Bottle 封装起来！并配置好相应的方法让他们能够被外部更改和访问。

> 小 tips：在之后的作业中，如果你对很多变量都需要重复实现 get 和 set 方法，挨个输入比较麻烦，有兴趣搜索一下 IDEA 的**一键生成方法（generate) 功能**，高效编码

#### 三、实例化

我们现在拥有了一个 Bottle 类，那么问题来了，怎么在 MainClass 里引用他呢？我们可以把 Bottle 想像成一个像 `int`、`char` 一样的变量类型。那么我们就可以使用这条语句：`Bottle bottle;` 来声明一个`Bottle`变量了。在Java中，声明的对象**变量**就像是C语言中的一个结构体**指针**，如果你不对其初始化那么这个变量就会指向一个 `null` 量，代表这是一个空指针，此时还没有任何内存空间被分配用于存储一个Bottle的信息，你还需要使用**构造函数**实例化一个对象。 代码如下：

```java
public class MainClass {
    public static void main(String[] argv) {
        Bottle bottle = new Bottle();  //new Bottle() 即构造函数
        bottle.setName("Cola");
        bottle.setPrice(3);
        bottle.setId(1);
        bottle.setCapacity(100.0);
        bottle.setFilled(true);
    }
}
```

构造函数的用途是在你需要创建一个对象的时候完成一些初始化工作，并给对象的所有属性赋予初始值。

虽然 Java 语言默认为每个类提供一个缺省的构造方法，但是你并不确定这个缺省构造方法把每个属性设置成什么初值。对于上述的 Bottle 缺省构造方法而言，把 id 初始化为 0，把 price 初始化为 0，把 name 初始化为 null，把 capacity 初始化为0.0，把 filled 初始化为 false。我们建议显式方式来实现自己所需的合适的构造方法，确保得到的对象初始状态直观可见且可控。

在类中以"public 类名(参数列表)"的方式定义的函数就是构造函数。

这里举一个长方体类的例子：

```java
public class CuboidBox {
    private double length;
    private double width;
    private double height;

    // 构造函数
    public CuboidBox(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }
}
```

那么现在就是练习时间啦，请你为 Bottle 写一个构造函数，要求该构造函数可以传入四个参数为 Bottle 赋值，同时需要初始化是否装满变量 `filled` 为 `true`。

#### 四、容器

既然我们拥有了装满药水的瓶子对象，那么自然也要拥有能够持有与使用它的人，这就是我们的冒险者。然而冒险者从来都得准备充分：在怪物面前若只有一瓶恢复药水，难免会疲于招架。因此冒险者可能会携带多个瓶子。那么，应当如何管理这些瓶子对象呢？

能够想到，数组可以完成这样的管理。不过在 Java 中，我们有更强大的“数组”——**容器**，它提供了更多管理多个对象的方法。以 `ArrayList` 为例，一个冒险者身上的瓶子可以以如下方式管理：

```java
public class MainClass {
    public static void main(String[] args) {
        // 以 ArrayList 为例，展示容器的用法

        // 1. 创建容器。大部分容器都会随着元素的加入自动扩容。
        ArrayList<Bottle> bottles = new ArrayList<>();

        // 2. 加入一个元素
        Bottle bottle = new Bottle();
        bottles.add(bottle);

        // 3. 判断元素是否在容器内
        if (bottles.contains(bottle)) {
            System.out.println("We have such a bottle!");
        }

        // 4. 遍历容器中的所有元素
        for (Bottle item : bottles) {
            System.out.println(item.getName());
        }

        // 5. 输出容器规模
        System.out.println(bottles.size());

        // 6. 删除一个元素
        bottles.remove(bottle);
    }
}
```

对于经常需要使用或添加药水瓶子的冒险者来说，使用容器是不二之选。注意到冒险者和瓶子同样是对象，并拥有一个 ID 与名字，因此我们可以将冒险者也封装为一个类：

```java
class Adventurer {
    private int id;
    private String name;
    private ArrayList<Bottle> bottles;
}
```

除了 `ArrayList` 外，还有 `HashMap`、`TreeMap`、`HashSet`、`TreeSet`等常用容器。

### 第四部分：题目描述

先介绍 pre1的背景故事。

想象你是一个冒险者，现在正在一个新的星球上进行探险，这个过程中你需要努力收集各种物品来不断增强自身能力值。在第一个 task 中你需要完成两个任务：

- 对基本物品 Bottle 和冒险者 Adventurer 进行建模
- 利用容器的知识，管理多个冒险者

首先，你需要构造一个 **Bottle** 类，来表示冒险者需要用到的瓶子类，要求 **Bottle** 类包含属性：ID，名字，价格，容量，和表达瓶子是否装满的标志量。

接着，再构造一个**Adventurer**类，用来表示冒险者类，要求**Adventurer**类包含属性：ID，名字，承载多个Bottle的容器。

在这个问题中，你需要管理多个冒险者。初始时，你没有需要管理的冒险者。接下来会有 1212 个操作：

1. 加入一个需要管理的冒险者
2. 给某个冒险者增加一个瓶子
3. 删除某个冒险者的某个瓶子
4. 更新某个冒险者所持有的某个瓶子的价格
5. 更新某个冒险者所持有的某个瓶子是否装满
6. 查询某个冒险者所持有的某个瓶子的名字
7. 查询某个冒险者所持有的某个瓶子的价格
8. 查询某个冒险者所持有的某个瓶子的容量
9. 查询某个冒险者所持有的某个瓶子是否装满
10. 输出某个冒险者所持有的某个瓶子的字符串描述
11. 查询某个冒险者所持有瓶子的价格之和
12. 查询某个冒险者所持有瓶子价格的最大值

操作1-5不需要任何输出，只需要对操作 6-12 进行输出回答。

------

### 第五部分：输入/输出格式

第一行一个整数 m*m*，表示操作的个数。

接下来的 m*m* 行，每行一个形如 `{type} {attribute}` 的操作，`{type}` 和 `{attribute}` 间、若干个 `{attribute}` 间使用若干个空格分割，操作输入形式及其含义如下：

| type | attribute                                     | 意义                                                         | 输出文本                                                     |
| ---- | --------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | `{adv_id} {name}`                             | 加入一个 ID 为 `{adv_id}`、名字为 `{name}` 的冒险者，且未持有任何瓶子 | 无                                                           |
| 2    | `{adv_id} {bot_id} {name} {price} {capacity}` | 给 ID 为 `{adv_id}` 的冒险者增加一个瓶子，瓶子的 ID、名字、价格、容量分别为 `{bot_id}`、`{name}`、`{price}`、`{capacity}`，**且默认为已装满** | 无                                                           |
| 3    | `{adv_id} {bot_id}`                           | 将 ID 为 `{adv_id}` 的冒险者的 id 为 `{bot_id}` 的瓶子删除   | 无                                                           |
| 4    | `{adv_id} {bot_id}{price}`                    | 将 ID 为 `{adv_id}` 的冒险者的 id 为 `{bot_id}` 的瓶子的价格更改为 `{price}` | 无                                                           |
| 5    | `{adv_id} {bot_id}{filled}`                   | 将 ID 为 `{adv_id}` 的冒险者的 id 为 `{bot_id}` 的瓶子的装满的状态更改为 `{filled}` | 无                                                           |
| 6    | `{adv_id} {bot_id}`                           | 查询ID 为 `{adv_id}` 的冒险者的 id 为 `{bot_id}` 的瓶子的名字 | 一个字符串，表示瓶子名字                                     |
| 7    | `{adv_id} {bot_id}`                           | 查询ID 为 `{adv_id}` 的冒险者的 id 为 `{bot_id}` 的瓶子的价格 | 一个整数，表示瓶子价格                                       |
| 8    | `{adv_id} {bot_id}`                           | 查询ID 为 `{adv_id}` 的冒险者的 id 为 `{bot_id}` 的瓶子的容量 | 一个浮点数，表示瓶子容量                                     |
| 9    | `{adv_id} {bot_id}`                           | 查询ID 为 `{adv_id}` 的冒险者的 id 为 `{bot_id}` 的瓶子是否装满 | 一个字符串，表示瓶子是否装满（输出true表示装满，false表示没有装满） |
| 10   | `{adv_id} {bot_id}`                           | 查询ID 为 `{adv_id}` 的冒险者的 id 为 `{bot_id}` 的瓶子的字符串描述 | 以`The bottle's id is {id}, name is {name}, capacity is {capacity}, filled is {filled}.`的形式打印状态。 |
| 11   | `{adv_id}`                                    | 查询 ID 为 `{adv_id}` 的冒险者所持有瓶子的价格之和           | 一个整数，表示瓶子价格之和                                   |
| 12   | `{adv_id}`                                    | 查询 ID 为 `{adv_id}` 的冒险者所持有瓶子价格的最大值         | 一个整数，表示瓶子价格的最大值                               |

#### 一、数据范围与操作限制

##### 变量约束

| 变量                  | 类型   | 说明                               |
| --------------------- | ------ | ---------------------------------- |
| `id (adv_id, bot_id)` | 整数   | 取值范围：0 - 2147483647           |
| `name`                | 字符串 | 保证不会出现空白字符               |
| `price`               | 长整数 | 在 long 精度范围内，且保证不小于 0 |
| `capacity`            | 浮点数 | 在 double 精度范围内               |

##### 操作约束

- **保证所有冒险者与瓶子的 ID 两两不同。**
- 操作 2-12：保证冒险者 ID 一定存在。
- 操作 3-10：冒险者一定持有该 ID 的瓶子。
- 操作 11：若冒险者不持有任何瓶子，则输出 0。
- 操作 12：冒险者持有至少一个瓶子。
- 操作数满足 1 \leq m \leq 20001≤*m*≤2000。

#### 二、测评方法

输出数值时，你的输出数值需要和正确数值相等。

**假设你的输出值 x_{out}\*x\**o\**u\**t\* 和正确数值 x_{std}\*x\**s\**t\**d\* 之间的绝对或相对误差小于等于 10 ^ {-5}10−5，则认为是相等的，即满足**

\dfrac {|x_{std} - x_{out}|}{\max(1, |x_{std}|)} \leq 10^{-5}max(1,∣*x**s**t**d*∣)∣*x**s**t**d*−*x**o**u**t*∣≤10−5

#### 三、输入样例与输出样例

**样例输入**

```
17
1 2 Co20ocvblT
1 30 Al8QnWnkS7
1 91 pqWY5UNcm4
2 91  7 q6DlfOJGzf 82 48.5801
2 30  8 0vyv58Ec49 25 12.1451
2 30  56 OdcdRFEw7s 13 34.3745
2 91  64 jMZ9uBOLy4 45 38.1122
2 2  65 COIecJNdIH 89 41.7995
2 2  26 UXDaKL9P1O 79 36.1887
2 91  15 Vy6EKNgojP 10 35.5545
3 91 7
4 30 56 67
5 91 15 true
6 2 65
7 91 15
8 2 26
9 91 15
```

**样例输出**

```
COIecJNdIH
10
36.1887
true
```

------

### 第六部分：提示

- 容器部分大家需要熟悉对容器的操作，题目中限制了所有对象（冒险者、瓶子）的 ID 不会相同，思考一下，哪种容器会更加适合本次任务？或者说哪些容器呢？
- 在本次作业中我们有求和操作，尽管我们将输入数据限制在 long 的范围内，但是在求和时可能会超出精度范围。请你查阅 Java 相关资料，来看看在 Java 中是如何解决超过普通数据类型数据范围的精度问题的。
- Java 中有些特别的类用于处理大数运算，如 `BigInteger`，`BigDecimal`。
- 数据类型的边界可以使用类中的常量，例如`Long.MIN_VALUE`表示`long`类型（或`Long`类型）的最小值。
- 操作10要求输出特定的`Bottle`类型实例的属性，建议在 `Bottle` 类中重写`toString` 方法，示例如下：

```java
class Bottle {
    private int id;
    // ...
    @Override public String toString() {
        return "The bottle's id is " + id + ".";
    }
}

class Main {
    public static void main(String[] argv) {
        Bottle bottle = new Bottle();
        // ...
        System.out.println(bottle);
    }
}
/**
 * 输出:
 * The bottle's id is 1.
 */
```

`java.io.PrintStream.println(Object x)`函数将调用`String.valueOf(x)`以获得实例`x`的字符串描述，而`java.lang.String.valueOf(Object x)`函数将在`x`不为`null`时返回`x.toString()`，因此我们可以通过重写`toString`方法使`println`函数输出自定义格式的“实例属性”。详见 [println() - JDK Documentation](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/io/PrintStream.html#println()) 和 [valueOf() - JDK Documentation](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/String.html#valueOf(java.lang.Object) ) 。