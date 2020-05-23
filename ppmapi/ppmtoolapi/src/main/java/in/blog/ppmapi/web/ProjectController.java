package in.blog.ppmapi.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.blog.ppmapi.domain.Project;
import in.blog.ppmapi.service.MapValidationErrorService;
import in.blog.ppmapi.service.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	@PostMapping("")
	public ResponseEntity<?> saveProject (@Valid @RequestBody Project project,BindingResult result){

		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result);
		if(errorMap != null) {
			return errorMap;
		}
		
		Project proj =projectService.saveOrUpdate(project);
		return new ResponseEntity<Project>(proj, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectByProjectIdentifier(@PathVariable String projectId){
		Project project =projectService.findProjectByIdentifier(projectId);
		return new ResponseEntity<Project> (project,HttpStatus.OK );
	}
	
	@GetMapping("/all")
	public Iterable<Project> getAllProjects(){
		return projectService.findAllProject();
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
		projectService.deleteProjectByProjectIdentifier(projectId);
		return new ResponseEntity<String> ("Project with Id '"+projectId+"' was deleted", HttpStatus.OK);
	}
}
