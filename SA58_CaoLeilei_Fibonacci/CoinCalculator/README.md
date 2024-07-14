# Coin Calculator

### Building the Docker Image
To build the Docker image for the CoinCalculator backend, follow these steps:

1. Open a terminal and navigate to the directory `${PROJECT_DIR}/SA58_CaoLeilei_Fibonacci/CoinCalculator`
2. Run the following command to build the Docker image:
```
docker build -t coincalculator-backend .
```
This command will create a Docker image named coincalculator-backend based on the instructions in the Dockerfile.

### Running the Docker Container
After building the Docker image, run the Docker container using the following steps:

1. In the terminal, run the following command to start the container:
```
docker run -d -p 8080:8080 coincalculator-backend
```

2. To check the application is running, make a HTTP request via the following command
```
curl --location 'http://localhost:8080/coin' \
--header 'Content-Type: application/json' \
--data '{
    "targetAmount": 7.03,
    "coinDenominators": [0.01, 0.5, 1, 5, 10]
}'
```
It should return `[0.01, 0.01, 0.01, 1.0, 1.0, 5.0]`
