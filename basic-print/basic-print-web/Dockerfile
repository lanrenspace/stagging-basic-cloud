FROM java:8
MAINTAINER Stagging Basic Cloud "https://github.com/lanrenspace/stagging-basic-cloud"
#修改镜像为东八区时间 \
ENV TZ Asia/Shanghai
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ADD ${CONF_DIR} /conf
EXPOSE 8089
# build param
# image tag: stagging-basic-cloud:print_v1.0.0
COPY fonts/simsun.ttf /usr/share/fonts/ttf-dejavu/simsun.ttf
EXPOSE 7777
ENTRYPOINT ["java","-server", "-Xms256m", "-Xmx512m","-jar","/app.jar","--spring.config.location=/conf/bootstrap.yml"]