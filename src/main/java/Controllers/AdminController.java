package Controllers;

import Entity.User;
import Service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String adminPage(Model model, @RequestParam(defaultValue = "0") int pageNumber,
                            @RequestParam(defaultValue = "10") int pageSize) {
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        Page<User> userPageDisplay = adminService.viewAllUsers(pageNumber, pageSize);
        model.addAttribute("users", userPageDisplay);
        model.addAttribute("pageNumber", pageNumber + 1);
        model.addAttribute("totalPages", userPageDisplay.getTotalPages());
        model.addAttribute("totalUsers", userPageDisplay.getTotalElements());
        return "admin";
    }

    @PostMapping("/onboarding")
    public String onboardingNewUser() {}

    @PostMapping("/accountCreation")
    public String createAccount() {}

    @PostMapping("/issueCard")
    public String issueCard() {}

    @PostMapping("/freezeAccount")
    public String freezeUserAccount() {}

    @PostMapping("/unfreezeAccount")
    public String unfreezeUserAccount() {}
}
