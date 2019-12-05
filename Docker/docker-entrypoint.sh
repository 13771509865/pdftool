#!/bin/bash
set -eo pipefail
shopt -s nullglob

flag=1

init_redis_config() {
    cat <<- EOCONF > $CONF_DIR/application.yml
server:
  port: 8081
  servlet:
    context-path: /

spring:
  devtools:
    restart:
      enabled: false

  thymeleaf:
    cache: false
    encoding: UTF-8
    mode: HTML
    prefix: classpath:/templates/
    suffix: .html
    servlet:
      content-type: text/html

  servlet:
    multipart:
      enabled: true
      max-file-size: 1024MB
      max-request-size: 1024MB

  datasource:
    druid:
      url: jdbc:mysql://rm-bp1g9bo2z8wps6s7w.mysql.rds.aliyuncs.com:3306/pts?characterEncoding=utf-8&serverTimezone=Asia/Shanghai
      username: pdf2word
      password: pdf2word@2019
      driver-class-name: com.mysql.cj.jdbc.Driver
      filters: mergeStat,config,wall,stat
      max-active: 20
      initial-size: 1
      max-wait: 3000
      min-idle: 1
      time-between-eviction-runs-millis: 60000
      min-evictable-idle-time-millis: 300000
      validation-query: select 1
      test-while-idle: true
      test-on-borrow: false
      test-on-return: false

  #缓存方式配置
  #redis缓存
  #######################################################################
  redis:
    database: 0
    timeout: 10000
    password: ${REDIS_PASS} 
    host: ${REDIS_HOST} 
    port: ${REDIS_PORT}
    lettuce:
      pool:
        max-active: 3000
        max-wait: -1 
        max-idle: 500
        min-idle: 0
      shutdown-timeout: 1000

mybatis:
  mapper-locations: classpath:mapping/*.xml
  type-aliases-package: com.neo.model

logging:
  config: classpath:log4j2.xml

#actuator,spring监控服务
management:
  endpoint:
    health:
      show-details: always
  endpoints:
    web:
      base-path: /actuator
      exposure:
        include: "*"

#httpclient相关优化配置
http:
  maxTotal: 3000
  defaultMaxPerRoute: 500
  connectTimeout: 2000
  connectionRequestTimeout: 2000
  socketTimeout: 60000
  maxIdleTime: 60000

#################自定义配置#################
pts:
  uaa_logout_url: http://auth.yozocloud.cn/api/account/logout
  uaa_userinfo_url: http://auth.yozocloud.cn/api/account/userinfo

  fcs_upload_url: http://${FCS_CLUSTER_IP}:8080/file/upload
  fcs_convert_url: http://${FCS_CLUSTER_IP}:8080/composite/convert
  fcs_vToken_url: http://${FCS_CLUSTER_IP}:8080/view/vToken
  fcs_downLoad_url: http://${FCS_CLUSTER_IP}:8080/view/download

EOCONF
}

init_yocloud_config() {
	cat <<- EOCONF > $CONF_DIR/yozocloud.properties
uaa.pub-key.url=http://auth.yozocloud.cn/oauth/token_key
uaa.token.endpoint=http://auth.yozocloud.cn/oauth/token
uaa.client.id=web-app
uaa.client.secret=yozo2019
uaa.oauth.session.timeout=7200
uaa.oauth.cookie.domain=yozodocs.cn;yozocloud.cn;yomoer.cn

uaa.redis.server=172.16.20.125
uaa.redis.port=6379
uaa.redis.password=babel220
uaa.redis.pool-size=100
uaa.redis.timeout=2000
uaa.redis.db=1
EOCONF
}

init_config_properties(){
	 cat <<- EOCONF > $CONF_DIR/config.properties
#转码并发设置
convertPoolSize=5
#转换文件超时时间(单位s)
convertTimeout=300

#注册用户文件上传大小(单位m)
mUploadSize=1024
#游客文件上传大小(单位m)
vUploadSize=2
#注册用户每天转换文件的数量
mConvertTimes=50
#游客每天转换文件的数量
vConvertTimes=5
#注册用户允许使用的模块
mConvertModule=1,2,3,4,5,6,7,8,9,10,11,12,13
#游客用户允许使用的模块
vConvertModule=1,2,4,6

#是否定时清除转换后的文件（文件保留X天，<=0 不删除）
clearDay=1
#是否为清理任务的Master服务器,多机部署时只有一台为true,总开关
clearMaster=true
#是否开启定期清理output目录
clearInputDir=false
#是否开启定期清理input目录
clearOutputDir=true
#目标文件设置成多文件夹目录结构
folderFormat=yyyy/MM/dd
#获取转换实例等待时间(小于0表示一直等待直到获取转换实例),单位秒
convertTicketWaitTime=10

#异步线程池配置
#核心线程数:线程池创建时候初始化的线程数
corePoolSize=5
#最大线程数:线程池最大的线程数,只有在缓冲队列满了之后才会申请超过核心线程数的线程
maxPoolSize=50
#缓冲队列:用来缓冲执行任务的队列
queueCapacity=200
#允许线程的空闲时间(秒):当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
keepAliveSeconds=86400
#是否等待所有任务执行完再销毁线程池:true等待所有任务执行完再销毁
waitForTasksToCompleteOnShutdown=true
#销毁线程池最大等待时间(秒):到了设置时间强制销毁,防止等待销毁一直阻塞
awaitTerminationSeconds=60

EOCONF

}

init_log4j2(){
cat > $CONF_DIR/log4j2.xml << EOF
<?xml version="1.0" encoding="UTF-8"?>
<configuration status="info" monitorInterval="60">
    <properties>
        <property name="LOG_HOME">/ptsdata/logs</property>
        <property name="FILE_NAME">pts_error</property>
        <property name="MYLOG_NAME">all_log</property>
        <property name="SIZEROLLING_FILE_NAME">pts_Feedback</property>
        <property name="LOG_LEVEL">INFO</property>
    </properties>
    <appenders>
        <console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="[%p] %d{yyyy-MM-dd HH:mm:ss,SSS} - %l - %m%n"/>
        </console>

        <RollingFile name="DayRollingFile" fileName="\${LOG_HOME}/\${FILE_NAME}.log" filePattern="\${LOG_HOME}/\${FILE_NAME}-%d{yyyy-MM-dd}.log">
            <PatternLayout pattern="[%p] %d{yyyy-MM-dd HH:mm:ss,SSS} - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy modulate="true" interval="1" />
            </Policies>
        </RollingFile>

          <RollingFile name="LogRollingFile" fileName="\${LOG_HOME}/\${MYLOG_NAME}.log" filePattern="\${LOG_HOME}/\${MYLOG_NAME}-%d{yyyy-MM-dd}.log">
            <PatternLayout pattern="[%p] %d{yyyy-MM-dd HH:mm:ss,SSS} - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy modulate="true" interval="1" />
            </Policies>
        </RollingFile>

        <RollingFile name="SizeRollingFile" fileName="\${LOG_HOME}/\${SIZEROLLING_FILE_NAME}.log" filePattern="\${LOG_HOME}/\${SIZEROLLING_FILE_NAME}-%d{yyyy-MM}-%i.log">
            <PatternLayout pattern="[%p] %d{yyyy-MM-dd HH:mm:ss,SSS} - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            <DefaultRolloverStrategy max="30"/>
        </RollingFile>
    </appenders>

    <loggers>
        <root level="\${LOG_LEVEL}">
            <appender-ref ref="Console"/>
        </root>

        <Logger name="R" level="ERROR" additivity="false">
            <appender-ref ref="DayRollingFile"/>
        </Logger>

        <Logger name="N" level="\${LOG_LEVEL}" additivity="true">
            <appender-ref ref="LogRollingFile"/>
        </Logger>

        <Logger name="Feedback" level="INFO" additivity="false">
            <appender-ref ref="SizeRollingFile"/>
        </Logger>
    </loggers>
</configuration>

EOF


}


redis_flag() {
    if [ -z "$REDIS_HOST" -a -z "$REDIS_PORT" -a -z "$REDIS_PASS" -a -z "${FCS_CLUSTER_IP}" ]; then
       echo >&2 'error: ptsserver is uninitialized and enviroment option is not specified'
       echo >&2 'You need to specify one of REDIS_HOST, REDIS_PORT, REDIS_PASS and FCS_CLUSTER_IP'
       exit 1
    else
       flag=0
    fi
}

log4j_dir() {
    #sed -i -e "s@ /users/yozo/log@/users/yozo/tomcat/logs@g" $CONF_DIR/log4j2.xml
    sed -i "s@ /users/yozo/logs/pdf2word@/dcsdata/logs@g" $CONF_DIR/log4j2.xml
}

redis_flag
if [ "$flag" -ne 1 ]; then
    init_redis_config
    init_yocloud_config
    init_config_properties
    #log4j_dir
    init_log4j2
    mkdir -p /ptsdata/logs
fi

exec "$@"
