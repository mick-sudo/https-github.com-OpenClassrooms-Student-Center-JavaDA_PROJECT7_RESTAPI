package com.nnk.springboot.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.repositories.RatingRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {
	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;
    
	@WithMockUser(authorities = "USER")
	@Test
	public void showRatingListTest() throws Exception {
		mockMvc.perform(get("/bidList/list")).andExpect(status().isForbidden());
	}

	@WithMockUser(authorities = "ADMIN")
	@Test
	public void showRatingTestAdmin() throws Exception {
		mockMvc.perform(get("/bidList/list")).andExpect(status().isOk());
	}

	@WithMockUser(authorities = "ADMIN")
	@Test
	public void testAddRatingAdmin() throws Exception {
		this.mockMvc.perform(get("/bidList/add")).andExpect(status().isOk());
	}
	
}
