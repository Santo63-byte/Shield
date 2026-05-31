package com.sbyte.shield.modals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponseModal extends ShieldResponse {

    @JsonProperty("user_profile")
    private UserProfileModal userProfile;


}
