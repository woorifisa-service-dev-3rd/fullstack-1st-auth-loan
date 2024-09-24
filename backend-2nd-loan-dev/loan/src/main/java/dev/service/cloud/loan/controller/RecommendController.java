package dev.service.cloud.loan.controller;


import dev.service.cloud.loan.dto.response.LoanProductResponseDto;
import dev.service.cloud.loan.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan-products")
@RequiredArgsConstructor
@Slf4j
public class RecommendController {
    private final RecommendService recommendService;


    @GetMapping("/recommend")
    public ResponseEntity<?> recommendByPointLoanProducts(@RequestParam int point) {
        List<LoanProductResponseDto> recommendedProducts = recommendService.recommendByPoint(point);
        return ResponseEntity.ok(recommendedProducts);
    }


    @GetMapping("/recommend/members/{memberId}")
    public ResponseEntity<List<LoanProductResponseDto>> getRecommendforMember(@PathVariable Long memberId) {
        List<LoanProductResponseDto> recommendations = recommendService.recommendLoanProductsforMember(memberId);
        return ResponseEntity.ok(recommendations);
    }


}
