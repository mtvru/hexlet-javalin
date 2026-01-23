@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

@template.layout.page(
content = @`
    <main>
        <h1>${page.getCourse().getId()}</h1>
        <p>${page.getCourse().getName()}</p>
        <p>${page.getCourse().getDescription()}</p>
    </main>
`
)
