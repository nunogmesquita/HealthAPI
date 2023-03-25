package HealthAPI.model;

public enum Speciality {

    MFR("Fisiatria"),
    FT("Fisioterapia"),
    TF("Terapia da fala"),
    NUTRI("Nutrição"),
    PQ("Psiquiatria"),
    PSI("Psicologia");

    String name;

    Speciality(String name) {
        this.name = name;
    }

}