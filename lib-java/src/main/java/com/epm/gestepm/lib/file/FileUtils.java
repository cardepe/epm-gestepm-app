package com.epm.gestepm.lib.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class FileUtils {

	private static final Log log = LogFactory.getLog(FileUtils.class);

	private FileUtils() {

	}
	
	public static byte[] compressImage(MultipartFile mpFile) {
		
		float quality = 0.3f;
		String imageName = mpFile.getOriginalFilename();
		String imageExtension = imageName.substring(imageName.lastIndexOf(".") + 1);

		// Returns an Iterator containing all currently registered ImageWriters that claim to be able to encode the named format.
		// You don't have to register one yourself; some are provided.
		ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(imageExtension).next();
		ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
		
		if (imageWriteParam.canWriteCompressed()) { 
			imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // Check the api value that suites your needs.
			imageWriteParam.setCompressionType(parseCompressionType(imageExtension));
			imageWriteParam.setCompressionQuality(quality); // A compression quality setting of 0.0 is most generically interpreted as "high compression is important," while a setting of 1.0 is most generically interpreted as "high image quality is important."
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		// MemoryCacheImageOutputStream: An implementation of ImageOutputStream that writes its output to a regular OutputStream, i.e. the ByteArrayOutputStream.
		ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(baos);
		
		// Sets the destination to the given ImageOutputStream or other Object.
		imageWriter.setOutput(imageOutputStream);
		
		BufferedImage originalImage = null;
		
		try (InputStream inputStream = mpFile.getInputStream()) {
			originalImage = ImageIO.read(inputStream);
		} catch (IOException e) {
			String info = String.format("compressImage - bufferedImage (file %s)- IOException - message: %s ",
					imageName, e.getMessage());
			log.error(info);
			return baos.toByteArray();
		}
		
		IIOImage image = new IIOImage(originalImage, null, null);
		
		try {
			imageWriter.write(null, image, imageWriteParam);
		} catch (IOException e) {
			String info = String.format("compressImage - imageWriter (file %s)- IOException - message: %s ", imageName, e.getMessage());
			log.error(info);
		} finally {
			imageWriter.dispose();
		}
		
		return baos.toByteArray();
	}

	public static byte[] compressBytes(byte[] data) {

		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];

		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}

		try {
			outputStream.close();
		} catch (IOException e) {
			log.error(e);
		}

		log.info("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	public static byte[] decompressBytes(byte[] data) {

		Inflater inflater = new Inflater();
		inflater.setInput(data);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];

		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
			log.error(ioe);
		} catch (DataFormatException e) {
			log.error(e);
		}

		return outputStream.toByteArray();
	}

	private static String parseCompressionType(final String compressionType) {

		if ("gif".equalsIgnoreCase(compressionType)) {
			return "LZW";
		} else if ("jpg".equalsIgnoreCase(compressionType)) {
			return "JPEG";
		} else if ("png".equalsIgnoreCase(compressionType)) {
			return "Deflate";
		}

		return compressionType.toUpperCase();
	}
}
