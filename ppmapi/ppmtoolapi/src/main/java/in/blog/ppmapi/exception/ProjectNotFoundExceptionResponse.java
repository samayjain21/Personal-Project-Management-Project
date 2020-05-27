package in.blog.ppmapi.exception;

public class ProjectNotFoundExceptionResponse {

	private String projectNotFound;
	

	public ProjectNotFoundExceptionResponse(String projectNotFound) {
		super();
		this.projectNotFound = projectNotFound;
	}

	public String getProjectNotFound() {
		return projectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		this.projectNotFound = projectNotFound;
	}
	
}
