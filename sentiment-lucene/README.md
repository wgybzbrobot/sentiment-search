LuceneIndex

运行环境：
jdk-1.7
tomcat-7.0.47

访问地址：
http://localhost:8080/LuceneIndex/IndexServerPort

http://localhost:8080/LuceneIndex/IndexServerPort?wsdl

# myeclipse web service:
1. create a 'Web Service Project'
2. create a class and add a method which will be a service interface
3. create a 'Web Service'
   3.1 choose 'JAX-WS' and 'from Java class',not 'from WSDL document', choose.
   3.2 choose Java class just created, and 'Generate WSDL in project'.
4. finish.

# myeclipse web service client:
1. create 'Java Project'.
2. create 'Web Service Client'
   2.1 choose 'JAX-WS'.
   2.2 paste 'WSDL URL' from web service, edit java package.
3. create test class.



