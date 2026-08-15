# Appointment Scheduling System

A full-stack web application for managing doctor appointment scheduling with user authentication, appointment booking, and admin dashboard analytics.

## Features

- **User Authentication**
    - Registration with password validation
    - Secure JWT-based login
    - Role-based access control (USER, ADMIN)

- **Appointment Booking**
    - Browse available providers (doctors)
    - View available time slots
    - Book appointments with confirmation
    - View appointment history

- **Admin Dashboard**
    - Manage service providers (create, update, delete)
    - Create time slot availability
    - View analytics (total appointments, providers, users)
    - Monitor appointment status

- **Security**
    - JWT authentication with secure token storage
    - CORS configuration for cross-origin requests
    - Environment-based configuration for sensitive data
    - Password encryption with bcrypt
    - Role-based endpoint protection

## Tech Stack

### Frontend
- **React 18** - UI library
- **React Router** - Client-side routing
- **Axios** - HTTP client with JWT interceptors
- **Jest & React Testing Library** - Testing framework

### Backend
- **Spring Boot 3.x** - Java framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - ORM with Hibernate
- **PostgreSQL** - Database
- **JWT (jjwt)** - Token generation & validation
- **Maven** - Build tool

### DevOps
- **GitHub Actions** - CI/CD pipeline

## Prerequisites

### Required
- **Java 17+** - Backend runtime
- **Node.js 18+** - Frontend runtime
- **PostgreSQL 12+** - Database
- **Maven 3.8+** - Build tool
- **npm 8+** - Package manager

### Recommended
- **Git** - Version control
- **VS Code** or **IntelliJ IDEA** - IDE
- **Postman** - API testing

## Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd AppointmentScheduling
```

### 2. Configure Environment Variables

This application requires three environment variables. See [Environment Variables](#environment-variables) below for details on what to set and how to generate secure values.

```bash
export DB_USERNAME="<your_postgres_username>"
export DB_PASSWORD="<your_postgres_password>"
export JWT_SECRET="<your_jwt_signing_key>"
```

**Windows (PowerShell):**
```powershell
$env:DB_USERNAME="<your_postgres_username>"
$env:DB_PASSWORD="<your_postgres_password>"
$env:JWT_SECRET="<your_jwt_signing_key>"
```

### 3. Database Setup

```bash
createdb scheduler
```

### 4. Backend Setup

```bash
# Build the backend
mvn clean install

# Run tests
mvn test

# Start the Spring Boot application
mvn spring-boot:run
```

The backend will start at `http://localhost:8080`

### 5. Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Run tests
npm test

# Start the React development server
npm start
```

The frontend will start at `http://localhost:3000`

## Running the Application

### Development

**Terminal 1 - Backend:**
```bash
cd D:\Launchcode\AppointmentScheduling
mvn spring-boot:run
```

**Terminal 2 - Frontend:**
```bash
cd D:\Launchcode\AppointmentScheduling\frontend
npm start
```

### Production

```bash
# Backend
mvn clean package
java -jar target/Schedular-App-0.0.1-SNAPSHOT.jar

# Frontend
npm run build
npm run start
```

## API Endpoints

### Authentication
- `POST /auth/register` - Register new user
- `POST /auth/login` - Login and receive JWT token
- `GET /auth/scheduleprovider/all` - Get all providers

### Appointments
- `GET /appointment/all` - Get all appointments (filtered by user)
- `POST /appointment/book` - Book new appointment

### Time Slots
- `GET /timeslot/available/{providerId}` - Get available slots for provider
- `POST /timeslot/create` - Create time slots (admin only)

### Admin
- `GET /admin/providers` - Get all providers with stats
- `POST /admin/providers/create` - Create new provider
- `PUT /admin/providers/{id}` - Update provider
- `DELETE /admin/providers/{id}` - Delete provider
- `GET /admin/analytics` - Get system analytics

## Environment Variables

| Variable | Description | Notes |
|----------|-------------|-------|
| `DB_USERNAME` | PostgreSQL username | Use a dedicated, non-superuser account |
| `DB_PASSWORD` | PostgreSQL password | Generate with `openssl rand -base64 32` |
| `JWT_SECRET` | JWT signing key | At least 256 bits; generate with `openssl rand -base64 48` |
| `SPRING_DATASOURCE_URL` | Database connection string | Defaults to `jdbc:postgresql://localhost:5432/scheduler` |

Credentials should never be committed to version control or hardcoded in source. Load them via a local `.env` file (already excluded from git) for development, and via your platform's secret manager (AWS Secrets Manager, HashiCorp Vault, container orchestration secrets, etc.) in production.

Rotate `JWT_SECRET` and database credentials periodically, and immediately if either is ever exposed. Current JWT expiry is 10 days (configurable in `JwtUtil.java`).

## Testing

### Backend Tests
```bash
mvn test
```

Includes:
- Authentication controller tests
- Appointment service tests
- Integration tests with database

### Frontend Tests
```bash
cd frontend
npm test
```

Includes:
- Component rendering tests
- Form validation tests
- API integration tests

### Run CI/CD Pipeline Locally
```bash
# Trigger GitHub Actions workflow manually (requires gh CLI)
gh workflow run ci.yml --ref main
```

## CI/CD Pipeline

GitHub Actions workflow (`.github/workflows/ci.yml`):

1. **Backend Build & Test**
    - Compile with Maven
    - Run unit tests
    - Code quality checks

2. **Frontend Build & Test**
    - Install dependencies
    - Run Jest tests
    - Build React app

3. **Artifacts**
    - Upload test results
    - Archive build artifacts

Workflow runs automatically on:
- Push to `main` branch
- Pull requests to `main` branch

## Project Structure

```
AppointmentScheduling/
├── src/
│   ├── main/java/AppointmentScheduling/
│   │   └── Schedular_App/
│   │       ├── Controller/        # REST endpoints
│   │       ├── Service/           # Business logic
│   │       ├── Entity/            # JPA entities
│   │       ├── Repository/        # Data access
│   │       ├── Security/          # JWT & auth
│   │       └── Dto/               # Data transfer objects
│   ├── test/java/                 # Backend tests
│   └── resources/
│       └── application.properties # Configuration
├── frontend/
│   ├── src/
│   │   ├── pages/                 # React pages
│   │   ├── components/            # React components
│   │   ├── services/              # API services
│   │   ├── __tests__/             # Frontend tests
│   │   └── App.js                 # Main app component
│   ├── package.json
│   └── public/
├── .github/
│   └── workflows/
│       └── ci.yml                 # GitHub Actions CI/CD
├── .gitignore
├── pom.xml                        # Maven configuration
└── README.md
```

## Default User

The application includes seeded roles in `data.sql`:
- **USER** role for regular users
- **ADMIN** role for administrators

Create admin account:
```bash
# Use registration endpoint with desired admin credentials
# Then manually set admin role in database
UPDATE users_roles SET role_id = 2 WHERE user_id = <admin_user_id>;
```

## Security Notes

- Enable HTTPS/TLS in production and redirect HTTP to HTTPS.
- Restrict CORS to trusted frontend domains only; the current development setting (`http://localhost:3000`) must be updated in `application.properties` for production.
- Enforce a strong password policy (12+ characters, mixed case, numbers, special characters recommended).
- Use a dedicated PostgreSQL user with limited permissions, enable SSL for database connections, and maintain regular encrypted backups.
- Implement rate limiting and log security-relevant events.

## Troubleshooting

### "Role not found: USER"
```bash
# Ensure roles are seeded in database
SELECT * FROM "role";

# If empty, manually insert:
INSERT INTO "role" (name) VALUES ('USER');
INSERT INTO "role" (name) VALUES ('ADMIN');
```

### JWT Token Invalid After Restart
- Ensure `JWT_SECRET` environment variable is set consistently
- Tokens are signed with the configured secret key

### CORS Errors
- Verify frontend URL in `application.properties` CORS configuration
- Ensure Authorization header is exposed in CORS settings

### Database Connection Failed
- Verify PostgreSQL is running
- Check credentials in environment variables
- Ensure database `scheduler` exists

## Contributing

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Commit changes: `git commit -m "Add your feature"`
3. Push to branch: `git push origin feature/your-feature`
4. Open a pull request with description

## License

This project is part of the capstone project.

## Support

For issues, questions, or feature requests, please create an issue in the repository or contact the development team.

---

**Last Updated:** August 12, 2026
**Version:** 1.0.0