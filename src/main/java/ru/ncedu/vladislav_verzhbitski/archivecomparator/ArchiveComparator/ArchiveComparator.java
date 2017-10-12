package ru.ncedu.vladislav_verzhbitski.archivecomparator.ArchiveComparator;

import ru.ncedu.vladislav_verzhbitski.archivecomparator.Modifications.Modification;
import ru.ncedu.vladislav_verzhbitski.archivecomparator.Modifications.Modifications;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ArchiveComparator {
    private ZipFile firstArchive;
    private ZipFile secondArchive;

    private ArrayList<FileInfo> firstArchiveFileList;
    private ArrayList<FileInfo> secondArchiveFileList;

    public ArchiveComparator(File file1, File file2) {
        try {
            firstArchive = new ZipFile(file1);
            secondArchive = new ZipFile(file2);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    private ArrayList<FileInfo> getArchiveFileList(ZipFile archive) {
        Enumeration<? extends ZipEntry> entries = archive.entries();
        ArrayList<FileInfo> fileList = new ArrayList<FileInfo>();

        while (entries.hasMoreElements()) {
            ZipEntry tmp = entries.nextElement();
            FileInfo fileInfo = new FileInfo(tmp.getName(), tmp.getCrc(), tmp.getSize());
            fileList.add(fileInfo);
        }

        return fileList;
    }

    public Modifications getModifications() {
        Modifications modifications = new Modifications();

        modifications.addModifications(findRenamedFiles());
        modifications.addModifications(findDeletedFiles());
        modifications.addModifications(findNewFiles());
        modifications.addModifications(findUpdatedFiles());

        return modifications;
    }

    public Modifications compareArchives() {
        firstArchiveFileList = getArchiveFileList(firstArchive);
        secondArchiveFileList = getArchiveFileList(secondArchive);

        Modifications modifications = getModifications();
        modifications.setFirstArchiveName(firstArchive.getName());
        modifications.setSecondArchiveName(secondArchive.getName());

        return modifications;
    }

    public Modifications findRenamedFiles() {
        Modifications modifications = new Modifications();

        for (FileInfo firstEntry: firstArchiveFileList) {
            for (FileInfo secondEntry: secondArchiveFileList) {
                if (firstEntry.getSize() == secondEntry.getSize() && firstEntry.getName().compareTo(secondEntry.getName()) != 0) {
                    Modification modification = new Modification(Modification.modType.Renamed, firstEntry.getName(), secondEntry.getName());
                    modifications.addModification(modification);
                }
            }
        }

        return modifications;
    }

    public Modifications findDeletedFiles() {
        Modifications modifications = new Modifications();

        for (FileInfo firstEntry: firstArchiveFileList) {
            boolean noSuchFile = true;

            for (FileInfo secondEntry: secondArchiveFileList) {
                if (firstEntry.getSize() == secondEntry.getSize() || firstEntry.getName().compareTo(secondEntry.getName()) == 0) {
                    noSuchFile = false;
                    break;
                }
            }

            if (noSuchFile) {
                Modification modification = new Modification(Modification.modType.Deleted, firstEntry.getName(), "");
                modifications.addModification(modification);
            }
        }

        return modifications;
    }

    public Modifications findNewFiles() {
        Modifications modifications = new Modifications();

        for (FileInfo secondEntry: secondArchiveFileList) {
            boolean noSuchFile = true;

            for (FileInfo firstEntry: firstArchiveFileList) {
                if (firstEntry.getSize() == secondEntry.getSize() || firstEntry.getName().compareTo(secondEntry.getName()) == 0) {
                    noSuchFile = false;
                    break;
                }
            }

            if (noSuchFile) {
                Modification modification = new Modification(Modification.modType.New, "", secondEntry.getName());
                modifications.addModification(modification);
            }
        }

        return modifications;
    }

    public Modifications findUpdatedFiles() {
        Modifications modifications = new Modifications();

        for (FileInfo firstEntry: firstArchiveFileList) {
            for (FileInfo secondEntry: secondArchiveFileList) {
                if (firstEntry.getName().compareTo(secondEntry.getName()) == 0 && firstEntry.getHash() != secondEntry.getHash()) {
                    Modification modification = new Modification(Modification.modType.Updated, firstEntry.getName(), secondEntry.getName());
                    modifications.addModification(modification);
                }
            }
        }

        return modifications;
    }
}
