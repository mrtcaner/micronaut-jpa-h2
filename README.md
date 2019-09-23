# micronaut-jpa-h2

## build

    - mvn clean install
    
    Application runs on port 8080. dev profile can be used to skip docker build
    
    - mvn clean install -Pdev
    
    There may be a plugin error while building docker image  with ide's bundled maven. But it works fine with maven 3.5.3
    
    Current configuration pushes image to local docker registry so make sure you have one.
    
## run 
    
    mvn spring-boot:run
    
    for docker;
    
    docker run -p 8080:8080 micronaut-jpa-h2
