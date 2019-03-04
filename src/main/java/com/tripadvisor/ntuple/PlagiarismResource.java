package com.tripadvisor.ntuple;

import javax.inject.Inject;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripadvisor.entity.Tuple;

@RestController
public class PlagiarismResource {
	
	private PlagiarismService plagiarismService;
	
	@Inject
	public PlagiarismResource(PlagiarismService plagiarismService) {
		this.plagiarismService = plagiarismService;
	}
	
	@GetMapping("/plagiarism/{id}")
	public String getPlagiarismPercentage(@PathParam("id") Long id) {
		
		/* If I am want to expose this endpoint, I would make Tuple an entity and save it in db
		 I would make you pass the tuple id as a path param and get the tuple values from db and then return percentage
		
		// For the sake of simplicity, I am just passing values hardcoded for now */
		
		Tuple tuple = new Tuple("synonyms.txt", "file1.txt", "file2.txt", 3);
		return plagiarismService.readAndExecuteInput(tuple);	
		
		// Tuple tuple = TupleDao.findById(id);
		// return plagiarismService.readAndExecuteInput(tuple);
	}

}
