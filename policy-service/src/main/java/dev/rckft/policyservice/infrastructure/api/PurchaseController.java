package dev.rckft.policyservice.infrastructure.api;

import dev.rckft.policyservice.application.CreatePolicyUseCase;
import dev.rckft.policyservice.domain.policy.Policy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    private final CreatePolicyUseCase createPolicyUseCase;

    public PurchaseController(CreatePolicyUseCase createPolicyUseCase) {
        this.createPolicyUseCase = createPolicyUseCase;
    }

    @PostMapping("/policies")
    public ResponseEntity<Long> purchasePolicy(@RequestBody CreatePolicyRequest request) {
        Policy policy = createPolicyUseCase.createPolicy(request);
        return ResponseEntity.ok(policy.getId());
    }


}
