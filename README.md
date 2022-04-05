# Course Registration API
This is a Spring boot app for Course Registration. This API helps to 

<li> Creates a new Course. Endpoint: <b> POST: /courses </b>
<li> Get the course details along with participants. Endpoint: <b> GET: /courses/{id} </b>
<li> Search for a course by title. Endpoint: <b> GET: /courses/{title} </b>
<li> Register/Enroll a user/Participant for a course.  Endpoint: <b> POST: /courses/{id}/add </b>
<li> Cancel enrollment for a course.  Endpoint: <b> POST: /courses/{id}/remove </b>
  
<b> Prerequisite: </b>

Java 8 and above
STS or any IDE
Maven

<b> Database </b>
h2

<b> Build: Creates a docker Image with name 'course-api' </b>
mvn clean install -Pdocker-image

<b> Run Application: </b>

docker run -p 50000:50000 course-api

<b> Swagger UI:  </b>

http://localhost:50000/swagger-ui/index.html#