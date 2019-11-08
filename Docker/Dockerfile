FROM hub.yozocloud.cn/base/tomcat9-oracle-jdk8-pdf2word:v1.0 
MAINTAINER yongjunshan <yongjunshan@gmail.com>

ENV CATALINA_HOME /users/yozo/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH
ENV CONF_DIR $CATALINA_HOME/webapps/ROOT/WEB-INF/classes

WORKDIR $CATALINA_HOME

ADD ROOT $CATALINA_HOME/webapps/ROOT

COPY docker-entrypoint.sh /usr/local/bin
COPY othersitefile/* $CATALINA_HOME/webapps/ROOT/WEB-INF/classes/static/ 

EXPOSE 8080

ENTRYPOINT [ "docker-entrypoint.sh" ]

CMD ["catalina.sh", "run"]
