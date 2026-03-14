package Controllers;

import DataTransferObjects.LoggedInAccountInfo;
import Entity.Card;
import Service.AccountService;
import Service.CardDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/atm")
public class AtmController {

    private final AccountService accountService;

    public AtmController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String userAtmDashboard(@AuthenticationPrincipal CardDetail cardDetail, Model model) {
        Card currentCard = cardDetail.getCard();
        LoggedInAccountInfo basicInfo = accountService.accountInfo(currentCard.getCardNumber());
        model.addAttribute("basicInfo", basicInfo);
        return "atm";
    }

    @PostMapping("/deposit")
    public String deposit(@AuthenticationPrincipal CardDetail cardDetail, @RequestParam BigDecimal amount,
                          RedirectAttributes redirectAttributes) {
        Card currentCard = cardDetail.getCard();
        try {
            accountService.deposit(currentCard.getCardNumber(), amount);
            redirectAttributes.addFlashAttribute("success", "Deposit Complete!");
            return "redirect:/atm";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/atm";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/atm";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/atm";
        }
    }

    @PostMapping("/withdraw")
    public String withdraw(@AuthenticationPrincipal CardDetail cardDetail, @RequestParam BigDecimal amount ,
                           RedirectAttributes redirectAttributes) {
        Card currentCard = cardDetail.getCard();
        try {
            accountService.withdraw(currentCard.getCardNumber(), amount);
            redirectAttributes.addFlashAttribute("success", "Withdrawal Complete!");
            return "redirect:/atm";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/atm";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/atm";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/atm";
        }
    }
}
