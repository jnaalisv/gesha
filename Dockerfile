FROM openjdk
RUN mkdir /opt/gesha
COPY build/libs/gesha.jar /opt/gesha
EXPOSE 9000
CMD ["java", "-jar", "/opt/gesha/gesha.jar"]
