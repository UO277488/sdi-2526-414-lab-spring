package com.uniovi.sdi.grademanager.entities;

public enum ProfessorCategory {
    PROFESOR_TITULAR("Profesor Titular"),
    CATEDRATICO("Catedrático"),
    PROFESOR_AYUDANTE("Profesor Ayudante"),
    PROFESOR_ASOCIADO("Profesor Asociado");

    private final String label;

    ProfessorCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ProfessorCategory fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ProfessorCategory category : values()) {
            if (category.name().equalsIgnoreCase(value) || category.label.equalsIgnoreCase(value)) {
                return category;
            }
        }
        return null;
    }
}
