## 第二次作业指导书

### 第一部分：训练目标

- 学习继承，了解设计模式中的工厂模式
- 学习方法的重写、Java 的多态机制以及 Java 的异常处理机制。
- 学习 Git 工具中的远程仓库、打标签、重置的相关知识。

### 第二部分：预备知识

#### 一、继承

继承就是定义子类继承父类的特征和行为，使得子类可以拥有父类的属性和方法，从而起到代码复用的目的。

举个例子，假设我们有一个类 `Hero` 表示英雄，其包含生命值，保护盾值与魔法值这三个属性，并包含“徒手攻击”这一方法：

```java
public class Hero {
    int healthPoint;
    int defensePoint;
    int magicalPoint;

    public void attackWithHand() {
        /**/
    }
}
```

假设我们还想设计一个类，比如“骑士”类 `Knight` ，他是英雄的一种，其也拥有生命，保护盾和魔法值，也拥有“徒手攻击”方法，除此之外其还拥有“手枪攻击”这个方法。如果从头开始实现这个类的话，需要编写如下代码：

```java
public class Knight {
    int healthPoint;
    int defensePoint;
    int magicalPoint;

    public void attackWithHand() {
        /**/
    }

    public void attackWithPistol() {
        /**/
    }
}
```

注意到骑士相比较英雄只多了一个使用手枪攻击的方法，其他部分都一样，所以我们可以认为骑士是一种特殊的英雄。其实我们还可能要设计“牧师“、“游侠”等类，其也拥有英雄类拥有的那些属性和方法，除此之外它们各自可能还有一些其他方法，则我们也可以将两个类也认为是特殊的英雄。倘若我们仍然直接编写代码，则又要写很多重复代码。如果直接复制粘贴，还要修改类名以及构造方法等地方，且假如第一个版本的方法写错了，后面复制粘贴的都会出错，修改时要处处做修改，非常麻烦。

这个时候，继承就登场了。使用继承，我们可以让类 `A` 去得到类 `B` 已有的属性和方法，接下来类 `A` 就只需要专注于编写其特有部分的代码了。

使用继承来编写骑士类的例子如下：

```java
public class Knight extends Hero {
    // 公共的属性和方法不需要重复编写

    // 只需要编写Knight特有的手枪攻击方法
    public void attackWithPistol() {
        /**/
    }
}
```

在Java中，我们使用 `extends` 关键字表示继承，`A extends B` 意味着 `A` 继承了 `B` ，`A` 是 `B` 的子类， `A` 得到了 `B` 的属性和方法。

从语义上来说，在 `A` 和 `B` 类型满足 *is-a* 关系（A is a B），即`A` 类型是 `B` 类型的一种时，可以使用继承来在程序表述。在本例中可以说 Knigh is a Hero，因此我们使用继承关系。

#### 二、向上转型

在建立了继承关系之后，可以使用**父类型**去引用通过**子类型**创建的对象。这里涉及两个重要的概念，对象与对象引用。一般而言，对象是一个类的实例化结果，对应内存中的一个数据结构。对象引用则是使用一个变量来指向内存中的这个数据结构（即对象）。

如我们可以使用上面的 Knight 类来构造一个对象：`new Knight()`，这条语句返回一个创建的对象。我们同时需要声明一个对象引用来指向返回的对象，否则可能就找不到这个对象了。所以，一般代码都会这么写：`Knight knt = new Knight()` 。

在建立了继承关系之后，我们也可以使用 Hero 类来声明一个对象引用，并指向类型为 Knight 的对象：`Hero h = new Knight()`。从程序类型的角度，这个表达方式称为向上的类型转换，简称**向上转型** (up cast)。向上转型的例子如下：

```java
public class Main {
    public static void main(String[] args) {
        Hero hero1 = new Knight();
        hero1.attackWithHand();
    }
}
```

因为 Knight 类提供了 `attackWithPistol()` 方法，因此通过 `new Knight()` 创建的对象是拥有手枪攻击这个能力。这里同学们可能会马上想到：能否通过上面例子中的 hero1 来调用这个方法呢？ 如下面的代码所示：

```java
public class Main {
    public static void main(String[] args) {
        Hero hero1 = new Knight();
        // 编译错误
        hero1.attackWithPistol();
    }
}
```

很不幸，上面的代码会出现编译错误，编译器认为 Hero 类中没有定义 `attackWithPistol()` 方法。这就带来了一个问题，明明所指向的对象拥有相应的方法，但是却不能调用。其原因是我们进行了向上转型，使用 Hero 类型的变量来引用它，这往往表明程序设计者此时只关心在 Hero 类这个层次能够看到的方法（否则就应该使用 Knight 来声明一个引用）。

#### 三、向下转型

Java 语言提供了一个特殊的关键词 `instanceof` 用来判断一个对象引用所指向的对象的创建类型是否为特定的某个类，一般写为 `obj instanceof A`，其中 obj 为一个对象引用，A 为一个类型（类或接口），这个表达式的取值结果为布尔型，如果 obj 的创建类型为 A，则结果为 true，否则为 false。在这个表达式取值为 true 的情况下，可以使用**向下转型** (down cast) 来使用一个 A 类型的对象来引用obj： `A ao = (A)obj` 。注意，实际上 obj 所指向对象的创建类型永远不会发生变化，转型的只是对象引用类型。下面例子给出了相应的向下转型场景：

```java
public class Main {
    public static void main(String[] args) {
        A[] list = new A[20];
        Scanner input = new Scanner(System.in);
        int cnt = 0;
        // 先构造10个对象，放到数组list中
        for (int i = 0; i < 10; i++) {
            int t = input.nextInt();
            if (t % 3 == 0) {
                list[cnt] = new A();
            } else if (t % 3 == 1) {
                list[cnt] = new B();
            } else {
                list[cnt] = new C();
            }
            cnt++;
        }
        // 我们想调用list中所有C类型对象的c()方法
        for (int i = 0; i < cnt; i++) {
            // 先判断是不是C类型的对象，A instanceof B会返回true 或者 false
            if (list[i] instanceof C) {
                // 如果是，就向下转型，使用这个对象原本的类型的引用去指向它
                // 如果不是却还强行向下转型，则会出现错误
                C ref = (C) list[i];
                // 然后调用其c()方法
                ref.c();
            }
        }
    }
}
```

值得注意的是，在 `instanceof` 返回真的时候使用向下转型，才能保证向下转型的安全性，否则运行时会触发错误。

#### 四、对象方法的重写和复用

有时候，你会发现具有继承关系的类的某些行为具有递进关系，比如在下方代码中 Course类 和 OOCourse类 之间具有继承关系，OOCourse与Course有部分**相同**行为（即Course中定义且被OOCourse继承的行为），但OOCourse也会有自己的**特有**行为。

为了确保不论是使用Course对象引用，或者OOCourse对象引用来访问OOCourse对象时都能够顺利调用相应的方法，我们期望这两个类中实现的特定方法同名。**这种让子类重新实现一个在父类中已经实现的方法是面向对象的一个重要机制，称为方法重写。**方法重写获得的直接好处是让子类与父类在相应方法的调用上保持了一致性。

更通俗的说，重写方法与父类方法在行为上具有相似功能，但子类重写的方法一般额外增加一些行为。举例而言，设Course中实现了一个显示课程信息的方法(displayInfo)，我们希望OOCourse重新实现这个方法，从而能够多显示一些特有的信息。在程序编写方面，一般会为重写方法标上一个@Override标签。看下面的例子：

```java
class Course {
    void displayInfo() {
        System.out.println("老师上课，同学完成作业，最终老师会给一个成绩");
    }
}
class OOCourse extends Course {
    @Override
    void displayInfo() {
        System.out.println("老师上课，同学完成作业，最终老师会给一个成绩");
        System.out.println("还有研讨课，强测互测等任务，学期结束还会有颁奖典礼");
    }
}
```

我们可以看到OOCourse重写的`displayInfo`方法中的第一句话与Course中`displayInfo`方法的语句完全相同。通常，我们不希望出现重复编写代码（又称为代码拷贝）的现象。Java语言提供了一个重要的关键词**super**，它实际指代的是当前对象从父类继承得到的内容，因此通过super.displayInfo()可以确保调用的是Course实现的displayInfo方法。请看下面的例子：

```java
class OOCourseAlpha extends Course {
    @Override
    void displayInfo() {
        super.displayInfo(); // 调用了类Course中定义的方法
        System.out.println("还有研讨课，强测互测等任务，学期结束还会有颁奖典礼");
    }
}
```

#### 五、多态

前面提到，如何判断实际调用的是子类重写的方法，还是父类实现的方法。其实，这与对象**引用的类型**无关，而是取决于被引用对象的**创建类型**。请看下面的代码示例：

```java
Course c1 = new Course();
Course c2 = new OOCourseAlpha();
c1.displayInfo();
c2.displayInfo();
```

其中通过c1调用的实际是Course类实现的displayInfo方法，而通过c2调用的则是OOCourseAlpla类重写的displayInfo方法，但实际上c1和c2的引用类型都是Course。 上面我们提到的这个特性，就叫做多态。

#### 六、异常处理

程序运行时，发生了不被期望的事件，它阻止了程序按照程序员的预期正常执行，这就是异常。运行出错后，Java 提供了一种优秀的解决办法：异常处理机制。

异常处理机制采取显式的方式来处理异常，包括两个方面：

- 引入了专门的表达和处理方式，代码上一目了然就能看出是异常处理；
- 一旦发生异常，会强迫程序执行进入异常处理分支。

在Java语言中，每个异常都被封装为Exception，异常有抛出和捕捉两种处理方式。所谓抛出，就是使用Java提供的throw关键词来产生一个Exception或者其某个子类的对象；而捕捉则是通过catch关键词来捕捉在一段代码的执行过程中所抛出的相关异常对象。

课程推荐使用异常处理机制来区分处理显著不同于一般情况下的数据状态。使用异常处理可以让你的代码更加清晰易于理解，降低出现 bug 的可能性。请阅读《Core Java》的第七章相关内容了解异常处理机制的使用方法，并参照本章最后 Tips 部分来使用异常处理优化你的代码。

------

### 第三部分：题目描述

本次作业需要在上次作业的基础上进行增量开发。

- 建立 Equipment 装备类。我们将 Task1 中的 Bottle 以及下面增加的所有药水类、武器类统称为 *“装备类”*，使所有装备类均继承自 Equipment 类（该类因而可称为基类， base class），请将所有装备都具有的属性定义在这个类里。同时，Task1 中每位冒险者拥有承载多个 Bottle 的容器，这里将承载 Bottle 的容器改为承载所有装备类的容器。
- 为冒险者新增一些属性如下：生命值 （`health`, 浮点数，默认值 100.0）、经验值（ `exp`, 浮点数，默认 0.0）、金钱数（ `money`, 浮点数，默认 0.0）。
- 增加药水 HealingPotion 和 ExpBottle 并继承 Bottle 的全部属性；添加“武器类” Sword 以及 RareSword 和 EpicSword，他们继承 Sword 全部属性。见下表：

| 药水类型      | 属性                                                         | 属性类型                                     |
| ------------- | ------------------------------------------------------------ | -------------------------------------------- |
| HealingPotion | 包括 Bottle 的全部属性，新增加属性 efficiency，代表药水的治疗效果 | Bottle 原有属性不变，efficiency 为浮点数类型 |
| ExpBottle     | 包括 Bottle 的全部属性，新增加属性 expRatio，代表水瓶对于经验值的增强效果 | Bottle 原有属性不变，expRatio为浮点数类型    |

| 武器类型  | 属性                                                         | 属性类型                                                     |
| --------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Sword     | id, name, price, sharpness。其中 id, name, price 与 Task1 中 Bottle 类的定义相同，sharpness 表示武器的锋利程度 | id, name, price 与 Bottle 类中相应属性类型相同，sharpness 为浮点数类型 |
| RareSword | 包括 Sword 的全部属性，新增加属性 extraExpBonus，代表使用武器的附加效果 | Sword 原有属性不变，extraExpBonus 为浮点数类型               |
| EpicSword | 包括 Sword 的全部属性，新增加属性 evolveRatio，代表使用武器的附加效果 | Sword 原有属性不变，evolveRatio 为浮点数类型                 |

- 为每一种装备设置一个**使用**方法，定义如下，设冒险者A使用了装备B：

| 装备B的类型                                       | 使用效果                                                     | 输出文本                                                     |
| ------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Bottle（若 filled 为 true）                       | A的生命值增加[B的 capacity 属性]的十分之一，之后 B 的 filled 变为 false，price 变为原来的十分之一（向下取整）。 | {A 的 name} drank {B 的 name} and recovered {生命值增加量}.  |
| HealingPotion（若 filled为true）                  | A的生命值增加[B的 capacity 属性]的十分之一，之后 B 的 filled 变为 false，price 变为原来的十分之一（向下取整）。然后A的生命值再额外增加[B的capacity属性]乘以[B的efficiency属性]的量。 | {A 的 name} drank {B 的 name} and recovered {生命值增加量}. {A 的 name} recovered extra {生命值额外增加量}. |
| ExpBottle（若 filled 为 true）                    | A的生命值增加[B的 capacity 属性]的十分之一，之后 B 的 filled 变为 false，price 变为原来的十分之一（向下取整）。然后A的经验值变为原来的[B的expRatio属性]倍。 | {A 的 name} drank {B 的 name} and recovered {生命值增加量}. {A 的 name}'s exp became {A 变化后的经验}. |
| Bottle/HealingPotion/ExpBottle（若filled为false） | 无任何作用效果。                                             | Failed to use {B 的 name} because it is empty.               |
| Sword                                             | 使用后A的生命值减少 10.0、经验值增加 10.0，金钱数增加相当于[B 的 sharpness属性]一倍的量。 | {A 的 name} used {B 的 name} and earned {增加的金钱数}.      |
| RareSword                                         | 使用后A的生命值减少 10.0、经验值增加 10.0，金钱数增加相当于[B 的 sharpness属性]一倍的量。然后 A 的经验值额外增加[B 的 extraExpBonus 属性]。 | {A 的name} used {B 的name} and earned {增加的金钱数}. {A 的name} got extra exp {额外获得的经验}. |
| EpicSword                                         | 使用后A的生命值减少 10.0、经验值增加 10.0，金钱数增加相当于[B 的 sharpness属性]一倍的量。然后B的sharpness 属性变为原来的 evolveRatio倍。 | {A 的 name} used {B 的 name} and earned {增加的金钱数}. {B 的 name}'s sharpness became {B 变化后的sharpness}. |

- 实现各项装备的查询和增删指令，设置如下操作：
  1. 加入一个冒险者
  2. 给某个冒险者添加某件装备（装备包括药水和武器）
  3. 删除某个冒险者拥有的某个装备
  4. 查询某个冒险者所拥有装备的价格之和
  5. 查询某个冒险者所拥有装备的价格最大值
  6. 查询某个冒险者拥有的装备总数
  7. 打印一个装备的全部属性，属性的输出顺序与输入创建该装备时给定的各参数顺序一致，具体格式详见下方 *属性打印方式*
  8. 某个冒险者使用其拥有的某个装备
  9. 打印某个冒险者的所有状态

------

### 第四部分：输入输出

第一行一个整数 m*m*，表示操作的个数。

接下来的 m*m* 行，每行一个形如 `{type} {attribute}` 的操作，`{type}` 和 `{attribute}` 间、若干个 `{attribute}` 间使用若干个空格分割，操作输入形式及其含义如下：

| type | attribute                                                    | 意义                                                         | 输出文本                                                     |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | `{adv_id} {name}`                                            | 加入一个 ID 为 `{adv_id}`、名字为 `{name}` 的冒险者，且未持有任何装备 | 无                                                           |
| 2    | `{adv_id} {equipment_type} {vars}`（equipment_type和vars的含义见下表） | 给予某个人某件装备，装备类型由 `{equipment_type}` 定义，属性由 `{vars}` 定义，**所有的瓶子初始默认装满** | 无                                                           |
| 3    | `{adv_id} {equipment_id}`                                    | 删除 ID 为 `{adv_id}` 的冒险者的 ID 为 `{equipment_id}` 的装备 | 无                                                           |
| 4    | `{adv_id}`                                                   | 查询 ID 为 `{adv_id}` 的冒险者所持有装备的价格之和           | 一个整数，表示该冒险者所有装备的价格总和                     |
| 5    | `{adv_id}`                                                   | 查询 ID 为 `{adv_id}` 的冒险者所持有装备价格的最大值         | 一个整数，表示该冒险者所有装备价格的最大值                   |
| 6    | `{adv_id}`                                                   | 查询 ID 为 `{adv_id}` 的冒险者的装备总数                     | 一个整数，表示该冒险者所有装备的数量之和                     |
| 7    | `{adv_id} {equipment_id}`                                    | 打印 ID 为 `{equipment_id}` 的装备的全部属性                 | 该装备的全部属性，格式见下文“属性打印方式”                   |
| 8    | `{adv_id}{equipment_id}`                                     | ID为 `{adv_id}` 的冒险者使用其 ID 为 `{equipment_id}` 的装备 | 装备在使用时会产生输出，除此之外无额外输出。                 |
| 9    | `{adv_id}`                                                   | 打印ID为 `{adv_id}` 的冒险者的所有状态。                     | 一个字符串表示冒险者的状态： `The adventurer's id is {adv_id}, name is {name}, health is {health}, exp is {exp}, money is {money}.` |

| 装备类型      | equipment_type | vars                                  |
| ------------- | -------------- | ------------------------------------- |
| Bottle        | 1              | id name price capacity                |
| HealingPotion | 2              | id name price capacity efficiency     |
| ExpBottle     | 3              | id name price capacity expRatio       |
| Sword         | 4              | id name price sharpness               |
| RareSword     | 5              | id name price sharpness extraExpBonus |
| EpicSword     | 6              | id name price sharpness evolveRatio   |

| 装备类型      | 属性打印方式                                                 |
| ------------- | ------------------------------------------------------------ |
| Bottle        | `The bottle's id is {id}, name is {name}, capacity is {capacity}, filled is {filled}.` |
| HealingPotion | `The healingPotion's id is {id}, name is {name}, capacity is {capacity}, filled is {filled}, efficiency is {efficiency}.` |
| ExpBottle     | `The expBottle's id is {id}, name is {name}, capacity is {capacity}, filled is {filled}, expRatio is {expRatio}.` |
| Sword         | `The sword's id is {id}, name is {name}, sharpness is {sharpness}.` |
| RareSword     | `The rareSword's id is {id}, name is {name}, sharpness is {sharpness}, extraExpBonus is {extraExpBonus}.` |
| EpicSword     | `The epicSword's id is {id}, name is {name}, sharpness is {sharpness}, evolveRatio is {evolveRatio}.` |

#### 数据范围与操作限制

##### 变量约束

| 变量                                                         | 类型   | 说明                               |
| ------------------------------------------------------------ | ------ | ---------------------------------- |
| id                                                           | 整数   | 取值范围：0 - 2147483647           |
| name                                                         | 字符串 | 保证不会出现空白字符               |
| price                                                        | 长整数 | 在 long 精度范围内，且保证不小于 0 |
| capacity, efficiency, expRatio, sharpness, extraExpBonus, evolveRatio, health, exp, money | 浮点数 | 在 double 精度范围内               |

##### 操作约束

- 操作数满足 1 \leq m \leq 20001≤*m*≤2000。
- **保证所有冒险者与装备的 ID 两两不同。**
- 操作2-9：冒险者 ID 一定存在。
- 操作 3,7,8：冒险者一定持有该 ID 的装备。
- 操作 4：若冒险者不持有任何装备，则输出 0。
- 操作 5：冒险者一定持有至少一个装备。

#### 测评方法

输出数值时，你的输出数值需要和正确数值相等。

**假设你的输出值 x_{out}\*x\**o\**u\**t\* 和正确数值 x_{std}\*x\**s\**t\**d\* 之间的绝对或相对误差小于等于 10 ^ {-5}10−5，则认为是相等的，即满足**

\dfrac {|x_{std} - x_{out}|}{\max(1, |x_{std}|)} \leq 10^{-5}max(1,∣*x**s**t**d*∣)∣*x**s**t**d*−*x**o**u**t*∣≤10−5

#### 输入样例

```
17
1 2 Co20ocvblT
1 30 Al8QnWnkS7
1 91 pqWY5UNcm4
2 91 1 26 6DlfOJGzfY 74 96.3964
2 2 6 35 yv58Ec49pK 2 65.161 68.6988
2 2 1 71 FEw7siBqbW 64 66.534
2 91 2 44 OLy4CqtmrO 45 60.135 13.2503
2 30 1 56 H2EvYaqUXD 0 64.7676
2 91 6 65 Wjsn3jVy6E 60 20.1061 23.1743
2 2 2 28 0WnMAYPzUH 37 27.0554 10.4833
3 30 56
4 30
5 91
6 91
7 91 65
8 2 35
9 91
```

#### 输出样例

```
0
74
3
The epicSword's id is 65, name is Wjsn3jVy6E, sharpness is 20.1061, evolveRatio is 23.1743.
Co20ocvblT used yv58Ec49pK and earned 65.161.
yv58Ec49pK's sharpness became 4476.4825068.
The adventurer's id is 91, name is pqWY5UNcm4, health is 100.0, exp is 0.0, money is 0.0.
```

### 第五部分：补充

- “装备类”包括 Bottle, HealingPotion 等 6 个不同的类，而冒险者需要拥有一个可以承载这 6 个装备类的容器。为了避免为 6 个装备类分别维护容器的麻烦，我们可以使用“向上转型”，在 Adventurer 类中统一维护一个承载 Equipment 的容器，并让 6 个装备类全部继承自 Equipment 类。由于“多态”的特性，在向上转型后对象仍然不会失去其原先的装备性质。
- 建议在Equipment类中定义一个`used`方法，该方法的代码用于描述这个装备被使用时会发生的效果，在所有的装备子类中都去重写这个`used`方法，另外还应该为所有需要打印描述字符串的类重写`toString`方法。在`Adventurer`类中定义`HashMap<Integer, Equipment>`类型的`equipments`属性（也可使用其他容器），表示冒险者拥有的全部装备，在执行操作8时，直接调用该装备对象的`used`方法（因为有多态机制，这里不需要强制转型，直接调用就可以保证行为正确）。
- 冒险者使用装备的过程中，是对冒险者属性和装备自身属性的读取，运算和修改。如何才能让装备类的方法可以读取并修改他的使用者的属性呢？为`used`方法传递一个冒险者作为参数是一个好主意。既然加了冒险者作为参数，那不妨把方法名从`used`改为`usedBy`，这会让你的代码看起来就像是英文句子一样，写出self-documenting code（自我解释型代码）。
- 在 `Bottle` 和它的子类在 `filled` 为 `false` 时被使用就可以看作是一种异常行为。于是你可以在 `Bottle.usedBy` 方法中抛出一个异常（使用 throw 语句），在 `HealingPotion.usedBy` 调用 `super.usedBy` 时，不处理这个异常而是将其继续抛出到上层，而在冒险者循环使用装备的代码中将其捕获并打印出错误信息。以下代码是 Bottle 类中推荐的 usedBy 实现方法：

```java
@Override
public void usedBy(Adventurer user) throws Exception { // 因为有一个 Adventurer 参数，所以方法名写作 "usedBy" 会比 "used" 更加易于理解。
    if (!isFilled()) {
        throw new Exception("Failed to use " + getName() + " because it is empty.");
    }
    user.setHealth(user.getHealth() + capacity / 10);
    setFilled(false);
    setPrice(getPrice().divide(BigInteger.TEN)); 

    System.out.println(user.getName() +
            " drank " + getName() +
            " and recovered " + capacity / 10 +
            ".");
}
```

以下代码是 Adventurer 类中用于完成操作 8 所推荐的 use 实现方法。

```java
public void use(Equipment equipment) {
    try {
        equipment.usedBy(this);
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}
```

- **设计模式**是软件开发人员经过相当长的实践总结出来的最佳设计方案，在面向对象设计与构造课程中，你将逐步了解和掌握几种基本的设计模式，包括工厂模式、单例模式、生产者-消费者模式等。

  现在，希望大家可以了解**工厂模式**，这是在继承和接口实现中常用的设计模式。

  大家可以参考[链接](https://d.buaa.edu.cn/https/77726476706e69737468656265737421e7e056d23525665f710ac7af9758/design-pattern/factory-pattern.html)中的介绍，也可以自行查阅资料。这将帮助你更轻松的完成日后的作业 :)

- 本次作业的“测验”部分仍然是与 Git 相关的题目，均可在 [Git Pro](https://www.progit.cn/) 中找到答案。测验题回答正误均没有影响，不计入成绩，我们将在提交正确或提交机会用尽时给出题目的详细解析，希望同学们阅读后能有所收获。