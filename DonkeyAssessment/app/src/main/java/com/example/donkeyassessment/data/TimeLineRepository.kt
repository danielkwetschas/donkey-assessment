package com.example.donkeyassessment.data

class TimeLineRepository {
    private val groups = listOf(
        "LINK",
        "Tim & Rank",
        "Gemeente",
        "Kerkraad",
        "Alpha"
    )
    private val messages = listOf(
        "Vanavond komen we om 19:30 bij elkaar. Tot vanavond!",
        "Bedankt aan iedereen die afgelopen zondag heeft geholpen.",
        "Deze week denken we samen na over wat het betekent om er voor elkaar te zijn.",
        "De foto's van afgelopen weekend staan online. Wat was het een mooie dag!",
        "Wie kan er zaterdag helpen met de voorbereidingen?",
        "Vanavond repeteren we de nieuwe nummers voor zondag.",
        "Vergeet je niet aan te melden voor de gezamenlijke maaltijd.",
        "Een mooie herinnering: soms zit kracht juist in de kleine dingen.",
        "Zondag na de dienst drinken we samen koffie. Iedereen is welkom!",
        "We zoeken nog twee vrijwilligers voor volgende week.",
        "Aanstaande zondag beginnen we om 10:00. We hopen jullie allemaal weer te zien!",
        "Dank voor alle mooie gesprekken tijdens de bijeenkomst van gisteren.",
        "De voorbereidingen voor het jongerenweekend zijn begonnen. Meer informatie volgt snel.",
        "Wie heeft er zin om volgende week mee te helpen met de kinderactiviteiten?",
        "Een fijne week gewenst allemaal!"
    )

    // Current user simulates a logged-in user
    private val currentUser = User (
        id = 1,
        name = "Jan",
        groups = listOf(
            "LINK",
            "Tim & Rank",
            "Gemeente"
        )
    )

    // Generate a large dataset to simulate timeline content
    private val items = List(1000) { index ->
        TimeLineItem(
            id = index + 1,
            groupName = groups[index % groups.size],
            content = messages[index % messages.size],
            imgURL = if (index % 3 == 0) {
                "https://picsum.photos/600/400?random=${index + 1}"
            } else {
                null
            }
        )
    }

    // Only get items from the usersgroups
    private val userItems = items.filter { item ->
        item.groupName in currentUser.groups
    }

    fun getItems(offset: Int, limit: Int): List<TimeLineItem> {
        require(offset >= 0) {"Offset cannot be negative"}
        require(limit >= 0) {"Limit cannot be negative"}
        return userItems.drop(offset).take(limit)
    }
}