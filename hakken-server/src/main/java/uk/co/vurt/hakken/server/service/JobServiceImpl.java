package uk.co.vurt.hakken.server.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.task.Page;
import uk.co.vurt.hakken.domain.task.PageSelector;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.domain.task.pageitem.PageItem;
import uk.co.vurt.hakken.server.persistence.GenericDAO;
import uk.co.vurt.hakken.server.persistence.JobDAO;

@Service
public class JobServiceImpl implements JobService{

	JobDAO dao;
	

	public JobDefinition getByName(String name){
		return dao.getByName(name);
	}

	@Override
	public void setDao(GenericDAO<Long, JobDefinition> dao) {
		this.dao = (JobDAO) dao;
		this.dao.setClazz(JobDefinition.class);
	}

	@Override
	public List<JobDefinition> getForUserSince(String username, String timestamp) {
//		return dao.getByUserAndDate(username, timestamp);
		//TODO: Implement a mechanism for mapping task definitions to sources of job instances
		
		//find out which task definitions the user has access to
		
		//for each task definition, lookup instance provider/data connector and invoke.

		
		Page page1 = new Page("Page 1", "Page 1", Arrays.asList(new PageItem("Item 1", "Item 1", "TEXT", "This is page 1. The next page should be Page 3")), Arrays.asList(new PageSelector("Page 3")));
		Page page2 = new Page("Page 2", "Page 2", Arrays.asList(new PageItem("Item 2", "Item 2", "TEXT", "This is page 2. You shouldn't be here.")));
		Page page3 = new Page("Page 3", "Page 3", Arrays.asList(new PageItem("Item 3", "Item 3", "TEXT", "This is page 3.")));
		
		TaskDefinition threePageTask = new TaskDefinition(2, "Three Pages", "A task with 3 pages.", Arrays.asList(page1, page2, page3));
		
		return Arrays.asList(new JobDefinition(1, "Test 3 Page Job", threePageTask, new Date(), new Date(), "AWAITING", 
				"Test 3 Page Job"));
		
//		return Arrays.asList(new JobDefinition(1, "Test Job", new TaskDefinition(
//				1, "Test Task", "Test Task", Arrays.asList(new Page("Page 1", 
//				"Page 1", Arrays.asList(new PageItem("Item 1", "Item 1", 
//				"DATETIME", null))))), new Date(), new Date(), "AWAITING", 
//				"Test Job"));
	}

	@Override
	public JobDefinition get(Long id) {
		return dao.get(id);
	}
}
