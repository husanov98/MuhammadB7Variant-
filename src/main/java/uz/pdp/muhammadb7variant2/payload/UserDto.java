package uz.pdp.muhammadb7variant2.payload;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    @Size(min = 3,max = 50)
    private String firstName;

    @Length(min = 3,max = 50)
    private String lastName;

    @Email
    private String email;

    @NotNull
    private String password;
}
