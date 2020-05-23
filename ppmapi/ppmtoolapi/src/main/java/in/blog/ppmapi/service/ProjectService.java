package in.blog.ppmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.blog.ppmapi.domain.Project;
import in.blog.ppmapi.exception.ProjectIdException;
import in.blog.ppmapi.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdate(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);

		} catch (Exception e) {
			throw new ProjectIdException("Project ID '" + project.getProjectIdentifier() + "' already exists");
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
