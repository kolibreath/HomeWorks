# 根据一个源程序 写出一个解析器

- 使用根据空格分解： 不行 有些符号之间没有空格；
但是表示符号中间一定会有空格，
然后分析哪一些是表达式 哪一些是 关键字


- 只能使用字母字母读取
- 两个终端符之间一定会有空格， 能否使用递归完成？ 不可以 因为表达式连接起来的运算式不一定有空格