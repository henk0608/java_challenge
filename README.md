REST API that exposes the sum, subtraction, multiplication and division. 
Offer Support for 2 operands only (a and b).

This Maven project includes two modules: 'rest' and 'calculator'. These modules communicate through RabbitMQ and Spring AMQP.

To run this project, do not forget:

Start docker: 
>> docker run -d --hostname rmq --name rabbit-server -p 8080:15672 -p 5672:5672 rabbitmq:3-management

http://localhost:8080/ to access the rabbitmq server (name and password are predefined as "guest")

Ports: 
 - rabbit server: port 8080
 - 'rest' module: port 8081
 - 'calculator' module: port 8082

Open one terminal on the main directory ('\java_challenge') 
>> mvn clean install

Open another terminal on 'rest' module ('\java_challenge\rest')
>> mvn spring-boot:run

Open another terminal on 'calculator' module ('\java_challenge\rest')
>> mvn spring-boot:run

Test the application by sending GET request through a tool like Postman or http://localhost:8081/sum?a=1&b=2 (example of 1+2 operation)
