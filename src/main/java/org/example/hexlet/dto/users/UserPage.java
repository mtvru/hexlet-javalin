package org.example.hexlet.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hexlet.dto.BasePage;
import org.example.hexlet.model.User;

@AllArgsConstructor
@Getter
public class UserPage extends BasePage {
    private User user;
}
