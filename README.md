# DropGoat

DropGoat is an intentionally vulnerable Java web application written using [DropWizard](https://www.dropwizard.io/)
framework that I use to teach application security and evaluate security scanners.

## Usage

You need a basic Java development environment with JDK and Maven. At this moment only JDK 8 will work.

Buld the application:

    mvn package

And run the output JAR package:

    java -jar target/DropGoat-1.0.jar server config.yml

Then head to [http://localhost:8888/app/assets/index.html](http://localhost:8888/app/assets/index.html) to
see its main page with links to specific exploits.