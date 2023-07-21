- Spring Boot, Maven
- The application exposes REST endpoint /routing/{origin}/{destination} that
returns a list of border crossings to get from origin to destination
- Single route is returned if the journey is possible
- If there is no land crossing, the endpoint returns HTTP 400

- HTTP request sample (land route from Czech Republic to Italy):
GET /routing/CZE/ITA HTTP/1.0 :
{
"route": ["CZE", "AUT", "ITA"]
}

How to run: 
execute: mvn spring-boot:run
