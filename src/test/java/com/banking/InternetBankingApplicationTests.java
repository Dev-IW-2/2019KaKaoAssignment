package com.banking;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InternetBankingApplicationTests {

	
	@Autowired
	MockMvc mockMvc;
	@Test
	public void contextLoads() throws Exception {
		
		//#API 1(데이터 추가 테스트)
		//mockMvc.perform(post("/add"))
		//		.andExpect(status().isCreated());
		
		//#API 2(서비스 접속기기 목록 출력 테스트)
		mockMvc.perform(get("/device"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("스마트폰")));
		
		//#API 3(년도별로 가장 많이 이용하는 접속기기 출력 테스트)
		mockMvc.perform(get("/device/max"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("device_id")));
		
		//#API 4 (특정년도, 그 해에 가장 많이 접속하는 기기 출력 테스트)
		mockMvc.perform(get("/device/2012/max"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("rate")));
		
		//#API 5(디바이스 아이디, 접속 비율이 가장 많은 해 출력 테스트)
		mockMvc.perform(get("/year/DIS4396321/max"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("year")));
		
	}

	
}
