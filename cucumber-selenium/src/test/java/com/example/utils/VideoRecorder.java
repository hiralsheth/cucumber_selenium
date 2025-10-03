package com.example.utils;

import org.apache.commons.io.FileUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

/**
 * Simple video recorder replacement that captures individual PNG frames and
 * packages them into a ZIP archive. This avoids platform-dependent Java
 * video encoding libraries (like jcodec) and keeps the project compilable.
 *
 * Use an external tool (ffmpeg) to convert extracted frames into MP4 if
 * desired. Example:
 * ffmpeg -r 10 -i frame-%05d.png -c:v libx264 -pix_fmt yuv420p out.mp4
 */
public class VideoRecorder {
    private final List<File> frames = new ArrayList<>();
    private final File tempDir;
    private boolean recording = false;

    public VideoRecorder() throws IOException {
        tempDir = Files.createTempDirectory("test-vid-").toFile();
    }

    public void start() {
        recording = true;
    }

    public void capture(BufferedImage img) throws IOException {
        if (!recording) return;
        File out = new File(tempDir, String.format("frame-%05d.png", frames.size()));
        ImageIO.write(img, "png", out);
        frames.add(out);
    }

    /**
     * Stop recording and create a ZIP archive containing all captured frames.
     * Returns the ZIP file, or null if no frames were captured.
     */
    public File stopAndEncode(String name) throws IOException {
        recording = false;
        if (frames.isEmpty()) return null;

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String fileName = name + "-" + LocalDateTime.now().format(f) + ".zip";
        File outDir = new File(System.getProperty("user.dir"), "target/videos");
        outDir.mkdirs();
        File outFile = new File(outDir, fileName);

        try (FileOutputStream fos = new FileOutputStream(outFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (File fimg : frames) {
                ZipEntry entry = new ZipEntry(fimg.getName());
                zos.putNextEntry(entry);
                try (FileInputStream fis = new FileInputStream(fimg)) {
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
                zos.closeEntry();
            }
        }

        // cleanup temp frames
        try { FileUtils.deleteDirectory(tempDir); } catch (Exception ignored) {}
        return outFile;
    }
}
