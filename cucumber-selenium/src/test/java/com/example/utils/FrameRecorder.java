// package com.example.utils;

// import org.apache.commons.io.FileUtils;
// import org.openqa.selenium.OutputType;
// import org.openqa.selenium.TakesScreenshot;
// import org.openqa.selenium.WebDriver;

// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.List;

// public class FrameRecorder {
//     private final WebDriver driver;
//     private final List<File> frames = new ArrayList<>();
//     private volatile boolean running = false;
//     private Thread worker;
//     private final File framesDir;

//     public FrameRecorder(WebDriver driver, String scenarioName) throws IOException {
//         this.driver = driver;
//         DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
//         String dirName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_") + "-" + LocalDateTime.now().format(f);
//         framesDir = new File(System.getProperty("user.dir"), "target/videos/" + dirName + "/frames");
//         FileUtils.forceMkdirParent(framesDir);
//         framesDir.mkdirs();
//     }

//     public void start() {
//         running = true;
//         worker = new Thread(() -> {
//             int i = 0;
//             while (running) {
//                 try {
//                     File shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//                     File dest = new File(framesDir, String.format("frame-%05d.png", i++));
//                     Files.copy(shot.toPath(), dest.toPath());
//                     frames.add(dest);
//                 } catch (Exception ignored) {}
//                 try { Thread.sleep(500); } catch (InterruptedException ignored) {}
//             }
//         });
//         worker.setDaemon(true);
//         worker.start();
//     }

//     public File stopAndZip() throws IOException {
//         running = false;
//         try { if (worker != null) worker.join(2000); } catch (InterruptedException ignored) {}
//         // zip frames into frames.zip next to the frames directory
//         File zip = new File(framesDir.getParentFile(), "frames.zip");
//         try (java.io.FileOutputStream fos = new java.io.FileOutputStream(zip);
//              java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(fos)) {
//             File[] files = framesDir.listFiles((d, name) -> name.toLowerCase().endsWith(".png"));
//             if (files != null) {
//                 java.util.Arrays.sort(files);
//                 byte[] buffer = new byte[4096];
//                 for (File f : files) {
//                     java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry(f.getName());
//                     zos.putNextEntry(entry);
//                     try (java.io.FileInputStream fis = new java.io.FileInputStream(f)) {
//                         int len;
//                         while ((len = fis.read(buffer)) > 0) {
//                             zos.write(buffer, 0, len);
//                         }
//                     }
//                     zos.closeEntry();
//                 }
//             }
//         }
//         // leave frames on disk; user can run ffmpeg to create video: ffmpeg -r 10 -i frame-%05d.png -vcodec libx264 out.mp4
//         return zip.exists() ? zip : null;
//     }
// }
