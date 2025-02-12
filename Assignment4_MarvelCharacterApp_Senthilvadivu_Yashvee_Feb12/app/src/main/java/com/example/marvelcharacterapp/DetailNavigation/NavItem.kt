package com.example.marvelcharacterapp.DetailNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings

//Contains objects to navigate between Character detail screens
sealed class NavItem {
    object Detail: Item("detail","Detail", Icons.Default.Build)
    object Comics: Item("comics","Comics", Icons.Default.Settings)
    object Series: Item("series","Series", Icons.Default.CheckCircle)
}
//Contains objects to navigate between Comic detail screens
sealed class ComicNavItem {
    object Detail: ComicItem("detail","Detail", Icons.Default.Build)
    object Creators: ComicItem("creators","Creators", Icons.Default.Settings)
    object Characters: ComicItem("characters","Characters", Icons.Default.CheckCircle)
    object Images: ComicItem("images","Images", Icons.Default.Face)
}