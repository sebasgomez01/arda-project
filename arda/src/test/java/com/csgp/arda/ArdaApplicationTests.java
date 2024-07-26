package com.csgp.arda;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.csgp.arda.web.PostController;

@SpringBootTest
class ArdaApplicationTests {

	@Autowired
	private PostController controller;

	@Test

	void contextLoads() {
		assertThat(controller).isNotNull();

	}

}
