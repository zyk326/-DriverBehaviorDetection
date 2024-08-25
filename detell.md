# POM.xml文件细节
* spring-boot-starter-web是spring mvc的加强版，是用于web开发的主流轻量级框架。  
* Fastjson 是一个java类库,可以被用来把Java对象转换成Json方式.也可以把Json字符串转换成对应的Java对象.Fastjson可以作用于任何Java对象,包含没有源代码已存在的对象。  
* spring-boot-starter-tomcat，是内嵌在web中的tomcat。  
* tomcat-embed-jasper，在jsp中添加java代码用，比如java标签库和EI表达式。  
* mybatis-spring-boot-starter，含mybatis启动器。  
* Druid是一个高效的数据查询系统，主要解决的是对于大量的基于时序的数据进行聚合查询。  
* mysql-connector-java，与mysql的连接器。  
* Lombok项目是一个Java库，它会自动插入编辑器和构建工具中，Lombok提供了一组有用的注释，用来消除Java类中的大量样板代码。仅五个字符(@Data)就可以替换数百行代码从而产生干净，简洁且易于维护的Java类。    
* servlet-api,servlet。
* jstl，JSP标准标识库。

# 配置maven
* 在设置里选定版本，我的配置文件和maven本地仓库在c盘.m2/*

# resources中的properties文件改成yml文件
* 两个格式本质相同，都是配置application的文件，只是格式有所不同。

# deadline
* 设计库，把application里的jdbc ： url改一下。
* 项目文件树框架搭起来。

# 前端尝试打开摄像头：
* uni-app失败 ： 原因是微信小程序开发时使用。
* 用jszip，将图片压缩，用blob二进制流传送给后台（异步上传）。:暂不需要
* 成功打开摄像头，下一步，使用间歇调用拍照功能，得到所谓的帧率。
* 定时函数setInterval,img转base64。

# 后端找方法解析图片
* tess4J ，可以做ocr文字识别。

# 先做java的人脸识别
* 用opencv
>关键地方：
>>配置opencv，具体：添加库，添加buildx64，添加java x64启动类。（VM option） https://blog.csdn.net/qq_45979629/article/details/123695294
>>编码转换，UTIL中。Mat -> Base64, Base64 -> Mat
>>用了一个ansBase64，放置返回的图像信息。考虑到status字段。
>>前端base64转绘图格式
>>vue发post请求，用axios，先安装.妈的没安装。在项目路径安装。

# 项目结构有很大问题
* 跟做项目，spring boot + Vue
* 先学会搭文件树框架（其中首先学Vue的项目框架）
* nodejs装好，用来使用npm
* 安装vue-cli (用3的版本),使用vue-cli创建vue工程（vue create xxx）
* 在idea中配置configuration，使用npm启动serve。（修改package.json的serve项，在启动命令后加入--open选项，自启页面）
> 自启页面是0.0.0.0 : 修改node_modules/@vue/cli-service/lib/commands/serve.js。把host改为127.0.0.1;
* UI用element-puls。在项目所在目录命令行安装。
> 注意事项是，需要在main.ts中引入ele-p(修改import和createApp.use(ElementPuls))

* 前后端分离，在项目目录下创建vue和spring两个文件夹，分别放前端和后端。后端的src和pox.xml文件在spring Initial网站选定需要的依赖下war，覆盖idea自动生成的src&pox.xml。
>改vue路径后，configuration的npm路径也要改。

* 后台用mybatis-plus交互数据库。

* 前端vue项目下安装axios插件，用作数据交互。（npm install axios -S）
> 在vue/src里创建utils包，下加入配置文件（交互向）request.js

* 跨域说的就是跨端口了，前端8080端口，后端9090端口，前端发送请求时，有个请求路径，这里为了省略前缀，在vue项目下创建vue.config.js文件，配置跨域，表现出来就是字符串的替换。




# 课程设计配置：
1. 创建VUE项目，改自启浏览器，改serve路径，加element-plus包，加axios包，加request配置，加vue.config.js配置。
2. 创建spring项目，加opencv库。

# 当前的问题：
1. 前端页面不好看
2. 后端反馈有延迟
3. 没有动作识别 

# solution
3. 用自己的模型，训练出来。 -》找数据集 IEEE数据集 ，用视频抽帧，制作数据集，1-n重命名
两个前置：infotxt 输出后，在库exe文件夹中cmd：
opencv_createsamples.exe -vec F:\conf\opencv14\trans\opencv_bin\info.vec -info F:\conf\opencv14\trans\opencv_bin\info.txt -bg F:\conf\opencv14\trans\opencv_bin\bg.txt -num 838 -w 20 -h 30
（num是pos的图片数量，不可超过实际数）
opencv_traincascade.exe -data F:\conf\opencv14\trans\opencv_bin\xml_file -vec F:\conf\opencv14\trans\opencv_bin\info.vec -bg F:\conf\opencv14\trans\opencv_bin\bg.txt -numPos 730 -numNeg 1500 -numStages 18 -featureType LBP  -w 20 -h 30 -maxfalsealarm 0.2 -minhitrate 0.995
（numPos是pos的图片数量，同样不可以超过实际数，numNeg是负样本数，可以超过实际数，numStages是分级数）

2. 使用Image对象绘制在画布上有回退帧的情况，用src直写没有，反馈延迟在前端降低图片分辨率和质量达到流畅。
》》继续优化的思路：前端图片显示可以等比放大（放后端会让需求带宽变大），后台编解码的方式找更快的方式。
》》等比放大，直接定义style中的we和hi就行。

>>前端打开摄像头可以用兼容更强的tracking框架。

# 使用自己采集的数据集训练模型

#放弃java做后台,用django
命令流程：
pip install -i https://pypi.douban.com/simple/ django --user  
C:\Users\Bar.Z\AppData\Roaming\Python\Python38\Scripts  加环境变量  
django-admin startproject mydemo
python manage.py runserver
python manage.py createsuperuser : userid :0 password :123456
python manage.py startapp polls

<html> add  {% csrf_token %}

# 什么垃圾django，去他妈的吧。