package ma.formations.soap.dtos.user;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDto {
    protected String username;
    protected String firstname;
    protected String lastname;
}
