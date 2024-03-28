package company.gonggam.member.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum AgeGroup {
    ZERO_TO_NINE,
    TEN_TO_NINETEEN,
    TWENTY_TO_TWENTYNINE,
    THIRTY_TO_THIRTYNINE,
    FORTY_TO_FORTYNINE,
    FIFTY_TO_FIFTYNINE,
    SIXTY_ABOVE,
    UNKNOWN;

    public static AgeGroup fromString(String age_range) {
        log.info("AgeGroup : " + age_range);

        // age_range가 null이거나 빈 문자열인 경우 UNKNOWN 반환
        if (age_range == null || age_range.isEmpty()) {
            return AgeGroup.UNKNOWN;
        }

        switch (age_range) {
            case "1~9":
                return AgeGroup.ZERO_TO_NINE;
            case "10~14":
            case "15~19":
            case "10~19":
                return AgeGroup.TEN_TO_NINETEEN;
            case "20~29":
                return AgeGroup.TWENTY_TO_TWENTYNINE;
            case "30~39":
                return AgeGroup.THIRTY_TO_THIRTYNINE;
            case "40~49":
                return AgeGroup.FORTY_TO_FORTYNINE;
            case "50~59":
                return AgeGroup.FIFTY_TO_FIFTYNINE;
            case "60~69":
            case "70~79":
            case "80~89":
            case "90~":
                return AgeGroup.SIXTY_ABOVE;
            default:
                return AgeGroup.UNKNOWN;
        }
    }
}
