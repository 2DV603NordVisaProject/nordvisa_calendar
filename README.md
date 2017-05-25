# nordvisa_calendar
## About
This is the repository of a project done at Linnaeus University for the course 2DV603. This was done in collaboration with NordVisa.

## Packaging
To install this software it has to be built from source for everything to work. This is because
both server and clients need to be configured to fit the URL of the website which the application
will run on. By default the application is configured to run on localhost:8080. But it's very easy
to change this.

You will need some tools to do this. But all of these are included in the Vagrant configuration used for this project. See [Installing Vagrant](https://www.vagrantup.com/docs/installation/) to install Vagrant on you computer.

When the installation is complete you can create the virtual machine by running `vagrant up`, and then SSH into the machine by running `vagrant ssh`. From where you are able to follow the build process step by step.

1. Open the file client/src/Client.js
1. At the top there is a line which looks like this: `const url = "http://localhost:8080"`
1. Change that like to fit you URL. We will use (example.com) `const url = "http://example.com"`
1. Next we need to configure the server.
1. Open the file server/src/main/resources/application.properties
1. Change the line `base_url=localhost:8080` to fit your URL `base_url=example.com`
1. Change the line `server.port=8080` to whatever port you want to use. Generally you want port 80 `server.port=80`
1. (optional) If you want to use recaptcha to validate a user is a human then add the secret to recaptcha.server

Now the application is ready to be packaged. Just follow these instructions.

1. Navigate into the client folder.
1. Run `npm install`
1. When that is done navigate back to root of the project directory.
1. Run `./buildToServer`
1. Then navigate to the server folder
1. Run `gradle build`
1. Now the application is packaged and you will find the .jar file in /server/build/libs/nordvisa_calendar-0.1.0.jar

## Run the application
To run the application you will need some software installed.
* Java 8
* MongoDB

When you have created your .jar file you can run it using the following command `java -jar nordvisa_calendar-0.1.0.jar`.
