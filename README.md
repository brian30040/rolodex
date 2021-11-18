# Rolodex
## Quarkus Hibernate ORM with Panache
### QAckathon 2021 project

#### openssl commands for generating keys
```
openssl genrsa -out rsaPrivateKey.pem 2048
openssl rsa -pubout -in rsaPrivateKey.pem -out publicKey.pem
```
#### openssl command for converting private key
```
openssl pkcs8 -topk8 -nocrypt \
  -inform pem \
  -in rsaPrivateKey.pem \
  -outform pem \
  -out privateKey.pem
```
#### generate a JWT to use with TokenSecuredResource endpoint
```
mvn exec:java \
  -Dexec.mainClass=com.qackathon.security.GenerateToken \
  -Dexec.classpathScope=test \
  -Dsmallrye.jwt.sign.key.location=privateKey.pem
```
*See [Using JWT RBAC](https://quarkus.io/guides/security-jwt#adding-a-public-key) for details*
#### move the generated keys to the relevant application folders
```
mv privateKey.pem src/test/resources/
mv publicKey.pem src/main/resources/
```
#### create database mount directory
```
mkdir /opt/apps/mysql/quarkus
chmod 777 /opt/apps/mysql/quarkus
```
#### run mysql container
```
docker-compose -f docker-compose-mysql.yml up
```
#### connect to mysql server
```
Host: localhost
Port: 3306
SSID: rolodex_db
User: rolodexL
Password: password
URL: jdbc:mysql://localhost:3306/rolodex_db
```
#### create test data
```
src/main/resources/create-countries.sql
src/main/resources/zipcodes.sql
src/main/resources/import.sql
```
#### start the application in development mode
```
./mvnw compile quarkus:dev
```
*See [Development mode](https://quarkus.io/guides/maven-tooling#dev-mode) for details*
#### Build docker image
```
./mvnw clean package \
  -Dquarkus.container-image.build=true \
  -Dquarkus.container-image.builder=docker \
  -Dquarkus.container-image.build=true \
  -Dquarkus.container-image.group= \
  -Dquarkus.container-image.name=rolodex \
  -Dquarkus.container-image.tag=hillbr \
  -DskipTests
```
#### Stop mysql container
```
docker-compose -f docker-compose-mysql.yml down
```
#### start the application from the base directory containing docker-compose.yml
```
docker-compose up
```
Front end service has not been implemented.
API commands can be sent using curl commands
```
curl --location --request GET \
'http://localhost:8080/contacts?lastName=Kru' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc3VlciIsInVwbiI6ImJyaWFuLncuaGlsbEBvbmVkYXRhc2Nhbi5jb20iLCJncm91cHMiOlsiVXNlciIsIkFkbWluIl0sImJpcnRoZGF0ZSI6IjE5NzItMDgtMTkiLCJpYXQiOjE2MzcxOTE1OTIsImV4cCI6MTYzNzE5MTg5MiwianRpIjoiZDdhN2VjNmQtYzc4ZC00M2E1LWFiYTgtNWJkYTVlZTljMzM0In0.rO9kz9y9D2V_XIv_VQvutc_LllkRJ3BEn07rT3Ek6kBcypa9713we5-ABPMhizyaTxnv32kiL1msjOfz2TW0sJNJ5IyUVI4h2AMlyxQN2YZyOTKkchka7vS4wWQDaW8TmB8VdCi75VEOTaRQiV2h1ND6dcOQ6eoQ3KOTFiZdZ3Oae6M6WnPxwbozKdFXQvtiC5U-oueLcWhK1tD1DbPU-RTGuj7XTwyz9oFjoJiik33AN-5rFeErYIFMVmI3cNk-v6gjcmL6-jTAJixKPUFWBsbLQ76pHCLkTHJmtc3l4vThIOJvoheK5Ay_pa2IkQVN32Q55_Fm2xFPApH5NFVh-g'

curl --location --request GET \
'http://localhost:8080/contacts?firstName=Jo' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc3VlciIsInVwbiI6ImJyaWFuLncuaGlsbEBvbmVkYXRhc2Nhbi5jb20iLCJncm91cHMiOlsiVXNlciIsIkFkbWluIl0sImJpcnRoZGF0ZSI6IjE5NzItMDgtMTkiLCJpYXQiOjE2MzcxOTE1OTIsImV4cCI6MTYzNzE5MTg5MiwianRpIjoiZDdhN2VjNmQtYzc4ZC00M2E1LWFiYTgtNWJkYTVlZTljMzM0In0.rO9kz9y9D2V_XIv_VQvutc_LllkRJ3BEn07rT3Ek6kBcypa9713we5-ABPMhizyaTxnv32kiL1msjOfz2TW0sJNJ5IyUVI4h2AMlyxQN2YZyOTKkchka7vS4wWQDaW8TmB8VdCi75VEOTaRQiV2h1ND6dcOQ6eoQ3KOTFiZdZ3Oae6M6WnPxwbozKdFXQvtiC5U-oueLcWhK1tD1DbPU-RTGuj7XTwyz9oFjoJiik33AN-5rFeErYIFMVmI3cNk-v6gjcmL6-jTAJixKPUFWBsbLQ76pHCLkTHJmtc3l4vThIOJvoheK5Ay_pa2IkQVN32Q55_Fm2xFPApH5NFVh-g'

curl --location --request GET \
'http://localhost:8080/contacts?phone=1910' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc3VlciIsInVwbiI6ImJyaWFuLncuaGlsbEBvbmVkYXRhc2Nhbi5jb20iLCJncm91cHMiOlsiVXNlciIsIkFkbWluIl0sImJpcnRoZGF0ZSI6IjE5NzItMDgtMTkiLCJpYXQiOjE2MzcxOTE1OTIsImV4cCI6MTYzNzE5MTg5MiwianRpIjoiZDdhN2VjNmQtYzc4ZC00M2E1LWFiYTgtNWJkYTVlZTljMzM0In0.rO9kz9y9D2V_XIv_VQvutc_LllkRJ3BEn07rT3Ek6kBcypa9713we5-ABPMhizyaTxnv32kiL1msjOfz2TW0sJNJ5IyUVI4h2AMlyxQN2YZyOTKkchka7vS4wWQDaW8TmB8VdCi75VEOTaRQiV2h1ND6dcOQ6eoQ3KOTFiZdZ3Oae6M6WnPxwbozKdFXQvtiC5U-oueLcWhK1tD1DbPU-RTGuj7XTwyz9oFjoJiik33AN-5rFeErYIFMVmI3cNk-v6gjcmL6-jTAJixKPUFWBsbLQ76pHCLkTHJmtc3l4vThIOJvoheK5Ay_pa2IkQVN32Q55_Fm2xFPApH5NFVh-g'
```
For more information about 
Quarkus/Hibernate/Panache/RestEasy-Jackson/SmallRye 

See [SIMPLIFIED HIBERNATE ORM WITH PANACHE](https://quarkus.io/guides/hibernate-orm-panache) 