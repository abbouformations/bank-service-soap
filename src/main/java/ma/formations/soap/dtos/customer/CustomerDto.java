package ma.formations.soap.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class CustomerDto {
    private String username;
    private String identityRef;
    private String firstname;
    private String lastname;
 //  private List<BankAccountDto> bankAccounts;
}
