#基础镜像使用jdk17
FROM openjdk:17-oracle
#作者
MAINTAINER duo
# VOLUME 指定临时文件目录为/tmp，在主机/var/lib/docker目录下创建了一个临时文件并链接到容器的/tmp
VOLUME /tmp
# 将jar包添加到容器中并更名
ADD duo-admin-1.0-SNAPSHOT.jar duo-admin.jar
# 运行jar包
RUN bash -c 'touch /duo-admin.jar'
ENTRYPOINT ["java","-jar","/duo-admin.jar"]
#暴露8989端口作为微服务
EXPOSE 8989
