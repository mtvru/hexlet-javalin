@import org.example.hexlet.dto.MainPage
@param MainPage page

@template.layout.page(
content = @`
    <div class="col-lg-8 mx-auto p-4 py-md-5">
        <main>
            <h1 class="text-body-emphasis">Hello, Hexlet!</h1>
            <p>Javalin + jte</p>
            @if(!page.isVisited())
                This message is shown only once. If you want to see it again, clear your cookies.
            @endif
            @if(page.getCurrentUser() != null)
                Welcome, ${page.getCurrentUser()}.
                To log out, delete the JSESSIONID cookie from your browser.
            @endif
        </main>
    </div>
`
)
