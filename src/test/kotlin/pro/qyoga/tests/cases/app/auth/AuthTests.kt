package pro.qyoga.tests.cases.app.auth

import io.restassured.http.Cookie
import io.restassured.matcher.RestAssuredMatchers.detailedCookie
import io.restassured.module.kotlin.extensions.Then
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import pro.qyoga.tests.assertions.shouldBe
import pro.qyoga.tests.clients.PublicClient
import pro.qyoga.tests.clients.TherapistClient
import pro.qyoga.tests.clients.pages.publc.LoginPage
import pro.qyoga.tests.fixture.therapists.THE_THERAPIST_LOGIN
import pro.qyoga.tests.fixture.therapists.THE_THERAPIST_PASSWORD
import pro.qyoga.tests.infra.web.QYogaAppIntegrationBaseTest
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


class AuthTests : QYogaAppIntegrationBaseTest() {

    @Test
    fun `When unauthenticated user requests secured page he should be redirected to login page`() {
        // Given
        val therapist = TherapistClient(Cookie.Builder("unauthorized").build())

        // When
        val response = therapist.clients.getClientsListPage()

        // Then
        response shouldBe LoginPage
    }

    @Test
    fun `TA should remember users for 9 days`() {
        // Given
        val now = Instant.now()
            .minusSeconds(1) // без этого тест мигает

        // When
        val response = PublicClient.authApi.loginForVerification(THE_THERAPIST_LOGIN, THE_THERAPIST_PASSWORD)

        response.Then {
            statusCode(HttpStatus.FOUND.value())
            cookie(
                "remember-me",
                detailedCookie()
                    .expiryDate(greaterThanOrEqualTo(Date(now.plus(9, ChronoUnit.DAYS).toEpochMilli())))
            )
        }
    }

}