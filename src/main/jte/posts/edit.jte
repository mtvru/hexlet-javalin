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
    <form action="${NamedRoutes.editPostPath(page.getId())}" method="post">
        <div>
            <label>
                Name
                <input type="text" name="name" value="${page.getName()}" />
            </label>
        </div>
        <div>
            <label>
                Email
                <input type="text" required name="body" value="${page.getBody()}" />
            </label>
        </div>
        <div>
            <label>
                Password
                <input type="password" name="password" />
            </label>
        </div>
        <div>
            <label>
                Confirm password
                <input type="password" name="passwordConfirmation" />
            </label>
        </div>
        <input type="submit" value="Update user" />
    </form>
`
)
