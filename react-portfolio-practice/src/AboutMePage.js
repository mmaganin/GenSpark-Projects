import React from 'react';
import { useNavigate } from 'react-router-dom';
import ProjectsPage from "./ProjectsPage";

function AboutMePage() {
    const navigate = useNavigate();
    function handleAboutMe() {
        navigate("/")
    }
    return (
        <div class="page-container">
            <h2>About Me</h2>
            <div>
                <p><strong>Position</strong>: Java Full Stack Developer</p>
                <p><strong>Current Employer</strong>: GenSpark, contracted out to Tech Mahindra in Plano, Tx</p>
                <p><strong>Skills</strong>: Spring Boot, Hibernate, MySQL, React, AWS, Java, JavaScript, HTML, CSS</p>
                <p><strong>Education</strong>: Purdue University B.S. in Computer Science with a concentration in Software Engineering</p>
                <p><strong>Some Hobbies</strong>: running, skiing, travelling, meditation, cryptocurrency, and gaming</p>
            </div>
            <h2>Links</h2>
            <div class="links-container">
                <div class="link-container">
                    <a href="https://github.com/mmaganin">Github</a>
                </div>
                <div class="link-container">
                    <a href="https://www.linkedin.com/in/michael-maganini/">LinkedIn</a>
                </div>
            </div>
            <br/>
            <button onClick={handleAboutMe}>Projects</button>
        </div>
    );
}

export default AboutMePage;
