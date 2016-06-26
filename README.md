## SalesFast, a Sales activity management tool for Pharmaceutical organizations

An interactive application to assist Sales Representatives and Managers to handle the sales activities better. Some intelligent features are incorporated in this application that help SalesRep on the field and Managers take informed decisions and drive towards better profit. 

### System details:

#### Detailed information regarding the business assumed for this system is present in /catalog/catalog.md file of the project. Following points are covered in this document.
- Information about the roles and responsibilities of various levels assumed
- Problems that the organization is facing in day to day sales activities
- What users are expecting from system
- How Salesfast addresses these problems and provides smart solutions
- Merits of the system and specific functionalities that achieve those merits
 
### System Setup:

#### To setup this system on your local machine, execute below mentioned steps:

- Clone this repository into your local system
- Create an empty database named 'salefast' in your local MySql server
- Import sql dump /datamode/salesfast_dump.sql into this database. To do this you can run this command:
```mysql -u username -p salesfast < location/salesfast_dump.sql```
- Go to root directory of the project and run following command
```mvn spring-boot:run```
 
### User credentials to log in:

#### There are multiple users in the system for each level of hierarchy, namely Sales Representative, District Manager, and Physicians. You can find there credentials in 'user' table in database. 
