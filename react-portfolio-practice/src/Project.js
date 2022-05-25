function Project(props) {
    const {name, githubLink, imagePath, description} = props
    return (
        <div class="single-project">
          <h3>{name}</h3>
          <div class="img-container">
            <a href={githubLink}>
              <img src={imagePath} alt=""/>
            </a>
          </div>
          <div class="description">
            <p>Description:</p>
            <p>{description}</p>
          </div>
        </div>
    )
}

export default Project;