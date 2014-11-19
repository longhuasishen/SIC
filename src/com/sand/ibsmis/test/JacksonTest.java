package com.sand.ibsmis.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.bean.User;


public class JacksonTest {
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("count", 123);
		List<User> list=new ArrayList<User>();
		User user=new User();
		user.setName("admin");
		user.setPassword("admin");
		Role role=new Role();
		role.setRole_name("admin");
		role.setRole_id("0001");
		role.setRole_comment("desc");
		user.setRole(role);
		list.add(user);
		map.put("rows",list);
		ObjectMapper objectMapper=new ObjectMapper();
		objectMapper.writeValue(System.out, map);
	}
}
