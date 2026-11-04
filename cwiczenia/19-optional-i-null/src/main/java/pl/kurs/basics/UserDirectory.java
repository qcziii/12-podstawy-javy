package pl.kurs.basics;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * User directory for the profile page.
 */
public class UserDirectory {

    private final Map<String, UserAccount> users = new HashMap<>();
    private final Map<String, String> avatars = new HashMap<>();
    private final AvatarStore avatarStore;

    public UserDirectory(AvatarStore avatarStore) {
        this.avatarStore = avatarStore;
    }

    public void add(UserAccount user) {
        users.put(user.login(), user);
    }

    public void setAvatar(String login, String avatarFile) {
        avatars.put(login, avatarFile);
    }

    /** The user with this login, if there is one. */
    public Optional<UserAccount> find(String login) {
        UserAccount user = users.get(login);
        if (user == null) {
            return null;
        }
        return Optional.of(user);
    }

    /** Name shown on the page: nickname, and if there is none, login. Unknown user -> "Nieznany użytkownik". */
    public String displayName(String login) {
        UserAccount user = find(login).get();
        return Optional.of(user.nickname()).orElse(user.login());
    }

    /** E-mail domain, e.g. "ala@example.com" -> "example.com". No user or no e-mail -> empty Optional. */
    public Optional<String> emailDomain(String login) {
        return find(login)
                .map(UserAccount::email)
                .map(email -> email.substring(email.indexOf('@') + 1));
    }

    /** Avatar file of the user; when the user has none, the default avatar from AvatarStore. */
    public String avatar(String login) {
        return Optional.ofNullable(avatars.get(login))
                .orElse(avatarStore.loadDefaultAvatar());
    }
}
