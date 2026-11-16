package com.example.marketgo.model

object TemporaryData {

    val categories = listOf(
        Category(1, "Fruits", "🍎"),
        Category(2, "Vegetables", "🥕"),
        Category(3, "Drinks", "🥤"),
        Category(4, "Fast Food", "🍔"),
        Category(5, "Bakery", "🍞"),
        Category(6, "Dairy & Eggs", "🥛"),
        Category(7, "Meat & Fish", "🥩"),
        Category(8, "Grocery", "🍚"),
        Category(9, "Medicine", "💊")
    )

    val products = listOf(
        Product(1, "Banana", 650, 1, "🍌"),
        Product(2, "Apple", 800, 1, "🍎"),
        Product(3, "Orange", 900, 1, "🍊"),

        Product(4, "Carrot", 300, 2, "🥕"),
        Product(5, "Tomato", 1100, 2, "🍅"),
        Product(6, "Potato", 250, 2, "🥔"),

        Product(7, "Cola 1L", 700, 3, "🥤"),
        Product(8, "Orange Juice", 1200, 3, "🧃"),
        Product(9, "Water 1.5L", 350, 3, "💧"),

        Product(10, "Burger", 1800, 4, "🍔"),
        Product(11, "Pizza", 3500, 4, "🍕"),
        Product(12, "French Fries", 1000, 4, "🍟"),

        Product(13, "Bread", 180, 5, "🍞"),
        Product(14, "Croissant", 450, 5, "🥐"),
        Product(15, "Donut", 400, 5, "🍩"),

        Product(16, "Eggs (10 pcs)", 900, 6, "🥚"),
        Product(17, "Milk 1L", 550, 6, "🥛"),
        Product(18, "Cheese", 2200, 6, "🧀"),

        Product(19, "Chicken", 2500, 7, "🍗"),
        Product(20, "Beef", 4500, 7, "🥩"),
        Product(21, "Salmon", 6000, 7, "🐟"),

        Product(22, "Rice 1kg", 800, 8, "🍚"),
        Product(23, "Pasta", 500, 8, "🍝"),
        Product(24, "Sugar 1kg", 450, 8, "🧂"),

        Product(25, "Vitamin C", 1500, 9, "💊"),
        Product(26, "Bandages", 600, 9, "🩹"),
        Product(27, "Thermometer", 2800, 9, "🌡️")
    )
}