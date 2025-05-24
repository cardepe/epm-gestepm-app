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
        try (
                ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage originalImage = ImageIO.read(bais);
            if (originalImage == null) {
                throw new IOException("No se pudo leer la imagen.");
            }

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
            if (!writers.hasNext()) {
                throw new IllegalStateException("No JPEG writers found!");
            }

            ImageWriter writer = writers.next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality); // Ej: 0.85f para buena calidad visual

            writer.setOutput(new MemoryCacheImageOutputStream(baos));
            writer.write(null, new javax.imageio.IIOImage(originalImage, null, null), param);
            writer.dispose();

            return baos.toByteArray();
        }
    }
}
