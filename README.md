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

Feel free to open an issue if you encounter any problems or have suggestions for improvement.

---
## License

This project is licensed under the terms of the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0). You are free to use, modify, and distribute this project as per the terms of the license.

