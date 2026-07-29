package co.com.pragma.model.common;

public final class ValidationMessageConstants {

    private ValidationMessageConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MSG_BOOTCAMP_ID_REQUIRED = "Bootcamp id is required";
    public static final String MSG_NAME_REQUIRED = "Bootcamp name is required";
    public static final String MSG_DESCRIPTION_REQUIRED = "Bootcamp description is required";
    public static final String MSG_LAUNCH_DATE_REQUIRED = "Bootcamp launch date is required";
    public static final String MSG_DURATION_IN_WEEKS_REQUIRED = "Bootcamp duration in weeks is required";
    public static final String MSG_CAPABILITY_COUNT_REQUIRED = "Capability count is required";
    public static final String MSG_TECHNOLOGY_COUNT_REQUIRED = "Technology count is required";
    public static final String MSG_ENROLLED_PERSON_COUNT_REQUIRED = "Enrolled person count is required";
}
