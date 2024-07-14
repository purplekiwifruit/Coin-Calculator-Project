# Coin-Web Frontend

### Prerequisite

1. Create `.env.development`, this is for running the project for development

```
echo VITE_API_URL=http://localhost:8080 >> .env.development
```

2. Create `.env.production`, this is for building the project for production, which is an **essential step for building
   the docker image**

```
echo VITE_API_URL={PRODUCTION_URL} >> .env.production
```

### Building the Docker Image

To build the Docker image for the Coin Calculator frontend, follow these steps:

1. Open a terminal and navigate to the directory, `${PROJECT_DIR}/SA58_CaoLeilei_Fibonacci/coin-web`
2. Run the following command to build the Docker image:

```
docker build -t coincalculator-frontend .
```

### Running the Docker Container

After building the Docker image, you can run the Docker container using the following steps:

1. In the terminal, run the following command to start the container:

```
docker run -d -p 80:80 coincalculator-frontend
```

2. Once the container is running, you can access the Coin Calculator frontend by navigating to
   http://localhost in your web browser.
