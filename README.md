<<<<<<< HEAD
#信息自动化采集后入库项目
The project of warehousing after information automatic collection

采集模块 --> 信息传递模块 --> 入库模块 gather --> sendMessage --> InDatabase
=======
# 信息自动化采集后入库项目
The project of warehousing after information automatic collection

采集模块 --> 信息传递模块  --> 入库模块
gather  --> sendMessage  --> InDatabase

本程序是模拟将树莓派中的二氧化碳，光照，温度湿度等数据采集过程，并将其将于服务器端处理入数据库。
eng:This program is to simulate the data collection process of carbon dioxide, light, temperature and humidity in the raspberry pie, and process it into the database on the server side.

day2018-09-19 更新：添加 Configuration 接口的实现类的编写，使类的实例化通过该类实现，当需要对象的时候只需要调用该方法即可。添加 ConfigutationAware接口的编写，使在类中可以引入另一个实例化的对象的引用。改写对 Configuration实现类的编写，使其能一步解析XML文件并拼接成properties文件。 eng:update:Add the implementation class of the Configuration interface to be written so that class instantiation is implemented through that class, and only call that method when an object is needed.Add the writing of the ConfigutationAware interface so that a reference to another instantiated object can be introduced in the class. Rewrite the Configuration implementation class so it can parse the XML file in one step and splice it into a properties file.

请替换 config.properties 中的相关参数
eng:please replace the parameter in config.properties!

driverClassName=#yourdriver
url=#yoururl
username=#yourusername
password=#yousrpassword
>>>>>>> b96f52f8515cffd38ba12ab61a8c1628affd810d
