package com.toedter.workshops.springrest.lab2.user;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UserRepositoryIntegrationTest {

	@Autowired
	UserRepository userRepository;

	@Test
	public void shouldFindsAllUsers() {
		Iterable<User> users = userRepository.findAll();
		assertThat(users, is(not(emptyIterable())));
	}

	@Test
	public void shouldCreatesNewUser() {
		long before = userRepository.count();

		User user = userRepository.save(createUser());

		Iterable<User> result = userRepository.findAll();
		assertThat(result, is(Matchers.iterableWithSize((int)(before + 1))));
		assertThat(result, Matchers.hasItem(user));
	}

	public static User createUser() {
		return new User("test_user", "Test", "test@toedter.com");
	}
}
