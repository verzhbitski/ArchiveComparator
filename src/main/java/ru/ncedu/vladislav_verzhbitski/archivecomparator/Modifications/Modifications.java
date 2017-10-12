package ru.ncedu.vladislav_verzhbitski.archivecomparator.Modifications;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Modifications {
    private String firstArchiveName;
    private String secondArchiveName;
    private ArrayList<Modification> modifications;

    public Modifications() {
        modifications = new ArrayList<Modification>();
    }

    public void createModificationsLog(File logFile) throws IOException {
        int columnSize = getMaxLength();
        PrintStream log = new PrintStream(logFile);
        log.println(" " + padRight(firstArchiveName, columnSize) + " | " + padRight(secondArchiveName, columnSize) + " ");
        log.println(getHorizontalLine(columnSize));
        for (Modification m: modifications) {
            log.println(getModificationStringFormatted(m, columnSize));
        }
        log.close();
    }

    private String getHorizontalLine(int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size + 2; ++i) {
            result.append("-");
        }
        result.append("|");
        for (int i = 0; i < size + 2; ++i) {
            result.append("-");
        }

        return result.toString();
    }

    private String getModificationStringFormatted(Modification m, int size) {
        StringBuilder result = new StringBuilder();

        switch (m.getModificationType()) {
            case New:
                result
                        .append("   ")
                        .append(padRight(m.getFirstFileName(), size - 2))
                        .append(" | " + modificationTypeToSymbol(m.getModificationType()) + " ")
                        .append(padRight(m.getSecondFileName(), size - 2))
                        .append(" ");
                return result.toString();
            case Deleted:
                result
                        .append(" " + modificationTypeToSymbol(m.getModificationType()) + " ")
                        .append(padRight(m.getFirstFileName(), size - 2))
                        .append(" |   ")
                        .append(padRight(m.getSecondFileName(), size - 2))
                        .append(" ");
                return result.toString();
            default:
                result
                        .append(" ")
                        .append(modificationTypeToSymbol(m.getModificationType()) + " ")
                        .append(padRight(m.getFirstFileName(), size - 2))
                        .append(" | ")
                        .append(modificationTypeToSymbol(m.getModificationType()) + " ")
                        .append(padRight(m.getSecondFileName(), size - 2))
                        .append(" ");
                return result.toString();
        }
    }

    private String padRight(String str, int pad) {
        StringBuilder result = new StringBuilder(str);

        for (int i = 0; i < pad - str.length(); ++i) {
            result.append(" ");
        }

        return result.toString();
    }

    private int getMaxLength() {
        int maxLength = Math.max(firstArchiveName.length(), secondArchiveName.length());

        for (Modification m: modifications) {
            maxLength = Math.max(maxLength, m.getFirstFileName().length() + 2);
            maxLength = Math.max(maxLength, m.getSecondFileName().length() + 2);
        }

        return maxLength;
    }

    public ArrayList<Modification> getModifications() {
        return modifications;
    }

    public void addModification(Modification modification) {
        modifications.add(modification);
    }

    public void addModifications(Modifications modifications) {
        this.modifications.addAll(modifications.getModifications());
    }

    public String getFirstArchiveName() {
        return firstArchiveName;
    }

    public String getSecondArchiveName() {
        return secondArchiveName;
    }

    public void setFirstArchiveName(String firstArchiveName) {
        this.firstArchiveName = firstArchiveName;
    }

    public void setSecondArchiveName(String secondArchiveName) {
        this.secondArchiveName = secondArchiveName;
    }

    private String modificationTypeToSymbol(Modification.modType modificationType) {
        switch (modificationType) {
            case New:
                return "+";
            case Updated:
                return "*";
            case Deleted:
                return "-";
            case Renamed:
                return "?";
        }

        return null;
    }
}
