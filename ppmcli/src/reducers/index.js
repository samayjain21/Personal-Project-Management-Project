import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import ProjectReducer from "./ProjectReducer";

export default combineReducers({
  errors: errorReducer,
  projects: ProjectReducer
});