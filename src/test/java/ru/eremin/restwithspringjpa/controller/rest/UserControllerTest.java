package ru.eremin.restwithspringjpa.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.eremin.restwithspringjpa.model.dto.UserDTO;
import ru.eremin.restwithspringjpa.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;


    private UserDTO testUser1;
    private UserDTO testUser2;
    private List<UserDTO> userList;

    @BeforeEach
    void setUp() {
        testUser1 = new UserDTO(1L, "Vasya Petrov", "Vasya@example.com", 30);
        testUser2 = new UserDTO(2L, "Petya Ivanov", "Petya@example.com", 25);
        userList = Arrays.asList(testUser1, testUser2);
    }

    @Test
    void findUsers_ShouldReturnAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(userList);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Vasya Petrov")))
                .andExpect(jsonPath("$[0].email", is("Vasya@example.com")))
                .andExpect(jsonPath("$[0].age", is(30)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Petya Ivanov")))
                .andExpect(jsonPath("$[1].email", is("Petya@example.com")))
                .andExpect(jsonPath("$[1].age", is(25)));
    }

    @Test
    void findUserById_ShouldReturnUser() throws Exception {
        when(userService.findById(1L)).thenReturn(testUser1);

        mockMvc.perform(get("/api/v1/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Vasya Petrov")))
                .andExpect(jsonPath("$.email", is("Vasya@example.com")))
                .andExpect(jsonPath("$.age", is(30)));
    }

    @Test
    void findUserByEmail_ShouldReturnUser() throws Exception {
        when(userService.findByEmail("Vasya@example.com")).thenReturn(testUser1);

        mockMvc.perform(get("/api/v1/users/emails/{email}", "Vasya@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Vasya Petrov")))
                .andExpect(jsonPath("$.email", is("Vasya@example.com")))
                .andExpect(jsonPath("$.age", is(30)));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        UserDTO updatedUser = new UserDTO(1L, "Vasya Updated", "VasyaUpdated@example.com", 31);
        when(userService.update(any(UserDTO.class), anyLong())).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/users/{id}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Vasya Updated")))
                .andExpect(jsonPath("$.email", is("VasyaUpdated@example.com")))
                .andExpect(jsonPath("$.age", is(31)));
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        when(userService.save(any(UserDTO.class))).thenReturn(testUser1);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Vasya Petrov")))
                .andExpect(jsonPath("$.email", is("Vasya@example.com")))
                .andExpect(jsonPath("$.age", is(30)));
    }

    @Test
    void deleteUserById_ShouldReturnNoContent() throws Exception {
        doNothing().when(userService).deleteById(anyLong());

        mockMvc.perform(delete("/api/v1/users/{id}", 1)).andExpect(status().is2xxSuccessful());
    }
}
