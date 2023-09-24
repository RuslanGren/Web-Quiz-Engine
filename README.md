#  Web Quiz Engine (Java) 

## About the project 
On the Internet, you can often find sites where you need to answer some questions.<br/> 
It can be educational sites, sites with psychological tests, job search services, <br/>
or just entertaining sites like web quests. The common thing for them is the ability <br/> 
to answer questions (or quizzes) and then see some results. In this project, you will <br/>
develop a multi-users web service for creating and solving quizzes.

## Learning outcomes
In this project, we develop a multi-user web service for creating and solving quizzes <br/>
using REST API, an embedded H2 database, security, and other technologies. <br/>
Here, we concentrated on the server side ("engine") without a user interface at all. <br/> 
A client can be a browser, the curl tool, a REST client like postman or something else. <br/>

## How to run
You can download an archive, unzip it inside the directory you want to, and open it in your IDE. 

If you want to clone the repo:

- run the command line in the directory you want to store the app and type the following command: 
  
``git clone https://github.com/Hubertoom/Web-Quiz-Engine-Java.git`` 

- or start with *Project from Version Control* in your IDE by providing the URL of this repository.

Project The project starts on ``localhost/8889`` by default. <br/>
If port `8889` is busy on your machine, you can specify another 
``in applicatoin.properties`` -> ``server.port=``port_number 

## How to use

To test this API, you may use a rest client like [postman](https://www.postman.com/product/api-client/) or the [curl](https://gist.github.com/subfuzion/08c5d85437d5d4f00e58) tool. GET requests can be <br/> 
tested by accessing the URL in your browser. You can also check your application in the browser using [reqbin](https://reqbin.com/).


Before you be able to add, get or solve any quiz you have to register, <br/>
and then have to be authorized via **HTTP Basic Auth** otherwise you get <br/>
```HTTP 401 UNAUTHORIZED``` response. <br/>
To register a new user, you need to send a JSON with ``email`` and ``password`` via ``POST`` request. <br/>
Here is an example:

```json
{
"email":"hubertoom@gmail.com",
"password":"helloworld"
}
```
The service returns ``HTTP 200```, if the registration has been completed successfully.

If another user already takes the email, the service will return ``HTTP 400``.

There are some additional restrictions to the format of user credentials:
	- an email must have a valid format (with @ and .);
    	- password must have at least five characters.

If they are unsatisfied, the service will also return ``HTTP 400``.

All the following operations need a registered user to be successfully completed.

### Create a new quiz

To create a new quiz, you need to send a JSON via ``POST`` request with the following keys:<br/>
- ``title``:string, required;
- ``text``:string, required;
- ``options``: an array of string, it's required, and should contain at least 2 items;
- ``answer``: an array of indexes of correct options, it is optional, if is null or empty it means neither option is correct

An example: 

```json
{
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"],
  "answer": 2
}
```

If the request doesn't contain a title or text or contains an empty string, or a number of options in the quiz <br/>
is less than 2, then the response will be ``HTTP 404 NOT FOUND``. 

### Get a quiz 
To get a quiz, you need to specify its ``id`` in your request:
```http://localhost:8080/api/quizzes/1```
the response will be:

```json
{
  "id": 1,
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
}
```
or ```404 NOT FOUND``` if a quiz if the specified id doesn't exist

### Get all quizzes (with paging)

The number of stored quizzes can be very large. For this sake, obtaining all quizzes if performed page by page,<br/>
10 quizzes at one page"
Example:

```json
{
"totalPages":1,"totalElements":5,"last":true,"first":true, "empty":false,
"content":[
    {"id":1,"title":"<string>","text":"<string>","options":["<string>","<string>","<string>"]},
    {"id":2,"title":"<string>","text":"<string>","options":["<string>", "<string>"]},
    {"id":3,"title":"<string>","text":"<string>","options":["<string>","<string>"]}
]
}
```

You can pass the ```page``` param to navigate through pages ```/api/quizzes?page=3```. <br/>
Counting starts from 0 (this is the very first page). <br/>
The response is ```HTTP 200 OK``` in all cases, even for empty set. <br/>
You will get page will empty ```content``` field.

### Solving a quiz

To solve a quiz, you need to pass an answer as JSON array with indexes via ```POST``` request <br/>
to the ```/api/quizzes/{id}/solve``` endpoint. <br/>
example:

```json
{
  "answer": [2]
}
```
 - for correct answers, the response is:
```json
{
  "success": true,
  "feedback": "Congratulations, you're right!"
}
```
- for incorrect answers:
```json
{
  "success": false,
  "feedback": "Wrong answer! Please, try again."
}
```
If a quiz with given {id} exists, the answer will be ``HTTP 200 OK`` respectively. <br/>
If quiz doesn't exist ```HTTP 404 NOT FOUND``` <br/>

### Getting all completed quizzes (with paging)

The API provides the operation to get all solved quizzes. Because set of data <br/>
can be very large, a response is separated by pages. <br/>

Response example: 

```json
{
  "totalPages":1,
  "totalElements":5,
  "last":true,
  "first":true,
  "empty":false,
  "content":[
        {
            "id": 6,
            "completedAt": "2023-08-13T18:00:00.791+00:00"
        },
        {
            "id": 5,
            "completedAt": "2023-08-13T17:59:58.588+00:00"
        },
        {
            "id": 5,
            "completedAt": "2023-08-13T17:59:54.472+00:00"
        },
        {
            "id": 2,
            "completedAt": "2023-08-13T17:59:45.037+00:00"
        },
        {
            "id": 1,
            "completedAt": "2023-08-13T17:59:33.073+00:00"
        }
  ]
}
```

Since it is allowed to solve a quiz multiple times, the response may contain duplicate quizzes but with different completion dates. <br/> 
The completions will be sorted by their completion time in descending order, i.e. newer completions first, older completions last. <br/>
We removed some metadata keys from the response to keep it more simple to understand. <br/>

### Deleting a quiz
It is possible to delete a quiz, but this can only be done by its creator.<br/>
To do so, you need to specify the quiz id in ```delete``` request <br/>

```http://localhost:8889/api/quizzes/1```

If the operation is successful, the service returns ```HTTP 204 (No content)```. <br/>
If the specified quiz does not exist, the server returns ```HTTP 404 NOT FOUND```. <br/> 
If the specified user is not the creator of this quiz, the response contains ```HTTP 403 Forbidden.```


#### Project page: https://hyperskill.org/projects/91
