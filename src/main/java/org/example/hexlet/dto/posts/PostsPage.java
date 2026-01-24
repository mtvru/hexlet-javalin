package org.example.hexlet.dto.posts;

import java.util.List;

import lombok.AccessLevel;
import org.example.hexlet.dto.BasePage;
import org.example.hexlet.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostsPage extends BasePage {
    private List<Post> posts;
    private Integer pageNumber;
    @Getter(AccessLevel.NONE)
    private boolean hasNext;

    public boolean hasNext() {
        return hasNext;
    }

    public boolean hasPrev() {
        return pageNumber > 1;
    }
}
