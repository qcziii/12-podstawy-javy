package pl.kurs.basics;

/**
 * Decides who can edit a document: its owner or an administrator.
 */
public class AccessChecker {

    public static final String ADMIN = "ADMIN";

    public boolean isOwner(Document document, User user) {
        return document.ownerId() == user.id();
    }

    public boolean isAdmin(User user) {
        return user.role() == ADMIN;
    }

    public boolean canEdit(Document document, User user) {
        return isOwner(document, user) || isAdmin(user);
    }
}
