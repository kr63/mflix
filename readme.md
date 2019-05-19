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
