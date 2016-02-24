cd src\main\webapp\js
call browserify .\lobby\lobby.js -o lobby4browser.js
call browserify .\cah\cah.js -o cah4browser.js
cd ..\..\..\..
call mvn clean package
call java -jar target\cards-against-humanity-0.1.0.jar
