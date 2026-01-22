@import org.example.hexlet.NamedRoutes

<div>
    <a href="/">Home</a>
    <a href="${NamedRoutes.usersPath()}">Users</a>
    <a href="${NamedRoutes.buildUserPath()}">Create user</a>
    <a href="${NamedRoutes.coursesPath()}">Courses</a>
    <a href="${NamedRoutes.buildCoursePath()}">Create course</a>
</div>
