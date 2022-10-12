## 第三次作业指导书

### 第一部分：训练目标

- 学习接口相关知识，并实践如何使用接口来建立行为层次结构。
- 学会使用 Java 类库提供的类进行排序。
- 掌握容器的克隆方法，理解浅克隆 (Shallow copy) 和 深克隆 (Deep copy)
- 初步了解 git 分支的用法

### 第二部分：预备知识

#### 一、接口

前面我们提到了子类可以重写父类的方法，这使得子类的方法可以在父类的方法的基础上增加功能，或者实现一套和父类不同的新的功能。

倘若父类的**抽象程度**很高，以至于在父类中没有办法去编写一个实现具体功能的方法，我们可能会想是不是可以不写方法的具体实现语句，只定义方法签名呢？

比方说，正方形和圆形的面积计算很具体，假设为正方形和圆形建立了一个共同的抽象父类二维图形，此时如何去实现一个二维图形的面积呢？

比如下面的例子：

```java
class Square {
    private double length;
    public double getArea() { //你可以为一个正方形编写求面积方法
        return length * length;
    }
}

class Circle {
    private double radius;
    public double getArea() { //你可以为一个圆形编写求面积方法
        return radius * radius * Math.PI;
    }
}
```

很显然，我们无法为抽象的二维图形Shape类实现面积求解方法。此时，我们可以使用接口(Interface)来表示这个抽象的类，然后声明上述两个具体的类实现(implements)了这个接口：

```java
interface Shape {
    public double getArea(); // 你不能为抽象的`形状`概念定义求面积方法
}

class Square implements Shape {
    private double length;
    public Square(double length) {
        this.length = length;
    }
    @Override
    public double getArea() { //你可以为一个正方形编写求面积方法
        return length * length;
    }
}

class Circle implements Shape {
    private double radius;
    public Circle(double radius) {
        this.radius = radius;
    }
    @Override
    public double getArea() { //你可以为一个圆形编写求面积方法
        return radius * radius * Math.PI;
    }
}
```

之后，你可以用接口类型来引用实现了该接口的任意类型的实例化对象，并调用接口所声明的方法。需要注意的是，你不能用接口类型来**实例化**一个对象：

```java
class Main {
    public static void main(String[] args) {
        Shape myShape; // 声明一个Shape的变量， 这是还没有任何实例产生
        myShape = new Square(888); // 创建一个Square的实例，用myShape变量引用它。
        System.out.println(myShape.getArea());
        myShape = new Circle(888); // 创建一个Circle的实例，用myShape变量引用它。
        System.out.println(myShape.getArea());
        myShape = new Shape(); // Shape的概念过于抽象以至于实例化没有意义，这一行编译报错。

    }
}
```

需要注意的是，接口提供了行为的抽象机制。在上面的例子中，Square和Circle的共性在于其行为操作，因而使用接口是合适的。对于其他一些情况，多个类之间可能即有共性的行为，也有共性的数据属性，此时使用类建立抽象层次更加合适。

在编程时，尽量使用高层次的引用（比如抽象类的引用和接口的引用），避免使用实际子类型的引用的方式，叫做面向抽象编程。下面我们会通过本 Task 让大家体会这一点。

#### 二、浅克隆与深克隆

前面已经提到，在 Java 中，我们使用引用 (reference) 来操作一个对象。这表明，当我们在程序中写出形如 `Bottle bottle` 时，我们所声明的 `bottle` 变量只是一个引用，他可能会引用所有类型正确的实例。因此，如果我们需要对一个实例进行复制操作，就需要仔细考虑复制的是引用还是实例。请看下面的程序片段：

```java
class Main {
    public static void main(String[] args) {
        Shape shape1 = new Square(888);
        Shape shape2;
        shape2 = shape1; // 试图复制一个实例
        shape1.setArea(1); // 更改 shape1 引用的实例
        System.out.println(shape2.getArea()); // 输出 shape2 引用的实例
    }
}
/**
 * 输出：
 * 1
 **/
```

我们可以发现，上面的程序只是对引用进行了克隆。上面的程序首先创建了一个 Square 实例，并使用 shape1 引用它。之后声明变量 shape2，并让 shape2 引用了 shape1 所引用的实例。我们只创建了一个实例，shape1 和 shape2 均为同一个实例的引用。因此，通过 shape1 引用对实例进行的修改，也会在使用 shape2 访问该示例时体现。

上面的这种只克隆引用的克隆过程，称为 *浅克隆* (Shallow copy)。如果希望创造出一个“完整”的克隆，我们不仅要在编码时创建一个新的引用，还要创建一个新的实例：

```java
class Main {
    public static void main(String[] args) {
        Shape shape1 = new Square(888);
        Shape shape2 = new Square();
        shape2.setArea(shape1.getArea());
        System.out.println("shape1: " + shape1.getArea() +
            ", shape2: " + shape2.getArea());
        shape2.setArea(1);
        System.out.println("shape1: " + shape1.getArea() +
            ", shape2: " + shape2.getArea());
    }
}
/**
 * 输出：
 * shape1: 888, shape2: 888
 * shape1: 888, shape2: 1
 **/
```

这种克隆引用和实例的克隆过程，称为 *深克隆* (Deep copy)。

#### 三、容器中的克隆

我们已经了解到，Java 使用引用来操作实例，这导致克隆时既可以克隆引用，也可以克隆实例，即深克隆和浅克隆。在之前的两次作业中，同学们已经学会了容器的基本使用方法。容器提供了管理多个对象的方法，字如其名，容器中”容纳了若干个对象”。在拷贝容器时，深拷贝和浅拷贝的区别将会加大。

现在请大家思考，一个对象是否可以位于多个容器中呢？答案是肯定的，这是因为 Java 中只能使用引用来操作实例，容器也不例外；每个容器维护的是若干个实例的 **引用**。因此，如果我们希望将一个容器进行拷贝，我们有两种方法：

1. 使用浅拷贝，拷贝出的另一个容器管理的 *引用* 与原容器相同
2. 使用深拷贝，先拷贝出该容器中管理的所有实例，再依次添加至新容器中

假设现在有一个名为 `advs` 的 `ArrayList` 容器，该容器管理了若干个 `Adventurer` 类型对象的引用。由于 Java 中所有类型都继承于 `Object` 类，该类拥有 `clone` 方法，因此我们使用 `advs.clone()` 对该容器进行克隆：

```java
advs.get(0).setName("Old Name");
ArrayList<Adventurer> advsClone = advs.clone();
advsClone.get(0).setName("New Name");
System.out.println(advs.get(0).getName());
/**
 * 输出：
 * New Name
 **/
```

可以发现，这是一个浅克隆，只克隆了容器中的引用，而没有克隆 `Adventurer` 对象。在 [ArrayList.clone()](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/ArrayList.html#clone()) 文档中已明确说明，该方法返回一个浅克隆的新容器：

> `public Object clone()` Returns a shallow copy of this ArrayList instance. (The elements themselves are not copied.)

如果希望对容器每个对象本身都进行克隆，则需要遍历该容器，克隆其中的每个对象，并添加至新容器中。

在对容器进行克隆操作时，需要特别注意是否需要进行深克隆。

------

### 第三部分：基本要求

本次作业是本单元最后一次作业，仍需在上一次作业的基础上进行增量开发。

在本任务中，我们允许**冒险者雇佣并使用另一个冒险者**，且**赋予冒险者价值的概念**，把装备和冒险者都看作是**价值体 `commodity`**。同时，我们还要对冒险者游戏增加**版本管理功能**，与 git 版本管理工具进行类比，可将冒险者游戏的状态视为需要管理的数据，每执行一条指令视为进行一次 commit，并实现简单的新建分支、检出分支功能。

------

### 第四部分：题目描述

- 增加 Commodity 接口，并使冒险者 Adventurer 类和装备 Equipment 类实现 Commodity 接口。接口中应定义冒险者和装备的共有方法，包括 `useBy` 方法等。
- 将原先的冒险者持有的 *装备* 的容器，更改为 *价值体* 的容器（即该容器可以容纳所有实现了 Commodity 接口的类）。
- 定义冒险者的**价值**为其拥有的所有价值体的价值之和，即冒险者的价值是其**装备**的价值及其**雇佣的冒险者**的价值的和。
- 增加冒险者之间的**雇佣关系**：冒险者 A 雇佣冒险者 B，可以认为是把冒险者 B 看成一种**价值体**。此时冒险者 A 拥有价值体冒险者 B，之后冒险者 A 便可以像使用其他装备一样**使用**冒险者 B。
- 定义**冒险者 A 使用冒险者 B**，其效果为冒险者 A **按照价值从大到小、价值相同则按价值体 `id` 从大到小的顺序** 依次使用**冒险者 B 的价值体**，价值体的价值指的是所有价值体在**本次使用前**的价值。我们规定：如果当前使用到了冒险者 B 雇佣的冒险者 C，则冒险者 C 要按照如上顺序使用其拥有的价值体，这些价值体将作用于最开始使用的冒险者，在此处即为冒险者 A。
- 新增**版本管理**功能：我们仿照 git 中的分支机制进行版本管理。将每一条执行的指令视为一次 commit，**初始状态下默认分支名称为`1`**，需要支持“创建分支并检出该分支”功能，以及“检出”功能。与 git 相同，每次 commit 都将移动当前 HEAD 指针所指向的分支指针，也就是说，假设当前处于 `br` 分支，执行了若干条指令（相当于在 `br` 分支上进行了若干条 commit）后，**`br` 分支也会发生更改**。

### 第五部分：输入/输出格式

第一行一个整数 m*m*，表示操作的个数。

接下来的 m*m* 行，每行一个形如 `{type} {attribute}` 的操作，`{type}` 和 `{attribute}` 间、若干个 `{attribute}` 间使用若干个空格分割，操作输入形式及其含义如下：

- 对 Task2 中的一些指令进行**少量修改**，**重点地方已经加粗**，并新增三条指令 10 ～ 12：

| type | attribute                                                    | 指令含义                                                     | 输出                                                         |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | `{adv_id} {name}`                                            | 加入一个 ID 为 `{adv_id}`、名字为 `{name}` 的冒险者，且未持有任何装备 | 无                                                           |
| 2    | `{adv_id} {equipment_type} {vars}（equipment_type和vars的含义见下表）` | 给予某个人某件装备，装备类型由 `{equipment_type}` 定义，属性由 `{vars}` 定义，所有的瓶子初始默认装满 | 无                                                           |
| 3    | `{adv_id} {id}`                                              | 删除 ID 为 `{adv_id}` 的冒险者的 ID 为 `{id}` 的**价值体** 如果被删除的价值体是冒险者，则解除雇佣关系，后续无法使用该被被解除了雇佣关系的冒险者 如果删除的价值体是装备，则丢弃该装备，后续该冒险者无法使用该装备 | 无                                                           |
| 4    | `{adv_id}`                                                   | 查询 ID 为 `{adv_id}` 的冒险者所持有**价值体**的价格之和 如果价值体是装备，则价值就是 `price` 如果价值体是冒险者，则其价值计算按照本 Task 最开始定义的规则 | 一个整数，表示某人所有**价值体**的价值总和                   |
| 5    | `{adv_id}`                                                   | 查询 ID 为 `{adv_id}` 的冒险者所持有**价值体**价格的最大值 如果价值体是装备，则价值就是 `price` 如果价值体是冒险者，则其价值计算按照本 Task 最开始定义的规则 | 一个整数，表示该冒险者所有**价值体**价格的最大值             |
| 6    | `{adv_id}`                                                   | 查询 ID 为 `{adv_id}` 的冒险者所持有的价值体总数 如果价值体是装备，则对总数的贡献是 1 如果价值体是冒险者，则**只要考虑被雇佣冒险者本身这一个价值体**即可，不需要考虑被雇佣冒险者所拥有的其他价值体，即对总数的贡献也是 1。 | 一个整数，表示某人所有**价值体**的数量之和                   |
| 7    | `{adv_id} {commodity_id}`                                    | 打印 ID 为 `{commodity_id}` 的**价值体**的全部属性           | 该**价值体**的全部属性，格式见下文“属性打印方式”             |
| 8    | `{adv_id}`                                                   | ID 为 `adv_id` 的冒险者按照价值**由大到小**的顺序使用其全部**价值体**，若价值相同则按照价值体的 `id` **由大到小**的顺序使用。（ 价值体价值为所有价值体**本次使用前的价值**） 若当前使用的是价值体是装备，这次使用的效果同 Task2 中的规定 若当前使用的价值体是冒险者，这次使用的效果已在 *第四部分* 中规定。 | 每个**价值体**在使用时就会产生输出，除此之外无额外输出       |
| 9    | `{adv_id}`                                                   | 打印 ID 为 `{adv_id}` 的冒险者的当前状态。                   | 一个字符串表示冒险者的状态： The adventurer's id is {adv_id}, name is {name}, health is {health}, exp is {exp}, money is {money}. |
| 10   | `{adv_id1} {adv_id2}`                                        | ID 为`adv_id1`的冒险者雇佣 ID 为`adv_id2`的冒险者            | 无                                                           |
| 11   | `{branch_name}`                                              | 在当前状态**新建分支**，分支名称为 branch_name。 与 git 类比，相当于在当前状态**创建一个名为 branch_name 的分支，并检出该分支**：`git branch ${branch_name} && git checkout ${branch_name}` 或 `git checkout -b ${branch_name}` | 无                                                           |
| 12   | `{branch_name}`                                              | **切换**到版本名称为 branch_name 的分支，之后的更改也将应用于该分支，详见“题目描述”部分。 与 git 类比，相当于**检出名为 branch_name 的分支**：`git checkout ${branch_name}` | 无                                                           |

`vars` 和 `equipment_type` 如下：

| 装备类型      | equipment_type | vars                                  |
| ------------- | -------------- | ------------------------------------- |
| Bottle        | 1              | id name price capacity                |
| HealingPotion | 2              | id name price capacity efficiency     |
| ExpBottle     | 3              | id name price capacity expRatio       |
| Sword         | 4              | id name price sharpness               |
| RareSword     | 5              | id name price sharpness extraExpBonus |
| EpicSword     | 6              | id name price sharpness evolveRatio   |

属性打印方式表格：

| 价值体类型             | 属性打印方式                                                 |
| ---------------------- | ------------------------------------------------------------ |
| Bottle                 | The bottle's id is {id}, name is {name}, capacity is {capacity}, filled is {filled}. |
| HealingPotion          | The healingPotion's id is {id}, name is {name}, capacity is {capacity}, filled is {filled}, efficiency is {efficiency}. |
| ExpBottle              | The expBottle's id is {id}, name is {name}, capacity is {capacity}, filled is {filled}, expRatio is {expRatio}. |
| Sword                  | The sword's id is {id}, name is {name}, sharpness is {sharpness}. |
| RareSword              | The rareSword's id is {id}, name is {name}, sharpness is {sharpness}, extraExpBonus is {extraExpBonus}. |
| EpicSword              | The epicSword's id is {id}, name is {name}, sharpness is {sharpness}, evolveRatio is {evolveRatio}. |
| **Adventurer**（新增） | The adventurer's id is {id}, name is {name}, health is {health}, exp is {exp}, money is {money}. |

#### 一、数据范围与操作限制

##### 变量约束

| 变量                                                         | 类型   | 说明                              |
| ------------------------------------------------------------ | ------ | --------------------------------- |
| id, adv_id, adv_id1, adv_id2, commodity_id                   | 整数   | 取值范围：0 - 2147483647          |
| name                                                         | 字符串 | 保证不会出现空白字符              |
| 装备的 price                                                 | 长整数 | 在 long 精度范围内，且保证不小于0 |
| capacity, efficiency, expRatio, sharpness, extraExpBonus, evolveRatio, health, exp, money | 浮点数 | 在 double 精度范围内              |
| branch_name                                                  | 字符串 | 只包含数字和字母                  |

##### 操作约束

- 操作数满足 1 \leq m \leq 20001≤*m*≤2000。
- **保证所有价值体的 ID 两两不同。**
- 操作2-9：冒险者 ID 一定存在。
- 操作 3,7：冒险者一定持有该 ID 的价值体。
- 操作 4,6：冒险者不持有任何价值体，则输出 0。
- 操作 5：冒险者一定持有至少一个价值体。
- 操作 10：雇佣和被雇佣的冒险者均已存在，且不是同一个冒险者。
- 指令 11：新建的 branch_name 不与已有的 branch_name 重名。
- 指令 12：检出的 branch_name 之前一定被新建过。
- 冒险者的雇佣关系不会存在循环雇佣的情况，每个冒险者最多仅能被一个其他冒险者雇佣一次。
- 初始状态下位于 branch_name 为 `1` 的分支。

#### 二、测评方法

输出数值时，你的输出数值需要和正确数值相等。

**假设你的输出值 x_{out}\*x\**o\**u\**t\* 和正确数值 x_{std}\*x\**s\**t\**d\* 之间的绝对或相对误差小于等于 10 ^ {-5}10−5，则认为是相等的，即满足**

\dfrac {|x_{std} - x_{out}|}{\max(1, |x_{std}|)} \leq 10^{-5}max(1,∣*x**s**t**d*∣)∣*x**s**t**d*−*x**o**u**t*∣≤10−5

#### 三、输入输出示例

##### 样例1

输入：

```
19
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
10 30 91
10 91 2
3 30 56
4 30
5 91
6 2
7 2 28
8 2
9 2    
```

输出：

```
282
103
3
The healingPotion's id is 28, name is 0WnMAYPzUH, capacity is 27.0554, filled is true, efficiency is 10.4833.
Co20ocvblT drank FEw7siBqbW and recovered 6.6534.
Co20ocvblT drank 0WnMAYPzUH and recovered 2.70554.
Co20ocvblT recovered extra 283.62987482.
Co20ocvblT used yv58Ec49pK and earned 65.161.
yv58Ec49pK's sharpness became 4476.4825068.
The adventurer's id is 2, name is Co20ocvblT, health is 382.98881482, exp is 10.0, money is 65.161.
```

##### 样例2

输入：

```
30
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
11 2
10 30 91
10 91 2
3 30 56
4 30
5 91
6 2
7 2 28
8 2
9 2    
12 1
10 30 91
10 91 2
3 30 56
4 30
5 91
6 2
7 2 28
8 2
9 2
```

输出：

```
282
103
3
The healingPotion's id is 28, name is 0WnMAYPzUH, capacity is 27.0554, filled is true, efficiency is 10.4833.
Co20ocvblT drank FEw7siBqbW and recovered 6.6534.
Co20ocvblT drank 0WnMAYPzUH and recovered 2.70554.
Co20ocvblT recovered extra 283.62987482.
Co20ocvblT used yv58Ec49pK and earned 65.161.
yv58Ec49pK's sharpness became 4476.4825068.
The adventurer's id is 2, name is Co20ocvblT, health is 382.98881482, exp is 10.0, money is 65.161.
282
103
3
The healingPotion's id is 28, name is 0WnMAYPzUH, capacity is 27.0554, filled is true, efficiency is 10.4833.
Co20ocvblT drank FEw7siBqbW and recovered 6.6534.
Co20ocvblT drank 0WnMAYPzUH and recovered 2.70554.
Co20ocvblT recovered extra 283.62987482.
Co20ocvblT used yv58Ec49pK and earned 65.161.
yv58Ec49pK's sharpness became 4476.4825068.
The adventurer's id is 2, name is Co20ocvblT, health is 382.98881482, exp is 10.0, money is 65.161.
```

##### 样例3

输入：

```
23
1 2 Co20ocvblT
1 30 Al8QnWnkS7
12 1
1 91 pqWY5UNcm4
2 91 1 26 6DlfOJGzfY 74 96.3964
2 2 6 35 yv58Ec49pK 2 65.161 68.6988
2 2 1 71 FEw7siBqbW 64 66.534
2 91 2 44 OLy4CqtmrO 45 60.135 13.2503
12 1
2 30 1 56 H2EvYaqUXD 0 64.7676
2 91 6 65 Wjsn3jVy6E 60 20.1061 23.1743
2 2 2 28 0WnMAYPzUH 37 27.0554 10.4833
10 30 91
10 91 2
3 30 56
4 30
12 1
5 91
6 2
7 2 28
12 1
8 2
9 2
```

输出：

```
282
103
3
The healingPotion's id is 28, name is 0WnMAYPzUH, capacity is 27.0554, filled is true, efficiency is 10.4833.
Co20ocvblT drank FEw7siBqbW and recovered 6.6534.
Co20ocvblT drank 0WnMAYPzUH and recovered 2.70554.
Co20ocvblT recovered extra 283.62987482.
Co20ocvblT used yv58Ec49pK and earned 65.161.
yv58Ec49pK's sharpness became 4476.4825068.
The adventurer's id is 2, name is Co20ocvblT, health is 382.98881482, exp is 10.0, money is 65.161.
```

##### 样例4

输入：

```
30
1 329740070 sEhDbhnEnd
1 1851576059 8N3Vj7BkdZ
1 1151146527 T3NZDh4jCz
2 1151146527 1 1604225601 1u4QtP6lL9 5038843478073893142 28.0905
2 1151146527 4 419039688 Yuu2onZU2y 2877398768860155635 93.917
2 1151146527 4 1151636326 Fv1RvYmP0E 7285089275503127969 95.7149
2 329740070 2 1154770639 eRNkZX7yE8 8269076524323616536 13.2538 24.7047
2 329740070 4 1710411361 N8Nav2fayl 2308642044102240255 72.4425
2 329740070 2 112570669 hFC53lbVRK 4601284869343090065 47.6571 43.1437
11 2
10 1851576059 1151146527
5 329740070
7 329740070 1154770639
6 1851576059
12 2
10 1151146527 329740070
8 329740070
2 329740070 2 698684406 xD5l7UCB4Y 3742687023378757769 79.1502 74.4603
6 329740070
11 3
9 329740070
2 1851576059 4 870218830 mgyVlRqvxp 2506134180893997996 67.5698
2 329740070 3 1851077531 fHDuxXvDW6 773490031336115588 94.9004 58.1943
6 329740070
12 2
1 909784950 6REvh6RB7v
2 329740070 4 264476603 ibOoPOPubU 278140469188458658 33.6055
8 1851576059
2 329740070 2 192707537 V8mhiYQmm6 5462659141869010887 80.353 52.5216
11 4
```

输入：

```
8269076524323616536
The healingPotion's id is 1154770639, name is eRNkZX7yE8, capacity is 13.2538, filled is true, efficiency is 24.7047.
1
sEhDbhnEnd drank eRNkZX7yE8 and recovered 1.32538.
sEhDbhnEnd recovered extra 327.43115286.
sEhDbhnEnd drank hFC53lbVRK and recovered 4.76571.
sEhDbhnEnd recovered extra 2056.10362527.
sEhDbhnEnd used N8Nav2fayl and earned 72.4425.
4
The adventurer's id is 329740070, name is sEhDbhnEnd, health is 2479.62586813, exp is 10.0, money is 72.4425.
5
8N3Vj7BkdZ drank xD5l7UCB4Y and recovered 7.91502.
8N3Vj7BkdZ recovered extra 5893.54763706.
8N3Vj7BkdZ used N8Nav2fayl and earned 72.4425.
Failed to use eRNkZX7yE8 because it is empty.
Failed to use hFC53lbVRK because it is empty.
8N3Vj7BkdZ used ibOoPOPubU and earned 33.6055.
8N3Vj7BkdZ used Fv1RvYmP0E and earned 95.7149.
8N3Vj7BkdZ drank 1u4QtP6lL9 and recovered 2.80905.
8N3Vj7BkdZ used Yuu2onZU2y and earned 93.917.
```

------

### 第六部分：提示

- 冒险者和装备都是价值体，都可以**求价值**、**被使用**以及**字符串化**等，故一个推荐的设计方法是建立**价值体接口** ，接口中包含上述提到的三个方法，让冒险者 `Adventurer` 和装备 `Equipment` 都实现这个接口，这样在顶层逻辑中就只能看到**价值体**这一种类型，可使用该类型的引用去调用不同子类型对象的这三种方法，这种处理称为归一化处理，会在正式课程中有专门的论述和训练。
- 本次作业将会涉及到自定义排序，请学习如何给对象编写 `compareTo` 方法并实现 `Comparable` 接口，之后即可利用 `Collections.sort` 方法来给容器内对象进行排序，考虑到有许多知识同学们还没有学过，本章结尾会给出一个例子，同学们可以照猫画虎地完成，compareTo方法仅需要在Equipment类中定义，Equipment类的子类如果不重写该方法的话，将会与父类行为保持一致。

> 与 `Collections.sort` 会调用 `compareTo` 方法实现自定义排序，类似地，`TreeSet` 和 `TreeMap` 容器也会通过调用对象的 `compareTo` 方法，从而维护一个key对象有序的集合/映射。
>
> 另外，`HashSet` 和 `HashMap` 这两种容器会通过调用对象的 `hashCode` 方法和 `equals` 方法来将任意对象作为key来使用。**这个知识点非常重要，不过因为原理上与 compareTo 相似度较高便不在此处过多训练，请同学们务必弄懂其原理**。
>
> Java中许多内置的类，比如 `Integer` 和 `BigInteger` 等等都已经实现了`compareTo`、`hashCode`、`equals` 方法，所以你才可以直接把他们当作 `TreeMap` 和 `HashMap` 的key来使用。

```java
// Comparable接口的例子

import java.util.ArrayList;
import java.util.Collections;

class MainClass {
    public static void main(String[] args) {
        Score xiaoMing = new Score(120, 138);
        Score xiaoHong = new Score(125, 133);
        Score xiaoWang = new Score(119, 145);
        ArrayList<Score> scores = new ArrayList<>();
        scores.add(xiaoMing);
        scores.add(xiaoHong);
        scores.add(xiaoWang);

        Collections.sort(scores);

        for (Score score : scores) { // 如果你使用IDEA编写代码，可以试一试打出 "scores.for<TAB>" 这一串按键，快速补齐for循环
            System.out.println(score); // 试一试 "score.sout<TAB>"，自动补齐打印语句
        }
        /*
        运行结果如下，越大的对象越在后面（即升序排序）:
        Score{chinese=120, math=138}
        Score{chinese=125, math=133}
        Score{chinese=119, math=145}
         */
    }
}

class Score implements Comparable<Score> { // 后面尖括号里的类名基本上都会与前面的类名相同，表达“Score这个类可以与Score这个类相比较”这个意思。
    private final int chinese;
    private final int math;

    public Score(int chinese, int math) {
        this.chinese = chinese;
        this.math = math;
    }

    public int getSum() {
        return chinese + math;
    }

    /**
     * 自定义分数的比较规则，首先比较总分，总分相同比较语文，语文相同比较数学……
     */
    @Override
    public int compareTo(Score other) {
        if (this.getSum() < other.getSum()) { //首先比较总分，总分高的先录取
            return -1; // 返回 -1 代表 this 小于 other
        } else if (this.getSum() > other.getSum()) {
            return 1; // 返回 1 代表 this 大于 other
        }

        if (this.chinese < other.chinese) { //若总分一样，按语文分更高的先录取
            return -1;
        } else if (this.chinese > other.chinese) {
            return 1;
        }

        // 返回任何负值都代表this < other，于是可以这样子简写，
        // 下面三行关于math的比较和上面的五行关于chinese的比较是完全等价的。
        if (this.math != other.math) {
            return this.math - other.math; 
        }

        return 0; //返回0代表两被比较对象相等
    }

    @Override
    public String toString() {
        return "Score{" +
                "chinese=" + chinese +
                ", math=" + math +
                '}';
    }

}
```