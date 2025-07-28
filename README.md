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

The app includes the `EcommerceApplication.initializeDatabase()` method which is run at startup that makes the database appear through the app inspection tab in android studio to populate the database manually there.
```sql
INSERT INTO products (name, detail, imageUrl, price, description, category, nutrition, review) VALUES
-- Fresh Fruits & Vegetable
('Natural Red Apple', '1kg', 'https://img.freepik.com/premium-photo/red-apple-with-white-background-shadow-it_14117-4740.jpg', 4.99, 'Apples are nutritious. Apples may be good for weight loss. Apples may be good for your heart. As part of a healthful and varied diet.', 'Fresh Fruits & Vegetable', 'Energy=52 kcal;Protein=0.3 g;Carbohydrates=14 g;Fat=0.2 g;Fiber=2.4 g', 5),
('Carrot', '1kg', 'https://t3.ftcdn.net/jpg/02/99/43/48/360_F_299434842_UF1e0k44KUpkdtAEu0XbbPVnTHFuRwAm.jpg', 1.99, 'Carrots are a great source of beta carotene, fiber, vitamin K1, and antioxidants.', 'Fresh Fruits & Vegetable', 'Energy=41 kcal;Protein=0.9 g;Carbohydrates=10 g;Fat=0.2 g;Fiber=2.8 g', 4),
('Cucumber', '1kg', 'https://img.freepik.com/premium-photo/cucumber-isolated-white-background_319514-5406.jpg', 1.49, 'Cucumbers are low in calories and high in water and important vitamins and minerals.', 'Fresh Fruits & Vegetable', 'Energy=16 kcal;Protein=0.7 g;Carbohydrates=3.6 g;Fat=0.1 g;Fiber=0.5 g', 3),

-- Cooking Oil & Ghee
('Sunflower Oil', '1L', 'https://purepng.com/public/uploads/large/purepng.com-sunflower-oilsunflower-oilcooking-oilfrying-oilnon-volatile-oil-1411529833165ctzjx.png', 5.49, 'Sunflower oil is light, healthy, and ideal for cooking and frying.', 'Cooking Oil & Ghee', 'Energy=884 kcal;Protein=0 g;Carbohydrates=0 g;Fat=100 g;Fiber=0 g', 4),
('Pure Ghee', '500g', 'https://rosepng.com/wp-content/uploads/2025/01/desi-ghee-1.png', 6.99, 'Ghee is rich in fat-soluble vitamins and is used widely in traditional recipes.', 'Cooking Oil & Ghee', 'Energy=900 kcal;Protein=0 g;Carbohydrates=0 g;Fat=100 g;Fiber=0 g', 5),
('Canola Oil', '1L', 'https://www.pikpng.com/pngl/b/197-1975663_oil-sunflower-canola-cooking-seed-rapeseed-oils-clipart.png', 4.79, 'Canola oil is low in saturated fat and good for heart health.', 'Cooking Oil & Ghee', 'Energy=884 kcal;Protein=0 g;Carbohydrates=0 g;Fat=100 g;Fiber=0 g', 4),

-- Meat & Fish
('Fresh Chicken Breast', '500g', 'https://cdn.pixabay.com/photo/2014/03/05/01/20/chicken-breast-279849_1280.jpg', 7.99, 'Lean and protein-rich, ideal for healthy cooking.', 'Meat & Fish', 'Energy=165 kcal;Protein=31 g;Carbohydrates=0 g;Fat=3.6 g;Fiber=0 g', 5),
('Salmon Fillet', '300g', 'https://cdn.pixabay.com/photo/2021/05/25/11/43/fish-6282216_1280.jpg', 9.99, 'Salmon is rich in omega-3 fatty acids and excellent for heart and brain health.', 'Meat & Fish', 'Energy=208 kcal;Protein=20 g;Carbohydrates=0 g;Fat=13 g;Fiber=0 g', 5),
('Beef Steak', '1kg', 'https://cdn.pixabay.com/photo/2018/02/08/15/02/meat-3139641_1280.jpg', 12.49, 'High-protein and iron-rich red meat.', 'Meat & Fish', 'Energy=250 kcal;Protein=26 g;Carbohydrates=0 g;Fat=17 g;Fiber=0 g', 4),

-- Bakery & Snacks
('Whole Wheat Bread', '400g', 'https://static.vecteezy.com/system/resources/previews/002/463/399/large_2x/slice-whole-wheat-bread-isolated-on-white-background-free-photo.jpg', 2.19, 'Made with whole grains, good source of fiber.', 'Bakery & Snacks', 'Energy=247 kcal;Protein=13 g;Carbohydrates=41 g;Fat=4.2 g;Fiber=7 g', 4),
('Butter Croissant', '1pc', 'https://cdn.pixabay.com/photo/2012/02/29/12/17/bread-18987_1280.jpg', 1.49, 'Flaky and buttery pastry, perfect for breakfast.', 'Bakery & Snacks', 'Energy=406 kcal;Protein=8 g;Carbohydrates=45 g;Fat=21 g;Fiber=2.6 g', 5),
('Potato Chips', '150g', 'https://cdn.pixabay.com/photo/2022/12/05/17/51/potato-chips-7637285_1280.jpg', 1.99, 'Crispy, salted, and addictive snack.', 'Bakery & Snacks', 'Energy=536 kcal;Protein=7 g;Carbohydrates=53 g;Fat=34 g;Fiber=4.8 g', 4),

-- Dairy & Eggs
('Fresh Milk', '1L', 'https://img.freepik.com/premium-photo/glass-bottle-fresh-milk-isolated-white-background_252965-47.jpg', 2.29, 'Rich in calcium and vitamin D for bone health.', 'Dairy & Eggs', 'Energy=42 kcal;Protein=3.4 g;Carbohydrates=5 g;Fat=1 g;Fiber=0 g', 5),
('Organic Eggs', '12pcs', 'https://img.freepik.com/free-photo/three-fresh-organic-raw-eggs-isolated-white-surface_114579-43677.jpg', 3.49, 'High in protein and healthy fats.', 'Dairy & Eggs', 'Energy=155 kcal;Protein=13 g;Carbohydrates=1.1 g;Fat=11 g;Fiber=0 g', 5),
('Cheddar Cheese', '250g', 'https://img.freepik.com/premium-photo/cheddar-cheese-isolated-white-background_407474-20664.jpg', 4.59, 'Bold, tangy flavor great for sandwiches and cooking.', 'Dairy & Eggs', 'Energy=403 kcal;Protein=25 g;Carbohydrates=1.3 g;Fat=33 g;Fiber=0 g', 4),

-- Beverages
('Orange Juice', '1L', 'https://img.freepik.com/premium-photo/orange-juice-white-background_269353-1137.jpg', 3.79, 'Freshly squeezed and full of vitamin C.', 'Beverages', 'Energy=45 kcal;Protein=0.7 g;Carbohydrates=10.4 g;Fat=0.2 g;Fiber=0.2 g', 5),
('Green Tea', '25 bags', 'https://img.freepik.com/premium-photo/cup-green-tea-with-leaves-white-background_787273-2374.jpg', 2.49, 'Boosts metabolism and rich in antioxidants.', 'Beverages', 'Energy=1 kcal;Protein=0 g;Carbohydrates=0 g;Fat=0 g;Fiber=0 g', 4),
('Cola Drink', '500ml', 'https://img.freepik.com/premium-photo/drink-cola-with-ice-glass-white-background_55716-123.jpg', 1.19, 'Classic carbonated soft drink.', 'Beverages', 'Energy=42 kcal;Protein=0 g;Carbohydrates=11 g;Fat=0 g;Fiber=0 g', 4);
```

---