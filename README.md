# Library Management System

A RESTful API for managing books and borrowing transactions built with Spring Boot, Hibernate, and JPA.

## Features

- **Book Management**: Create, retrieve, and manage books with ISBN tracking
- **Borrowing System**: Track book borrowing and returns
- **Availability Status**: Real-time book availability tracking
- **Transaction History**: Complete borrowing transaction history
- **RESTful API**: Clean, intuitive API endpoints

## Technology Stack

- **Spring Boot 3.5.0**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database** (in-memory for development)
- **Java 21**

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory:
   ```bash
   cd Nkerabahizi-Love
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   or on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Database Access

- **H2 Console**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:librarydb`
- **Username**: `sa`
- **Password**: `password`

## API Endpoints

### Book Management

#### 1. Create a New Book
```http
POST /api/books
Content-Type: application/json

{
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "isbn": "978-0743273565",
    "availabilityStatus": "AVAILABLE"
}
```

**Response:**
```json
{
    "id": 1,
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "isbn": "978-0743273565",
    "availabilityStatus": "AVAILABLE"
}
```

#### 2. Get Book by ISBN
```http
GET /api/books/{isbn}
```

**Response:**
```json
{
    "id": 1,
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "isbn": "978-0743273565",
    "availabilityStatus": "AVAILABLE"
}
```

#### 3. Check Book Availability
```http
GET /api/books/{isbn}/availability
```

**Response:**
```
AVAILABLE
```
or
```
BORROWED
```

#### 4. Get All Books
```http
GET /api/books
```

#### 5. Get Available Books
```http
GET /api/books/available
```

### Borrowing Management

#### 1. Create Borrowing Transaction
```http
POST /api/borrowings
Content-Type: application/json

{
    "isbn": "978-0743273565",
    "borrowerName": "John Doe",
    "borrowDate": "2024-01-15T10:30:00"
}
```

**Response:**
```json
{
    "id": 1,
    "bookTitle": "The Great Gatsby",
    "bookIsbn": "978-0743273565",
    "borrowerName": "John Doe",
    "borrowDate": "2024-01-15T10:30:00",
    "returnDate": null,
    "status": "PENDING"
}
```

#### 2. Return a Book
```http
PUT /api/borrowings/{transactionId}/return
```

**Response:**
```json
{
    "id": 1,
    "bookTitle": "The Great Gatsby",
    "bookIsbn": "978-0743273565",
    "borrowerName": "John Doe",
    "borrowDate": "2024-01-15T10:30:00",
    "returnDate": "2024-01-20T14:45:00",
    "status": "RETURNED"
}
```

#### 3. Get All Borrowing Transactions
```http
GET /api/borrowings
```

#### 4. Get Borrowing Transaction by ID
```http
GET /api/borrowings/{transactionId}
```

#### 5. Get Transactions by Status
```http
GET /api/borrowings/status/{status}
```
Where `{status}` can be `PENDING` or `RETURNED`

## Business Rules

1. **Book Availability Check**: A book must be available before it can be borrowed
2. **ISBN Uniqueness**: Each book must have a unique ISBN
3. **Transaction Status**: Borrowing transactions start with `PENDING` status and change to `RETURNED` when the book is returned
4. **Automatic Status Updates**: Book availability is automatically updated when borrowed or returned

## Sample Data

The application comes pre-loaded with 5 sample books:
- The Great Gatsby (F. Scott Fitzgerald)
- To Kill a Mockingbird (Harper Lee)
- 1984 (George Orwell)
- Pride and Prejudice (Jane Austen)
- The Hobbit (J.R.R. Tolkien)

## Error Handling

The API returns appropriate HTTP status codes and error messages:

- **400 Bad Request**: Validation errors or business rule violations
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Unexpected server errors

## Testing the API

You can test the API using tools like:
- **Postman**
- **cURL**
- **Insomnia**
- **H2 Console** for database inspection

### Example cURL Commands

```bash
# Create a book
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Book","author":"Test Author","isbn":"1234567890","availabilityStatus":"AVAILABLE"}'

# Get book by ISBN
curl http://localhost:8080/api/books/1234567890

# Check availability
curl http://localhost:8080/api/books/1234567890/availability

# Create borrowing transaction
curl -X POST http://localhost:8080/api/borrowings \
  -H "Content-Type: application/json" \
  -d '{"isbn":"1234567890","borrowerName":"John Doe","borrowDate":"2024-01-15T10:30:00"}'

# Return a book
curl -X PUT http://localhost:8080/api/borrowings/1/return
```

## Project Structure

```
src/main/java/com/love/
├── controller/          # REST controllers
│   ├── BookController.java
│   └── BorrowingController.java
├── dto/                # Data Transfer Objects
│   ├── BookRequest.java
│   ├── BookResponse.java
│   ├── BorrowingRequest.java
│   └── BorrowingResponse.java
├── entity/             # JPA entities
│   ├── Book.java
│   └── BorrowingTransaction.java
├── enums/              # Enumerations
│   ├── BookAvailabilityStatus.java
│   └── BorrowingStatus.java
├── exception/          # Exception handling
│   └── GlobalExceptionHandler.java
├── repository/         # Data access layer
│   ├── BookRepository.java
│   └── BorrowingTransactionRepository.java
├── service/            # Business logic
│   ├── BookService.java
│   └── BorrowingService.java
└── config/             # Configuration
    └── DataLoader.java
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License. 
