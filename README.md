# HedgeFlows Desktop App

A modern Finance Dashboard built with JavaFX 17, following strict MVC architecture and Component-Based Design.

## Description

HedgeFlows is a comprehensive desktop application designed for financial professionals to manage foreign exchange trades, payments, and generate reports. Built with JavaFX 17, it demonstrates modern UI/UX principles with a clean, intuitive interface inspired by Tailwind CSS design patterns.

## Key Features

- **Authentication:** Mock login/registration with JSON persistence
- **Dashboard:** Interactive charts and key metrics visualization
- **FX Trades:** CRUD operations with search, filtering, and "New Trade" dialogs
- **Payments:** Tabbed view for "Outstanding" vs "Paid" bills
- **Reports:** Financial report listing with download simulation
- **Settings:** User profile management and application preferences
- **Architecture:** Demonstrates Generics (`JsonRepository<T>`), Polymorphism, and Service-Repository pattern

## Tech Stack

- **Language:** Java 17
- **Framework:** JavaFX 17
- **Build Tool:** Apache Maven
- **Data Persistence:** Gson (JSON)
- **Styling:** CSS (Tailwind-inspired design system)
- **UI Components:** Ikonli FontAwesome icons, Lombok for boilerplate reduction

## Project Architecture

The application follows a strict Model-View-Controller (MVC) architecture with additional layers for better separation of concerns:

```
com.hedgeflows.desktop/
├── controller/     # UI logic and event handlers
├── model/          # Data classes and entities
├── repository/     # Data persistence layer
├── service/        # Business logic layer
├── util/           # Utility classes and constants
└── view/           # FXML layout files
```

### Design Patterns Implemented

1. **Generic Repository Pattern:** `JsonRepository<T>` provides reusable CRUD operations for any entity
2. **Service Layer:** Business logic separated from UI concerns
3. **Dependency Injection:** Controllers manage their dependencies explicitly
4. **Component-Based UI:** Reusable CSS classes for consistent styling

## Setup Instructions

### Prerequisites

- JDK 17 or higher
- Apache Maven 3.8+ installed and configured in PATH

### Running the Application

```bash
mvn clean javafx:run
```

### Login Credentials

- **Email:** admin@hedgeflows.com
- **Password:** admin123

These credentials are seeded automatically on first run.

## Development Notes

### Data Persistence

All data is persisted as JSON files in the `data/` directory:
- `users.json` - User accounts
- `trades.json` - FX trades
- `payments.json` - Payment records

### CSS Design System

The application uses a custom CSS design system inspired by Tailwind CSS with:
- Consistent color palette
- Responsive spacing system
- Reusable component classes (.btn-primary, .card, etc.)

## Future Enhancements

Potential improvements for future versions:
- Dark mode implementation
- Real database integration
- Export functionality for reports
- Advanced filtering and sorting
- Multi-user support with roles and permissions


