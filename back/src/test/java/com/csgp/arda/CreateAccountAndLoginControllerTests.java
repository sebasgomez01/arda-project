package com.csgp.arda;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.csgp.arda.web.CreateAccountController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test") 
@SpringBootTest
@AutoConfigureMockMvc
@Transactional //esto es para limpiar la base de datos después de cada test, igual los tests funcionan correctamente sin la anotación
public class CreateAccountAndLoginControllerTests {
    @Autowired
	private MockMvc mockMvc;



    @Test
    public void testCreateUser() throws Exception {
        // Arrange
        String userJson = "{\"name\":\"homero\", \"username\":\"cosme-fulanito\",\"password\":\"abcdef123456\", \"role\":\"USER\"}";

		// Act
		// Create User
		ResultActions result = mockMvc.perform(post("/users/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));
        // Assert
        result.andExpect(status().isCreated());
    }

    @Test
    public void testCreateUserWithExistsUsername() throws Exception {
        // Arrange
        String userJson = "{\"name\":\"homero\", \"username\":\"cosme-fulanito\",\"password\":\"abcdef123456\", \"role\":\"user\"}";

		// Act
		// Create User
		mockMvc.perform(post("/users/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

		ResultActions result = mockMvc.perform(post("/users/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));
        
				// Assert
        result.andExpect(status().isConflict())
			.andExpect(content().string(org.hamcrest.Matchers.containsString("username is already taken!")));
    }


    @Test
    public void testLoginUser() throws Exception {
        // Arrange
        String userJson = "{\"name\":\"homero\", \"username\":\"cosme-fulanito\",\"password\":\"abcdef123456\", \"role\":\"user\"}";

		// Act

		// Create User
		mockMvc.perform(post("/users/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

		// Login
		ResultActions result = mockMvc.perform(post("/users/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(userJson));

        String token = result.andReturn().getResponse().getHeader("Authorization");

        // Assert
        result.andExpect(status().isOk())
			.andExpect(header().exists("Authorization"))
			.andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")));

        // chequeo que el token es válido creando un post
        String postJson = "{\"title\":\"hola\", \"textContent\":\"soy Germán\", \"imagePath\":\"/\"}";

        mockMvc.perform(post("/posts")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
			.content(postJson))
            .andExpect(status().isCreated());
    }


    @Test
    public void testLoginUserWithIncorrectUsername() throws Exception {
        // Arrange
        String userJson = "{\"name\":\"frodo baggins\", \"username\":\"frodo\",\"password\":\"abcdef123456\", \"role\":\"user\"}";

		// Act

		// Create User
		mockMvc.perform(post("/users/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

		// Login
		ResultActions result = mockMvc.perform(post("/users/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"username\":\"frodo-bagin\",\"password\":\"abcde123456\"}"));

        // Assert
        result.andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginUserWithIncorrectPassword() throws Exception {
        // Arrange
        String userJson = "{\"name\":\"legolas greenleaf\", \"username\":\"legolas\",\"password\":\"abcdef123456\", \"role\":\"user\"}";

		// Act
		// Create User
		mockMvc.perform(post("/user/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

		// Login
		ResultActions result = mockMvc.perform(post("/users/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"username\":\"legolas\",\"password\":\"abcde123456\"}"));

        // Assert
        result.andExpect(status().isUnauthorized());
    }


    @Test
    public void testLoginNoExistingUser() throws Exception {
        // Arrange
        String userJson = "{\"name\":\"Gandalf\", \"username\":\"gandalf-the-grey\",\"password\":\"youShallNotPass\", \"role\":\"user\"}";
		// Login
		ResultActions result = mockMvc.perform(post("/users/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(userJson));

        // Assert
        result.andExpect(status().isUnauthorized());
    }

    @Test
    public void testLogoutUser() throws Exception {
        // Arrange
        String userJson = "{\"name\":\"homero\", \"username\":\"cosme-fulanito\",\"password\":\"abcdef123456\", \"role\":\"user\"}";
		// Act
		// Create User
		mockMvc.perform(post("/users/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

		// Login
		ResultActions result = mockMvc.perform(post("/users/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"username\":\"cosme-fulanito\",\"password\":\"abcdef123456\"}"));

        String token = result.andReturn().getResponse().getHeader("Authorization");

        // Assert
        result.andExpect(status().isOk())
			.andExpect(header().exists("Authorization"))
			.andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")));

        // chequeo que el token es válido creando un post
        String postJson = "{\"title\":\"hola\", \"textContent\":\"soy Germán\", \"imagePath\":\"/\"}";

        mockMvc.perform(post("/posts")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
			.content(postJson))
            .andExpect(status().isCreated());

        // logout
        mockMvc.perform(post("/logout")
            .header("Authorization", token)) 
            .andExpect(status().isOk());

        
        mockMvc.perform(post("/posts")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
			.content(postJson))
            .andExpect(status().isUnauthorized());
        
    }    
}

