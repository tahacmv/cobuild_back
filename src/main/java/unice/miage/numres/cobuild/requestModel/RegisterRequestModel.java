package unice.miage.numres.cobuild.requestModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestModel {

    String username;
    String password;
    String email;
    String roleName;
}
