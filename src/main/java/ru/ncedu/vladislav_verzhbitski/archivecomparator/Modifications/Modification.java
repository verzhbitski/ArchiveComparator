package ru.ncedu.vladislav_verzhbitski.archivecomparator.Modifications;

public class Modification {
    public enum modType{Renamed, New, Deleted, Updated}
    private final modType modificationType;
    private final String firstFileName;
    private final String secondFileName;

    public Modification(modType modificationType, String firstFile, String secondFile) {
         this.modificationType = modificationType;
         this.firstFileName = firstFile;
         this.secondFileName = secondFile;
    }

    public modType getModificationType() {
        return modificationType;
    }

    public String getFirstFileName() {
        return firstFileName;
    }

    public String getSecondFileName() {
        return secondFileName;
    }
}
