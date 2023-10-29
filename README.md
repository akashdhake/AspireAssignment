# Loan Financing Application

The Loan Financing Application is a Java-based web application that provides a platform for managing loans, repayments, and user authentication. It includes features for creating users with different roles, maintaining authentication and authorization, loan approval by administrators, loan requests by customers, repayment scheduling (weekly, monthly, or yearly), customer repayments, and tracking of loan repayments.

## Features

1. **User Management:** The application allows the creation of users with specific roles, ensuring secure access and actions based on user roles.
2. **Authentication and Authorization:** Users can authenticate, and the application enforces proper authorization for various functionalities.
3. **Loan Approval:** Administrators can approve loan requests made by customers.
4. **Loan Request:** Customers can request loans with specified parameters like loan amount, term, and repayment frequency.
5. **Repayment Scheduling:** Repayments can be scheduled on a weekly, monthly, or yearly basis.
6. **Customer Repayments:** Customers can make repayments for their loans.
7. **Loan Repayment Tracking:** The application tracks loan repayments for users and provides relevant data.

## API Endpoints

The application exposes the following API endpoints for various functionalities:

1. **User Creation:** API to create users with specific roles.
2. **List Loans and Repayments:** APIs to list loans and repayments for a specific user.
3. **Request Loan:** API for customers to request a loan.
4. **Loan Approval:** API for administrators to approve loans.
5. **Repayment:** API for users to make repayments.

## Data Storage

The application uses in-memory HashMaps to store user, loan, and repayment data. This approach simplifies data management and is suitable for testing and prototyping.

## Prerequisites

Before you get started with the Loan Financing Application, make sure you have the following prerequisites installed on your system:

- **Java 11:** The application is built using Java 11. You can download and install Java 11 from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use an open-source distribution like OpenJDK.

- **Maven:** The project uses Maven as the build and dependency management tool. You can download and install Maven from the [official website](https://maven.apache.org/download.cgi).

Make sure you have these prerequisites in place to successfully build and run the application.

## Getting Started

Follow these steps to run the Loan Financing Application:

1. Clone the repository to your local machine.
2. Build and run the application.
3. Run the JUnit tests to test the application
4. Use the provided API endpoints to interact with the application.

## Dependencies

The application is built using Java and Spring Boot, with various dependencies for web and data management. Make sure to review the `pom.xml` file for a list of dependencies used.

