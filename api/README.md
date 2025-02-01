# api

A Helidon MP application representing the Company Space API for booking space shuttles accross the galaxy.

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

curl -X GET http://localhost:8000/location
[
  {
    "id": "1",
    "name": "Earth",
    "latitude": 0,
    "longitude": 0,
    "radialDistance": 27000
  }
]

curl -X GET http://localhost:8000/location/1
{
  "id": "1",
  "name": "Earth",
  "latitude": 0,
  "longitude": 0,
  "radialDistance": 27000
}
```

Feel free to try out more endpoints with the [Open API UI](http://localhost:8000/openapi)!

### Database Setup

In the `pom.xml` and `application.yaml` we provide configuration needed for h2 database.
Start your database before running this application.

In the `/conte` folder, run the following command to run the database:

```
docker-compose up -d
```

## Docker 
### Building the Docker Image

```
docker build -t api .
```

### Running the Docker Image

```
docker run --rm -p 8000:8000 api:latest
```