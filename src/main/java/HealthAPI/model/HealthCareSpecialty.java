package HealthAPI.model;

public enum HealthCareSpecialty {

    MFR("Fisiatria"),
    FT("Fisioterapia"),
    TF("Terapia da fala"),
    NUTRI("Nutrição"),
    PQ("Psiquiatria"),
    PSI("Psicologia");

    String name;

    HealthCareSpecialty(String name) {
        this.name = name;
    }

}