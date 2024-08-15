package com.epm.gestepm.lib.nas;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DATASOURCE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_UPDATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_UPSERT;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.epm.gestepm.lib.logging.AppLogger;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.logging.applog.LogHandler;
import com.epm.gestepm.lib.nas.exception.NASFileAlreadyExistsInFolderException;
import com.epm.gestepm.lib.nas.exception.NASFileNotExistsException;
import com.epm.gestepm.lib.nas.exception.NASFolderNotExistsException;
import com.epm.gestepm.lib.nas.exception.NasIOException;
import com.epm.gestepm.lib.nas.exception.ReadPathIsNotAFileException;
import org.springframework.validation.annotation.Validated;

@Validated
@EnableExecutionLog(layerMarker = DATASOURCE)
public class DefaultNASDatasource implements NASDatasource {

    private static final AppLogger logger = AppLogger.forClass(DefaultNASDatasource.class);

    private static final String WORKING_FILE = "workingFile";

    private final String workingDir;

    public DefaultNASDatasource(String workingDir) {
        this.workingDir = workingDir;
    }

    @Override
    public String getWorkingDir() {
        return workingDir;
    }

    @Override
    @LogExecution(operation = OP_READ,
            msgIn = "Reading file from NAS, expected result",
            msgOut = "File read from NAS",
            errorMsg = "Error reading file from NAS")
    public byte[] read(String path) {
        return NASDatasource.super.read(path);
    }

    @Override
    @LogExecution(operation = OP_READ,
            msgIn = "Reading file from NAS folder, expected result",
            msgOut = "File read from NAS",
            errorMsg = "Error reading file from NAS")
    public byte[] read(String folderPath, String filename) {

        final String filePath = NASPathUtils.concat(List.of(folderPath, filename));

        if (!exists(filePath)) {
            throw new NASFileNotExistsException();
        }

        if (!isFile(filePath)) {
            throw new ReadPathIsNotAFileException();
        }

        return readAllBytes(filePath);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            msgIn = "Creating file in NAS, required existing folder",
            msgOut = "File created in NAS",
            errorMsg = "Error creating file in NAS")
    public void create(String path, byte[] content) {
        NASDatasource.super.create(path, content);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            msgIn = "Creating file in NAS folder, required existing folder",
            msgOut = "File created in NAS folder",
            errorMsg = "Error creating file in NAS folder")
    public void create(String folderPath, String filename, byte[] content) {

        if (!exists(folderPath)) {
            throw new NASFolderNotExistsException();
        }

        final String filePath = NASPathUtils.concat(List.of(folderPath, filename));

        if (exists(filePath)) {
            throw new NASFileAlreadyExistsInFolderException();
        }

        createFile(filePath);
        writeToFile(filePath, content);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            msgIn = "Creating file in NAS, required existing folder unless flagged otherwise",
            msgOut = "File created in NAS",
            errorMsg = "Error creating file in NAS")
    public void create(String path, byte[] content, boolean createDirs) {
        NASDatasource.super.create(path, content, createDirs);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            msgIn = "Creating file in NAS folder, required existing folder unless flagged otherwise",
            msgOut = "File created in NAS folder",
            errorMsg = "Error creating file in NAS folder")
    public void create(String folderPath, String filename, byte[] content, boolean createDirs) {

        if (!exists(folderPath) && createDirs) {
            createDirs(folderPath);
        }

        create(folderPath, filename, content);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            msgIn = "Updating file in NAS, required existing file",
            msgOut = "File updated in NAS",
            errorMsg = "Error updating file in NAS")
    public void update(String path, byte[] content) {
        NASDatasource.super.update(path, content);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            msgIn = "Updating file in NAS folder, required existing file",
            msgOut = "File updated in NAS folder",
            errorMsg = "Error updating file in NAS folder")
    public void update(String folderPath, String filename, byte[] content) {

        final String filePath = NASPathUtils.concat(List.of(folderPath, filename));

        if (!exists(filePath)) {
            throw new NASFileNotExistsException();
        }

        writeToFile(filePath, content);
    }

    @Override
    @LogExecution(operation = OP_UPSERT,
            msgIn = "Upsert file in NAS, required existing folder",
            msgOut = "File upsert in NAS",
            errorMsg = "Error upsert file in NAS")
    public void upsert(String path, byte[] content) {
        NASDatasource.super.upsert(path, content);
    }

    @Override
    @LogExecution(operation = OP_UPSERT,
            msgIn = "Upsert file in NAS folder, required existing folder",
            msgOut = "File upsert in NAS folder",
            errorMsg = "Error upsert file in NAS folder")
    public void upsert(String folderPath, String filename, byte[] content) {
        NASDatasource.super.upsert(folderPath, filename, content);
    }

    @Override
    @LogExecution(operation = OP_UPSERT,
            msgIn = "Upsert file in NAS, required existing folder unless flagged otherwise",
            msgOut = "File upsert in NAS",
            errorMsg = "Error upsert file in NAS")
    public void upsert(String path, byte[] content, boolean createDirs) {
        NASDatasource.super.upsert(path, content, createDirs);
    }

    @Override
    @LogExecution(operation = OP_UPSERT,
            msgIn = "Upsert file in NAS folder, required existing folder unless flagged otherwise",
            msgOut = "File upsert in NAS folder",
            errorMsg = "Error upsert file in NAS folder")
    public void upsert(String folderPath, String filename, byte[] content, boolean createDirs) {
        NASDatasource.super.upsert(folderPath, filename, content, createDirs);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            msgIn = "Deleting file in NAS if exists, no-op otherwise",
            msgOut = "File deleted in NAS",
            errorMsg = "Error deleting file in NAS")
    public void delete(String path) {
        NASDatasource.super.delete(path);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            msgIn = "Deleting file in NAS folder if exists, no-op otherwise",
            msgOut = "File deleted in NAS folder",
            errorMsg = "Error deleting file in NAS folder")
    public void delete(String folderPath, String filename) {

        final String filePath = NASPathUtils.concat(List.of(folderPath, filename));
        deleteIfExists(filePath);
    }

    private byte[] readAllBytes(String filePath) {

        final LogHandler logHandler = logger.handler();

        final String workingFile = pathWithWorkingDir(filePath);
        final Path asPath = Paths.get(workingFile);

        logHandler.data(WORKING_FILE, workingFile);

        try {

            logHandler.msg("Reading file for path %s", workingFile).debug();

            return Files.readAllBytes(asPath);

        } catch (IOException e) {

            logHandler.msg("Could not read file for path %s", workingFile)
                .exceptionMsg(e.getMessage())
                .exceptionClass(e.getClass().getCanonicalName())
                .error();

            throw new NasIOException(e);
        }
    }

    private void createDirs(String folderPath) {

        final LogHandler logHandler = logger.handler();

        final String workingFolder = pathWithWorkingDir(folderPath);
        final Path asPath = Paths.get(workingFolder);

        logHandler.data("workingFolder", workingFolder);

        try {

            logHandler.msg("Creating directories for path %s", workingFolder).debug();

            Files.createDirectories(asPath);

        } catch (IOException e) {

            logHandler.msg("Could not create directories for path %s", workingFolder)
                .exceptionMsg(e.getMessage())
                .exceptionClass(e.getClass().getCanonicalName())
                .error();

            throw new NasIOException(e);
        }
    }

    private void createFile(String filePath) {

        final LogHandler logHandler = logger.handler();

        final String workingFile = pathWithWorkingDir(filePath);
        final Path asPath = Paths.get(workingFile);

        logHandler.data(WORKING_FILE, workingFile);

        try {

            logHandler.msg("Creating file for path %s", workingFile).debug();

            Files.createFile(asPath);

        } catch (IOException e) {

            logHandler.msg("Could not create file for path %s", workingFile)
                .exceptionMsg(e.getMessage())
                .exceptionClass(e.getClass().getCanonicalName())
                .error();

            throw new NasIOException(e);
        }
    }

    private void writeToFile(String filePath, byte[] content) {

        final LogHandler logHandler = logger.handler();

        final String workingFile = pathWithWorkingDir(filePath);
        final Path asPath = Paths.get(workingFile);

        logHandler.data(WORKING_FILE, workingFile).data("contentSizeInBytes", content.length);

        try {

            logHandler.msg("Writing content on file for path %s", workingFile).debug();

            Files.write(asPath, content);

        } catch (IOException e) {

            logHandler.msg("Could not write content on file for path %s", workingFile)
                .exceptionMsg(e.getMessage())
                .exceptionClass(e.getClass().getCanonicalName())
                .error();

            throw new NasIOException(e);
        }
    }

    private void deleteIfExists(String filePath) {

        final LogHandler logHandler = logger.handler();

        final String workingFile = pathWithWorkingDir(filePath);
        final Path asPath = Paths.get(workingFile);

        logHandler.data(WORKING_FILE, workingFile);

        try {

            logHandler.msg("Deleting (only if exists) file for path %s", workingFile).debug();

            Files.deleteIfExists(asPath);

        } catch (IOException e) {

            logHandler.msg("Could not delete file for path %s", workingFile)
                .exceptionMsg(e.getMessage())
                .exceptionClass(e.getClass().getCanonicalName())
                .error();

            throw new NasIOException(e);
        }
    }

}
