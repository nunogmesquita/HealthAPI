package HealthAPI.model;

public enum Speciality {

    MFR("Fisiatria"),
    FT("Fisioterapia"),
    TF("Terapia da fala"),
    NUTRI("Nutrição"),
    PQ("Psiquiatria"),
    PSI("Psicologia");

    public String name;

    Speciality(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Speciality{" +
                "name='" + name + '\'' +
                '}';
    }
}