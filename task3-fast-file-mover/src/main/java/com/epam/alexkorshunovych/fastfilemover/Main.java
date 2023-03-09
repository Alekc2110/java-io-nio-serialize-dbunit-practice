package com.epam.alexkorshunovych.fastfilemover;

import com.epam.alexkorshunovych.fastfilemover.util.FastFileMover;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Use: FastFileMover with args: <source> <destination>");
            System.exit(1);
        }

        String source = args[0];
        String destination = args[1];

//        boolean moved = FastFileMover.moveFileWithIO(source, destination);
//        System.out.println("moved using File: " + moved);

//        FastFileMover.copyFileWithBufferedIO(source, destination, 0);
//        System.out.println("copied using buffered FileStream IO");
//
//        Path path = FastFileMover.moveFileWithFiles(source, destination);
//        System.out.println("move by Files to new path: " + path);
//
//        FastFileMover.copyFileWithByteBuffer(source, destination, 0);
//        System.out.println("copied by Channel NIO with byteBuffer to: " + destination);
//
//        FastFileMover.copyFileWithByteBufferVar2(source, destination, 100_000);
//        System.out.println("copied by Channel NIO with byteBuffer to: " + destination);
//
        long bytes = FastFileMover.copyFileWithChannel(source, destination);
        System.out.println("copied by Channel 'transferTo' NIO total bytes: " + bytes);

    }
}
