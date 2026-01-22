@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.users.UserPage
@param UserPage page

@template.layout.page(
content = @`
    <form action="${NamedRoutes.userPath(page.getUser().getId()}" method="patch">
        <div>
            <label>
                Name
                <input type="text" name="name" value="${page.getUser().getName()}" />
            </label>
        </div>
        <div>
            <label>
                Email
                <input type="email" required name="email" value="${page.getUser().getEmail()}" />
            </label>
        </div>
        <div>
            <label>
                Password
                <input type="password" required name="password" value="${page.getUser().getPassword()}" />
            </label>
        </div>
        <div>
            <label>
                Confirm password
                <input type="password" required name="passwordConfirmation" />
            </label>
        </div>
        <input type="submit" value="Update user" />
    </form>
`
)
