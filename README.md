# Expense-Reimbursement-System 

## Project Description
The Expense Reimbursement System manages the process of reimbursing employees for expenses incurred. 

## Technologies Used
- Javalin, version 4.1.1
- Java, version 1.8.0
- slf4j version 1.7.32
- Gradle
- Javascript
- HTML
- CSS
- React

## Features

- Employees are given the option to be registered into the system with a chosen unique username and a(n) auto generated account Id that will be associated with the account. 
- An employee will be able to login to the account with the corresponding username and password, and submit a reimbursement request for later approval .
- The employee may display previous request that have been resolved and filter the result by type of reimbursement. 
- Pending requests may also be viewed and its results can also be filtered by type. 

- Managers are given the same functionality as employees, in additions to added features
- View all pending reimbursement requests made by the employee, and filter the request by type, author, and reimbursement id (as indvidual filters or cumulative)
- View all resolved requests and filter the results by reimbursement type, id, and author. 
- Managers may choose to deny or approve pending requests (by clicking on react-icons) after filtering the results based on type, author, or reimbursement id.

- Employees and Managers are able to easily navigate through the page with the nav bar present on each screen

To-do List
- Implement password enscription
- Filter results by data within a given range
- Style user interfaces according to a consistent layout for better user experience
- Use local storage to have data persistent after refreshing the page

## Getting Started
1. Clone repository into the a working directory with the following command:
  git clone https://github.com/robpduo/Expense-Reimbursement-System.git

2. Run the Javalin server in the root directory under app/src/main/java/com.revature/driver.java

3. Run the React front-end components by changing directory into ers-frontend folder from the root director:
  cd ers-frontend/
  npm start

4. Setup the database using the script file provided in the resource folder located in app/src/main/resources/ers script.sql

## Usage
### As an Employee
1. Register a new user as an employee<br/>
![image](https://user-images.githubusercontent.com/101683611/171869050-227f3c2c-ebf6-4a57-8e98-e53c21303cbf.png)
![image](https://user-images.githubusercontent.com/101683611/171869334-eab03ea0-7b10-4582-bb05-65490cdb42f5.png)

2. Login using the same credential you used to register your account<br/>
![image](https://user-images.githubusercontent.com/101683611/171869679-123f073f-21cc-483e-a0bb-ddcf3121217b.png)<br/>

3. Use the navigation bar at the top of the page to submit a reimbursement form
![image](https://user-images.githubusercontent.com/101683611/171869963-18b7be04-a7de-4e82-893d-cce164b5de66.png)<br/>

4. After navigating to the reimbursement form, fill out the form to submit a request<br/>
![image](https://user-images.githubusercontent.com/101683611/171870157-26ebf2f0-24ac-47bc-adba-610a7898825a.png)<br/>

5. You may view any pending and resolved request by navigating to the corresponsing page
![image](https://user-images.githubusercontent.com/101683611/171870896-db2d777f-5dfb-4383-b4ae-ab76baeb9886.png)<br/>
![image](https://user-images.githubusercontent.com/101683611/171870963-51972433-2026-495e-b558-ba186c3e83aa.png)<br/>

6. You may filter through the results by using the form near the top of the page<br/>
![image](https://user-images.githubusercontent.com/101683611/171871219-daeb403e-6dfd-404f-a708-fbc5ff534964.png)<br/>

7. Logout button will navigate you back to the login menu and ending the current session

### As a Manager
1. After logging in, the navigation bar will include an extended features list
![image](https://user-images.githubusercontent.com/101683611/171871672-230bd401-d6fb-4914-b240-d5bc27c0aa02.png)<br/>

2. Viewing all pending requests will give you the option to filter and resolve the pending requests, clicking on the X will deny the request, while the checkmark will approve the request
![image](https://user-images.githubusercontent.com/101683611/171871829-d710ff06-f50e-4fcd-a92e-1714dee4d066.png)<br/>

3. Viewing all resolved request will bring a table of all past requests, the filter option is present at the top of the screen
![image](https://user-images.githubusercontent.com/101683611/171872136-b09a8f1d-2b22-42a8-9503-bd8ed1a39a8c.png)<br/>














  
