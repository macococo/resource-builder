package jp.mofbrown.resource.js;

import java.io.IOException;

public interface JavaScriptCompressor {

	void compress(final JavaScriptDeployProperty prop, final String deployDirectoryPath) throws IOException;
	
}
