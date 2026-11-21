package com.larpclient.module;
public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    VISUALS("Visuals"),
    COSMETICS("Cosmetics"),
    BASE_FINDING("Base Finding"),
    MISC("Misc");
    private final String displayName;
    Category(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
