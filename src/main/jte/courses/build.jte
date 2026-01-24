@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.courses.BuildCoursePage
@param BuildCoursePage page

@template.layout.page(
page = page,
content = @`
    <form action="${NamedRoutes.coursesPath()}" method="post">
        <div>
            <label>
                Name
                <input type="text" name="name" value="${page.getName()}" />
            </label>
        </div>
        <div>
            <label>
                Description
                <input type="text" required name="description" value="${page.getDescription()}" />
            </label>
        </div>
        <input type="submit" value="Add course" />
    </form>
`
)
