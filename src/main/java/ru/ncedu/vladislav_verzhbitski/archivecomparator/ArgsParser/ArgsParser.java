package ru.ncedu.vladislav_verzhbitski.archivecomparator.ArgsParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

public class ArgsParser {
    private enum fileMark {NeedExist, NeedNotExist}
    private File firstFile;
    private File secondFile;
    private File log;
    private final static String MODIFICATIONS_FILENAME = "modifications.txt";

    public ArgsParser(String[] args) throws FileNotFoundException, FileFoundException {
        switch (args.length) {
            case 2:
                twoArgs(args);
                break;
            case 3:
                threeArgs(args);
                break;
            default:
                throw new InputMismatchException("Invalid arguments.\nUsage: first_file_path second_file_path [log_name]");
        }
    }

    private void threeArgs(String[] args) throws FileNotFoundException, FileFoundException {
        firstFile = toFile(args[0], fileMark.NeedExist);
        secondFile = toFile(args[1], fileMark.NeedExist);
        log = toFile(args[2], fileMark.NeedNotExist);
    }

    private void twoArgs(String[] args) throws FileNotFoundException, FileFoundException {
        firstFile = toFile(args[0], fileMark.NeedExist);
        secondFile = toFile(args[1], fileMark.NeedExist);
        log = toFile(MODIFICATIONS_FILENAME, fileMark.NeedNotExist);
    }

    private File toFile(String path, fileMark mark) throws FileNotFoundException, FileFoundException {
        File file = new File(path);

        switch (mark) {
            case NeedExist:
                if (!file.exists()) {
                    throw new FileNotFoundException("File with path: " + path + " is not found");
                }
                break;
            case NeedNotExist:
                if (file.exists()) {
                    throw new FileFoundException("File with path: " + path + " already exists");
                }
                break;
        }

        return file;
    }

    public File getFirstFile() {
        return firstFile;
    }

    public File getSecondFile() {
        return secondFile;
    }

    public File getLog() {
        return log;
    }
}
