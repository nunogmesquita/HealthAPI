package HealthAPI.messages;

public class Responses {

    public static final String DELETED_APPOINTMENT = "Appointment %s has been successfully deleted.";
    public static final String DELETED_USER = "User %s has been successfully deleted.";
    public static final String DELETED_ALL_USERS_TIMESLOTS = "All timeslots of user %s have been successfully deleted.";
    public static final String DELETED_TIMESLOT = "Timeslot %s has been successfully deleted.";
    public static final String DELETED_CLIENT = "Client %s has been successfully deleted.";
    public static final String TIMESLOTS_CREATED = "You're weekly time slots have been created";
    public static final String AT_LEAST_18_YEARS_OLD = "You must have at least 18 years old.";
    public static final String TOO_OLD = "Please insert a valid birthdate. You seriously can't have %s years old. " +
            "Actually, the oldest known living person is Maria Branyas of Spain, aged 116 years, 26 days. " +
            "The oldest known living man is Juan Vicente Pérez of Venezuela, aged 113 years, 307 days. " +
            "The 100 oldest women have, on average, lived several years longer than the 100 oldest men. " +
            "Source: https://en.wikipedia.org/wiki/List_of_the_verified_oldest_people";
    public static final String INVALID_EMAIL = "Please insert a valid email.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String CLIENT_NOT_FOUND = "Client not found.";
    public static final String TIMESLOT_NOT_FOUND = "TimeSlot %s not found";
    public static final String APPOINTMENT_NOT_FOUND = "Appointment not found.";
    public static final String APPOINTMENT_ALREADY_ACTIVE = "Appointment %s is already active.";
    public static final String RESTORED_USER = "User %s has been restored.";
    public static final String APPOINTMENT_ALREADY_INACTIVE = "Appointment %s has already been removed.";
    public static final String INVALID_REQUEST_FORMAT = "Invalid request body format.";
    public static final String DATA_INTEGRITY_VIOLATION = "Please make sure your email and/or NIF " +
            "isn't being used in another account.";
    public static final String INVALID_NAME = "%s is an invalid name.";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number.";
    public static final String INVALID_NIF = "Invalid NIF.";
    public static final String INVALID_USER = "Invalid user.";
    public static final String USER_ALREADY_ACTIVE = "User %s is already active.";
    public static final String UNAUTHORIZED_ACTION = "Unauthorized action.";
    public static final String INVALID_INPUT = "Invalid input.";
    public static final String TIMESLOT_HOUR_ERROR = "The beginning hour can't be after final hour.";
    public static final String INVALID_TIMESLOT_DATE = "You can't create a timeslot for a day that has already passed.";
    public static final String TIMESLOTS_ALREADY_EXISTS = "Can't create those timeslots because they already exist.";
    public static final String TIMESLOT_ALREADY_BOOKED = "Timeslot %s is already booked.";
}