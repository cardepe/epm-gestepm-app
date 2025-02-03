package com.epm.gestepm.model.common.pdf;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageUtils {

    public static byte[] compressImage(byte[] imageBytes, float quality) throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        final BufferedImage originalImage = ImageIO.read(bais);

        final int newWidth = originalImage.getWidth() / 2;
        final int newHeight = originalImage.getHeight() / 2;
        final BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No JPEG writers found!");
        }
        final ImageWriter writer = writers.next();
        final ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality); // Calidad (0.0 = peor calidad, 1.0 = mejor calidad)

        writer.setOutput(new MemoryCacheImageOutputStream(baos));
        writer.write(null, new IIOImage(resizedImage, null, null), param);
        writer.dispose();

        return baos.toByteArray();
    }
}
