package com.uniovi.sdi.grademanager.entities;

public enum ProfessorCategory {
    PROFESOR_TITULAR("Profesor Titular", "professor.category.PROFESOR_TITULAR"),
    CATEDRATICO("Catedrático", "professor.category.CATEDRATICO"),
    PROFESOR_AYUDANTE("Profesor Ayudante", "professor.category.PROFESOR_AYUDANTE"),
    PROFESOR_ASOCIADO("Profesor Asociado", "professor.category.PROFESOR_ASOCIADO");

    private final String label;
    private final String messageKey;

    ProfessorCategory(String label, String messageKey) {
        this.label = label;
        this.messageKey = messageKey;
    }

    public String getLabel() {
        return label;
    }

    public String getMessageKey() {
        return messageKey;
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
