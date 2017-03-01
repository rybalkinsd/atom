package ru.atom.enums;

/**
 * Created by sergey on 2/28/17.
 */
public enum Gender {
    Male,
    Female,
    Other;

    /**
     * Check couple.
     *
     * @param country - target country
     * @param partner - available partner
     * @return legal status
     */
    public boolean isLegalCouple(Country country, Gender partner) {
        switch (country) {
            case Russia:
                return this != partner
                        && this != Other
                        && partner != Other;
            case Netherlands:
            case USA:
                return true;
            default:
                return false;
        }
    }
}