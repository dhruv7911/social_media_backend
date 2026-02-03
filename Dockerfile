FROM debian:stable-slim

# ---------- Base packages ----------
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    tar \
    gzip \
    ca-certificates \
 && rm -rf /var/lib/apt/lists/*

# ---------- Install Java 25 ----------
RUN wget -O jdk.tar.gz https://api.adoptium.net/v3/binary/latest/25/ea/linux/x64/jdk/hotspot/normal/eclipse \
 && mkdir -p /opt/java \
 && tar -xzf jdk.tar.gz -C /opt/java --strip-components=1 \
 && rm jdk.tar.gz

ENV JAVA_HOME=/opt/java
ENV PATH="$JAVA_HOME/bin:$PATH"

# ---------- Install Tomcat 11.0.18 (FAST CDN) ----------
RUN wget https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.18/bin/apache-tomcat-11.0.18.tar.gz \
 && tar -xzf apache-tomcat-11.0.18.tar.gz \
 && mv apache-tomcat-11.0.18 /opt/tomcat \
 && rm apache-tomcat-11.0.18.tar.gz

# remove default ROOT app
RUN rm -rf /opt/tomcat/webapps/ROOT

# ---------- Copy WAR ----------
COPY target/server.war /opt/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["/opt/tomcat/bin/catalina.sh", "run"]
