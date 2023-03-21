package HealthAPI.model;

public enum AppointmentSpecialty {
    MFR("Fisiatria"),
    FT("Fisioterapia"),
    TF("Terapia da fala"),
    NUTRI("Nutrição"),
    PQ("Psiquiatria"),
    PSI("Psicologia");

    String name;

    AppointmentSpecialty(String name) {
        this.name = name;
    }
}
