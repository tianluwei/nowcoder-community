##nowcoder-community

+ *问题的出现不是让你止步不前，而是为你指明方向。*
+ *年轻人，你的职责是平整土地，而非焦虑时光。你做三四月的事，八九月的事自有答案*
+ *我可以不是最好的那个，但我最少得是最努力的那个。*

#####(5.7~)
###第一章：
1. 环境搭建
	+ pom.xml中主要依赖有：spring-boot-starter-web(2.5.0)、spring-boot-starter-test、spring-boot-starter-thymeleaf、mysql-connection-java、mybatis-spring-boot-starter(2.1.4)、spring-boot-starter-mail(邮件服务)、commons-lang3(StringUtils)、kaptcha(2.3.2生成验证码)
	+ application.properties
2. 开发社区首页
	+ 用一个Page对象来封装页面的信息。
	+ action：/index GET 查询当前页面消息列表的数据，将对应的user属性也查询出来，然后放到Model（等同于HttpServletRequest）中返还给index.html页面。
3. 一些细节：
	+ @RequestParam注解，就相当于是request.getParameter()，是从request对象中获取参数的。有时，我们也愿意利用请求路径本身来传参，即将参数拼到路径里，如/xxx/1，这里的1就是参数，那么在解析路径的时候，也是能获取到这个参数的。而@PathVarible就是解析路径，从中获得对应级次的参数。
	+ 日志级别：trace < debug < info < warn < error


###第二章：(5.16~5.)
1. 注册功能+发送邮件激活邮箱。
	
2. 生成验证码。
3. 开发登录退出。
3. 一些细节：
	+ 用cookie来进行会话管理。登录状态保存。
	+ Cookie是服务器发送到浏览器，并保存到浏览器端的一小块数据。
浏览器下次访问该服务器时，会自动携带该块数据，将其发送给服务器。
测试cookie，cookie会先从服务端发给客户端，然后客户端再请求时会携带cookie。
Session是JavaEES的标准，用于在服务端记录客户端信息。
数据存放在服务端更加安全，但是也会增加服务端的内存压力。
session的特点是，在响应的时候，通过cookie传一个sessionID，给浏览器存。
	+ 一个小问题是：激活邮箱时显示userId为0，这是userId没有注入的问题。在mapper文件中keyProperty属性可以把id注入到实体对象user中。
	+ 更新远程分支列表：git remote update origin -prune
	+ 如果mapping方法中的参数不是普通的参数，而是实体对象，比如User。Springmvc就会把这个User装到model里，页面上就可以直接获取。如果是String或基本类型，就不会放到model里。它是从request请求中携带过来的，所以直接request里拿也可以。
3. 



















---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---
---

#Markdown Cheat Sheet:
heading:
- #
- ##
- ###

Bold(黑体):**haha**
Italic（斜体）：*haha*
Blockquote：
>haha
Ordered List：

1. haha
2. haha
3. jlajdf 

+ haha
+ haha
+ haha

`int clude<main>{
	println("hello world");
}`
--------
fsljfa 
[haha](www.)
![haha](.png)

|sytax| des|
|------|---------|
|header|
```
{
	"hallo"
	"hahah"
}
```


== jlajsl==

__[pica](https://nodeca.github.io/pica/demo/)__ hahah

8-)
---
___

***
__bold__
**bold**

~~strike~~



*[HTML]:Hyper Text Markup Langurage
:::warning
*here be drage*
:::

