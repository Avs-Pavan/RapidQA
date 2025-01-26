# RapidQA

RapidQA is an open-source Android SDK designed to streamline and enhance the QA process for API testing. With RapidQA, you can effortlessly trace, delay, and mock API calls, as well as export detailed request and response information for further analysis.

## Features

### Annotations
RapidQA offers three annotations to simplify API call management:
1. **`@Named`**: Assign a custom name to an API call for better identification.
2. **`@Delayed`**: Introduce a delay to API calls for testing scenarios with network latency.
3. **`@Mocked`**: Mock API responses using JSON files stored in the assets folder.

### RapidQaTracer
- **RapidQaTracer** seamlessly integrates with Retrofit to monitor and trace all API traffic.
- Saves traces and displays them in an intuitive **TracerUI**.
- View comprehensive details for every API call, including headers, body content, and timing.

### Export Functionality
- Export API trace data in two formats:
  - **Text**: Human-readable format for quick reviews.
  - **Postman**: Export to JSON format for importing into Postman.
---
### Screen shots:
<img src="https://github.com/Avs-Pavan/RapidQA/blob/main/5042980512983542505.JPG" style=" height:350px " />    <img src="https://github.com/Avs-Pavan/RapidQA/blob/main/5257672003933655050.JPG" style=" height:350px " />    <img src="https://github.com/Avs-Pavan/RapidQA/blob/main/8878824932387304160.JPG" style=" height:350px " />


## RapidQA Integration with Network Monitoring and Dependency Injection

This section describes how to integrate RapidQA, a mobile performance monitoring library, into your Android application for network call tracing, mocking, and logging. It utilizes Dagger Hilt for dependency injection and Retrofit for networking.

**Key Components:**

* **Dagger Hilt:** Provides dependency injection throughout the application.
* **Retrofit:** Handles network calls with interceptors for monitoring.
* **RapidQA:**
    * `RapidQADataStore`: Stores trace records.
    * `RapidQAMockInterceptor`: Mocks network responses (enabled globally by default).
    * `RapidQaTracer`: Traces network calls and stores them in `RapidQADataStore`.
    * `RapidQADelayInterceptor`: Simulates network delays (enabled by default).
    * `RapidQANameTagInterceptor`: Adds identifying tags to network requests.
* **HttpLoggingInterceptor:** Logs HTTP request and response details (body level).

**Implementation Steps:**

1. **Add Dependencies:**
   - Include Dagger Hilt, Retrofit, and RapidQA libraries in your project.

2. **Network Interceptors:**
   - Create a `HttpLoggingInterceptor` to log request/response details.
   - Create a `RapidQAMockInterceptor` for network response mocking (enabled by default).
   - Create a `RapidQADataStore` for storing trace records (in-memory implementation used here).
   - Create a `RapidQaTracer` to trace network calls and store them in `RapidQADataStore`.
   - Create a `RapidQADelayInterceptor` to simulate network delays (enabled by default).
   - Create a `RapidQANameTagInterceptor` to add tags to network requests.

3. **OkHttpClient Configuration:**
   - Build an `OkHttpClient` instance using the interceptor chain:
     - `HttpLoggingInterceptor`
     - `RapidQANameTagInterceptor`
     - `RapidQADelayInterceptor`
     - `RapidQaTracer`
     - `RapidQAMockInterceptor`

4. **Retrofit Configuration:**
   - Build a `Retrofit` instance with the configured `OkHttpClient` and desired base URL.

5. **Dependency Injection (Dagger Hilt):**
   - Annotate classes like `RapidQADataStore` and `OkHttpClient` with `@Singleton` and `@Provides` for dependency injection.

**Code Example:**

```kotlin
@Singleton
@Provides
fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }
}

@Singleton
@Provides
fun provideMockInterceptor(
    @ApplicationContext context: Context
): RapidQAMockInterceptor {
    return RapidQAMockInterceptor(context.assets, isGlobalMockingEnabled = { true }) 
}

@Singleton
@Provides
fun provideQADataStore(): RapidQADataStore<Long, RapidQATraceRecord> {
    return RapidQAInMemoryDataStore()
}

@Singleton
@Provides
fun provideQATracer(dataStore: RapidQADataStore<Long, RapidQATraceRecord>): RapidQaTracer {
    return RapidQaTracer(dataStore)
}

@Singleton
@Provides
fun provideDelayedInterceptor(): RapidQADelayInterceptor {
    return RapidQADelayInterceptor(isDelayEnabled = { true })
}

@Singleton
@Provides
fun provideNameTagInterceptor(): RapidQANameTagInterceptor {
    return RapidQANameTagInterceptor()
}

@Singleton
@Provides
fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    rapidQAMockInterceptor: RapidQAMockInterceptor,
    rapidQaTracer: RapidQaTracer,
    rapidQADelayInterceptor: RapidQADelayInterceptor,
    rapidQaNameTagInterceptor: RapidQANameTagInterceptor
): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(rapidQaNameTagInterceptor)
        .addInterceptor(rapidQADelayInterceptor)
        .addInterceptor(rapidQaTracer)
        .addInterceptor(rapidQAMockInterceptor)
    return builder.build()
}

@Provides
@Singleton
fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("[https://example.com/](https://example.com/)")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

```

Inject the created datastore

```kotlin
 @Inject
    lateinit var dataStore: RapidQADataStore<Long, RapidQATraceRecord>
```

Navigate to Rapid QA tracer activity

```kotlin
RapidQATracerActivity.getInstance(
                                        context = this@MainActivity,
                                        dataStore = dataStore
                                    )
```


Feel free to open an issue if you encounter any problems or have suggestions for improvement.

---
## License

This project is licensed under the terms of the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0). You are free to use, modify, and distribute this project as per the terms of the license.

