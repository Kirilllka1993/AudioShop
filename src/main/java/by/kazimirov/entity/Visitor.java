package by.kazimirov.entity;

import java.util.Locale;
import java.util.Objects;

/**
 *
 */
public class Visitor {
    public enum Role {
        ADMIN, USER, GUEST
    }

    private Role role;
    private Locale locale;
    private String currentPage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return role == visitor.role &&
                Objects.equals(locale, visitor.locale) &&
                Objects.equals(currentPage, visitor.currentPage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, locale, currentPage);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Visitor{");
        sb.append("role=").append(role);
        sb.append(", locale=").append(locale);
        sb.append(", currentPage='").append(currentPage).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
