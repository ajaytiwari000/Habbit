**Steps and tools that you need to install first:**

1. JDK 8

2. Maven

   1. Mac OS Homebrew user try `brew install maven`. Ubuntu users can run `sudo apt-get install maven`.

3. Download [IntelliJ](https://www.jetbrains.com/idea/) or [Ecllipse]( https://www.eclipse.org/downloads/) then setup the plugins [Lombok](https://projectlombok.org/setup/maven) and [Database Navigator](https://plugins.jetbrains.com/plugin/1800-database-navigator) to get started with the [Spring Boot](https://spring.io/quickstart) Project

4. Set the environment variables and check if everything is installed properly.

5. MySQL server (below given are tools to inspect data and avoid cli operation so these are optional if you want to work with CLI)

   1. [Wrokbench](https://www.mysql.com/products/workbench/)

   2. [Sequel](https://sequelpro.com/docs/get-started/get-connected/local-mysql) OR [Follow this tutorial](https://youtu.be/UcpHkYfWarM)

6. Add these credentials to your MySQL while installing 

   1. username: **root**
   2. password: **herculus**
   3. localhost: **127.0.0.1 (default)**
   4. port: **3306**

7. After step 5 you can run this snippet

   ```sql
   CREATE DATABASE SALESMANAGER;
   CREATE USER root IDENTIFIED BY 'herculus';
   GRANT ALL ON SALESMANAGER.* TO root;
   FLUSH PRIVILEGES;
   ```

8. Download/Clone the source code from Github repository.

9. Search for  File **'database.properties**' inside **shop-sm** and make sure your code lines has these values in it:

   ```properties
   ##
   ## db config
   ##
   
   MYSQL
   db.jdbcUrl=jdbc:mysql://127.0.0.1:3306/SALESMANAGER?allowPublicKeyRetrieval=true&useSSL=false
   db.user=root
   db.password=herculus
   db.driverClass=com.mysql.jdbc.Driver
   hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
   
   db.show.sql=true
   db.preferredTestQuery=SELECT 1
   db.schema=SALESMANAGER
   hibernate.hbm2ddl.auto=update
   
   ##
   ## configuration pooling base de donn�es
   ##
   db.initialPoolSize=4
   db.minPoolSize=4
   db.maxPoolSize=8
   ```

10. Now try building and running using commands given below

   For building (when you are inside the main directory i.e [current_project]):

```bash
   mvn clean install
```

   For running the local server (make sure you are inside shop-sm i.e, [cureent_project]/shop-sm):

```bash
   mvn spring-boot:run
```

11. If you get any error please try to go through it once and check the statement. If it's a formatting error then it will give a command to correct it well in the last paragraph of the log. Else make sure that you have performed the all operation well. If the error still exist please contact us back. 
12. If you want to access the new feature in the backend you have to use step 7 first then use step 10. This will erase the data from DB :cry: but it's necessary to update the newly created schema.