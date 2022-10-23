docker build -t vaadin-petstore .
#docker build --build-arg jarsrc='https://oss.sonatype.org/service/local/repositories/snapshots/content/com/net128/oss/web/app/petstore/0.0.1-SNAPSHOT/petstore-0.0.1-20221023.131733-1.jar' -t vaadin-petstore .
docker build --build-arg jarsrc="https://oss.sonatype.org/service/local/artifact/maven/redirect?r=snapshots&g=com.net128.oss.web.app&a=perstore&v=LATEST" -t vaadin-petstore .
