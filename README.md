# Keycloak Unique Attribute Validator Provider

This repository contains the Keycloak Unique Attribute Validator Provider, designed to enhance your Keycloak instance by enabling the validation of unique attributes for user profiles. Below are the steps to set up and use this provider in your Keycloak environment.

## Prerequisites

Before you begin, ensure that the `User Profile` feature is enabled in Keycloak. By default, this feature is not active. To enable it, you must start Keycloak with a specific feature flag.

## Enabling User Profile in Keycloak

To enable the `User Profile` feature:

1. Run Keycloak with the `--features=declarative-user-profile` flag. This setting is already included in the `docker-compose` file provided with this repository.
2. Start the project using the following command:

```bash
docker compose up -d
```

3. Once the Keycloak instance is up and running, navigate to the admin console.

### Accessing the Admin Console

1. Go to the Keycloak admin console.
2. Log in using the credentials:
- **Username:** admin
- **Password:** admin
3. Select your realm.

### Enabling User Profile

1. In the realm, navigate to `Realm Settings -> General`.
2. Enable `User Profile Enabled`.

## Configuring the Unique Attribute Validator

After enabling the User Profile feature:

1. Go to `Realm Settings -> User Profile`.
2. Add your attribute to the profile.
3. Add the validator named `unique-attribute`.

Once these steps are completed, your Keycloak instance will be configured to validate unique attributes in user profiles, using the Unique Attribute Validator Provider.

## Support

If you encounter any issues or have questions, please file an issue in this repository's issue tracker.
