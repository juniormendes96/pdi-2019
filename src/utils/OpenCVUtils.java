package utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class OpenCVUtils {

	public static Image mat2Image(Mat frame) {
		// create a temporary buffer
		MatOfByte buffer = new MatOfByte();
		// encode the frame in the buffer, according to the PNG format
		Imgcodecs.imencode(".png", frame, buffer);
		// build and return an Image created from the image encoded in the
		// buffer
		return new Image(new ByteArrayInputStream(buffer.toArray()));
	}

	public static Mat image2Mat(Image image) {
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		return bufferedImage2Mat(bImage);
	}

	public static Mat bufferedImage2Mat(BufferedImage in) {
		Mat out;
		byte[] data;
		int r, g, b;
		int height = in.getHeight();
		int width = in.getWidth();
		if (in.getType() == BufferedImage.TYPE_INT_RGB || in.getType() == BufferedImage.TYPE_INT_ARGB) {
			out = new Mat(height, width, CvType.CV_8UC3);
			data = new byte[height * width * (int) out.elemSize()];
			int[] dataBuff = in.getRGB(0, 0, width, height, null, 0, width);
			for (int i = 0; i < dataBuff.length; i++) {
				data[i * 3 + 2] = (byte) ((dataBuff[i] >> 16) & 0xFF);
				data[i * 3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
				data[i * 3] = (byte) ((dataBuff[i] >> 0) & 0xFF);
			}
		} else {
			out = new Mat(height, width, CvType.CV_8UC1);
			data = new byte[height * width * (int) out.elemSize()];
			int[] dataBuff = in.getRGB(0, 0, width, height, null, 0, width);
			for (int i = 0; i < dataBuff.length; i++) {
				r = (byte) ((dataBuff[i] >> 16) & 0xFF);
				g = (byte) ((dataBuff[i] >> 8) & 0xFF);
				b = (byte) ((dataBuff[i] >> 0) & 0xFF);
				data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b)); // luminosity
			}
		}
		out.put(0, 0, data);
		return out;
	}

}
