package com.example.marvelcharacterapp.DetailNavigation

import androidx.compose.ui.graphics.vector.ImageVector

//Class define items(path,title and icon) for NavItem
open class Item (
    val path:String,
    val title: String,
    val icon: ImageVector
){}

//Class defines items for ComicNavItem
open class ComicItem (
    val path:String,
    val title: String,
    val icon:ImageVector
){}