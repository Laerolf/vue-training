# api

A Helidon MP application representing the NovaGate API for booking space shuttles across the galaxy.

## Build and run

With JDK21
```bash
mvn package
java -jar target/api.jar
```

## Trying the application

JSON:
```
curl -s -X GET http://localhost:8000/health
{
  "status": "UP",
  "checks": [
    {
      "name": "dbreadinesscheck$proxy$_$$_weldclientproxy",
      "status": "UP",
      "data": {
        "description": "Checks if the database is ready to be used.",
        "readyTime": "2025-02-01T10:11:09.276434300+09:00[Asia/Tokyo]"
      }
    }
  ]
}
```

Feel free to try out more endpoints with the [Open API UI](http://localhost:8000/openapi)!

### Database Setup

In the `pom.xml` and `application.yaml` we provide configuration needed for h2 database.
Start your database before running this application.