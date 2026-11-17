# Market Go

Market Go is an online supermarket Android app built with Kotlin and Jetpack Compose.
Users can browse products, search, explore categories, add items to a basket,
save favorites, place orders, and review their purchase history.

## Author
- Iles Artur 240103183

## Main Features
- Welcome screen with a Continue button
- Main screen: product grid (2 columns) with live search and a "Product not found" state
- Category screen: 9 categories in a 3-column grid; each opens a products sub-screen
- Basket: item counts, +/- buttons, delete, total price and total items
- Checkout sub-screen: final product list, "Final purchase" button, success state
- Favorites screen: only liked products
- Profile screen with Purchase history: every purchase is stored as a separate
  order with its products, counts, total price and date
- Bottom navigation bar with 5 tabs: Main, Category, Basket, Favorites, Profile

## Architecture (MVVM)
- `model/`: data classes (Product, Category, BasketItem, Order) and sample data
- `viewmodel/`: MarketViewModel holds all state as StateFlow
- `ui/screens/`: screens built with Jetpack Compose
- `ui/components/`: reusable composables (ProductCard, CategoryCard, OrderCard, ...)
- `ui/theme/`: Material 3 theme (colors, typography)
- `navigation/`: routes and NavHost (Navigation Compose)

## Navigation
- Navigation Compose with a NavHost and a bottom bar visible only on the 5 main tabs
- Argument passing: `category_products/{categoryId}`
- Back navigation: back arrows on Category products, Checkout and Purchase history

## Tech Stack
- Kotlin, Jetpack Compose, Material 3
- ViewModel + StateFlow
- Navigation Compose