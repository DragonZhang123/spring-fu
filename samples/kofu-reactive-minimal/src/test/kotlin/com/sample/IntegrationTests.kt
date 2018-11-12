package com.sample

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.web.reactive.server.*

class IntegrationTests {

	private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181").build()

	private lateinit var context: ConfigurableApplicationContext

	@BeforeAll
	fun beforeAll() {
		context = app.run(profiles = "test")
	}

	@Test
	fun `Request root endpoint`() {
		client.get().uri("/").exchange()
				.expectStatus().is2xxSuccessful
				.expectBody<String>().isEqualTo("Hello world!")
	}

	@AfterAll
	fun afterAll() {
		context.close()
	}
}