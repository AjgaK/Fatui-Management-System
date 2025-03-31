# Fatui Organizational System

A project developed for the course **Design and Analysis of Information Systems** at PJATK, focusing primarily on the design and implementation of UML diagrams in Java.

## Project description

This system was created to manage **missions, employees (agents and harbingers), regions**, and related information for a fictional organization called Fatui. It allows users to assign employees to missions, manage employee hierarchy structure, manage mission details, and view information about regions, their gods, and leadership.

The goal of this project was to model complex relationships between entities using UML and transform the diagrams into a working implementation using best practices in object-oriented design.

## Main features
- **Employee management**
  - Add/view agents and harbingers
  - Support for overlapping roles (special/senior/normal agents)
  - Agent supervision and role reassignment
- **Mission management**
  - Create missions with a start date, deadline, description, and budget
  - Automatic fund calculation based on assigned harbingers and agents
  - Assign employees to missions and manage resources
- **Region information**
  - Display region details with their governing leaders and gods
  - Show god attributes like element, age, and titles

## Technologies used

- **Spring**
- **Thymeleaf** – for HTML view rendering
- **H2 Database** – in-memory DB
- **Lombok** – to reduce boilerplate code

