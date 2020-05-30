package in.blog.ppmapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.blog.ppmapi.domain.Backlog;
import in.blog.ppmapi.domain.Project;
import in.blog.ppmapi.domain.ProjectTask;
import in.blog.ppmapi.exception.ProjectNotFoundException;
import in.blog.ppmapi.repository.BacklogRepository;
import in.blog.ppmapi.repository.ProjectRepository;
import in.blog.ppmapi.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		// Exception handling: in case project is not available

		try {

			// ProjectTAsk should be added to a specific Project , project != null, backlog
			// exist
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

			// set the backlog to the project Task
			projectTask.setBacklog(backlog);
			// we want our project to look like : IDPRO-1, IDPRO-2...
			Integer backlogSequence = backlog.getPTSequence();
			// Update backlog sequence
			backlogSequence++;
			backlog.setPTSequence(backlogSequence);
			// Add Backlog sequence to ProjectTask
			projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
			projectTask.setProjectIdentifer(projectIdentifier);
			// setting default priority and status
			//if priority is left not filled then it is set to low
			//priority 1- low; 2- medium; 3- high
			if (projectTask.getPriority() == 0 ||projectTask.getPriority() == null) {
				projectTask.setPriority(3);

			}
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TODO");
			}
			// Few changes done by me
			// ProjectTask projectTask2 = projectTaskRepository.save(projectTask);
//			backlogRepository.save(backlog);
			return projectTaskRepository.save(projectTask);

		} catch (Exception ex) {
			throw new ProjectNotFoundException("project not found");
		}

	}

	public Iterable<ProjectTask> findBacklogById(String id) {
		Project project = projectRepository.findByProjectIdentifier(id);
		if (project == null) {
			throw new ProjectNotFoundException("Project not found");
		}
		return projectTaskRepository.findByProjectIdentiferOrderByPriority(id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		// make sure that backlog id exist
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if (backlog == null) {
			throw new ProjectNotFoundException("Project with id : '" + backlog_id + "' does not exist");
		}
		// make sure that project task id exist

		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project task with id : '" + pt_id + "' does not exist");
		}

		// make sure that backlog id and project identifier is same
		if (!projectTask.getProjectIdentifer().equals(backlog_id)) {
			throw new ProjectNotFoundException("Backlog Id: '" + backlog_id
					+ "' does not match with project identifier '" + projectTask.getProjectIdentifer() + "'");

		}
		return projectTask;
	}

	public ProjectTask updateByProjectSequence(ProjectTask updateTask,String backlog_id, String pt_id) {
		//find the existing project task and its validation
		ProjectTask projectTask =findPTByProjectSequence(backlog_id, pt_id);
		//replace project task with updated
		projectTask = updateTask;
		
		//save the project task
		
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id,String pt_id) {
		// find the project task
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
		Backlog backlog =projectTask.getBacklog();
		
		List<ProjectTask> pts =backlog.getProjectTasks();
		pts.remove(projectTask);
		backlogRepository.save(backlog);
		projectTaskRepository.delete(projectTask);
	}
}
