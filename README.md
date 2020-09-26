# 1. 物料清单

## 1.1 硬件环境

Linux服务器5台:

- **deploy-main**
  - 操作系统： CentOS-7-x86_64-1804
  - 用途: 用于给其他三台服务器部署docker及k8s环境
- **data-center** （CentOS-7-x86_64-1804）
  - 操作系统： CentOS-7-x86_64-1804
  - 用途: 网关，代码仓库，数据库
- **harbor**
  - 操作系统： CentOS-7-x86_64-1804
  - 用途: docker k8s helm仓库
- **k8s-master**
  - 操作系统： CentOS-7-x86_64-1804
  - 用途: k8s集群主节点
- **k8s-worker**
  - 操作系统： CentOS-7-x86_64-1804
  - 用途: k8s集群从节点

## 1.2 软件环境及服务

### 1.2.1 环境

- docker - 1.13.1
- kubernetes - 1.19.0
- jdk - 8u191

### 1.2.2 服务

- breeze - 1.19.0
- docker-compose - 1.12.0
- gitlab - （docker）
- nexus repository manager - 3.27.0 （docker）
- redis - （docker）
- mysql - 5.7 （docker）
- apollo - （docker）



## 1.3  服务器IP规划

- **deploy-main 192.168.1.19**

- **data-center 192.168.146.150**

- **harbor 192.168.146.151**

- **k8s-vip 192.168.146.152**

- **k8s-master 192.168.146.153**

- **k8s-worker 192.168.146.154**




---

# 2. 部署步骤浅析

## 2.1 部署breeze![部署breeze](https://github.com/0nebean/chemical-el/blob/master/src/main/resources/md/magnesium/3.0.0/img/部署breeze-1601104387629.png)

## 2.2 使用breeze部署docker环境

![部署docker 及k8s环境](https://github.com/0nebean/chemical-el/blob/master/src/main/resources/md/magnesium/3.0.0/img/部署docker 及k8s环境-1601104387630.png)

## 2.3 部署依赖服务到data-center

![部署依赖服务](https://github.com/0nebean/chemical-el/blob/master/src/main/resources/md/magnesium/3.0.0/img/部署依赖服务-1601104387630.png)

---



# 3. 部署breeze

## 3.1 官方部署资料

详细部署流程请参考 [breeze-1.19.0 官方部署手册](https://github.com/wise2c-devops/breeze/blob/v1.19.0/BreezeManual-CN.md)



## 3.2 部署详情



### 3.2.1 放开防火墙

```shell
setenforce 0 &&\
sed --follow-symlinks -i "s/SELINUX=enforcing/SELINUX=disabled/g" /etc/selinux/config &&\
firewall-cmd --set-default-zone=trusted &&\
firewall-cmd --complete-reload
```


### 3.2.2 安装docker-compose

详细部署流程请参考 [linux 安装docker-compose](https://www.jianshu.com/p/8adb20fe5860)


### 3.2.3 安装docker-ce

详细部署流程请参考 [linux centos 7 安装docker-ce](https://www.jianshu.com/p/b0d1fb39a2a5)

### 3.2.4  下载并启动 breeze

下载用于部署某个Kubernetes版本的docker-compose文件并使部署程序运行起来，例如：

```shell
curl -L https://raw.githubusercontent.com/wise2c-devops/breeze/v1.19.0/docker-compose.yml -o docker-compose.yml
curl -L https://raw.githubusercontent.com/wise2c-devops/breeze/v1.19.0/docker-compose-centos.yml -o docker-compose.yml
curl -L https://raw.githubusercontent.com/wise2c-devops/breeze/v1.19.0/docker-compose-ubuntu.yml -o docker-compose.yml
```

国内用户可以使用阿里云镜像站点文件，部署所用的image将从阿里云拉取：

```shell
curl -L https://raw.githubusercontent.com/wise2c-devops/breeze/v1.19.0/docker-compose-aliyun.yml -o docker-compose.yml
curl -L https://raw.githubusercontent.com/wise2c-devops/breeze/v1.19.0/docker-compose-centos-aliyun.yml -o docker-compose.yml
curl -L https://raw.githubusercontent.com/wise2c-devops/breeze/v1.19.0/docker-compose-ubuntu-aliyun.yml -o docker-compose.yml
```

然后：

```shell
docker-compose up -d
```

如果机器磁盘性能较差，需要调整超时，请用以下命令启动：

```shell
COMPOSE_HTTP_TIMEOUT=300 docker-compose up -d
```



### 3.2.5 对目标服务器ssh免密登录

deploy-main上生成rsa公私钥，用于ssh到其他部署机器上

```shell
ssh-keygen -t rsa
```

在deploy-main上对所有需要部署的机器做，免密操作

```shell
 ssh-copy-id 192.168.146.151
 ssh-copy-id 192.168.146.153
 ssh-copy-id 192.168.146.154 
```

### 3.2.6 访问deploy-mian上的breeze

deploy-mian IP + 端口号88

![BreezeScreenShots001](README.assets/BreezeScreenShots001-1601104387630.png)

---

# 4. breeze部署k8s环境

- step-1
  ![image-20200918112128256](README.assets/image-20200918112128256-1601104387630.png)

- step-2

  ![image-20200920224519813](README.assets/image-20200920224519813-1601104387631.png)


- step-3

![image-20200920224535735](README.assets/image-20200920224535735-1601104387631.png)


- step-4

![image-20200920224544407](README.assets/image-20200920224544407-1601104387631.png)


- step-5

![image-20200920224604090](README.assets/image-20200920224604090-1601104387631.png)


- step-6

![image-20200920224553400](README.assets/image-20200920224553400-1601104387631.png)


- step-7

![image-20200920224846922](README.assets/image-20200920224846922-1601104387631.png)


- step-8

![image-20200920224819026](README.assets/image-20200920224819026-1601104387631.png)


- step-9

你可以删除`harbor`，使用 `nexus` 作为docker仓库，也可以保留它。这里我选择删除它，因为它占用的端口号太多。

如果你选择 `nexus` 作为docker仓库，需要在docker配置文件中将，nexus仓库的http地址，设置为白名单，如果你使用ssl证书，则不需要：

```shell
#编辑docker配置文件
vim /etc/docker/daemon.json

#添加配置
"insecure-registries":["nexus.tkfc.com:5000"],

#重启docker
systemctl daemon-reload && systemctl restart docker
```


---

# 5. data-center服务部署

## 5.1 端口号域名规划

### 5.1.1 端口号规划

- **80 uag**
- **8081 nexus**
- **8082 gitlab**
- **8083 apollo-portal**
- **8084 eureka**
- **15672 rabbit-mq-admin**
- **5672 rabbit-mq**
- **6380 redis**
- **13306 mysql**

### 5.1.2 域名规划



## 5.2 部署详情

### 5.2.1 安装docker-ce

详细部署流程请参考 [linux centos 7 安装docker-ce](https://www.jianshu.com/p/b0d1fb39a2a5)

### 5.2.2 安装docker-compose

详细部署流程请参考 [linux 安装docker-compose](https://www.jianshu.com/p/8adb20fe5860)

### 5.2.3 docker安装apollo

详细部署流程请参考 [Apollo Quick Start Docker部署](https://github.com/ctripcorp/apollo/wiki/Apollo-Quick-Start-Docker%E9%83%A8%E7%BD%B2)

使用 [DownGit](https://minhaskamal.github.io/DownGit) 下载apollo的 [docker-compose文件](https://github.com/ctripcorp/apollo/tree/master/scripts/docker-quick-start) 上传到data-center服务器上，按照规划端口号  修改compose文件 ：

```shell
vim docker-compose.yml
```



```yaml
- "8084:8080"
- "8083:8070"
```

然后启动apollo：

```shell
docker-compose up -d
```

#### 5.2.3.1 apollo初始化apollo

连接上apollo 的数据库 data-center IP + 13306 root / 无密码 ， 对数据库 `ApolloPortalDB` 执行一下操作：

- 创建用户账户 admin/43118vu0r647VlfZaw3087d31U6ea1
- 创建 公共配置public-conf


```sql
INSERT  INTO `Authorities`(`Id`,`Username`,`Authority`) VALUES (2,'admin','ROLE_user');
INSERT  INTO `Users`(`Id`,`Username`,`Password`,`Email`,`Enabled`) VALUES (2,'admin','$2a$10$qijRYq/tvuRUlDeyaSw6teO67z6LTQtMg5wBU6Y/ylZpe12cvXWwG','admin@apollo.com',1);
UPDATE `ServerConfig` s SET s.`Value` = 'admin' WHERE s.`Key` = 'superAdmin';
UPDATE `ServerConfig` s SET s.`Value` = '[{"orgId":"public-conf","orgName":"公共配置"}]' WHERE s.`Key` = 'organizations';

```

#### 5.2.3.2 创建公用配置

请参考 [公用配置文件](https://github.com/0nebean/public.conf/tree/master/conf)

![image-20200918151449448](README.assets/image-20200918151449448-1601104387631.png)

![image-20200918152240010](README.assets/image-20200918152240010-1601104387631.png)

#### 5.2.3.3 修改apollo-mysql配置

mysql-5.7默认不支持 多列group by ，需要如下设置：

详细部署流程请参考 [docker 运行 mysql5.7 中文乱码 group by 不支持单字段](https://www.jianshu.com/p/3c35a35cf2fe)



### 5.2.4 docker安装redis

详细部署流程请参考 [docker 安装 redis](https://www.jianshu.com/p/9d52450b7013)



```shell
docker pull redis
docker images
docker run --name redis-server  -p 6379:6379 -v $PWD/data:/data  -d  redis:latest redis-server --appendonly yes --requirepass "123456"
```



### 5.2.5 docker安装nexus

#### 5.2.5.1 运行nexus

详细部署流程请参考 [docker 安装nexus3](https://www.jianshu.com/p/9f6ad50f2d4d)



```shell
docker search nexus
docker pull sonatype/nexus3
docker images
#创建本地目录当做仓库
mkdir -p /usr/program/nexus 
docker run -id --privileged=true --name=nexus3 --restart=always -p 8081:8081 -p 5000:5000 -u root -v /usr/program/nexus:/nexus-data sonatype/nexus3
```

#### 5.2.5.2 创建docker仓库

详细部署流程请参考 [nexues 创建 docker register](https://www.jianshu.com/p/1e54e832c285)



![image-20200920233641383](README.assets/image-20200920233641383-1601104387631.png)

![image-20200920233655882](README.assets/image-20200920233655882-1601104387631.png)


将docker仓库的密码，保存到文件，制作镜像推送仓库时，需要用到。

```shell
 echo '43118vu0r647VlfZaw3087d31U6ea1' >  /usr/program/docker-profile	
```





### 5.2.6 docker安装gitlab

#### 5.2.6.1 安装gitlab服务

详细部署流程请参考 [docker-compose安装gitlab](https://www.jianshu.com/p/80173f8d91c8)

`vim docker-compose.yml`

```yaml
web:
  image: 'gitlab/gitlab-ce:latest'
  restart: always
  hostname: 'gitlab.tkfc.com'
  environment:
    GITLAB_OMNIBUS_CONFIG: |
      external_url 'http://gitlab.tkfc.com:8082'
      gitlab_rails['gitlab_shell_ssh_port'] = 2224
  ports:
    - '8082:8082'
    - '2224:22'
  volumes:
    - '$GITLAB_HOME/config:/etc/gitlab'
    - '$GITLAB_HOME/logs:/var/log/gitlab'
    - '$GITLAB_HOME/data:/var/opt/gitlab'
```

```shell
#创建临时目录并导出目录变量
mkdir -p /usr/program/gitlab && export GITLAB_HOME=/usr/program/gitlab
docker-compose up -d
```



#### 5.2.6.2 安装gitlab-runner

gitlab-runner是gitlab的脚本解释器，用来执行CI脚本，gitlab-runner可以通过k8s安装或者物理安装。

- 详细部署流程请参考 [kubernetes 安装配置 GitLab Runner](https://www.jianshu.com/p/e4102ff3c44d)

- 详细部署流程请参考 [物理安装配置 GitLab Runner](https://www.jianshu.com/p/cfa310896705)

这里建议物理安装，k8s安装的runner 如果需要执行 mvn 或 docker命令 还涉及到 将maven 放到docker中或在docker中运行docker，我觉得你应该不想这么做。

#### 5.2.6.5  安装kubectl

详细部署流程请参考 [linux centos 以Minikube单机模式运行 kubernetes](https://www.jianshu.com/p/65e6ce59ef5f)

这里只需要安装 `kubectl`。



### 5.2.7 物理安装maven



详细部署流程请参考 [centos 安装并配置maven环境变量](https://www.jianshu.com/p/b47ad48ee9c2)

确保`maven`安装路径正确

```
#maven 路径
/usr/program/apache-maven-3.5.4
#maven 本地仓库路径
/usr/program/m2
```



###   5.2.8  物理安装JDK8

详细部署流程请参考 [CentOS 6 7 JDK下载 安装 配置](https://www.jianshu.com/p/466cfdca1eef)



### 5.2.9  eureka 部署

[net.onebean.middleware.eureka](https://github.com/0nebean/net.onebean.middleware.eureka) 安装

- 修改配置文件
  https://github.com/0nebean/net.onebean.middleware.eureka/blob/master/src/main/resources/package/bin/startup.sh

  ```shell
  # 启动日志追加到启动日志文件中
  #echo -e ${STARTUP_LOG} >> ${LOG_STARTUP_PATH}
  # 打印启动日志
  #echo -e ${STARTUP_LOG}
  
  # 打印项目日志
  #tail -f ${LOG_PATH}
  echo 'started eureka!'
  ```

  末尾修改如上内容，并在`/etc/rc.local` 文件中，设置该脚本的自启动:

  ```shell
  JAVA_HOME=/usr/program/jdk1.8.0_191
  JRE_HOME=$JAVA_HOME/jre
  PATH=$PATH:$JAVA_HOME/bin
  CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  export JAVA_HOME
  export JRE_HOME
  export PATH
  export CLASSPATH
  
  /bin/bash -c /usr/program/eureka/server/bin/restart.sh;
  echo 'eureka started';
  ```

  

- mvn install

- tar-zxvf ./server-dev-1.0.0-SNAPSHOT.tar.gz

- ./server/bin/startup.sh

- enjoy



---



# 6.  设置k8s及控制台

## 6.1 访问 Dashboard 

访问地址 ：https://k8s-master IP:30300 ，可以访问到 Dashboard 控制台。

新版本Dashboard引入了验证模式，可以通过以下命令获取admin-user的访问令牌：

```
kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | grep admin-user | awk '{print $1}')
```

将返回的token字串粘贴至登录窗口即可实现登录。

## 6.2 设置ssl证书

chrome 会提示 Dashboard 的https 证书不是有效的证书，如下：

![image-20200919201523111](README.assets/image-20200919201523111-1601104387631.png)

你可以选择继续访问不安全网站，或者设置ssl证书。

详细部署流程请参考 [kubernetes dashboard 2.x 配置https证书](https://www.jianshu.com/p/717a3e292c38)

- 在你的 k8s-master 创建并执行 secret `kubernetes-dashboard-certs` 指定你的ssl证书路径

```shell
kubectl delete secret kubernetes-dashboard-certs -n kubernetes-dashboard && \
kubectl create secret generic kubernetes-dashboard-certs --from-file="/usr/program/crts/dashboard/dashboard.crt,/usr/program/crts/dashboard/dashboard.key" -n kubernetes-dashboard
```

这里crt和key文件的文件名一定要叫`dashboard`,不然证书会无效。

- 查看secret内容

```yaml
kubectl get secret kubernetes-dashboard-certs -n kube-system -o yaml

# 内容如下：
apiVersion: v1
data:
  k8s-dashboard.crt: ........
  k8s-dashboard.key: ........
kind: Secret
metadata:
  creationTimestamp: "2020-06-30T15:34:40Z"
  name: kubernetes-dashboard-certs
  namespace: kube-system
  resourceVersion: "1728623"
  selfLink: /api/v1/namespaces/kube-system/secrets/kubernetes-dashboard-certs
  uid: a915cd0d-195f-47d3-8975-e7350f8800e1
type: Opaque
```

然后就可以了，如果不行的话，重新部署一下dashboard：

```shell
kubectl get deployment kubernetes-dashboard -n kubernetes-dashboard -o yaml >> kubernetes-dashboard.yaml && \
kubectl delete -f kubernetes-dashboard.yaml && \
kubectl apply -f kubernetes-dashboard.yaml && \
rm -rf ./kubernetes-dashboard.yaml
```



## 6.3 设置k8s容器硬件限制

详细部署流程请参考 [kubernetes cpu内存资源指定详解，及用量计算方式](https://www.jianshu.com/p/cf3dd3cff7dc)

## 6.4 设置k8s的docker仓库访问权限

详细部署流程请参考 [Kubernetes 访问 docker 仓库失败 no basic auth credentials](https://www.jianshu.com/p/7a0af5f26e3d)



# 7. 部署网关控制台



## 1.1 资源准备

- 两个二级域名(一级也可)分别用于网关控制台和网关,以及其对应的ssl证书
  - **uag.tkfc.com**
  - **gateway.tkfc.com**
- 一台centos系统的服务器(推荐centos7)



## 1.2 环境安装

- 网关环境依赖-OpenResty



### 1.2.1 安装OpenResty

#### 1.2.1.1 添加官方源

```shell
sudo yum install yum-utils
sudo yum-config-manager --add-repo https://openresty.org/package/centos/openresty.repo
```

#### 1.2.1.2 安装依赖

```shell
yum install pcre-devel openssl-devel gcc curl
```

#### 1.2.1.3 官网下载解压安装包

[官网下载地址](http://openresty.org/en/download.html)

```shell
tar -zxvf openresty-1.15.8.1.tar.gz
```

然后在进入 openresty-${VERSION}/ 目录, 然后输入以下命令配置:

```shell
./configure
```

![](https://upload-images.jianshu.io/upload_images/2862577-3541487350beb4b0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

配置成功后成了了 make file 就可以make && make install 
电脑多核的话 可以加上参数-j 多线程编译

```shell
make -j2 && \
make install
```



## 1.3 项目配置初始化

### 1.3.1 gateway-java配置

- 若使用Apollo 配置中心，请在类 `net.onebean.spring.Main` 加上注解：

```java
@EnableApolloConfig
```

并修改启动类，`net.onebean.spring.Mian`:

```java
//从Apollo加载配置
ConfigInitializer.initApollo();
```



apollo的注册地址在 `core核心包中的配置文件里，你需要修改后自己编译`：  

`https://github.com/0nebean/net.onebean.core/blob/master/src/main/resources/system.properties`

```properties
#apollo
apollo.bootstrap.enabled = true
apollo.bootstrap.namespaces = application,public-conf.jdbc,public-conf.redis,public-conf.mongodb,public-conf.aliyun,public-conf.rabbitmq,public-conf.sso,public-conf.spring-cloud
##################这里是apollo各个环境注册地址####################
dev.meta=http://${开发环境}:8084
fat.meta=http://${测试环境}:8084
uat.meta=http://${灰度环境}:8084
pro.meta=http://${生产环境}:8084
##################这里是apollo各个环境注册地址####################
#loggings
logging.config = classpath:META-INF/logback/logback-config.xml
logging.path=./logs
#RestTemplate
simple.client.http.request.factory.read.timeout = 5000
simple.client.http.request.factory.connect.timeout = 15000
```

- 如不使用Apollo 配置中心， 可以直接修改项目中的配置文件

```shell
# mysql
https://github.com/0nebean/Magnesium/tree/master/gateway-java/net.onebean.gateway.web/src/main/resources/public-conf.jdbc.properties
# redis
https://github.com/0nebean/Magnesium/tree/master/gateway-java/net.onebean.gateway.web/src/main/resources/public-conf.redis.properties
# spring-cloud
https://github.com/0nebean/Magnesium/tree/master/gateway-java/net.onebean.gateway.web/src/main/resources/public-conf.spring-cloud.properties
# 单点登录
https://github.com/0nebean/Magnesium/tree/master/gateway-java/net.onebean.gateway.web/src/main/resources/public-conf.sso.properties
```



### 1.3.2 uag-nginx配置

```shell
#网关API 域名 证书 日志 配置文件
https://github.com/0nebean/Magnesium/blob/master/uag-nginx/conf/basic/platform.conf
#网关控制台 域名 证书 日志 配置文件
https://github.com/0nebean/Magnesium/blob/master/uag-nginx/conf/front/gateway.conf
#网关配置文件 redis配置修改 
https://github.com/0nebean/Magnesium/blob/master/uag-nginx/conf/config.json
```



## 1.4 数据库脚本初始化

执行 `https://github.com/0nebean/Magnesium/blob/master/sql/gateway.sql`脚本创建数据库



## 1.5 启动OpenResty 

将`uag-nginx`模块上传到 `data-center` 服务器路径:`/usr/local/openresty/nginx`上

执行命令创建工作目录:

```
mkdir -p /tmp/uag/backup && mkdir -p /tmp/uag/delete && mkdir -p /opt/tar
```



随后执行命令 启动nginx:

```shell
/usr/local/openresty/nginx/sbin/nginx -c /usr/local/openresty/nginx/uag/conf/common.conf
```



并在`/etc/rc.local` 文件中，设置openresty的自启动：

```shell
echo 'eureka started'
{ # try
 /usr/local/openresty/nginx/sbin/nginx -s stop
} || { # catch
   echo 'nginx not started,will start nginx' 
}
/usr/local/openresty/nginx/sbin/nginx -c /usr/local/openresty/nginx/uag/conf/common.conf
echo 'nginx started'
```



## 1.6 部署gateway-java到GitLab

### 1.6.1 设置GitLab设置CI参数

#### 1.6.1.1 创建group 

![image-20200924160825372](README.assets/image-20200924160825372-1601104387631.png)

#### 1.6.1.2 设置CI参数

![image-20200924160905733](README.assets/image-20200924160905733-1601104387631.png)



- UAG_HOST (开始准备的uag域名)
- UAG_PORT (uag的端口号)
- DEFAULT_VM_ARGS （jvm参数  需要base64 [ echo 'jvm参数' | base64 ]）
- KUBE_CONFIG (k8s的 config文件 需要base64 去k8s-master上执行 [ cat /root/.kube/config | base64 ])

![image-20200924173407628](README.assets/image-20200924173407628-1601104387632.png)

 这里的CI参数，是和group关联的，也可以给项目单独设置。

### 1.6.2 创建并上传gitlab项目

在上一步创建并设置了CI参数的group下，创建项目，并将 `gateway-java` 提交到GitLab。

![image-20200924174100515](README.assets/image-20200924174100515-1601104387632.png)

并将CI的脚本文件放在项目根路径：

- `.gitlab-ci.yml`

```yaml
stages:
  - maven-build
  - docker-build
  - k8s-deploy


before_script:
  - pwd
  - CI_COMMIT_TAG=${CI_COMMIT_TAG}
  - echo $CI_COMMIT_TAG


maven-build:
  stage: maven-build
  only:
    - /^*_v.*$/
  tags:
    - java
  script:
    - mvn test


docker-build:
  stage: docker-build
  only:
    - /^*_v.*$/
  script:
    - env=${CI_COMMIT_TAG%%_*}
    - echo $env
    - rm -rf ./app && mkdir ./app
    - mvn clean package -f ./pom.xml -P $env
    - tar -zxvf ./net.onebean.gateway.web/target/server-$env.tar.gz -C ./app
    - cp $JAVA_HOME/jre/lib/security/cacerts ./app    
    - docker login harbor.tkfc.com -u=admin --password-stdin < /usr/program/nexus-profile/passwd
    - docker build -t harbor.tkfc.com/onebean-func/gateway:${CI_COMMIT_TAG} .
    - docker push harbor.tkfc.com/onebean-func/gateway:${CI_COMMIT_TAG}
    - docker rmi harbor.tkfc.com/onebean-func/gateway:${CI_COMMIT_TAG}
    - rm -rf ./app


k8s-deploy_dev:
  stage: k8s-deploy
  only:
    - /^dev_v.*$/
  script:
    - mkdir -p ~/.gitlab-runner
    - echo ${KUBE_CONFIG} | base64 -d > ~/.gitlab-runner/config
    - export KUBECONFIG=~/.gitlab-runner/config
    - kubectl version
    - KUBERNETES_VERSION=$CI_COMMIT_TAG
    - KUBERNETES_VERSION=${KUBERNETES_VERSION//'.'/'-'}
    - KUBERNETES_VERSION=${KUBERNETES_VERSION//'_'/'-'}
    - test='s/kubernetes_version/temp-str/g'
    - KUBERNETES_VERSION_REGULAR=${test//'temp-str'/$KUBERNETES_VERSION}
    - sed -i $KUBERNETES_VERSION_REGULAR ./deployment.yml
    - UAG_HOST=${UAG_HOST}
    - test='s/uag-host/temp-str/g'
    - UAG_HOST=${test//'temp-str'/$UAG_HOST}
    - sed -i $UAG_HOST ./deployment.yml
    - UAG_PORT=${UAG_PORT}
    - test='s/uag-port/temp-str/g'
    - UAG_PORT=${test//'temp-str'/$UAG_PORT}
    - sed -i $UAG_PORT ./deployment.yml
    - DEFAULT_VM_ARGS=${DEFAULT_VM_ARGS}
    - test='s/default_vm_args/temp-str/g'
    - DEFAULT_VM_ARGS=${test//'temp-str'/$DEFAULT_VM_ARGS}
    - sed -i $DEFAULT_VM_ARGS ./deployment.yml
    - test='s/latest/temp-str/g'
    - CI_COMMIT_TAG=${test//'temp-str'/$CI_COMMIT_TAG}
    - sed -i $CI_COMMIT_TAG ./deployment.yml
    - sed -i '/hostAliases:/ r /usr/program/host/hosts.yml' ./deployment.yml
    - kubectl apply -f deployment.yml
    - kubectl expose deployment gateway-deployment-${KUBERNETES_VERSION} --type=NodePort -n onebean-func
    - echo "成功提交部署请求到k8s，请到k8s控制台上确认镜像是否成功部署！"
```

- `deployment.yml`

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: gateway-deployment-kubernetes_version
  namespace: onebean-func
  labels:
    app: gateway-kubernetes_version
spec:
  selector:
    matchLabels:
      app: gateway-kubernetes_version
  replicas: 1
  minReadySeconds: 5
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 1
  template:
    metadata:
      labels:
        app: gateway-kubernetes_version
    spec:
      hostAliases:
      containers:
        - name: gateway-pod-kubernetes_version
          image: harbor.tkfc.com/onebean-func/gateway:latest
          imagePullPolicy: Always
          env:
            - name: JAVA_OPT
              value: "default_vm_args"
          ports:
            - containerPort: 8080
          lifecycle:
            postStart:
              httpGet:
                host: uag-host
                path: devops/addNode?nodeName=gateway&currentVersion=latest&nodeNamespace=onebean-func
                port: uag-port
                scheme: HTTP
      imagePullSecrets:
        - name: secret-register-auth

```

- `Dockerfile`

```dockerfile
FROM openjdk:8u191-jre-alpine

MAINTAINER 0neBean

ADD ./app /app

RUN mv ./app/cacerts /usr/lib/jvm/java-1.8-openjdk/jre/lib/security

ENTRYPOINT /app/server/bin/startup.sh

```



## 1.7 初始化gateway配置

### 1.7.1 访问控制台

去除sso单点登录配置，通过 k8s，nodePort 访问 `gateway-java`。

### 1.7.2 设置服务节点访问地址

修改gateway 服务节点访问地址为当前浏览器访问地址。

![image-20200926134449494](README.assets/image-20200926134449494-1601104387632.png)

### 1.7.3 gateway设置服务访问域名

![image-20200926134924693](README.assets/image-20200926134924693-1601104387632.png)

### 1.7.4 设置gateway登录回调地址

![image-20200926142658891](README.assets/image-20200926142658891-1601104387632.png)

### 1.7.5  设置服务器节点



![image-20200926142820548](README.assets/image-20200926142820548-1601104387632.png)



### 1.7.6 同步数据到缓存

![image-20200926142750260](README.assets/image-20200926142750260-1601104387632.png)

![image-20200926142808621](README.assets/image-20200926142808621-1601104387632.png)

### 1.7.7 还原sso单点登录配置

重启k8s上`gateway-java`服务，通过刚设置域名访问即可。

用户名/密码：`15800000001 / 123456`