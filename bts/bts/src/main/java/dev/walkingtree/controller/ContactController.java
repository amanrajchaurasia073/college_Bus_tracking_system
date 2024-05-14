package dev.walkingtree.controller;

import dev.walkingtree.dto.ContactDto;
import dev.walkingtree.service.mailSender.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @PostMapping("/processcontact")
    public String processContact(@ModelAttribute("contactDto") ContactDto contactDto) {

        String userEmail = contactDto.getEmail();
        String message = contactDto.getMessage();
        String username = contactDto.getName();

        String queryMessage = """
                Hello my name  is %s and my email is %s
                and this is my query-> %s
                """.formatted(username, userEmail, message);

        // send our query message to the owner mail id
        contactService.contactus(queryMessage);

        return "contactsucess";

    }

}
