# 数据库文档

<a name="返回顶部"></a>

## 数据表列表

* [me_user(用户表)](#me_user_pointer)



## 数据表说明

<a name="me_user_pointer"></a>

* me_user表(用户表)[↑](#返回顶部)

|字段名称|字段类型|字段含义|
|:---:|:---:|:---:|
|user_id|varchar(36)|PK|
|nick_name|varchar(50)|昵称|
|real_name|varchar(50)|真实姓名|
|wx_openid|varchar(50)||
|mobile|varchar(20)|手机号|
|enabled|tinyint(1)|0-未激活，1-激活|
|user_name|varchar(80)||
|password|varchar(80)||

