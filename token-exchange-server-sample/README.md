# Token Exchange Server Sample

A server application that demonstrates Tapkey's Token Exchange grant type.

## Endpoints

* `GET /public-key`: Returns the public key of the key pair in use.
* `POST /user`: Creates a new local use that can be used for HTTP Basic
  Authentication in the `/user/tapkey-token` operation.
* `GET /user/tapkey-token`: Returns a JWT token that can be exchanged for a
  Tapkey access token. Requires Basic Authentication.
* `GET /user/grants?grantIds=1,2,3,4`: Returns application-specific grant
  information for the specified grant IDs.
  
## Setup
* Clone this repository
* Change into `/token-exchange-server-sample/`
* Copy `/src/main/resources/application.properties.default` to
  `/src/main/resources/application.properties` and fill in all missing
  configuration values.
* Run `mvn clean install`
* Run `mvn spring-boot:run`
* The application is now served from `http://localhost:8080`
