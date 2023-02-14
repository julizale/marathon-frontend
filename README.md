# Marathon Frontend

## Table of contents
* [General Information](#general-information)
* [Setup](#setup)
* [Description](#description)
* [Plans for further development](#plans-for-further-development)

## General Information

**Project Marathon** is the application that provides functionality of web service for a nonexistent, hypothetical sporting event „Częstochowa Marathon”.
Includes REST API to provide functionality for administration of the event. 
There is also the frontend layer, implemented with the Vaadin library.

## Setup
- Backend app is deployed to server, so is database with sample data to play with,
so you can just launch frontend app. It starts locally on port **8090**


If for some reason the server is down, you can follow the steps below:
- link to backend: [https://github.com/julizale/marathon](https://github.com/julizale/marathon)
- configure database connection in backend's `application.properties`
- change backendUrl in frontend's `application.properties` : `backendUrl=http://localhost:8080/`
- launch the backend application
- launch frontend: application starts locally on port **8090**

## Description
The frontend application offers an administrator view that allows you to enter, delete and edit race and users data.

Two external data sources were used for support purposes:
- you can check the weather in recent years on the day scheduled for the event
- completing the name of the city based on the postal code when adding a user (limit 100 queries / day)

The backend application includes database support, and a REST API providing endpoints for operations related with the event.
Entities and related tables are modeled:
- User – representing users, along with their personal data. 
- Race - there may be several races at different distances within the sporting event.
- Team - optionally, the user can belong to a team of runners.
- Performance - connecting the user with a specific race (one user may take part in one race).
Creating a performance is equivalent to signing up a user for a race.
After paying for the event (payment made outside the website) administrator should give the user a starting number (bibNumber).
After the run, you may update the status from default DNS – Did Not Start to Finisher or DNF - Did Not Finish, and enter the netto and gross time.

In application, there is a scheduler implemented to send a daily e-mail to the administrator 
with information about the number of users registered for races. You may change administrator 
credentials in backend's `application.properties`

You can explore endpoints with Swagger at address `/swagger-ui/index.html#/`

## Plans for further development
- user authentication & authorization
- creating a user view
- recording of events taking place in the system to the database for information purposes

There's still plenty to do, like fixing minor issues, e.g. validating bibNumbers to be unique within one race, 
possibly automating the process of setting bibNumbers, and many more.
I will gradually introduce changes as time permits.