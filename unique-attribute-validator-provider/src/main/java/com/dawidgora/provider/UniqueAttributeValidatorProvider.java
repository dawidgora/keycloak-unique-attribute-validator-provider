package com.dawidgora.provider;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserProvider;
import org.keycloak.provider.ConfiguredProvider;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.validate.AbstractStringValidator;
import org.keycloak.validate.ValidationContext;
import org.keycloak.validate.ValidationError;
import org.keycloak.validate.ValidatorConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class UniqueAttributeValidatorProvider extends AbstractStringValidator implements ConfiguredProvider {
    public static final String ID = "unique-attribute";
    public static final String MESSAGE_ATTRIBUTE_NOT_UNIQUE = "error.duplicated.attribute";
    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

    public UniqueAttributeValidatorProvider() {
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    protected void doValidate(String attributeValue, String attributeName, ValidationContext context, ValidatorConfig config) {
        KeycloakSession session = context.getSession();

        UserModel currentUser = (UserModel) context.getAttributes().get(UserModel.class.getName());

        if (!isAttributeUnique(attributeValue, attributeName, session, currentUser)) {
            context.addError(new ValidationError(ID, attributeName, MESSAGE_ATTRIBUTE_NOT_UNIQUE + '.' + attributeName));
        }
    }

    public boolean isAttributeUnique(String attributeValue, String attributeName, KeycloakSession session, UserModel currentUser) {
        UserProvider userProvider = session.getProvider(UserProvider.class);

        RealmModel realm = this.getRealm(session);

        Stream<UserModel> usersWithSameAttributeValue = userProvider.searchForUserByUserAttributeStream(realm, attributeName, attributeValue);

        return currentUser != null
            ? usersWithSameAttributeValue.filter(user -> !user.getId().equals(currentUser.getId())).findAny().isEmpty()
            : usersWithSameAttributeValue.findAny().isEmpty();
    }

    @Override
    public String getHelpText() {
        return "Unique Attribute";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    RealmModel getRealm(KeycloakSession session) {
        if (session.getContext() == null) {
            return null;
        }
        if (session.getContext().getRealm() != null) {
            return session.getContext().getRealm();
        }
        if (session.getContext().getAuthenticationSession() != null
                && session.getContext().getAuthenticationSession().getRealm() != null) {
            return session.getContext().getAuthenticationSession().getRealm();
        }
        if (session.getContext().getClient() != null
                && session.getContext().getClient().getRealm() != null) {
            return session.getContext().getClient().getRealm();
        }
        return null;
    }
}
