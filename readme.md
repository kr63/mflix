M220J: MongoDB for Java Developers
==================================

[Learn the essentials of Java application development with MongoDB](https://university.mongodb.com/courses/M220J/about])

[Course completion confirmation](https://university.mongodb.com/course_completion/daaff904-9f61-40d3-98ac-5e1058cc0672/printable)


#### How to restore database from backup:
1. Run docker container:
    ```
    docker-compose up
    ```
1. Connect to the docker container:
    ```
    docker ps
    docker exec -it <container name> /bin/bash
    ```
1. Restore data 
    ```
    mongorestore --drop --gzip ./mnt --uri "mongodb://root:example@localhost:27017"
    ```
