package jp.mofbrown.resource.css;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import jp.mofbrown.resource.common.BuildUtils;

import com.yahoo.platform.yui.compressor.YUICompressor;

/**
 * スクリプトを圧縮します܂
 * @author mkudo
 *
 */
public class YUICssCompressor implements CssCompressor {

	public void compress(final CssDeployProperty prop, final String deployDirectoryPath) throws IOException {
		for (String file : prop.getTargetFiles()) {
			if (isIgnoreFile(prop, file)) {
				appendFile(prop.getSourceDirectory() + File.separator + file, deployDirectoryPath, prop.getEncoding(), prop.isAppend());
			} else {
				compressFile(prop.getSourceDirectory() + File.separator + file, deployDirectoryPath, prop.getEncoding(), prop.isAppend());
			}
		}
	}
	
	private boolean isIgnoreFile(final CssDeployProperty prop, final String file) {
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
		String[] newArguments = new String[7];
		
		newArguments[0] = "--type";
		newArguments[1] = "css";
		newArguments[2] = "--charset";
		newArguments[3] = "UTF-8";
		newArguments[4] = "-o";
		newArguments[5] = outFileName + ".filepart";
		newArguments[6] = inFileName;
		
		System.out.println("[compress ] " + inFileName);
		
		YUICompressor.main(newArguments);

		try {
			BuildUtils.appendFile(outFileName + ".filepart", outFileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BuildUtils.delete(outFileName + ".filepart");
		}
	}
	
}