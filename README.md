# Nordvisa Calendar
## About
This is the repository of a project done at Linnaeus University for the course 2DV603. This was done in collaboration with NordVisa.

## Run in development environemnt
The application is currently configured to run on localhost:8080. This makes it posible to run localy on your machine without doing any changes to the code. The easiest way is to use the vagrant machine to run the application. To install Vagrant see [Installing Vagrant](https://www.vagrantup.com/docs/installation/. After it's installed just run `vagrant up` and when that is done run `vagrant ssh`

Next follow these steps.
1. Move into the client directory and run `npm install`
1. Move back into to root directory and run the build script `./buildToServer.sh`
1. Move into the server/ folder and run gradle bootRun
1. When spring has launched the application should be available at localhost:8080

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

## How to use / Features

### Register
![Screenshot](./documentation/howToUse/register.gif)

## License
### MIT
Copyright 2017 NordVisa, Axel Nilsson, Johan Gudmundsson, Leif Karlsson, Francis Menkes, Feiyu Xiong

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
