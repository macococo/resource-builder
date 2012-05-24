package jp.mofbrown.resource.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class UrlAppender {

	private static final String[] EXTENSIONS = new String[] {".html", ".php", ".jsp", ".js", ".css"};
	private static final Pattern URL_PATTERN = Pattern.compile("\\.(js|css|gif|jpg|jpeg|png)(\\\\|\"|'|\\)|\\s)");
	
	private static final String LS = System.getProperty("line.separator");

	public void append(Long build, String path) throws IOException {
		append(build, new File(path));
	}

	protected String appendLine(final String line, final Long build) {
		String l = line;
		l = l.replaceAll("\\?" + getPrefix() + "[0-9]+", "");
		l = StringUtils.replace(l, "$", "\\$");
		
		Matcher matcher = URL_PATTERN.matcher(l);
		
		if (matcher.find()) {
			String group1 = matcher.group(1);
			String group2 = matcher.group(2);
			
			l = matcher.replaceAll(Matcher.quoteReplacement("." + group1 + "?" + getPrefix() + build + group2));
		}
		
		return StringUtils.replace(l, "\\$", "$");
	}
	
	protected String getPrefix() {
		return "b";
	}
	
	private void append(Long build, File parent) throws IOException {
		if (parent.isDirectory()) {
			for (File file : parent.listFiles()) {
				if (file.isDirectory()) {
					append(build, file);
				} else if (isTargetFile(file)) {
					appendBuild(build, file);
				}
			}
		} else if (isTargetFile(parent)) {
			appendBuild(build, parent);
		}
	}
	
	private boolean isTargetFile(File file) {
		for (String extension : EXTENSIONS) {
			if (file.getName().lastIndexOf(extension) > 1) {
				String ext = file.getName().substring(file.getName().lastIndexOf(extension), file.getName().length());
				if (extension.equals(ext)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void appendBuild(Long build, File file) throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		final StringBuilder builder = new StringBuilder();

		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(appendLine(line, build)).append(LS);
			}
		} finally {
			reader.close();
		}
		
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
		try {
			writer.write(builder.toString());	
		} finally {
			writer.close();	
		}
	}
	
}
