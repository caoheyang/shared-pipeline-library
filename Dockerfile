FROM jenkinsci/blueocean
ADD  apache-maven-3.6.3-bin.tar.gz /usr/local/
ENV  MAVEN_HOME=/usr/local/apache-maven-3.6.3
ENV  PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
USER root
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
USER jenkins