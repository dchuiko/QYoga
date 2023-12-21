package pro.qyoga.tests.clients.pages.therapist.clients.journal

import org.jsoup.nodes.Element
import pro.qyoga.core.clients.journals.api.JournalEntry
import pro.qyoga.tests.assertions.shouldHave
import pro.qyoga.tests.clients.pages.therapist.clients.ClientPageTabsFragment
import pro.qyoga.tests.infra.html.QYogaPage


abstract class ClientJournalPage : QYogaPage {

    override val path = "/therapist/clients/{id}/journal"

    override val title = ""

    override fun match(element: Element) {
        element shouldHave ClientPageTabsFragment
    }

}

object EmptyClientJournalPage : ClientJournalPage() {

    override fun match(element: Element) {
        super.match(element)

        element shouldHave EmptyClientJournalFragment
    }

}

class NonEmptyClientJournalPage(private val entries: List<JournalEntry>) : ClientJournalPage() {

    override fun match(element: Element) {
        super.match(element)

        element shouldHave ClientJournalFragment.fragmentFor(entries)
    }


}