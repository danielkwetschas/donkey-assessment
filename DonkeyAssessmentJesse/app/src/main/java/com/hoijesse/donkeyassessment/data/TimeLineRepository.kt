package com.hoijesse.donkeyassessment.data

import kotlin.random.Random

class TimeLineRepository {
    private val groups = listOf(
        "Main",
        "Bijbeltekst",
        "Klussen",
        "Kerktuin",
        "Oeps"
    )

    // Entry messages, divided into the aforementioned groups
    private val messageText = mapOf(
        "${groups[0]}1" to "De kerkdienst begint om 9:30!",
        "${groups[0]}2" to "Ook deze week collecteren we voor een goed doel.",
        "${groups[0]}3" to "De orde van dienst is online beschikbaar.",
        "${groups[0]}4" to "Kom jij ook mee naar het gemeenteweekend?",
        "${groups[0]}5" to "Op Openmonumentendag zijn wij open.",
        "${groups[1]}1" to "Mijn hulp komt van de HEER, die hemel en aarde gemaakt heeft. - Psalmen 121:2",
        "${groups[1]}2" to "Wees niet bang, want Ik ben bij je, vrees niet, want Ik ben je God. - Jesaja 41:10",
        "${groups[1]}3" to "Proef en geniet de goedheid van de HEER, gelukkig de mens die bij Hem schuilt. - Psalmen 34:9",
        "${groups[1]}4" to "Alles wat u doet, moet u met liefde doen. - 1 Korintiërs 16:14",
        "${groups[1]}5" to "Wees vastberaden en standvastig. Laat je niet afschrikken, want de HEER, je God, staat je bij. - Jozua 1:9",
        "${groups[2]}1" to "Help mee klussen!",
        "${groups[2]}2" to "Vandaag weer hard bezig geweest.",
        "${groups[2]}3" to "Dat onhandige schuurtje zal binnenkort weg zijn.",
        "${groups[2]}4" to "Klussen begint om 19:30.",
        "${groups[2]}5" to "Klussen gaat vandaag niet door.",
        "${groups[3]}1" to "Gisteren een hoop gedaan in de kerktuin.",
        "${groups[3]}2" to "Gisteren begonnen we om 08.00 uur in de kerktuin en ronde  om 10.00 uur af.",
        "${groups[3]}3" to "Vandaag is de hedra in de kerktuin gesnoeid.",
        "${groups[3]}4" to "Meerdere plantjes weer gekregen en in de tuin geplaatst.",
        "${groups[3]}5" to "We hebben een hoop gedaan vandaag.",
        "${groups[4]}1" to "Vanaf maandag gaan 'ze' weer verder",
        "${groups[4]}2" to "Het deurslot van de Kerk is bijna kapot.",
        "${groups[4]}3" to "De koffiekan van het kleine koffieapparaat is gebarsten.",
        "${groups[4]}4" to "Enkele lampjes doen niet meer mee",
        "${groups[4]}5" to "Een stukje van de nieuwe vloerbedekking op het balkon is los."
    )

    private val firstName = listOf(
        "Frederik", "Cornelis", "Leopold", "Diederik", "Bert", "Ad", "Harm", "Menno", "Rik", "Alfred", "Quirijn", "Henricus", "Melvin", "Jochem", "Michel", "Govert", "Godfried", "Jonas", "Joos", "Adam",
        "Loes", "Lieve", "Willeke", "Marijke", "Anouk", "Lieke", "Mariska", "Karlijn", "Ingrid", "Marianne","Mirjam", "Famke", "Charlotte", "Lonneke", "Annie", "Wilma", "Claudia", "Carolien", "Ans", "Sanne"
    )

    private val lastName = listOf(
        "Tromp", "Bolhuis", "Kroon", "Eekhout", "Van Hemert", "Lens", "Van der Ende", "Van der Veen", "Fontein", "Borremans", "Middelkoop", "Hoes", "Maertens", "Wagenaar", "Ruysch", "Strijk", "Spiegel",
        "Westerveld", "Klooster", "Bruynseraede"
    )

    // Simulate a user
    private val currentUser = User (
        uid = 1,
        name = "Henk",
        inGroups = listOf(
            "Main",
            "Bijbeltekst",
            "Klussen",
            "Oeps"
        )
    )

    // Generate the large dataset of timeline entries
    private val entries = List(1500) { index ->
        if (index != 21) TimeLineEntry(
            id = index + 1,
            inGroup = groups[index % groups.size],
            text = "${messageText["${groups[index % groups.size]}${Random.nextInt(1, 5)}"]}",
            image = if (Random.nextInt(1, 3) == 2) {
                "https://picsum.photos/600/400?random=${Random.nextInt(1, 4096)}"
            } else null,
            senderName = "${firstName[Random.nextInt(0, firstName.size - 1)]} ${lastName[Random.nextInt(0, lastName.size - 1)]}"
        )
        else TimeLineEntry(
            id = 22,
            inGroup = "Main",
            text = "Het nieuwe kantoor van Donkey Mobile is inmiddels officieel geopend door de burgemeester en het is hier erg gezellig!",
            image = """https://cdn.donkeymobile.com/JPEG_1789673175275-d55ca8c0-1d9a-4551-82e9-9bddf3eb0a0c.jpg""",
            senderName = "Jesse van den Berg"
        )
    }

    // Filter entries for the current user
    private val groupFiltered = entries.filter { it.inGroup in currentUser.inGroups }

    fun getEntries(start: Int, entries: Int): List<TimeLineEntry> {
        require(start >= 0) { "Starting index must be non-negative" }
        require(entries >= 0) { "Number of entries must be non-negative" }
        return groupFiltered.drop(start).take(entries)
    }
}