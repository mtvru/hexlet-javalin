@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.posts.PostPage
@param PostPage page

@template.layout.page(
content = @`
    <main>
        <h1>${page.getPost().getId()}</h1>
        <p>${page.getPost().getName()}</p>
        <p>${page.getPost().getBody()}</p>
        <a href="${NamedRoutes.editPostPath(page.getPost().getId())}">Edit</a>
    </main>
`
)
