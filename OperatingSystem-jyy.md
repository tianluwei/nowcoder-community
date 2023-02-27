## **动手实践是认识新事物的最好方法**

**less is more.**

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

**ctrl+o**：回到之前的位置。【类似于idea里的navigate】

**ctrl+i**：回到新的位置。（ctrl+o的回退）



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

a：将可在光标之后插入文本。append

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

**反编译：objdump -d hello > output**

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



初始化数据库：mysqld --initialize --user=root

启动数据库：systemctl start mysqld



### **第3讲vim** ：[:help :command]，或者[:help command]（注意:命令和normal模式下不同），查看command的说明。

:sp打开两个窗口，方便去看文件的不同部分。一个buffer可以同时被零后多个window打开。

:qa退出所有的window

ctrl+wj向下跳转窗口

ctrl+wk向上跳转窗口

ctrl+d向下跳转多少行（默认半屏）。ctrl+u向上，ctrl+f向下。

:tabnew打开一个新的tab

gt从一个tab跳转到另一个tab

w向后跳转一个单词；b向前跳转一个单词。e跳转到下一个单词末尾。(backward)（u->undo撤销）（ctrl+r->redo重做）

0跳转到行首。$跳转到行末。^跳转到行首第1个非空白字符。

fo跳转到右边的第一个字母o（first，第一个）【find】

Fo跳转到左边离o最近的位置

td跳转到右边第一个字母d左边。

Td跳转到右边第一个字母d右边。（末尾tail）【to】

dw向后删除一个单词。db向前删除一个单词，删除到前一个单词。de删除到一个单词的末尾（可以从中间开始）。

c（change）删除并插入。c2w

y复制。（yank，提取，拉拽）

vim的复制粘贴默认不是操作系统的复制粘贴。

~改变字符的大小写。

4w、4j、4e、4b。

4dd删除4行。4dw删除4个单词。

:set nu设置行数。:set rnu设置相对行数。

a（around）包含、i（inside）【vim修饰符】

c2w改变两个单词。删除两个单词并插入。

ci[改变[ ]里面的内容。ci'改变单引号里面的内容。

di(删除()里面的内容。

.会重复之前的编辑命令。

### **第4讲**数据整理Data Wrangling

journalctl查看系统日志。

把日志特定字段前面的全丢掉：cat ssh.log |sed 's/.*sshd//'|less

**.任意字符。*零次或多次。+一次或多次。[]匹配多种字符中的一种。?一次或0次。在+或*后面加?表示非贪心匹配。**

echo aab|sed 's/[ab]//'默认只匹配一次，最前面的。

echo aab|sed 's/[ab]//g'。尽可能多匹配。（可能是global）

echo bafafafbbbaaavab|sed 's/[ab]//g'。移除所有的a和b，只剩下fffv

 echo babababa|sed -E 's/(ba)*//g' 把连在一起的ba全部移除掉。

sed要加-E，因为sed很老了，它只支持很旧版本的正则表达式。加上-E就会用一套支持更多的更现代的语法。如果没有-E，就要在括号前加\，来告诉它使用特殊含义的括号。例如：echo babababa|sed 's/\(ba\)*//g'。

echo abcababc|sed -E 's/(ab|bc)//g'。移除所有（g）的ab或bc。

Capture group捕获组。( )	捕获组编号的方法：\2。可以这样用： cat test.log |sed -E 's/1月 [0-9]+ [0-9:]+ [a-z|0-9]+ (sshd)\[([0-9]+)\]: (Connection closed by|fatal: Read from socket failed: Connection reset by peer)/\2/'

uniq输出去重后的输入。uniq -c输出重复行的次数。

sort -nk1,1排序。-n数值排序，-k1允许你在输入中选中空白字符分割的一列作为排序。,1我想要计数第1列到第1列。

paste -s把一大堆行的输入处理成以tab分割的一行。-d,使其以,分割而不是tab。

awk '$1==2376 && $2 ~ /^1.*4$/{print $0}'表示第一个元素等于2376，第二个元素匹配1开头4结尾的所有列。

awk 'BEGIN {rows=0} {rows+=1} END {print rows}'甚至可以样统计行数。或awk 'BEGIN {rows=0} $1==2376 && $2 ~ /^1.*4$/{print $0} {rows+=1} END {print rows}'。

bc是一个计算器。

awk '$1 != 1 {print $1}'|paste -sd+|bc -l把数值非1的所有打印出来并计算总和。

xargs接受若干行输出，把它们转为参数形式。

### 第5讲Command-Line Environment

job control：

sleep 5 睡眠5秒

ctrl+c，发signal叫做SIGINT，interrupt program。

ctrl+\，SIGQUIT，terminate program。

ctrl+z，SIGSTOP signal，say it's suspend。在后台停止状态。bg %1让第1个job继续开始运行。

&，让program在后台运行。

jobs，显示suspended的job。

kill，可以发送任何unix signal。kill -STOP %1，让第1个job停下来。

nohup，即使退出了，或者发出hung up信号，还是会在后台运行。

kill -HUP %1，hung up掉第1个job。

**tmux**：sessions、windows、panes。

ctrl+b(a按完之后松开，再按)d：to detach，从当前tmux session中分离出来。（ad直接exit了，分离是什么？）【快捷键可以改】

tmux a：reattach to the session.

Shell start a process call tmux,tmux start a different process which is the shell we current in.tmux process is seperate from the original shell process.

tmux ls：列出有几个sessions。

ctrl+a，c：create a new window。【ctrl+b，c】

ctrl+a：jump between new tabs.加p键 for previous。加n键 next window。【ctrl+b】

ctrl+d：exited。【有pane时是退出一个pane】

ctrl+b，1：跳转到窗口1.

ctrl+b，"：分割成两个pane。水平

ctrl+b，%：分割成两个pane。竖直、垂直

ctrl+b，上下左右箭头：在pane之间跳转。

ctrl+b，[空格]：is pretty neat,equispace the current ones.let you through different layouts.

ctrl+a，z：you can zoom into this。【ctrl+b，z】把当前窗口放大。



alias：remap a short sequence of characters to a long sequence。如：alias ll="ls -alh"

alias+command：what the command that you areexecuting actually is。



configuring your shell more and more.

在~/.bashrc文件中配置，alias ls="ls"，then bash生效。

PS1="> "给前面的prompt换成> 。PS1="\w >> "。前面加上当前work directory。

vim ~/.config/alacritty，可以改变屏幕的配置，比如显示的字体size等。 

github上可以搜：dotfiles，找各种工具相关配置。

ln -s：symlink。链接/符号链接。

ssh let you execute commands remotely.

scp：remote copy files。

rsync：copy file，continue from where it stoped。

shell中的颜色（比如ls）是怎么来的：printf "\e[38;2;255;0;0m haha \e[0m"

logger "message"：往系统日志中添加一条message。journalctl 查看系统日志。

### 第六讲：version control

【？？？】



### 第七讲：debugging and profiling

**debugger** is a tool that will wrap around your code and will let you run your code.

Python debugger：python -m ipdb bubble.py

gdb --args sleep 20；run开始运行，（调试）；ctrl+c：Program received signal SIGINT, Interrupt。



Your program is a black box. 

strace ls -l：trace system calls and signals。

pyflakes：simple Python 2 source checker。

writegood：英语语法、用词检查。



profiling：is how to optimize the code.

time工具，衡量userTime、sysTime。

CPU profiler：cpu分析器、分析工具。

python -m memory_profiler mem.py：mem分析工具。

kernprof -l -v urls.py：时间消耗分析工具。比如：哪一段消耗时间最多。

perf：performance analysis tool for unix.

perf stat stress -c 1：查看当前系统各个性能（cpu utilized、page fault等）。

du：（disk usage）

nudu：interactive version

lsof：list of open file。

### 第八讲：Metaprogramming

构建系统工具：需要定义*依赖*、*目标*和*规则*。【开始、过程、结尾】您必须告诉构建系统您具体的构建目标，系统的任务则是找到构建这些目标所需要的依赖，并根据规则构建所需的中间产物，直到最终目标被构建出来。



持续集成系统【DI】：随着您接触到的项目规模越来越大，您会发现修改代码之后还有很多额外的工作要做。您可能需要上传一份新版本的文档、上传编译后的文件到某处、发布代码到 pypi，执行测试套件等等。或许您希望每次有人提交代码到 GitHub 的时候，他们的代码风格被检查过并执行过某些基准测试？如果您有这方面的需求，那么请花些时间了解一下持续集成。

这些工具大部分都是免费或开源的。比较大的有 Travis CI、Azure Pipelines 和 GitHub Actions。它们的工作原理都是类似的：您需要在代码仓库中添加一个文件，描述当前仓库发生任何修改时，应该如何应对。目前为止，最常见的规则是：如果有人提交代码，执行测试套件。当这个事件被触发时，CI 提供方会启动一个（或多个）虚拟机，执行您制定的规则，并且通常会记录下相关的执行结果。您可以进行某些设置，这样当测试套件失败时您能够收到通知或者当测试全部通过时，您的仓库主页会显示一个徽标。

本课程的网站基于 GitHub Pages 构建，这就是一个很好的例子。Pages 在每次`master`有代码更新时，会执行 Jekyll 博客软件，然后使您的站点可以通过某个 GitHub 域名来访问。对于我们来说这些事情太琐碎了，我现在我们只需要在本地进行修改，然后使用 git 提交代码，发布到远端。CI 会自动帮我们处理后续的事情。





printf 'hello'|sha1sum	打印hello的sha 1，hash算法的值。
aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d  -

systemctl status：可以看到正在运行的所有守护进程。

which或者type都可以找执行文件位置。

/sbin：基本的系统二进制文件，通常是root运行的。

/dev：设备文件，通常是硬件设备接口文件。

/etc：主机特定的系统配置文件。

/home：系统用户的主目录。

/lib：系统软件通用库。

/opt：可选的应用软件。

/sys：包含系统的信息和配置。

/tmp：临时文件（/var/tmp）通常重启时删除。

/usr/只读的用户数据

 - /usr/bin非必须的命令二进制文件。
 - /usr/sbin非必须的系统二进制文件，通常是由root运行的。
 - /usr/local/bin用户编译程序的二进制文件。

/var：变量文件，像日志或缓存。

**gdb**：gdb filename（可执行文件）

break lineNum：第几行打断点

run：开始运行

list：查看最近10行代码

print variableName：查看变量的名字

set var=value：给变量赋值

next：下一行

step：下一步。（如果有函数，会走到一个函数里面去。相当于step into。）

gcc main.c -g -Wall -Werror -o main：以gdb更友好的方式编译。-g是可debug，-Wall是输出warning，-Werror是。

setting watchPoint：Setting watchpoints is like asking the debugger to provide you with a running commentary of any changes that happen to the variables. Whenever a change occurs, the program pauses and provides you with the details of the change.：watch <var>。【可以watch多个值，然后continue】

continue：继续，相当于idea的resume键。

man -a：搜索并打开所有man中同名手册。

man -aw：显示所有手册文件的路径。

man -M：指定手册文件的搜索路径。

man -k <关键字>：根据关键字搜索，模糊搜索。

man -f <关键字>：根据关键字精确搜索。

> 比较两个文件的不同：md5sum命令。

> uniq：report or omit repeated lines。

> 系统监控：`jobs`, `ps`, `top`, `kill`, `free`, `demsg`, `lsof`。

### 第九讲security and cryptography【https://missing-semester-cn.github.io/2020/security/】





















不要抱怨，不要嫉妒，不要怨恨。我应该把手头的工作做好【优先级也挺高的】。爱是聪明的，恨是愚蠢的。先拿到今年的再说。

事情是做不完的，分清事物的优先级。底层>抽象。所以先熟悉工具GDB，然后再看习题课，再把os做了。这才是最重要的。