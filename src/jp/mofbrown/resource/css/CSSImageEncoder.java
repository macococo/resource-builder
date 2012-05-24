package jp.mofbrown.resource.css;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class CSSImageEncoder {
	
	private static final Pattern PATTERN = Pattern.compile("url\\(([^\\)]+)\\)");

	public void encode(final String cssPath, final String encode) throws IOException {
		System.out.println("encode image.");
		
		final File file = new File(cssPath);
		if (!file.exists()) {
			System.out.println(String.format("%s not found.", cssPath));
			System.exit(-1);
		}
		if (Double.parseDouble(System.getProperty("java.specification.version")) < 1.6) {
			System.out.println("Please run with Java version 1.6 or later.");
			System.exit(-1);
		}
		
		String content = IOUtils.toString(new FileInputStream(file), encode);
		
		final Matcher matcher = PATTERN.matcher(content);
		while (matcher.find()) {
			final String fullpath = matcher.group(1);
			String path = fullpath;
			if (path.indexOf("?") > 0) {
				path = path.substring(0, path.indexOf("?"));
			}
			
			final File image = new File(file.getParent() + File.separator + path);
			final String data = "data:image/" + getExtent(image) + ";base64," + encodeImage(image);
			
			System.out.println("[encode] " + fullpath + " => " + data);
			content = StringUtils.replace(content, fullpath, data);
		}
		
		IOUtils.write(content, new FileOutputStream(file), encode);
	}
	
	private String encodeImage(final File file) throws IOException {
		final BufferedImage image = ImageIO.read(file);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final BufferedOutputStream bos = new BufferedOutputStream(baos);

		image.flush();
		ImageIO.write(image, getExtent(file), bos);
		bos.flush();
		bos.close();
		byte[] bImage = baos.toByteArray();
		
		return Base64.encode(bImage);
	}
	
	private String getExtent(final File file) {
		final String name = file.getName();
		return name.substring(name.lastIndexOf(".") + 1, name.length());
	}
	
}
