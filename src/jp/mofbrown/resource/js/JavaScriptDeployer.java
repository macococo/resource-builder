package jp.mofbrown.resource.js;

import java.io.File;
import java.io.IOException;

import jp.mofbrown.resource.common.BuildUtils;

/**
 * スクリプトファイルを圧縮/難読化した1ファイルを生成します
 * @author mkudo
 *
 */
public class JavaScriptDeployer {
	
	private JavaScriptDeployProperty prop;
	
	public JavaScriptDeployer(final JavaScriptDeployProperty prop) {
		this.prop = prop;
	}
	
	public JavaScriptDeployer(final String path) {
		this.prop = new JavaScriptDeployProperty(path);
	}
	
	public void deploy() throws JavaScriptDeployException {
		deploy(null);
	}
	
	public void deploy(final JavaScriptCompressFinishedListener listener) throws JavaScriptDeployException {
		if (!validateProperty()) return;
		
		try {
			System.out.println("\ncompress start!");
			
			String firstDeployDirectory = null;
			for (String deployDirectory : prop.getDeployDirectories()) {
				String deployDirectoryPath = deployDirectory + File.separator + prop.getFileName() + ".js";
				
				if (firstDeployDirectory == null) {
					firstDeployDirectory = deployDirectoryPath;
					
					BuildUtils.mkdirOnNotExist(deployDirectory);
					BuildUtils.delete(deployDirectoryPath);
					
					JavaScriptCompressor compressor = new YUIJavaScriptCompressor();
					compressor.compress(prop, deployDirectoryPath);
					
					if (listener != null) {
						listener.onFinished(deployDirectoryPath);	
					}
				} else {
					BuildUtils.copy(firstDeployDirectory, deployDirectoryPath);
				}
			}
		} catch (IOException e) {
			throw new JavaScriptDeployException(e);
		}
	}
	
	private boolean validateProperty() {
		boolean exist = true;
		
		System.out.println("[filename] " + prop.getFileName());
		
		File sourceDirectory = new File(prop.getSourceDirectory());
		System.out.println("[source-directory] path:" + sourceDirectory.getAbsolutePath() + ", exist:" + sourceDirectory.exists());
		if (!sourceDirectory.exists()) exist = false;
		
		for (String deploy : prop.getDeployDirectories()) {
			File deployDirectory = new File(deploy);
			System.out.println("[deploy-directory] path:" + deployDirectory.getAbsolutePath() + ", exist:" + deployDirectory.exists());
			if (!deployDirectory.exists()) exist = false;
		}
		
		if (!exist) {
			System.out.println("directory not exist.");
		}
		
		return exist;
	}
	
}
