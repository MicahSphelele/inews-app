package sphe.inews.domain.enums

import sphe.inews.R

enum class NewsCategory(val title:  String, val image: Int) {
    BUSINESS ("Business", R.drawable.ic_business),
    HEALTH("Health", R.drawable.ic_health),
    SPORTS("Sports", R.drawable.ic_sports),
    TECHNOLOGY("Technology", R.drawable.ic_technology),
    ENTERTAINMENT("Entertainment", R.drawable.ic_entertainment)

}