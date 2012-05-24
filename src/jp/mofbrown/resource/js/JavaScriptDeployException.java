package jp.mofbrown.resource.js;

/**
 * Script圧縮時の例外を表すException
 * @author mkudo
 *
 */
@SuppressWarnings("serial")
public class JavaScriptDeployException extends Exception {

	public JavaScriptDeployException(String message) {
		super(message);
	}
	
	public JavaScriptDeployException(Throwable cause) {
		super(cause);
	}
	
}
