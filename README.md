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
全用的阻塞http，返回结果基本正确，耗时略长。。
