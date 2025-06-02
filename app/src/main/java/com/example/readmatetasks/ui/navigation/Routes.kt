package com.example.readmatetasks.ui.navigation

/**
 * Representa las rutas de navegación dentro de la aplicación.
 *
 * @property route Cadena que identifica la ruta en la navegación.
 */
sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Login : Routes("login")
    object Register : Routes("register")
    object Home : Routes("home")
    object Discover : Routes("discover")
    object Timer : Routes("timer")
    object Wishlist : Routes("wishlist")
    object FinishedBooks : Routes("finished_books")
    object WishlistPending : Routes("wishlist_pending")
    object Profile : Routes("profile")
    object EditProfile : Routes("editProfile")
    object BookDetail : Routes("book_detail")
}
