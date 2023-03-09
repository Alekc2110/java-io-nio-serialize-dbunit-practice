package com.epam.alexkorshunovych.fastfilemover.util;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

class FastFileMoverTest {

    private static final String FROM_DIR_FILE_1KB = "";
    private static final String COPY_TO_DIR_FILE_1KB = "";
    private static final String MOVE_TO_DIR_FILE_1KB = "";

    private static final String FROM_DIR_FILE_100KB = "";
    private static final String COPY_TO_DIR_FILE_100KB = "";
    private static final String MOVE_TO_DIR_FILE_100KB = "";

    private static final String FROM_DIR_FILE_10MB = "";
    private static final String COPY_TO_DIR_FILE_10MB = "";
    private static final String MOVE_TO_DIR_FILE_10MB = "";
    private static final String PRINT_OUT_FORMAT = "Average time for file copy: %d μs";
    private static final int CYCLES_1000 = 1000;
    private static final int DEFAULT_SIZE = 0;
    private static final int _100KB_SIZE = 100_000;

    private Stopwatch timer;

    @Test
    @DisplayName("run File 'renameTo' method for file - size 1kb, print total time")
    void moveFileWithIOSize1KbTest() {
        timer = Stopwatch.createStarted();

        FastFileMover.moveFileWithIO(FROM_DIR_FILE_1KB, MOVE_TO_DIR_FILE_1KB);

        timer.stop();
        System.out.println(timer.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @DisplayName("run File 'renameTo' method for file - size 100kb, print total time")
    void moveFileWithIOSize100KbTest() {
        timer = Stopwatch.createStarted();

        FastFileMover.moveFileWithIO(FROM_DIR_FILE_100KB, MOVE_TO_DIR_FILE_100KB);

        timer.stop();
        System.out.println(timer.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @DisplayName("run File 'renameTo' method for file - size 10Mb, print total time")
    void moveFileWithIOSize10MbTest() {
        timer = Stopwatch.createStarted();

        FastFileMover.moveFileWithIO(FROM_DIR_FILE_10MB, MOVE_TO_DIR_FILE_10MB);

        timer.stop();
        System.out.println(timer.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @DisplayName("run move file using Files 'move' NIO,file size 1Kb, print total time")
    void moveFileWithFilesNIOFileSize1KbTest() {
        timer = Stopwatch.createStarted();

        FastFileMover.moveFileWithFiles(FROM_DIR_FILE_1KB, MOVE_TO_DIR_FILE_1KB);

        timer.stop();
        System.out.println(timer.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @DisplayName("run move file using Files 'move' NIO,file size 100Kb, print total time")
    void moveFileWithFilesNIOFileSize100KbTest() {
        timer = Stopwatch.createStarted();

        FastFileMover.moveFileWithFiles(FROM_DIR_FILE_100KB, MOVE_TO_DIR_FILE_100KB);

        timer.stop();
        System.out.println(timer.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @DisplayName("run move file using Files 'move' NIO,file size 10Mb, print total time")
    void moveFileWithFilesNIOFileSize10MbTest() {
        timer = Stopwatch.createStarted();

        FastFileMover.moveFileWithFiles(FROM_DIR_FILE_10MB, COPY_TO_DIR_FILE_10MB);

        timer.stop();
        System.out.println(timer.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @DisplayName("run copying file FileStreams,file - size 1kb, 1000 times, buffer size 8192bytes print total time")
    void copyFileWithBufferedIOBuffer8192BytesFileSize1KbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithBufferedIO(FROM_DIR_FILE_1KB, COPY_TO_DIR_FILE_1KB, DEFAULT_SIZE);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copying file FileStreams,file size 100kb, 1000 times, buffer size 8192bytes print total time")
    void copyFileWithBufferedIOBuffer8192BytesFileSize100KbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithBufferedIO(FROM_DIR_FILE_100KB, COPY_TO_DIR_FILE_100KB, DEFAULT_SIZE);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copying file FileStreams,file size 10Mb, 1000 times, buffer size 8192bytes print total time")
    void copyFileWithBufferedIOBuffer8192BytesFileSize10MbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithBufferedIO(FROM_DIR_FILE_10MB, COPY_TO_DIR_FILE_10MB, DEFAULT_SIZE);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copying file FileStreams,file size 1kb, 1000 times, buffer size 100Kb print total time")
    void copyFileWithBufferedIOBuffer100KbFileSize1KbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithBufferedIO(FROM_DIR_FILE_1KB, COPY_TO_DIR_FILE_1KB, _100KB_SIZE);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copying file FileStreams,file size 100kb, 1000 times, buffer size 100Kb print total time")
    void copyFileWithBufferedIOBuffer100KbFileSize100KbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithBufferedIO(FROM_DIR_FILE_100KB, COPY_TO_DIR_FILE_100KB, _100KB_SIZE);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copy file FileStreams,file size 10Mb, 1000 times, buffer size 100Kb print total time")
    void copyFileWithBufferedIOBuffer100KbFileSize10MbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithBufferedIO(FROM_DIR_FILE_10MB, COPY_TO_DIR_FILE_10MB, _100KB_SIZE);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }


    @Test
    @DisplayName("run copy file using Files Channels NIO,file size 1Kb, buffer size 8192bytes 1000 times, print total time")
    void copyFileWithByteBufferNIOFileSize1KbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithByteBuffer(FROM_DIR_FILE_1KB, COPY_TO_DIR_FILE_1KB, DEFAULT_SIZE);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copy file using Files Channels NIO,file size 100Kb, buffer size 8192bytes 1000 times, print total time")
    void copyFileWithByteBufferNIOFileSize100KbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithByteBuffer(FROM_DIR_FILE_100KB, COPY_TO_DIR_FILE_100KB, DEFAULT_SIZE);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copy file using Files Channels NIO,file size 10Mb, buffer size 8192bytes 1000 times, print total time")
    void copyFileWithByteBufferNIOFileSize10MbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithByteBuffer(FROM_DIR_FILE_10MB, COPY_TO_DIR_FILE_10MB, DEFAULT_SIZE);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copy file (var2) using Files Channels NIO,file size 10Mb, buffer size 100Kb 1000 times, print total time")
    void copyFileWithByteBufferNIOFileSize10MbVar2Test() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithByteBufferVar2(FROM_DIR_FILE_10MB, COPY_TO_DIR_FILE_10MB, _100KB_SIZE);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copy file using Files Channels 'transferTo' NIO,file size 1Kb, 1000 times, print total time")
    void copyFileWithChannelFileSize1KbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithChannel(FROM_DIR_FILE_1KB, COPY_TO_DIR_FILE_1KB);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copy file using Files Channels 'transferTo' NIO,file size 100Kb, 1000 times, print total time")
    void copyFileWithChannelFileSize100KbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithChannel(FROM_DIR_FILE_100KB, COPY_TO_DIR_FILE_100KB);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

    @Test
    @DisplayName("run copy file using Files Channels 'transferTo' NIO,file size 10Mb, 1000 times, print total time")
    void copyFileWithChannelFileSize10MbTest() {
        long elapsed = 0;
        for (int i = 0; i < CYCLES_1000; i++) {
            timer = Stopwatch.createStarted();
            FastFileMover.copyFileWithChannel(FROM_DIR_FILE_10MB, COPY_TO_DIR_FILE_10MB);
            timer.stop();
            elapsed += timer.elapsed(TimeUnit.MICROSECONDS);
        }
        System.out.printf(PRINT_OUT_FORMAT, elapsed / CYCLES_1000);
    }

}