##Docker部署
####一.环境准备
1. 在玩家目录（例如`/home/jonas`）创建项目目录`gameweb`，该目录称为工作目录。
2. 将`src/main/docker/Dockerfile`文件和打包得到的`gameweb.jar`上传到工作目录。

####二.启动服务
1. 在工作目录执行如下指令，创建镜像。
```
docker build -t gameweb .
```
- 通过`spring.config.location`配置使得可以在外部修改配置文件，但必须重启才能生效。

2. 执行如下命令启动服务。
```
docker run -d -p 18080:8080 -e TZ=Asia/Shanghai --name GameWeb -v /home/jonas/gameweb/logs:/logs 
-v /home/jonas/gameweb/conf/application.yml:/conf/application.yml gameweb
```
- 通过挂载日志目录，使得可以在工作目录下的`logs`目录查看到服务日志。

3. 服务器上测试端口
```
curl -X POST -d 'playerId=3e5b942b-17c3-3c58-a611-2c99578dabb9&gameType=1' 127.0.0.1:18080/game/getPeak
```


