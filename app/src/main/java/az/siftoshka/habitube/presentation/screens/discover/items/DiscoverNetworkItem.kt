package az.siftoshka.habitube.presentation.screens.discover.items

import az.siftoshka.habitube.presentation.screens.discover.DiscoverScreen

/**
 * Item for [DiscoverScreen].
 */
class DiscoverNetworkItem(
    val category: DiscoverNetworkCategory,
    var text: String
)

enum class DiscoverNetworkCategory(val id: String) {
    NETFLIX("213"),
    HBO("49"),
    DISNEY("2739"),
    AMAZON("1024"),
    ADULT_SWIM("80"),
    APPLE("2552"),
    BBC("4"),
    HULU("453"),
    CBS("16"),
    NBC("6")
}

val networks = mutableListOf(
    DiscoverNetworkItem(
        DiscoverNetworkCategory.NETFLIX,
        "Netflix"
    ),
    DiscoverNetworkItem(
        DiscoverNetworkCategory.HBO,
        "HBO"
    ),
    DiscoverNetworkItem(
        DiscoverNetworkCategory.DISNEY,
        "Disney +"
    ),
    DiscoverNetworkItem(
        DiscoverNetworkCategory.AMAZON,
        "Amazon Prime Video"
    ),
    DiscoverNetworkItem(
        DiscoverNetworkCategory.ADULT_SWIM,
        "Adult Swim"
    ),
    DiscoverNetworkItem(
        DiscoverNetworkCategory.APPLE,
        "Apple TV +"
    ),
    DiscoverNetworkItem(
        DiscoverNetworkCategory.BBC,
        "BBC One"
    ),
    DiscoverNetworkItem(
        DiscoverNetworkCategory.HULU,
        "Hulu"
    ),
    DiscoverNetworkItem(
        DiscoverNetworkCategory.CBS,
        "CBS"
    ),
    DiscoverNetworkItem(
        DiscoverNetworkCategory.NBC,
        "NBC"
    )
)