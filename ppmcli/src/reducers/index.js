import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import ProjectReducer from "./ProjectReducer";
import BacklogReducer from "./BacklogReducer";

export default combineReducers({
  errors: errorReducer,
  projects: ProjectReducer,
  backlog:BacklogReducer
});