# Coin Calculator

### Building the Docker Image
To build the Docker image for the CoinCalculator backend, follow these steps:

1. Open a terminal and navigate to the directory containing the Dockerfile.
2. Run the following command to build the Docker image: docker build -t coincalculator-backend .  
    This command will create a Docker image named coincalculator-backend based on the instructions in the Dockerfile.

### Running the Docker Container
After building the Docker image, you can run the Docker container using the following steps:

1. In the terminal, run the following command to start the container: docker run -d -p 8080:8080 coincalculator-backend
2. To check that your application is running enter url `http://localhost:8080`

