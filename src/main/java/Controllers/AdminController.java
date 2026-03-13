package Controllers;

import DataTransferObjects.AccountCreatedDTO;
import DataTransferObjects.CardCreatedDTO;
import DataTransferObjects.UserCreatedDTO;
import Entity.User;
import Service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

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
    public String onboardingNewUser(@RequestParam String firstName, @RequestParam String lastName,
                                    @RequestParam String username, @RequestParam String email,
                                    @RequestParam BigDecimal balance, @RequestParam String accountType,
                                    @RequestParam String cardPin, RedirectAttributes redirectAttributes) {
        if (!accountType.toLowerCase().equals("checking") && !accountType.toLowerCase().equals("savings")){
            accountType = "checking";
        }
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            redirectAttributes.addFlashAttribute("error", "Invalid balance");
            return "redirect:/admin";
        }
        try {
            UserCreatedDTO createdDTO = adminService.onBoarding(firstName, lastName, username, email,
                    balance, accountType, cardPin);
            redirectAttributes.addFlashAttribute("success", createdDTO.toString());
            return "redirect:/admin";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/accountCreation")
    public String createAccount(@RequestParam String username, @RequestParam BigDecimal runningBalance,
                                @RequestParam String accountType, RedirectAttributes redirectAttributes) {
        if (!accountType.toLowerCase().equals("checking") && !accountType.toLowerCase().equals("savings")){
            redirectAttributes.addFlashAttribute("error", "Invalid account");
            return "redirect:/admin";
        }
        if (runningBalance.compareTo(BigDecimal.ZERO) < 0) {
            redirectAttributes.addFlashAttribute("error", "Invalid balance");
            return "redirect:/admin";
        }
        try {
            AccountCreatedDTO accountCreatedDTO = adminService.newAccountForUser(username, runningBalance, accountType);
            redirectAttributes.addFlashAttribute("success", accountCreatedDTO.toString());
            return "redirect:/admin";
        } catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        } catch (NoSuchElementException ne){
            redirectAttributes.addFlashAttribute("error", ne.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/issueCard")
    public String issueCard(@RequestParam UUID accountNumber, @RequestParam String cardPin,
                            RedirectAttributes redirectAttributes) {
        try {
            CardCreatedDTO cardCreatedDTO = adminService.newCardForAccount(accountNumber, cardPin);
            redirectAttributes.addFlashAttribute("success", cardCreatedDTO.toString());
            return "redirect:/admin";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/freezeAccount")
    public String freezeUserAccount(@RequestParam UUID accountNumber, RedirectAttributes redirectAttributes) {
        try {
            adminService.freezeAccount(accountNumber);
            redirectAttributes.addFlashAttribute("success", "Account frozen");
            return "redirect:/admin";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/unfreezeAccount")
    public String unfreezeUserAccount(@RequestParam UUID accountNumber, RedirectAttributes redirectAttributes) {
        try {
            adminService.unfreezeAccount(accountNumber);
            redirectAttributes.addFlashAttribute("success", "Account unfreezed");
            return "redirect:/admin";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }
    }
}
