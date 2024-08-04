# 3GPP Bulk Configuration Management API
This microservice is designed for parsing, storing, and retrieving telecom 3GPP Bulk CM files as defined by [ETSI TS 132 612](https://www.etsi.org/deliver/etsi_ts/132600_132699/132612/18.00.00_60/ts_132612v180000p.pdf).

## Installation
1. Clone the repository
```bash
git clone https://github.com/zeyadmohaymen/3gpp-bulk-conf-manager.git
```

2. Ensure Docker is installed on your machine

3. Create a `.env` file in the root directory of the project and add the following environment variables:
```
MONGODB_USER=<username>
MONGODB_PASSWORD=<password>
MONGODB_DATABASE=<database>
MONGODB_LOCAL_PORT=27017
MONGODB_DOCKER_PORT=27017

SPRING_LOCAL_PORT=8080
SPRING_DOCKER_PORT=8080
```

4. Run the docker compose file
```bash
docker compose up
```

## Usage
The API provides the following endpoints. API documentation can be found at http://localhost:8080/swagger-ui.html after running the application.

### POST /api/parse
This endpoint is used to parse a 3GPP Bulk CM file and store it in the database.

```bash
curl -X POST "http://localhost:8080/api/parse" -F "file=@/path/to/file"
```

### GET /api/configs/all
This endpoint is used to retrieve all the parsed configurations.

```bash
curl "http://localhost:8080/api/configs/all"
```

### GET /api/configs/{id}
This endpoint is used to retrieve a specific configuration by its ID.

```bash
curl "http://localhost:8080/api/configs/{id}"
```
