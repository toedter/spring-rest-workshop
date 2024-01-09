docker run -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -e KEYCLOAK_IMPORT=/movies-realm.json \
    -v $PWD/movies-realm.json:/movies-realm.json -p 9000:8080 jboss/keycloak