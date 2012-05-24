package jp.mofbrown.resource.css;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import jp.mofbrown.resource.common.SimpleXPath;

/**
 * Script圧縮設定情報を格納するクラス
 * @author mkudo
 *
 */
public class CssDeployProperty {
	
	private static final String APPEND = "append";
	
	private static final String DEFAULT_ENCODING = "UTF-8";

	public CssDeployProperty() {
		this.encoding = DEFAULT_ENCODING;
		this.deployDirectories = new ArrayList<String>();
		this.targetFiles = new ArrayList<String>();
		this.ignoreFilePatterns = new ArrayList<Pattern>();
	}
	
	public CssDeployProperty(final String configPath) {
		try {
			final SimpleXPath xpath = new SimpleXPath(configPath);
			
			this.isAppend = APPEND.equals(xpath.getNodeBody("//script-compress/type"));
			this.encoding = xpath.getNodeBody("//script-compress/encoding");
			this.fileName = xpath.getNodeBody("//script-compress/filename");
			this.sourceDirectory = xpath.getNodeBody("//script-compress/source-directory");
			this.deployDirectories = xpath.getNodeListBody("//script-compress/deploy-directories/directory");
			this.targetFiles = xpath.getNodeListBody("//script-compress/targetfiles/file");
			this.ignoreFilePatterns = new ArrayList<Pattern>();
			
			if (this.encoding == null || this.encoding.length() <= 0) {
				this.encoding = DEFAULT_ENCODING;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	private boolean isAppend = false;
	
	private String encoding;
	
	/**
	 * スクリプトビルドファイル名称
	 */
	private String fileName;
	
	/**
	 * スクリプトソースディレクトリ
	 */
	private String sourceDirectory;
	
	/**
	 * スクリプトデプロイディレクトリ
	 */
	private List<String> deployDirectories;
	
	/**
	 * 圧縮対象スクリプトファイルを格納したList
	 */
	private List<String> targetFiles;
	
	/**
	 * background-image に指定された画像をエンコードするかどうか
	 */
	private boolean encodeBackgroundImage;
	
	/**
	 * 圧縮を行わないファイルパターン
	 */
	private List<Pattern> ignoreFilePatterns;

	public boolean isAppend() {
		return isAppend;
	}

	public void setAppend(boolean isAppend) {
		this.isAppend = isAppend;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSourceDirectory() {
		return sourceDirectory;
	}

	public void setSourceDirectory(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}

	public List<String> getDeployDirectories() {
		return deployDirectories;
	}

	public void setDeployDirectories(List<String> deployDirectories) {
		this.deployDirectories = deployDirectories;
	}

	public List<String> getTargetFiles() {
		return targetFiles;
	}

	public void setTargetFiles(List<String> targetFiles) {
		this.targetFiles = targetFiles;
	}

	public List<Pattern> getIgnoreFilePatterns() {
		return ignoreFilePatterns;
	}

	public void setIgnoreFilePatterns(List<Pattern> ignoreFilePatterns) {
		this.ignoreFilePatterns = ignoreFilePatterns;
	}

	public boolean isEncodeBackgroundImage() {
		return encodeBackgroundImage;
	}

	public void setEncodeBackgroundImage(boolean encodeBackgroundImage) {
		this.encodeBackgroundImage = encodeBackgroundImage;
	}
	
}
