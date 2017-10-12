package ru.ncedu.vladislav_verzhbitski.archivecomparator;

import ru.ncedu.vladislav_verzhbitski.archivecomparator.ArchiveComparator.ArchiveComparator;
import ru.ncedu.vladislav_verzhbitski.archivecomparator.ArgsParser.ArgsParser;
import ru.ncedu.vladislav_verzhbitski.archivecomparator.Modifications.Modifications;

public class Main {

    public static void main(String[] args) {
        try {
            ArgsParser parser = new ArgsParser(args);
            ArchiveComparator comparator = new ArchiveComparator(parser.getFirstFile(), parser.getSecondFile());
            Modifications modifications = comparator.compareArchives();
            modifications.createModificationsLog(parser.getLog());
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}
