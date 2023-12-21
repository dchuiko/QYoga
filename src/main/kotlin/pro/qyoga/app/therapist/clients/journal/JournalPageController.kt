package pro.qyoga.app.therapist.clients.journal

import org.springframework.data.domain.Page
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import pro.qyoga.core.clients.journals.api.JournalEntry
import pro.qyoga.core.clients.journals.api.JournalPageRequest
import pro.qyoga.platform.spring.mvc.modelAndView


private const val JOURNAL = "journal"

@Controller
@RequestMapping("/therapist/clients")
class JournalPageController(
    private val getJournalPageWorkflow: GetJournalPageWorkflow
) {

    @GetMapping("/{id}/journal")
    fun getJournalPage(
        @PathVariable id: Long
    ): ModelAndView {
        val result = getJournalPageWorkflow.getJournalPage(JournalPageRequest.firstPage(id))
        return when (result) {
            is GetJournalPageResult.ClientNotFound ->
                ModelAndView("forward:error/404")

            is GetJournalPageResult.Success -> {
                modelAndView("therapist/clients/client-edit") {
                    "client" bindTo result.client
                    JOURNAL bindTo result.page
                    "activeTab" bindTo JOURNAL
                }
            }
        }
    }

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun getJournal(model: Map<String, Any>): Page<JournalEntry> = model[JOURNAL] as Page<JournalEntry>

    }

}