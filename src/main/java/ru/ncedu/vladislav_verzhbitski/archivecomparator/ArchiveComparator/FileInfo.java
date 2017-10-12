package ru.ncedu.vladislav_verzhbitski.archivecomparator.ArchiveComparator;

public class FileInfo {
    private final String name;
    private final long hash;
    private final long size;

    FileInfo(String name, long hash, long size) {
        this.name = name;
        this.hash = hash;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long getHash() {
        return hash;
    }

    public long getSize() {
        return size;
    }
}
