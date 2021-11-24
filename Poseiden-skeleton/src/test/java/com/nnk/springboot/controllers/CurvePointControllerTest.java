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

import com.nnk.springboot.repositories.CurvePointRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	CurvePointRepository curvePointRepository;
	
	@WithMockUser(authorities = "USER")
	@Test
	public void showCurpointList() throws Exception {
		mockMvc.perform(get("/curvePoint/list")).andExpect(status().isForbidden());
	}

	@WithMockUser(authorities = "ADMIN")
	@Test
	public void showCurpointTestAdmin() throws Exception {
		mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk());
	}

	@WithMockUser(authorities = "ADMIN")
	@Test
	public void testAddCurvepointListAdmin() throws Exception {
		this.mockMvc.perform(get("/curvePoint/add")).andExpect(status().isOk());
	}

}
