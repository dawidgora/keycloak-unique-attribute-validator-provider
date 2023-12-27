# unique-attribute-validator-provider
FROM maven:3.8.1-openjdk-17-slim AS unique-attribute-validator-provider

WORKDIR /app

COPY unique-attribute-validator-provider/pom.xml .
COPY unique-attribute-validator-provider/src ./src

RUN mvn clean package && \
    mkdir -p /result && \
    cp /app/target/unique-attribute-validator-provider-1.0-SNAPSHOT.jar /result/unique-attribute-validator-provider.jar

# keycloak stage
FROM quay.io/keycloak/keycloak:22.0.5

WORKDIR /opt/keycloak

COPY --from=unique-attribute-validator-provider /result/unique-attribute-validator-provider.jar /opt/keycloak/providers

RUN /opt/keycloak/bin/kc.sh build

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
