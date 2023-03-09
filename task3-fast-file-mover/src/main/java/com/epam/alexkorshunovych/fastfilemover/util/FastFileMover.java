package com.epam.alexkorshunovych.fastfilemover.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.EnumSet;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;


public class FastFileMover {

    public static boolean moveFileWithIO(String from, String to) {
        File srcFile = new File(from);
        return srcFile.renameTo(new File(to));
    }

    public static Path moveFileWithFiles(String from, String to) {
        Path newPath = null;
        try {
            newPath = Files.move(Paths.get(from), Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to move file: " + e.getMessage());
        }
        return newPath;
    }

    public static void copyFileWithBufferedIO(String from, String to, int bufferSize) {
        int size = bufferSize <= 0 ? 8192 : bufferSize;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(from), size);
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(to), size)
        ) {
            int i;
            while ((i = bis.read()) != -1) {
                bos.write(i);
            }
        } catch (IOException e) {
            System.err.println("Failed to copy file: " + e.getMessage());
        }
    }

    public static void copyFileWithByteBuffer(String from, String to, int bufferSize) {
        int size = bufferSize <= 0 ? 8192 : bufferSize;
        ByteBuffer buffer = ByteBuffer.allocateDirect(size);

        try (FileChannel inChannel = new FileInputStream(from).getChannel();
             FileChannel outChannel = new FileOutputStream(to).getChannel()
        ) {
            while (inChannel.read(buffer) != -1 || buffer.position() > 0) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.compact();
            }
        } catch (IOException e) {
            System.err.println("Failed to copy file: " + e.getMessage());
        }

    }

    public static void copyFileWithByteBufferVar2(String from, String to, int bufferSize) {
        int size = bufferSize <= 0 ? 8192 : bufferSize;
        ByteBuffer buffer = ByteBuffer.allocateDirect(size);
        try (ReadableByteChannel rbc = Files.newByteChannel(Paths.get(from), EnumSet.of(READ));
             WritableByteChannel wbc = Files.newByteChannel(Paths.get(to), EnumSet.of(WRITE))
        ) {
            while (rbc.read(buffer) != -1 || buffer.position() > 0) {
                buffer.flip();
                wbc.write(buffer);
                buffer.compact();
            }

        } catch (IOException e) {
            System.err.println("Failed to copy file: " + e.getMessage());
        }
    }

    public static long copyFileWithChannel(String from, String to) {
        long movedBytes = 0;
        try (FileChannel inChannel = new FileInputStream(from).getChannel();
             FileChannel outChannel = new FileOutputStream(to).getChannel()
        ) {
            movedBytes = inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            System.err.println("Failed to copy file: " + e.getMessage());
        }
        return movedBytes;
    }

}
