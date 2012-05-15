package uk.co.vurt.hakken.server.web.admin;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.server.connector.DataConnector;
import uk.co.vurt.hakken.server.mapping.ServiceMapping;
import uk.co.vurt.hakken.server.service.DataConnectorService;
import uk.co.vurt.hakken.server.service.MappingService;
import uk.co.vurt.hakken.server.task.TaskRegistry;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	TaskRegistry taskRegistry;
	@Autowired
	DataConnectorService dataConnectorService;
	@Autowired
	MappingService mappingService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model){
		
		model.addAttribute("taskDefinitions", taskRegistry.getAllTasks());
//		model.addAttribute("dataConnectors", dataConnectorService.getConnectorNames());
		model.addAttribute("dataConnectors", dataConnectorService.getDataConnectorsMap());
		model.addAttribute("mappings", mappingService.getAll());
		return "admin";
	}
	
	@RequestMapping(value = "/task/{name}", method = RequestMethod.GET)
	public String viewTask(@PathVariable String name, Model model){
		model.addAttribute("task", taskRegistry.getTask(name));
		return "task";
	}
	
	@RequestMapping(value = "/dataconnector/{name}", method = RequestMethod.GET)
	public String viewDataConnector(@PathVariable String name, Model model){
		model.addAttribute("dataConnector", dataConnectorService.getDataConnector(name));
		model.addAttribute("taskDefinitions", taskRegistry.getAllTasks());
		model.addAttribute("connectorName", name);
		return "dataconnector";
	}
	
	@RequestMapping(value = "/mapping/new", method = RequestMethod.POST)
	public String createMapping(@RequestParam String connectorName, @RequestParam String taskName, Model model){
		model.addAttribute("dataConnector", dataConnectorService.getDataConnector(connectorName));
		model.addAttribute("taskDefinition", taskRegistry.getTask(taskName));
		return "createmapping";
	}
	@RequestMapping(value = "/mapping/save", method = RequestMethod.POST)
	public String createMapping(HttpServletRequest request/*, @RequestParam String taskName, @RequestParam String connectorName*/){
		String taskName = request.getParameter("taskName");
		String connectorName = request.getParameter("connectorName");
		
		logger.info("TaskName: " + taskName + " connectorName: " + connectorName);
		
		TaskDefinition task = taskRegistry.getTask(taskName);
		DataConnector connector = dataConnectorService.getDataConnector(connectorName);
		logger.info("TASK : " + task);
		logger.info("CONNECTOR: " + connector);
		
		if(task != null && connector != null){
			ServiceMapping mapping = new ServiceMapping();
			mapping.setTaskDefinition(task);
			mapping.setDataConnector(connector);

			Enumeration parameterNames = request.getParameterNames();
			while(parameterNames.hasMoreElements()){
				String paramName = (String)parameterNames.nextElement();
				if(paramName.contains("_")){
					logger.info("Found page item parameter: " + paramName);
					mapping.setMapping(request.getParameter(paramName), paramName);
				}
			}
			logger.info("MAPPING: " + mapping.toString());
			mappingService.save(mapping);
		}
		return("redirect:/admin/");
	}
	
	@RequestMapping(value="/mapping/{id}", method=RequestMethod.GET)
	public String viewMapping(@PathVariable long id, Model model){
		model.addAttribute("mapping", mappingService.get(id));
		return ("mapping");
	}
	
	@RequestMapping("/reloadTasks")
	public String reloadTasks(Model model){
		taskRegistry.reload();
		return home(model);
	}
}