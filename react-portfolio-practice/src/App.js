import React from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import ProjectsPage from "./ProjectsPage";
import AboutMePage from "./AboutMePage";

function App() {
  return (
    <div>
      <Router>
        <Routes>
          <Route exact path="/" element={<ProjectsPage />} />
          <Route path="/aboutme" element={<AboutMePage />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
