
package com.sbyte.shield.view;

import com.sbyte.shield.modals.AuthenticatorModal;
import com.sbyte.shield.modals.RegistrationModal;
import com.sbyte.shield.modals.ShieldResponse;

public interface ShieldView {

    ShieldResponse login(AuthenticatorModal loginModal);

    ShieldResponse logout(AuthenticatorModal logoutModal);

    ShieldResponse register(RegistrationModal registerModal);
}
