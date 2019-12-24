FROM jenkinsci/blueocean
USER root
RUN apt-get update && apt-get install -y libltdl7.*
RUN apt-get install vim -y
RUN apt-get install maven -y