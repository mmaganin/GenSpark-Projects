import React from 'react';
import { useNavigate } from 'react-router-dom';
import AboutMePage from "./AboutMePage";
import Project from './Project';

const projectsProps = {
    cryptoApp: {
        name: "Crypto Full Stack App",
        githubLink: "https://github.com/mmaganin/crypto-fullstack-app",
        imagePath: "images/placeholder.png",
        description: "React, spring boot, MySQL web application that allows users to view cryptocurrency market data and create a portfolio. Uses JWT and custom Spring Security configuration for user authentication and authorization."
    },
    socialMediaApp: {
        name: "Purdue Party Team Project Full Stack App",
        githubLink: "https://github.com/mmaganin/PurdueParty.io-1",
        imagePath: "images/placeholder.png",
        description: "React, Redux, Firebase, web application for Purdue Students. Users can create an account and do things like post on forums, and buy and sell on a student marketplace. Uses Firebase auth for authentication and authorization."
    },
    mediaStreamingApp: {
        name: "Media Streaming Availability Genspark Team Project",
        githubLink: "https://github.com/mmaganin/fullstack-team-project",
        imagePath: "images/placeholder.png",
        description: "React, spring boot, MySQL web application that allows users to search for movies or shows to see if they are available on different streaming services in different countries. Users can add media to a personal must-watch list. Uses JWT and custom Spring Security configuration for user authentication and authorization."
    },
}

function ProjectsPage() {
    const navigate = useNavigate();
    function handleAboutMe() {
        navigate("/aboutme")
    }

    return (
        <div class="page-container">
            <h1>Michael Maganini's Developer Portfolio</h1>
            <h2>Projects</h2>
            <div class="projects-container">
                <Project {...projectsProps.cryptoApp} />
                <Project {...projectsProps.socialMediaApp} />
                <Project {...projectsProps.mediaStreamingApp} />
                <div class="single-project">
                    <h3>Find my other projects through the GitHub Link in the About Me Page</h3>
                </div>
            </div>
            <br/>
            <button onClick={handleAboutMe}>About Me</button>
        </div>
    );
}

export default ProjectsPage;
