package microservices.hr.infrastructure.logging;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;

import microservices.hr.core.Logger;
import microservices.hr.infrastructure.Safe;

final public class FileLogger implements Logger {
    private final Writer file;

    public FileLogger(String file) throws IOException {
        this.file = new BufferedWriter(new FileWriter(file));
    }

    @Override
    public void exception(Exception exception) {
        writeException(exception);
    }

    private synchronized void writeException(Exception exception) {
        String stackTrace = ExceptionUtils.getStackTrace(exception);
        String dateTime = LocalDateTime.now().toString();
        String level = "EXCEPTION";
        String message = exception.getMessage();

        Safe.safeVoid(() -> file.write(String.format(
                "[%s] %s: %s\n%s\n",
                dateTime,
                level,
                message,
                stackTrace
        )));
    }
}
