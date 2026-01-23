@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.posts.PostsPage
@import org.example.hexlet.model.Post
@param PostsPage page

@template.layout.page(
content = @`
    @if(page.getPosts().isEmpty())
        <p>No posts have been added yet.</p>
    @else
        @for(Post post : page.getPosts())
            <div>
                <h2><a href="${NamedRoutes.postPath(post.getId())}">${post.getName()}</a></h2>
                <p>${post.getBody()}</p>
            </div>
        @endfor
    @endif
    @if(page.hasPrev())
        <a href="${NamedRoutes.postsPath()  + "?page=" + (page.getPageNumber() - 1)}">prev</a>
    @endif
    @if(page.hasNext())
        <a href="${NamedRoutes.postsPath() + "?page=" + (page.getPageNumber() + 1)}">next</a>
    @endif
`
)
