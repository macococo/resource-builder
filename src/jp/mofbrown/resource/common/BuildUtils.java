package jp.mofbrown.resource.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FileUtils;

/**
 * ビルド処理に関するユーティリティクラス
 * @author mkudo
 *
 */
public class BuildUtils {

	/**
	 * ディレクトリを作成します
	 * @param file
	 * @return
	 */
	public static boolean mkdir(File file) {
		System.out.println("[mkdir    ] " + file.getPath());
		return file.mkdirs();
	}
	
	/**
	 * ディレクトリが存在しない場合に作成します
	 * @param fileName
	 * @return
	 */
	public static boolean mkdirOnNotExist(String fileName) {
		File file = new File(fileName);
		
		if (!file.exists()) {
			return mkdir(file);
		}
		return false;
	}
	
	/**
	 * ディレクトリを下位階層まで作成します
	 * @param file
	 * @return
	 */
	public static boolean mkdirs(File file) {
		System.out.println("[mkdirs   ] " + file.getPath());
		return file.mkdirs();
	}
	
	/**
	 * ファイルをコピーします
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFile(File src, File dest) throws IOException {
		System.out.println("[copy file] " + src.getPath() + " to " + dest.getPath());
		FileUtils.copyFile(src, dest);
	}
	
	/**
	 * ファイルをコピーします
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void copy(String src, String dest) throws IOException {
		copyFile(new File(src), new File(dest));
	}
	
	/**
	 * ファイルを削除します
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File file) throws IOException {
		if (file.exists()) {
			System.out.println("[delete   ] " + file.getPath());
			file.delete();			
		}
	}
	
	/**
	 * ファイルを削除します
	 * @param fileName
	 * @throws IOException
	 */
	public static void delete(final String fileName) throws IOException {
		delete(new File(fileName));
	}

	/**
	 * ファイル内容を連結します
	 * @param inputFileName
	 * @param outFileName
	 * @throws IOException
	 */
	public static void appendFile(final String inputFileName, final String outFileName) throws IOException {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "UTF-8"));
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileName, true), "UTF-8"));
			
			String line = "";

			while ((line = reader.readLine()) != null) {
				writer.write(line + "\n");
			}
			
			writer.flush();
		} finally {
			if (reader != null) reader.close();
			if (writer != null) writer.close();
		}
	}
	
}
