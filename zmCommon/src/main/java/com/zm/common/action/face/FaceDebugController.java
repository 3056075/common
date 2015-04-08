package com.zm.common.action.face;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class FaceDebugController {
	private List<String> messages = new LinkedList<String>();

	@RequestMapping("facedebug")
	public String debug(Model model) {
		model.addAttribute("messages", messages);
		return "admin/facedebug";
	}

	public synchronized void put(String message) {
		Date now =new Date();
		String dateStr = DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss");
		dateStr = "<span style=\"color:red\">"+dateStr+"</span>";
		messages.add(0,dateStr+" "+message);
		while (messages.size() > 500) {
			messages.remove(messages.size()-1);
		}
	}
}
