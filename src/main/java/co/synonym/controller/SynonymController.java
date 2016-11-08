package co.synonym.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.synonym.model.Query;
import com.synonym.model.Synonym;

@Controller
public class SynonymController {
	
	@MessageMapping("/query")
	@SendTo("/topic/synonym")
	public List<Synonym> querySynonymList(Query query) {
		List<Synonym> res = new ArrayList<Synonym>();
		//TODO
		String input = query.getInput();
		String mode = query.getMode();
		String category = query.getCategory();
		
		return res;
	}

}
