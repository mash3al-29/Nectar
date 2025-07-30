# Nectar - Online Grocery Store Android App

## 📱 Application Overview

**Nectar** is a modern Android grocery ecommerce application that allows users to browse products, search for items, add them to cart, and manage their shopping experience. The app provides an intuitive interface for online grocery shopping with features like product categorization, exclusive offers, best-selling items, and a comprehensive cart management system.

## 🏗️ Architecture Overview

### Clean Architecture Implementation

The application follows **Clean Architecture** principles with clear separation of concerns across three main layers:

```
┌─────────────────────────────────────────┐
│                UI Layer                 │  ← Jetpack Compose + MVVM
├─────────────────────────────────────────┤
│              Domain Layer               │  ← Business Logic + Use Cases
├─────────────────────────────────────────┤
│               Data Layer                │  ← Room Database + Repository
└─────────────────────────────────────────┘
```

Why Clean Architecture?
- Testability: Each layer can be tested independently
- Maintainability**: Changes in one layer don't affect others
- Scalability: Easy to add new features without breaking existing code
- Dependency Inversion: High level modules don't depend on low level modules

### MVVM Pattern with Jetpack Compose

The UI layer implements Model View ViewModel (MVVM) pattern:
- Model: Data classes and business logic
- View: Jetpack Compose UI components
- ViewModel: Manages UI state and handles business logic calls

**MVVM + MVI Hybrid Approach:**

We've also incorporated **MVI (Model-View-Intent)** structure concepts within our MVVM architecture to get the best of both worlds while avoiding MVI boilerplate (Found many codelabs and examples online following this format):

**MVI Elements We Adopted:**
- **Single State Object**: Each screen has one comprehensive state data class (e.g., `MainScreenState`)
- **Intent/Event System**: User actions are modeled as sealed class events (e.g., `MainScreenEvent`)
- **Unidirectional Data Flow**: Events flow down → State flows up → UI recomposes

**MVVM Foundation We Kept:**
- **ViewModel Structure**: Using Android's ViewModel as our base instead of pure MVI stores
- **Lifecycle Integration**: Automatic state preservation and restoration
- **Hilt Integration**: Easy dependency injection with `@HiltViewModel`

**Implementation Example:**
```kotlin
// MVI-style state management
data class MainScreenState(
    val exclusiveOffers: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)

// MVI-style events
sealed class MainScreenEvent {
    data class OnSearchQueryChange(val query: String) : MainScreenEvent()
    data class OnAddToCartClicked(val product: Product) : MainScreenEvent()
}

// MVVM-style ViewModel handling MVI concepts
@HiltViewModel
class MainScreenViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()
    
    fun onEvent(event: MainScreenEvent) { /* Handle events */ }
}
```

**Why This Hybrid Approach?**
- **Reduced Boilerplate**: No need for MVI stores, reducers, or middleware
- **Predictable State**: Single source of truth with clear state mutations
- **Easy Testing**: Simple event driven testing without complex MVI setup
- **Familiar Structure**: Developers can leverage existing MVVM knowledge
- **Compose Integration**: Perfect fit with Compose's state driven UI

## 🎯 Key Decisions

### 1. Dependency Injection with Dagger Hilt

**Decision**: Use Dagger Hilt for dependency injection across the entire application.

**Implementation**:
- `@HiltAndroidApp` in `EcommerceApplication`
- Modular DI setup with separate modules for Database, Repository and UseCase layers
- `@HiltViewModel` for ViewModels with automatic injection

**Why Hilt?**
- **Compile time Safety**: Dependency graph validated at compile time
- **Reduced Boilerplate**: Less manual dependency wiring
- **Android Integration**: Built specifically for Android lifecycle
- **Testability**: Easy to replace dependencies in tests

### 2. State Management

**Decision**: Use `StateFlow` and `MutableStateFlow` for state management with Compose integration.

**Implementation**:
```kotlin
private val _state = MutableStateFlow(MainScreenState())
val state: StateFlow<MainScreenState> = _state.asStateFlow()
```

**Why StateFlow?**
- **Lifecycle Awareness**: Automatically handles lifecycle states
- **Compose Integration**: Perfect integration with `collectAsStateWithLifecycle()`
- **Thread Safety**: Safe for concurrent access
- **Hot Stream**: Always emits current state to new collectors

### 3. Use Case Pattern

**Decision**: Implement dedicated Use Cases for each business operation.

**Examples**:
- `GetAllProductsUseCase`
- `SearchProductsUseCase`
- `AddToCartUseCase`
- `FilterProductsUseCase`

**Why Use Cases?**
- **Single Responsibility**: Each use case handles one specific business operation
- **Reusability**: Can be used across multiple ViewModels
- **Testing**: Easy to test business logic in isolation and switching the usage of mock repositories for testing
- **Clear Intent**: Makes business operations explicit and discoverable

### 4. Repository Pattern

**Decision**: Abstract data access through Repository interfaces with concrete implementations.

**Implementation**:
- `ProductRepository` interface in domain layer
- `ProductRepositoryImpl` in data layer
- Dependency injection binds interface to implementation

**Why Repository Pattern?**
- **Abstraction**: Domain layer doesn't know about data source details
- **Flexibility**: Easy to switch between different data sources
- **Testing**: Can easily mock repositories for testing
- **Caching Strategy**: Centralized place for data caching logic

## 💾 Data Layer Decisions

### Room Database

**Decision**: Use Room as the local database solution with entity-to-domain mapping.

**Structure**:
- `ProductEntity` for database storage
- `Product` domain model for business logic
- Automatic mapping between entities and domain models

**Why Room?**
- **Type Safety**: Compile-time verification of SQL queries
- **SQLite Integration**: Built on top of SQLite with additional safety
- **Coroutines Support**: Native support for suspend functions
- **Migration Support**: Handles database schema changes

### Data Mapping Strategy

**Decision**: Separate data entities from domain models with explicit mapping.

**Implementation**:
```kotlin
// Entity has mapping functions
fun toDomainModel(): Product { ... }
companion object {
    fun fromDomainModel(product: Product): ProductEntity { ... }
}
```

**Why Separate Models?**
- **Layer Independence**: Database changes don't affect domain logic
- **Domain Purity**: Business models aren't tied to storage format
- **Flexibility**: Different representations for different purposes

## 🎨 UI Layer Decisions

### Component Organization

**Decision**: Organize UI components in a modular, reusable structure.

**Structure**:
```
ui/
├── Common/                     # Reusable components
│   ├── GeneralComponents.kt    # Search bars, buttons, etc.
│   ├── ProductComponents.kt    # Product-related UI components
│   ├── CategoriesComponents.kt # Category-related components
│   ├── StateComponents.kt      # Loading, error, empty states
│   └── ProductGridComponents.kt# Grid layouts for products
├── [Screen]/                   # Feature-specific screens
│   ├── [Screen].kt            # Composable screen
│   ├── [Screen]State.kt       # State data class
│   └── [Screen]ViewModel.kt   # ViewModel for business logic
└── theme/                      # Design system
    ├── Color.kt               # Color palette
    ├── Typography.kt          # Text styles
    ├── Dimensions.kt          # Spacing and sizes
    └── Shape.kt               # Component shapes
```

**Why This Organization?**
- **Reusability**: Common components can be used across screens
- **Consistency**: Centralized design system ensures visual consistency
- **Maintainability**: Easy to find and modify specific UI elements
- **Scalability**: Easy to add new screens following the same pattern

### Theme System

**Decision**: Implement a custom theme system with centralized colors, typography, and dimensions.

**Implementation**:
- `AppColors` class with semantic color naming
- `AppTextStyles` for consistent typography
- `AppDimensions` for spacing and sizing
- Extension properties on `MaterialTheme` for easy access

**Why Custom Theme System?**
- **Design Consistency**: Ensures all screens follow the same visual guidelines
- **Easy Maintenance**: Colors and styles defined in one place
- **Semantic Naming**: Colors named by purpose, not appearance
- **Dark Mode Ready**: Easy to implement theme switching

### Navigation

**Decision**: Use Jetpack Navigation Compose with centralized navigation logic.

**Features**:
- Screen-based navigation with type-safe arguments
- Bottom navigation integration
- Proper back stack management
- Animation support between screens

## 📁 Project Structure

```
Nectar/
├── app/
│   ├── build.gradle.kts              # App-level build configuration
│   ├── proguard-rules.pro           # Code obfuscation rules
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml   # App configuration
│       │   ├── java/com/mashaal/ecommerce_app/
│       │   │   ├── EcommerceApplication.kt  # Application class with Hilt
│       │   │   ├── MainActivity.kt          # Single activity host
│       │   │   │
│       │   │   ├── data/                    # 📊 DATA LAYER
│       │   │   │   ├── Dao/                 # Database access objects
│       │   │   │   │   ├── CartDao.kt       # Cart operations
│       │   │   │   │   └── ProductDao.kt    # Product database queries
│       │   │   │   ├── Database/
│       │   │   │   │   └── ProductDatabase.kt # Room database configuration
│       │   │   │   ├── Entity/              # Database entities
│       │   │   │   │   ├── CartEntity.kt    # Cart table structure
│       │   │   │   │   └── ProductEntity.kt # Product table structure
│       │   │   │   ├── repository/          # Repository implementations
│       │   │   │   │   ├── CartRepositoryImpl.kt
│       │   │   │   │   └── ProductRepositoryImpl.kt
│       │   │   │   └── util/
│       │   │   │       └── MapConverter.kt  # Room type converters
│       │   │   │
│       │   │   ├── di/                      # 🔧 DEPENDENCY INJECTION
│       │   │   │   ├── DatabaseModule.kt    # Database dependencies
│       │   │   │   ├── RepositoryModule.kt  # Repository bindings
│       │   │   │   └── UseCaseModule.kt     # Use case providers
│       │   │   │
│       │   │   ├── domain/                  # 🏛️ DOMAIN LAYER
│       │   │   │   ├── extensions/          # Domain model extensions
│       │   │   │   │   └── ProductExtensions.kt # Product utility functions
│       │   │   │   ├── model/               # Business models
│       │   │   │   │   ├── Cart.kt         # Cart domain model
│       │   │   │   │   └── Product.kt      # Product domain model
│       │   │   │   ├── repository/          # Repository interfaces
│       │   │   │   │   ├── CartRepository.kt
│       │   │   │   │   └── ProductRepository.kt
│       │   │   │   └── usecase/             # Business use cases
│       │   │   │       ├── CartUseCase.kt
│       │   │   │       ├── FilterProductsUseCase.kt
│       │   │   │       ├── productUseCase.kt
│       │   │   │       └── SearchProductsUseCase.kt
│       │   │   │
│       │   │   └── ui/                      # 🎨 UI LAYER
│       │   │       ├── AcceptedOrderScreen/ # Order confirmation
│       │   │       ├── CategoriesScreen/    # Browse product categories
│       │   │       ├── CategorySelectedScreen/ # Products in category
│       │   │       ├── Common/              # Reusable UI components
│       │   │       │   ├── CategoriesComponents.kt
│       │   │       │   ├── GeneralComponents.kt
│       │   │       │   ├── ProductComponents.kt
│       │   │       │   ├── ProductGridComponents.kt
│       │   │       │   ├── SearchResultsContent.kt
│       │   │       │   └── StateComponents.kt
│       │   │       ├── FilterScreen/        # Product filtering
│       │   │       ├── MainScreen/          # Home screen with products
│       │   │       ├── MyCartScreen/        # Shopping cart management
│       │   │       ├── Navigation/          # Navigation setup
│       │   │       │   ├── AppNavigation.kt
│       │   │       │   └── BottomBarController.kt
│       │   │       ├── OnStartScreen/       # Welcome screen
│       │   │       ├── ProductScreen/       # Product details
│       │   │       ├── SeeAllScreen/        # View all products
│       │   │       ├── SplashScreen/        # App launch screen
│       │   │       └── theme/               # Design system
│       │   │           ├── Color.kt         # App color palette
│       │   │           ├── Dimensions.kt    # Spacing and sizes
│       │   │           ├── Icons.kt         # Icon definitions
│       │   │           ├── Shape.kt         # Component shapes
│       │   │           ├── ThemeComponents.kt # Theme extensions
│       │   │           ├── Type.kt          # Typography
│       │   │           └── Typography.kt    # Text styles
│       │   │
│       │   └── res/                         # 📱 ANDROID RESOURCES
│       │       ├── drawable/                # Vector graphics and images
│       │       ├── font/                    # Custom fonts (Gilroy)
│       │       ├── mipmap-*/               # App icons
│       │       ├── values/                 # Strings, colors, themes
│       │       └── xml/                    # Configuration files
│       │
│       ├── androidTest/                     # 🧪 INSTRUMENTATION TESTS
│       └── test/                           # 🧪 UNIT TESTS
│
├── build.gradle.kts                        # Project build configuration
├── gradle/                                 # Gradle wrapper and dependencies
├── gradle.properties                       # Gradle properties
├── gradlew                                # Gradle wrapper script (Unix)
├── gradlew.bat                            # Gradle wrapper script (Windows)
└── settings.gradle.kts                    # Project settings
```

## 🎯 Key Features

- **Product Browsing**: Browse products by categories
- **Search Functionality**: Real-time product search
- **Shopping Cart**: Add, remove, and manage cart items
- **Product Details**: Detailed product information and reviews
- **Filtering**: Filter products by various criteria
- **Modern UI**: Material Design 3 with custom theming
- **Offline Support**: Local database with Room
- **Responsive Design**: Adapts to different screen sizes

## 🚀 Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync project with Gradle files
4. Run the app on an emulator or device

## 🗃️ Database Setup

The app automatically creates and populates a local SQLite database with sample product data on first launch. The database includes:

- **18 sample products** across 6 categories
- **Nutritional information** for each product
- **Product images, descriptions, and ratings**
- **Pre-configured cart functionality**

### Database Features

- **Auto-seeding**: Database is automatically populated on first install
- **Persistent data**: Data survives app reinstalls and updates
- **Demo-ready**: Perfect for demonstrations and testing
- **Real-time sync**: Changes are immediately reflected in the UI

### Database Structure

```sql
-- Products table with sample data including:
-- Fresh Fruits & Vegetable (Apple, Carrot, Cucumber)
-- Cooking Oil & Ghee (Sunflower Oil, Pure Ghee, Canola Oil)  
-- Meat & Fish (Chicken Breast, Salmon, Beef Steak)
-- Bakery & Snacks (Wheat Bread, Croissant, Potato Chips)
-- Dairy & Eggs (Fresh Milk, Organic Eggs, Cheddar Cheese)
-- Beverages (Orange Juice, Green Tea, Cola Drink)

CREATE TABLE products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    detail TEXT NOT NULL,
    imageUrl TEXT NOT NULL,
    price REAL NOT NULL,
    description TEXT NOT NULL,
    category TEXT NOT NULL,
    nutrition TEXT NOT NULL, -- JSON formatted nutrition data
    review INTEGER NOT NULL
);
```

The database is automatically created and seeded through the `DatabaseSeeder` class, ensuring consistent demo data across all installations.

---