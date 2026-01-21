@import org.example.hexlet.dto.users.UsersPage
@param UsersPage page

@template.layout.page(
content = @`
    @for(var user : page.getUsers())
        <h2><a href="/users/${user.getId()}">${user.getName()}</a></h2>
        <div>${user.getEmail()}</div>
        <div>${user.getCreatedAt().toString()}</div>
    @endfor
`
)
