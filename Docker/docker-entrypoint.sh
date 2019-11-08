#!/bin/bash
set -eo pipefail
shopt -s nullglob

flag=1

init_redis_config() {
    cat <<- EOCONF > $CONF_DIR/application.yml
fcscloud:
  cache:                            
    type: ${DB_ENGINE}
  redis:
    enable: true                    
  mq:
    enable: false                   
    type: rocket                    
  mysql:
    enable: false                   

spring:
  servlet:
    multipart:                  
      enabled: true             
      max-file-size: 1024MB      
      max-request-size: 1024MB

  redis:
    database: 0 
    timeout: 10000 
    password: ${REDIS_PASS}
    # redis sigle node
    host: ${REDIS_HOST}
    port: ${REDIS_PORT}
    # redis multiple node
    #    cluster:
    #      max-redirects: 3
    #      nodes:
    #        - 127.0.0.1:6379
    #        - 192.168.91.5:9002
    lettuce:
      pool:
        max-active: 3000 
        max-wait: -1
        max-idle: 500 
        min-idle: 100 
      shutdown-timeout: 1000
http:
  maxTotal: 4000
  defaultMaxPerRoute: 400
  connectTimeout: 2000
  connectionRequestTimeout: 2000
  socketTimeout: 60000
  maxIdleTime: 60000

server:
  port: 9090
  servlet:
    context-path: /

logging:
  config: classpath:log4j2.xml

management:
  endpoint:
    health:
      show-details: always
  endpoints:
    web:
      base-path: /actuator
      exposure:
        include: "*"

mybatis:
  mapper-locations: classpath:mapping/*.xml
  type-aliases-package: com.yozo.fcscloud.commom.model

EOCONF
}

init_mysql_config() {
	cat <<- EOCONF > $CONF_DIR/application.yml
fcscloud:
  cache:                          
    type: ${DB_ENGINE}
  redis:
    enable: false
  mq:
    enable: false                   
    type: rocket                    
  mysql:
    enable: true

spring:
  servlet:
    multipart:                  
      enabled: true             
      max-file-size: 1024MB      
      max-request-size: 1024MB   


  datasource:
    druid:
      url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${DB_NAME}?characterEncoding=utf-8          
      driver-class-name: com.mysql.jdbc.Driver
      password: ${MYSQL_PASS}
      username: ${MYSQL_USER}
      filters: mergeStat
      maxActive: 20                 
      initialSize: 1                
      maxWait: 3000                 
      minIdle: 1                    
      timeBetweenEvictionRunsMillis: 60000   
      minEvictableIdleTimeMillis: 300000     
      validationQuery: select 1     
      testWhileIdle: true           
      testOnBorrow: false           
      testOnReturn: false           

  thymeleaf:
    cache: false           
    encoding: UTF-8        
    mode: HTML             
    prefix: classpath:/templates/  
    suffix: .html          
    servlet:
      content-type: text/html   
http:
  maxTotal: 4000
  defaultMaxPerRoute: 400
  connectTimeout: 2000
  connectionRequestTimeout: 2000
  socketTimeout: 60000
  maxIdleTime: 60000

server:
  port: 9090
  servlet:
    context-path: /

logging:
  config: classpath:log4j2.xml

management:
  endpoint:
    health:
      show-details: always
  endpoints:
    web:
      base-path: /actuator
      exposure:
        include: "*"         

mybatis:
  mapper-locations: classpath:mapping/*.xml                          
  type-aliases-package: com.yozo.fcscloud.commom.model       

EOCONF
}

init_mq_config() {
        cat <<- EOCONF > $CONF_DIR/application.yml
fcscloud:
  cache:                            
    type: ${DB_ENGINE:0:5}
  redis:
    enable: false
  mq:
    enable: true
    type: ${DB_ENGINE:0:5}
  mysql:
    enable: false                   

spring:
  servlet:
    multipart:                  
      enabled: true             
      max-file-size: 1024MB      
      max-request-size: 1024MB   

rocketmq:
  producer:
    groupName: Producer             
  consumer:
    groupName: PushConsumer         
    httpGroupName: httpPushConsumer 
  namesrvAddr: ${ROKETMQ_HOST}:${ROKETMQ_PORT}

http:
  maxTotal: 4000                      
  defaultMaxPerRoute: 400            
  connectTimeout: 2000               
  connectionRequestTimeout: 2000     
  socketTimeout: 60000               
  maxIdleTime: 60000                 

server:
  port: 9090                 
  servlet:
    context-path: /          

logging:
  config: classpath:log4j2.xml      

management:
  endpoint:
    health:
      show-details: always   
  endpoints:
    web:
      base-path: /actuator   
      exposure:
        include: "*"          

mybatis:
  mapper-locations: classpath:mapping/*.xml
  type-aliases-package: com.yozo.fcscloud.commom.model

EOCONF
}

init_config_properties(){
cat <<- EOCONF > $CONF_DIR/config.properties
#源文件路径(上传也在这个目录下)
inputDir=/fcsdata/input
#目标文件路径
outputDir=/fcsdata/output
#目标文件设置成多文件夹目录结构
folderFormat=yyyy/MM/dd
#文件上传大小(单位m)
uploadSize=1024
#转码并发设置
convertPoolSize=8
#转换文件超时时间(单位s)
convertTimeout=300
#是否定时清除转换前和转换后的文件(文件保留X天，<=0 不删除),float类型
clearDay=3
#view模块(预览,下载)时拼接的domain地址(需要/结尾)
viewDomain=${VIEW_DOMAINURL}/
#是否为清理任务的Master服务器,多机部署时只有一台为true,总开关
clearMaster=true
#是否开启定期清理output目录
clearInputDir=true
#是否开启定期清理input目录
clearOutputDir=true
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

redis_flag() {
    if [ -z "$REDIS_HOST" -a -z "$REDIS_PORT" -a -z "$REDIS_PASS" -a -z "$VIEW_DOMAINURL" ]; then
       echo >&2 'error: fcsserver is uninitialized and enviroment option is not specified'
       echo >&2 ' You need to specify one of REDIS_HOST, REDIS_PORT, REDIS_PASS and VIEW_DOMAINURL'
       exit 1
    else
       flag=0
    fi
}

mysql_flag() {
    if [ -z "$MYSQL_HOST" -a -z "$MYSQL_PORT" -a -z "$MYSQL_PASS" -a -z "$DB_NAME" -a -z "$VIEW_DOMAINURL" ]; then
       echo >&2 'error: fcsserver is uninitialized and enviroment option is not specified'
       echo >&2 ' You need to specify one of MYSQL_HOST, MYSQL_PORT, MYSQL_PASS, DB_NAME and VIEW_DOMAINURL'
       exit 1
    else
       flag=0
    fi
}

mq_flag() {
    if [ -z "$ROKETMQ_HOST" -a -z "$ROKETMQ_PORT" -a -z "$VIEW_DOMAINURL" ]; then
       echo >&2 'error: fcsserver is uninitialized and enviroment option is not specified'
       echo >&2 ' You need to specify one of ROKET_HOST, ROKET_PORT and VIEW_DOMAINURL'
       exit 1
    else
       flag=0
    fi
}

edit_config() {
    sed -i -e "s@viewDomain=.*@viewDomain="${VIEW_DOMAINURL}/"@g" $CONF_DIR/config.properties
    sed -i -e "s@inputDir=.*@inputDir=/fcsdata/input@g" $CONF_DIR/config.properties
    sed -i -e "s@outputDir=.*@outputDir=/fcsdata/output@g" $CONF_DIR/config.properties
}

log4j_dir() {
    sed -i -e "s@/home/admin/dcs/log@/fcsdata/logs@g" $CONF_DIR/log4j2.xml
}

if [ "$DB_ENGINE" ]; then
    if [ "$DB_ENGINE" = "redis" ]; then
	    redis_flag
	    if [ "$flag" -ne 1 ]; then
	        init_redis_config
		init_config_properties
                #edit_config
                log4j_dir
	    fi
    fi
    
    if [ "$DB_ENGINE" = "mysql" ]; then
	    mysql_flag 
	    if [ "$flag" -ne 1 ]; then
		init_mysql_config
		init_config_properties
                #edit_config
                log4j_dir
 	    fi
    fi

    if [ "$DB_ENGINE" = "roketmq" ]; then
	    mq_flag 
	    if [ "$flag" -ne 1 ]; then
		init_mq_config
		init_config_properties
		#edit_config
                log4j_dir
 	    fi
    fi
else
    echo >&2 'error: fcsserver is uninitialized and enviroment option is not specified'
    echo >&2 ' You need to specify one of DB_ENGINE(redis mysql roketmq)'
    exit 1
fi

exec "$@"
