package dev.ohoussein.mergeadaptertutorial.data

object DataProvider {

    fun getRandomItemsGroup(countItems: Int, suffix: String): ItemsGroup =
        ItemsGroup(
            title = "Header $suffix",
            items = (1..countItems)
                .map {
                    "Item number $it"
                }
        )

    fun getRandomItemsGroupList(countGroups: Int): List<ItemsGroup> =
        (1..countGroups)
            .map { i ->
                getRandomItemsGroup(i, i.toString())
            }
}