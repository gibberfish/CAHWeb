
net stop "%TOMCAT_SERVICE%"

del "%APACHE_HOME%\webapps\cahweb.war"
rmdir /S /q "%APACHE_HOME%\webapps\cahweb"

rmdir /S /q "%APACHE_HOME%\logs"
del "C:\Mark\*.log"

copy target\cahweb.war "%APACHE_HOME%\webapps\"

net start "%TOMCAT_SERVICE%"
