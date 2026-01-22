@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

@template.layout.page(
content = @`
    <form action="${NamedRoutes.coursePath(page.getCourse().getId()}" method="patch">
        <div>
            <label>
                Name
                <input type="text" name="name" value="${page.getCourse().getName()}" />
            </label>
        </div>
        <div>
            <label>
                Description
                <input type="email" required name="email" value="${page.getCourse().getDescription()}" />
            </label>
        </div>
        <input type="submit" value="Update user" />
    </form>
`
)
