@import org.example.hexlet.dto.users.UserPage
@param UserPage page

@template.layout.page(
content = @`
    <main>
        <h1>${page.getUser().getId()}</h1>
        <p>${page.getUser().getName()}</p>
        <p>${page.getUser().getEmail()}</p>
        <p>${page.getUser().getCreatedAt().toString()}</p>
    </main>
`
)
