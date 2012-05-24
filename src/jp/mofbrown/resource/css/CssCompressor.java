package jp.mofbrown.resource.css;

import java.io.IOException;

public interface CssCompressor {

	void compress(final CssDeployProperty prop, final String deployDirectoryPath) throws IOException;
	
}
