FROM adoptopenjdk/openjdk11:latest AS builder

ARG DEBIAN_FRONTEND=noninteractive
ARG SOURCE_CODE_URL=https://gitlab.com/pSharpX/productsmanagement.git
ARG MAIN_DIR=tekton
ARG WORKPLACE_PATH=/opt/source

WORKDIR ${WORKPLACE_PATH}

RUN apt update && \
    apt install -y git && \
    git clone ${SOURCE_CODE_URL} ${MAIN_DIR}

WORKDIR ${WORKPLACE_PATH}/${MAIN_DIR}
RUN chmod +x mvnw && \
    ./mvnw clean package -DskipTests

FROM adoptopenjdk:11-jre-hotspot

ARG MAIN_DIR=tekton
ARG WORKPLACE_PATH=/opt/source

LABEL com.tekton.project.name="ProductsManagement" \
    com.tekton.project.key="productsmanagement" \
    com.tekton.project.version="1.0" \
    com.tekton.project.description="ProductManagement Application" \
    com.tekton.author.name="Christian Rivera" \
    com.tekton.author.email="crivera2093@gmail.com" \
    com.tekton.author.url="https://gitlab.com/pSharpX"

WORKDIR ${WORKPLACE_PATH}/${MAIN_DIR}

COPY --from=builder ${WORKPLACE_PATH}/${MAIN_DIR}/target/*.jar productsmanagement.jar

ENTRYPOINT ["java", "-jar", "productsmanagement.jar"]