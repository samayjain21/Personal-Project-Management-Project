package in.blog.ppmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.blog.ppmapi.domain.Backlog;
import in.blog.ppmapi.domain.Project;
import in.blog.ppmapi.exception.ProjectIdException;
import in.blog.ppmapi.repository.BacklogRepository;
import in.blog.ppmapi.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrUpdate(Project project)
	{
		try {
			
			//while savingthe project backlog should be saved
			// when rpject is not having id it is new  you need to create backlog as well
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
				
				
			}
			//while updating the project backlog should be set as it is and not null
			if(project.getId()!=null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
		project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
		return projectRepository.save(project);
		}
		catch (Exception e) {
			throw new ProjectIdException("ProjectId Already Exists");
		}
	}

	public Project findProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Project ID '" + projectId + "' does not exists");
		}
		return project;
	}
	
	public Iterable<Project> findAllProject(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectByProjectIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId);
		if (project == null) {
			throw new ProjectIdException("Cannot delete project with id '"+projectId+"'. This project dies not exists. "); 
		}
		
		projectRepository.delete(project);
	}
}
