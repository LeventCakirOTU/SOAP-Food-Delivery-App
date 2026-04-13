# SOAP-Food-Delivery-App
The purpose of this project is to create a food delivery app with four main types of users.

## Overview:

## High level goals & expected outcomes:
The main goal is to create a user interface which enables customers to browse nearby restaurants, explore their menus and place orders, but also allow delivery drivers to accept and manage their tasks, as well as allow restaurant owners to include their business and sell their products visible in their menu. Throughout the development of this project, the team will ensure the final product is delivered to meet the client’s expectations on time. The expected outcome of this project is to have a fully functioning delivery app where customers can easily order food from various restaurants in their vicinity. 

## Iteration Progress
Iteration 1: COMPLETE  
Iteration 2: COMPLETE  
Iteration 3: COMPLETE

## Final Burndown Chart (April 13, 2026)
<img width="846" height="458" alt="Screenshot 2026-04-12 182703" src="https://github.com/user-attachments/assets/a819eb7f-4fd7-43d7-86ea-45dacf658795" />

## Basic Setup Instructions:
Initial IDE Build Tool Requirements: Find pom.xml in the directory, right click and select add as a maven project. Navigate to Settings>Build,Execution,Deployment>Build Tools>Maven>Runner. From there, select an SDK and enable Delegate IDE build/run actions to Maven.  

To Register and Login:  
1) Choose to register as either a Customer, Driver or Manager.
2) After creating your account, use the same information to login into the type of user account.
3) After logging in, the user can now see and view the actions they can do as that type of user.

To View Restaurants:  
1) In the main menu, select 'Browse Restaurants'.

To Create a Restaurant:  
1) In the main menu, select 'Add Restaurant'.
2) Fill in the attributes of the restaurant in the designated areas (optional: add starter menu items).

## Running the JAR file instructions:

1) Download the file food-delivery-app.jar.
2) Find the location food-delivery-app.jar is stored in.
3) Open a terminal in the folder where the file is located. You can do this by right-clicking inside the folder and selecting “Open PowerShell window here” or “Open Terminal here”.
4) Run the application using the command java -jar food-delivery-app.jar.

## Features:
- Customers, Drivers, Restaurant Managers, and Admins have separate logins
- Customers can order menu items from a list of restaurants
- Customers can view restaurants by proximity
- Drivers can view a list of customers' orders and choose which ones to accept
- Drivers can provide updates on orders
- Restaurant Managers can add their restaurant pages and edit the menus
- Restaurant Managers can
- Admins can remove restaurant pages

## Tech Stack (Libraries utilized):
- JUnit (Testing)
- Java Swing (Graphical User Interface)
- Java awt (Graphical User Interface)

## Video Demonstration:
https://github.com/user-attachments/assets/859be62e-6ee9-4bfa-86c6-dd106b366790

https://github.com/user-attachments/assets/948cab72-2611-45a6-92d8-7a6b061ed350

## Contributors:
### Levent Cakir
* General team management
* README basic setup instructions
### Ryan Lildhar
* App GUI
* UI and back-end integration
* Implemented features:
  - Checkout
  - Order history
### Dylan Reid
* Build scripts
* Implemented features:
  - Search filter
  - Restaurant proximity
### Greg Merola
* Unit tests
* Testing plan
### Edwin Li
* Team productivity + velocity calculations
* README file documentation
* Phase documents
