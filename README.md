# moviemanager
This application was generated using JHipster 4.6.2, you can find documentation and help at [https://jhipster.github.io/documentation-archive/v4.6.2](https://jhipster.github.io/documentation-archive/v4.6.2).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.
2. [Yarn][]: We use Yarn to manage Node dependencies.
   Depending on your system, you can install Yarn either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    yarn install

We use yarn scripts and [Webpack][] as our build system.


Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    yarn start

[Yarn][] is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `yarn update` and `yarn install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `yarn help update`.

The `yarn run` command will list all of the scripts available to run for this project.

### Service workers

Service workers are commented by default, to enable them please uncomment the following code.

* The service worker registering script in index.html
```
<script>
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker
        .register('./sw.js')
        .then(function() { console.log('Service Worker Registered'); });
    }
</script>
```
* The copy file option in webpack-common.js
```js
{ from: './src/main/webapp/sw.js', to: 'sw.js' },
```
Note: Add the respective scripts/assets in `sw.js` that is needed to be cached.

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

    yarn add --exact leaflet

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

    yarn add --dev --exact @types/leaflet

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:

Edit [src/main/webapp/app/vendor.ts](src/main/webapp/app/vendor.ts) file:
~~~
import 'leaflet/dist/leaflet.js';
~~~

Edit [src/main/webapp/content/css/vendor.css](src/main/webapp/content/css/vendor.css) file:
~~~
@import '~leaflet/dist/leaflet.css';
~~~

Note: there are still few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Using angular-cli

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

    ng generate component my-component

will generate few files:

    create src/main/webapp/app/my-component/my-component.component.html
    create src/main/webapp/app/my-component/my-component.component.ts
    update src/main/webapp/app/app.module.ts

## Building for production

To optimize the moviemanager application for production, run:

    ./mvnw -Pprod clean package

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.war

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./mvnw clean test

### Client tests

Unit tests are run by [Karma][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

    yarn test

UI end-to-end tests are powered by [Protractor][], which is built on top of WebDriverJS. They're located in [src/test/javascript/e2e](src/test/javascript/e2e)
and can be run by starting Spring Boot in one terminal (`./mvnw spring-boot:run`) and running the tests (`yarn run e2e`) in a second one.
### Other tests

Performance tests are run by [Gatling][] and written in Scala. They're located in [src/test/gatling](src/test/gatling) and can be run with:

    ./mvnw gatling:execute

For more information, refer to the [Running tests page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.
For example, to start a mysql database in a docker container, run:

    docker-compose -f src/main/docker/mysql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mysql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw package -Pprod docker:build

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[JHipster Homepage and latest documentation]: https://jhipster.github.io
[JHipster 4.6.2 archive]: https://jhipster.github.io/documentation-archive/v4.6.2

[Using JHipster in development]: https://jhipster.github.io/documentation-archive/v4.6.2/development/
[Using Docker and Docker-Compose]: https://jhipster.github.io/documentation-archive/v4.6.2/docker-compose
[Using JHipster in production]: https://jhipster.github.io/documentation-archive/v4.6.2/production/
[Running tests page]: https://jhipster.github.io/documentation-archive/v4.6.2/running-tests/
[Setting up Continuous Integration]: https://jhipster.github.io/documentation-archive/v4.6.2/setting-up-ci/

[Gatling]: http://gatling.io/
[Node.js]: https://nodejs.org/
[Yarn]: https://yarnpkg.org/
[Webpack]: https://webpack.github.io/
[Angular CLI]: https://cli.angular.io/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
[Leaflet]: http://leafletjs.com/
[DefinitelyTyped]: http://definitelytyped.org/


## CD with Fabric8

### 1. Jenkins credentials
Add credentials in Jenkins

### 2. Jenkins ssh config
In Fabric8 menu Secrets -> jenkins-ssh-config, add the following in the Config key:
```
Host github.com
User git
IdentityFile /root/.ssh-git/ssh-key
StrictHostKeyChecking no
```

---

## Running app from within container

This will add your current directory as a volume to the container, set the working directory to the volume, and exec 
you into the container

```
docker run --rm -v "$PWD":/usr/src/app -w /usr/src/app -it openjdk:8 /bin/bash
```

First run the container:

```
docker run --name jhipster -v "$PWD":/home/jhipster/app -v ~/.m2:/home/jhipster/.m2 -v "/var/run/docker.sock:/var/run/docker.sock" -p 8000:8080 -p 9000:9000 -p 3001:3001 -d -t jhipster/jhipster

```

Then open one terminal window and exec into it; and we will run backend in it:

```
docker exec -it jhipster bash

& then run

./mvnw
```

Then open another terminal window and exec into it; and we will run frontend in it:

```
docker container exec -it jhipster bash

& then run

yarn install
yarn start
```

Accessing the container

Warning: On Windows, you need to execute the Docker Quick Terminal as Administrator to be able to create symlinks during the `yarn install` step.

The easiest way to log into the running container is by executing following command:

```
docker container exec -it <container_name> bash
```

If you copy-pasted the above command to run the container, notice that you have to specify jhipster as the container name:

```
docker container exec -it jhipster bash
```

You will log in as the “jhipster” user.

If you want to log in as “root”, as the sudo command isn’t available in Ubuntu Xenial, you need to run:

```
docker container exec -it --user root jhipster bash
```

```
Tip: If you are having issues with Yarn, you can use jhipster --npm, to use NPM instead of Yarn.
```

Once your application is created, you can run all the normal gulp/bower/maven commands, for example:

```
./mvnw
```

Congratulations! You’ve launched your JHipster app inside Docker!

On your host machine, you should be able to :

* Access the running application at http://DOCKER_HOST:8080
* Get all the generated files inside your shared folder

---
