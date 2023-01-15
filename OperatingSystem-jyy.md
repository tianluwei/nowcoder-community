1、ssh root@47.100.196.80

mysql -u root -p

2、vim：

dw：删除，直到下一个单词起始处；

de：删除，直到单词末尾。

x：删除字符。

d$：删除行末。

d2w：删除两个单词。

dd：删除整行。

2dd：删除两个整行。



operator+[number]+motion：

operator操作符，比如d代表删除。

number次数。

motion动作，代表在所操作的文本上的移动，例如 w 代表单词(word)，$ 代表行末等等。





u：撤销。

U：撤销在一行中所做的改动。

ctrl+R：撤销掉，撤销命令。



移动：

2w：使光标向前移动两个单词。

2e：使光标向前移动两个单词，移动到两个单词的末尾。

0：移动到行首。

e：使光标移动到单词末尾。



p： p 将最后一次删除的内容置入光标之后。

r：替换。（一个字符）

cw：要改变文本一个单词。

ce：改变文本直到一个单词的末尾。

c$：改变文本直到行末。



gg：回到开始位置。

G：回到文件末尾。

ctrl+g：显示当前编辑文件中当前光标所在行位置以及文件状态信息。

500（行号）+G：回到该行。



搜索类命令：

/+字符：查找匹配字符串。

?+字符：逆向查找匹配字符串。

n向下一个方向。N向相反方向。

ctrl+o：回到之前的位置。

ctrl+i：回到新的位置。（ctrl+o的回退）



%：查找匹配的括号。

:s/thee/the：把thee替换成the。

:s/thee/the/g：把这一行中thee替换成the。

:%s/old/new/g：把整个文件中的old替换成new。



在vim内执行外部命令：

:!+ls：执行外部的ls命令。

:!+pwd：执行外部的pwd命令。



:w+fileName：将当前文件以fileName保存。

v：进入可视模式，进行选取（文本）。之后可以用:w保存或:d删除。

:r+fileName：提取文件内容，将提取进来的文件将从光标所在位置处开始置入。

:r+ !ls：将读取 ls 命令的输出，并把它放置在光标后面。



o ：将在光标的下方打开新的一行并进入插入模式。

O ：将在光标的下方打开新的一行并进入插入模式。

a：将可在光标之后插入文本。

A：可以在光标所在行的行末之后插入文本。

R：替换。可连续替换多个字符。



j$：光标移动到下一行的末尾。

y：复制。

yw：复制一个单词。

p：粘贴。

:set ic：（ignore case）忽略大小写。主要用来查找。

:set noic：不忽略大小写。

:set hls is：高亮查找。

:nohlsearch：移除匹配高亮。

/ignore\c <回车>：仅在一次查找中忽略大小写。



输入 :set xxx 可以设置 xxx 选项。一些有用的选项如下：

​    'ic' 'ignorecase'    查找时忽略字母大小写

​    'is' 'incsearch'    查找短语时显示部分匹配

​    'hls' 'hlsearch'    高亮显示所有的匹配短语

   选项名可以用完整版本，也可以用缩略版本。

 在选项前加上 no 可以关闭选项： :set noic



H/M/L：跳转到屏幕的开头，中间，结尾。

ctrl+u：上翻页。

ctrl+f：下翻页。



i1<ESC>   		q1  yyp<C-a>  q98@1：在文章中生成1，到100的数字。【recording and replaying】

<C-v>24l 4j d$ p：将4行的左右文本替换。

有多少种方法回到第一行开头：gg、3k0、H。



.vimrc配置文件中，syntax on  开启语法高亮。

less is more。集中、专注之后看似你得到的少，但其实你得到的更多。

less中：&是过滤  想要的字符。



编译：gcc hello.c -o hello

反编译：objdump -d hello > output

统计时间：time ./myprog < data |tee output

追加append：<<

用root执行整条命令：#

统计磁盘使用情况：du -sc /usr/share/* | sort -nr | more

统计代码行数：find . | grep '\.c$\|\.h$' | xargs wc -l

寻找合适的程序来打开文件：xdg-open。（macOS：open）



把cd1改成curl，把wd1改成wget。

安装tldr：yum -y install npm
npm install -g tldr（有可能报错，报错则再执行一次）。安装成功后，第一次执行会比较慢



$0：是脚本名称。（只有以bash file.sh 启动时才会是脚本的名称。）

$?：是上个脚本的错误代码。（返回值）

$_：会获取上条命令的最后一个参数。

!!：执行命令权限不够，sudo !!。可以执行上一条命令。

**foo=$(ls)：把ls的执行结果，保存在变量foo中。**

echo "haha $(pwd)"：pwd的执行结果作为输出的一部分。

进程替换（process substitution）：cat <(ls) <(ls ..) 会连续将ls和ls ..的结果输出。

​	ls会将输出放到临时文件(/dev/fd/63，和/dev/fd/62)中，然后把两个文件连接起来。

$#：代表给定参数的个数。

$$：代表pid。

$@：可以展开为所有的参数。

2>：重定向标准错误流。

-ne：比较运算符。（non equal，不等于）

ls *.sh   查看所有的sh后缀文件。

ls project?   列出1个字符，1个特定的项。

convert image.{png,jpg} image.npg    正则表达式{}代表边界，最小是png，最大是jpg。或

mkdir {a,b}/{a..f}  在ab下建a、b、c、d、e、f 文件夹。

diff <(ls foo) <(ls bar) 查看两个文件夹下文件有什么不同。

#!/usr/bin/python  (sharp bang)shell通过它了解怎么运行这个程序。shell用首行识别到，需要用python解释器运行这个程序。

#!/usr/bin/env python  会在环境变量那些路径中找Python 二进制文件。接着执行，用它去解读这个脚本。

test   -f  命令：作比较。 【-f 查询当前文件是否存在。】

tldr（too long don't read）命令，man的简单版，会给出一些示例。

Bash中：ctrl+w删除最后一个单词。ctrl+u从当前删除到当前行的第一个位置。ctrl+a回到当前行第一个位置。ctrl+e回到当前行的末尾位置。ctrl+k删除到当前末尾位置。

pstree：查看进程树。

lsof：（list open file）查看打开的文件。

netstat -lntp：check what process is listening。

ss -plat：同上。（for TCP，add -u for UDP。）

lsof -iTCP。（-sTCP）用途同上。

lsof/fuser：for open sockets and files。

w/uptime：see how long the system has been running。

alias：给命令做个快捷键（换个名字）。shortcuts。例如：alias ll ='ls -latr'。创建alias ll。

find . -name src -type d：在当前目录中递归，寻找名叫src的文件夹。

命令行设计哲学：看起来什么都没发生，就是返回0，就是有事发生。

find . -name "*.tmp" -exec rm {} \;	find .tmp文件并执行rm操作。

ctrl+R：backward search。倒序搜索。

fish：the friendly interactive shell。这个非常好用，建议试试。

tree：查看当前文件目录。而不用递归。

tldr：https://tldr.ostera.io/。非常好用。