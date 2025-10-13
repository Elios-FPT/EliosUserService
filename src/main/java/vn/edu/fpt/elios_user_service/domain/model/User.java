package vn.edu.fpt.elios_user_service.domain.model;

import vn.edu.fpt.elios_user_service.enums.Gender;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public final class User extends BaseEntity<UUID> {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String avatarUrl;
    private String avatarPrefix;
    private String avatarFileName;
    private Instant createdAt;
    private Instant updatedAt;

    private User(UUID id) {
        super(id);
    }

    public static User create(UUID id, String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        User u = new User(id);
        u.firstName = validateString("firstName", firstName, 20, true);
        u.lastName  = validateString("lastName",  lastName,  20, true);
        u.gender = gender;
        u.dateOfBirth = dateOfBirth;
        u.createdAt = Instant.now();
        u.updatedAt = u.createdAt;
        return u;
    }

    public void rename(String firstName, String lastName) {
        this.firstName = validateString("firstName", firstName, 20, true);
        this.lastName  = validateString("lastName",  lastName,  20, true);
        touch();
    }

    public void updateDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = validateDob(dateOfBirth);
        touch();
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
        touch();
    }

    public void updateAvatar(String avatarUrl, String avatarPrefix, String avatarFileName) {
        this.avatarUrl = validateString("avatarUrl",  avatarUrl,  1024, false);
        this.avatarPrefix = validateString("avatarPrefix",  avatarPrefix,  128, false);
        this.avatarFileName = validateString("avatarFileName",  avatarFileName,  80, false);
        touch();
    }

    public void clearAvatar() {
        this.avatarUrl = null;
        this.avatarPrefix = null;
        this.avatarFileName = null;
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    // === Validation helpers ===

    private static String validateString(String field, String value, int maxLen, boolean required) {
        if (value == null) {
            if (required) {
                throw new IllegalArgumentException(field + " is required");
            }

            return null;
        }

        String v = value.trim();
        if (required && v.isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        if (v.length() > maxLen) {
            throw new IllegalArgumentException(field + " must be <= " + maxLen + " characters");
        }

        return v;
    }

    private static LocalDate validateDob(LocalDate dob) {
        if (dob == null) return null;
        if (dob.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("dateOfBirth must not be in the future");
        }
        return dob;
    }

    // Equality by id
    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof User u)) {
            return false;
        }

        return Objects.equals(getId(), u.getId());
    }

    @Override public int hashCode() {
        return Objects.hash(getId());
    }
}

