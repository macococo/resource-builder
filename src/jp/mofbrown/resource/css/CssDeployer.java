package jp.mofbrown.resource.css;

import java.io.File;
import java.io.IOException;

import jp.mofbrown.resource.common.BuildUtils;

/**
 * スクリプトファイルを圧縮/難読化した1ファイルを生成します
 * @author mkudo
 *
 */
public class CssDeployer {
	
	private CssDeployProperty prop;
	
	public CssDeployer(final CssDeployProperty prop) {
		this.prop = prop;
	}
	
	public CssDeployer(final String path) {
		this.prop = new CssDeployProperty(path);
	}
	
	public void deploy() throws CssDeployException {
		if (!validateProperty()) return;
		
		try {
			System.out.println("\ncompress start!");
			
			String firstDeployDirectory = null;
			for (String deployDirectory : prop.getDeployDirectories()) {
				String deployDirectoryPath = deployDirectory + File.separator + prop.getFileName() + ".css";
				
				if (firstDeployDirectory == null) {
					firstDeployDirectory = deployDirectoryPath;
					
					BuildUtils.mkdirOnNotExist(deployDirectory);
					BuildUtils.delete(deployDirectoryPath);
					
					CssCompressor compressor = new YUICssCompressor();
					compressor.compress(prop, deployDirectoryPath);
					if (prop.isEncodeBackgroundImage()) {
						new CSSImageEncoder().encode(deployDirectoryPath, prop.getEncoding());
					}
				} else {
					BuildUtils.copy(firstDeployDirectory, deployDirectoryPath);
				}
			}
		} catch (IOException e) {
			throw new CssDeployException(e);
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
