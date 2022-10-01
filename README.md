
# locateuser

Project with user and location crud operation. Following are features
    
    1) Save or update user
    2) Partial update user
    3) Save user location.
    4) Fetch user info with latest location.
    5) Fetch user locations for given date range.





    








## Steps to Setup
* Note : It is maven based project. Please install and setup maven before using project. 

#### 1)clone the project
    git clone https://github.com/pawankhatri22/locateuser.git

#### 2) run following commands
    cd locateuser
    mvn clean install
    java -jar target/locate-user-0.0.1-SNAPSHOT.jar

project will start running. you can import postman collection in locate user folder with name JitPay.postman_collection and use apis easily.


## Exploring the APIs
This project contains following apis

#### 1) Save or Update User   
* Api to save or update

        POST localhost:8080/user
        Request Body 
        {
        "createdOn": "2022-02-08T11:44:00.521",
        "email": "alex.schmid@gmail.com",
        "firstName": "asd",
        "secondName": "Schmid"
        }

![image](https://user-images.githubusercontent.com/32278634/193406682-92eedf25-3215-4126-bc12-a0f0bd40e5a9.png)



#### 2) Parital  User    
* Api to update different params of user. Add any param of user object in  body that you want to update
    
        Patch localhost:8080/user?userId=2e3b11b0-07a4-4873-8de5-d2ae2eab26b2
        Request Body 
        {
        "email":"asd@hail.com"
        }

![image](https://user-images.githubusercontent.com/32278634/193406933-fddb6d4c-675b-4d2a-ab22-160e7325d976.png)



#### 3) Save User Location 
* Api to save user Location

        POST localhost:8080/user/location
        Request Body 
        {
            "userId": "2e3b11b0-07a4-4873-8de5-d2ae2eab26b2",
            "createdOn": "2022-02-13T07:43:00.521",
            "location": {
                "latitude": 58.257423425784111,
                "longitude": 10.540583401747602
            }
        }

![image](https://user-images.githubusercontent.com/32278634/193406960-f5409f32-cf4d-4084-8abd-113d46d10f13.png)


#### 4) Fetch User with Latest Location
* API to fetch user with latest location for given user id.

        GET localhost:8080/user/2e3b11b0-07a4-4873-8de5-d2ae2eab26b2/location
       
![image](https://user-images.githubusercontent.com/32278634/193406993-57647ba4-8208-4e6e-976e-9ecdd1ab7de4.png)


#### 5) Fetch User with Location List
* Api to fetch User with List of location between given date range. 

        GET localhost:8080/user/2e3b11b0-07a4-4873-8de5-d2ae2eab26b2/location/between?fromDate=2022-02-01T07:43:00.524&toDate=2022-02-13T07:43:00.524
       

![image](https://user-images.githubusercontent.com/32278634/193407021-bf054b92-33fe-48bb-b8b3-f9c67f5a328e.png)


    








    



