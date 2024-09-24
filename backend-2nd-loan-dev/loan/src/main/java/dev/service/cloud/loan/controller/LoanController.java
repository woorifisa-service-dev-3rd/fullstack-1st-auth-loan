package dev.service.cloud.loan.controller;

import dev.service.cloud.loan.dto.request.LoanRequestDto;
import dev.service.cloud.loan.dto.response.LoanResponseDto;
import dev.service.cloud.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @GetMapping
    public ResponseEntity<List<LoanResponseDto>> findAllLoans() {
        List<LoanResponseDto> allLoans = loanService.findAllLoans();
        return ResponseEntity.ok(allLoans);
    }

    @PostMapping
    public ResponseEntity<LoanResponseDto> addNewLoan(@RequestBody LoanRequestDto loanRequestDto) {
        LoanResponseDto loanResponseDto = loanService.addNewLoan(loanRequestDto);
        return ResponseEntity.ok(loanResponseDto);
    }

    @PatchMapping("/{loanId}")
    public ResponseEntity<LoanResponseDto> repay(@PathVariable Long loanId) {
        LoanResponseDto loanResponseDto = loanService.repay(loanId);
        return ResponseEntity.ok(loanResponseDto);
    }
}
