# spring boot + Vue 实现汽车销售管理系统

### [在线预览](http://139.196.74.191:8080)
账号 | 用户名 | 密码
:---:|:---:|:---:
123456	| 马经理 | 123456
1703001	| 邓布利多 | 123456
1810001	| 何熙洋 | 123456
1810002 | 赫敏·格兰杰 | 123456
1812001	| 罗冠杰 | 123456
1901001 | 张晓兰 | 123456
使用账号及密码登录，所以用户密码均为`123456`，其中账号`123456`(马经理)为经理角色

### 项目简介
本项目基于spring boot以及Vue开发，前端实现基于[PanJiaChen](https://github.com/PanJiaChen)所提供的开源后台项目[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)改造。

针对汽车销售提供客户信息、车辆信息、订单信息、销售人员管理、财务报表等功能，提供经理和销售两种角色进行管理。

### 食用指南
1. 导入carsale.sql到数据库中
2. 修改`src/resources/application.yml`的数据库连接信息
3. 启动CarsaleApplication，访问[localhost:8080](localhost:8080)

**食用指南PLUS（Vue项目启动）**

1. 安装好node环境
2. 在front目录下运行`npm install`安装所需要的包
3. 在front目录下运行`npm run dev`，访问[localhost:9527](localhost:9527)
