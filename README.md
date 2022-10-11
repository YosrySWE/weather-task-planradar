PlanRadar Android developer assessment
Task: Create a simple weather app
Speci cation
The user should be able to maintain a list of ci es where he can get the current weather
status as well as historical weather data. With historical weather data is meant all weathers
requests the user has made with the app. You can nd the API info in the 2nd page.
Create a table where the user can add/remove City names (We have tested with London,
Vienna, Paris -> all giving valid responses for API Endpoint 1, check the api at the second
page).
A er Click on an added City Name show a Detail page displaying the following informa on
from the response of API endpoint 1:
- weather.descrip on
- main.temp (response is kelvin -> convert to celsius)
- main.humidity (in percentage)
- wind.speed
The request must be asynchronous, during the request show some loading indicator.
Development guidelines
• UI/UX must be like the specs
• Clean architecture (MVVM is suggested)
• React-Programming (RxJava - Kotlin corou nes)API Info
Weather API: h ps://openweathermap.org
API Key: f5cb0b965ea1564c50c6f1b74534d823
Endpoints to be used:
1) GET: api.openweathermap.org/data/2.5/weather?q=<CITY_NAME>
2) GET: h p://openweathermap.org/img/w/<ICON_ID>.png
Assessment Grading
-
-
-
-
-
App Architecture & Codestyle/quality & Documenta on of Code
User Interface ( Pixel perfect design is a strong plus )
Error Handling
Interpreta on of speci ca on
Tests
For any Ques ons on the assessment: mobile@planradar.com
Delivery
Zip File containing the source.
