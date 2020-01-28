# Hello World REST Service Example

Simple example of calling a REST service.  Contains two programs:

* kub-server: Exposes a single REST service at /helloworld:8081

* kub-client: Consumes the REST data returned by kub-server and displays it to the user.

kub-client has three views.  They are identical except in the way the kub-server is called:

* local: Calls kub-server via http://localhost:8081/helloworld

* remote: Calls kub-server via http://kub-server:8081/helloworld

* config: Calls kub-server via http://<service.name>:8081/helloworld where <service.name> is determine by the service.name application property. 

## Building the Applications

The code can be obtained by cloning the following GitHub repository:

```
https://github.com/rgtodd/gcp-practice.git
```

Both kub-client and kub-server can be built using the following Maven commands:

```
cd ~/git/gcp-practice/kub-client
mvn clean package 

cd ~/git/gcp-practice/kub-server
mvn clean package
```

Note: the example above assumes ~/git is your local Git repository.

When complete, the following JAR files should exist:

* ~/git/gcp-practice/kub-client/target/kub-client-0.0.1-SNAPSHOT.jar
* ~/git/gcp-practice/kub-server/target/kub-server-0.0.1-SNAPSHOT.jar

## Running Locally

To run the program locally, open a terminal window and run the following program:

```
java -jar ~/git/gcp-practice/kub-client/target/kub-client-0.0.1-SNAPSHOT.jar
```

In a second window, run the following program:

```
java -jar ~/git/gcp-practice/kub-server/target/kub-server-0.0.1-SNAPSHOT.jar
```

In a web browser, open the following URL:

```
http://localhost:8081/helloworld
```

You should see output like the following:

```json
{"message":"Hello world!","dateTime":"2020-01-28T08:27:33.859","hostName":"centos","hostAddress":"10.0.2.15","exception":null}
```

This confirms the server program is successfully handling REST service calls.

Then, open the following URL:

```
http://localhost:8080/local
```

You should see output like the following:

<div style="background-color:lightyellow">
<hr>
	<h1>Hello Local</h1>
	<p>Calls a REST service on localhost using a hard-coded URL.</p><h2>Local</h2>
	<p>
		Message: <span>Hello world!</span>
	</p><p>
		Local Date/Time: <span>2020-01-28T08:41:03.848</span>
	</p><p>
		Host Name: <span>centos</span>
	</p><p>
		Host Address: <span>10.0.2.15</span>
	</p><p>
		Exception: <span></span>
	</p><h2>Remote</h2>
	<p>
		URL: <span>http://localhost:8081/helloworld</span>
	</p><p>
		Message: <span>Hello world!</span>
	</p><p>
		Local Date/Time: <span>2020-01-28T08:41:03.850</span>
	</p><p>
		Host Name: <span>centos</span>
	</p><p>
		Host Address: <span>10.0.2.15</span>
	</p><p>
		Exception: <span></span>
</p>
<hr>
</div>

This page indicates that kub-client has successfully called kub-server using the hard-coded link http://localhost:8081/helloworld.

Now, open the link:

```
http://localhost:8080/config
```

You should see a similar page.  However, the server name is now determined by the following entry in the application.properties resource file:

```
service.name = localhost
```

## Running in Docker

Run the following commands to build Docker images for the programs:

```
docker build -t kub-client ~/git/gcp-practice/kub-client/.
docker build -t kub-server ~/git/gcp-practice/kub-server/.
```

Create a user-defined network that will allow the two containers to communicate with each other:

```
docker network create kub-network
```

Create containers for each of the docker images:

```
docker create --name kub-client --network kub-network --publish 8080:8080 kub-client
docker create --name kub-server --network kub-network kub-server
```

These containers can then be started:

```
docker start kub-client
docker start kub-server
```

Open the following URL in a browser window:

```
http://localhost:8080/remote
```

The following page will be shown:

<div style="background-color:lightyellow">
<hr>
	<h1>Hello Server</h1>
	<p>Calls a REST service on kub-server using a hard-coded URL.</p><h2>Local</h2>
	<p>
		Message: <span>Hello world!</span>
	</p><p>
		Local Date/Time: <span>2020-01-28T15:57:44.517</span>
	</p><p>
		Host Name: <span>f10cb55556cf</span>
	</p><p>
		Host Address: <span>172.19.0.2</span>
	</p><p>
		Exception: <span></span>
	</p><h2>Remote</h2>
	<p>
		URL: <span>http://kub-server:8081/helloworld</span>
	</p><p>
		Message: <span>Hello world!</span>
	</p><p>
		Local Date/Time: <span>2020-01-28T15:57:44.674</span>
	</p><p>
		Host Name: <span>2e8e3d2fe048</span>
	</p><p>
		Host Address: <span>172.19.0.3</span>
	</p><p>
		Exception: <span></span>
</p>
<hr>
</div>

When complete, stop the docker containers:

```
docker stop kub-client
docker stop kub-server
```

## Running in Minikube

In order to consume the Docker images in a Kubernetes environment, they should be published to a image repository.

If you do not have an account on Docker Hub, go to https://hub.docker.com/ and create one. 
```

Then tag your images appropriately:

```
docker tag kub-client <userId>/kub-client
docker tag kub-server <userId>/kub-server

```

Replace &lt;userId&gt; with your Docker Hub user ID.

You can then push your images to the repository:

```
docker push <userId>/kub-client
docker push <userId>/kub-server
```

Start Minikube.  If running Minikube for the first time, use the profile option to specify the virtualization environment to be used.  If you have VirtualBox installed, specify:

```
minikube start --profile="virtualbox"
```

Otherwise, specify:

```
minikube start
```

Once Minikube has started, launch the dashboard as follows:

```
minikube dashboard
```

You can deploy your images by clicking the + icon on the top menu bar

![Minikube - Create kub-server deployment](minikube-kub-server.png)

