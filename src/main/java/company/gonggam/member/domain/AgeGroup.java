package company.gonggam.member.domain;

public enum AgeGroup {
    ZERO_TO_NINE,
    TEN_TO_NINETEEN,
    TWENTY_TO_TWENTYNINE,
    THIRTY_TO_THIRTYNINE,
    FORTY_TO_FORTYNINE,
    FIFTY_TO_FIFTYNINE,
    SIXTY_ABOVE,
    UNKNOWN;

    public static AgeGroup fromInt(Integer age) {
        if (age >= 0 && age <= 9) {
            return AgeGroup.ZERO_TO_NINE;
        } else if (age >= 10 && age <= 19) {
            return AgeGroup.TEN_TO_NINETEEN;
        } else if (age >= 20 && age <= 29) {
            return AgeGroup.TWENTY_TO_TWENTYNINE;
        } else if (age >= 30 && age <= 39) {
            return AgeGroup.THIRTY_TO_THIRTYNINE;
        } else if (age >= 40 && age <= 49) {
            return AgeGroup.FORTY_TO_FORTYNINE;
        } else if (age >= 50 && age <= 59) {
            return AgeGroup.FIFTY_TO_FIFTYNINE;
        } else if (age >= 60) {
            return AgeGroup.SIXTY_ABOVE;
        } else {
            return AgeGroup.UNKNOWN;
        }
    }

    public static AgeGroup fromString(String age_range) {
        return AgeGroup.UNKNOWN;
    }
}
