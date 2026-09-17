package mk.finki.nutritioneer.domain;


public enum UserRole {
    user,
    administrator,
    trainer;

    public String authority() {
        return "ROLE_" + name().toUpperCase();
    }
}
