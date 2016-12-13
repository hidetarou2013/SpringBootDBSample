# SpringBootDBSample

Spring Boot DB サンプル

IDE:pleiades-e4.5
java-version:1.8
javac-version:1.8
spring-boot-starter-parent:1.3.5.RELEASE

## DataBase

+ HSQLDB
+ MongoDB
+ Cassandra

## Application URL

MongoDB
http://localhost:1598/MemoBox

Cassandra
http://localhost:1598/greeting

## ref1:Spring Boot Cassandra Sample

refs #2 Spring Boot Cassandra Sample
https://dzone.com/articles/set-up-a-springdata-project-with-apache-cassandra

### Cassandra database:

database_creation.cql

```
CREATE KEYSPACE IF NOT EXISTS example WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;
CREATE TABLE IF NOT EXISTS example.greetings (
    user text,
    id timeuuid,
    greet text,
    creation_date timestamp,
    PRIMARY KEY (user, id)
) WITH CLUSTERING ORDER BY (id DESC);

CREATE INDEX greet ON greetings ( greet );

INSERT INTO greetings (user, id,greet,creation_date) VALUES ('yamada_1',now(),'greet message1','2016-12-13');
INSERT INTO greetings (user, id,greet,creation_date) VALUES ('yamada_2',now(),'greet message2','2016-12-13');
```

### touble shoot

```
com.datastax.driver.core.exceptions.InvalidQueryException: unconfigured table schema_keyspaces
```


The Cassandra Java Driver Spring Data is using is 2.1.9

The Cassandra version you're using is probably 3.x. Since 3.x all the meta data has been moved to keyspace system_schema

The solution to your issue are:

downgrade your version to Cassandra 2.2.x or 2.1.x
wait for Spring Data Cassandra to upgrade the driver version to 3.0.0
if you don't want to wait, use Achilles 4.1.0 which is an object mapper that support Cassandra 3.x. Note: I'm the creator of Achilles
if you don't want to wait, use the driver-mapper module that comes with the Cassandra Java Driver

### docker run cassandra

```
$ docker run --name cassandra_2_1_16 -d -p 7000:7000 -p 7199:7199 -p 9042:9042 -p 9160:9160 cassandra:2.1.16
$ docker cp database_creation.cql cassandra_2_1_16:/tmp/
$ docker-enter cassandra_2_1_16

# cqlsh -f /tmp/database_creation.cql
# nodetool -h 127.0.0.1 flush

```



