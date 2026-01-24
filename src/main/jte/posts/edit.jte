@import io.javalin.validation.ValidationError
@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.posts.EditPostPage
@import java.util.List
@param EditPostPage page

@template.layout.page(
content = @`
    @if(page.getErrors() != null)
        <ul>
            @for(List<ValidationError<Object>> validator : page.getErrors().values())
                @for(ValidationError error : validator)
                    <li>${error.getMessage()}</li>
                @endfor
            @endfor
        </ul>
    @endif
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
