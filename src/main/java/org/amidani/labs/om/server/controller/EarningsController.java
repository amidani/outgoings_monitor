package org.amidani.labs.om.server.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amidani.labs.om.server.model.Earning;
import org.amidani.labs.om.server.service.EarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/earnings")
public class EarningsController {
	
	Logger log = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	EarningsService earningsService;
	
	@RequestMapping(method=RequestMethod.GET, value="/all")
	public void getAllEarnings(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		log.info("Retrieving earnings...");
		List<Earning> l = earningsService.getEarnings();
		log.info("Write JSON in the output stream of the servlet");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), l);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/add/{label}/{amount}")
	public void addEarning(@PathVariable String label, @PathVariable int amount, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		log.info("Add new earnings...");
		long id = earningsService.addEarning(label, amount);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(),id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/delete/{id}")
	public void removeEarning(@PathVariable Long id, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		log.info("Delete earnings...");
		boolean result = earningsService.removeEarning(id);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(),result);
	}
}
