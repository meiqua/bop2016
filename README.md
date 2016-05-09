# bop2016
属性图数据库定义好了，都是通过propertyRepository操作。比如：  
```Java
// 基本的操作
propertyRepository.save();

// 我定义的两个操作
propertyRepository.findByNameAndType();
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


