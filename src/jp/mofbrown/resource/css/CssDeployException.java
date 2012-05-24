package jp.mofbrown.resource.css;

/**
 * Script圧縮時の例外を表すException
 * @author mkudo
 *
 */
@SuppressWarnings("serial")
public class CssDeployException extends Exception {

	public CssDeployException(String message) {
		super(message);
	}
	
	public CssDeployException(Throwable cause) {
		super(cause);
	}
	
}
