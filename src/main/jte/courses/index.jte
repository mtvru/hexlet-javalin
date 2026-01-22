@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.courses.CoursesPage
@import org.example.hexlet.model.Course
@param CoursesPage page

@template.layout.page(
content = @`
    <h1>${page.getHeader()}</h1>
    <form action="${NamedRoutes.coursesPath()}" method="get">
        <input type="search" name="term" value="${page.getTerm()}">
        <input type="submit" value="Search">
    </form>
    @if(page.getCourses().isEmpty())
        <p>No courses have been added yet.</p>
    @else
        @for(Course course : page.getCourses())
            <div>
                <h2><a href="${NamedRoutes.coursePath(course.getId())}">${course.getName()}</a></h2>
                <p>${course.getDescription()}</p>
            </div>
        @endfor
    @endif
`
)
