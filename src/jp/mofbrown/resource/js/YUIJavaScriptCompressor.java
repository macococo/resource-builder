package jp.mofbrown.resource.js;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import jp.mofbrown.resource.common.BuildUtils;

import com.yahoo.platform.yui.compressor.YUICompressor;

public class YUIJavaScriptCompressor implements JavaScriptCompressor {

	public void compress(final JavaScriptDeployProperty prop, final String deployDirectoryPath) throws IOException {
		for (String file : prop.getTargetFiles()) {
			if (isIgnoreFile(prop, file)) {
				appendFile(prop.getSourceDirectory() + File.separator + file, deployDirectoryPath, prop.getEncoding(), prop.isAppend());
			} else {
				compressFile(prop.getSourceDirectory() + File.separator + file, deployDirectoryPath, prop.getEncoding(), prop.isAppend());
			}
		}
	}
	
	private boolean isIgnoreFile(final JavaScriptDeployProperty prop, final String file) {
		for (final Pattern pattern : prop.getIgnoreFilePatterns()) {
			if (pattern.matcher(file).matches()) {
				return true;
			}
		}
		return false;
	}
	
	public void appendFile(final String inFileName, final String outFileName, final String encoding, final boolean append) throws IOException {
		System.out.println("[append ] " + inFileName);
		BuildUtils.appendFile(inFileName, outFileName);
	}
	
	public void compressFile(final String inFileName, final String outFileName, final String encoding, final boolean append) throws IOException {
		String in = inFileName;
		
		if (!append) {
			String[] newArguments = new String[5];
			in = outFileName + ".filepart";
			
			newArguments[0] = "--charset";
			newArguments[1] = encoding;
			newArguments[2] = "-o";
			newArguments[3] = in;
			newArguments[4] = inFileName;
			
			System.out.println("[compress ] " + inFileName);
			
			YUICompressor.main(newArguments);
		}

		try {
			BuildUtils.appendFile(in, outFileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BuildUtils.delete(outFileName + ".filepart");
		}
	}
	
}
