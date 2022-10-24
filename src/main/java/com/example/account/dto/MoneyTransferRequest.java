package com.example.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoneyTransferRequest {
    private Long fromId;
    private Long toId;
    private Long amount;
    @Pattern(regexp = "TR[a-zA-Z0-9]{2}\\s?([0-9]{4}\\s?){1}([0-9]{1})([a-zA-Z0-9]{3}\\s?)([a-zA-Z0-9]{4}\\s?){3}([a-zA-Z0-9]{2})\\s?")
    private String iban;


}
