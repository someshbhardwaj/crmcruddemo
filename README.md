# CRM CRUD Demo
Spring CRM CRUD demo with Spring Security and MySQL as DB

The code runs on the localhost
Below are important changes made in the application.properties 
server.port=5000

I am using two separate MySQL databases.
1. First database is used to store secure data used in customer login and logout.    
   Database url : security.datasource.jdbc-url=jdbc:mysql://localhost:3306/spring_security_demo_bcrypt?useSSL=false&serverTimezone=UTC

2. Second data is used to store customer data.     
  Database url : app.datasource.jdbc-url=jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false&serverTimezone=UTC

I deployed this on AWS so you need to change them from localhost to the endpoint provided by the RDS instance in the AWS.

Below is the sample how it might look like:

app.datasource.jdbc-url=jdbc:mysql://springonaws1.cwni6rgktrat.us-east-1.rds.amazonaws.com:3306/web_customer_tracker?useSSL=false&serverTimezone=UTC

security.datasource.jdbc-url=jdbc:mysql://springonaws1.cwni6rgktrat.us-east-1.rds.amazonaws.com:3306/spring_security_demo_bcrypt?useSSL=false&serverTimezone=UTC

Root Password to access DB is also set in the application.properties:

app.datasource.username=springstudent

app.datasource.password=springstudent

security.datasource.username=springstudent

security.datasource.password=springstudent


The endpoints are as follows:

http://localhost:5000/api/customers/   (GET API)

http://localhost:5000/api/listPageable?page=0&size=5&sort=lastName

http://localhost:5000/api/customers/   (Put or Update API)

http://localhost:5000/api/customers/1    (GET INDIVIDUAL DETAIL OF CUSTOMER)

http://localhost:5000/api/searchCustomer/man

http://localhost:5000/api/customers/1     (DELETE INDIVIDUAL ENTRY FROM DB)


I have used JPA and Hibernate and Simple Query DAO Implmentation class.

PREREQUSITE:
1. Eclipse should be there in the system
2. MySQL DB should be installed in the system.
One needs to run below DB scripts:

CREATE DATABASE  IF NOT EXISTS `web_customer_tracker` 

USE `web_customer_tracker`;

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  
  `first_name` varchar(45) DEFAULT NULL,
  
  `last_name` varchar(45) DEFAULT NULL,
  
  `email` varchar(45) DEFAULT NULL,
  
  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;



DROP DATABASE  IF EXISTS `spring_security_demo_bcrypt`;

CREATE DATABASE  IF NOT EXISTS `spring_security_demo_bcrypt`;

USE `spring_security_demo_bcrypt`;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (

  `username` varchar(50) NOT NULL,

`password` char(68) NOT NULL,

`enabled` tinyint(1) NOT NULL,

PRIMARY KEY (`username`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `authorities` (

`username` varchar(50) NOT NULL,

`authority` varchar(50) NOT NULL,

UNIQUE KEY `authorities_idx_1` (`username`,`authority`),

CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

The user needs to have Correct ROLES to do operation on the Customer table.

See the sample code of the check made:
@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/api/customers").hasRole("EMPLOYEE")
    
		.antMatchers(HttpMethod.GET, "/api/customers/**").hasRole("EMPLOYEE")
		
    .antMatchers(HttpMethod.POST, "/api/customers").hasAnyRole("MANAGER", "ADMIN")
		
    .antMatchers(HttpMethod.POST, "/api/customers/**").hasAnyRole("MANAGER", "ADMIN")
		
    .antMatchers(HttpMethod.PUT, "/api/customers").hasAnyRole("MANAGER", "ADMIN")
		
    .antMatchers(HttpMethod.PUT, "/api/customers/**").hasAnyRole("MANAGER", "ADMIN")
		
    .antMatchers(HttpMethod.DELETE, "/api/customers/**").hasRole("ADMIN")
		
    .antMatchers(HttpMethod.GET, "/api/searchCustomer/**").hasAnyRole("ADMIN", "MANAGER")
		
    .antMatchers(HttpMethod.GET,"/api/listPageable/**").hasAnyRole("ADMIN","MANAGER")
		
.and()

.httpBasic()

.and()

.csrf().disable()

.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
}

The code also supports 3 conurrent session in the localhost

http.sessionManagement().maximumSessions(3);




Hope this code is useful for you!!!

Enjoy Coding




