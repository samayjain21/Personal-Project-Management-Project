import React from 'react';
import './App.css';
import Dashboard from './components/Dashboard';
import AddProject from './components/projects/AddProject';
import Header from './components/layout/Header';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import {Provider} from "react-redux";
import store from './store';
import UpdateProject from "./components/projects/UpdateProject";
function App() {
  return (
    <Provider store={store}>
      <Router>
      <div >
        <Header/>
      </div>
      <Route path="/dashboard" component={Dashboard}/>
      <Route path="/addProject" component={AddProject}/>
      <Route path="/updateProject/:id" component={UpdateProject}></Route>
      </Router>
      </Provider>
  );
}

export default App;
