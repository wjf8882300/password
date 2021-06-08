#### 家庭管理系统

##### 功能介绍
###### 密码管理
> 痛点：注册的网站太多，日常要记忆的密码太多，如果都用一个密码容易泄漏，不同密码又不容易记忆。

> 这个功能就是只要记一个密码，然后登录之后，就可以查看所有密码。

> 通过密码和google auth双重认证，保证系统绝对安全。

###### 收支管理
> 记录家庭收支情况，方便控制预算。

###### 积分管理

> 针对家里的小孩，制定各种奖惩措施，通过奖励积分和扣减积分来达到激励和惩罚的目的。



##### 部署说明
1.安装mysql数据库

2.到mysql数据库中执行password/src/main/resources/database/cp.sql和cp_data.sql

3.修改password/src/main/resources/bootstrap.yml中的数据库连接，把数据库地址、用户名和密码替换为您自己的。
```
      url: jdbc:mysql://192.168.1.3:53306/cp?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true
      username: xxx
      password: xxx
```
4.切换到password目录下执行以下命令，结束后到password/target/目录下，获取password-1.0.0.jar文件
> mvn clean package

5.执行执行以下命令运行项目
> java -jar password-1.0.0.jar &

##### 使用介绍
1、首次登录，需要设置下google auth，访问以下地址，然后用手机的google auth扫描二维码即可
> google auth二维码地址: http://localhost:8082/qr.html

2、系统通过以下地址访问项目
> 密码管理: http://localhost:8082/password.html

> 收支管理: http://localhost:8082/income.html

> 积分管理: http://localhost:8082/score.html

##### 截图
![avatar](https://github.com/wjf8882300/password/blob/main/src/main/resources/static/img/1.png)
![avatar](https://github.com/wjf8882300/password/blob/main/src/main/resources/static/img/2.png)
![avatar](https://github.com/wjf8882300/password/blob/main/src/main/resources/static/img/3.png)
![avatar](https://github.com/wjf8882300/password/blob/main/src/main/resources/static/img/4.png)
![avatar](https://github.com/wjf8882300/password/blob/main/src/main/resources/static/img/5.png)