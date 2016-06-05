# Demonstration of JSON Web Token authentication with Spring Security

## [Auth0](https://auth0.com/) as JWT implementation

### Cloning and running from terminal
1. `git clone git@github.com:jnaalisv/gesha.git`
2. `cd gesha`
3. `./gradlew jettyRunWarDebug`

### Importing to IDE
Importing as "gradle project" is supported by latest Eclipse and IntelliJ IDEA 

### Usage examples
The application is initialized with following users:

```
{"username": "Admin", "password": "admin"}
{"username": "User", "password": "topSecret"}
{"username": "INTEGRATION", "password": "abc123"}
```

To get authentication token for 'Admin' user:

```
curl -X POST -H "Content-Type: application/json" \ 
	-d '{"username": "Admin", "password": "admin"}' \
	http://localhost:8080/gesha/authenticate
```

Then use the returned JSON Web Token in place of TOKEN to query for all users:

```
curl -X GET http://localhost:8080/gesha/users \
	-H "Authorization: TOKEN"
```