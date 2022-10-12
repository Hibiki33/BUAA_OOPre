# 第零次作业

## 训练目标

- 学习使用 `git`以及 `gitlab`相关操作
- 学习使用课程网站提交

## 任务一：git 学习

### step 0 git 安装与配置

#### git 的安装

##### Linux

```bash
sudo apt-get install git
```

##### Mac OS X

从 AppStore 安装 Xcode，运行 Xcode，选择菜单 "Xcode" -> "Preferences"，在弹出窗口中找到 "Downloads"，选择 "Command Line Tools"，点 "Install" 即可完成安装。

##### Windows

在[官网](https://git-scm.com/downloads)上选择最新版本进行下载安装。

#### git 的配置

```bash
git config --global user.name "你的名字"
git config --global user.email "21xxxxxx@buaa.edu.cn"
```

利用上述指令配置 git，注意将 “你的名字” 和 “21xxxxxx@buaa.edu.cn” 分别替换成你真实姓名和北航邮箱。

#### 配置 ssh key

参考[官网](https://docs.gitlab.com/ee/ssh/)上给出的介绍说明进行 ssh key 的配置

## 任务二：课程网站提交

### 问题描述:

输入两个整数 a, b 输出它们的和（保证结果在int范围内）

### 输入格式:

两个以空格分开的整数。

### 输出格式:

一个整数。

### 样例:

**stdin:**

```
2 3
```

**stdout:**

```
5
```

## 参考资料

1. [Git 使用心得 & 常见问题整理](https://juejin.cn/post/6844904191203213326)
2. [Git 廖雪峰教程](https://www.liaoxuefeng.com/wiki/896043488029600)文档中可以找到相关内容。

