@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.posts.EditPostPage
@param EditPostPage page

@template.layout.page(
page = page,
content = @`
    <form action="${NamedRoutes.postPath(page.getId())}" method="post">
        <div>
            <label>
                Name
                <input type="text" name="name" value="${page.getName()}" />
            </label>
        </div>
        <div>
            <label>
                Body
                <input type="text" name="body" value="${page.getBody()}" />
            </label>
        </div>
        <input type="submit" value="Update" />
    </form>
`
)
