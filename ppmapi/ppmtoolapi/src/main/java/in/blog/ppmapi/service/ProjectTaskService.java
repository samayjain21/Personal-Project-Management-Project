package in.blog.ppmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.blog.ppmapi.domain.Backlog;
import in.blog.ppmapi.domain.ProjectTask;
import in.blog.ppmapi.repository.BacklogRepository;
import in.blog.ppmapi.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(String projectIdentifier , ProjectTask projectTask) {
		//Exception handling: in case project is not available
		
		//ProjectTAsk should be added to a specific Project , project != null, backlog exist
		Backlog backlog =backlogRepository.findByProjectIdentifier(projectIdentifier);
		
		//set the backlog to the project Task
		projectTask.setBacklog(backlog);
		//we want our project to look like : IDPRO-1, IDPRO-2...
		Integer backlogSequence =backlog.getPTSequence();
		//Update backlog sequence
		backlogSequence++;
		backlog.setPTSequence(backlogSequence);
		// Add Backlog sequence to ProjectTask
		projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
		projectTask.setProjectIdentifer(projectIdentifier);
		//setting default priority and status
		
		if(projectTask.getPriority()==null) {
			projectTask.setPriority(3);
			
		}
		if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
			projectTask.setStatus("TODO");
		}
		// Few changes done by me
		ProjectTask projectTask2 = projectTaskRepository.save(projectTask);
		backlogRepository.save(backlog);
		return projectTask2;
		
	}
}
