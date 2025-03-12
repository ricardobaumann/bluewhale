package com.github.ricbau.bluewhale.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidResourceReference extends RuntimeException {
    private final String resourceName;
    private final UUID id;

    @Override
    public String getMessage() {
        return String.format("The %s with ID %s does not exists", resourceName, id);
    }
}
