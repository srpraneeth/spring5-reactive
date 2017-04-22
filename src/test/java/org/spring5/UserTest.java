package org.spring5;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class UserTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void test() throws IOException {
		FluxExchangeResult<User> result = webTestClient.get().uri("/api/user").accept(MediaType.APPLICATION_JSON)
				.exchange().returnResult(User.class);
		assert result.getStatus().value() == 200;
		List<User> users = result.getResponseBody().collectList().block();
		assert users.size() == 2;
		assert users.iterator().next().getUser().equals("User1");
	}

	@Test
	public void test1() throws IOException {
		User user = webTestClient.get().uri("/api/user/1")
				.accept(MediaType.APPLICATION_JSON).exchange().returnResult(User.class).getResponseBody().blockFirst();
		assert user.getId() == 1;
		assert user.getUser().equals("User1");
	}

	@Test
	public void test2() throws IOException {
		webTestClient.get().uri("/api/user/10").accept(MediaType.APPLICATION_JSON).exchange().expectStatus()
				.isNotFound();
	}

}
