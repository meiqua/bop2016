# bop2016
属性图数据库定义好了，都是通过propertyRepository操作。比如：  
```Java
// 基本的操作
importRepository.save();

// 我定义的两个操作
importRepository.findByNameAndType();
prppertyRepository.findByOneHopPropertiesName();

// 虽然我就定义了个函数名，但是已经被spring自动实现了
```

直接用intelliJ idea打开（import project --> build.gradle），点击运行(也许还需要选下spring boot)。  
或者用cmd, gradlew build --> java -jar ./build/libs/---.jar  
然后访问localhost:8080，能看到json就说明运行成功
  
现在只是个框架，能运行，能操作图数据库，  
在mvcController直接返回对象就是json数组了  

还需要：  
* 生成图数据库
* 写个类从微软的api那拿数据
* 把我们的主要思路写出来,我想的是写在mvcController那个方法里

  
主要参考网页：  
https://spring.io/guides/gs/accessing-data-neo4j/  
https://spring.io/guides/gs/accessing-neo4j-data-rest/  
https://spring.io/guides/gs/rest-service/  

## update  
* 增加了事务管理，现在是30000 operation/transaction  
* 增加了csv file reader，我想的是把数据库存到本地然后生成图数据库  
* 使用更加底层的API增加效率  
* findOneHopProperties findByNameAndType两个方法还没写
  
主要参考网页  
http://iordanis.com/post/22677357894/import-large-graphs-to-neo4j-with-spring-data-fast

http://stackoverflow.com/questions/9568203/save-method-of-crudrepository-is-very-slow  

## update
全用的阻塞http，返回结果基本正确，耗时略长。。最后拿了个衣服  
简单说明：hopMethod是所有1-2-3hop的跳转路径，client根据flag制定跳转细则；  
          hopPath打算进行异步http控制,后来把自己绕进去了，时间也不够了就全用的阻塞。。  
          afid-auid-afid的路径不好直接用微软api查，我们就用了个neo4j图数据库存到本地查；  
          怎么存的代码在一次烦躁过程中全删了（当时遇到bug就把多余的全删了），  
          反正是照着参考网页一步一步来的，重来也容易，我就懒得再加上原来代码了。  
改进建议：首先得改成异步吧，然后httpClient最好一直连接着，查一次一个请求不知道握手的时间花了有多少了。。  
不足反思：首先是抽象的程度不够，所有路径都是手动写的函数，应该弄个更高层次的抽象，自动生成；  
          然后是异步多线程啥的没怎么编过，只有个概念，第一次实际动手手忙脚乱的把自己绕晕了；  
          3hop的时候感觉没大局观，写的代码只能一条线一条线读，我觉得高手应该能写成一束一束读；  
          也没啥测试的概念，计时什么的后来都很难加进去。
