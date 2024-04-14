package hh.sof03.musicplaylist.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.sof03.musicplaylist.domain.User;
import hh.sof03.musicplaylist.domain.UserRepository;

@Controller
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public @ResponseBody List<User> getUserListRest() {
        return (List<User>)userRepository.findAll();
    }
}
