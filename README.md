# Read Me First
rate-limiter-demo application provides an example implementation of the rate limiter library. 

## How to enable Rate Limiting for an API?
Use ```@EnableRateLimit``` annotation on the API method. Also provide a
unique identifier for the API. The API name must match the API Name provided 
in the ```application.yaml```

Example - 
```dtd
@EnableRateLimit(api = "getDeveloperDetails")
    public Developer fetchDeveloperDetails(@PathVariable String developerId) {
        return developerService.fetchDeveloperDetails(developerId);
    }
```

## How to configure Rate limits for an API?
Add the following properties in the ```application.yaml```
```dtd
rate:
  limits:
    defaultMaxCallsAllowed: 20
    defaultTimeUnit: SECONDS
    throttleRateMap:
      getDeveloperDetails:
        - user: user1
          maxCallsAllowed: 3
          timeUnit: SECONDS
        - user: user2
          maxCallsAllowed: 5
          timeUnit: MINUTES
      getOrganizationDetails:
        - user: user1
          maxCallsAllowed: 3
          timeUnit: SECONDS
        - user: user4
          maxCallsAllowed: 2
          timeUnit: SECONDS
```

### Detailed Description of the fields
| Property               | Type     | Repeating? | Example                                                               |
|------------------------|----------|------------|-----------------------------------------------------------------------|
| defaultMaxCallsAllowed | Integer  | N          | 10                                                                    |
| defaultTimeUnit        | TimeUnit | N          | NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS,MINUTES, HOURS, DAYS |
| API Name               | String   | Y          | getDeveloperDetails, getOrganizationDetails                           |
| user                   | String   | Y          | user1, user2                                                          |
| maxCallsAllowed        | Integer  | Y          | 10                                                                    |
| timeUnit               | TimeUnit | Y          | NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS,MINUTES, HOURS, DAYS |

## How to invoke an API with rate limiting enabled?
Provide the user details in the request headers
```dtd
curl --location --request GET 'localhost:8080/api/v1/developers/12345' \
--header 'user: user2'
```

# Getting Started

## Requirements
* Java 11
* Maven 3.6.3

## How to build source using maven?
```mvn clean install```

## How to run the application using Maven?
```mvn spring-boot:run```

## How to run the application using JRE?
Build the source ```mvn clean install```<br/>
Execute archive ```java -jar target/rate-limiter-demo.jar```

## How to run the application using IDE?
Run ```RateLimiterDemoApplication.java```




