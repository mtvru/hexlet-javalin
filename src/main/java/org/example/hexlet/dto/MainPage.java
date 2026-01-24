package org.example.hexlet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MainPage extends BasePage {
    private String currentUser;
    private Boolean visited;

    public Boolean isVisited() {
        return visited;
    }
}
