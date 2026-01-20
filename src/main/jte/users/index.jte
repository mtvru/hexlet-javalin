@import org.example.hexlet.dto.users.UsersPage
@param UsersPage page

@template.layout.page(
content = @`
    @for(var user : page.getUsers())
        <div>${user.getName()}</div>
    @endfor
`
)
