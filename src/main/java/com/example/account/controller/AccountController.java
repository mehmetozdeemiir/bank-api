package com.example.account.controller;
import com.example.account.dto.AccountDto;
import com.example.account.dto.CreateAccountRequest;
import com.example.account.dto.MoneyTransferRequest;
import com.example.account.dto.UpdateAccountRequest;
import com.example.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping
    public ResponseEntity<Object> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return ResponseEntity.ok(accountService.createAccount(createAccountRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody UpdateAccountRequest updateAccountRequest) {
        return ResponseEntity.ok(accountService.updateAccount(id, updateAccountRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/withdraw/{id}/{amount}")
    public ResponseEntity<AccountDto> withdrawMoney(@PathVariable Long id, @PathVariable Long amount) {
        return ResponseEntity.ok(accountService.withdrawMoney(id, amount));
    }

    @PutMapping("/addMoney/{id}/{amount}")
    public ResponseEntity<AccountDto> addMoney(@PathVariable Long id, @PathVariable Long amount) {
        return ResponseEntity.ok(accountService.addMoney(id, amount));
    }

    @PutMapping("/transfer")
    public ResponseEntity<Void> transferMoney(@Valid @RequestBody MoneyTransferRequest transferRequest) {
        accountService.transferMoney(transferRequest);
        return ResponseEntity.ok().build();
    }

}