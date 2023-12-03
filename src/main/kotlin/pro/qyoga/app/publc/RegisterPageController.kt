package pro.qyoga.app.publc

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import pro.qyoga.core.users.api.RegisterTherapistRequest

@Controller
class RegisterPageController(
    private val registerTherapistWorkflow: RegisterTherapistWorkflow,
    @Value("\${qyoga.admin.email}") private val adminEmail: String
) {

    @GetMapping("/register")
    fun getRegisterPage(model: Model): String {
        model.addAttribute("requestForm", RegisterTherapistRequest("", "", ""))
        return "public/register"
    }

    @PostMapping("/register")
    fun register(registerTherapistRequest: RegisterTherapistRequest, model: Model): String {
        val therapist = registerTherapistWorkflow.registerNewTherapist(registerTherapistRequest)

        if (therapist == null) {
            model.addAttribute("userAlreadyExists", true)
            model.addAttribute("adminEmail", adminEmail)
            model.addAttribute("requestForm", registerTherapistRequest)
            return "public/register :: registerForm"
        }

        return "public/register-success-fragment"
    }

}