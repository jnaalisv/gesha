# Demonstration of JSON Web Token authentication with Spring Security

## [Auth0](https://auth0.com/) as JWT implementation

```
git clone git@github.com:jnaalisv/gesha.git
cd gesha
./gradlew bootRun
```

### Usage examples
The application is initialized with following users:

```
{"username": "Admin", "password": "admin"}
{"username": "User", "password": "topSecret"}
{"username": "INTEGRATION", "password": "abc123"}
```

To get authentication token for 'Admin' user:

```
curl -X POST -H "Content-Type: application/json" -d '{"username": "Admin", "password": "admin"}' http://localhost:8080/authenticate
```

Then use the returned JSON Web Token in place of TOKEN to query for all users:

```
curl -X GET http://localhost:8080/users -H "Authorization: Bearer TOKEN"
```


#### Docker
Build docker image

```bash
# docker build -t gesha .
```

Run the build docker image
```bash 
# docker run -p 8080:8080 -it --rm --name running-gesha gesha

```
