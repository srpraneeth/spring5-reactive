package org.spring5.repository;

import java.util.Arrays;
import java.util.List;

import org.spring5.model.User;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepository {

	private final List<User> users = Arrays.asList(new User(1L, "User1"), new User(2L, "User2"));

	public Mono<User> getUserById(String id) {
		return Mono.justOrEmpty(users.stream().filter(user -> {
			return user.getId().equals(Long.valueOf(id));
		}).findFirst().orElse(null));
	}

	public Flux<User> getUsers() {
		return Flux.fromIterable(users);
	}

}
