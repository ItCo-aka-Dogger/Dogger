package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dogger.dto.ResponseDto;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.services.UsersService;

@RestController
public class DeleteUserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/delete")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(new ResponseDto(usersService.delete(userId)));
    }
}
