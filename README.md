# DocuGen
The main purpose of this application is to help create a contract for a wedding musical setting between engaged couple and musicians (mainly organists, vocalists). App can be extended in the future for generating another type of documents.

In an app a user can make typical CRUD operations for a musical contracts (creating, reading, updating, deleting). Each contract consists of several groups of data e.g. contact information of customer and performer, event details, important dates, choosen service package and payment information.

Service package items can be chosen from a list of predefined items (e.g. organs, violin, string quartet etc.). There is also available a text box for typing additional service information. A list of items to choose can be changed in another application view.

One of the most important functions of an application is ability to create and download PDF files containing formatted contract with all information typed in wedding ceremony contract form.

## Technologies used
- Java 8
- Spring 5
- Spring Boot 2
- Spring Data JPA
- Flying Saucer / OpenPDF
- Project Lombok 
- Thymeleaf
- Bootstrap 4
- JUnit 4
- Mockito 

## TODO
- Allow caching generated PDFs for future downloading

## Tests status
[![CircleCI](https://circleci.com/gh/karolgrudzinski/docugen.svg?style=svg&circle-token=f02c9138e06dd42970efb4465fd42c6d3b57bcff)](https://circleci.com/gh/karolgrudzinski/docugen)
[![codecov](https://codecov.io/gh/karolgrudzinski/docugen/branch/master/graph/badge.svg?token=ZN03n5CTFr)](https://codecov.io/gh/karolgrudzinski/docugen)


